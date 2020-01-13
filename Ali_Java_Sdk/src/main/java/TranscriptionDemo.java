import com.alibaba.fastjson.JSON;
import response.HttpResponse;
import utils.HttpUtil;
import utils.RequestBody;

/**
 * Created by shaosongsong on 16/5/18.
 */

public class TranscriptionDemo {

    /**
     * 服务url
     * */
    private static String url = "https://nlsapi.aliyun.com/transcriptions";
    private static RequestBody body = new RequestBody();
    private static HttpUtil request = new HttpUtil();



    public static void main(String[] args) throws InterruptedException {
        String ak_id = "LTAI4FjejeuKtMLLUwqBCApD"; //数加管控台获
        // 得的accessId
        String ak_secret = "vSKEgd7Vgv389oY8sn18AL49aT2h5e"; // 数加管控台获得的accessSecret

        body.setApp_key("nls-service-shurufa16khz"); //简介页面给出的Appkey
        body.setFile_link("https://chendragon.oss-cn-beijing.aliyuncs.com/test.pcm");//离线文件识别的文件url,推荐使用oss存储文件。链接大小限制为128MB

		//热词接口
        //使用热词需要指定Vocabulary_id字段，如何设置热词参考文档：[热词设置](~~49179~~)
        //body.setVocabulary_id("vocab_id");

		/* 获取完整识别结果，无需设置本参数！*/
        //body.addValid_time(100,2000,0);       //validtime  可选字段  设置的是语音文件中希望识别的内容,begintime,endtime以及channel
        //body.addValid_time(2000,10000,1);   //validtime  默认不设置。可选字段  设置的是语音文件中希望识别的内容,begintime,endtime以及channel
		/* 获取完整识别结果，无需设置本参数！*/

        System.out.println("Recognize begin!");

        /*
        * 发送录音转写请求
        * **/
        String bodyString;
        bodyString = JSON.toJSONString(body,true);
        System.out.println("bodyString is:" + bodyString);
        HttpResponse httpResponse = HttpUtil.sendPost(url,bodyString,ak_id,ak_secret);
        if (httpResponse.getStatus()==200) {
            System.out.println("post response is:" + httpResponse.getResult());
        }else {
            System.out.println("error msg: "+httpResponse.getMassage());
        }

        /*
        * 通过TaskId获取识别结果
        * **/
        if (httpResponse.getStatus() == 200) {
            String TaskId = JSON.parseObject(httpResponse.getResult()).getString("id");
            String status = "RUNNING";
            HttpResponse getResponse = null;
            while (status.equals("RUNNING")) {
                Thread.sleep(10000);
                getResponse = HttpUtil.sendGet(url, TaskId, ak_id, ak_secret);
                if (getResponse.getStatus() == 200) {
                    status = JSON.parseObject(getResponse.getResult()).getString("status");
                    System.out.println("get response is:" + getResponse.getResult());
                }else {
                    System.out.println("error msg: "+getResponse.getMassage());
                    break;
                }
            }

            System.out.println("Recognize over!");
        }
    }
}

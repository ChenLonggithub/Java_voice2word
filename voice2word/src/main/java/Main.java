import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

/**
 * 本地转换文字
 */
public class Main {

    //设置APPID/AK/SK
    public static final String APP_ID = "17825717";
    public static final String API_KEY = "0Ractl77lNIi0IlYijgcwPN0";
    public static final String SECRET_KEY = "zc9hvsgfbmPPMvilXxUbGhGqF9PERVh7";

    public static void main(String[] args) throws Throwable {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        asr(client);
    }

    public static void asr(AipSpeech client) throws Throwable {
        // 对本地语音文件进行识别
        //String path = "C:\\Users\\PlusTech\\Desktop\\public\\public\\16k.pcm";
        //String path = "C:\\Users\\PlusTech\\Desktop\\public\\public\\16k.wav";
        String path = "C:\\Users\\PlusTech\\Desktop\\test.pcm";

        JSONObject asrRes = client.asr(path, "pcm", 16000, null);
        System.out.println(asrRes);

        // 对语音二进制数据进行识别
        //byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        //JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);
        //System.out.println(asrRes2);

    }
}

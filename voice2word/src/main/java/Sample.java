import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "17825717";
    public static final String API_KEY = "0Ractl77lNIi0IlYijgcwPN0";
    public static final String SECRET_KEY = "zc9hvsgfbmPPMvilXxUbGhGqF9PERVh7";

    public static void main(String[] args) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "classPath:log4j.properties");

        // 调用接口
        JSONObject res = client.asr("C:\\Users\\PlusTech\\Desktop\\2019-11-21.wav", "wav", 16000, null);
        System.out.println(res.toString());
        
    }
}

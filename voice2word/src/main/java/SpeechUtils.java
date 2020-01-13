import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.json.JSONObject;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

/**
 * 百度语音工具类
 */
public class SpeechUtils {

    public static final String APP_ID = "17825717";
    public static final String API_KEY = "0Ractl77lNIi0IlYijgcwPN0";
    public static final String SECRET_KEY = "zc9hvsgfbmPPMvilXxUbGhGqF9PERVh7";

    /**
     * 语音合成
     * @param text 文字内容
     * @param Path 合成语音生成路径
     * @return
     */
    public static void SpeechSynthesis(String text, String Path) {
        /*
        最长的长度
         */
        int maxLength = 1024;
        if (text.getBytes().length >= maxLength) {
            return ;
        }
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        TtsResponse res = client.synthesis(text, "zh", 1, null);
        byte[] data = res.getData();
        //定义变量调用转换格式
        boolean a = true;
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, "D:\\rap\\output.mp3");
                a=false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!a) {
            convertMP3ToPcm(Path,"D:\\temp\\output.pcm");
        }

    }

    /**
     * 语音识别
     * @param Path 路径
     * @param Path 语音类型
     * @return
     */
    public static String SpeechRecognition(String Path) throws IOException {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        byte[] data = Util.readFileByBytes(Path);     //readFileByBytes仅为获取二进制数据示例
        JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);
        return asrRes2.toString();
    }


    /**
     *  mp3转pcm
     * @param mp3path MP3文件存放路径
     * @param pcmpath pcm文件保存路径
     * @return
     */
    public static boolean convertMP3ToPcm(String mp3path, String pcmpath){
        try {
            //获取文件的音频流，pcm的格式
            AudioInputStream audioInputStream = getPcmAudioInputStream(mp3path);
            //将音频转化为  pcm的格式保存下来
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmpath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得pcm文件的音频流
     * @param mp3filepath
     * @return
     */
    private static AudioInputStream getPcmAudioInputStream(String mp3filepath) {
        File mp3 = new File(mp3filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

    public static void main(String[] args) throws IOException {

        //SpeechSynthesis("你好，我是dog", "D:\\output.mp3");
        //这个参数是刚合成的本地路径

       // boolean b = convertMP3ToPcm("C:\\Users\\PlusTech\\Desktop\\测试.mp3", "C:\\Users\\PlusTech\\Desktop\\2.pcm");
        //System.out.println(b);
        //String s = SpeechRecognition("C:\\Users\\PlusTech\\Desktop\\public\\public\\16k.pcm");
        //String s = SpeechRecognition("C:\\Users\\PlusTech\\Desktop\\test.pcm");
        //System.out.println(s);

        //["我不知道，还曾总您好，那我是深圳大管家的环境还是这样的，真的我们现在针对贵公司的说说，
        // 嘿嘿，那有一个项目进度管理和客户关系管理系统，然后请郑总您这边来了解一下吧，啊，就我们
        // 这边是做智能化企业管理系统的小孩是针对公司内部人员管理这方面可以帮助您去解决一些商务
        // 制制度管理，客户管理以及智能绩效管理这方面的问题的啊，这种临之前是有了解过企业管理系统的吗？
        // 我不需要不，我先加一下您的微信，把我们的资料发给您先了解一下咯，后续有需要的话我们可以再联系嘛，
        // 这个手机号可以加您的微信吗？"


        //嗯,嗯。,唉张总，您好！,你好，啊我是深圳大管家的海军。,是张总张总。,我们现在针对贵公司的实施行业呢有一个
        // 项目进度管理和客户关系管理系统，想邀请郑总您这边来了解一下吧。,什么东西！,啊就我们这边是做智能化企业管理
        // 系统的，主要是针对公司内部人员管理这方面，,它可以帮助您去解决一些项目技术管理、客户管理以及智能绩效管理这方面的问题的。
        // ,啊这种您之前是有了解过企业管理系统的吗？,我不需要！,啊那要不我先加一下您的微信，把我们的资料发给您，
        // 先了解一下了。,后续有需要的话我们可以再联系嘛。,嗯这个手机号可以加您的微信吗
    }

}
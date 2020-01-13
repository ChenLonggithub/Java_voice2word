package utils;

/**
 * Created by songsong.sss on 16/5/23.
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.crypto.spec.SecretKeySpec;
import response.HttpResponse;
import sun.misc.BASE64Encoder;
import javax.crypto.Mac;
import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;
@SuppressWarnings("restriction")
public class HttpUtil {
    static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    /*
     * 计算MD5+BASE64
     */
    public static String MD5Base64(String s) throws UnsupportedEncodingException {
        if (s == null)
            return null;
        String encodeStr = "";

        //string 编码必须为utf-8
        byte[] utfBytes = s.getBytes("UTF-8");

        MessageDigest mdTemp;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(utfBytes);
            byte[] md5Bytes = mdTemp.digest();
            BASE64Encoder b64Encoder = new BASE64Encoder();
            encodeStr = b64Encoder.encode(md5Bytes);
        } catch (Exception e) {
            throw new Error("Failed to generate MD5 : " + e.getMessage());
        }
        return encodeStr;
    }

    /*
     * 计算 HMAC-SHA1
     */
    public static String HMACSha1(String data, String key) {
        String result;
        try {

            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = (new BASE64Encoder()).encode(rawHmac);
        } catch (Exception e) {
            throw new Error("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /*
     * 等同于javaScript中的 new Date().toUTCString();
     */
    public static String toGMTString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    /*
     * 发送POST请求
     */
    public static HttpResponse sendPost(String url, String body, String ak_id, String ak_secret) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HttpResponse response = new HttpResponse();
        try {
            URL realUrl = new URL(url);

            /*
             * http header 参数
             */
            String method = "POST";
            String accept = "application/json";
            String content_type = "application/json";
            String date = toGMTString(new Date());

            // 1.对body做MD5+BASE64加密
            String bodyMd5 = MD5Base64(body);
            String stringToSign = method + "\n" + accept + "\n" + bodyMd5 + "\n" + content_type + "\n" + date ;
            // 2.计算 HMAC-SHA1
            String signature = HMACSha1(stringToSign, ak_secret);
            // 3.得到 authorization header
            String authHeader = "Dataplus " + ak_id + ":" + signature;

            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", accept);
            conn.setRequestProperty("content-type", content_type);
            conn.setRequestProperty("date", date);
            conn.setRequestProperty("Authorization", authHeader);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(body);
            // flush输出流的缓冲
            out.flush();
            response.setStatus(conn.getResponseCode());
            // 定义BufferedReader输入流来读取URL的响应
            if (response.getStatus() ==200){
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            if (response.getStatus() == 200){
                response.setResult(result);
                response.setMassage("OK");
            }else {
                response.setMassage(result);
            }
            System.out.println("post response status code: ["+response.getStatus()+"], response massage : ["+response.getMassage()+"] ,result :["+response.getResult()+"]");

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }


    /*
     * GET请求
     */

    public static HttpResponse sendGet(String url, String task_id,String ak_id, String ak_secret) {
        String result = "";
        BufferedReader in = null;
        HttpResponse response = new HttpResponse();
        try {
            URL realUrl = new URL(url+"/"+task_id);
            /*
             * http header 参数
             */
            String method = "GET";
            String accept = "application/json";
            String content_type = "application/json";
            String path = realUrl.getFile();
            String date = toGMTString(new Date());
            // 1.对body做MD5+BASE64加密
            //String bodyMd5 = MD5Base64("");
            String stringToSign = method + "\n" + accept + "\n" + "" + "\n" + content_type + "\n" + date;
            // 2.计算 HMAC-SHA1
            String signature = HMACSha1(stringToSign, ak_secret);
            // 3.得到 authorization header
            String authHeader = "Dataplus " + ak_id + ":" + signature;
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", accept);
            connection.setRequestProperty("content-type", content_type);
            connection.setRequestProperty("date", date);
            connection.setRequestProperty("Authorization", authHeader);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response.setStatus(connection.getResponseCode());
            // 定义BufferedReader输入流来读取URL的响应
            if (response.getStatus() ==200){
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            if (response.getStatus() == 200){
                response.setResult(result);
                response.setMassage("OK");
            }else {
                response.setMassage(result);
            }
            System.out.println("get response status code: ["+response.getStatus()+"], response massage : ["+response.getMassage()+"] ,result :["+response.getResult()+"]");

        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}

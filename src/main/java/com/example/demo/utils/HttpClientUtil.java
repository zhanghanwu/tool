package com.example.demo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pc on 15-1-21.
 */
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static String getPostString(Map<String, String> params) {
        StringBuilder bufferString = new StringBuilder();
        Iterator it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            if (bufferString.length()>0) {
                bufferString.append("&");
            }
            bufferString.append(key);
            bufferString.append("=");
            try {
                bufferString.append(URLEncoder.encode(params.get(key), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("编码转为uft-8失败",e);
            }
        }
        return bufferString.toString();
    }

    /**
     * @Description: 发送请求信息，并返回处理结果
     * @param map
     * @param url
     * @return
     * @author 王家佳
     * @date 2015-9-17 下午6:46:23
     */
    public static Map postSend(Map<String,String> map, String url){
        String result = sendPostRequestUrl(map,url);
        JSONObject json =(JSONObject) JSONValue.parse(result);
        return json;
    }
    /**
     * 发送post请求，并取得返回数据
     * @param params
     * @param url
     * @return
     */
    public static String sendPostRequestUrl(Map<String, String> params,String url) {
        return sendPostRequestUrl(params,url,"GBK");
    }

    /**
     * 发送post请求，并取得返回数据
     * @param params
     * @param url
     * @return
     */
    public static String sendPostRequestUrl(Map<String, String> params,String url,String encode) {
        String result = null;
        try {
            URL _url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)_url.openConnection();
            /**
             * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。 通过把URLConnection设为输出，可以把数据向你个Web页传送。下面是如何做：
             */
            connection.setRequestProperty("accept", "text/html");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0b11) Gecko/20100101 Firefox/4.0b11");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            /**
             *为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
             */
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), encode);
            String paramstr = getPostString(params);
            //out.write("IPT_LOGINUSERNAME=" + name + "&IPT_LOGINPASSWORD=" + password); // 向页面传递数据。post的关键所在！
            out.write(paramstr); // 向页面传递数据。post的关键所在！
            out.flush();
            out.close();
            /**
             * 这样就可以发送一个看起来象这样的POST： POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT: text/plain Content-type:
             * application/x-www-form-urlencoded Content-length: 99 username=bob password=someword
             */
            //一旦发送成功，用以下方法就可以得到服务器的回应：
            String sCurrentLine = "";
            StringBuilder bufferString = new StringBuilder();
            InputStream l_urlStream = connection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(l_urlStream,encode));
            while ((sCurrentLine = buffer.readLine()) != null) {
                bufferString.append(sCurrentLine);
            }
            l_urlStream.close();
            result = bufferString.toString();
        } catch (Exception e) {
            logger.error("调用http返回结果异常:",e);
        }
        return result;
    }
}

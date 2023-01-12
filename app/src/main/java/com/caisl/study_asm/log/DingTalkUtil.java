package com.caisl.study_asm.log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: CaiSongL
 * @date: 2023/1/11 17:24
 */
public class DingTalkUtil {
    public static String DING_TALK_URL = "https://oapi.dingtalk.com/robot/send?access_token=acdbbe7ab40ae4277100841570b1ab76ac82c6a4522d895125c9ac6d1336d185";

//    public static String DING_TALK_URL = "https://oapi.dingtalk.com/robot/send?access_token=deabd312af5e63b6fbfbdc294fa36e993e6caa6b1f8f7728f18eaaf4044341bc";

    /**
     * 发送POST请求，参数是Map, contentType=x-www-form-urlencoded
     * @return
     */
    public static String sendPostByMap(String content) {


        Map<String,Object> json = new HashMap();
        Map<String,Object> text = new HashMap();
        Map<String, List<String>> phone = new HashMap();
        String [] mobile = {};
        List mob = new ArrayList<String>();
        // 数据库填入注册用户的手机号
        mob.add("");//要@的人的手机号码
        json.put("msgtype","markdown");//指定消息类型是text
        HashMap<String,Object> markDownMap = new HashMap<>();
        markDownMap.put("title","监控系统发现异常");
        markDownMap.put("text",content);
        json.put("markdown",markDownMap);
        phone.put("atMobiles", mob);//将手机号码放进参数中
//        json.put("text",text);
        json.put("at", phone);
        Map<String, String> headParam = new HashMap();
        //指定http的内容类型为JSON数据格式
        headParam.put("Content-type", "application/json;charset=UTF-8");
        return sendPost(DING_TALK_URL, json, headParam);

//        // 请求的JSON数据，这里我用map在工具类里转成json格式
//        Map<String,Object> json = new HashMap();
//        Map<String,Object> text = new HashMap();
//        Map<String, List<String>> phone = new HashMap();
//        String [] mobile = {};
//        List mob = new ArrayList<String>();
//        // 数据库填入注册用户的手机号
//        mob.add("");//要@的人的手机号码
//        json.put("msgtype","text");//指定消息类型是text
//        text.put("content",content);//
//        phone.put("atMobiles", mob);//将手机号码放进参数中
//        json.put("text",text);
//        json.put("at", phone);
//
//        Map<String, String> headParam = new HashMap();
//        //指定http的内容类型为JSON数据格式
//        headParam.put("Content-type", "application/json;charset=UTF-8");
//        return sendPost(DING_TALK_URL, json, headParam);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, Object> param, Map<String, String> headParam) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);//通过此对象可以解析出url中的所有信息，比如协议，验证信息，端口，请求参数，定位位置等
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性 请求头
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Fiddler");

            if (headParam != null) {
                for (Map.Entry<String, String> entry : headParam.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(new Gson().toJson(param));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            logger.info("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
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
        return result;
    }

}

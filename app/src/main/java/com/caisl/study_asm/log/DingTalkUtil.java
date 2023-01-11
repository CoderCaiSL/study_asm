package com.caisl.study_asm.log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author: CaiSongL
 * @date: 2023/1/11 17:24
 */
public class DingTalkUtil {

    public void sendMSG(String msg){
        String pathUrl = "钉钉机器人链接";
        try {
            URL url = new URL(pathUrl);
            HttpURLConnection httpPost= (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

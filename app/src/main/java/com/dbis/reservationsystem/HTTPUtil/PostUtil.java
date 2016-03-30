package com.dbis.reservationsystem.HTTPUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nklyp on 2016/3/13.
 */
public class PostUtil {
    public static String sendPost(String url,String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();

            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection", "Keep-Alive");

            connection.setDoInput(true);
            connection.setDoOutput(true);

            out = new PrintWriter(connection.getOutputStream());
            out.print(params);

            out.flush();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            line = in.readLine();
            while (true) {
                result += line;
                line = in.readLine();
                if (line != null) {
                    result += "/n";
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("发送post请求出现异常！"+e);
            e.printStackTrace();
        } finally {
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

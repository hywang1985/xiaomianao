package com.jianfeng.xiaomianao.util;

public class URLEncoder {

    public static String encode(String content, String enc) {
        try {
            String result = java.net.URLEncoder.encode(content, enc);
            result = result.replaceAll("\\+", "%20");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeUTF8(String content) {
        return encode(content, HttpUtil.UTF8);
    }
}

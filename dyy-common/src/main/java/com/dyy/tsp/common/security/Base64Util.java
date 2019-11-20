package com.dyy.tsp.common.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("all")
public class Base64Util {
    // 解密
    public static byte[] decode(String s) {
        byte[] b = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }
    // 解密
    public static String decod(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    } 
    // 加密  
    public static String encod(String s) {  
        String result = null;  
        if (s != null) {  
            BASE64Encoder encoder = new BASE64Encoder();  
            try {  
            	result = encoder.encodeBuffer(s.getBytes());  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }
    // 加密
    public static String encode(byte[] bytes) {
        String result = null;
        if (bytes.length>0) {
            BASE64Encoder encoder = new BASE64Encoder();
            try {
                result = encoder.encodeBuffer(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

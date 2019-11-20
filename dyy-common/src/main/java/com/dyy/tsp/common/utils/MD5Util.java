package com.dyy.tsp.common.utils;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * created by dyy
 */
@SuppressWarnings("all")
public class MD5Util {

    private static final String ALGORITHM = "MD5";
    
    public static String digest(String in) {
        return DatatypeConverter.printHexBinary(digest(in.getBytes(Charset.forName("UTF-8")))).toLowerCase();
    }
    
    private static byte[] digest(byte[] in) {
        try { 
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.reset();
            return messageDigest.digest(in);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}

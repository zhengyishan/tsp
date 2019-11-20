package com.dyy.tsp.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip压缩工具
 * 只适合大数据 字节数组压缩
 * created by dyy
 */
@SuppressWarnings("all")
public class GzipUtil {

    /**
     * 数据压缩
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        ByteArrayOutputStream bos = null;
        GZIPOutputStream gzip = null;
        try {
            bos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            b = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bos!=null){
                try{
                    bos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(gzip!=null){
                try{
                    gzip.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return b;
    }

    /**
     * 数据解压缩
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] unGzip(byte[] data){
        byte[] b = null;
        ByteArrayOutputStream baos = null;
        GZIPInputStream gzip = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(data);
            gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(baos!=null){
                try{
                    baos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(gzip!=null){
                try{
                    gzip.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(bis!=null){
                try{
                    bis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return b;
    }

}

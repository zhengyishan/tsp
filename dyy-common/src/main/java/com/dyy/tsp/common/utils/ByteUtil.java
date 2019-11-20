package com.dyy.tsp.common.utils;

import com.dyy.tsp.common.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 字节操作类
 * created by dyy
 */
@SuppressWarnings("all")
public class ByteUtil {

    private final static char[] digits = {'0', '1'};

    /**
     * 切割字节数组
     * @param bytes
     * @param size
     * @return
     */
    public static byte[][] splitBytes(byte[] bytes, int size) {
        double splitLength = Double.parseDouble(size + "");
        int arrayLength = (int) Math.ceil(bytes.length / splitLength);
        byte[][] result = new byte[arrayLength][];
        int from, to;
        for (int i = 0; i < arrayLength; i++) {

            from = (int) (i * splitLength);
            to = (int) (from + splitLength);
            if (to > bytes.length)
                to = bytes.length;
            result[i] = Arrays.copyOfRange(bytes, from, to);
        }
        return result;
    }

    /**
     * 合并多个byte数组返回
     * @param values
     * @return
     */
    public static byte[] mergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xff);
        if (hex.length() == 1)
            hex = (new StringBuilder(String.valueOf('0'))).append(hex).toString();
        return hex.toUpperCase();
    }
    /**
     *
     * @Title: byteToHex
     * @Description: 字节类型转16进制
     * @param: @param
     *             bs[]
     * @param: @return
     * @return: String
     */
    public static String byteToHex(byte bs[]) {
        StringBuffer sb = new StringBuffer();
        if (bs != null && bs.length > 0) {
            for (int i = 0; i < bs.length; i++)
                sb.append(byteToHex(bs[i]));

        }
        return sb.toString();
    }

    public static byte hexToByte(int n) {
        return (byte) n;
    }

    public static byte hexToByte(String hexString) {
        byte b[] = hexStringToBytes(hexString);
        if (b == null || b.length != 1)
            return b[0];
        else
            return b[0];

    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals(""))
            return null;
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char hexChars[] = hexString.toCharArray();
        byte d[] = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String parseAscii(byte bs[]) {
        StringBuffer str = new StringBuffer("");
        for (int i = 0; i < bs.length; i++) {
            char a = (char) Integer.parseInt(String.valueOf(bs[i]));
            str.append(a);
        }
        return str.toString();
    }

    public static String getAscii(String str) {
        if (StringUtils.isEmpty(str))
            return null;
        char ch[] = str.toCharArray();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < ch.length; i++)
            sb.append(Integer.toHexString(Integer.valueOf(Integer.toString(ch[i])).intValue()));
        return sb.toString();
    }

    public static String byteToStr(byte bs[]) {
        StringBuffer sb = new StringBuffer();
        if (bs != null && bs.length > 0) {
            for (int i = 0; i < bs.length; i++) {
                String str = String.valueOf(bs[i] & 0xff);
                if (str.length() == 1)
                    str = (new StringBuilder(String.valueOf('0'))).append(str).toString();
                sb.append(str);
            }

        }
        return sb.toString();
    }

    public static byte[] stringToBytes(String str) throws UnsupportedEncodingException{
        byte bytes[] = str.getBytes(Constants.GBK);
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xf0) >> 4));
            sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xf) >> 0));
        }
        return hexStringToBytes(sb.toString());
    }

    public static byte[] stringToBytes(String str,String charCode) throws UnsupportedEncodingException {
        byte bytes[] = str.getBytes(charCode);
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xf0) >> 4));
            sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xf) >> 0));
        }
        return hexStringToBytes(sb.toString());
    }

    public static String bytesToString(byte bts[]) {
        String bytes = byteToHex(bts);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write(
                    "0123456789ABCDEF".indexOf(bytes.charAt(i)) << 4 | "0123456789ABCDEF".indexOf(bytes.charAt(i + 1)));

        try {
            return new String(baos.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteToBinary(byte bytes[]) {
        StringBuffer sb = new StringBuffer();
        byte abyte0[];
        int j = (abyte0 = bytes).length;
        for (int i = 0; i < j; i++) {
            byte b = abyte0[i];
            sb.append(byteToBinary(b));
        }

        return sb.toString();
    }

    public static String byteToBinary(byte bye) {
        String status1 = Integer.toBinaryString(bye & 0xff);
        int length = 8 - status1.length();
        for (int i = 0; i < length; i++)
            status1 = (new StringBuilder("0")).append(status1).toString();

        return status1;
    }

    public static String byteToBinary(int num) {
        String status1 = Integer.toBinaryString(num);
        int length = 8 - status1.length();
        for (int i = 0; i < length; i++)
            status1 = (new StringBuilder("0")).append(status1).toString();

        return status1;
    }

    public static byte[] long2Byte(long x) {
        byte bb[] = new byte[8];
        bb[0] = (byte) (int) (x >> 56);
        bb[1] = (byte) (int) (x >> 48);
        bb[2] = (byte) (int) (x >> 40);
        bb[3] = (byte) (int) (x >> 32);
        bb[4] = (byte) (int) (x >> 24);
        bb[5] = (byte) (int) (x >> 16);
        bb[6] = (byte) (int) (x >> 8);
        bb[7] = (byte) (int) (x >> 0);
        return bb;
    }

    public static Long byte2Long(byte bb[]) {
        if (bb != null && bb.length == 8) {
            ByteBuffer aa = ByteBuffer.wrap(bb);
            return Long.valueOf(aa.getLong());
        } else {
            return null;
        }
    }

    public static byte[] int2Byte(int x) {
        byte bb[] = new byte[4];
        bb[0] = (byte) (x >> 24);
        bb[1] = (byte) (x >> 16);
        bb[2] = (byte) (x >> 8);
        bb[3] = (byte) (x >> 0);
        return bb;
    }

    public static byte[] short2Byte(short x) {
        byte bb[] = new byte[2];
        bb[0] = (byte) (x >> 8);
        bb[1] = (byte) (x >> 0);
        return bb;
    }

    public static Short byte2Short(byte bb[]) {
        if (bb != null && bb.length == 2) {
            ByteBuffer aa = ByteBuffer.wrap(bb);
            return Short.valueOf(aa.getShort());
        } else {
            return null;
        }
    }

    public static Character byte2Char(byte bb[]) {
        if (bb != null && bb.length == 2) {
            ByteBuffer aa = ByteBuffer.wrap(bb);
            return Character.valueOf(aa.getChar());
        } else {
            return null;
        }
    }

    public static float byte2float(byte b[], int index) {
        int l = b[index + 0];
        l &= 0xff;
        l = (int) ((long) l | (long) b[index + 1] << 8);
        l &= 0xffff;
        l = (int) ((long) l | (long) b[index + 2] << 16);
        l &= 0xffffff;
        l = (int) ((long) l | (long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * 计算CRC校验码
     * @param data
     * @return
     */
    public static int getCRC(List<Short> data) {
        short crc = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            crc ^= data.get(i);
        }
        return crc;
    }

    /**
     * 计算CRC校验码
     * @param data
     * @param len
     * @return
     */
    public static int getCRC(byte[] data, int len) {
        // byte b = data[0];
        // Integer.toHexString(b & 0xff);
        short crc = (short) (data[0] & 0xFF);
        for (int i = 1; i < len; i++) {
            crc ^= data[i] & 0xFF;
        }
        return crc;
    }

    public static byte getCS(byte data[]) throws Exception {
        int sum = 0;
        for (int i = 0; i < data.length; i++)
            sum += Integer.valueOf(byteToHex(data[i]), 16).intValue();

        String sumHex = Integer.toHexString(sum);
        sumHex = sumHex.substring(sumHex.length() - 2);
        return hexToByte(sumHex);
    }

    public static short getShort(byte bytes[]) {
        return (short) (0xff & bytes[0] | 0xff00 & bytes[1] << 8);
    }

    public static char getChar(byte bytes[]) {
        return (char) (0xff & bytes[0] | 0xff00 & bytes[1] << 8);
    }

    public static int getUnsignedInt(byte bytes[]) {
        return byte2Short(bytes).shortValue() & 0xffff;
    }

    public static long getUnsignedLong(byte bytes[]) {
        return (long) byte2Int(bytes) & 0xffffffffL;
    }

    public static int byte2Int(byte[] bytes) {
        if (bytes == null || bytes.length < 4)
            return -1;
        return (int) (0xff & bytes[3] | 0xff00 & (bytes[2] << 8) | 0xff0000 & (bytes[1] << 16)
                | 0xff000000 & (bytes[0] << 24));
    }

    public static String getUnsignedChar(byte bytes) {
        return String.valueOf(bytes & 0xff);
    }

    // public static byte[] toByte(int x)
    // {
    // byte bb[] = new byte[4];
    // bb[0] = (byte)(x >> 24);
    // bb[1] = (byte)(x >> 16);
    // bb[2] = (byte)(x >> 8);
    // bb[3] = (byte)(x >> 0);
    // return bb;
    // }

    public static byte[] CreateDateTimeBytes(Calendar datetime) {
        byte[] Reply = new byte[7];
        Reply[0] = (byte) ((datetime.get(Calendar.YEAR) & 0xFF00) >> 8);
        Reply[1] = (byte) (datetime.get(Calendar.YEAR) & 0xFF);
        Reply[2] = (byte) (datetime.get(Calendar.MONTH)+1);
        Reply[3] = (byte) (datetime.get(Calendar.DAY_OF_MONTH));
        Reply[4] = (byte) (datetime.get(Calendar.HOUR_OF_DAY));
        Reply[5] = (byte) (datetime.get(Calendar.MINUTE));
        Reply[6] = (byte) (datetime.get(Calendar.SECOND));
        return Reply;
    }

    public static int byte2int(byte res, byte res1) {
        int targets = (res & 0xff) | ((res1 << 8) & 0xff00);
        return targets;
    }


    /// <summary>
    /// 十六进制时间字节数组转换成时间字符串
    /// </summary>
    /// <param name="dataTimebytes"></param>
    /// <returns></returns>
    public static String bytesToDataTime(byte[] dataTimebytes) {
        Calendar dataTime = Calendar.getInstance();
        // 获取时间
        byte[] year = { 0x00, 0x00 };
        byte[] month = { 0x00 };
        byte[] day = { 0x00 };
        byte[] hour = { 0x00 };
        byte[] min = { 0x00 };
        byte[] second = { 0x00 };
        System.arraycopy(dataTimebytes, 0, year, 0, 2);
        System.arraycopy(dataTimebytes, 2, month, 0, 1);
        System.arraycopy(dataTimebytes, 3, day, 0, 1);
        System.arraycopy(dataTimebytes, 4, hour, 0, 1);
        System.arraycopy(dataTimebytes, 5, min, 0, 1);
        System.arraycopy(dataTimebytes, 6, second, 0, 1);
        // datatime default year,month and day are all equal to 1
        //short curYear =ByteUtil.byte2Short(year);
        dataTime.set(Calendar.YEAR, ByteUtil.byte2int(year[1],year[0]));
        dataTime.set(Calendar.MONTH, (month[0]&0xff)-1);
        dataTime.set(Calendar.DAY_OF_MONTH, (day[0]&0xff));
        dataTime.set(Calendar.HOUR_OF_DAY, (hour[0]&0xff));
        dataTime.set(Calendar.MINUTE, (min[0]&0xff));
        dataTime.set(Calendar.SECOND,(second[0]&0xff));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // "yyyy-MM-dd
        // HH:mm:ss"
        return format.format(dataTime.getTime());
    }

    public static Date bytesToDT(byte[] dataTimebytes) {
        Calendar dataTime = Calendar.getInstance();
        // 获取时间
        byte[] year = { 0x00, 0x00 };
        byte[] month = { 0x00 };
        byte[] day = { 0x00 };
        byte[] hour = { 0x00 };
        byte[] min = { 0x00 };
        byte[] second = { 0x00 };
        System.arraycopy(dataTimebytes, 0, year, 0, 2);
        System.arraycopy(dataTimebytes, 2, month, 0, 1);
        System.arraycopy(dataTimebytes, 3, day, 0, 1);
        System.arraycopy(dataTimebytes, 4, hour, 0, 1);
        System.arraycopy(dataTimebytes, 5, min, 0, 1);
        System.arraycopy(dataTimebytes, 6, second, 0, 1);
        // datatime default year,month and day are all equal to 1
        //short curYear =ByteUtil.byte2Short(year);
        dataTime.set(Calendar.YEAR, ByteUtil.byte2int(year[1],year[0]));
        dataTime.set(Calendar.MONTH, (month[0]&0xff)-1);
        dataTime.set(Calendar.DAY_OF_MONTH, (day[0]&0xff));
        dataTime.set(Calendar.HOUR_OF_DAY, (hour[0]&0xff));
        dataTime.set(Calendar.MINUTE, (min[0]&0xff));
        dataTime.set(Calendar.SECOND,(second[0]&0xff));
        return dataTime.getTime();
    }

    //转成国标时间
    public static byte[] bytesToGBDataTime(byte[] dataTimebytes) {
        //国标时间0.year 1.month 2.day 3.hour 4.min 5.second
        byte[] gbTime = new byte[6];
        byte[] yearByte = { 0x00, 0x00 };
        int yearInt = 0;
        if(dataTimebytes.length==7) {
            System.arraycopy(dataTimebytes, 0, yearByte, 0, 2);
            yearInt = ByteUtil.getUnsignedInt(yearByte)-2000;
            yearByte = ByteUtil.short2Byte((short)yearInt);
            gbTime[0] = yearByte[1];
            System.arraycopy(dataTimebytes, 2, gbTime, 1, 1);
            System.arraycopy(dataTimebytes, 3, gbTime, 2, 1);
            System.arraycopy(dataTimebytes, 4, gbTime, 3, 1);
            System.arraycopy(dataTimebytes, 5, gbTime, 4, 1);
            System.arraycopy(dataTimebytes, 6, gbTime, 5, 1);
            return gbTime;
        } else{
            return null;
        }
    }

    /**
     * String类型的OTA版本号，转换为hex类型的OTA版本号
     * @param version
     * @return
     */
    public static String versionStringToHex(String version) {
        if (version != null) {
            String[] versionitem = version.split("\\.");
            if(versionitem.length != 2) return null;
            String curVersion = byteToHex(short2Byte(Short.valueOf(versionitem[0]))[1])
                    + byteToHex(short2Byte(Short.valueOf(versionitem[1]))[1]);
            return curVersion;
        } else {
            return null;
        }
    }

    /**
     * long值转Calendar对象
     * @param time
     * @return
     */
    public static Calendar longToCalendar(Long time){
        Date date=new Date(time);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    /**
     * long值转国标时间数组
     * @param time
     * @return
     */
    public static byte[] longToGbTime(Long time){
        Calendar calendar = longToCalendar(time);
        byte[] bytes = CreateDateTimeBytes(calendar);
        return bytesToGBDataTime(bytes);
    }

    /**
     * 国标时间数组转format格式化
     * @param time
     * @return
     */
    public static String gbTimeArrayFormat(byte[] bytes1){
        int year = Short.toUnsignedInt(bytes1[0])+2000;
        int month = Byte.toUnsignedInt(bytes1[1]);
        int day = Byte.toUnsignedInt(bytes1[2]);
        int hour = Byte.toUnsignedInt(bytes1[3]);
        int minute = Byte.toUnsignedInt(bytes1[4]);
        int seconds = Byte.toUnsignedInt(bytes1[5]);
        Date date = parseDate(year, month, day, hour, minute, seconds);
        String format = DateTimeUtil.format(date, DateTimeUtil.YYYYMMDDHHMMSS);
        return format;
    }

    /**
     * 国标时间数组转long值
     * @param time
     * @return
     */
    public static Long gbTimeArrayLong(byte[] bytes1){
        int year = Short.toUnsignedInt(bytes1[0])+2000;
        int month = Byte.toUnsignedInt(bytes1[1]);
        int day = Byte.toUnsignedInt(bytes1[2]);
        int hour = Byte.toUnsignedInt(bytes1[3]);
        int minute = Byte.toUnsignedInt(bytes1[4]);
        int seconds = Byte.toUnsignedInt(bytes1[5]);
        Date date = parseDate(year, month, day, hour, minute, seconds);
        return date.getTime();
    }

    public static byte getBCC(byte[] data) {
        byte[] BCC = new byte[1];

        for(int i = 0; i < data.length; ++i) {
            BCC[0] ^= data[i];
        }

        return BCC[0];
    }

    /**
     * 获取一个字节的bit数组
     * @param value
     * @return
     */
    public static byte[] getByteArray(byte value) {
        byte[] byteArr = new byte[8]; //一个字节八位
        for (int i = 7; i > 0; i--) {
            byteArr[i] = (byte) (value & 1); //获取最低位
            value = (byte) (value >> 1); //每次右移一位
        }
        return byteArr;
    }

    /**
     * 把byte转为字符串的bit
     * @param b
     * @return
     */
    public static String byteToBitString(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    /**
     * 将int型整数转成32位的2进制形式
     * @param num
     * @return String
     */
    public static String to32BinaryString(int num) {
        char[] buf = new char[32];
        int pos = 32;
        int mask = 1;
        do
        {
            buf[--pos] = digits[num & mask];
            num >>>= 1;
        }
        while (pos > 0);

        return new String(buf, pos, 32);
    }

    /**
     * 将年月日时分秒转换为date
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param seconds
     * @return
     */
    public static Date parseDate(int year, int month, int day, int hour, int minute, int seconds) {
        return DateTime.now().withDate(year, month, day)
                .withTime(hour, minute, seconds, 0).toDate();
    }


}

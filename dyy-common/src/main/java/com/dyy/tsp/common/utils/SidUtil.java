package com.dyy.tsp.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 国标登入、登出的sid值处理工具
 * created by dyy
 */
@SuppressWarnings("all")
public class SidUtil {
    // gateway 多节点需重新存入redis等
    private static Map<String,AtomicInteger> SID_MAP = new HashMap<String,AtomicInteger>();

    private static Object object = new Object();

    public  static int getSid(String sn) {
        AtomicInteger sid;
        synchronized (object) {
            sid = SID_MAP.get(sn);
            if (sid == null) {
                sid = new AtomicInteger(0);
                SID_MAP.put(sn, sid);
            }
        }
        return sid.get();
    }

    public static int addAndGetSid(String sn,Date date) {
        AtomicInteger sid;
        synchronized (object) {
            sid = SID_MAP.get(sn);
            if (sid == null) {
                sid = new AtomicInteger(0);
                SID_MAP.put(sn, sid);
            }
            int day = new Date().getDay();
            if (date != null && (date.getDay() == day)) {
                if (sid.get() > 65531) {
                    sid.set(0);
                }
            } else {
                sid.set(0);
            }
        }
        return sid.addAndGet(1);
    }
}

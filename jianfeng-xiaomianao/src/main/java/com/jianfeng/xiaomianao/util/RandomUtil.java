package com.jianfeng.xiaomianao.util;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    public static String genRandomNum(int n) {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < n; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        if (sRand.substring(0, 1).equals("0")) {
            sRand = "1" + sRand.substring(1);
        }
        return sRand;
    }

    public static <T> T random(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <T> T random(T[] arr) {
        if (arr == null) {
            return null;
        }
        return arr[new Random().nextInt(arr.length)];
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("\\-", "").toUpperCase();
    }

    public static double getDistance(double wd1, double jd1, double wd2, double jd2) {
        double x, y, out;
        double PI = 3.14159265;
        double R = 6.371229 * 1e6;

        x = (jd2 - jd1) * PI * R * Math.cos(((wd1 + wd2) / 2) * PI / 180) / 180;
        y = (wd2 - wd1) * PI * R / 180;
        out = Math.hypot(x, y);
        return out;
    }
}

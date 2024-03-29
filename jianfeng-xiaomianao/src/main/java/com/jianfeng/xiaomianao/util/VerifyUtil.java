package com.jianfeng.xiaomianao.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class VerifyUtil {

    private static final String regEmail = "^[_\\-a-z0-9A-Z]*?[\\._\\-a-z0-9]*?[a-z0-9]+@[a-z0-9]+[a-z0-9\\-]*?[a-z0-9]+\\.[\\.a-z0-9]*$";

    private static final String regAmount = "[0-9]+";

    /**
     * 验证是否邮箱
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {

        if (StringUtils.isBlank(email)) {

            return false;
        }
        if (Pattern.matches(regEmail, email)) {

            return true;
        }
        return false;
    }

    /**
     * 验证是否手机号
     * 
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {

        return true;
        /*
         * if(StringUtils.isEmpty(phone)) {
         * 
         * return false; } if(Pattern.matches(regPhone, phone)) {
         * 
         * return true; } return false;
         */
    }

    /**
     * 验证是否为金额
     * 
     * @param amount
     * @return
     */
    public static boolean isInt(String amount) {
        if (StringUtil.isEmpty(amount)) {
            return false;
        }
        if (Pattern.matches(regAmount, amount)) {

            return true;
        }
        return false;
    }

    public static boolean isZero(double d) {
        String s = String.valueOf(d);
        String[] values = s.split("\\.");
        if (0 == Long.parseLong(values[0])) {
            if (1 == values.length) {
                return true;
            }
            if (0 == Long.parseLong(values[1])) {
                return true;
            }
        }
        return false;
    }
}

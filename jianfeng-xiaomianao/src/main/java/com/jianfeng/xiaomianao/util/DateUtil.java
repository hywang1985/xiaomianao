package com.jianfeng.xiaomianao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

public class DateUtil {

    private static Date date_1000 = null;

    static {
        try {
            date_1000 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1000-01-01 00:00:00");
        } catch (ParseException e) {
        }
    }

    public static Date get1000Date() {
        return date_1000;
    }

    public static Date parse(String timeStr) {
        return parse("yyyy-MM-dd HH:mm:ss", timeStr);
    }

    public static Date parse(String pattern, String timeStr) {
        try {
            return new SimpleDateFormat(pattern).parse(timeStr);
        } catch (ParseException e) {
        }
        return null;
    }

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String format(long millis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millis));
    }

    public static String format(String pattern, long millis) {
        return new SimpleDateFormat(pattern).format(new Date(millis));
    }

    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 取得下一个更新统计缓存时间，即明日0点
     * 
     * @return
     */
    public static Date nextTaskTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 取得当前月份
     * 
     * @return
     */
    public static Date getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 取得当日0点
     * 
     * @return
     */
    public static Date getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getCurrentDate() {
        return  Calendar.getInstance().getTime();
    }

    public static int getDayWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static Calendar getCalendar(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar;
    }

    public static int getDifferDays(Date begin, Date end) {
        return (int) ((end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000));
    }

    public static int getDifferHours(Date begin, Date end) {
        return (int) ((end.getTime() - begin.getTime()) / (60 * 60 * 1000));
    }

    public static int calcAge(String birthday) {
        int iage = 0;
        if (!StringUtil.isEmpty(birthday)) {
            try {
                int year = Integer.parseInt(birthday.substring(0, 4));
                int month = Integer.parseInt(birthday.substring(4, 6));
                int day = Integer.parseInt(birthday.substring(6, 8));
                Calendar birthDate = new GregorianCalendar(year, month, day);
                Calendar today = Calendar.getInstance();
                if (today.get(Calendar.YEAR) > birthDate.get(Calendar.YEAR)) {
                    iage = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) - 1;
                    if (today.get(Calendar.MONTH) + 1 > birthDate.get(Calendar.MONTH)) {
                        iage++;
                    } else if (today.get(Calendar.MONTH) + 1 == birthDate.get(Calendar.MONTH)) {
                        if (today.get(Calendar.DAY_OF_MONTH) > birthDate.get(Calendar.DAY_OF_MONTH)) {
                            iage++;
                        }
                    }
                }
                return iage;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 获取now之前或者之后的某天
     * 
     * @param now 现在的日期
     * @param day 相对天数（昨天-1，明天1等等）
     * @return
     */
    public static Date getDateByAddDay(Date now, Integer day) {
        DateTime dateTime = new DateTime(now);
        MutableDateTime mdt = dateTime.toMutableDateTime();
        mdt.addDays(day);
        return mdt.toDate();
    }

    public static Date startOfDay(Date date, Integer day) {
        DateTime dt = new DateTime(date);
        MutableDateTime mdt = dt.toMutableDateTime();
        mdt.addDays(day);
        mdt.setHourOfDay(0);
        mdt.setMinuteOfHour(0);
        mdt.setSecondOfMinute(0);
        mdt.setMillisOfSecond(0);
        return mdt.toDate();
    }

    public static Date getRelativeDate(Date now, Long millis) {
        DateTime dateTime = new DateTime(now);
        MutableDateTime mdt = dateTime.toMutableDateTime();
        mdt.add(millis);
        return mdt.toDate();
    }

    public static Date getRelativeDateByHour(Date now, Integer hours) {
        DateTime dateTime = new DateTime(now);
        MutableDateTime mdt = dateTime.toMutableDateTime();
        mdt.addHours(hours);
        return mdt.toDate();
    }
}

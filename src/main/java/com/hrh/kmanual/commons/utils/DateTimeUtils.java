package com.hrh.kmanual.commons.utils;/**
 * Created by Administrator on 2018/10/9 0009.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/9 0009 15:37
 */
public class DateTimeUtils {

    private static SimpleDateFormat format;

    public static final String DEFAULT_TIMEZONE = "GMT+8";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String ONLY_DATE = "yyyy-MM-dd";

    static {
        format = new SimpleDateFormat(DEFAULT_FORMAT);
    }

    private DateTimeUtils() {
    }

    public static String getCurrentTime() {
        return format.format(new Date());
    }

    public static String getCurrentDate() {
        return format(new Date(), ONLY_DATE);
    }

    /**
     * 转换为默认时间格式
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format.format(date);
    }

    /**
     * 自定义格式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 按照默认格式转换时间
     *
     * @param time
     * @return
     */
    public static Date fromString(String time) {

        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 按照指定格式转换时间
     *
     * @param time
     * @param pattern
     * @return
     */
    public static Date fromString(String time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}

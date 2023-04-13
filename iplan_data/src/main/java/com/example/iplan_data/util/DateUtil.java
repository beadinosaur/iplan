package com.example.iplan_data.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * <p>
 * date utility class
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
public class DateUtil {
    private final static String FULL_DATE = "yyyy-MM-dd HH:mm:ss";
    private final static String DATE_NO_S = "yyyy-MM-dd HH:mm";
    private final static String DATE_HH_MM_SS = "HH:mm:ss";

    /**
     * Gets the time of the first day of the year
     *
     * @return
     */
    public static Date getFirstDay() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        //当年第一天
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * <p class="detail">
     * Convert to date in full format yyyy-MM-dd HH:mm:ss
     * </p>
     *
     * @param date
     * @return
     */
    public static Date fullParse(String date) {
        if (CheckUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(FULL_DATE);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }


    /**
     * <p class="detail">
     * Convert to date in full format yyyy-MM-dd HH:mm:ss
     * </p>
     *
     * @param date
     * @return
     */
    public static String format(Timestamp date) {
        if (CheckUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_NO_S);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p class="detail">
     * Convert to date in full format yyyy-MM-dd HH:mm:ss
     * </p>
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (CheckUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_NO_S);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p class="detail">
     * Convert to date in full format yyyy-MM-dd HH:mm:ss
     * </p>
     *
     * @param date
     * @return
     */
    public static String formatHHMMSS(Date date) {
        if (CheckUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_HH_MM_SS);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  get new Start TIme
     * @param startTime
     * @param newStartTIme
     * @return
     */
    public static Date getNewDate(Timestamp startTime, Timestamp newStartTIme) {
        if (!CheckUtil.isEmpty(newStartTIme)) {
            return new Date(newStartTIme.getTime());
        } else if (CheckUtil.isEmpty(newStartTIme) && !CheckUtil.isEmpty(startTime)) {
            return new Date(startTime.getTime());
        } else {
            return new Timestamp(getFirstDay().getTime());
        }
    }

    public static long betweenDate(Date d) {
        return (new Date().getTime() - d.getTime()) / (60 * 60 * 24 * 1000);
    }


    /**
     * Gets the week from the date
     *
     * @param date
     * @return
     */
    public static String dateToWeek(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // Get a calendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Indicates the day of the week
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }
}

package com.example.iplan_data.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * check utility class
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
public class CheckUtil {

    /**
     * Determining the Length of a String
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static Boolean checkStrLength(String str, Integer min, Integer max) {
        return (!isEmpty(str) && str.length() >= min && str.length() <= max);
    }

    /**
     * Determines whether the string is a number
     *
     * @param str
     * @return
     */
    public static Boolean checkNumber(String str) {
        return !isEmpty(str) && str.matches("^\\d+$");
    }

    /**
     * Determines whether the array is completely empty
     *
     * @param o
     * @return
     */
    public static Boolean isAllEmpty(Object[] o) {
        if (o == null)
            return true;

        for (Object tmp : o) {
            if (!isEmpty(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether the string is null or no characters
     *
     * @param o
     * @return
     */
    public static Boolean isEmpty(String o) {
        return (o == null || o.trim().length() == 0);
    }

    /**
     * Determines whether an integer is null or 0
     *
     * @param value
     * @return
     */
    public static Boolean isEmpty(Integer value) {
        return (value == null || value == 0);
    }

    /**
     * Determines whether an integer is null or 0
     *
     * @param value
     * @return
     */
    public static Boolean isEmpty(Long value) {
        return (value == null || value == 0);
    }

    /**
     * Check if List is empty
     * @param list
     * @return
     */
    public static Boolean isEmpty(List<?> list) {
        return (list == null || list.size() == 0);
    }

    /**
     * Determines whether the Map is empty
     *
     * @param map
     * @return
     */
    public static Boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.size() == 0);
    }

    /**
     * Determines whether Set is empty
     * @param set
     * @return
     */
    public static Boolean isEmpty(Set<?> set) {
        return (set == null || set.size() == 0);
    }

    /**
     * Determines whether Object is empty
     *
     * @param o
     * @return
     */
    public static Boolean isEmpty(Object o) {
//		return o == null;
        if (o == null) {
            return true;
        }
        boolean isEmpty = false;
        if (o instanceof String) {
            isEmpty = isEmpty((String) o);
        } else if (o instanceof Integer) {
            isEmpty = isEmpty((Integer) o);
        }
        return isEmpty;
    }

    /**
     * Determines if the array is empty
     *
     * @param o
     * @return
     */
    public static Boolean isEmpty(Object[] o) {
        return (o == null || o.length == 0);
    }

    /**
     * Validates a string against the specified regular expression
     *
     * @param regex
     * @param str
     * @return
     */
    public static Boolean checkRegex(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * Validating positive integers
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static Boolean checkPositive(String str, int min, int max) {
        return checkRegex("^\\d{" + min + "," + max + "}$", str);
    }

    /**
     * Validating positive integers
     *
     * @param str
     * @param length
     * @return
     */
    public static Boolean checkPositive(String str, int length) {
        return checkRegex("^\\d{" + length + "}$", str);
    }

    /**
     * Determine if the value in the Object array is empty
     *
     * @param o
     * @return
     */
    public static boolean oneMoreEmpty(Object[] o) {
        boolean b = false;

        for (int i = 0; i < o.length; i++)
            if (isEmpty(o[i])) {
                b = true;
                break;
            }

        return b;
    }

    /**
     * Determine if the time is today
     *
     * @param date
     * @return
     */
    public static boolean timeIsTodayTime(Date date) {
        if (date == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String paramDate = sdf.format(date);
        String nowDate = sdf.format(new Date());
        return paramDate.equals(nowDate);
    }


    /**
     * Checks the string against the specified regular expression
     *
     * @param reg
     * @param string
     * @return
     */
    public static boolean startCheck(String reg, String string) {
        if (isEmpty(string)) {
            return false;
        }
        boolean tem = false;

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);

        tem = matcher.matches();
        return tem;
    }

    //public static final String REGEX_MOBILE = "^0?1(?:3[0-9]|4[457]|5[0-35-9]|8[0-35-9])\\d{8}$";
    public static final String REGEX_MOBILE = "^0?1(?:[0-9]|[457]|[0-35-9]|[0-35-9])\\d{9}$";

    public static final String REGEX_FLOAT = "^[0-9]+\\.{0,1}[0-9]{0,2}$";

    /**
     * Mobile number verification
     */
    public static boolean checkCellPhone(String cellPhoneNr) {
        // String reg = "^(13[0-9]|14[57]|15[^4]|18[6-9])\\d{8}$";
        return startCheck(REGEX_MOBILE, cellPhoneNr);
    }

    /**
     * Check numbers, including decimals
     *
     * @param number
     * @return
     */
    public static boolean checkFloat(String number) {
        return startCheck(REGEX_FLOAT, number);
    }

    public static final String REGEX_PHONE = "^(0[0-9]{2,3})?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$|(^400[0-9]{7}$)";

    public static boolean checkTel(String phone) {
        return startCheck(REGEX_PHONE, phone);
    }
}

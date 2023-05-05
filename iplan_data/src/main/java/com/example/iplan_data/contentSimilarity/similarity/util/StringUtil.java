package com.example.iplan_data.contentSimilarity.similarity.util;

import java.util.*;

/**
 * String manipulation utility class
 */
public class StringUtil {
    private static final String EMPTY = "";
    private static final String NULL = "null";


    /**
     * Determines whether the string s is empty
     *
     * @return
     */
    public static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }


    /**
     * Determines if the collection is empty
     *
     * @param array
     * @return
     */
    public static boolean isBlank(Collection<? extends Object> array) {
        return array == null || array.size() == 0;
    }


    public static String toString(Object obj) {
        if (obj == null) {
            return NULL;
        } else {
            return obj.toString();
        }
    }

}

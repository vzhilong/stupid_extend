package com.stupid.extend.lang;

/**
 * Created by vincent on 16/6/4.
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 字符串工具集合
 * <p>
 * Created by vincent on 15/8/27.
 */
public class StringUtils {

    private static int INDEX_NOT_FOUND = -1;

    public static String EMPTY = "";

    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == null && cs2 != null || cs1 != null && cs2 == null) {
            return false;
        }
        if (cs1 != null && cs2 != null) {
            if (cs1.length() != cs2.length()) {
                return false;
            } else {
                int len = cs1.length();
                for (int i = 0; i < len; i++) {
                    if (cs1.charAt(i) != cs2.charAt(i)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean notEquals(final CharSequence cs1, final CharSequence cs2) {
        return !equals(cs1, cs2);
    }

    public static String defaultString(final String cs) {
        if (cs == null) {
            return EMPTY;
        }

        return cs;
    }

    public static String defaultString(final String cs, final String defaultString) {
        if (cs == null) {
            return defaultString;
        }

        return cs;
    }

    public static boolean equalsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == null && cs2 != null || cs1 != null && cs2 == null) {
            return false;
        }
        if (cs1 != null && cs2 != null) {
            if (cs1.length() != cs2.length()) {
                return false;
            } else {
                int len = cs1.length();
                for (int i = 0; i < len; i++) {
                    //according to jdk, need two way kinds of equal.
                    if (Character.toUpperCase(cs1.charAt(i)) != Character.toUpperCase(cs2.charAt(i))) {
                        return false;
                    }
                    if (Character.toLowerCase(cs1.charAt(i)) != Character.toLowerCase(cs2.charAt(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return true;
        }

        int strLen = cs.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !StringUtils.isBlank(cs);
    }

    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    public static String trim(final String str, final String trimChars) {
        if (str == null) {
            return null;
        }

        String res = trimLeft(str, trimChars);
        return trimRight(res, trimChars);
    }

    public static String join(String[] arr, final String separator) {
        if (arr == null || arr.length == 0) {
            return EMPTY;
        }
        StringBuffer sb = new StringBuffer(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sb.append(separator).append(arr[i]);
        }

        return sb.toString();
    }

    public static <T> String join(Collection<T> col, final String separator) {
        if (col == null || col.size() == 0) {
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        Iterator<T> iterator = col.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String trimLeft(final String str, final String trimChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (isEmpty(trimChars)) {
            return str;
        } else {
            while (start != strLen && trimChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND) {
                start++;
            }
        }
        return str.substring(start);
    }

    public static String trimRight(final String str, final String trimChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (isEmpty(trimChars)) {
            return str;
        } else {
            while (end != 0 && trimChars.indexOf(str.charAt(end - 1)) != INDEX_NOT_FOUND) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * 必须是整数，不能是空、空格、浮点数
     * 不限制整数的长短
     *
     * @param str
     * @return
     */
    public static boolean isInteger(final String str) {
        if (isBlank(str)) {
            return false;
        }
        final int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBoolean(final String str) {
        return "true".equals(str) || "false".equals(str);
    }

    public static String[] splitByInterval(String s, int interval) {
        int arrayLength = (int) Math.ceil(s.length() * 1.0f / interval);
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

    public static Map<String, String> split(final String str, final String kvSeparator, final String sectionSeparator) {
        Map<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(str)) {
            String[] sections = str.split(Pattern.quote(sectionSeparator));
            if (sections != null && sections.length > 0) {
                for (String section : sections) {
                    if (StringUtils.isNotBlank(section)) {
                        String[] kv = section.split(Pattern.quote(kvSeparator));
                        if (kv != null && kv.length > 0) {
                            if (kv.length > 1) {
                                map.put(kv[0], kv[1]);
                            } else {
                                map.put(kv[0], StringUtils.EMPTY);
                            }
                        }
                    }
                }
            }
        }

        return map;
    }
}
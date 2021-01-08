package com.robbendev.common.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author robbendev
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰
     *
     * @param str str
     * @return camelStr
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();

        return str;
    }

    /**
     * 下划线转驼峰首字母大写
     *
     * @param str sourceStr
     * @return camelStrFirsUpper
     */
    public static String lineToHumpFirstUpper(String str) {
        str = lineToHump(str);
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    /**
     * 驼峰转下划线,效率比上面高
     *
     * @param str str
     * @return camelStr
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine(String)})
     *
     * @param str str
     * @return camelStr
     */
    @Deprecated
    public static String humpToLine2(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }


    /**
     * 首字母转小写
     *
     * @param string string
     * @return result
     */
    public static String toLowerCaseFirstOne(String string) {
        if (org.apache.commons.lang3.StringUtils.isBlank(string)) {
            return string;
        }
        if (Character.isLowerCase(string.charAt(0))) {
            return string;
        } else {
            return Character.toLowerCase(string.charAt(0)) + string.substring(1);
        }
    }

    /**
     * 首字母转大写
     *
     * @param s string
     * @return result
     */
    public static String toUpperCaseFirstOne(String s) {
        if (org.apache.commons.lang3.StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }

    /**
     * object转String 失败返回空字符串
     *
     * @param object obj
     * @return string
     */
    public static String getString(Object object) {
        return getString(object, "");
    }


    /**
     * object转String 失败返回defaultValue
     *
     * @param object       obj
     * @param defaultValue default string
     * @return string
     */
    public static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return object.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * 判断字符串是否为空
     *
     * @param cs cs
     * @return bool
     */
    public static boolean isEmpty(final CharSequence cs) {
        return isBlank(cs);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param cs cs
     * @return bool
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 路径处理
     *
     * @param path path
     * @return path
     */
    public static String pathHandler(String path) {
        path = path.replace("\\\\", "/").replace("\\", "/")
                .replace("///", "/").replace("//", "/");
        if (!path.substring(path.length() - 1, path.length()).equals("/")) {
            path += "/";
        }
        return path;
    }

    /**
     * 从错误堆栈信息错误第一行错误信息
     *
     * @param message error msg
     * @return first line of error msg
     */
    public static String getMainErrorMsg(String message) {
        if (isEmpty(message)) {
            return "";
        }
        message = message.trim();
        int lastSubIdx = message.indexOf("\r");
        if (lastSubIdx <= 0) {
            lastSubIdx = message.indexOf("\n");
        }
        if (lastSubIdx <= 0) {
            lastSubIdx = message.indexOf("\r\n");
        }
        if (lastSubIdx == 0) {
            return message;
        }
        return message.substring(0, lastSubIdx).trim();
    }

    /**
     * 格式化保留两位小数
     *
     * @param val double
     * @return str
     */
    public static String formatDouble(double val) {
        try {
            return formatBigDecimal(new BigDecimal(val));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化保留两位小数
     *
     * @param val decimal
     * @return str
     */
    public static String formatBigDecimal(BigDecimal val) {
        String result = null;
        try {
            result = String.valueOf(val.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式化保留两位小数
     *
     * @param val str
     * @return str
     */
    public static String formatStringNumber(String val) {
        try {
            return formatBigDecimal(new BigDecimal(val));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是数字
     *
     * @param str str
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 是否内地手机号
     *
     * @param phone 手机号
     * @return bool
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    /**
     * 是否能转int
     *
     * @param str str
     * @return bool
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}

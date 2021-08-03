package com.ian.tools.string;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtils
 * 
 * @see http://www.voidcn.com/article/p-wtrgaqvp-qr.html
 * @see https://www.cnblogs.com/LiuChunfu/p/5661810.html
 */
public class StrUtils {

    /**
     * 去除字符串中所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     * 
     * @param s
     * @return
     */
    public static String removeAllBlank(String s) {
        String result = "";
        if (null != s && !"".equals(s)) {
            result = s.replaceAll("[　*| *| *|//s*]*", "");
        }
        return result;
    }

    /**
     * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     * 
     * @param s
     * @return
     */
    public static String trim(String s) {
        String result = "";
        if (null != s && !"".equals(s)) {
            result = s.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
        }
        return result;
    }

    /**
     * 
     * 檢查是否為相同的英數字、連續英文字或連號數字
     * 
     * @param value
     * @param length
     * @return 是連續 true ,不是 false
     */
    public boolean continuousLetterAndNumCheck(String value, int length) {
        // 是否不合法
        boolean isValidate = false;
        //

        // 計數器
        int counter = 1;
        //
        for (int i = 0; i < value.length() - 1; i++) {
            // 當前Ascii值
            int currentAscii = Integer.valueOf(value.charAt(i));
            // 下一個Ascii值
            int nextAscii = Integer.valueOf(value.charAt(i + 1));
            // 滿足區間進行判斷
            if ((this.rangeInDefined(currentAscii, 48, 57) || this.rangeInDefined(currentAscii, 65, 90)
                    || this.rangeInDefined(currentAscii, 97, 122))
                    && (this.rangeInDefined(nextAscii, 48, 57) || this.rangeInDefined(nextAscii, 65, 90)
                            || this.rangeInDefined(nextAscii, 97, 122))) {
                // 計算兩數之間差一位關於連續
                if (Math.abs((nextAscii - currentAscii)) == 1) {
                    // 計數器++
                    counter++;
                } else {
                    // 否計算器重新計數
                    counter = 1;
                }
            }
            // 滿足連續數字或者字母
            if (counter >= length)
                return !isValidate;
        }
        return isValidate;
    }

    /**
     * 
     * 檢查是否為相同的英數字英文字或數字
     * 
     * @param value
     * @param length
     * @return 是相同 true ,不是 false
     */

    public static boolean SameLetterAndNumCheck(String value, int length) {
        // 是否不合法
        boolean isValidate = false;
        // 計數器
        int counter = 1;
        //
        for (int i = 0; i < value.length() - 1; i++) {
            // 當前Ascii值
            int currentAscii = Integer.valueOf(value.charAt(i));
            // 下一個ascii值
            int nextAscii = Integer.valueOf(value.charAt(i + 1));
            // 滿足區間進行判斷
            // 計算兩數之間差一位關於連續
            if (currentAscii == nextAscii) {
                // 計數器++
                counter++;
            } else {
                // 否計算器重新計數
                counter = 1;
            }
            // 滿足連續數字或者字母
            if (counter >= length)
                return !isValidate;
            //
        }
        //
        return isValidate;
    }

    /**
     * 判斷一個數字是否在某個區間
     *
     * @param current 當前比對值
     * @param min     最小範圍值
     * @param max     最大範圍值
     * @return
     */
    public boolean rangeInDefined(int current, int min, int max) {

        return Math.max(min, current) == Math.min(current, max);
    }

    public static Map<String, String> maptest(String f) {

        Map<String, String> maptest = new HashMap<String, String>();
        return maptest;

    }

    /**
     * java去除字串中的空格、回車、分行符號、定位字元
     * 
     * @param str
     * @return
     * @see https://www.oschina.net/code/snippet_107039_6026
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = str.replaceAll("\\s", "");
        }
        return dest;
    }

    static final int HIDELENGHTSTAR = 5; //// 開始
    static final int HIDELENGHTEND = 3; //// 結束
    static final String replaceSymbol = "*"; //// 替換字元

    /**
     * 字串遮罩從後面開始
     * 
     * @param replaceStr 遮罩字串
     * @return
     */
    public static String strReplaceByMask(String replaceStr) {
        int startIndex = replaceStr.length() - HIDELENGHTSTAR;// 開始
        int endIndex = replaceStr.length() + 1 - HIDELENGHTEND;// 結束
        String replaceSymbol = "*";// 替換字元
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < replaceStr.length(); i++) {
            char number = replaceStr.charAt(i);
            if (i >= startIndex && i < endIndex) {
                stringBuilder.append(replaceSymbol);
            } else {
                stringBuilder.append(number);
            }
        }
        return String.valueOf(stringBuilder);
    }

    /**
     * 字串遮罩
     * 
     * @param replaceStr 遮罩字串
     * @return
     */
    public static String strReplaceByMask2(String replaceStr) {
        int startIndex = replaceStr.length() - HIDELENGHTSTAR;// 起始
        String replaceSymbol = "*";// 替換字元
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < replaceStr.length(); i++) {
            char number = replaceStr.charAt(i);
            if (i >= startIndex) {
                stringBuilder.append(replaceSymbol);
            } else {
                stringBuilder.append(number);
            }
        }
        return String.valueOf(stringBuilder);
    }

    /**
     * todo
     */
    public static char[] isNotBlank(String string) {
        return null;
    }

    /**
     * 
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        String nStr = String.valueOf(obj);
        return nStr.isBlank();
    }

}
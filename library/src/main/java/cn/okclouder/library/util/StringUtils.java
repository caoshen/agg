package cn.okclouder.library.util;

import android.text.TextUtils;

public class StringUtils {

    private static final String EMPTY = "";

    /**
     * Tests if a string is blank: null, empty, or only whitespace (" ", \r\n, \t, etc)
     *
     * @param string string to test
     * @return if string is blank
     */
    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0)
            return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!isWhitespace(string.codePointAt(i)))
                return false;
        }
        return true;
    }

    /**
     * Tests if a code point is "whitespace" as defined in the HTML spec.
     *
     * @param c code point to test
     * @return true if code point is whitespace, false otherwise
     */
    private static boolean isWhitespace(int c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }

        StringBuilder sb = new StringBuilder();
        int pos = 0;
        int index = inString.indexOf(oldPattern);
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sb.append(inString.substring(pos));
        return sb.toString();
    }

    private static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static String checkNull(String str) {
        if (TextUtils.isEmpty(str)) {
            return EMPTY;
        } else {
            return str;
        }
    }

}

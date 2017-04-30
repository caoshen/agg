package xyz.dcme.agg.util;

public class StringUtils {

    /**
     * Tests if a string is blank: null, empty, or only whitespace (" ", \r\n, \t, etc)
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
     * @param c code point to test
     * @return true if code point is whitespace, false otherwise
     */
    public static boolean isWhitespace(int c){
        return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
    }
}

package cn.okclouder.ovc.ui.publish.helper;

import java.io.UnsupportedEncodingException;

public class ImageUploadError {

    private static final String UTF_8 = "UTF-8";

    public static String getErrorMessage(String error) {
        try {
            return new String(error.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            return error;
        }
    }

}

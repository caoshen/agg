package xyz.dcme.agg.ui.publish;

import java.io.UnsupportedEncodingException;

public class ImageUploadError {

    public static String getErrorMessage(String error) {
        try {
            return new String(error.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return error;
        }
    }

}

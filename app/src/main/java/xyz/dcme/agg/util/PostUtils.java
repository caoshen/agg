package xyz.dcme.agg.util;

public class PostUtils {

    private PostUtils() {

    }

    /**
     * @param url www.guanggoo.com/t/123#reply1
     * @return post tid
     */
    public static String getUid(String url) {
        if (url.startsWith(Constants.HOME_URL)) {
            String[] splits = url.split(Constants.HOME_URL);
            if (splits.length == 2) {
                url = splits[1];
            }
        }
        if (url.startsWith("/t/")) {
            int pos = url.indexOf("#");
            if (pos <= 0) {
                return url.substring(3);
            } else {
                return url.substring(3, pos);
            }
        }
        return "";
    }
}

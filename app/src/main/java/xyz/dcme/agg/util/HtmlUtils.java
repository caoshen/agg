package xyz.dcme.agg.util;

public class HtmlUtils {
    public static String makeHtml(String body) {
        String html = "<!DOCTYPE html>"
                + "<html lang=\"zh-CN\">"
                + "<head>"
                + "<meta charset=\"utf-8\">"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                + "<link href=\"file:///android_asset/style.css\" rel=\"stylesheet\">"
                + "</head>"
                + "<body>"
                + body
                + "</body>"
                + "</html>";
        return html;
    }
}

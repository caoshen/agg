package xyz.dcme.agg.ui.notify;

public class Message {
    private String mTitle;
    private String mContent;
    private String mLink;

    public Message(String title, String content, String link) {
        mTitle = title;
        mContent = content;
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }
}

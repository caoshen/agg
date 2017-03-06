package xyz.dcme.agg.ui.postdetail.data;

public class PostContent extends PostDetailItem {

    private String mTitle;

    public PostContent(String userName, String avatar, String content) {
        super(userName, avatar, content);
    }

    public PostContent(String userName, String avatar, String content, String title) {
        super(userName, avatar, content);
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}

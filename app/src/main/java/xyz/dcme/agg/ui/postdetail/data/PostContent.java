package xyz.dcme.agg.ui.postdetail.data;

public class PostContent extends PostDetailItem {

    private String mTitle;
    private String mClickCount;
    private String mFavCount;
    private String mLikeCount;
    private String mNode;

    public PostContent(String userName, String avatar, String content, String createTime) {
        super(userName, avatar, content, createTime);
    }

    public PostContent(String userName, String avatar, String content, String createTime, String title,
                       String clickCount, String favCount, String likeCount, String node) {
        super(userName, avatar, content, createTime);
        mTitle = title;
        mClickCount = clickCount;
        mFavCount = favCount;
        mLikeCount = likeCount;
        mNode = node;
    }

    public String getFavCount() {
        return mFavCount;
    }

    public void setFavCount(String favCount) {
        mFavCount = favCount;
    }

    @Override
    public String getLikeCount() {
        return mLikeCount;
    }

    @Override
    public void setLikeCount(String likeCount) {
        mLikeCount = likeCount;
    }

    public String getNode() {
        return mNode;
    }

    public void setNode(String node) {
        mNode = node;
    }

    public String getClickCount() {
        return mClickCount;
    }

    public void setClickCount(String clickCount) {
        mClickCount = clickCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}

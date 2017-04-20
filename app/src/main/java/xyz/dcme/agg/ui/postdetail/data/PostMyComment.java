package xyz.dcme.agg.ui.postdetail.data;

public class PostMyComment extends PostDetailItem {
    private String mTotalCount;

    public PostMyComment(String userName, String avatar, String content, String createTime) {
        super(userName, avatar, content, createTime);
    }

    public String getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(String totalCount) {
        mTotalCount = totalCount;
    }
}

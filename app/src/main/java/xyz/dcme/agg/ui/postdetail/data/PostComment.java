package xyz.dcme.agg.ui.postdetail.data;

public class PostComment extends PostDetailItem {
    public String getReplyId() {
        return mReplyId;
    }

    public void setReplyId(String replyId) {
        mReplyId = replyId;
    }

    private String mReplyId;

    public PostComment(String userName, String avatar, String content, String createTime) {
        super(userName, avatar, content, createTime);
    }

}

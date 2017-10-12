package xyz.dcme.agg.ui.postdetail.data;

public class PostComment extends PostDetailItem {
    private String mVoteUrl;

    public PostComment(String userName, String avatar, String content, String createTime) {
        super(userName, avatar, content, createTime);
    }

    public String getVoteUrl() {
        return mVoteUrl;
    }

    public void setVoteUrl(String voteUrl) {
        mVoteUrl = voteUrl;
    }

}

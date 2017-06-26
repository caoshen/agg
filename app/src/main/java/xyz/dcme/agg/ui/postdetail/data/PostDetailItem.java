package xyz.dcme.agg.ui.postdetail.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PostDetailItem implements Parcelable {
    public static final Creator<PostDetailItem> CREATOR = new Creator<PostDetailItem>() {
        @Override
        public PostDetailItem createFromParcel(Parcel in) {
            return new PostDetailItem(in);
        }

        @Override
        public PostDetailItem[] newArray(int size) {
            return new PostDetailItem[size];
        }
    };
    protected String userName;
    protected String avatar;
    protected String content;
    protected String createTime;
    protected String floor;
    protected String likeCount;

    public PostDetailItem(String userName, String avatar, String content, String createTime) {
        this.userName = userName;
        this.avatar = avatar;
        this.content = content;
        this.createTime = createTime;
    }

    protected PostDetailItem(Parcel in) {
        userName = in.readString();
        avatar = in.readString();
        content = in.readString();
        createTime = in.readString();
        floor = in.readString();
        likeCount = in.readString();
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(avatar);
        parcel.writeString(content);
        parcel.writeString(createTime);
        parcel.writeString(floor);
        parcel.writeString(likeCount);
    }
}

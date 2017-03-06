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

    public PostDetailItem(String userName, String avatar, String content) {
        this.userName = userName;
        this.avatar = avatar;
        this.content = content;
    }

    protected PostDetailItem(Parcel in) {
        userName = in.readString();
        avatar = in.readString();
        content = in.readString();
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
    }
}

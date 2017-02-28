package xyz.dcme.agg.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PostDetail implements Parcelable {
    public String title;
    public String userName;
    public String avatarUrl;
    public String createdTime;
    public String content;

    protected PostDetail(Parcel in) {
        title = in.readString();
        userName = in.readString();
        avatarUrl = in.readString();
        createdTime = in.readString();
        content = in.readString();
    }

    public static final Creator<PostDetail> CREATOR = new Creator<PostDetail>() {
        @Override
        public PostDetail createFromParcel(Parcel in) {
            return new PostDetail(in);
        }

        @Override
        public PostDetail[] newArray(int size) {
            return new PostDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(userName);
        parcel.writeString(avatarUrl);
        parcel.writeString(createdTime);
        parcel.writeString(content);
    }
}

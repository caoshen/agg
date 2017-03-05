package xyz.dcme.agg.model;

import android.os.Parcel;
import android.os.Parcelable;


public class PostComment implements Parcelable {
    public String userName;
    public String avatar;
    public String content;

    public PostComment(String userName, String content, String avatar) {
        this.userName = userName;
        this.content = content;
        this.avatar = avatar;
    }

    protected PostComment(Parcel in) {
        userName = in.readString();
        content = in.readString();
        avatar = in.readString();
    }

    public static final Creator<PostComment> CREATOR = new Creator<PostComment>() {
        @Override
        public PostComment createFromParcel(Parcel in) {
            return new PostComment(in);
        }

        @Override
        public PostComment[] newArray(int size) {
            return new PostComment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(content);
        parcel.writeString(avatar);
    }

}

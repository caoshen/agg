package xyz.dcme.agg.model;

import android.os.Parcel;
import android.os.Parcelable;

import xyz.dcme.agg.ui.postdetail.PostDetailType;


public class PostComment implements Parcelable, PostDetailType {
    public String userName;
    public String content;

    protected PostComment(Parcel in) {
        userName = in.readString();
        content = in.readString();
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
    }

    @Override
    public int getPostType() {
        return PostDetailType.COMMENT_TYPE;
    }
}

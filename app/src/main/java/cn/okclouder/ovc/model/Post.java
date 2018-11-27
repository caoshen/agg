package cn.okclouder.ovc.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    public String title;
    public String avatarUrl;
    public String link;
    public String userName;
    public String lastVisitTime;
    public String node;
    public String readCount;
    public String commentCount;
    public String likeCount;

    public Post(String title, String avatarUrl, String link, String userName, String lastVisitTime,
        String node, String commentCount) {
        this.title = title;
        this.avatarUrl = avatarUrl;
        this.link = link;
        this.userName = userName;
        this.lastVisitTime = lastVisitTime;
        this.node = node;
        this.commentCount = commentCount;
    }

    protected Post(Parcel in) {
        title = in.readString();
        avatarUrl = in.readString();
        link = in.readString();
        userName = in.readString();
        lastVisitTime = in.readString();
        node = in.readString();
        commentCount = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(avatarUrl);
        parcel.writeString(link);
        parcel.writeString(userName);
        parcel.writeString(lastVisitTime);
        parcel.writeString(node);
        parcel.writeString(commentCount);
    }
}

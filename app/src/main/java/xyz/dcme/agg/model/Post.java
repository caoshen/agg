package xyz.dcme.agg.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    public String title;
    public String avatarUrl;

    public Post(String title, String avatarUrl) {
        this.title = title;
        this.avatarUrl = avatarUrl;
    }

    protected Post(Parcel in) {
        title = in.readString();
        avatarUrl = in.readString();
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
    }
}

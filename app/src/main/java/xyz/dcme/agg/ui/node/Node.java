package xyz.dcme.agg.ui.node;

import android.os.Parcel;
import android.os.Parcelable;

public class Node implements Parcelable {
    public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel in) {
            return new Node(in);
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };
    private String mName;
    private String mTitle;

    protected Node(Parcel in) {
        mName = in.readString();
        mTitle = in.readString();
    }

    public Node(String name, String title) {
        mName = name;
        mTitle = title;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mTitle);
    }
}

package cn.okclouder.ovc.ui.node;

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
    private int mFixed;
    private int mCurrent;
    private int mPosition;
    private String mCategory;
    private int mSelected;
    private int mFix;

    protected Node(Parcel in) {
        mName = in.readString();
        mTitle = in.readString();
        mFixed = in.readInt();
        mCurrent = in.readInt();
        mPosition = in.readInt();
        mCategory = in.readString();
        mSelected = in.readInt();

        mFix = in.readInt();
    }

    public Node(String name, String title, String category, int position, int selected, int fix) {
        mName = name;
        mTitle = title;
        mFixed = 0;
        mCurrent = 0;
        mPosition = position;
        mCategory = category;
        mSelected = selected;
        mFix = fix;
    }

    public Node(String name, String title) {
        mName = name;
        mTitle = title;
        mFixed = 0;
        mCurrent = 0;
        mPosition = 0;
        mCategory = null;
        mSelected = 0;
        mFix = 0;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getSelected() {
        return mSelected;
    }

    public void setSelected(int selected) {
        mSelected = selected;
    }

    public int getFix() {
        return mFix;
    }

    public void setFix(int fix) {
        mFix = fix;
    }

    public int getFixed() {
        return mFixed;
    }

    public void setFixed(int fixed) {
        this.mFixed = fixed;
    }

    public int getCurrent() {
        return mCurrent;
    }

    public void setCurrent(int current) {
        this.mCurrent = current;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
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
        dest.writeInt(mFixed);
        dest.writeInt(mCurrent);
        dest.writeInt(mPosition);
        dest.writeString(mCategory);
        dest.writeInt(mSelected);
        dest.writeInt(mFix);
    }
}

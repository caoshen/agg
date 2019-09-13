package cn.okclouder.account;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountInfo implements Parcelable {

    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {
        @Override
        public AccountInfo createFromParcel(Parcel in) {
            return new AccountInfo(in);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };

    private String mId;
    private String mUserName;
    private String mNickName;
    private String mEmail;
    private String mAvatarUrl;
    private String mLink;
    private String mTopicCount;
    private String mReplyCount;
    private String mFavouriteCount;
    private String mCreditCount;
    private String mRegisterCount;
    private String mRegisterTime;

    protected AccountInfo(Parcel in) {
        mId = in.readString();
        mUserName = in.readString();
        mNickName = in.readString();
        mEmail = in.readString();
        mAvatarUrl = in.readString();
        mLink = in.readString();
        mTopicCount = in.readString();
        mReplyCount = in.readString();
        mFavouriteCount = in.readString();
        mCreditCount = in.readString();
        mRegisterCount = in.readString();
        mRegisterTime = in.readString();
    }

    public AccountInfo() {

    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mUserName);
        parcel.writeString(mNickName);
        parcel.writeString(mEmail);
        parcel.writeString(mAvatarUrl);
        parcel.writeString(mLink);
        parcel.writeString(mTopicCount);
        parcel.writeString(mReplyCount);
        parcel.writeString(mFavouriteCount);
        parcel.writeString(mCreditCount);
        parcel.writeString(mRegisterCount);
        parcel.writeString(mRegisterTime);
    }
}

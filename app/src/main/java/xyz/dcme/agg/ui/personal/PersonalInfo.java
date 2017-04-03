package xyz.dcme.agg.ui.personal;

import android.os.Parcel;
import android.os.Parcelable;

import static xyz.dcme.agg.util.LogUtils.makeLogTag;


public class PersonalInfo implements Parcelable {

    private static final String TAG = makeLogTag("PersonalInfo");
    private String mUserId;
    private String mUserName;
    private String mNumber;
    private String mSince;
    private String mNickName;
    private String mCompany;
    private String mTopicsCount;
    private String mRepliesCount;
    private String mFavouritesCount;
    private String mReputationCount;
    private String mIntro;

    public static final Creator<PersonalInfo> CREATOR = new Creator<PersonalInfo>() {
        @Override
        public PersonalInfo createFromParcel(Parcel in) {
            return new PersonalInfo(in);
        }

        @Override
        public PersonalInfo[] newArray(int size) {
            return new PersonalInfo[size];
        }
    };

    protected PersonalInfo(Parcel in) {
        mUserId = in.readString();
        mUserName = in.readString();
        mNumber = in.readString();
        mSince = in.readString();
        mNickName = in.readString();
        mCompany = in.readString();
        mTopicsCount = in.readString();
        mRepliesCount = in.readString();
        mFavouritesCount = in.readString();
        mReputationCount = in.readString();
        mIntro = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserId);
        dest.writeString(mUserName);
        dest.writeString(mNumber);
        dest.writeString(mSince);
        dest.writeString(mNickName);
        dest.writeString(mCompany);
        dest.writeString(mTopicsCount);
        dest.writeString(mRepliesCount);
        dest.writeString(mFavouritesCount);
        dest.writeString(mReputationCount);
        dest.writeString(mIntro);
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getSince() {
        return mSince;
    }

    public void setSince(String since) {
        mSince = since;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getTopicsCount() {
        return mTopicsCount;
    }

    public void setTopicsCount(String topicsCount) {
        mTopicsCount = topicsCount;
    }

    public String getRepliesCount() {
        return mRepliesCount;
    }

    public void setRepliesCount(String repliesCount) {
        mRepliesCount = repliesCount;
    }

    public String getFavouritesCount() {
        return mFavouritesCount;
    }

    public void setFavouritesCount(String favouritesCount) {
        mFavouritesCount = favouritesCount;
    }

    public String getReputationCount() {
        return mReputationCount;
    }

    public void setReputationCount(String reputationCount) {
        mReputationCount = reputationCount;
    }

    public String getIntro() {
        return mIntro;
    }

    public void setIntro(String intro) {
        mIntro = intro;
    }
}

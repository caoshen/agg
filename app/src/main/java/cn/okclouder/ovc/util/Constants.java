package cn.okclouder.ovc.util;

public class Constants {
    public static final String HOME_URL = "http://www.guanggoo.com";
    public static final String LOGIN_URL = HOME_URL + "/login";
    public static final String USER_PROFILE_URL = HOME_URL + "/u/";
    public static final String CREATE_POST_URL = HOME_URL + "/t/create/";

    public static final String TOPIC_URL = "/topics";
    public static final String REPLY_URL = "/replies";

    public static final String EXTRA_ACCOUNT_NAME = "extra_account_name";

    public static final String NODE_NAME = "node_name";
    public static final int FIXED_NODE = 1;
    public static final String HOME_PAGE = "http://www.guanggoo.com/?p=";
    public static final String ACTION_LOGIN_SUCCESS = "cn.okclouder.ovc.action.LOGIN_SUCCESS";
    public static final String FAVORITES = "/favorites";
    public static final String AT = "@";
    public static final String SPACE = " ";
    public static final String UPLOAD_IMAGE_URL = HOME_URL + "/image_upload";
    public static final String ONE = "1";
    public static final String EMPTY_STR = "";
    public static final String ACTION_USER_PAGE = "cn.okclouder.ovc.intent.action.USER_PAGE";

    // website url
    public static final String HOME = "home";
    public static final String PREFIX_HOME = HOME_URL + "/?p=";
    public static final String PREFIX_TAB = HOME_URL + "/?tab=";

    // website tab
    public static final String TAB_LATEST = "latest";
    public static final String TAB_ELITE = "elite";
    public static final String TAB_INTEREST = "interest";
    public static final String TAB_FOLLOWS = "follows";
    public static final String[] TABS = {TAB_LATEST, TAB_ELITE, TAB_INTEREST, TAB_FOLLOWS};
    public static final String[] TABS_NOT_LOGIN = {TAB_LATEST, TAB_ELITE};
    public static final String TAB_NAME = "tab_name";

    // notifications
    public static final String NOTIFICATION_URL = HOME_URL + "/notifications";
    public static final String PACKAGE_NAME = "cn.okclouder.ovc";
    public static final String PROVIDER_AUTH = PACKAGE_NAME + ".provider";

    // favourite
    public static final String ADD_FAVOURITE = HOME_URL +"/favorite?topic_id=";

    // vote
    public static final String VOTE = HOME_URL +"/vote?topic_id=";

    // Register
    public static final String REGISTER_URL = "http://www.guanggoo.com/register";
}

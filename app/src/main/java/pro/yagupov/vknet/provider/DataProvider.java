package pro.yagupov.vknet.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public class DataProvider {

    private static final String
        USER_NAME   = "USER_NAME",
        AVATAR = "AVATAR";

    private static SharedPreferences sSharedPreferences;

    private static DataProvider sProvider;

    private DataProvider(Context pContext) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(pContext);
    }

    public static void onCreate(Context pContext) {
        sProvider = new DataProvider(pContext);
    }

    public static DataProvider getInstance() {
        return sProvider;
    }

    protected static void putString(String pKey, String pValue) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(pKey, pValue);
        editor.apply();
    }

    protected String getString(String pKey) {
        return sSharedPreferences.getString(pKey, "");
    }

    public DataProvider setUserName(String pName) {
        putString(USER_NAME, pName);
        return this;
    }

    public String getUserName() {
        return getString(USER_NAME);
    }

    public DataProvider setAvatar(String pAvatar) {
        putString(AVATAR, pAvatar);
        return this;
    }

    public String getAvatar() {
        return getString(AVATAR);
    }

    public boolean isHasUserInfo() {
        return !(getAvatar().isEmpty() || getUserName().isEmpty());
    }
}

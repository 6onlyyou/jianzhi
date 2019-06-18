package fu.com.parttimejob.utils;

import android.content.SharedPreferences;

import fu.com.parttimejob.App;


/**
 * Created by YuanGang on 2018/9/6.
 */

public class SPUtil {


    public static SharedPreferences getPreferences() {
        return App.getInstance().getSharedPreferences("localData", App.getInstance().MODE_PRIVATE);
    }

    public static String getString(String key, String defValue) {

        return getPreferences().getString(key, defValue);
    }

    public static Boolean getBoolean(String key, Boolean defValue) {

        return getPreferences().getBoolean(key, defValue);
    }

    public static int getInt(String key, int defValue) {

        return getPreferences().getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {

        return getPreferences().getLong(key, defValue);
    }

    /**
     * 保存字符串变量
     */
    public static void saveString(String key, String value) {
        getPreferences().edit().putString(key, value).commit();
    }

    public static void saveBoolean(String key, Boolean value) {
        getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveInt(String key, int value) {
        getPreferences().edit().putInt(key, value).commit();
    }

    public static void saveLong(String key, long value) {
        getPreferences().edit().putLong(key, value).commit();
    }

}

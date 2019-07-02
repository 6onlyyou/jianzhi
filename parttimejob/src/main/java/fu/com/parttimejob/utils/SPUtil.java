package fu.com.parttimejob.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference工具类，用于进行数据缓存以及获取
 */
public class SPUtil {
    private static SharedPreferences preferences;

    public static void getPreference(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        }
    }
    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param value 属性值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        getPreference(context);
        preferences.edit().putBoolean(key, value).commit();
    }
    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param defaultValue 属性值 默认
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        getPreference(context);
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param value 属性值
     */
    public static void putFloat(Context context, String key, float value) {
        getPreference(context);
        preferences.edit().putFloat(key, value).commit();
    }
    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param defaultValue 属性值 默认
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        getPreference(context);
        return preferences.getFloat(key, defaultValue);
    }

    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param value 属性值
     */
    public static void putString(Context context, String key, String value) {
        getPreference(context);
        preferences.edit().putString(key, value).commit();
    }
    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param defaultValue 属性值 默认
     */
    public static String getString(Context context, String key, String defaultValue) {
        getPreference(context);
        return preferences.getString(key, defaultValue);
    }

    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param value 属性值
     */
    public static void putLong(Context context, String key, long value) {
        getPreference(context);
        preferences.edit().putLong(key, value).commit();
    }
    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param defaultValue 属性值 默认
     */
    public static long getLong(Context context, String key, long defaultValue) {
        getPreference(context);
        return preferences.getLong(key, defaultValue);
    }
    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param value 属性值
     */
    public static void putInt(Context context, String key, int value) {
        getPreference(context);
        preferences.edit().putInt(key, value).commit();
    }
    /**
     *
     * @param context  上下文 最好用Application的context
     * @param key 属性名
     * @param defaultValue 属性值 默认
     */
    public static int getInt(Context context, String key, int defaultValue) {
        getPreference(context);
        return preferences.getInt(key, defaultValue);
    }

}

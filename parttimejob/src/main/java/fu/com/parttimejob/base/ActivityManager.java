package fu.com.parttimejob.base;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity 堆栈管理
 * Created by YuanGang on 2018/3/20.
 */
public class ActivityManager {

    public static List<Activity> activityList = new ArrayList<>();

    /**
     * 添加Activity到列表中
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 从列表移除Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public static Activity getCurrentActivity() {
        return activityList.get(activityList.size()-1);
    }

    /**
     * 退出应用程序
     */
    public static void exitApp() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void finishExcept(Class<?> clazz) {
        for (Activity activity : activityList) {
            if (!activity.getClass().equals(clazz)) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }
}

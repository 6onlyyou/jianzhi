package fu.com.parttimejob.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by PVer on 2019/6/15.
 */

public class AppUtils {
    public static String getAppVersion(Context context) {
        String appVersion;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appVersion = pi != null ? pi.versionName : "1.0.0";

        return appVersion;
    }
}

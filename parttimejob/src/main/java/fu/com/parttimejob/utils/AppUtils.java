package fu.com.parttimejob.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import fu.com.parttimejob.bean.GetLabelsBean;

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
    static  int  i = 0;

    public static GetLabelsBean[] setCollection(ArrayList<GetLabelsBean> a, ArrayList<GetLabelsBean>  b) {
        Map<GetLabelsBean, GetLabelsBean> map = new TreeMap<>();
        for (int i = 0; i < a.size(); i++) {
            map.put(a.get(i), a.get(i));
        }
        for (int i = 0; i < b.size(); i++) {
            map.put(b.get(i), b.get(i));
        }
        Collection<GetLabelsBean> values = map.values();
        Iterator<GetLabelsBean> iterator = values.iterator();
        GetLabelsBean[] c = new GetLabelsBean[values.size()];
        while (iterator.hasNext()) {
            c[i++] = iterator.next();
        }
        return c;
    }
}

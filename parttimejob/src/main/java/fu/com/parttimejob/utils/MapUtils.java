package fu.com.parttimejob.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;

import java.util.List;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

public class MapUtils {

    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);

    }

    private static boolean  isInstalled(String packageName, Context mContext) {
        PackageManager manager = mContext.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

    /**
     * 跳转百度地图
     */
    public static void goToBaiduMap(Context context, double mLat, double mLng, String mAddressStr) {
        if (!isInstalled("com.baidu.BaiduMap", context)) {
            Toast.makeText(context, "您手机内尚未安装百度或者高德地图", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + mLat + ","
                + mLng + "|name:" + mAddressStr + // 终点
                "&mode=driving" + // 导航路线方式
                "&src=" + getPackageName()));
        context.startActivity(intent); // 启动调用
    }

    public static void goToGaodeMap(Context mContext, LatLng endPoint, String mAddressStr) {
        if (!isInstalled("com.autonavi.minimap", mContext)) {
            goToBaiduMap(mContext,endPoint.latitude,endPoint.longitude,mAddressStr);
            return;
        }
//        LatLng endPoint = BD2GCJ(latLng);//坐标转换
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=").append("amap");
        stringBuffer.append("&lat=").append(endPoint.latitude)
                .append("&lon=").append(endPoint.longitude).append("&keywords=" + mAddressStr)
                .append("&dev=").append(0)
                .append("&style=").append(2);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        mContext.startActivity(intent);
    }
}

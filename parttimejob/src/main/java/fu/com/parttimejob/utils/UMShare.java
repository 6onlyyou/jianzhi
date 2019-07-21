package fu.com.parttimejob.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.luck.picture.lib.rxbus2.RxBus;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import fu.com.parttimejob.bean.RxBusEntity;


/**
 * Description:
 * Data：2018/3/23-17:45
 * Author: fushuaige
 */
public class UMShare implements UMShareListener {
    Context context;
    private static final String TAG = "UMShare";
    public UMShare(Context contexts) {
        context = contexts;
    }

    /**
     * @param platform 平台类型
     * @descrption 分享开始的回调
     */
    @Override
    public void onStart(SHARE_MEDIA platform) {
        Log.i(TAG, "onStart: ");
    }

    /**
     * @param platform 平台类型
     * @descrption 分享成功的回调
     */
    @Override
    public void onResult(SHARE_MEDIA platform) {
        RxBusEntity rxBusEntity = new RxBusEntity();
        rxBusEntity.setMsg("1");
        RxBus.getDefault().post(rxBusEntity);
        Log.i(TAG, "onResult: ");
        Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
    }

    /**
     * @param platform 平台类型
     * @param t        错误原因
     * @descrption 分享失败的回调
     */
    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        Log.d("shareloss",t.getMessage());
        Toast.makeText(context, "分享失败", Toast.LENGTH_LONG).show();
    }

    /**
     * @param platform 平台类型
     * @descrption 分享取消的回调
     */
    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Log.i(TAG, "onCancel: ");
    }
}
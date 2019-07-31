package fu.com.parttimejob.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import fu.com.parttimejob.R;
import fu.com.parttimejob.weight.BaseUiListener;

/**
 * Created by YuanGang on 2018/3/21.
 */

public class SocietyShareUtils {

    private static final int THUMB_SIZE = 150;
    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;

    private int mcriseTargetScene = SendMessageToWX.Req.WXSceneTimeline;

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 新浪微博分享
     *
     * @param activity
     * @param context
     * @param url
     * @param title
     * @param description
     */
    public void sinaShare(Activity activity, Context context, String url, String title, String description) {
        UMShare share = new UMShare(context);
        UMWeb umWeb = new UMWeb(url);
        UMImage image = new UMImage(context, R.mipmap.ic_launcher);
        umWeb.setTitle(title);
        umWeb.setDescription(description);
        umWeb.setThumb( image);
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.SINA)//传入平台
//                .withText("hello")//分享内容
                .withMedia(umWeb)
                .setCallback(share)//回调监听器
                .share();
    }
    private Tencent mTencent= null ;//qq主操作对象
    /**
     * QQ分享
     *
     * @param activity
     * @param context
     * @param url
     * @param title
     * @param description
     */
    public void qqShare(Activity activity, Context context, String url, String title, String description) {
        final Bundle params = new Bundle();
        mTencent = Tencent.createInstance("1109483400", context);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  description);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,SPUtil.getString(activity, "headImg", "http://konkonyu.oss-cn-beijing.aliyuncs.com/moren.jpg"));
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
        mTencent.shareToQQ(activity, params, new BaseUiListener());
//        UMShare share = new UMShare(context);
//        UMWeb umWeb = new UMWeb(url);
//        UMImage image = new UMImage(context, R.mipmap.ic_launcher);
//        umWeb.setTitle(title);
//        umWeb.setDescription(description);
//        umWeb.setThumb( image);
//        new ShareAction(activity)
//                .setPlatform(SHARE_MEDIA.QQ)//传入平台
////                .withText("hello")//分享内容
//                .withMedia(umWeb)
//                .setCallback(share)//回调监听器
//                .share();
    }

    /**
     * 空间分享
     *
     * @param activity
     * @param context
     * @param url
     * @param title
     * @param description
     */
    public void roomShare(Activity activity, Context context, String url, String title, String description) {
        final Bundle params = new Bundle();
        mTencent = Tencent.createInstance("1109483400", context);
        params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QQ_APP_NAME );
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,description);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "http://konkonyu.oss-cn-beijing.aliyuncs.com/moren.jpg");
        mTencent.shareToQzone(activity, params, new BaseUiListener());
//        UMShare share = new UMShare(context);
//        UMWeb umWeb = new UMWeb(url);
//        umWeb.setTitle(title);
//        umWeb.setDescription(description);
//        new ShareAction(activity)
//                .setPlatform(SHARE_MEDIA.QZONE)//传入平台
////                .withText("hello")//分享内容
//                .withMedia(umWeb)
//                .setCallback(share)//回调监听器
//                .share();
    }

    /**
     * 微信分享
     *
     * @param context
     * @param api
     * @param url
     * @param title
     * @param description
     */
    //    api = WXAPIFactory.createWXAPI(this, AppConstant.APP_ID);
    public void friendShare(Context context, IWXAPI api, String url, String title, String description) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mTargetScene;
        api.sendReq(req);
    }

    /**
     * 朋友圈分享
     *
     * @param context
     * @param api
     * @param url
     * @param title
     * @param description
     */
    public void circleShare(Context context, IWXAPI api, String url, String title, String description) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mcriseTargetScene;
        api.sendReq(req);
    }
}

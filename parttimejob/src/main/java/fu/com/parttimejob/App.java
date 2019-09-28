package fu.com.parttimejob;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.heixiu.errand.net.OkHttpFactory;
import com.heixiu.errand.net.RetrofitFactory;
import com.luck.picture.lib.rxbus2.RxBus;
import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import fu.com.parttimejob.bean.RxBusEntity;
import fu.com.parttimejob.utils.SPContants;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by PVer on 2019/5/6.
 */

public class App extends MultiDexApplication implements RongIMClient.OnReceiveMessageListener {
    private static App application;

    {
        PlatformConfig.setQQZone(SPContants.QQ_APP_ID, SPContants.QQ_APP_SERCRET);
    }

    public static App getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        OkHttpFactory.INSTANCE.init(this);
        RetrofitFactory.INSTANCE.init();
        RongIM.init(this);
        RongIM.setOnReceiveMessageListener(this);
        MobSDK.init(this);
        UMShareAPI.get(this);
        UMConfigure.init(this, "5cf7644a3fc1950df70011cf", "chess", UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public boolean onReceived(Message message, int i) {
        RxBusEntity rxBusEntity = new RxBusEntity();
        rxBusEntity.setMsg("103");
        RxBus.getDefault().post(rxBusEntity);
        return false;
    }
}

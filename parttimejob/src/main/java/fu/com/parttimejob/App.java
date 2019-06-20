package fu.com.parttimejob;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.heixiu.errand.net.OkHttpFactory;
import com.heixiu.errand.net.RetrofitFactory;

import io.rong.imkit.RongIM;

/**
 * Created by PVer on 2019/5/6.
 */

public class App extends MultiDexApplication {
    private static App application;

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
    }
}

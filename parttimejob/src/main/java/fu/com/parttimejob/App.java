package fu.com.parttimejob;

import android.app.Application;

import com.heixiu.errand.net.OkHttpFactory;
import com.heixiu.errand.net.RetrofitFactory;

/**
 * Created by PVer on 2019/5/6.
 */

public class App extends Application {
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
    }
}

package fu.com.parttimejob.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * Created by PVer on 2019/6/15.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (isTranslucent()) {
            QMUIStatusBarHelper.translucent(this);
        }
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViewParams();
        initViewClick();
        ActivityManager.addActivity(this);
    }

    public void startActivity(Class activity, boolean isFinish) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }


    public void showToast(String message) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected abstract int getLayoutId();

    protected abstract void initViewParams();

    protected abstract void initViewClick();

    protected boolean isTranslucent() {
        return false;
    }


}

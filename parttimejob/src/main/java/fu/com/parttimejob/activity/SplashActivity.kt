package fu.com.parttimejob.activity

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.utils.AppUtils
import fu.com.parttimejob.utils.LocationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : BaseActivity() {
    var time: Int = 10
    var timer = Timer()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initViewParams() {
        var timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    time_txt.text = time--.toString()
                    if (time == -1) {
                        startActivity(ChooseProfessionActivity::class.java, true)
                    }
                }
            }
        }
        timer.schedule(timerTask, 1000, 1000)

        app_version_txt.text = AppUtils.getAppVersion(this)

        LocationUtils().getLocation(this, object : AMapLocationListener {
            override fun onLocationChanged(p0: AMapLocation?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1)//自定义的code
        }
    }

    override fun initViewClick() {
        go_experience_txt.setOnClickListener {
            startActivity(ChooseProfessionActivity::class.java, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun isTranslucent(): Boolean {
        return true
    }
}

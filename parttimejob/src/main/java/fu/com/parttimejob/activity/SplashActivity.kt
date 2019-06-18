package fu.com.parttimejob.activity

import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.utils.AppUtils
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
}

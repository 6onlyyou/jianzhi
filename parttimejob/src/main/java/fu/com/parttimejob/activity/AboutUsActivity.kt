package fu.com.parttimejob.activity

import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_about_us
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }
    }

}

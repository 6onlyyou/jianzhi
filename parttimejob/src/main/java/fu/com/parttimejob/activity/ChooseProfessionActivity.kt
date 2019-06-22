package fu.com.parttimejob.activity

import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_profession.*

class ChooseProfessionActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_choose_profession
    }

    override fun initViewParams() {

    }

    override fun initViewClick() {
        job_hunter_img.setOnClickListener {
            startActivity(LoginActivity::class.java, true)
        }

        interviewer_img.setOnClickListener {
            startActivity(LoginActivity::class.java, true)
        }
    }

    override fun isTranslucent(): Boolean {
        return true
    }
}

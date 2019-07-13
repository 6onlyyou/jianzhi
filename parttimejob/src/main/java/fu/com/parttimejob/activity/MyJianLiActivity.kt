package fu.com.parttimejob.activity

import android.content.Intent
import android.os.Bundle
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_jian_li.*

class MyJianLiActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_my_jian_li
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }
    }


}

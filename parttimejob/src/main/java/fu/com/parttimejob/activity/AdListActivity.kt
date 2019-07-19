package fu.com.parttimejob.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ad_list.*

/**
 * 广告活动
 */
class AdListActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_ad_list
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        pushgaogao.setOnClickListener {
            startActivity(Intent(this, PublishAdActivity::class.java))

        }
    }


}

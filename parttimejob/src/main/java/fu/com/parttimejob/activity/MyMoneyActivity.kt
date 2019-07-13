package fu.com.parttimejob.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_money.*

class MyMoneyActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_my_money
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        back.setOnClickListener {
        }
        recharge.setOnClickListener {
            startActivity(Intent(this, RechargeActivity::class.java))
        }
    }


}

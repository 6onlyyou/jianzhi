package fu.com.parttimejob.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_my_money.*

class MyMoneyActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_my_money
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        myMoneyTv.setText(SPUtil.getInt(this@MyMoneyActivity, "totalCount", 0))
        back.setOnClickListener {
            finish()
        }
        recharge.setOnClickListener {
            startActivity(Intent(this, RechargeActivity::class.java))
        }
    }


}

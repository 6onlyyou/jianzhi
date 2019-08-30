package fu.com.parttimejob.activity

import android.content.Intent
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_my_money.*

class MyMoneyActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_my_money
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        myMoneyTv.setText(SPUtil.getInt(this@MyMoneyActivity, "totalCount", 0).toString())
        back.setOnClickListener {
            finish()
        }
        recharge.setOnClickListener {
            startActivity(Intent(this, RechargeActivity::class.java))
        }
        duihshop.setOnClickListener {
            startActivity(Intent(this, ExchangeShopActivity::class.java))
        }

        bill.setOnClickListener { startActivity(Intent(this, BillActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getUserInfo(SPUtil.getString(this@MyMoneyActivity, "thirdAccount", ""), SPUtil.getInt(this@MyMoneyActivity, "Profession", 1), SPUtil.getString(this@MyMoneyActivity, "longitude", ""), SPUtil.getString(this@MyMoneyActivity, "latitude", ""), SPUtil.getString(this@MyMoneyActivity, "city", ""), SPUtil.getString(this@MyMoneyActivity, "token", ""))).subscribe({
            SPUtil.putInt(this@MyMoneyActivity, "loginType", it.loginType)
            SPUtil.putString(this@MyMoneyActivity, "registrationDate", it.registrationDate)
            SPUtil.putString(this@MyMoneyActivity, "city", it.city)
            SPUtil.putString(this@MyMoneyActivity, "longitude", it.longitude)
            SPUtil.putString(this@MyMoneyActivity, "latitude", it.latitude)
            SPUtil.putString(this@MyMoneyActivity, "inviteCode", it.inviteCode)
            SPUtil.putString(this@MyMoneyActivity, "labelName", it.labelName)
            SPUtil.putInt(this@MyMoneyActivity, "totalCount", it.totalCount)
            SPUtil.putString(this@MyMoneyActivity, "phoneNumber", it.labelName)
            SPUtil.putInt(this@MyMoneyActivity, "inviteCount", it.inviteCount)
            SPUtil.putString(this@MyMoneyActivity, "nickName", it.name)
            SPUtil.putString(this@MyMoneyActivity, "headImg", it.headImg)
            SPUtil.putString(this@MyMoneyActivity, "companyName", it.companyName)
            myMoneyTv.setText(it.totalCount.toString())

        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
    }
}

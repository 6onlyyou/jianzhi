package fu.com.parttimejob.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.ExchangeBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_shou_huo.*

class ShouHuoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_shou_huo
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        val b = intent.extras
        val obj = b!!.getParcelable<ExchangeBean>("exchangeBean")
        pushjianli.setOnClickListener {
            if (TextUtils.isEmpty(jianliname.text) || TextUtils.isEmpty(jianlisex.text)|| TextUtils.isEmpty(jianlijianjie.text)){
                ToastUtils.showLongToast(applicationContext, "请您把信息填写完整才能确定~")
            }else{
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().exchangeGoods(SPUtil.getString(this@ShouHuoActivity, "thirdAccount", ""), obj.id,jianlijianjie.text.toString(),jianlisex.text.toString(),jianliname.text.toString())).subscribe({
                    ToastUtils.showLongToast(applicationContext, "兑换成功")
                    SPUtil.putInt(this@ShouHuoActivity, "totalCount", SPUtil.getInt(this@ShouHuoActivity, "totalCount", 0)-obj.goodsPrice)
                    finish()
                }, {
                    ToastUtils.showLongToast(applicationContext, it.message.toString())
                })
            }
        }
    }

}

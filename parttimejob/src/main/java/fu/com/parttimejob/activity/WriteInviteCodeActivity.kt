package fu.com.parttimejob.activity

import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_write_invite_code.*

class WriteInviteCodeActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_write_invite_code
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }
        confirm.setOnClickListener {
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().inputInvit(SPUtil.getString(this@WriteInviteCodeActivity, "thirdAccount", ""), writeInvite.text.toString())).subscribe({
                ToastUtils.showLongToast(applicationContext, "绑定成功")

            }, {
                ToastUtils.showLongToast(applicationContext, it.message.toString())
            })
        }

    }

}

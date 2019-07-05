package fu.com.parttimejob.activity

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_change_pwd.*

class ChangePwdActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_change_pwd
    }

    override fun initViewParams() {

    }

    override fun initViewClick() {
        getPhoneCodeTv.setOnClickListener({
            if (!TextUtils.isEmpty(phoneEt.text) || phoneEt.text.length != 11) {
                getPhoneCode(phoneEt.text)
            } else {
                showToast("手机号码为空或者格式错误!")
            }
        })

        changePwdTv.setOnClickListener({
            if (judgeIsCanChange()) {
                startChangePwd()
            }
        })
        back.setOnClickListener {
            finish()
        }
    }

    private fun getPhoneCode(text: Editable?) {


    }

    private fun judgeIsCanChange(): Boolean {
        if (TextUtils.isEmpty(phoneEt.text) || TextUtils.isEmpty(smsCodeEt.text) || TextUtils.isEmpty(pwdEt.text) || TextUtils.isEmpty(pwdNextEt.text)) {
            showToast("您的信息未填写完整!")
            return false
        }
        return true
    }

    private fun startChangePwd() {
        //接口
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().modifyPassword(phoneEt.text.toString(),pwdNextEt.text.toString())).subscribe({
            showToast("修改密码成功")
            finish()
        }, {
            ToastUtils.showLongToast(applicationContext,it.message.toString())
        })
    }


    internal inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long, private val codeText: Button)
        : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            codeText.text = "获取验证码"
            codeText.isEnabled = true
            cancel()
        }

        override fun onTick(millisUntilFinished: Long) {
            codeText.text = (millisUntilFinished / 1000).toString() + "s"
            codeText.isEnabled = false
        }
    }

    override fun isTranslucent(): Boolean {
        return true
    }
}

package fu.com.parttimejob.activity

import android.graphics.Color
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.CountDownTimerUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_bindr.*

class BindPhoneActivity : BaseActivity() {
    var mCountDownTimerUtils: CountDownTimerUtils? = null
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                showToast("验证码错误：" + msg.obj)
            } else if (msg.what == 2) {
                showToast("获取验证码失败" + msg.obj)
            } else if (msg.what == 4) {
                SPUtil.putString(applicationContext, "ronyuntoken", msg.obj.toString())
            } else if (msg.what == 5) {
            } else {
                showToast(msg.obj.toString())
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bindr
    }

    override fun initViewParams() {

    }

    private var time: TimeCount? = null
    override fun initViewClick() {

        time = TimeCount(60000, 1000)
        mCountDownTimerUtils = CountDownTimerUtils(getPhoneCodeTv, 60000, 1000)
        getPhoneCodeTv.setOnClickListener({
            if (!TextUtils.isEmpty(phoneEt.text) || phoneEt.text.length != 11) {
                getPhoneCode(phoneEt.text)
            } else {
                showToast("手机号码为空或者格式错误!")
            }
        })
        back.setOnClickListener {
            finish()
        }
        registerTv.setOnClickListener({
            if (judgeIsCanRegister()) {
                submitCode("86", phoneEt.text.toString(), smsCodeEt.text.toString())
            }
        })
    }

    private fun getPhoneCode(text: Editable?) {
        sendCode("86", phoneEt.text.toString())
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    fun sendCode(country: String, phone: String) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        time!!.start();
                        // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    }
                } else {
                    var message: Message = Message()
                    message.obj = data.toString()
                    message.what = 2
                    handler.sendMessage(message);
                }
            }
        })
        // 触发操作
        SMSSDK.getVerificationCode(country, phone)
    }

    fun submitCode(country: String, phone: String, code: String) {

        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().bindPhoneNum(SPUtil.getString(this@BindPhoneActivity, "thirdAccount", ""), phoneEt.text.toString())).subscribe({
                            ToastUtils.showLongToast(applicationContext, "绑定成功")
                            SPUtil.putString(this@BindPhoneActivity,"phoneNumber",phoneEt.text.toString())
                            finish()
                        }, {
                            ToastUtils.showLongToast(applicationContext, it.message.toString())
                        })
                    }
                } else {
                    var message: Message = Message()
                    message.obj = data.toString()
                    message.what = 1
                    handler.sendMessage(message);
                }
            }
        })
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code)
    }

    private fun judgeIsCanRegister(): Boolean {
        if (TextUtils.isEmpty(phoneEt.text) || TextUtils.isEmpty(smsCodeEt.text)) {
            showToast("您的信息未填写完整!")
            return false
        }
        return true
    }

    private fun startRegister() {
        //接口


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

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterAllEventHandler()
    };

    internal inner class TimeCount(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            getPhoneCodeTv.setClickable(false)
            getPhoneCodeTv.setTextColor(Color.parseColor("#B6B6D8"))
            getPhoneCodeTv.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送")
        }

        override fun onFinish() {
            getPhoneCodeTv.setText("重新获取验证码")
            getPhoneCodeTv.setClickable(true)
        }
    }
}
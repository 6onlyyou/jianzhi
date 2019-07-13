package fu.com.parttimejob.activity

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.WXPayEntity
import fu.com.parttimejob.utils.PayResult
import fu.com.parttimejob.utils.SPContants
import kotlinx.android.synthetic.main.activity_recharge.*

/**
 * 充值提现
 */
class RechargeActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return (R.layout.activity_recharge)
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }
    }

    fun startWxPay(entity: WXPayEntity) {
        val api = WXAPIFactory.createWXAPI(this, SPContants.WX_APP_ID, false)
        api.registerApp(SPContants.WX_APP_ID)
        val payReq = PayReq()
        payReq.appId = SPContants.WX_APP_ID
        payReq.partnerId = entity.partnerid
        payReq.prepayId = entity.prepayid
        payReq.packageValue = entity.packageX
        payReq.nonceStr = entity.noncestr
        payReq.timeStamp = entity.timestamp.toString()
        payReq.sign = entity.sign
        api.sendReq(payReq)
    }

    fun startAliPay(result: String) {
        val payRunnable = Runnable {
            val alipay = PayTask(this)
            val result = alipay.payV2(result, true)
            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }
        val payThread = Thread(payRunnable)
        payThread.start()
    }

    private val SDK_PAY_FLAG = 1

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as MutableMap<String, String>?)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通3知。
                     */
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        showToast("支付成功")
                    } else {
                        showToast("支付失败")
                    }
                }
                else -> {
                }
            }
        }
    }
}

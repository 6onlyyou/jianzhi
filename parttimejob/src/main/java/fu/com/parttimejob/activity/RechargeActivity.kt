package fu.com.parttimejob.activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import com.alipay.sdk.app.PayTask
import com.heixiu.errand.net.RetrofitFactory
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.Pickers
import fu.com.parttimejob.bean.WXPayEntity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.PayResult
import fu.com.parttimejob.utils.SPContants
import fu.com.parttimejob.utils.SPUtil
import fu.com.parttimejob.view.PickerScrollView
import kotlinx.android.synthetic.main.activity_recharge.*


/**
 * 充值提现
 */
class RechargeActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return (R.layout.activity_recharge)
    }

    override fun initViewParams() {
        aliPayCheck.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    wxPayCheck.isChecked = false
                }
            }
        })
        wxPayCheck.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    aliPayCheck.isChecked = false
                }
            }
        })

        var datas = ArrayList<Pickers>()
        datas.add(Pickers("10", "1"))
        datas.add(Pickers("20", "2"))
        datas.add(Pickers("50", "3"))
        datas.add(Pickers("100", "4"))
        datas.add(Pickers("200", "5"))
        pickerscrlllview.setData(datas)
        pickerscrlllview.setSelected(0)

        pickerscrlllview.setOnSelectListener(pickerListener)

    }

    var pickerListener: PickerScrollView.onSelectListener = PickerScrollView.onSelectListener { pickers ->
        rechargeMoneyEd.text = pickers.showConetnt
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }

        recharge.setOnClickListener {
            if (aliPayCheck.isChecked) {
                getAliPayInfo()
            }
            if (wxPayCheck.isChecked) {
                getWxPayInfo()
            }
        }

        rechargeMoneyEd.setOnClickListener {
            pickView.visibility = View.VISIBLE
        }
        picker_yes.setOnClickListener {
            pickView.visibility = View.GONE
        }
        cancel.setOnClickListener {
            pickView.visibility = View.GONE
        }
    }

    private fun getAliPayInfo() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().alipay(SPUtil.getString(this@RechargeActivity, "thirdAccount", ""), Integer.valueOf(rechargeMoneyEd.text.toString())
                , "1", "2"))
                .subscribe({
                    startAliPay(it)
                }, {
                    showToast(it.message + "")
                })
    }

    fun getWxPayInfo() {

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().wxPay(SPUtil.getString(this@RechargeActivity, "thirdAccount", ""), Integer.valueOf(rechargeMoneyEd.text.toString())
                , "1", "充值" + rechargeMoneyEd.text.toString() + "金币", getInNetIp(this)))
                .subscribe({
                    startWxPay(it)
                }, {
                    showToast(it.message + "")
                })
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

    fun getInNetIp(context: Context): String {
        //获取wifi服务
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }

        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress

        return intToIp(ipAddress)
    }

    //这段是转换成点分式IP的码
    private fun intToIp(ip: Int): String {
        return (ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "." + (ip shr 24 and 0xFF)
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
                        showToast("支付失败" + payResult.memo + payResult.result)
                    }
                }
                else -> {
                }
            }
        }
    }

}

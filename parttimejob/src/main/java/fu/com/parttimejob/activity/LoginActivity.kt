package fu.com.parttimejob.activity

import android.text.TextUtils
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.utils.SPContants
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViewParams() {

    }

    override fun initViewClick() {
        forgetPwd.setOnClickListener({
            startActivity(ChangePwdActivity::class.java, false)
        })

        go_register.setOnClickListener({
            startActivity(RegisterActivity::class.java, false)
        })

        login_with_wx.setOnClickListener({
            startWxLogin()
        })

        login_with_qq.setOnClickListener({

        })

        login_with_pwd.setOnClickListener {
            if (judgeIsCanLogin()) {
                startPwdLogin()
            }
        }


        mIwxapi = WXAPIFactory.createWXAPI(this, SPContants.WX_APP_ID, true)
        mIwxapi.registerApp(SPContants.WX_APP_ID)
    }

    private fun startPwdLogin() {
        showToast("登录成功")
//        startActivity(MainActivity::class.java, true)
        startActivity(ChooseJobActivity::class.java, true)
    }



    private fun judgeIsCanLogin(): Boolean {
        if (TextUtils.isEmpty(loginPhoneEt.text) || TextUtils.isEmpty(loginPwdEt.text)) {
            showToast("您的信息未填写完整!")
            return false
        }
        return true
    }

    private fun startRegister() {
        //接口
    }


    lateinit var mIwxapi: IWXAPI

    public fun startWxLogin() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_login"
        val result = mIwxapi.sendReq(req)
    }

}

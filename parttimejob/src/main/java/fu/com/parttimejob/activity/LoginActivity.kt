package fu.com.parttimejob.activity

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.tencent.connect.UserInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.retrofitNet.ApiService
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPContants
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : BaseActivity() {
    private var mTencent: Tencent? = null //qq主操作对象
    private var loginListener: IUiListener? = null //授权登录监听器
    private var userInfoListener: IUiListener? = null //获取用户信息监听器
    private var userInfo: UserInfo? = null //qq用户信息
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViewParams() {

    }

    override fun initViewClick() {
        back.setOnClickListener {
            startActivity(ChooseProfessionActivity::class.java, false)
        }
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
            login()
        })

        login_with_pwd.setOnClickListener {
            if (judgeIsCanLogin()) {
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().login(loginPhoneEt.text.toString(),loginPwdEt.text.toString())).subscribe({
                    ToastUtils.showLongToast(applicationContext,"登入成功")
                    SPUtil.putString(this,"token",it.token.toString())
                    startPwdLogin()
                }, {
                    ToastUtils.showLongToast(applicationContext,it.message.toString())
                })

            }
        }
        mTencent = Tencent.createInstance("1109483400", this.getApplicationContext());
        mIwxapi = WXAPIFactory.createWXAPI(this, SPContants.WX_APP_ID, true)
        mIwxapi.registerApp(SPContants.WX_APP_ID)
        inView()
    }

    private fun login() {
        //如果session无效，就开始登录
        if (!mTencent!!.isSessionValid()) {
            //开始qq授权登录
            mTencent!!.login(this, "all", loginListener)
        }
    }

    fun inView() {

        loginListener = object : IUiListener {

            override fun onError(arg0: UiError) {
                // TODO Auto-generated method stub

            }

            override fun onComplete(value: Any?) {
                if (value == null) {
                    return
                }
                try {
                    val jo = value as JSONObject?
                    val ret = jo!!.getInt("ret")
                    println("json=" + jo.toString())
                    if (ret == 0) {
                        val openID = jo.getString("openid")
                        val accessToken = jo.getString("access_token")
                        val expires = jo.getString("expires_in")
                        mTencent!!.setOpenId(openID)
                        mTencent!!.setAccessToken(accessToken, expires)
                        userInfo = UserInfo(this@LoginActivity, mTencent!!.getQQToken())
                        userInfo!!.getUserInfo(userInfoListener)
                    }

                } catch (e: Exception) {
                }

            }

            override fun onCancel() {

            }
        }

        userInfoListener = object : IUiListener {

            override fun onError(arg0: UiError) {

            }

            override fun onComplete(arg0: Any?) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    return
                }
                try {
                    val jo = arg0 as JSONObject?
                    println("json=" + jo.toString())
                    val ret = jo!!.getInt("ret")
                    val nickName = jo.getString("nickname")
                    val gender = jo.getString("gender")
                    val figureurl = jo.getString("figureurl").toString()
                    val city = jo.getString("city")
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().register(mTencent!!.openId,SPUtil.getInt(this@LoginActivity,"Profession",1),nickName,figureurl,gender,1)).subscribe({
                        ToastUtils.showLongToast(applicationContext,"登入成功")
                        startActivity(MainActivity::class.java, true)

                    }, {
                        ToastUtils.showLongToast(applicationContext,it.message.toString())
                    })
                } catch (e: Exception) {

                }


            }

            override fun onCancel() {
                // TODO Auto-generated method stub

            }
        }
    }

    private fun startPwdLogin() {
        showToast("登录成功")
        startActivity(MainActivity::class.java, true)
        finish()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);

    }

    lateinit var mIwxapi: IWXAPI

    public fun startWxLogin() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_login"
        val result = mIwxapi.sendReq(req)
    }

    override fun isTranslucent(): Boolean {
        return true
    }
}

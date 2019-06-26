package fu.com.parttimejob.activity

import android.content.Intent
import android.text.TextUtils
import com.tencent.connect.UserInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.utils.SPContants
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
        //初始化qq主操作对象
        mTencent = Tencent.createInstance("1109483400",  this.getApplicationContext());
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
                startPwdLogin()
            }
        }


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

            /**
             * 返回json数据样例
             *
             * {"ret":0,"pay_token":"D3D678728DC580FBCDE15722B72E7365",
             * "pf":"desktop_m_qq-10000144-android-2002-",
             * "query_authority_cost":448,
             * "authority_cost":-136792089,
             * "openid":"015A22DED93BD15E0E6B0DDB3E59DE2D",
             * "expires_in":7776000,
             * "pfkey":"6068ea1c4a716d4141bca0ddb3df1bb9",
             * "msg":"",
             * "access_token":"A2455F491478233529D0106D2CE6EB45",
             * "login_cost":499}
             */
            override fun onComplete(value: Any?) {
                // TODO Auto-generated method stub

                println("有数据返回..")
                if (value == null) {
                    return
                }

                try {
                    val jo = value as JSONObject?

                    val ret = jo!!.getInt("ret")

                    println("json=" + jo.toString())

                    if (ret == 0) {

//                        Toast.makeText(LoginActivity::class.java, "登录成功",
//                                Toast.LENGTH_LONG).show()

                        val openID = jo.getString("openid")
                        val accessToken = jo.getString("access_token")
                        val expires = jo.getString("expires_in")
                        mTencent!!.setOpenId(openID)
                        mTencent!!.setAccessToken(accessToken, expires)
                        userInfo = UserInfo(this@LoginActivity, mTencent!!.getQQToken())
                        userInfo!!.getUserInfo(userInfoListener)
                    }

                } catch (e: Exception) {
                    // TODO: handle exception
                }

            }

            override fun onCancel() {
                // TODO Auto-generated method stub

            }
        }

        userInfoListener = object : IUiListener {

            override fun onError(arg0: UiError) {
                // TODO Auto-generated method stub

            }

            /**
             * 返回用户信息样例
             *
             * {"is_yellow_year_vip":"0","ret":0,
             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
             * "city":"黄冈","
             * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
             * "vip":"0","level":"0",
             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "province":"湖北",
             * "is_yellow_vip":"0","gender":"男",
             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
             */
            override fun onComplete(arg0: Any?) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    return
                }
                try {
                    val jo = arg0 as JSONObject?
                    val ret = jo!!.getInt("ret")
                    println("json=" + jo.toString())
                    val nickName = jo.getString("nickname")
                    val gender = jo.getString("gender")
                    val figureurl = jo.getString("figureurl").toString()
                    val city = jo.getString("city")

                } catch (e: Exception) {
                    // TODO: handle exception
                }


            }

            override fun onCancel() {
                // TODO Auto-generated method stub

            }
        }
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

package fu.com.parttimejob.activity

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.luck.picture.lib.rxbus2.RxBus
import com.tencent.connect.UserInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.GetTokenEntity
import fu.com.parttimejob.bean.WXInfoEntity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPContants
import fu.com.parttimejob.utils.SPUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
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
        RxBus.getDefault().toObservable(SendAuth.Resp::class.java).subscribe({
            getWxUserInfo(it)
        }, {
            showToast("登入失败请重新登入")
        })

    }

    private fun getWxUserInfo(resp: SendAuth.Resp) {
        RetrofitFactory.getRetrofit().getToken(SPContants.WX_APP_ID, SPContants.WX_APP_SERCRET, resp.code, "authorization_code")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<GetTokenEntity> { getTokenEntity ->
                    RetrofitFactory.getRetrofit().getWXInfo(getTokenEntity.getmAccessToken().toString(), getTokenEntity.getmOpenid())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(Consumer<WXInfoEntity> { wxInfoEntity ->
                                RxBus.getDefault().post(wxInfoEntity)
                                loginWithWx(wxInfoEntity)
                            }, Consumer<Throwable> {
//                                showToast("获取登陆信息失败")
                            })
                }, Consumer<Throwable> {
//                    showToast("获取登陆信息失败")
                })
    }

    private fun loginWithWx(wxInfoEntity: WXInfoEntity) {
        var sex = 0
        if( wxInfoEntity.getmSex().equals("男")){
            sex = 1
        }else{
            sex = 2
        }
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().register(wxInfoEntity!!.getmOpenid(), SPUtil.getInt(this@LoginActivity, "Profession", 1),
                wxInfoEntity.getmNickname(), wxInfoEntity.getmHeadimgurl(),sex , 1, "wx23456")).subscribe({
            if (it.register) {
                ToastUtils.showLongToast(applicationContext, "登入成功")
                SPUtil.putString(this@LoginActivity, "token", it.token)
                SPUtil.putString(applicationContext,"thirdAccount",wxInfoEntity!!.getmOpenid())
                startActivity(MainActivity::class.java, true)
            } else {
                ToastUtils.showLongToast(applicationContext, "登入成功")
                SPUtil.putString(this@LoginActivity, "token", it.token)
                SPUtil.putString(applicationContext,"thirdAccount",wxInfoEntity!!.getmOpenid())
                if(SPUtil.getInt(this@LoginActivity, "Profession", 1)==1){
                    startActivity(ChooseJobActivity::class.java, true)
                }else{
                    startActivity(CreateJobCardActivity::class.java, true)
                }
            }
            finish()

        }, {
            showToast(it.message)
//            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
    }

    override fun initViewClick() {
        back.setOnClickListener {
            startActivity(ChooseProfessionActivity::class.java, true)
        }
        forgetPwd.setOnClickListener {
            startActivity(ChangePwdActivity::class.java, false)
        }

        go_register.setOnClickListener {
            startActivity(RegisterActivity::class.java, false)
        }

        login_with_wx.setOnClickListener {
            if(radioButton2.isChecked){
                startWxLogin()
            }else{
                ToastUtils.showShortToast(this,"请先同意隐私协议")
            }
        }
        radyhxieyi.setOnClickListener {
            WebActivity.startSelf(this, "隐私协议", "http://www.jjqhkj.com/appservice/user_agreement.html")
        }
        login_with_qq.setOnClickListener {
            if(radioButton2.isChecked){
                login()
            }else{
                ToastUtils.showShortToast(this,"请先同意隐私协议")
            }

        }

        login_with_pwd.setOnClickListener {
            if(radioButton2.isChecked){
                if (judgeIsCanLogin()) {

                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().login(loginPhoneEt.text.toString(), loginPwdEt.text.toString(),SPUtil.getInt(applicationContext, "Profession", 1))).subscribe({
                        if (SPUtil.getBoolean(applicationContext,"",true)) {
                            ToastUtils.showLongToast(applicationContext, "登入成功")
                            SPUtil.putString(applicationContext,"thirdAccount",loginPhoneEt.text.toString())
                            SPUtil.putString(this, "token", it.token.toString())
                            startPwdLogin()
                        } else {
                            SPUtil.putString(applicationContext,"thirdAccount",loginPhoneEt.text.toString())
                            ToastUtils.showLongToast(applicationContext, "注册成功")
                            if(SPUtil.getInt(applicationContext, "Profession", 1)==1){
                                startActivity(ChooseJobActivity::class.java, true)
                            }else{
                                startActivity(CreateJobCardActivity::class.java, true)

                            }
                            finish()
                        }

                    }, {
                        showToast(it.message)
                    })

                }
            }else{
                ToastUtils.showShortToast(this,"请先同意隐私协议")
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
                    var sex = 0
                    if( gender.equals("男")){
                        sex = 1
                    }else{
                        sex = 2
                    }
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().register(mTencent!!.openId, SPUtil.getInt(this@LoginActivity, "Profession", 1), nickName, figureurl, sex, 1, "qq123456")).subscribe({
                        if (it.register) {
                            SPUtil.putString(applicationContext,"thirdAccount",mTencent!!.openId)
                            ToastUtils.showLongToast(applicationContext, "登入成功")
                            SPUtil.putString(this@LoginActivity, "token", it.token)
                            startActivity(MainActivity::class.java, true)
                        } else {
                            SPUtil.putString(applicationContext,"thirdAccount",mTencent!!.openId)
                            ToastUtils.showLongToast(applicationContext, "登入成功")
                            SPUtil.putString(this@LoginActivity, "token", it.token)
                            if(SPUtil.getInt(this@LoginActivity, "Profession", 1)==1){
                                startActivity(ChooseJobActivity::class.java, true)
                            }else{
                                startActivity(CreateJobCardActivity::class.java, true)
                            }
                        }
                        finish()
                    }, {
                        ToastUtils.showLongToast(applicationContext, it.message.toString())
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
//        startActivity(ChooseJobActivity::class.java, true)
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

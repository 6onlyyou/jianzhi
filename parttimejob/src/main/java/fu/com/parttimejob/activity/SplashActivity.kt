package fu.com.parttimejob.activity

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.AppUtils
import fu.com.parttimejob.utils.LocationUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import android.support.v4.content.ContextCompat.startActivity
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.dialog.ProtocolDialog
import android.support.v4.content.ContextCompat.getSystemService
import fu.com.parttimejob.dialog.JurisdictionDialog


class SplashActivity : BaseActivity() {
    var time: Int = 3
    var timer = Timer()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initViewParams() {
        if(!SPUtil.getBoolean(this,"firstplat",false)){
            JurisdictionDialog(this, R.style.dialog, "请务必详细阅读，充分理解《服务协议和隐私政策》各条款；为了向提供更好的服务，会需要您授权地理位置和相机权限。你你可以在个人页查看和变更协议。" +
                    "你可阅读《服务协议和隐私政策》了解详细信息。如你同意，请点击同意开始接受我们的服务", object : JurisdictionDialog.OnCloseListener {
                override 
                fun onClick(dialog: Dialog, confirm: Boolean) {
                    if (confirm) {
                        SPUtil.putBoolean(this@SplashActivity,"firstplat",true);
                        var timerTask: TimerTask = object : TimerTask() {
                            override fun run() {
                                runOnUiThread {
                                    time_txt.text = time--.toString()
                                    if (time == -1) {
                                        if (SPUtil.getString(this@SplashActivity, "thirdAccount", "").equals("")) {
                                            startActivity(ChooseProfessionActivity::class.java, true)
                                        } else {
                                            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getUserInfo(SPUtil.getString(this@SplashActivity, "thirdAccount", ""), SPUtil.getInt(this@SplashActivity, "Profession", 1), SPUtil.getString(this@SplashActivity, "longitude", ""), SPUtil.getString(this@SplashActivity, "latitude", ""), SPUtil.getString(this@SplashActivity, "city", ""), SPUtil.getString(this@SplashActivity, "token", ""))).subscribe({
                                                SPUtil.putInt(this@SplashActivity, "loginType", it.loginType)
                                                SPUtil.putString(this@SplashActivity, "registrationDate", it.registrationDate)
                                                SPUtil.putString(this@SplashActivity, "city", it.city)
                                                SPUtil.putInt(this@SplashActivity, "userid", it.id)
                                                SPUtil.putString(this@SplashActivity, "longitude", it.longitude)
                                                SPUtil.putString(this@SplashActivity, "latitude", it.latitude)
                                                SPUtil.putString(this@SplashActivity, "inviteCode", it.inviteCode)
                                                SPUtil.putString(this@SplashActivity, "labelName", it.labelName)
                                                SPUtil.putString(this@SplashActivity, "phoneNumber", it.phoneNumber)
                                                SPUtil.putInt(this@SplashActivity, "totalCount", it.totalCount)
                                                SPUtil.putInt(this@SplashActivity, "inviteCount", it.inviteCount)
                                                SPUtil.putString(this@SplashActivity, "nickName", it.name)
                                                SPUtil.putString(this@SplashActivity, "headImg", it.headImg)
                                                SPUtil.putString(this@SplashActivity, "companyName", it.companyName)
                                                SPUtil.putString(this@SplashActivity, "cardHeadImg", it.cardHeadImg)
                                                SPUtil.putInt(this@SplashActivity, "vipLevel", it.vipLevel)
                                                SPUtil.putString(this@SplashActivity, "jianliname", it.nickName)
                                                if (SPUtil.getInt(this@SplashActivity, "Profession", 1) == 2) {
                                                    if (!SPUtil.getString(this@SplashActivity, "companyName","").equals("")) {
                                                        startActivity(MainActivity::class.java, true)
                                                    } else {
                                                        startActivity(CreateJobCardActivity::class.java, true)
                                                    }
                                                } else {
                                                    if (!SPUtil.getString(this@SplashActivity, "labelName","").equals("")) {

                                                        startActivity(MainActivity::class.java, true)
                                                    } else {
                                                        val intent = Intent(this@SplashActivity, ChooseJobActivity::class.java)
                                                        intent.putExtra("starpage", "")
                                                        startActivity(intent)
                                                        finish()
                                                    }
                                                }
                                            }, {
                                                ToastUtils.showLongToast(applicationContext, it.message.toString())
                                            })
                                        }
                                    }else if (time==-3){
                                        ToastUtils.showLongToast(applicationContext, "网络中断请连接网络重新打开")
                                    }
                                }
                            }
                        }
                        timer.schedule(timerTask, 1000, 1000)
                    }else{
                        finish()
                    }
                    dialog.dismiss()
                }
            }).show()
        }else{
            var timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        time_txt.text = time--.toString()
                        if (time == -1) {
                            if (SPUtil.getString(this@SplashActivity, "thirdAccount", "").equals("")) {
                                startActivity(ChooseProfessionActivity::class.java, true)
                            } else {
                                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getUserInfo(SPUtil.getString(this@SplashActivity, "thirdAccount", ""), SPUtil.getInt(this@SplashActivity, "Profession", 1), SPUtil.getString(this@SplashActivity, "longitude", ""), SPUtil.getString(this@SplashActivity, "latitude", ""), SPUtil.getString(this@SplashActivity, "city", ""), SPUtil.getString(this@SplashActivity, "token", ""))).subscribe({
                                    SPUtil.putInt(this@SplashActivity, "loginType", it.loginType)
                                    SPUtil.putString(this@SplashActivity, "registrationDate", it.registrationDate)
                                    SPUtil.putString(this@SplashActivity, "city", it.city)
                                    SPUtil.putInt(this@SplashActivity, "userid", it.id)
                                    SPUtil.putString(this@SplashActivity, "longitude", it.longitude)
                                    SPUtil.putString(this@SplashActivity, "latitude", it.latitude)
                                    SPUtil.putString(this@SplashActivity, "inviteCode", it.inviteCode)
                                    SPUtil.putString(this@SplashActivity, "labelName", it.labelName)
                                    SPUtil.putString(this@SplashActivity, "phoneNumber", it.phoneNumber)
                                    SPUtil.putInt(this@SplashActivity, "totalCount", it.totalCount)
                                    SPUtil.putInt(this@SplashActivity, "inviteCount", it.inviteCount)
                                    SPUtil.putString(this@SplashActivity, "nickName", it.name)
                                    SPUtil.putString(this@SplashActivity, "headImg", it.headImg)
                                    SPUtil.putString(this@SplashActivity, "companyName", it.companyName)
                                    SPUtil.putString(this@SplashActivity, "cardHeadImg", it.cardHeadImg)
                                    SPUtil.putInt(this@SplashActivity, "vipLevel", it.vipLevel)
                                    SPUtil.putString(this@SplashActivity, "jianliname", it.nickName)
                                    if (SPUtil.getInt(this@SplashActivity, "Profession", 1) == 2) {
                                        if (!SPUtil.getString(this@SplashActivity, "companyName","").equals("")) {
                                            startActivity(MainActivity::class.java, true)
                                        } else {
                                            startActivity(CreateJobCardActivity::class.java, true)
                                        }
                                    } else {
                                        if (!SPUtil.getString(this@SplashActivity, "labelName","").equals("")) {

                                            startActivity(MainActivity::class.java, true)
                                        } else {
                                            val intent = Intent(this@SplashActivity, ChooseJobActivity::class.java)
                                            intent.putExtra("starpage", "")
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                                }, {
                                    ToastUtils.showLongToast(applicationContext, it.message.toString())
                                })
                            }
                        }else if (time==-3){
                            ToastUtils.showLongToast(applicationContext, "网络中断请连接网络重新打开")
                        }
                    }
                }
            }
            timer.schedule(timerTask, 1000, 1000)
        }



        app_version_txt.text = AppUtils.getAppVersion(this)
        LocationUtils().getLocation(this, object : AMapLocationListener {
            override fun onLocationChanged(amapLocation: AMapLocation?) {
                SPUtil.putString(this@SplashActivity, "longitude", amapLocation!!.getLongitude().toString())
                SPUtil.putString(this@SplashActivity, "latitude", amapLocation!!.getLatitude().toString())
                SPUtil.putString(this@SplashActivity, "city", amapLocation.city)
            }
        })

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            ), 1)//自定义的code
        }
    }

    override fun initViewClick() {
        go_experience_txt.setOnClickListener {
            if (SPUtil.getString(this@SplashActivity, "thirdAccount", "").equals("")) {
                startActivity(ChooseProfessionActivity::class.java, true)
            } else {
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getUserInfo(SPUtil.getString(this@SplashActivity, "thirdAccount", ""), SPUtil.getInt(this@SplashActivity, "Profession", 1), SPUtil.getString(this@SplashActivity, "longitude", ""), SPUtil.getString(this@SplashActivity, "latitude", ""), SPUtil.getString(this@SplashActivity, "city", ""), SPUtil.getString(this@SplashActivity, "token", ""))).subscribe({
                    SPUtil.putInt(this@SplashActivity, "loginType", it.loginType)
                    SPUtil.putString(this@SplashActivity, "registrationDate", it.registrationDate)
                    SPUtil.putString(this@SplashActivity, "city", it.city)
                    SPUtil.putInt(this@SplashActivity, "userid", it.id)
                    SPUtil.putString(this@SplashActivity, "inviteCode", it.inviteCode)
                    SPUtil.putString(this@SplashActivity, "labelName", it.labelName)
                    SPUtil.putInt(this@SplashActivity, "totalCount", it.totalCount)
                    SPUtil.putString(this@SplashActivity, "phoneNumber", it.phoneNumber)
                    SPUtil.putInt(this@SplashActivity, "inviteCount", it.inviteCount)
                    SPUtil.putInt(this@SplashActivity, "vipLevel", it.vipLevel)

                    SPUtil.putString(this@SplashActivity, "nickName", it.name)
                    SPUtil.putString(this@SplashActivity, "headImg", it.headImg)
                    SPUtil.putString(this@SplashActivity, "companyName", it.companyName)
                    SPUtil.putString(this@SplashActivity, "cardHeadImg", it.cardHeadImg)
                    SPUtil.putString(this@SplashActivity, "jianliname", it.nickName)
                    if (SPUtil.getInt(this@SplashActivity, "Profession", 1) == 2) {
                        if (!SPUtil.getString(this@SplashActivity, "companyName","").equals("")) {
                            startActivity(MainActivity::class.java, true)
                        } else {
                            startActivity(CreateJobCardActivity::class.java, true)

                        }
                    } else {
                        if (!SPUtil.getString(this@SplashActivity, "labelName","").equals("")) {

                            startActivity(MainActivity::class.java, true)
                        } else {
                            val intent = Intent(this, ChooseJobActivity::class.java)
                            intent.putExtra("starpage", "")
                            startActivity(intent)
                            finish()
                        }
                    }
                }, {
                    ToastUtils.showLongToast(applicationContext, it.message.toString())
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun isTranslucent(): Boolean {
        return true
    }
}

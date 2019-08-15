package fu.com.parttimejob.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.luck.picture.lib.rxbus2.RxBus
import com.tencent.connect.common.Constants
import com.tencent.tauth.Tencent
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.RecruitInfoBean
import fu.com.parttimejob.bean.RxBusEntity
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.dialog.RadDialog
import fu.com.parttimejob.dialog.ShareTypeFragment
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.MapUtils
import fu.com.parttimejob.utils.SPUtil
import fu.com.parttimejob.weight.BaseUiListener
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_job_info.*


class JobInfoActivity : BaseActivity() {
    private var subscribe: Disposable? = null
    private var shareTypeFragment: ShareTypeFragment? = null
    var dialogPro: ProgressDialog? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_job_info
    }

    var aMap: AMap? = null

    override fun onDestroy() {
        super.onDestroy()
        subscribe!!.dispose()
        jobLocationMap.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        jobLocationMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        jobLocationMap.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobLocationMap.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = jobLocationMap.map

        }
        jobLocationMap.requestDisallowInterceptTouchEvent(true)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        jobLocationMap.onSaveInstanceState(outState)
    }

    override fun initViewParams() {
        dialogPro = ProgressDialog(this)
        dialogPro!!.setCanceledOnTouchOutside(true)
        dialogPro!!.setMessage("小二加载中，大人请稍后~")
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addNumberOfRecruitView(SPUtil.getString(this@JobInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
        subscribe = RxBus.getDefault().toObservable(RxBusEntity::class.java).subscribe(object : Consumer<RxBusEntity> {
            @Throws(Exception::class)
            override fun accept(catchDollUserInfoBean: RxBusEntity) {
                if (catchDollUserInfoBean.msg.equals("101")) {
                    if(SPUtil.getString(this@JobInfoActivity, "thirdAccount", "").equals(recruitInfoBean!!.thirdAccount)){

                    }else{
                        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addNumberOfRecruitForwarding(SPUtil.getString(this@JobInfoActivity, "thirdAccount", ""), recruitInfoBean!!.id)).subscribe({
                            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().receiveOfRecruitmentVirtual(SPUtil.getString(this@JobInfoActivity, "thirdAccount", ""), recruitInfoBean!!.id)).subscribe({
                                RadDialog(this@JobInfoActivity, R.style.dialog, "恭喜获得" + recruitInfoBean!!.getNumberOfVirtualCoins() / recruitInfoBean!!.getRedEnvelopeNumber() + "金币", RadDialog.OnCloseListener { dialog, confirm ->
                                    ToastUtils.showLongToast(applicationContext, "已经存入您的钱包")
                                    dialog.dismiss()
                                })
                                        .setTitle("").show()
                            }, {
                                ToastUtils.showLongToast(applicationContext, it.message.toString())
                            })
                        }, {
                            ToastUtils.showLongToast(applicationContext, it.message.toString())
                        })
                    }


                }
            }
        }, object : Consumer<Throwable> {
            @Throws(Exception::class)
            override fun accept(throwable: Throwable) {
            }
        })
        shareTypeFragment = ShareTypeFragment()
    }

    var recruitInfoBean: RecruitInfoBean? = null
    var jinbi = 0
    var latLng: LatLng? = null
    override fun initViewClick() {
        recruitInfoBean = RecruitInfoBean()
        getDate()
        back.setOnClickListener {
            finish()
        }
//        job_jbi.setOnClickListener {
//            shareTypeFragment!!.show(getFragmentManager(), "11", "sss")
//        }
    }
fun getDate(){
    dialogPro!!.show()
    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().singleRecruitmentDetail(SPUtil.getString(this, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
        dialogPro!!.dismiss()
        recruitInfoBean = it
        if (SPUtil.getString(this, "thirdAccount", "").equals(it.thirdAccount)) {
            if (it.state == 1||it.state == 2) {
                ji_gouton.text = "关闭招聘"
            } else {
                ji_gouton.text = "开启招聘"
            }

        } else {
            ji_gouton.text = "立即沟通"
        }
        if(it.latitude==null){
            jobLocationMap.visibility = View.GONE
        }else{
            latLng  = LatLng(java.lang.Double.valueOf(it.latitude), java.lang.Double.valueOf(it.longitude))
            val marker = aMap?.addMarker(MarkerOptions().position(latLng).title("").snippet(it.contactAddress))
            val LUJIAZUI = CameraPosition.Builder()
                    .target(latLng).zoom(18f).bearing(0f).tilt(30f).build()
            val aOptions =  AMapOptions()
            aOptions.zoomGesturesEnabled(false)// 禁止通过手势缩放地图
            aOptions.scrollGesturesEnabled(false)// 禁止通过手势移动地图
            aOptions.tiltGesturesEnabled(false)// 禁止通过手势倾斜地图
            aOptions.camera(LUJIAZUI)
            val mCameraUpdate = CameraUpdateFactory.newCameraPosition(CameraPosition(latLng, 17f, 30f, 0f))
            aMap?.moveCamera(mCameraUpdate)
        }
//            it.latitude= 30.28582.toString()
//            it.longitude= 119.992592.toString()
        jinbi = it.unclaimedVirtualCoins
        label_job.text = it.label
        jobSalaryTv.text = it.salaryAndWelfare
        jobLocation.text = it.city
        content_job.text = "工作内容：" + it.workContent
        location_job.text = "工作地点：" + it.contactAddress
        phones.text = "手机号码："+it.phoneNumber
        time_job.text = "工作时间："+ it.workTime
        num_job.text = "招聘人数：" + it.recruitingNumbers
        job_jbi.text = "分享群领取" + it.numberOfVirtualCoins / it.redEnvelopeNumber + "金币"
        coid_job.text = "分享成功后可获得" + it.numberOfVirtualCoins / it.redEnvelopeNumber + "金币奖励"
        ji_gouton.setOnClickListener {
            if (SPUtil.getString(this, "thirdAccount", "").equals("")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                ToastUtils.showShortToast(this, "需要先登入才可以使用其他功能")
            } else {
                when (ji_gouton.text.toString()) {
                    "关闭招聘" -> clossD()
                    "开启招聘" -> openD()
                    "立即沟通" -> tallD()

                }
            }
        }
        location_job.setOnClickListener {
            //                MapUtils.goToBaiduMap(this@JobInfoActivity, latLng.latitude.toString(), latLng.longitude.toString(),"浙江省杭州市余杭区东莲街")
            MapUtils.goToGaodeMap(this@JobInfoActivity,latLng,recruitInfoBean?.contactAddress)
        }
        aMap?.setOnMapClickListener {
            MapUtils.goToGaodeMap(this@JobInfoActivity, latLng, recruitInfoBean?.contactAddress)
        }
    }, {
        if(dialogPro!!.isShowing){
            dialogPro!!.dismiss()
        }

        ToastUtils.showLongToast(this, it.message.toString())
    })
    back.setOnClickListener {
        finish()
    }
    job_jbi.setOnClickListener {
        shareTypeFragment!!.show(getFragmentManager(), "1",recruitInfoBean!!.label,recruitInfoBean!!.city,recruitInfoBean!!.salaryAndWelfare,recruitInfoBean!!.workContent,recruitInfoBean!!.phoneNumber,SPUtil.getString(this@JobInfoActivity, "inviteCode", ""),SPUtil.getString(this@JobInfoActivity, "longitude", ""),SPUtil.getString(this@JobInfoActivity, "latitude", ""),"https://www.pgyer.com/Tbl7",recruitInfoBean!!.recruitingNumbers.toString(),recruitInfoBean!!.workTime,recruitInfoBean!!.contactAddress)
    }
}
    fun clossD() {
        HintDialog(this, R.style.dialog, "关闭招聘返还" + recruitInfoBean!!.numberOfVirtualCoins + "金币,是否继续？", object : HintDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().closeRecruitmentInfo(SPUtil.getString(this@JobInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                        getDate()
                        ToastUtils.showLongToast(this@JobInfoActivity, it)
                    }, {
                        ToastUtils.showLongToast(this@JobInfoActivity, it.message.toString())
                    })
                } else {

                }
                dialog.dismiss()
            }
        })
                .setTitle("").show()

    }

    fun openD() {
        HintDialog(this, R.style.dialog, "重新打开招聘需要" + jinbi + "金币,是否继续？", object : HintDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().openAdvertisementInfo(SPUtil.getString(this@JobInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                        ToastUtils.showLongToast(this@JobInfoActivity, it)
                        getDate()
                    }, {
                        ToastUtils.showLongToast(this@JobInfoActivity, it.message.toString())
                    })
                } else {

                }
                dialog.dismiss()
            }
        })
                .setTitle("").show()
    }

    fun tallD() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addCommunicationRecord(SPUtil.getString(this, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
            RongIM.setUserInfoProvider({
                //在这里，根据userId，使用同步的请求，去请求服务器，就可以完美做到显示用户的头像，昵称了
                if (recruitInfoBean!!.headImg == null || recruitInfoBean!!.headImg.equals("")) {
                    UserInfo(recruitInfoBean!!.thirdAccount.toString(), recruitInfoBean!!.companyName.toString() + "的" + recruitInfoBean!!.name.toString(), Uri.parse("http://konkonyu.oss-cn-beijing.aliyuncs.com/moren.jpg"))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
                } else {
                    UserInfo(recruitInfoBean!!.thirdAccount.toString(), recruitInfoBean!!.companyName.toString() + "的" + recruitInfoBean!!.name.toString(), Uri.parse(recruitInfoBean!!.headImg + ""))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
                }

            }, true)
            RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString(this, "thirdAccount", ""), SPUtil.getString(this, "nickName", ""), Uri.parse(SPUtil.getString(this, "headImg", ""))))
            RongIM.getInstance().setMessageAttachedUserInfo(true)
            RongIM.getInstance().startPrivateChat(this, recruitInfoBean!!.thirdAccount.toString(), recruitInfoBean!!.companyName + "的" + recruitInfoBean!!.name.toString())

        }, {

            ToastUtils.showLongToast(this, it.message.toString())
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (null != mTencent)
//        {
//            mTencent!!.onActivityResult(requestCode, resultCode, data)
//        }
        Tencent.onActivityResultData(requestCode, resultCode, data, BaseUiListener());

        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE ||
                    resultCode == Constants.REQUEST_QZONE_SHARE ||
                    resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, BaseUiListener());
            }
        }

    }
}

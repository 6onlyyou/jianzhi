package fu.com.parttimejob.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.amap.api.maps.model.LatLng
import com.bumptech.glide.Glide
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.luck.picture.lib.rxbus2.RxBus
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.AdvertisingInfoBean
import fu.com.parttimejob.bean.RxBusEntity
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.dialog.ShareTypeFragment
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_ad_info.*
import java.lang.Double

class AdInfoActivity : BaseActivity() {
    private var subscribe: Disposable? = null
    private var shareTypeFragment: ShareTypeFragment? = null
    var dialogPro: ProgressDialog? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_ad_info
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe!!.dispose()
    }

    var advertisingInfoBean: AdvertisingInfoBean? = null
    override fun initViewParams() {
        dialogPro = ProgressDialog(this)
        dialogPro!!.setCanceledOnTouchOutside(true)
        dialogPro!!.setMessage("小二加载中，大人请稍后~")
        subscribe = RxBus.getDefault().toObservable(RxBusEntity::class.java).subscribe(object : Consumer<RxBusEntity> {
            @Throws(Exception::class)
            override fun accept(catchDollUserInfoBean: RxBusEntity) {
                if (catchDollUserInfoBean.msg.equals("101")) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addNumberOfAdvertisingForwarding(SPUtil.getString(this@AdInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                    }, {
                        ToastUtils.showLongToast(applicationContext, it.message.toString())
                    })

                }
            }
        }, object : Consumer<Throwable> {
            @Throws(Exception::class)
            override fun accept(throwable: Throwable) {
            }
        })
        shareTypeFragment = ShareTypeFragment()
        advertisingInfoBean = AdvertisingInfoBean()
        getDate()

    }

    fun getDate() {
        dialogPro!!.show()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().singleAdDetail(SPUtil.getString(this, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
            if(dialogPro!!.isShowing){
                dialogPro!!.dismiss()
            }
            advertisingInfoBean = it
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addNumberOfAdvertisingView(SPUtil.getString(this@AdInfoActivity, "thirdAccount", ""),intent.getIntExtra("id",0))).subscribe({
                if(SPUtil.getString(this@AdInfoActivity, "thirdAccount", "").equals(advertisingInfoBean!!.thirdAccount)){

                }else{
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().receiveOfAdVirtual(SPUtil.getString(this@AdInfoActivity, "thirdAccount", ""), intent.getIntExtra("id",0))).subscribe({
                        ToastUtils.showShortToast(applicationContext, "领取成功，金币已存入钱包")
                    }, {
                        ToastUtils.showShortToast(applicationContext, it.message.toString())
                    })
                }

            }, {
                ToastUtils.showShortToast(applicationContext, it.message.toString())
            })
            if (SPUtil.getString(this, "thirdAccount", "").equals(it.thirdAccount)) {
                if (it.state == 1||it.state == 2) {
                    ji_gouton.setText("关闭广告")
                } else {
                    ji_gouton.setText("开启广告")
                }

            } else {
                ji_gouton.visibility = View.GONE
            }
            if(it.getHeadImg()==null||it.getHeadImg().equals("")){
                ava.visibility = View.GONE
            }else{
                ava.visibility = View.VISIBLE
                GlideUtil.load(this, it.getHeadImg(), ava)

            }
            ava.setOnClickListener {
                DlgForBigPhto(advertisingInfoBean!!.getHeadImg())
            }
            zhuanfa_ad.setOnClickListener {
                shareTypeFragment!!.show(getFragmentManager(), "2", advertisingInfoBean!!.companyName,advertisingInfoBean!!.publichDate,advertisingInfoBean!!.city,advertisingInfoBean!!.advertisementContent,advertisingInfoBean!!.advertisementImg,SPUtil.getString(this@AdInfoActivity, "inviteCode", ""),"https://www.pgyer.com/Tbl7",advertisingInfoBean!!.headImg)

            }
            name.setText(it.companyName)
            time.setText("发布时间：" + it.publichDate)
            location.setText("地点：" + it.address)
            ad_content.setText(it.advertisementContent)
            Glide.with(this)
                    .load( it.advertisementImg)
                    .placeholder(R.mipmap.defind)
                    .into(ad_cimg)
            ad_cimg.setOnClickListener {
                DlgForBigPhto(advertisingInfoBean!!.advertisementImg)
            }
            ji_gouton.setOnClickListener {
                if (SPUtil.getString(this, "thirdAccount", "").equals("")) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    ToastUtils.showShortToast(this, "需要先登入才可以使用其他功能")
                } else {
                    when (ji_gouton.text.toString()) {
                        "关闭广告" -> clossD()
                        "开启广告" -> openD()

                    }
                }
            }
            if (it.latitude!=null){

            var latLng :LatLng = LatLng (Double.valueOf(it.latitude), Double.valueOf(it.longitude))
            location.setOnClickListener {
                MapUtils.goToGaodeMap(this@AdInfoActivity, latLng, advertisingInfoBean?.city)
            }
            }
        }, {
            if(dialogPro!!.isShowing){
                dialogPro!!.dismiss()
            }
            ToastUtils.showLongToast(this, it.message.toString())
        })
    }

    override fun initViewClick() {

    }
    fun clossD() {
        HintDialog(this, R.style.dialog, "关闭广告返还" + advertisingInfoBean!!.unclaimedVirtualCoins + "金币,是否继续？", object : HintDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().closeAdvertisement(SPUtil.getString(this@AdInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                        ToastUtils.showLongToast(this@AdInfoActivity, it)
                        getDate()
                    }, {
                        ToastUtils.showLongToast(this@AdInfoActivity, it.message.toString())
                    })
                } else {

                }
                dialog.dismiss()
            }
        })
                .setTitle("").show()

    }

    fun openD() {
        HintDialog(this, R.style.dialog, "重新打开广告需要" + advertisingInfoBean!!.numberOfVirtualCoins + "金币,是否继续？", object : HintDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().openAdvertisement(SPUtil.getString(this@AdInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                        ToastUtils.showLongToast(this@AdInfoActivity, it)
                        getDate()
                    }, {
                        ToastUtils.showLongToast(this@AdInfoActivity, it.message.toString())
                    })
                } else {

                }
                dialog.dismiss()
            }
        })
                .setTitle("").show()
    }

    private fun DlgForBigPhto(url: String) {
        val pDialogMy = DialogShowPic(this)
        pDialogMy.SetStyle(R.style.myDialogTheme1)
        pDialogMy.InitDialog(object : DialogShowPicP {
            lateinit var dialog6LlBckgrnd: LinearLayout
            lateinit var dialog6IvPic: ImageView
            override fun SetDialogView(pDialog: Dialog, pDialogMy: DialogShowPic) {
                pDialog.setContentView(R.layout.show_pic)
                dialog6LlBckgrnd = pDialog.findViewById<View>(R.id.dialog6LlBckgrnd) as LinearLayout
                dialog6LlBckgrnd.setOnClickListener(pDialogMy)
                dialog6IvPic = pDialog.findViewById<View>(R.id.dialog6IvPic) as ImageView
//                Glide.with(dialog6IvPic.context)
//                        .load(url)
//                        .into(dialog6IvPic)
                Glide.with(this@AdInfoActivity)
                        .load(url)
                        .placeholder(R.mipmap.defind)
                        .into(dialog6IvPic)
                dialog6IvPic.setOnClickListener { pDialog.dismiss() }
            }

            override fun SetOnClickListener(v: View, pDialogMy: DialogShowPic) {
                if (v === dialog6LlBckgrnd) {
                    pDialogMy.Destroy()
                }
            }

            override fun SetOnKeyListener(keyCode: Int, event: KeyEvent) {}
        })
        pDialogMy.Show()
    }
}

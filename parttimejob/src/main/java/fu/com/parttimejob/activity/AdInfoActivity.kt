package fu.com.parttimejob.activity

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.AdvertisingInfoBean
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.GlideUtil
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_ad_info.*

class AdInfoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_ad_info
    }
    
    var advertisingInfoBean: AdvertisingInfoBean ? = null
    override fun initViewParams() {
        advertisingInfoBean = AdvertisingInfoBean()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().singleAdDetail(SPUtil.getString(this,"thirdAccount",""),intent.getIntExtra("id",0))).subscribe({
            advertisingInfoBean = it
            if (SPUtil.getString(this, "thirdAccount", "").equals(it.thirdAccount)) {
                if (it.state == 1) {
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
                Glide.with(this)
                        .load( it.getHeadImg())
                        .placeholder(R.mipmap.defind)
                        .into(ava)
            }

            name.setText(it.companyName)
            time.setText("发布时间："+it.publichDate)
            location.setText("地点："+it.city)
            ad_content.setText(it.advertisementContent)
            GlideUtil.load(this, it.advertisementImg, ad_cimg )
            ji_gouton.setOnClickListener {
                if (SPUtil.getString(this, "thirdAccount", "").equals("")) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    ToastUtils.showShortToast(this, "需要先登入才可以使用其他功能")
                } else {
                    when (ji_gouton.text.toString()) {
                        "关闭广告" -> openD()
                        "开启广告" -> clossD()

                    }
                }
            }
        }, {
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
        HintDialog(this, R.style.dialog, "重新打开广告需要" + advertisingInfoBean!!.unclaimedVirtualCoins + "金币,是否继续？", object : HintDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().openAdvertisement(SPUtil.getString(this@AdInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                        ToastUtils.showLongToast(this@AdInfoActivity, it)
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
}

package fu.com.parttimejob.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.GlideUtil
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_ad_info.*

class AdInfoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_ad_info
    }
    override fun initViewParams() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().singleAdDetail(SPUtil.getString(this,"thirdAccount",""),intent.getIntExtra("id",0))).subscribe({
            GlideUtil.load(this, it.getHeadImg(), ava )
            name.setText(it.companyName)
            time.setText("发布时间："+it.publichDate)
            location.setText("地点："+it.city)
            ad_content.setText(it.advertisementContent)
            GlideUtil.load(this, it.advertisementImg, ad_cimg )

        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })
    }

    override fun initViewClick() {
    }

}

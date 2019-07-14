package fu.com.parttimejob.activity

import android.content.Intent
import android.os.Bundle
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_my_jian_li.*

/**
 * 我的简历/求职者简历
 */
class MyJianLiActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_my_jian_li
    }

    override fun initViewParams() {
        var beViewedAccount = intent.getStringExtra("beViewedAccount")
        if(beViewedAccount ==null||beViewedAccount.equals("")){
            beViewedAccount = SPUtil.getString(this,"thirdAccount","")
        }
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getResumeInfo(SPUtil.getString(this,"thirdAccount",""),beViewedAccount)).subscribe({
            name.setText(it.name)
            sex.setText(it.sex+"  "+it.age+"岁")
            myInfo.setText(it.personalProfile)

        }, {

            ToastUtils.showLongToast(this, it.message.toString())
        })
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }
    }


}

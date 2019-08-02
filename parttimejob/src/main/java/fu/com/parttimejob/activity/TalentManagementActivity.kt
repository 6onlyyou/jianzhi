package fu.com.parttimejob.activity

import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.TalentFragmentAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.ResumeInfoBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_talent_management.*

class TalentManagementActivity : BaseActivity() {

    lateinit var adapter: TalentFragmentAdapter
    var data: List<ResumeInfoBean>? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_talent_management
    }

    override fun initViewParams() {
        adapter = TalentFragmentAdapter()
        data = ArrayList<ResumeInfoBean>()
//        adapter.data = data
        viewPager.adapter = adapter
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().searchSameCity(SPUtil.getString(this, "thirdAccount", "111"), SPUtil.getString(this, "city", ""))).subscribe({
            data = it
            adapter.data = it
            adapter.notifyDataSetChanged()
        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
        viewPager.pageMargin = 45
    }

    override fun initViewClick() {
        back.setOnClickListener { finish() }
        communicationNow.setOnClickListener {
            if (data!!.size < 1) {
                ToastUtils.showLongToast(applicationContext, "请选择沟通对象")
            } else {
                ToastUtils.showLongToast(applicationContext, data!![viewPager.adapter!!.getItemPosition(this)].thirdAccount.toString())

            }

        }
    }


}

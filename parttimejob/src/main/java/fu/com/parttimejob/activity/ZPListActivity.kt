package fu.com.parttimejob.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.AdListAdapter
import fu.com.parttimejob.adapter.ZpListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_zp_list.*

/**
 * 招聘列表
 */
class ZPListActivity : BaseActivity() {
    var adListAdapter = ZpListAdapter()
    override fun getLayoutId(): Int {
        return R.layout.activity_zp_list
    }

    override fun initViewParams() {
        adList.layoutManager = LinearLayoutManager(this)
        adList.adapter = adListAdapter
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }
        pushgaogao.setOnClickListener {

            startActivity(Intent(this, PublishAdActivity::class.java))

        }
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().myRecruitmentList(SPUtil.getString(this, "thirdAccount", ""))).subscribe({
            adListAdapter.clear();
            adListAdapter.notifyDataSetChanged() ;
            adListAdapter.addAll(it as List<BaseRecyclerModel>?)
            adListAdapter.notifyDataSetChanged()
        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })
    }
}
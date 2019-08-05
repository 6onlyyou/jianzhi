package fu.com.parttimejob.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.AdListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.base.baseadapter.OnItemClickListener
import fu.com.parttimejob.bean.JobInfoBean
import fu.com.parttimejob.bean.MAdvertisingBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_ad_list.*

/**
 * 广告活动
 */
class AdListActivity : BaseActivity() {
    var adListAdapter = AdListAdapter()
    override fun getLayoutId(): Int {
        return R.layout.activity_ad_list
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
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().myAdvertisingCampaignList(SPUtil.getString(this,"thirdAccount",""))).subscribe({
            if (it.size==0){
                emptyView.visibility= View.VISIBLE
            }else{
                emptyView.visibility= View.GONE
                adListAdapter.data.clear()
                adListAdapter.addAll(it as List<BaseRecyclerModel>?)
                adListAdapter.notifyDataSetChanged()
            }
        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })

        adListAdapter.setOnItemClickListener { view, t, position ->
            val intent = Intent(view.context, AdInfoActivity::class.java)
            intent.putExtra("id", (adListAdapter.data[position] as MAdvertisingBean).id)
            startActivity(intent)
        }
    }
}

package fu.com.parttimejob.activity

import android.support.v7.widget.LinearLayoutManager
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.DuiHuanListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_my_dui_huan.*

class MyDuiHuanActivity : BaseActivity() {
    var adapter = DuiHuanListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.activity_my_dui_huan
    }

    override fun initViewParams() {
        duihuanList.layoutManager = LinearLayoutManager(this)
        duihuanList.adapter = adapter

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().changeList(SPUtil.getString(this,"thirdAccount",""))).subscribe({
            adapter.addAll(it as List<BaseRecyclerModel>?)
            adapter.notifyDataSetChanged()
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

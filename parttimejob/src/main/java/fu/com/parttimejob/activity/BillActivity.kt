package fu.com.parttimejob.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.BillListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_bill.*
import kotlinx.android.synthetic.main.activity_bill.back
import kotlinx.android.synthetic.main.activity_bill.konkonshuj

class BillActivity : BaseActivity() {
    lateinit var adapter: BillListAdapter
    override fun getLayoutId(): Int {
        return (R.layout.activity_bill)
    }

    override fun initViewParams() {
        adapter = BillListAdapter()
        bill_list.layoutManager = LinearLayoutManager(this)
        bill_list.adapter = adapter

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().myBill(SPUtil.getString(this, "thirdAccount", ""))).subscribe({
            adapter.clear();
            adapter.notifyDataSetChanged() ;
            adapter.addAll(it as List<BaseRecyclerModel>?)
            adapter.notifyDataSetChanged()
            if(it.size<1){
                konkonshuj.visibility = View.VISIBLE
            }else{
                konkonshuj.visibility = View.GONE
            }
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

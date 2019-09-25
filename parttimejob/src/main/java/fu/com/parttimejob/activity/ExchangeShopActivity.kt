package fu.com.parttimejob.activity

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.ExchangeShopListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.bean.ExchangeBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_exchange_shop.*

class ExchangeShopActivity : BaseActivity() {

    lateinit var adapter: ExchangeShopListAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_exchange_shop
    }

    override fun initViewParams() {

        back.setOnClickListener({
            finish()
        })
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().exchange(SPUtil.getString(this,"thirdAccount",""))).subscribe({
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
        adapter = ExchangeShopListAdapter()
        exchange_shop_list.layoutManager = GridLayoutManager(this, 2)
        exchange_shop_list.adapter = adapter

        var list: ArrayList<ExchangeBean> = ArrayList()
        adapter.addAll(list as List<BaseRecyclerModel>?)


    }

    override fun initViewClick() {

    }

    override fun onResume() {
        super.onResume()

    }
}

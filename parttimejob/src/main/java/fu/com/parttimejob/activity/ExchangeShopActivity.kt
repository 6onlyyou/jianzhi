package fu.com.parttimejob.activity

import android.support.v7.widget.GridLayoutManager
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.ExchangeShopListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
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

        adapter = ExchangeShopListAdapter()
        exchange_shop_list.layoutManager = GridLayoutManager(this, 2)
        exchange_shop_list.adapter = adapter

        var list: ArrayList<BaseRecyclerModel> = ArrayList()
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        adapter.addAll(list)

        back.setOnClickListener({
            finish()
        })
    }

    override fun initViewClick() {

    }

}

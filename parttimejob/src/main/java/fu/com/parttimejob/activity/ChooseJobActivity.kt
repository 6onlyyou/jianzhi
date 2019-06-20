package fu.com.parttimejob.activity

import android.support.v7.widget.GridLayoutManager
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.ChooseDreamJobListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import kotlinx.android.synthetic.main.activity_choose_job.*

class ChooseJobActivity : BaseActivity() {
    lateinit var adapter: ChooseDreamJobListAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_choose_job
    }

    override fun initViewParams() {
        adapter = ChooseDreamJobListAdapter()
        dreamJobList.layoutManager = GridLayoutManager(this, 4)
        dreamJobList.adapter = adapter
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
    }

    override fun initViewClick() {
        next.setOnClickListener({
            startActivity(MainActivity::class.java,true)
        })
    }


}

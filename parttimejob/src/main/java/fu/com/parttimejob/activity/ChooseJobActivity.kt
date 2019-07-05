package fu.com.parttimejob.activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.ChooseDreamJobListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.retrofitNet.RxUtils
import kotlinx.android.synthetic.main.activity_choose_job.*

class ChooseJobActivity : BaseActivity() {
    lateinit var adapter: ChooseDreamJobListAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_choose_job
    }

    override fun initViewParams() {
        adapter = ChooseDreamJobListAdapter()
        dreamJobList.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        dreamJobList.adapter = adapter
        var strarr = "".split(",")
        var list: ArrayList<BaseRecyclerModel> = ArrayList()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getLabel()).subscribe({

            if (it.labels != null && !it.labels.equals("")) {
                strarr = it.labels.substring(0, it.labels.length).split(",")
                var baseRecyclerModel: BaseRecyclerModel = BaseRecyclerModel()
                var index = 0;
                while (index <= strarr.size) {
                    baseRecyclerModel!!.setViewTypeSt(strarr[index])
                    index++//自增
                }
                list.add(BaseRecyclerModel())
                adapter.addAll(list)

            }
        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })


    }

    override fun initViewClick() {
        next.setOnClickListener({
            startActivity(MainActivity::class.java, true)
        })
    }

    override fun isTranslucent(): Boolean {
        return true
    }

}

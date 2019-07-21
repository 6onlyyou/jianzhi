package fu.com.parttimejob.activity

import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.dialog.abelPopWindowL
import kotlinx.android.synthetic.main.activity_job.*
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.lljjcoder.citypickerview.widget.CityPicker
import fu.com.parttimejob.adapter.HomeJobListAdapter
import fu.com.parttimejob.adapter.JobAdapter
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.bean.GetLabelsBean
import fu.com.parttimejob.bean.JobInfoBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil


class JobActivity : BaseActivity(){

    var homeJobListAdapter = JobAdapter()
    override fun getLayoutId(): Int {
        return R.layout.activity_job
    }

    override fun initViewParams() {

    }
     var list : ArrayList<GetLabelsBean>? =null
    var strarr: List<String> ?=null
    override fun initViewClick() {
        cityname.setText(SPUtil.getString(this,"city","廊坊"))
        jobList.layoutManager = LinearLayoutManager(this)
        jobList.adapter = homeJobListAdapter
         list = ArrayList()
        if (SPUtil.getString(this@JobActivity,"labelName","")!= null && !SPUtil.getString(this@JobActivity,"labelName","").equals("")) {
            strarr = SPUtil.getString(this@JobActivity,"labelName","").substring(0, SPUtil.getString(this@JobActivity,"labelName","").length).split(",")
            var index = 0;
            while (index < strarr!!.size) {
                var baseRecyclerModel: GetLabelsBean = GetLabelsBean()
                baseRecyclerModel.labels = (strarr!![index])
                index++//自增
                list!!.add(baseRecyclerModel)
            }
            job_label.setText(list!![0].labels)
        }else{
            job_label.setText("")
        }

        homeJobListAdapter.addAll(list as List<BaseRecyclerModel>?)
        job_city.setOnClickListener {
            selectAddress()
        }
        job_work.setOnClickListener {
            val addPopWindow = abelPopWindowL(this@JobActivity)
            addPopWindow.showPopupWindow(job_work)
        }

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().sameCity(SPUtil.getString(this,"thirdAccount",""),cityname.text.toString(),job_label.text.toString())).subscribe({
            homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
            homeJobListAdapter.notifyDataSetChanged()
        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })
    }
    private fun selectAddress() {
        val cityPicker = CityPicker.Builder(this@JobActivity)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(true)
                .build()
        cityPicker.show()
        cityPicker.setOnCityItemClickListener(object :CityPicker.OnCityItemClickListener{
            override fun onSelected(vararg citySelected: String?) {
                val province = citySelected[0]
                //城市
                val city = citySelected[1]
                //区县（如果设定了两级联动，那么该项返回空）
                cityname.setText(city)
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().sameCity(SPUtil.getString(this@JobActivity,"thirdAccount",""),city,job_label.text.toString())).subscribe({
                    homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
                    homeJobListAdapter.notifyDataSetChanged()
                }, {
                    ToastUtils.showLongToast(this@JobActivity, it.message.toString())
                })
            }

            override fun onCancel() {
            }

        })
            //监听方法，获取选择结果
    }

}

package fu.com.parttimejob.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.lljjcoder.citypickerview.widget.CityPicker
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.JobAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.bean.GetLabelsBean
import fu.com.parttimejob.dialog.abelPopWindowL
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_job.*


class JobActivity : BaseActivity() {

    var homeJobListAdapter = JobAdapter()
    override fun getLayoutId(): Int {
        return R.layout.activity_job
    }

    override fun initViewParams() {

    }

    var list: ArrayList<GetLabelsBean>? = null
    var strarr: List<String>? = null
    val list2: ArrayList<GetLabelsBean>? = ArrayList<GetLabelsBean>()
    override fun initViewClick() {
        cityname.text = SPUtil.getString(this, "city", "廊坊市")
        jobList.layoutManager = LinearLayoutManager(this)
        jobList.adapter = homeJobListAdapter
        list = ArrayList()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().label).subscribe { getLabelsBean ->
            if (getLabelsBean.labels != null && getLabelsBean.labels != "") {
                val strarr1 = getLabelsBean.labels.substring(0, getLabelsBean.labels.length).split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var index = 0
                index = 0
                while (index < strarr1.size - 1) {
                    val baseRecyclerModel = GetLabelsBean()
                    baseRecyclerModel.labels = strarr1[index]
                    if (index == 0) {
                        baseRecyclerModel.labelssel = true
                    }
                    index++//自增
                    list2!!.add(baseRecyclerModel)
                }
                job_label.text = list2!![0].labels
            }
        }
//        if (SPUtil.getString(this@JobActivity,"labelName","")!= null && !SPUtil.getString(this@JobActivity,"labelName","").equals("")) {
//            strarr = SPUtil.getString(this@JobActivity,"labelName","").substring(0, SPUtil.getString(this@JobActivity,"labelName","").length).split(",")
//            var index = 0;
//            while (index < strarr!!.size) {
//                var baseRecyclerModel: GetLabelsBean = GetLabelsBean()
//                baseRecyclerModel.labels = (strarr!![index])
//                index++//自增
//                list!!.add(baseRecyclerModel)
//            }
//            job_label.setText(list!![0].labels)
//        }else{
//            job_label.setText("")
//        }

        homeJobListAdapter.addAll(list as List<BaseRecyclerModel>?)
        job_city.setOnClickListener {
            selectAddress()
        }
        job_work.setOnClickListener {
            val addPopWindow = abelPopWindowL(this@JobActivity)
            addPopWindow.showPopupWindow(job_work) { jobName ->
                job_label.text = if (TextUtils.isEmpty(jobName)) {
                    "保姆"
                } else {
                    jobName
                }
                getJobList()
            }
        }
        back.setOnClickListener {
            finish()
        }
        getJobList()
    }

    public fun getJobList() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().sameCity(SPUtil.getString(this, "thirdAccount", ""), cityname.text.toString(), job_label.text.toString())).subscribe({
            homeJobListAdapter.clear();
            homeJobListAdapter.notifyDataSetChanged() ;
            homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
            homeJobListAdapter.notifyDataSetChanged()
            if(it.size>0){
                konkonshuj.visibility = View.GONE

            }else{
                konkonshuj.visibility = View.VISIBLE
            }
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
        cityPicker.setOnCityItemClickListener(object : CityPicker.OnCityItemClickListener {
            override fun onSelected(vararg citySelected: String?) {
                val province = citySelected[0]
                //城市
                val city = citySelected[1]
                //区县（如果设定了两级联动，那么该项返回空）
                cityname.text = city
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().sameCity(SPUtil.getString(this@JobActivity, "thirdAccount", ""), city, job_label.text.toString())).subscribe({
                    homeJobListAdapter.clear();
                    homeJobListAdapter.notifyDataSetChanged() ;
                    homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
                    homeJobListAdapter.notifyDataSetChanged()
                    if(it.size>0){
                        konkonshuj.visibility = View.GONE

                    }else{
                        konkonshuj.visibility = View.VISIBLE
                    }
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

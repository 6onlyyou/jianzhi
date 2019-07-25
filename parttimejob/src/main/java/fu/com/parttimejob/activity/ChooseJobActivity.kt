package fu.com.parttimejob.activity

import android.support.v7.widget.GridLayoutManager
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.ChooseDreamJobListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.bean.GetLabelsBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_choose_job.*
import java.util.*


class ChooseJobActivity : BaseActivity() {
    lateinit var adapter: ChooseDreamJobListAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_choose_job
    }

    var listself: ArrayList<GetLabelsBean>? = null
    var strarr: List<String>? = null
    var labelName: String? = ""
    override fun initViewParams() {
        listself = ArrayList<GetLabelsBean>()
        adapter = ChooseDreamJobListAdapter()
        dreamJobList.layoutManager = GridLayoutManager(this, 4)
        dreamJobList.adapter = adapter
        adapter.setOnItemClickListener { view, t, position -> adapter.changeSelectPosition(position) }
        if (SPUtil.getString(this@ChooseJobActivity, "labelName", "").equals("")) {
            myla_job.text = "您还没选择想找的工作类型"
        } else {
            myla_job.text = "我想找：" + SPUtil.getString(this@ChooseJobActivity, "labelName", "") + "工作"
        }

        if (SPUtil.getString(this@ChooseJobActivity, "labelName", "") != null && !SPUtil.getString(this@ChooseJobActivity, "labelName", "").equals("")) {
            strarr = SPUtil.getString(this@ChooseJobActivity, "labelName", "").substring(0, SPUtil.getString(this@ChooseJobActivity, "labelName", "").length).split(",")

        }

        var selectSize=0
        var strarrAll: List<String>
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().label).subscribe({
            if (it.labels != null && it.labels != "") {
                strarrAll = it.labels.substring(0, it.labels.length).split(",")

                if (strarr != null) {
                    // 选择过一次 删选重复项
                    selectSize = strarr!!.size
                    var index = 0
                    while (index < strarrAll.size) {
                        if (!strarr!!.contains(strarrAll[index])) {
                            strarr = strarr?.plus(strarrAll[index])
                        }
                        index++
                    }
                } else {
                    //没有选过 第一次
                    strarr = strarr?.plus(strarrAll)
                }

                var position = 0
                while (position < strarr!!.size) {
                    var baseRecyclerModel: GetLabelsBean = GetLabelsBean()
                    baseRecyclerModel.labels = (strarr!![position])
                    position++//自增
                    listself!!.add(baseRecyclerModel)
                }
                adapter.addAll(listself as List<BaseRecyclerModel>?)

                if (selectSize>0){
                    for (i in 0..selectSize) {
                        adapter.changeSelectPosition(i)
                    }
                }

            }
        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
    }

    override fun initViewClick() {
            next.setOnClickListener {
            var index = 0
            while (index < adapter.getselectPositionsData().size) {
                labelName = if (labelName.equals("")) {
                    labelName + adapter.getselectPositionsData()[index].labels
                } else {
                    labelName + "," + adapter.getselectPositionsData()[index].labels
                }

                index++//自增
            }

            labelName = labelName + "," + loginPwdEt.text.toString()
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().customizeLabel(SPUtil.getString(this, "thirdAccount", ""), labelName)).subscribe({
                ToastUtils.showLongToast(applicationContext, it)
                SPUtil.putString(this, "labelName", labelName)
            }, {
                ToastUtils.showLongToast(applicationContext, it.message.toString())
            })
            startActivity(MainActivity::class.java, true)
        }
    }

    override fun isTranslucent(): Boolean {
        return true
    }
}

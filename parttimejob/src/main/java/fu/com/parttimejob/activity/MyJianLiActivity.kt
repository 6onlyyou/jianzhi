package fu.com.parttimejob.activity

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.ChooseDreamJobListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.bean.GetLabelsBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.AppUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_my_jian_li.*
import java.util.ArrayList

/**
 * 我的简历/求职者简历
 */
class MyJianLiActivity : BaseActivity() {
    var beViewedAccount = ""
    var dialogPro: ProgressDialog? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_my_jian_li
    }
    lateinit var adapter: ChooseDreamJobListAdapter
    override fun initViewParams() {
        dialogPro = ProgressDialog(this)
        dialogPro!!.setCanceledOnTouchOutside(false)
        dialogPro!!.setMessage("小二加载中，大人请稍后~")
        dialogPro!!.setOnKeyListener(object : View.OnKeyListener, DialogInterface.OnKeyListener {
            override fun onKey(p0: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK && event!!.getAction() == KeyEvent.ACTION_DOWN) {
//                    val rxBusEntity = RxBusEntity()
//                    rxBusEntity.msg = "77"
//                    RxBus.getDefault().post(rxBusEntity)
//                    dialogPro!!.dismiss()
//                    finish()
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                return false
            }

        })
         beViewedAccount = intent.getStringExtra("beViewedAccount")
        if(beViewedAccount ==null||beViewedAccount.equals("")){
            beViewedAccount = SPUtil.getString(this,"thirdAccount","")
        }
        adapter = ChooseDreamJobListAdapter()
        jobYixiangList.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        jobYixiangList.adapter = adapter
    }

    override fun initViewClick() {
        jian_xiugai.setOnClickListener {
            startActivity(Intent(this, DisplayJianLiActivity::class.java))
        }
        back.setOnClickListener {
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        var strarr: List<String>
        var list: ArrayList<GetLabelsBean> = ArrayList()
        dialogPro!!.show()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getResumeInfo(SPUtil.getString(this,"thirdAccount",""),beViewedAccount)).subscribe({
            name.setText(it.name)
            sex.setText(it.sex+"  "+it.age+"岁")
            myInfo.setText(it.personalProfile)
            myLocation.setText(it.city)
            myInfo.setText(it.personalProfile)
            if (it.picOrVedioSource != null && !it.picOrVedioSource.equals("")) {
                strarr = it.labelName.substring(0, it.labelName.length).split(",")
                var index = 0;
                while (index < strarr.size) {
                    var baseRecyclerModel: GetLabelsBean = GetLabelsBean()
                    baseRecyclerModel.labels = (strarr[index])
                    baseRecyclerModel.labelssel = true
                    index++//自增
                    list.add(baseRecyclerModel)
                }
                adapter.addAll(list as List<BaseRecyclerModel>?)
            }
            if (it.labelName != null && !it.labelName.equals("")) {
                strarr = it.labelName.substring(0, it.labelName.length).split(",")
                var index = 0;
                while (index < strarr.size) {
                    var baseRecyclerModel: GetLabelsBean = GetLabelsBean()
                    baseRecyclerModel.labels = (strarr[index])
                    baseRecyclerModel.labelssel = true
                    index++//自增
                    list.add(baseRecyclerModel)
                }
                adapter.addAll(list as List<BaseRecyclerModel>?)
            }
            phone.setText(it.phoneNum)
            dialogPro!!.dismiss()
        }, {
            dialogPro!!.dismiss()
            startActivity(Intent(this, DisplayJianLiActivity::class.java))
            ToastUtils.showLongToast(this, "您还未创建简历请创建，让老板更了解你")
        })
    }
}

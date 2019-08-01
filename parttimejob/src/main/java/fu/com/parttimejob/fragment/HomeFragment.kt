package fu.com.parttimejob.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils

import fu.com.parttimejob.R
import fu.com.parttimejob.activity.*
import fu.com.parttimejob.adapter.HomeJobListAdapter
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.bean.JobInfoBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    var homeJobListAdapter = HomeJobListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jobList.layoutManager = LinearLayoutManager(context)
        jobList.adapter = homeJobListAdapter
//        var list: ArrayList<JobInfoBean> = ArrayList()
//
//        list.add(JobInfoBean())
//        homeJobListAdapter.addAll(list as List<BaseRecyclerModel>?)

        initClickListener()

    }

    private fun initClickListener() {
        if( SPUtil.getInt(activity, "Profession", 1)==1){
            make_money_iv.visibility = View.VISIBLE
            exchange_shop_iv.visibility = View.VISIBLE
            brief_iv.visibility = View.VISIBLE
            management.visibility = View.GONE
            campaign.visibility = View.GONE
            brief_iv.visibility = View.VISIBLE
            pushzp.visibility = View.GONE
        }else{
            make_money_iv.visibility = View.GONE
            exchange_shop_iv.visibility = View.GONE
            brief_iv.visibility = View.GONE
            management.visibility = View.VISIBLE
            campaign.visibility = View.VISIBLE
            pushzp.visibility = View.VISIBLE
        }
        management.setOnClickListener {
            startActivity(Intent(context, TalentManagementActivity::class.java))
        }
        campaign.setOnClickListener {
            startActivity(Intent(context, AdListActivity::class.java))
        }
        pushzp.setOnClickListener {
            startActivity(Intent(context, PublishJobActivity::class.java))
        }
        same_city_iv.setOnClickListener {
            startActivity(Intent(context, JobActivity::class.java))
        }
        make_money_iv.setOnClickListener {
            startActivity(Intent(context, MyInviteActivity::class.java))
        }

        exchange_shop_iv.setOnClickListener {
            startActivity(Intent(context, ExchangeShopActivity::class.java))
        }

        brief_iv.setOnClickListener {
            val intent = Intent(context, MyJianLiActivity::class.java)
            intent.putExtra("beViewedAccount", "")
            context!!.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().firstPage(SPUtil.getString(context,"thirdAccount",""))).subscribe({
            homeJobListAdapter.clear();
            homeJobListAdapter.notifyDataSetChanged() ;
            homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
            homeJobListAdapter.notifyDataSetChanged()
        }, {
            ToastUtils.showLongToast(context, it.message.toString())
        })
    }
}// Required empty public constructor

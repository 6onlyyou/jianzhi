package fu.com.parttimejob.fragment


import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.GlideImageLoader
import fu.com.parttimejob.utils.NotificationUtil
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


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
        var k=false;
        k= NotificationUtil.isNotifyEnabled(context);
        if(!k) {
            HintDialog(activity, R.style.dialog, "请前往设置打开通知权限，以便我们及时通知您", object : HintDialog.OnCloseListener {
                override fun onClick(dialog: Dialog, confirm: Boolean) {
                    if (confirm) {
                        val localIntent = Intent()
                        //直接跳转到应用通知设置的代码：
                        //直接跳转到应用通知设置的代码：
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //8.0及以上
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
                            localIntent.setData(Uri.fromParts("package", activity!!.packageName, null))
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0以上到8.0以下
                            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS")
                            localIntent.putExtra("app_package", activity!!.packageName)
                            localIntent.putExtra("app_uid", activity!!.applicationInfo.uid)
                        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) { //4.4
                            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            localIntent.addCategory(Intent.CATEGORY_DEFAULT)
                            localIntent.setData(Uri.parse("package:$ activity!!.packageName"))
                        } else {
                            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            if (Build.VERSION.SDK_INT >= 9) {
                                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
                                localIntent.setData(Uri.fromParts("package", activity!!.packageName, null))
                            } else if (Build.VERSION.SDK_INT <= 8) {
                                localIntent.setAction(Intent.ACTION_VIEW)
                                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails")
                                localIntent.putExtra("com.android.settings.ApplicationPkgName", activity!!.packageName)
                            }
                        }
                        startActivity(localIntent)
                    } else {

                    }
                    dialog.dismiss()
                }
            })
                    .setTitle("").show()
        }
        initClickListener()

    }

    private fun initClickListener() {
        if (SPUtil.getInt(activity, "Profession", 1) == 1) {
            make_money_iv.visibility = View.VISIBLE
            exchange_shop_iv.visibility = View.VISIBLE
            brief_iv.visibility = View.VISIBLE
            management.visibility = View.GONE
            campaign.visibility = View.GONE
            brief_iv.visibility = View.VISIBLE
            pushzp.visibility = View.GONE
        } else {
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
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().firstPage(SPUtil.getString(context, "thirdAccount", ""))).subscribe({
            homeJobListAdapter.clear();
            homeJobListAdapter.notifyDataSetChanged();
            homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
            homeJobListAdapter.notifyDataSetChanged()
            if (it.size < 1) {
                konkonshuj.visibility = View.VISIBLE
                konkonshuTv.visibility = View.VISIBLE
                jobList.visibility = View.GONE

            } else {
                konkonshuj.visibility = View.GONE
                konkonshuTv.visibility = View.GONE
                jobList.visibility = View.VISIBLE

            }

        }, {
            ToastUtils.showLongToast(context, it.message.toString())
        })

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().bannerInfo()).subscribe({
            banner.setImageLoader(GlideImageLoader())
            //设置图片集合
            val imageUrls = ArrayList<Any>()
            if (it.size > 0) {
                for (bannerBean in it) {
                    imageUrls.add(bannerBean.imgAddress)
                }
                banner.setOnBannerListener { position ->
                    WebActivity.startSelf(context, it[position].title, it[position].netAddress)
                }
                banner.setImages(imageUrls)
                banner.start()
                banner.setDelayTime(3000)
            }
        }, {
            ToastUtils.showLongToast(context, it.message.toString())
        })
    }
}// Required empty public constructor

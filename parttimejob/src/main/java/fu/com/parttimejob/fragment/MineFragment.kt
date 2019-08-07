package fu.com.parttimejob.fragment


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fu.com.parttimejob.R
import fu.com.parttimejob.activity.*
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.utils.GlideUtil
import fu.com.parttimejob.utils.SPUtil
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.fragment_mine.*


/**
 * A simple [Fragment] subclass.
 */
class MineFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (SPUtil.getInt(activity, "Profession", 1) == 1) {
            editJianLiLayout.visibility = View.VISIBLE
            historyLayout.visibility = View.VISIBLE
            zhaopinlishi.visibility = View.GONE
            editGaogaohdon.visibility = View.GONE
            setsgil.visibility = View.VISIBLE
            ava.setImageResource(R.mipmap.ic_interviewer_img)
            myDuiHuanLayout.visibility = View.VISIBLE
        } else {
            jianliview.visibility = View.GONE
            vidwdiz.visibility = View.GONE
            viewgout.visibility = View.GONE
            editJianLiLayout.visibility = View.GONE
            editGaogaohdon.visibility = View.VISIBLE
            zhaopinlishi.visibility = View.VISIBLE
            historyLayout.visibility = View.GONE
            setsgilview.visibility = View.GONE
            setsgilview1.visibility = View.GONE
            myDuiHuanLayout.visibility = View.GONE
            setsgil.visibility = View.GONE
            ava.setImageResource(R.mipmap.ic_job_hunter_img)
        }
        if (SPUtil.getString(context, "cardHeadImg", "").equals("")) {
            GlideUtil.load(context, SPUtil.getString(context, "headImg", ""), ava)
        }else{
            GlideUtil.load(context, SPUtil.getString(context, "cardHeadImg", ""), ava)
        }


        if (SPUtil.getString(context, "nickName", "").equals("")) {
            name.text = SPUtil.getString(context, "jianliname", "")
        } else {
            name.text = SPUtil.getString(context, "nickName", "")
        }
        sex_age.text = SPUtil.getString(context, "phoneNumber", "")
        setsgil.setOnClickListener {
            startActivity(Intent(context, ChooseJobActivity::class.java))
        }

        myDuiHuanLayout.setOnClickListener {
            startActivity(Intent(context, MyDuiHuanActivity::class.java))
        }
        historyLayout.setOnClickListener {
            var intent = Intent(context, CommunicateHistoryActivity::class.java)
            intent.putExtra("title", CommunicateHistoryActivity.CommuniHistory)
            startActivity(Intent(context, CommunicateHistoryActivity::class.java))
        }
        zhaopinlishi.setOnClickListener {
            var intent = Intent(context, ZPListActivity::class.java)
            startActivity(intent)
        }
        editGaogaohdon.setOnClickListener {
            startActivity(Intent(context, AdListActivity::class.java))

        }
        editJianLiLayout.setOnClickListener {
            val intent = Intent(context, MyJianLiActivity::class.java)
            intent.putExtra("beViewedAccount", "")
            context!!.startActivity(intent)
        }
        yaoQingLayout.setOnClickListener {
            startActivity(Intent(context, MyInviteActivity::class.java))
        }
        goset.setOnClickListener {
            startActivity(Intent(context, CreateJobCardActivity::class.java))
        }
        myMoneyLayout.setOnClickListener {
            startActivity(Intent(context, MyMoneyActivity::class.java))
        }

        aboutUsLayout.setOnClickListener {
            startActivity(Intent(context, AboutUsActivity::class.java))
        }
        loginOut.setOnClickListener {
            HintDialog(context, R.style.dialog, "是否确定退出登录？", object : HintDialog.OnCloseListener {
                override fun onClick(dialog: Dialog, confirm: Boolean) {
                    if (confirm) {
                        RongIM.getInstance().logout()
                        SPUtil.putString(context, "thirdAccount", "")
                        SPUtil.putInt(context, "loginType", 0)
                        SPUtil.putString(context, "registrationDate", "")
                        SPUtil.putString(context, "city", "")
                        SPUtil.putString(context, "longitude", "")
                        SPUtil.putString(context, "latitude", "")
                        SPUtil.putString(context, "inviteCode", "")
                        SPUtil.putString(context, "labelName", "")
                        SPUtil.putString(context, "phoneNumber","")
                        SPUtil.putInt(context, "totalCount", 0)
                        SPUtil.putInt(context, "inviteCount", 0)
                        SPUtil.putString(context, "nickName", "")
                        SPUtil.putString(context, "headImg", "")
                        SPUtil.putString(context, "companyName", "")
                        activity!!.finish()
                        startActivity(Intent(context, ChooseProfessionActivity::class.java))
                    } else {

                    }
                    dialog.dismiss()
                }
            })
                    .setTitle("").show()
        }
    }
}// Required empty public constructor

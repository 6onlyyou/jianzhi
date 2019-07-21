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

        if( SPUtil.getInt(activity, "Profession", 2)==2){
            editJianLiLayout.visibility = View.VISIBLE
            historyLayout.visibility = View.VISIBLE
            zhaopinlishi.visibility = View.GONE
            editGaogaohdon.visibility = View.GONE
            setsgil.visibility = View.VISIBLE
        }else{
            editJianLiLayout.visibility = View.GONE
            editGaogaohdon.visibility = View.VISIBLE
            zhaopinlishi.visibility = View.GONE
            historyLayout.visibility = View.GONE
            setsgil.visibility = View.GONE
        }
        setsgil.setOnClickListener {
            startActivity(Intent(context, ChooseJobActivity::class.java))
        }
        historyLayout.setOnClickListener {
            startActivity(Intent(context, CommunicateHistoryActivity::class.java))

        }
        zhaopinlishi.setOnClickListener {
            startActivity(Intent(context, CommunicateHistoryActivity::class.java))

        }
        editGaogaohdon.setOnClickListener {
            startActivity(Intent(context, AdListActivity::class.java))

        }
        editJianLiLayout.setOnClickListener {
            startActivity(Intent(context, MyJianLiActivity::class.java))
        }
        yaoQingLayout.setOnClickListener {
            startActivity(Intent(context, MyInviteActivity::class.java))
        }
        myMoneyLayout.setOnClickListener {
            startActivity(Intent(context, MyMoneyActivity::class.java))
        }
        historyLayout.setOnClickListener {
            startActivity(Intent(context, CommunicateHistoryActivity::class.java))
        }
        aboutUsLayout.setOnClickListener {
            startActivity(Intent(context, AboutUsActivity::class.java))
        }
        loginOut.setOnClickListener {
            HintDialog(context, R.style.dialog, "是否确定退出登录？", object : HintDialog.OnCloseListener {
                override fun onClick(dialog: Dialog, confirm: Boolean) {
                    if (confirm) {
                        RongIM.getInstance().logout()
                        SPUtil.putString(context,"thirdAccount","")
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

package fu.com.parttimejob.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fu.com.parttimejob.R
import fu.com.parttimejob.activity.CommunicateHistoryActivity
import fu.com.parttimejob.activity.DisplayJianLiActivity
import fu.com.parttimejob.activity.MyInviteActivity
import fu.com.parttimejob.activity.MyMoneyActivity
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
        editJianLiLayout.setOnClickListener {
            startActivity(Intent(context, DisplayJianLiActivity::class.java))
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
            startActivity(Intent(context, CommunicateHistoryActivity::class.java))
        }
    }
}// Required empty public constructor

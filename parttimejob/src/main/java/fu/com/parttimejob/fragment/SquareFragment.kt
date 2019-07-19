package fu.com.parttimejob.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fu.com.parttimejob.R
import fu.com.parttimejob.view.bezier.RedPacketsLayout


/**
 * A simple [Fragment] subclass.
 */
class SquareFragment : Fragment(),View.OnClickListener  {
    override fun onClick(p0: View?) {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v:View = inflater.inflate(R.layout.fragment_square, container, false)
        val redPacketsLayout = v.findViewById<RedPacketsLayout>(R.id.packets_layout)
        redPacketsLayout.post(Runnable { redPacketsLayout.startRain() })
        return v

    }

}// Required empty public constructor

package fu.com.parttimejob.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fu.com.parttimejob.R


/**
 * A simple [Fragment] subclass.
 *
 */
class JianliFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_jianli, container, false)
    }


}

package fu.com.parttimejob.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.HomeJobListAdapter
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
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
        var list: ArrayList<BaseRecyclerModel> = ArrayList()
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        homeJobListAdapter.addAll(list)

    }
}// Required empty public constructor

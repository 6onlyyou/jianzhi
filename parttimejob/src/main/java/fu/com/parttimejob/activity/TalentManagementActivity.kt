package fu.com.parttimejob.activity

import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.TalentFragmentAdapter
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_talent_management.*

class TalentManagementActivity : BaseActivity() {

    lateinit var adapter: TalentFragmentAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_talent_management
    }

    override fun initViewParams() {
        adapter = TalentFragmentAdapter()
        var data: ArrayList<Any> = ArrayList<Any>()
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        adapter.data = data
        viewPager.adapter = adapter

        viewPager.pageMargin = 45
    }

    override fun initViewClick() {
    }


}

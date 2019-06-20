package fu.com.parttimejob.activity

import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.heixiu.errand.net.RetrofitFactory
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.FragmentAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.fragment.HomeFragment
import fu.com.parttimejob.fragment.MessageFragment
import fu.com.parttimejob.fragment.MineFragment
import fu.com.parttimejob.fragment.SquareFragment
import fu.com.parttimejob.utils.SPUtil
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {
    private lateinit var adapter: FragmentAdapter
    private val titles = ArrayList<String>()
    private val selectImgs = ArrayList<Int>()

    override fun initViewParams() {
        adapter = FragmentAdapter(supportFragmentManager)

        selectImgs.add(R.mipmap.ic_launcher)
        selectImgs.add(R.mipmap.ic_launcher)
        selectImgs.add(R.mipmap.ic_launcher)
        selectImgs.add(R.mipmap.ic_launcher)

        adapter.addFragments(HomeFragment())
        adapter.addFragments(SquareFragment())
        adapter.addFragments(MessageFragment())
        adapter.addFragments(MineFragment())


        for (i in titles.indices) {
            val tabView = LayoutInflater.from(this).inflate(R.layout.item_tab_main, null)
            val tabImg:ImageView = tabView.findViewById(R.id.tabImg)
            val tabTitle:TextView = tabView.findViewById(R.id.tab)
            tabImg.setImageResource(selectImgs[i])
            if (i == 0) {
                tabImg.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary))
            } else {
                tabImg.setColorFilter(ContextCompat.getColor(this, R.color.colorLine))
            }
            tabTitle.setText(titles[i])
            val tab = main_tab.newTab().setCustomView(tabView)
            tab.setTag(tabImg)

            main_tab.addTab(tab)
        }

        main_vp.adapter = adapter

        main_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until main_tab.tabCount) {
                    val selectTab = main_tab.getTabAt(i)
                    if (i == position) {
                        selectTab!!.select()
                        (selectTab.tag as ImageView).setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
                    } else {
                        (selectTab!!.tag as ImageView).setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorWhite))
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        main_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                main_vp.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun initViewClick() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private fun connect(token: String) {
        RongIM.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onTokenIncorrect() {
                Log.e("MainActivity", "--onTokenIncorrect")

            }

            override fun onSuccess(userid: String) {
                Log.e("MainActivity", "--onSuccess--" + userid)
                RongIM.getInstance().setCurrentUserInfo(UserInfo(userid, SPUtil.getString("nickname", ""), Uri.parse(SPUtil.getString("headurl", ""))))
                RongIM.getInstance().setMessageAttachedUserInfo(true)
            }

            override fun onError(errorCode: RongIMClient.ErrorCode) {
                Log.e("MainActivity", "--onError")
                showToast("融云登录失败，请检查网络")
            }
        })
    }
}

package fu.com.parttimejob.activity

import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.FragmentAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.fragment.HomeFragment
import fu.com.parttimejob.fragment.MessageFragment
import fu.com.parttimejob.fragment.MineFragment
import fu.com.parttimejob.fragment.SquareFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {
    private lateinit var adapter: FragmentAdapter
    private val titles = ArrayList<String>()
    private val imgs = ArrayList<Int>()


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initViewParams() {
        adapter = FragmentAdapter(supportFragmentManager)

        imgs.add(R.mipmap.ic_home_select)
        imgs.add(R.mipmap.ic_shop_unselect)
        imgs.add(R.mipmap.ic_message_unselect)
        imgs.add(R.mipmap.ic_mine_unselect)

        adapter.addFragments(HomeFragment())
        adapter.addFragments(SquareFragment())
        adapter.addFragments(MessageFragment())
        adapter.addFragments(MineFragment())

        titles.add("首页")
        titles.add("商城")
        titles.add("消息")
        titles.add("我的")

        for (i in titles.indices) {
            val tabView = LayoutInflater.from(this).inflate(R.layout.item_tab_main, null)
            val tabImg: ImageView = tabView.findViewById(R.id.tabImg)
            val tabTitle: TextView = tabView.findViewById(R.id.tab)
            tabImg.setImageResource(imgs[i])
            if (i == 0) {
                tabImg.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary))
            } else {
                tabImg.setColorFilter(ContextCompat.getColor(this, R.color.colorLine))
            }
            tabTitle.text = titles[i]
            val tab = main_tab.newTab().setCustomView(tabView)
            tab.tag = tabImg

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
                        (selectTab!!.tag as ImageView).setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorMenuWhite))
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


}

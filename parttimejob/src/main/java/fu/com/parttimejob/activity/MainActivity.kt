package fu.com.parttimejob.activity

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.luck.picture.lib.rxbus2.RxBus
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.FragmentAdapter
import fu.com.parttimejob.base.ActivityManager
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.RxBusEntity
import fu.com.parttimejob.dialog.DingPDialog
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.fragment.HomeFragment
import fu.com.parttimejob.fragment.MessageFragment
import fu.com.parttimejob.fragment.MineFragment
import fu.com.parttimejob.fragment.SquareFragment
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.ConversationListAdapterEx
import fu.com.parttimejob.utils.LocationUtils
import fu.com.parttimejob.utils.SPUtil
import fu.com.parttimejob.weight.ConversationListImFragment
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.rong.imkit.RongContext
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.imlib.model.UserInfo
import io.rong.message.TextMessage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_talent_management.view.*
import java.util.*

class MainActivity : BaseActivity() {
    private lateinit var adapter: FragmentAdapter
    private val titles = ArrayList<String>()
    private val imgs = ArrayList<Int>()
    private var numcon: TextView? = null
    private var subscribe: Disposable? = null



    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun isTranslucent(): Boolean {
        return true
    }

    private fun initConversationList(): Fragment {
        val listFragment = ConversationListImFragment()
        listFragment.setAdapter(ConversationListAdapterEx(RongContext.getInstance()))
        val uri = Uri.parse("rong://" + applicationInfo.packageName).buildUpon()
                .appendPath("conversationList")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .build()
        listFragment.uri = uri
        return listFragment
    }
    override fun initViewParams() {



        subscribe = RxBus.getDefault().toObservable(RxBusEntity::class.java).subscribe(object : Consumer<RxBusEntity> {
            @Throws(Exception::class)
            override fun accept(catchDollUserInfoBean: RxBusEntity) {
                if (catchDollUserInfoBean.msg.equals("103")) {

                    initConversationList()
                }
            }
        }, object : Consumer<Throwable> {
            @Throws(Exception::class)
            override fun accept(throwable: Throwable) {
            }
        })
        adapter = FragmentAdapter(supportFragmentManager)

        imgs.add(R.mipmap.ic_home_select)
        imgs.add(R.mipmap.ic_shop_unselect)
        imgs.add(R.mipmap.ic_message_unselect)
        imgs.add(R.mipmap.ic_mine_unselect)

        adapter.addFragments(HomeFragment())
        adapter.addFragments(SquareFragment())
        adapter.addFragments(initConversationList())
        adapter.addFragments(MineFragment())
        titles.add("首页")
        titles.add("家园")
        titles.add("消息")
        titles.add("我的")

        for (i in titles.indices) {
            if(i==2){
                val tabView = LayoutInflater.from(this).inflate(R.layout.item_tabs_main, null)
                val tabImg: ImageView = tabView.findViewById(R.id.tabImg)
                val tabTitle: TextView = tabView.findViewById(R.id.tab)
                numcon = tabView.findViewById(R.id.numcon)
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
            }else{
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



        }
        main_vp.offscreenPageLimit =3
        main_vp.adapter = adapter
        main_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until main_tab.tabCount) {
                    val selectTab = main_tab.getTabAt(i)
                    if(position == 0){
                        incl_titles.visibility = View.GONE
                    }else if(position == 1){
                        incl_titles.visibility = View.GONE
                    }else if(position == 2){
                        incl_titles.visibility = View.VISIBLE
                    }else if(position == 3){
                        incl_titles.visibility = View.GONE
                    }
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            DingPDialog(this, R.style.dialog, "需要允许地理位置,为您推荐附近工作", object : DingPDialog.OnCloseListener {
                override fun onClick(dialog: Dialog, confirm: Boolean) {
                    if (confirm) {
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                        ), 1)//自定义的code
                    } else {

                    }
                    dialog.dismiss()
                }
            })
                    .setTitle("").show()

        }
//        if (SPUtil.getInt(this@MainActivity, "Profession", 1) == 2) {

            main_vp.currentItem = 0
            incl_titles.visibility = View.GONE
//        }else{
//            main_vp.currentItem = 1
//        }
        main_vp.offscreenPageLimit = 4
    }

    override fun initViewClick() {
        initRongMessage()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getCloudToken( SPUtil.getString(this@MainActivity, "thirdAccount", ""))).subscribe({
            SPUtil.putString(this@MainActivity,"tokenryun",it.token)
            connect(it.token)
        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
    }

    private fun connect(token: String) {
        RongIM.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onTokenIncorrect() {
                Log.e("MainActivity", "--onTokenIncorrect")

            }

            override fun onSuccess(userid: String) {
                Log.e("MainActivity", "--onSuccess--" + userid)
                if (SPUtil.getInt(this@MainActivity, "Profession", 1) == 1) {
                    RongIM.getInstance().setCurrentUserInfo(UserInfo(userid, SPUtil.getString(this@MainActivity, "nickName", "游客用户"), Uri.parse(SPUtil.getString(this@MainActivity, "headImg", ""))))
                    RongIM.getInstance().setMessageAttachedUserInfo(true)
                }else{
                    RongIM.getInstance().setCurrentUserInfo(UserInfo(userid, SPUtil.getString(this@MainActivity, "companyName", "")+"的"+SPUtil.getString(this@MainActivity, "nickName", "游客用户"), Uri.parse(SPUtil.getString(this@MainActivity, "headImg", ""))))
                    RongIM.getInstance().setMessageAttachedUserInfo(true)
                }

            }

            override fun onError(errorCode: RongIMClient.ErrorCode) {
                Log.e("MainActivity", "--onError")
                showToast("融云登录失败，请检查网络")
            }
        })
    }

    private var firstTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                firstTime = secondTime
                return true
            } else {
                ActivityManager.exitApp()
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    var userInfo: UserInfo? = null
    override fun onResume() {
        super.onResume()
        var strarr: List<String>
        var listimg: ArrayList<String> = ArrayList()
        if(SPUtil.getString(this@MainActivity, "longitude", "")!=null&&!SPUtil.getString(this@MainActivity, "longitude", "").equals("")){
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getUserInfo(SPUtil.getString(this@MainActivity, "thirdAccount", ""), SPUtil.getInt(this@MainActivity, "Profession", 1), SPUtil.getString(this@MainActivity, "longitude", ""), SPUtil.getString(this@MainActivity, "latitude", ""), SPUtil.getString(this@MainActivity, "city", ""), SPUtil.getString(this@MainActivity, "token", ""))).subscribe({
                SPUtil.putInt(this@MainActivity, "loginType", it.loginType)
                SPUtil.putInt(this@MainActivity, "userid", it.id)
                SPUtil.putString(this@MainActivity, "registrationDate", it.registrationDate)
                SPUtil.putString(this@MainActivity, "city", it.city)
                SPUtil.putString(this@MainActivity, "cardPhoneNum", it.cardPhoneNum)
                SPUtil.putString(this@MainActivity, "inviteCode", it.inviteCode)
                SPUtil.putString(this@MainActivity, "labelName", it.labelName)
                SPUtil.putInt(this@MainActivity, "totalCount", it.totalCount)
                SPUtil.putString(this@MainActivity, "phoneNumber", it.phoneNumber)
                SPUtil.putInt(this@MainActivity, "inviteCount", it.inviteCount)
                SPUtil.putString(this@MainActivity, "nickName", it.name)
                SPUtil.putString(this@MainActivity, "headImg", it.headImg)
                SPUtil.putString(this@MainActivity, "companyName", it.companyName)
                SPUtil.putString(this@MainActivity, "jianliname", it.nickName)
                SPUtil.putInt(this@MainActivity, "vipLevel", it.vipLevel)
                SPUtil.putString(this@MainActivity, "cardHeadImg", it.cardHeadImg)

                if(SPUtil.getInt(this@MainActivity, "Profession", 1)==1){
                    if(SPUtil.getString(this@MainActivity, "jianliname", "").toString().equals("")){
                    }else{
                        userInfo = UserInfo(SPUtil.getString(this@MainActivity, "thirdAccount", "").toString(), SPUtil.getString(this@MainActivity, "jianliname", "").toString(), Uri.parse(SPUtil.getString(this@MainActivity, "jianlihead", "https://apic.douyucdn.cn/upload/avatar_v3/201811/1fc77337cf1c0460659ccc6b9583d812_middle.jpg")))
                    }

                }else{
                    if(SPUtil.getString(this@MainActivity, "redEnvelopename", "").toString().equals("")){
                    }else{
                        userInfo = UserInfo(SPUtil.getString(this@MainActivity, "thirdAccount", "").toString(), SPUtil.getString(this@MainActivity, "redEnvelopename", "").toString(), Uri.parse(SPUtil.getString(this@MainActivity, "cardHeadImg", "https://apic.douyucdn.cn/upload/avatar_v3/201811/1fc77337cf1c0460659ccc6b9583d812_middle.jpg")))
                    }

                }

                RongIM.getInstance().refreshUserInfoCache(userInfo)
            }, {
                ToastUtils.showLongToast(applicationContext, it.message.toString())
            })
        }

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getResumeInfo(SPUtil.getString(this,"thirdAccount",""),SPUtil.getString(this,"thirdAccount",""))).subscribe({
            if (it.picOrVedioSource != null && !it.picOrVedioSource.equals("")) {
                strarr = it.picOrVedioSource.substring(0, it.picOrVedioSource.length).split(";")
                var index = 0;
                while (index < strarr.size) {
                    listimg.add(strarr[index])
                    index++//自
                }
                if (listimg.size < 1) {

                } else if (listimg.size == 1) {

                    SPUtil.putString(this@MainActivity, "contactInformationheadImg",listimg[0])
                } else {

                    SPUtil.putString(this@MainActivity, "contactInformationheadImg",listimg[1])
                }


            }

        }, {

        })
    }
    private fun initRongMessage() {
      
        val conversationTypes = arrayOf(Conversation.ConversationType.PRIVATE, Conversation.ConversationType.DISCUSSION, Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM, Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE)
        val handler = Handler()
        handler.postDelayed({
            RongIM.getInstance().setOnReceiveUnreadCountChangedListener(mCountListener, *conversationTypes)
        }, 1000)

    }
    var mCountListener: RongIM.OnReceiveUnreadCountChangedListener = RongIM.OnReceiveUnreadCountChangedListener { count ->
        if (count == 0) {
            numcon!!.visibility = View.GONE
        } else if (count in 1..99) {
            numcon!!.visibility = View.VISIBLE
            numcon!!.text = count.toString()
        }
        RongIM.setConversationClickListener(MyConversationClickListener())
    }
    /**
     * 融云消息接收，及初始化
     */
    private inner class MyConversationClickListener : RongIM.ConversationClickListener {
        /**
         * 当点击用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param user             被点击的用户的信息。
         * @param targetId         会话 id
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        override fun onUserPortraitClick(context: Context, conversationType: Conversation.ConversationType, user: UserInfo, targetId: String): Boolean {

//            intent.putExtra("beViewedAccount", user.userId)
//            startActivity(intent)
            return true
        }

        /**
         * 当长按用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param user             被点击的用户的信息。
         * @param targetId         会话 id
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        override fun onUserPortraitLongClick(context: Context, conversationType: Conversation.ConversationType, user: UserInfo, targetId: String): Boolean {
            return false
        }

        /**
         * 当点击消息时执行。
         *
         * @param context 上下文。
         * @param view    触发点击的 View。
         * @param message 被点击的消息的实体信息。
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
         */
        override fun onMessageClick(context: Context, view: View, message: Message): Boolean {
            return false
        }

        /**
         * 当点击链接消息时执行。
         *
         * @param context 上下文。
         * @param link    被点击的链接。
         * @param message 被点击的消息的实体信息
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
         */
        override fun onMessageLinkClick(context: Context, link: String, message: Message): Boolean {
            return false
        }

        /**
         * 当长按消息时执行。
         *Notification
         * @param context 上下文。
         * @param view    触发点击的 View。
         * @param message 被长按的消息的实体信息。
         * @return 如果用户自己处理了长按后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        override fun onMessageLongClick(context: Context, view: View, message: Message): Boolean {
            return false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RongIM.getInstance().disconnect()
        subscribe!!.dispose()
    }
}

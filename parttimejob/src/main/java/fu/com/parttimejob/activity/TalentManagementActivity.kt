package fu.com.parttimejob.activity

import android.net.Uri
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.TalentFragmentAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.ResumeInfoBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_talent_management.*

class TalentManagementActivity : BaseActivity() {

    lateinit var adapter: TalentFragmentAdapter
    var data: List<ResumeInfoBean>? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_talent_management
    }

    override fun initViewParams() {
        adapter = TalentFragmentAdapter()
        data = ArrayList<ResumeInfoBean>()
        viewPager.adapter = adapter
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().searchSameCity(SPUtil.getString(this, "thirdAccount", "111"), SPUtil.getString(this, "city", ""))).subscribe({
            data = it
            adapter.data = it
            adapter.notifyDataSetChanged()
            if(it.size<1){
                konkonshuj.visibility = View.VISIBLE
            }else{
                konkonshuj.visibility = View.GONE
            }
        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
        viewPager.pageMargin = 45
    }

    override fun initViewClick() {
        back.setOnClickListener { finish() }
        communicationNow.setOnClickListener {
            if (data!!.size < 1) {
                ToastUtils.showLongToast(applicationContext, "没有人可以沟通噢")
            } else {
                RongIM.setUserInfoProvider({
                    //在这里，根据userId，使用同步的请求，去请求服务器，就可以完美做到显示用户的头像，昵称了
                    if (data!![viewPager.currentItem]!!.headImg == null || data!![viewPager.currentItem]!!.headImg.equals("")) {
                        UserInfo(data!![viewPager.currentItem]!!.thirdAccount.toString(), data!![viewPager.currentItem]!!.name.toString() , Uri.parse("http://konkonyu.oss-cn-beijing.aliyuncs.com/moren.jpg"))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
                    } else {
                        UserInfo(data!![viewPager.currentItem]!!.thirdAccount.toString(), data!![viewPager.currentItem]!!.name.toString(), Uri.parse(data!![viewPager.currentItem]!!.headImg + ""))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
                    }

                }, true)
                RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString(this, "thirdAccount", ""), SPUtil.getString(this, "nickName", ""), Uri.parse(SPUtil.getString(this, "headImg", ""))))
                RongIM.getInstance().setMessageAttachedUserInfo(true)
                RongIM.getInstance().startPrivateChat(this, data!![viewPager.currentItem]!!.thirdAccount.toString(), data!![viewPager.currentItem]!!.name.toString())

            }

        }
    }


}

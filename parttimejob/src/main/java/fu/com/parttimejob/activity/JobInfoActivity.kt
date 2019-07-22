package fu.com.parttimejob.activity

import android.content.Intent
import android.net.Uri
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.SameCityBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_job_info.*

class JobInfoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_job_info
    }

    override fun initViewParams() {

    }

    override fun initViewClick() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().singleRecruitmentDetail(SPUtil.getString(this,"thirdAccount",""),intent.getIntExtra("id",0))).subscribe({
            label_job.setText(it.label)
            jobSalaryTv.setText(it.salaryAndWelfare)
            jobLocation.setText(it.city)
            content_job.setText(it.workContent)
            phone.setText(it.phoneNumber)
            job_jbi.setText("分享群领取"+it.recruitingNumbers/it.redEnvelopeNumber+"金币")
            coid_job.setText("分享成功后可获得"+it.recruitingNumbers/it.redEnvelopeNumber+"虚拟币奖励")
        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })
        back.setOnClickListener {
            finish()
        }
//        var smeCityBean:SameCityBean=  intent.getSerializableExtra("user") as SameCityBean
        ji_gouton.setOnClickListener {
            if (SPUtil.getString(this, "thirdAccount", "").equals("")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                ToastUtils.showShortToast(this,"需要先登入才可以使用其他功能")
            } else {
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addCommunicationRecord(SPUtil.getString(this,"thirdAccount",""),intent.getIntExtra("id",0))).subscribe({

                }, {

                    ToastUtils.showLongToast(this, it.message.toString())
                })
//                RongIM.setUserInfoProvider({
                    //在这里，根据userId，使用同步的请求，去请求服务器，就可以完美做到显示用户的头像，昵称了
//                    if(orderEntity.orderInfo!!.headImgUrl==null||orderEntity.orderInfo!!.headImgUrl.equals("")){
//                        UserInfo(orderEntity.orderInfo!!.phoneNumber.toString(), orderEntity.orderInfo!!.nickName.toString(), Uri.parse("http://konkonyu.oss-cn-beijing.aliyuncs.com/moren.jpg"))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
//                    }else{
//                        UserInfo(orderEntity.orderInfo!!.phoneNumber.toString(), orderEntity.orderInfo!!.nickName.toString(), Uri.parse(orderEntity.orderInfo!!.headImgUrl+""))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
//                    }
//
//                }, true)
//                RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString(this, "phonenumber", ""), SPUtil.getString(this, "nickname", ""), Uri.parse(SPUtil.getString(this, "headurl", ""))))
//                RongIM.getInstance().setMessageAttachedUserInfo(true)
//                RongIM.getInstance().startPrivateChat(this, orderEntity.orderInfo!!.phoneNumber.toString(), orderEntity.orderInfo.nickName)
            }
        }
    }

}

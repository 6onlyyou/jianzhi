package fu.com.parttimejob.activity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.RecruitInfoBean
import fu.com.parttimejob.dialog.HintDialog
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
    var recruitInfoBean: RecruitInfoBean ? = null
    var jinbi = 0
    override fun initViewClick() {
        recruitInfoBean = RecruitInfoBean()

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().singleRecruitmentDetail(SPUtil.getString(this, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
            recruitInfoBean = it
            if (SPUtil.getString(this, "thirdAccount", "").equals(it.thirdAccount)) {
                if (it.state == 1) {
                    ji_gouton.setText("关闭招聘")
                } else {
                    ji_gouton.setText("开启招聘")
                }

            } else {
                ji_gouton.setText("立即沟通")
            }

            jinbi = it.unclaimedVirtualCoins
            label_job.setText(it.label)
            jobSalaryTv.setText(it.salaryAndWelfare)
            jobLocation.setText(it.city)
            content_job.setText("工作内容:"+it.workContent)
            location_job.setText("工作地点："+it.contactAddress)
            phone.setText(it.phoneNumber)
            num_job.setText("招聘人数："+it.numberOfVirtualCoins)
            job_jbi.setText("分享群领取" + it.recruitingNumbers / it.redEnvelopeNumber + "金币")
            coid_job.setText("分享成功后可获得" + it.recruitingNumbers / it.redEnvelopeNumber + "虚拟币奖励")
            ji_gouton.setOnClickListener {
                if (SPUtil.getString(this, "thirdAccount", "").equals("")) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    ToastUtils.showShortToast(this, "需要先登入才可以使用其他功能")
                } else {
                    when (ji_gouton.text.toString()) {
                        "关闭招聘" -> openD()
                        "开启招聘" -> clossD()
                        "立即沟通" -> tallD()

                    }
                }
            }
        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })
        back.setOnClickListener {
            finish()
        }

    }

    fun clossD() {
        HintDialog(this, R.style.dialog, "关闭招聘返还" + jinbi + "金币,是否继续？", object : HintDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().closeRecruitmentInfo(SPUtil.getString(this@JobInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                        ToastUtils.showLongToast(this@JobInfoActivity, it)
                    }, {
                        ToastUtils.showLongToast(this@JobInfoActivity, it.message.toString())
                    })
                } else {

                }
                dialog.dismiss()
            }
        })
                .setTitle("").show()

    }

    fun openD() {
        HintDialog(this, R.style.dialog, "重新打开招聘需要" + jinbi + "金币,是否继续？", object : HintDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().openAdvertisementInfo(SPUtil.getString(this@JobInfoActivity, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
                        ToastUtils.showLongToast(this@JobInfoActivity, it)
                    }, {
                        ToastUtils.showLongToast(this@JobInfoActivity, it.message.toString())
                    })
                } else {

                }
                dialog.dismiss()
            }
        })
                .setTitle("").show()
    }

    fun tallD() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addCommunicationRecord(SPUtil.getString(this, "thirdAccount", ""), intent.getIntExtra("id", 0))).subscribe({
            RongIM.setUserInfoProvider({
                //在这里，根据userId，使用同步的请求，去请求服务器，就可以完美做到显示用户的头像，昵称了
                if (recruitInfoBean!!.headImg == null ||recruitInfoBean!!.headImg.equals("")) {
                    UserInfo(recruitInfoBean!!.thirdAccount.toString(), recruitInfoBean!!.companyName.toString()+"的"+recruitInfoBean!!.nickName.toString(), Uri.parse("http://konkonyu.oss-cn-beijing.aliyuncs.com/moren.jpg"))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
                } else {
                    UserInfo(recruitInfoBean!!.thirdAccount.toString(), recruitInfoBean!!.companyName.toString()+"的"+recruitInfoBean!!.nickName.toString(), Uri.parse(recruitInfoBean!!.headImg + ""))//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。   
                }

            }, true)
            RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString(this, "thirdAccount", ""), SPUtil.getString(this, "nickName", ""), Uri.parse(SPUtil.getString(this, "headImg", ""))))
            RongIM.getInstance().setMessageAttachedUserInfo(true)
            RongIM.getInstance().startPrivateChat(this, recruitInfoBean!!.thirdAccount.toString(), recruitInfoBean!!.companyName+"的"+recruitInfoBean!!.nickName.toString())

        }, {

            ToastUtils.showLongToast(this, it.message.toString())
        })

    }
}

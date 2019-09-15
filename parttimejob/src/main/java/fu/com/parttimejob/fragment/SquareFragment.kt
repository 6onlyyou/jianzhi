package fu.com.parttimejob.fragment


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.activity.AdInfoActivity
import fu.com.parttimejob.activity.BindPhoneActivity
import fu.com.parttimejob.activity.JobInfoActivity
import fu.com.parttimejob.bean.RedPackageBean
import fu.com.parttimejob.dialog.JobDialog
import fu.com.parttimejob.dialog.RadDialog
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.AutoUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_square.*


/**
 * A simple [Fragment] subclass.
 */
class SquareFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        if (SPUtil.getString(context, "phoneNumber", "") == "") run {
            ToastUtils.showLongToast(context, "请先绑定手机号才可以查看噢")
            val intent = Intent(context, BindPhoneActivity::class.java)
            startActivity(intent)
            return
        }
        when (p0?.id) {
            R.id.box1 -> {
                openBox(box1IsOpen, 0, box1)
            }

            R.id.box2 -> {
                openBox(box2IsOpen, 1, box2)
            }
            R.id.box3 -> {
                openBox(box3IsOpen, 2, box3)
            }
        }
    }

    private fun openBox( isopen: Boolean, position: Int, view: ImageView) {
        if (!isopen) {
            dialogPro.show()
            if (data[position].isAdvertiseType) {
                getGuangGao(data[position], view)
            }
            if (data[position].isRecruitmentType) {
                getZhaoPin(data[position], view)
            }
            when (position+1) {
                1 -> {
                    box1IsOpen = true
                }
                2 -> {
                    box2IsOpen = true
                }
                3 -> {
                    box3IsOpen = true
                }
            }
        } else {
            ToastUtils.showLongToast(context, "该红包已开启")
        }
    }

    var box1IsOpen = false
    var box2IsOpen = false
    var box3IsOpen = false
    lateinit var dialogPro: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_square, container, false)
//        val redPacketsLayout = v.findViewById<RedPacketsLayout>(R.id.packets_layout)
//        redPacketsLayout.post(Runnable { redPacketsLayout.startRain() })
        AutoUtils.setSize(activity, false, 1080, 1980)
        AutoUtils.auto(v)
        dialogPro = ProgressDialog(context)
        dialogPro.setCanceledOnTouchOutside(true)
        dialogPro.setMessage("小二加载中，大人请稍后~")

        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).asGif().load(R.drawable.hongbao_bg).into(bg)

        getRedPackage()

        box1.setOnClickListener(this)
        box2.setOnClickListener(this)
        box3.setOnClickListener(this)
    }

    fun getAnima(): RotateAnimation {
        val rotate = RotateAnimation(30f, -30f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 1500
        rotate.repeatCount = -1
        rotate.repeatMode = Animation.REVERSE

        return rotate
    }

    lateinit var data: List<RedPackageBean>
    fun getRedPackage() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().randomGetThreeRedPacket(SPUtil.getString(context, "thirdAccount", ""))).subscribe({
            var i = 1
            data = it
            for (redPackageBean in it) {
                if (i == 1) {
                    dealBitType(box1, redPackageBean.bitType)
                }
                if (i == 2) {
                    dealBitType(box2, redPackageBean.bitType)
                }
                if (i == 3) {
                    dealBitType(box3, redPackageBean.bitType)
                }
                i++
            }
            when (it.size) {
                1 -> {
                    box1.startAnimation(getAnima())
                }
                2 -> {
                    box1.startAnimation(getAnima())
                    box2.startAnimation(getAnima())
                }
                3 -> {
                    box1.startAnimation(getAnima())
                    box2.startAnimation(getAnima())
                    box3.startAnimation(getAnima())
                }
            }
        }, {
            ToastUtils.showLongToast(context, it.message)
        })
    }

    fun dealBitType(view: ImageView, bitType: Int) {
        when (bitType) {
            1 -> {
                view.setImageResource(R.mipmap.tie_xiangzi)
            }
            2 -> {
                view.setImageResource(R.mipmap.jin_xiangzi)
            }
            3 -> {
                view.setImageResource(R.mipmap.zuan_xiangzi)
            }

        }
    }

    fun getZhaoPin(bean: RedPackageBean, view: ImageView) {
        JobDialog(context, R.style.dialog, bean.workContent, JobDialog.OnCloseListener { dialog, confirm ->
            val intent = Intent(context, JobInfoActivity::class.java)
            intent.putExtra("id", bean.id)
            startActivity(intent)
            dialogPro.dismiss()
            dialog.dismiss()
        }).show()
        when (bean.bitType) {
            1 -> {
                view.setImageResource(R.mipmap.tie_open)
            }
            2 -> {
                view.setImageResource(R.mipmap.jin_open)
            }
            3 -> {
                view.setImageResource(R.mipmap.zuan_open)
            }

        }
        dialogPro.dismiss()
    }

    fun getGuangGao(bean: RedPackageBean, view: ImageView) {
        RadDialog(context, R.style.dialog, "恭喜获得" + bean.numberOfVirtualCoins / bean.redEnvelopeNumber + "金币", RadDialog.OnCloseListener { dialog, confirm ->
            val intent = Intent(context, AdInfoActivity::class.java)
            intent.putExtra("id", bean.id)
            startActivity(intent)
            dialog.dismiss()
            dialogPro.dismiss()
        }).setTitle("").show()
        when (bean.bitType) {
            1 -> {
                view.setImageResource(R.mipmap.tie_open)
            }
            2 -> {
                view.setImageResource(R.mipmap.jin_open)
            }
            3 -> {
                view.setImageResource(R.mipmap.zuan_open)
            }

        }
        dialogPro.dismiss()
    }
}// Required empty public constructor

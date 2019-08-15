package fu.com.parttimejob.activity

import android.content.Intent
import android.util.Log
import android.widget.TextView
import com.luck.picture.lib.rxbus2.RxBus
import com.tencent.connect.common.Constants
import com.tencent.tauth.Tencent
import com.umeng.socialize.UMShareAPI
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.RxBusEntity
import fu.com.parttimejob.dialog.ShareTypeFragment
import fu.com.parttimejob.utils.SPUtil
import fu.com.parttimejob.weight.BaseUiListener
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_my_invite.*

class MyInviteActivity : BaseActivity() {
    private var shareTypeFragment: ShareTypeFragment? = null
    private var mTencent: Tencent? = null//qq主操作对象
    private var subscribe: Disposable? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_my_invite
    }
    override fun onDestroy() {
        super.onDestroy()
        subscribe!!.dispose()
    }
    override fun initViewParams() {
        subscribe = RxBus.getDefault().toObservable(RxBusEntity::class.java).subscribe(object : Consumer<RxBusEntity> {
            @Throws(Exception::class)
            override fun accept(catchDollUserInfoBean: RxBusEntity) {
                if (catchDollUserInfoBean.msg.equals("101")) {

                }
            }
        }, object : Consumer<Throwable> {
            @Throws(Exception::class)
            override fun accept(throwable: Throwable) {
            }
        })
        shareTypeFragment = ShareTypeFragment()
    }

    lateinit var invite_num: TextView;
    override fun initViewClick() {
        mTencent = Tencent.createInstance("1109483400", this)
        share.setOnClickListener {
            shareTypeFragment!!.show(getFragmentManager(), "3" )
        }

        back.setOnClickListener {
            finish()
        }
        invite_num = findViewById(R.id.invite_num)
        invite_num.setText(SPUtil.getInt(this@MyInviteActivity, "inviteCount", 0).toString())
        invite_code.setText(SPUtil.getString(this@MyInviteActivity, "inviteCode", ""))
        putshare.setOnClickListener {
            startActivity(Intent(this, WriteInviteCodeActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (null != mTencent)
//        {
//            mTencent!!.onActivityResult(requestCode, resultCode, data)
//        }
        Tencent.onActivityResultData(requestCode, resultCode, data,  BaseUiListener());

        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE ||
                    resultCode == Constants.REQUEST_QZONE_SHARE ||
                    resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data,  BaseUiListener());
            }
        }

    }


}

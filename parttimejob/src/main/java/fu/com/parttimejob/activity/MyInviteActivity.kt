package fu.com.parttimejob.activity

import android.content.Intent
import android.widget.TextView
import com.umeng.socialize.UMShareAPI
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.dialog.ShareTypeFragment
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_my_invite.*

class MyInviteActivity : BaseActivity() {
    private var shareTypeFragment: ShareTypeFragment? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_my_invite
    }

    override fun initViewParams() {
        shareTypeFragment = ShareTypeFragment()
    }
    lateinit var invite_num: TextView;
    override fun initViewClick() {
        share.setOnClickListener {
            shareTypeFragment!!.show(getFragmentManager(), "11", "sss")
        }

        back.setOnClickListener {
            finish()
        }
        invite_num = findViewById(R.id.invite_num)
        invite_num.setText(SPUtil.getInt(this@MyInviteActivity, "inviteCount", 0).toString())
        invite_code.setText(SPUtil.getString(this@MyInviteActivity,"inviteCode",""))
        putshare.setOnClickListener {
            startActivity(Intent(this, WriteInviteCodeActivity::class.java))
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

}

package fu.com.parttimejob.activity

import android.content.Intent
import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_invite.*

class MyInviteActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_my_invite
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        putshare.setOnClickListener {
            startActivity(Intent(this, WriteInviteCodeActivity::class.java))
        }
    }


}

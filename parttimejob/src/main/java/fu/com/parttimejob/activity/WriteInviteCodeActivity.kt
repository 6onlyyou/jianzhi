package fu.com.parttimejob.activity

import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import kotlinx.android.synthetic.main.activity_write_invite_code.*

class WriteInviteCodeActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_write_invite_code
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        back.setOnClickListener {
            finish()
        }
    }

}

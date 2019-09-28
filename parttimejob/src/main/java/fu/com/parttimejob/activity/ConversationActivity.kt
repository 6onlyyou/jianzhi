package fu.com.parttimejob.activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fu.com.parttimejob.R
import kotlinx.android.synthetic.main.conversation.*

class ConversationActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation)
        val sName = intent.data!!.getQueryParameter("title")//获取昵称

        message_name.text = sName

        message_back.setOnClickListener { finish() }
    }
}

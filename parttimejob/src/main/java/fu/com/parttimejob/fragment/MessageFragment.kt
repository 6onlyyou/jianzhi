package fu.com.parttimejob.fragment


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fu.com.parttimejob.R
import fu.com.parttimejob.utils.ConversationListAdapterEx
import fu.com.parttimejob.weight.ConversationListImFragment
import io.rong.imkit.RongContext
import io.rong.imlib.model.Conversation


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }
    private fun initConversationList(): Fragment {

        val listFragment = ConversationListImFragment()
        listFragment.setAdapter(ConversationListAdapterEx(RongContext.getInstance()));
        val uri = Uri.parse("rong://" + context!!.packageName).buildUpon()
                .appendPath("conversationList")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .build()
        listFragment.uri = uri
        return listFragment
    }
}// Required empty public constructor

package fu.com.parttimejob.activity;

import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.fragment.IHistoryDataResultCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Description:
 * Data：2019/1/18-20:27
 * Author: fushuaige
 */
public class ConversationListImFragment extends ConversationListFragment {

    @Override
    public void getConversationList(Conversation.ConversationType[] conversationTypes, final IHistoryDataResultCallback<List<Conversation>> callback) {

        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {

            @Override

            public void onSuccess(List<Conversation> conversations) {

                if (callback != null) {
                    callback.onResult(conversations);
                }

            }


            @Override

            public void onError(RongIMClient.ErrorCode e) {

                if (callback != null) {

                    callback.onError();

                }

            }

        }, conversationTypes);
        super.getConversationList(conversationTypes,callback);
    }
}

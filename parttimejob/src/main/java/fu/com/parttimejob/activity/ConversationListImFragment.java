package fu.com.parttimejob.activity;

import android.net.Uri;
import android.os.Parcel;

import com.heixiu.errand.net.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import fu.com.parttimejob.bean.UserInfoBean;
import fu.com.parttimejob.retrofitNet.RxUtils;
import io.reactivex.functions.Consumer;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.fragment.IHistoryDataResultCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

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
            public void onSuccess(final List<Conversation> conversations) {
                if (callback != null) {
//                    final List<Conversation> conversationsl = new ArrayList<Conversation>();
//                    final Conversation conversation = new Conversation();
////                    conversations.get(i).setSenderUserName(userInfoBean.getNickName());
//                    if(conversations ==null){
                        callback.onResult(conversations);
//                    }else{
//                        for(int i=0;i<conversations.size();i++){
//                            final int finalI = i;
//                            RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().h5queryUserInfo(conversations.get(i).getTargetId())).subscribe(new Consumer<UserInfoBean>() {
//                                @Override
//                                public void accept(UserInfoBean userInfoBean) throws Exception {
//                                    conversation.setSenderUserName(userInfoBean.getName());
//                                    conversation.setConversationTitle(userInfoBean.getName());
//                                    conversation.setLatestMessage(conversations.get(finalI).getLatestMessage());
//                                    conversation.setPortraitUrl(userInfoBean.getHeadImg());
//                                    conversation.setSenderUserId(conversations.get(finalI).getSenderUserId());
//                                    conversation.setTargetId(conversations.get(finalI).getTargetId());
//                                    conversation.setObjectName(conversations.get(finalI).getObjectName());
//                                    conversation.setConversationType(conversations.get(finalI).getConversationType());
//                                    conversationsl.add(conversation);
//                                    callback.onResult(conversationsl);
//                                }
//                            }, new Consumer<Throwable>() {
//                                @Override
//                                public void accept(Throwable throwable) throws Exception {

//                                    callback.onResult(conversationsl);
//                                }
//                            });
//                        }
//                    }
//                    conversationsl.add()

        }

    }

            @Override

            public void onError(RongIMClient.ErrorCode e) {

                if (callback != null) {

                    callback.onError();

                }

            }

        }, conversationTypes);
//        super.getConversationList(conversationTypes,callback);
    }
}

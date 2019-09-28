package fu.com.parttimejob.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.heixiu.errand.net.RetrofitFactory;

import fu.com.parttimejob.bean.UserInfoBean;
import fu.com.parttimejob.retrofitNet.RxUtils;
import io.reactivex.functions.Consumer;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Description:
 * Data：2019/3/13-16:14
 * Author: fushuaige
 */
public class ConversationListAdapterEx extends ConversationListAdapter {
    public ConversationListAdapterEx(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        return super.newView(context, position, group);
    }
    UserInfo  userInfo=null;
    @Override
    protected void bindView(View v, int position, final UIConversation data) {

        if(data.getUIConversationTitle().equals("")||data.getUIConversationTitle().length()>12){
            RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().h5queryUserInfo(data.getConversationSenderId())).subscribe(new Consumer<UserInfoBean>() {
                @Override
                public void accept(UserInfoBean userInfoBean) throws Exception {
                    userInfo = new UserInfo(data.getConversationTargetId(), userInfoBean.getName(), Uri.parse(userInfoBean.getHeadImg()));
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    userInfo = new UserInfo(data.getConversationTargetId(), "公众号用户", Uri.parse("https://jiujiuqihan.oss-cn-beijing.aliyuncs.com/default/defaultHeadImg.png"));
                }
            });
        }else{
             userInfo = new UserInfo(data.getConversationTargetId(),data.getUIConversationTitle() ,data.getIconUrl());
        }

        if (data.getConversationType().equals(Conversation.ConversationType.DISCUSSION))

            data.setUnreadType(UIConversation.UnreadRemindType.REMIND_ONLY);

        data.updateConversation(userInfo);
        super.bindView(v, position, data);
    }
}
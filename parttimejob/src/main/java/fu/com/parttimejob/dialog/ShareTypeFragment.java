package fu.com.parttimejob.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import fu.com.parttimejob.R;
import fu.com.parttimejob.utils.SPContants;
import fu.com.parttimejob.utils.SPUtil;
import fu.com.parttimejob.utils.SocietyShareUtils;


public class ShareTypeFragment extends DialogFragment {

    private Dialog mDialog;
    private TextView qqFriends;
    private TextView qqSpeace;
    private TextView wxFriends;
    private TextView wxSpeace;
    private String orderid;
    private String orderinfo;

    private IWXAPI api;

    public static ShareTypeFragment newInstance() {
        ShareTypeFragment fragment = new ShareTypeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView( R.layout.pop_layout_share);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(0, 0, 0, 0); //没有边距
        //layoutParams.dimAmount = 0.0f;  //背景不变暗
        layoutParams.dimAmount = 0.7f;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //背景透明
        window.setAttributes(layoutParams);

        api = WXAPIFactory.createWXAPI(getActivity(), SPContants.WX_APP_ID, false);
        final String inviteCode = SPUtil.getString(getActivity(),"","");
        qqFriends = mDialog.findViewById(R.id.qq_friend);
        qqSpeace = mDialog.findViewById(R.id.qq_space);
        wxFriends = mDialog.findViewById(R.id.wx_friend);
        wxSpeace = mDialog.findViewById(R.id.wx_space);

        mDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        wxSpeace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SocietyShareUtils().circleShare(getActivity(), api, "http://www.konkonyu.com/page/personOrderDetail.html?orderNum="+orderid, "动态详情", orderinfo);
            }
        });
        wxFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SocietyShareUtils().friendShare(getActivity(), api, "http://www.konkonyu.com/page/personOrderDetail.html?orderNum="+orderid, "动态详情", orderinfo);
//                new SocietyShareUtils().friendShare(getActivity(), api, Urls.SHARE_URL + inviteCode, getString(R.string.share_title), getString(R.string.share_thumb));
            }
        });
        qqSpeace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SocietyShareUtils().roomShare(getActivity(), getActivity(), "http://www.konkonyu.com/page/personOrderDetail.html?orderNum="+orderid,"动态详情", orderinfo);
            }
        });
        qqFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SocietyShareUtils().qqShare(( getActivity()), getActivity(), "http://www.konkonyu.com/page/personOrderDetail.html?orderNum="+orderid,"动态详情", orderinfo);
            }
        });
        return mDialog;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDialog.isShowing())
            mDialog.dismiss();
    }
    public void show(FragmentManager manager, String tag,String orderinfo) {
        try {
            orderid = tag;
            this.orderinfo = orderinfo;
            super.show(manager, tag);

        } catch (IllegalStateException ignore) {
            //
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

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
    private String name,address,salaryAndWelfare,workContent,phone,code,lon,lat,url,recruitingNumbers,workTime,contactAddress,publichDate,city,content,imageUrl,headImg;
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
                if(orderid.equals("1")){

                    new SocietyShareUtils().circleShare(getActivity(), api, "http://jiujiu.konkonyu.com/part/#/detailsCopy?name="+name+"&address="+address+"&salaryAndWelfare="+salaryAndWelfare+"&workContent="+workContent+"&phone="+phone+"&code="+code+"&lon="+lon+"&lat="+lat+"&url="+url+"&recruitingNumbers="+recruitingNumbers+"&workTime="+workTime+"&contactAddress="+contactAddress, name, workContent);

                }else if(orderid.equals("2")){
                    new SocietyShareUtils().circleShare(getActivity(), api, "http://jiujiu.konkonyu.com/part/#/advertisementCopy?name="+name+"&publichDate="+publichDate+"&city="+city+"&content="+content+"&imageUrl="+imageUrl+"&code="+code+"&headImg="+headImg, name, content);
                }else if(orderid.equals("3")){
                    new SocietyShareUtils().circleShare(getActivity(), api, "http://jiujiu.konkonyu.com/part/#/invitationCopy?coin=60&code="+SPUtil.getString(getActivity(), "inviteCode", "")+"&url=http://jiujiu.konkonyu.com/appservice/99zhaogon.apk", "好友拉你抢红包找工作", "下载填写邀请码即可获得红包噢等什么马上行动起来吧");
                }
            }
        });
        wxFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderid.equals("1")){

                    new SocietyShareUtils().friendShare(getActivity(), api, "http://jiujiu.konkonyu.com/part/#/detailsCopy?name="+name+"&address="+address+"&salaryAndWelfare="+salaryAndWelfare+"&workContent="+workContent+"&phone="+phone+"&code="+code+"&lon="+lon+"&lat="+lat+"&url="+url+"&recruitingNumbers="+recruitingNumbers+"&workTime="+workTime+"&contactAddress="+contactAddress, name, workContent);

                }else if(orderid.equals("2")){
                    new SocietyShareUtils().friendShare(getActivity(), api, "http://jiujiu.konkonyu.com/part/#/advertisementCopy?name="+name+"&publichDate="+publichDate+"&city="+city+"&content="+content+"&imageUrl="+imageUrl+"&code="+code+"&headImg="+headImg, name, content);
                }else if(orderid.equals("3")){
                    new SocietyShareUtils().friendShare(getActivity(), api, "http://jiujiu.konkonyu.com/part/#/invitationCopy?coin=60&code="+SPUtil.getString(getActivity(), "inviteCode", "")+"&url=http://jiujiu.konkonyu.com/appservice/99zhaogon.apk", "好友拉你抢红包找工作", "下载填写邀请码即可获得红包噢等什么马上行动起来吧");
                }
            }
        });
        qqSpeace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderid.equals("1")){

                    new SocietyShareUtils().roomShare(getActivity(),  getActivity(), "http://jiujiu.konkonyu.com/part/#/detailsCopy?name="+name+"&address="+address+"&salaryAndWelfare="+salaryAndWelfare+"&workContent="+workContent+"&phone="+phone+"&code="+code+"&lon="+lon+"&lat="+lat+"&url="+url+"&recruitingNumbers="+recruitingNumbers+"&workTime="+workTime+"&contactAddress="+contactAddress, name, workContent);

                }else if(orderid.equals("2")){
                    new SocietyShareUtils().roomShare(getActivity(),  getActivity(), "http://jiujiu.konkonyu.com/part/#/advertisementCopy?name="+name+"&publichDate="+publichDate+"&city="+city+"&content="+content+"&imageUrl="+imageUrl+"&code="+code+"&headImg="+headImg, name, content);
                }else if(orderid.equals("3")){
                    new SocietyShareUtils().roomShare(getActivity(),  getActivity(), "http://jiujiu.konkonyu.com/part/#/invitationCopy?coin=60&code="+SPUtil.getString(getActivity(), "inviteCode", "")+"&url=http://jiujiu.konkonyu.com/appservice/99zhaogon.apk", "好友拉你抢红包找工作", "下载填写邀请码即可获得红包噢等什么马上行动起来吧");
                }
            }
        });
        qqFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderid.equals("1")){

                    new SocietyShareUtils().qqShare(getActivity(),  getActivity(), "http://jiujiu.konkonyu.com/part/#/detailsCopy?name="+name+"&address="+address+"&salaryAndWelfare="+salaryAndWelfare+"&workContent="+workContent+"&phone="+phone+"&code="+code+"&lon="+lon+"&lat="+lat+"&url="+url+"&recruitingNumbers="+recruitingNumbers+"&workTime="+workTime+"&contactAddress="+contactAddress, name, workContent);

                }else if(orderid.equals("2")){
                    new SocietyShareUtils().qqShare(getActivity(),  getActivity(), "http://jiujiu.konkonyu.com/part/#/advertisementCopy?name="+name+"&publichDate="+publichDate+"&city="+city+"&content="+content+"&imageUrl="+imageUrl+"&code="+code+"&headImg="+headImg, name, content);
                }else if(orderid.equals("3")){
                    new SocietyShareUtils().qqShare(getActivity(),  getActivity(), "http://jiujiu.konkonyu.com/part/#/invitationCopy?coin=60&code="+SPUtil.getString(getActivity(), "inviteCode", "")+"&url=http://jiujiu.konkonyu.com/appservice/99zhaogon.apk", "好友拉你抢红包找工作", "下载填写邀请码即可获得红包噢等什么马上行动起来吧");
                }
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

    public void show(FragmentManager manager, String tag,String name,String address,String salaryAndWelfare,String workContent,String phone,String code,String lon,String lat,String url,String recruitingNumbers,String workTime,String contactAddress) {
        try {
            orderid = tag;
            this.name = name;
            this.address = address;
            this.salaryAndWelfare = salaryAndWelfare;
            this.workContent = workContent;
            this.phone = phone;
            this.code = code;
            this.lon = lon;
            this.lat = lat;
            this.url = url;
            this.recruitingNumbers = recruitingNumbers;
            this.workTime = workTime;
            this.contactAddress = contactAddress;
            super.show(manager, tag);

        } catch (IllegalStateException ignore) {
            //
        }
    }
    public void show(FragmentManager manager, String tag,String name,String publichDate,String city,String content,String imageUrl,String code,String url,String headImg) {
        try {
            orderid = tag;
            this.name = name;
            this.publichDate = publichDate;
            this.city = city;
            this.content = content;
            this.imageUrl = imageUrl;
            this.code = code;
            this.url = url;
            this.headImg = headImg;
            super.show(manager, tag);

        } catch (IllegalStateException ignore) {

        }
    }
    public void show(FragmentManager manager, String tag) {
        try {
            orderid = tag;
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

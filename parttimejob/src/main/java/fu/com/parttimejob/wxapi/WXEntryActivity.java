package fu.com.parttimejob.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lljjcoder.citylist.Toast.ToastUtils;
import com.luck.picture.lib.rxbus2.RxBus;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import fu.com.parttimejob.bean.RxBusEntity;
import fu.com.parttimejob.utils.SPContants;


/**
 * 微信登录授权
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        api = WXAPIFactory.createWXAPI(this, SPContants.WX_APP_ID, false);
        api.registerApp(SPContants.WX_APP_ID);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i(TAG + "AAAA", " onResp error code = " + baseResp.errCode);
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    RxBusEntity rxBusEntity = new RxBusEntity();
                    rxBusEntity.setMsg("101");
                    RxBus.getDefault().post(rxBusEntity);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    break;
                default:
                    finish();
                    break;
            }
            finish();
        }
        if (baseResp instanceof SendAuth.Resp) {
            final SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            Log.e(TAG + "AAAA", resp.code);
            if (resp.errCode == 0) {
                if (resp.state.equals("wechat_login")) {
                    RxBus.getDefault().post(resp);
                    finish();
                }
            }
        }

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

}

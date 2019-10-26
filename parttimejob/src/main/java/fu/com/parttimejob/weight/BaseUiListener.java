package fu.com.parttimejob.weight;

import com.lljjcoder.citylist.Toast.ToastUtils;
import com.luck.picture.lib.rxbus2.RxBus;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import fu.com.parttimejob.bean.RxBusEntity;

/**
 * Description:
 * Data：2019/6/26-22:37
 * Author: fushuaige
 */
public class BaseUiListener implements IUiListener {

    @Override
    public void onComplete(Object response) {
        doComplete(response);
    }
    protected void doComplete(Object values) {
        if(!values.equals("s")){
            RxBusEntity rxBusEntity =  new RxBusEntity();
            rxBusEntity.setMsg("101");
            RxBus.getDefault().post(rxBusEntity);
        }
    }

    @Override
    public void onError(UiError e) {
        doComplete(e.errorCode);
//        showResult("onError:", "code:" + e.errorCode + ", msg:"
//                + e.errorMessage + ", detail:" + e.errorDetail);
    }
    @Override
    public void onCancel() {
        doComplete("s");
//        showResult("onCancel", "");
    }
}
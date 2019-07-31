package fu.com.parttimejob.weight;

import com.lljjcoder.citylist.Toast.ToastUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Description:
 * Data：2019/6/26-22:37
 * Author: fushuaige
 */
public class BaseUiListener implements IUiListener {

    @Override
    public void onComplete(Object response) {
//        mBaseMessageText.setText("onComplete:");
//        mMessageText.setText(response.toString());
        doComplete(response);
    }
    protected void doComplete(Object values) {

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
package fu.com.parttimejob.weight;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Description:
 * Data：2019/6/26-22:37
 * Author: fushuaige
 */
public class BaseUiListener implements IUiListener {


    public void onComplete(Object o) {
        JSONObject response;
        response =  (JSONObject)o;
    }


    @Override
    public void onError(UiError e) {
//        showResult("onError:", "code:" + e.errorCode + ", msg:"
//                + e.errorMessage + ", detail:" + e.errorDetail);
    }
    @Override
    public void onCancel() {
//        showResult("onCancel", "");
    }
}
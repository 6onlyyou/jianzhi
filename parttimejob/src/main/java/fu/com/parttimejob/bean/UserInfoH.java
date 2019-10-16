package fu.com.parttimejob.bean;

import android.net.Uri;
import android.os.Parcel;

import io.rong.imlib.model.UserInfo;

/**
 * Description:
 * Data：2019/10/12-19:17
 * Author: fushuaige
 */
public class UserInfoH extends UserInfo {
    private String myId;
    private String myName;
    private String myIcon;

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyIcon() {
        return myIcon;
    }

    public void setMyIcon(String myIcon) {
        this.myIcon = myIcon;
    }

    public UserInfoH(Parcel in) {
        super(in);
    }

    public UserInfoH(String id, String name, Uri portraitUri,String myId,String myIcon,String myName) {
        super(id, name, portraitUri);
        this.myId = myId;
        this.myIcon = myIcon;
        this.myName = myName;
    }
}

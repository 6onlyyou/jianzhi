package fu.com.parttimejob.retrofitNet;

import fu.com.parttimejob.bean.ResponseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by PVer on 2019/5/5.
 */

public interface ApiService {
    /**
     * 获取token
     *
     * @return
     */
    @POST("/appservice/app/alluser/register")
    Observable<ResponseBean<String>> register(@Query("thirdAccount") String thirdAccount, @Query("identyType") int identyType, @Query("nickName") String nickName, @Query("headImg") String headImg, @Query("sex") String sex, @Query("loginType") int loginType);

}

package fu.com.parttimejob.retrofitNet;

import fu.com.parttimejob.bean.LoginBean;
import fu.com.parttimejob.bean.ResponseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by PVer on 2019/5/5.
 */

public interface ApiService {
    /**
     * 手机注册接口
     *
     * @return
     */
    @POST("/appservice/app/alluser/register")
    Observable<ResponseBean<String>> phoneregister(@Query("thirdAccount") String thirdAccount, @Query("identyType") int identyType,  @Query("loginType") int loginType, @Query("password") String password);
    /**
     * 第三方注册接口
     *
     * @return
     */
    @POST("/appservice/app/alluser/register")
    Observable<ResponseBean<String>> register(@Query("thirdAccount") String thirdAccount, @Query("identyType") int identyType, @Query("nickName") String nickName, @Query("headImg") String headImg, @Query("sex") String sex, @Query("loginType") int loginType, @Query("password") String password);
    /**
     * 登入接口
     *
     * @return
     */
    @POST("/appservice/app/alluser/login")
    Observable<ResponseBean<LoginBean>> login(@Query("thirdAccount") String thirdAccount, @Query("password") String password);
    /**
     * 修改密码接口
     *
     * @return
     */
    @POST("/appservice/app/alluser/modifyPassword")
    Observable<ResponseBean<LoginBean>> modifyPassword(@Query("thirdAccount") String thirdAccount, @Query("password") String password);
}

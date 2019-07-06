package fu.com.parttimejob.retrofitNet;

import fu.com.parttimejob.bean.GetLabelsBean;
import fu.com.parttimejob.bean.GetTokenEntity;
import fu.com.parttimejob.bean.LoginBean;
import fu.com.parttimejob.bean.RegisterBean;
import fu.com.parttimejob.bean.ResponseBean;
import fu.com.parttimejob.bean.WXInfoEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    Observable<ResponseBean<RegisterBean>> phoneregister(@Query("thirdAccount") String thirdAccount, @Query("identyType") int identyType, @Query("loginType") int loginType, @Query("password") String password);
    /**
     * 第三方注册接口
     *
     * @return
     */
    @POST("/appservice/app/alluser/register")
    Observable<ResponseBean<RegisterBean>> register(@Query("thirdAccount") String thirdAccount, @Query("identyType") int identyType, @Query("nickName") String nickName, @Query("headImg") String headImg, @Query("sex") String sex, @Query("loginType") int loginType, @Query("password") String password);


    /**
     * 获得access_token和openid
     *
     * @param appId
     * @param secret
     * @param code
     * @param authorization_code
     * @return
     */
    @GET("https://api.weixin.qq.com/sns/oauth2/access_token")
    Observable<GetTokenEntity> getToken(@Query("appid") String appId, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String authorization_code);

    /**
     * 微信獲取用戶信息
     * @param accessToken
     * @param openid
     * @return
     */
    @GET("https://api.weixin.qq.com/sns/userinfo")
    Observable<WXInfoEntity> getWXInfo(@Query("access_token") String accessToken, @Query("openid") String openid);
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

    /**
     * 获得标签
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/getLabel")
    Observable<ResponseBean<GetLabelsBean>> getLabel();
    /**
     * 创建简历
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/createPR")
    Observable<ResponseBean<String>> createPR(@Query("thirdAccount") String thirdAccount,@Query("name") String name, @Query("sex") String sex, @Query("age") String age, @Query("personalProfile") String personalProfile, @Body RequestBody file );
    /**
     * 设置标签
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/customizeLabel")
    Observable<ResponseBean<String>> customizeLabel(@Query("thirdAccount") String thirdAccount, @Query("labelName") String labelName);

    /**
     * 查询个人信息
     *
     * @return
     */
    @POST("/appservice/app/alluser/getUserInfo")
    Observable<ResponseBean<String>> getUserInfo(@Query("thirdAccount") String thirdAccount, @Query("identyType") String identyType, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("city") String city, @Query("token") String token);

}

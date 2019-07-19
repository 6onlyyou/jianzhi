package fu.com.parttimejob.retrofitNet;

import java.util.List;

import fu.com.parttimejob.bean.ExchangeBean;
import fu.com.parttimejob.bean.GetLabelsBean;
import fu.com.parttimejob.bean.GetTokenEntity;
import fu.com.parttimejob.bean.JobInfoBean;
import fu.com.parttimejob.bean.LoginBean;
import fu.com.parttimejob.bean.RegisterBean;
import fu.com.parttimejob.bean.ResponseBean;
import fu.com.parttimejob.bean.ResumeInfoBean;
import fu.com.parttimejob.bean.RunTokenBean;
import fu.com.parttimejob.bean.SameCityBean;
import fu.com.parttimejob.bean.UserInfoBean;
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
     * 获得融云token
     *
     * @return
     */
    @POST("/appservice/app/alluser/getCloudToken")
    Observable<ResponseBean<RunTokenBean>> getCloudToken(@Query("thirdAccount") String thirdAccount);
    /**
     * 查询个人信息
     *
     * @return
     */
    @POST("/appservice/app/alluser/queryUserInfo")
    Observable<ResponseBean<UserInfoBean>> getUserInfo(@Query("thirdAccount") String thirdAccount, @Query("identyType") int identyType, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("city") String city, @Query("token") String token);
    /**
     * 获取首页列表
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/firstPage")
    Observable<ResponseBean<List<JobInfoBean>>> firstPage(@Query("thirdAccount") String thirdAccount);
    /**
     * 获取兑换商品列表
     *
     * @return
     */
    @POST("/appservice/app/alluser/exchange")
    Observable<ResponseBean<List<ExchangeBean>>> exchange(@Query("thirdAccount") String thirdAccount);
    /**
     * 获得简历详情接口
     *
     * @return
     */
    @POST("/appservice/app/recruitment/getResumeInfo")
    Observable<ResponseBean<ResumeInfoBean>> getResumeInfo(@Query("thirdAccount") String thirdAccount, @Query("beViewedAccount") String beViewedAccount);

    /**
     * 获得同城招聘接口
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/sameCity")
    Observable<ResponseBean<List<SameCityBean>>> sameCity(@Query("thirdAccount") String thirdAccount, @Query("city") String city, @Query("label") String label);


    /**
     *随机获得一个招聘详情接口
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/randomGetOne")
    Observable<ResponseBean<SameCityBean>> randomGetOne(@Query("thirdAccount") String thirdAccount);

    /**
     *发布招聘订单
     *
     * @return
     */
    @POST("/appservice/app/recruitment/publichInfo")
    Observable<ResponseBean<String>> publichInfo(@Query("thirdAccount") String thirdAccount,@Query("companyName") String companyName,@Query("label") String label,@Query("numberOfVirtualCoins") String numberOfVirtualCoins,@Query("redEnvelopeNumber") String redEnvelopeNumber,@Query("recruitingNumbers") String recruitingNumbers,@Query("salaryAndWelfare") String salaryAndWelfare,@Query("phoneNumber") String phoneNumber,@Query("contactAddress") String contactAddress,@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("workContent") String workContent,@Query("businessLicenseImg") String businessLicenseImg,@Query("city") String city);
    /**
     *添加沟通历史
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/addCommunicationRecord")
    Observable<ResponseBean<String>> addCommunicationRecord(@Query("thirdAccount") String thirdAccount,@Query("recruitmentId") int recruitmentId);
    /**
     *沟通历史列表
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/queryCommunicationRecord")
    Observable<ResponseBean<List<SameCityBean>>> queryCommunicationRecord(@Query("thirdAccount") String thirdAccount);
    /**
     *人才管理列表
     *
     * @return
     */
    @POST("/appservice/app/recruitment/searchSameCity")
    Observable<ResponseBean<List<ResumeInfoBean>>> searchSameCity(@Query("thirdAccount") String thirdAccount,@Query("city") String city);

}

package fu.com.parttimejob.retrofitNet;

import java.util.List;

import fu.com.parttimejob.bean.AdInfoBean;
import fu.com.parttimejob.bean.AdvertisingInfoBean;
import fu.com.parttimejob.bean.ExchangeBean;
import fu.com.parttimejob.bean.GetLabelsBean;
import fu.com.parttimejob.bean.GetTokenEntity;
import fu.com.parttimejob.bean.JobInfoBean;
import fu.com.parttimejob.bean.KuaiDiBean;
import fu.com.parttimejob.bean.LoginBean;
import fu.com.parttimejob.bean.MAdvertisingBean;
import fu.com.parttimejob.bean.RecruitInfoBean;
import fu.com.parttimejob.bean.RegisterBean;
import fu.com.parttimejob.bean.ResponseBean;
import fu.com.parttimejob.bean.ResumeInfoBean;
import fu.com.parttimejob.bean.RunTokenBean;
import fu.com.parttimejob.bean.SameCityBean;
import fu.com.parttimejob.bean.UserInfoBean;
import fu.com.parttimejob.bean.WXInfoEntity;
import fu.com.parttimejob.bean.WXPayEntity;
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
    Observable<ResponseBean<RegisterBean>> register(@Query("thirdAccount") String thirdAccount, @Query("identyType") int identyType, @Query("nickName") String nickName, @Query("headImg") String headImg, @Query("sex") int sex, @Query("loginType") int loginType, @Query("password") String password);


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
     *
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
    Observable<ResponseBean<LoginBean>> login(@Query("thirdAccount") String thirdAccount, @Query("password") String password, @Query("identyType") int identyType);

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
    Observable<ResponseBean<String>> createPR(@Query("thirdAccount") String thirdAccount, @Query("name") String name, @Query("sex") int sex, @Query("age") String age, @Query("personalProfile") String personalProfile, @Body RequestBody file, @Query("city") String city);

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
    Observable<ResponseBean<List<JobInfoBean>>> sameCity(@Query("thirdAccount") String thirdAccount, @Query("city") String city, @Query("label") String label);


    /**
     * 随机获得一个招聘详情接口
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/randomGetOne")
    Observable<ResponseBean<SameCityBean>> randomGetOne(@Query("thirdAccount") String thirdAccount);

    /**
     * 随机获得一个广告接口
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/randomGetOneAdvertisement")
    Observable<ResponseBean<AdInfoBean>> randomGetOneAdvertisement(@Query("thirdAccount") String thirdAccount);

    /**
     * 发布招聘订单
     *
     * @return
     */
    @POST("/appservice/app/recruitment/publichInfo")
    Observable<ResponseBean<String>> publichInfo(@Query("thirdAccount") String thirdAccount, @Query("companyName") String companyName, @Query("label") String label, @Query("numberOfVirtualCoins") String numberOfVirtualCoins, @Query("redEnvelopeNumber") String redEnvelopeNumber, @Query("recruitingNumbers") String recruitingNumbers, @Query("salaryAndWelfare") String salaryAndWelfare, @Query("phoneNumber") String phoneNumber, @Query("contactAddress") String contactAddress, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("workContent") String workContent, @Body RequestBody file, @Query("city") String city, @Query("workTime") String workTime);

    /**
     * 发布招聘订单
     *
     * @return
     */
    @POST("/appservice/app/recruitment/publichInfo")
    Observable<ResponseBean<String>> publichNoImgInfo(@Query("thirdAccount") String thirdAccount, @Query("companyName") String companyName, @Query("label") String label, @Query("numberOfVirtualCoins") String numberOfVirtualCoins, @Query("redEnvelopeNumber") String redEnvelopeNumber, @Query("recruitingNumbers") String recruitingNumbers, @Query("salaryAndWelfare") String salaryAndWelfare, @Query("phoneNumber") String phoneNumber, @Query("contactAddress") String contactAddress, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("workContent") String workContent, @Query("city") String city, @Query("workTime") String workTime);

    /**
     * 添加沟通历史
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/addCommunicationRecord")
    Observable<ResponseBean<String>> addCommunicationRecord(@Query("thirdAccount") String thirdAccount, @Query("recruitmentId") int recruitmentId);

    /**
     * 沟通历史列表
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/queryCommunicationRecord")
    Observable<ResponseBean<List<JobInfoBean>>> queryCommunicationRecord(@Query("thirdAccount") String thirdAccount);

    /**
     * 人才管理列表
     *
     * @return
     */
    @POST("/appservice/app/recruitment/searchSameCity")
    Observable<ResponseBean<List<ResumeInfoBean>>> searchSameCity(@Query("thirdAccount") String thirdAccount, @Query("city") String city);

    /**
     * 发布广告
     *
     * @return
     */
    @POST("/appservice/app/recruitment/publichAdvertisement")
    Observable<ResponseBean<String>> publichAdvertisement(@Query("thirdAccount") String thirdAccount, @Query("companyName") String companyName, @Query("redEnvelopeNumber ") String redEnvelopeNumber, @Query("numberOfVirtualCoins") String numberOfVirtualCoins, @Query("city") String city, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("advertisementContent") String advertisementContent, @Body RequestBody file);

    /**
     * 招聘者名片创建
     *
     * @return
     */
    @POST("/appservice/app/recruitment/createCard")
    Observable<ResponseBean<String>> createCard(@Query("thirdAccount") String thirdAccount, @Body RequestBody file, @Query("name") String name, @Query("phoneNumber") String phoneNumber, @Query("companyName") String companyName);

    /**
     * 关闭招聘
     *
     * @return
     */
    @POST("/appservice/app/recruitment/closeRecruitmentInfo")
    Observable<ResponseBean<String>> closeRecruitmentInfo(@Query("thirdAccount") String thirdAccount, @Query("id") int id);


    /**
     * 关闭广告
     *
     * @return
     */
    @POST("/appservice/app/recruitment/closeAdvertisement")
    Observable<ResponseBean<String>> closeAdvertisement(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 打开广告
     *
     * @return
     */
    @POST("/appservice/app/recruitment/openAdvertisement")
    Observable<ResponseBean<String>> openAdvertisement(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 打开招聘
     *
     * @return
     */
    @POST("/appservice/app/recruitment/openRecruitmentInfo")
    Observable<ResponseBean<String>> openAdvertisementInfo(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 我的广告活动列表
     *
     * @return
     */
    @POST("/appservice/app/recruitment/myAdvertisingCampaignList")
    Observable<ResponseBean<List<MAdvertisingBean>>> myAdvertisingCampaignList(@Query("thirdAccount") String thirdAccount);


    /**
     * 我的招聘列表
     *
     * @return
     */
    @POST("/appservice/app/recruitment/myRecruitmentList")
    Observable<ResponseBean<List<JobInfoBean>>> myRecruitmentList(@Query("thirdAccount") String thirdAccount);


    /**
     * 增加广告转发次数
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/addNumberOfAdvertisingForwarding")
    Observable<ResponseBean<String>> addNumberOfAdvertisingForwarding(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 增加广告查看次数
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/addNumberOfAdvertisingView")
    Observable<ResponseBean<String>> addNumberOfAdvertisingView(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 增加招聘转发次数
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/addNumberOfRecruitForwarding")
    Observable<ResponseBean<String>> addNumberOfRecruitForwarding(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 增加招聘查看次数
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/addNumberOfRecruitView")
    Observable<ResponseBean<String>> addNumberOfRecruitView(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 广告详情
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/singleAdDetail")
    Observable<ResponseBean<AdvertisingInfoBean>> singleAdDetail(@Query("thirdAccount") String thirdAccount, @Query("id") int id);


    /**
     * 招聘详情接口
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/singleRecruitmentDetail")
    Observable<ResponseBean<RecruitInfoBean>> singleRecruitmentDetail(@Query("thirdAccount") String thirdAccount, @Query("id") int id);


    /**
     * 领取广告虚拟币接口
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/receiveOfAdVirtual")
    Observable<ResponseBean<String>> receiveOfAdVirtual(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 领取招聘虚拟币接口
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/receiveOfRecruitmentVirtual")
    Observable<ResponseBean<String>> receiveOfRecruitmentVirtual(@Query("thirdAccount") String thirdAccount, @Query("id") int id);

    /**
     * 领取招聘虚拟币接口
     *
     * @return
     */
    @POST("/appservice/app/alluser/bindPhoneNum")
    Observable<ResponseBean<String>> bindPhoneNum(@Query("thirdAccount") String thirdAccount, @Query("phoneNum") String phoneNum);

    /**
     * 填写邀请码接口
     *
     * @return
     */
    @POST("/appservice/app/alluser/inputInvit")
    Observable<ResponseBean<String>> inputInvit(@Query("thirdAccount") String thirdAccount, @Query("inviteCode") String inviteCode);


    /**
     * 微信支付
     *
     * @return
     */
    @POST("/appservice/app/wechatpay/wechat")
    Observable<ResponseBean<WXPayEntity>> wxPay(@Query("thirdAccount") String thirdAccount, @Query("totalPrice") int totalPrice, @Query("desc") String desc, @Query("detail") String detail, @Query("spbill_create_ip") String spbill_create_ip);

    /**
     * 支付宝支付
     *
     * @return
     */
    @POST("/appservice/app/pay/alipay")
    Observable<ResponseBean<String>> alipay(@Query("thirdAccount") String thirdAccount, @Query("totalPrice") int totalPrice, @Query("desc") String desc, @Query("detail") String detail);

    /**
     * 兑换商品
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/exchangeGoods")
    Observable<ResponseBean<String>> exchangeGoods(@Query("thirdAccount") String thirdAccount, @Query("goodsId") String goodsId, @Query("address") String address, @Query("phoneNumber") String phoneNumber, @Query("name") String name);


    /**
     * 用户已经兑换列表
     *
     * @return
     */
    @POST("/appservice/app/jobhunter/changeList")
    Observable<ResponseBean<List<KuaiDiBean>>> changeList(@Query("thirdAccount") String thirdAccount);


}

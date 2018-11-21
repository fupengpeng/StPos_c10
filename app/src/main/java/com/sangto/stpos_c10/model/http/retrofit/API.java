package com.sangto.stpos_c10.model.http.retrofit;

import com.sangto.stpos_c10.bean.Result;
import com.sangto.stpos_c10.bean.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 *  url 地址
 */
public interface API {

    @GET
    Observable<String> getUrlContent(@Url String url);

//    /*相关推荐*/
////    @GET("api/portal/lists/getCategoryPostLists")
////    Call<Result<TuiJianList>> getCategoryPostLists(@QueryMap Map<String,String> params);
//   /* Observable<Result<TuiJianList>> getCategoryPostLists(
//            @Field("my_id") int my_id,
//            @Field("field") String field,
//            @Field("limit") int limit,
//            @Field("order") String order, // 极光推送id
//            @Field("my_where") String my_where,
//            @Field("device_type") String model //登录设备
//    );*/
//
    /*登录*/
    @FormUrlEncoded
    @POST("api/user/public/login")
    Observable<Result<User>> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("device_type") String model, //登录设备
            @Field("version_number") String version_number,
            @Field("registrationid") String registrationid // 极光推送id
    );
//
//    /*预约首页数据*/
//    @FormUrlEncoded
//    @POST("api/home/reserve/index")
//    Observable<Result<IndexInfo.DataBean>> index(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String model, //登录设备
//            @Field("version_number") String version_number,
//            @Field("school_id") String school_id,
//            @Field("subject") String subject
//
//    );
//
//    /*公告*/
//    @FormUrlEncoded
//    @POST("api/portal/announcement/announcement_list")
//    Observable<Result<Notice>> notice(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("page") String page,
//            @Field("device_type") String model, //登录设备
//            @Field("school_id") String school_id,
//            @Field("version_number") String version_number
//    );
//
//    /*找回密码*/
//    @FormUrlEncoded
//    @POST("api/user/public/passwordReset")
//    Observable<Result<ErrorBean>> passwordReset(
//            @Field("username") String username,
//            @Field("password") String password,
//            @Field("verification_code") String code,
//            @Field("version_number") String version_number
//    );
//
//    /*更改密码*/
//    @FormUrlEncoded
//    @POST("api/user/profile/changePassword")
//    Observable<Result<ErrorBean>> changePassword(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("old_password") String old_password,
//            @Field("password") String password,
//            @Field("confirm_password") String confirm_password
//
//    );
//
//    /*意见反馈*/
//    @FormUrlEncoded
//    @POST("api/user/Feedback/send")
//    Observable<Result<ErrorBean>> sendOpinion(
//            @Field("user_id") int user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("feedback") String feedback,//意见内容
//            @Field("contact") String contact//联系方式
//
//    );
//
//    /*获取城市列表*/
//    @FormUrlEncoded
//    @POST("api/user/Region/sel_region")
//    Observable<Result<City>> getCityList(
//            @Field("pid") String city,
//            @Field("version_number") String version_number
//    );
//
//    /*   更改头像 */
////    @FormUrlEncoded
//    @Multipart
//    @POST("api/user/upload/one")
//    Observable<Result<ErrorBean>> uploadPhoto(
//            @Part("user_id") String user_id,
//            @Part("token") String token,
//            @Part("device_type") String device_type,
//            @Part("version_number") String version_number,
//            @Part MultipartBody.Part file
//    );
//
//    /*实名认证*/
//    @FormUrlEncoded
//    @POST("api/user/Certification/attestation")
//    Observable<Result<ErrorBean>> realName(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("realname") String real_name,
//            @Field("driving_type") String driving_type,
//            @Field("idcard") String idcard
//    );
//
//    /*修改资料*/
//    @FormUrlEncoded
//    @POST("api/user/profile/userInfo")
//    Observable<Result<ErrorBean>> changeData(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("user_nickname") String user_nickname,
//            @Field("avatar") String avatar,
//            @Field("address") String address,
//            @Field("age") String age,
//            @Field("sex") String sex
//    );
//
//    /*获取验证码*/
//    @FormUrlEncoded
//    @POST("api/user/Verificationcode/send")
//    Observable<Result<ErrorBean>> getPhoneCode(
//            @Field("username") String phonenum,
//            @Field("version_number") String version_number,
//            @Field("templatecode") int templatecode//1注册 2修改密码
//    );
//
//    /*获取订单详情信息*/
//    @FormUrlEncoded
//    @POST("api/home/reserve/pay")
//    Observable<Result<PayInfo>> getPayInfo(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("appointment_id") String appointment_id
//    );
//
//    /*支付*/
//    @FormUrlEncoded
//    @POST("api/home/wxpay/pay")
//    Observable<Result<Pay>> pay(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("pay_way") String pay_way,
//            @Field("appointment_id") String appointment_id
//    );
//
//    /*取消预约*/
//    @FormUrlEncoded
//    @POST("api/home/reserve/removeAppo")
//    Observable<Result<ErrorBean>> canclePay(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("appointment_id") String appointment_id
//    );
//
//    /*订单列表*/
//    @FormUrlEncoded
//    @POST("api/home/reserve/orderList")
//    Observable<Result<List<OrderList>>> getOrderList(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("pay") String pay
//    );
//
//    /*练车报告*/
//    @FormUrlEncoded
//    @POST("api/test/result")
//    Observable<Result<List<BaoGaoList>>> result(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number
//    );
//
//    /*提交订单*/
//    @FormUrlEncoded
//    @POST("api/home/reserve/reserve")
//    Observable<Result<OrderId>> commitYuYue(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("appointment_time") String appointment_time,//（类型格式：2018-03-22）
//            @Field("time_type") String time_type,//(上午还是下午   1 ---上午  ；  2----下午)
//            @Field("appointment_hour[]") Object[] appointment_hour,
//            @Field("appointment_type") int appointment_type,
//            @Field("appointment_subject") String appointment_subject,
//            @Field("appointment_price") String appointment_price,
//            @Field("appointment_number") String appointment_number,
//            @Field("school_id") String school_id,
//            @Field("need_price") String need_price
//    );
//
//    /*获取城市列表*/
//    @FormUrlEncoded
//    @POST("api/user/public/register")
//    Observable<Result<ErrorBean>> regist(
//            @Field("username") String phonenum,
//            @Field("password") String password,
//            @Field("version_number") String version_number,
//            @Field("verification_code") String code
//    );
//
//    /*微信支付回调信息*/
//    @FormUrlEncoded
//    @POST("api/home/wxpay/confirm")
//    Observable<Result<ErrorBean>> confirm(
//            @Field("user_id") String user_id,
//            @Field("token") String token,
//            @Field("device_type") String device_type,
//            @Field("version_number") String version_number,
//            @Field("appointment_id") String appointment_id
//    );
//
//    /*轮播图*/
//    @FormUrlEncoded
//    @POST("api/home/lists/ad")
//    Observable<Result<List<MainBanner>>> getBanner(
//            @Field("device_type") String device_type
//    );
//
//    /*驾校详情*/
//    @FormUrlEncoded
//    @POST("api/home/lists/schoolInfo")
//    Observable<Result<SchoolDetils>> getSchoolDetils(
//            @Field("device_type") String device_type,
//            @Field("school_id") String school_id
//    );
//
//    /*陪练列表*/
//    @FormUrlEncoded
//    @POST("api/user/coach/index")
//    Observable<Result<PeiLianInfo>> getPeiLianList(
//            @Field("city") String city,
//            @Field("per_page") String per_page
//    );
//
//    /*取消预约*/
//    @FormUrlEncoded
//    @POST("api/user/coach/info")
//    Observable<Result<JiaoLianDetBean>> PeiLianInfo(
//            @Field("id") String id
//    );
//      /*   更改头像 */
//   /* @Multipart
//    @POST("userinterface/uploadPhoto")
//    Observable<Result<String>> uploadPhoto(
//            @Part("userid") String userid,
//            @Part("type") String type,
//            @Part("token") String token,
//            @Part MultipartBody.Part file
//    );*/
//
//
//
//
//
//
//
//
//    /*退出登录*/
//    /*个人中心修改手机、密码、忘记密码 获取手机验证码接口*/
//   /* @FormUrlEncoded
//    @POST("userinterface/loginOut")
//    Observable<Result<UserInfo>> loginOut(
//            @Field("userid") String userid,
//            @Field("token") String token,
//            @Field("type") String type);
//*/
//
//    // TODO: 2018/6/22 -------------------------------------------------------------------------------------
//
//    /**
//     * 检测是否更新apk
//     *
//     * @param device_type
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/home/lists/version")
//    Observable<Result<CheckUpdateResponse.CheckUpdateBean>> updateApk(
//            @Field("device_type") String device_type
//            , @Field("version_number") String version_number
//    );
//
//
//    /**
//     * 章节
//     *
//     * @param type
//     * @param subject
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("examination/ExaminationApi/chapter_list")
//    Observable<Result<List<SectionBean>>> section(
//            @Field("type") String type,
//            @Field("subject") String subject
//    );
//
//
//    /**
//     * @param category_id 7-科目二，8-科目三，9-拿证
//     * @param field       post.id,post.post_title,post.more
//     * @param limit       查几条		（当page不为空时，limit失效）
//     *                    分页	   	（格式1,2  逗号前面是第几页，后面是每页显示多少个）
//     * @param order       排序		（+为正序，-为倒叙）
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/portal/lists/getCategoryPostLists")
//    Observable<Result<CourseResponse>> courseTop(
//            @Field("category_id") String category_id
//            , @Field("field") String field
//            , @Field("limit") String limit
//            , @Field("order") String order
//    );
//
//    /**
//     * @param category_id 7-科目二，8-科目三，9-拿证
//     * @param field       post.id,post.post_title,post.more
//     * @param page        3,1     <第几页，一页多少个>
//     * @param order       -id
//     * @param my_where    recommended,neq,1
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/portal/lists/getCategoryPostLists")
//    Observable<Result<CourseResponse>> courseBottom(
//            @Field("category_id") String category_id
//            , @Field("field") String field
//            , @Field("page") String page
//            , @Field("order") String order
//            , @Field("my_where") String my_where
//    );
//    /**
//     * 语音下载列表
//     */
//    @FormUrlEncoded
//    @POST("api/portal/voice/index")
//    Observable<Result<VoiceListBean>> getVoiceList(
//            @Field("city") String city,
//            @Field("device_type") String model
//    );
//
//    /**
//     * 评论列表
//     * @param id  文章id
//     * @param page  第几页
//     * @return  文章分页数据
//     */
//    @FormUrlEncoded
//    @POST("/api/portal/articles/comment_list")
//    Observable<Result<CommentListResponse.CommentPageData>> commentList(
//            @Field("id") int id
//            , @Field("page") int page
//    );

}

package com.sangto.stpos_c10.model.http.retrofit;



import com.sangto.stpos_c10.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class APIClient {
    public static final String URL = "https://yun.xiaojiangjiakao.com/";
    private static API api;
    private APIClient() {
        //no instance
    }
    /**
     * @return 客户端实例
     */
    public static API getInstance() {


        if (api == null) {
            // 拦截打印
          /*  HttpLoggingInterceptor  interceptor = new HttpLoggingInterceptor(message -> {
                try {
                    StringReader reader = new StringReader(message);
                    Properties properties = new Properties();
                    properties.load(reader);
                    LogUtil.d( properties.toString());
                } catch (IOException e) {
                    LogUtil.e("打印信息时 出错了");
                }
            });*/
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    // 拦截 信息 并打印出来
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(interceptor);
            }
            // 创建接口
            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    // 对字符串的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    // gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //  rxjava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            api = retrofit.create(API.class);
        }
        return api;
    }
}

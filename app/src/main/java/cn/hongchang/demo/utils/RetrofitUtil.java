package cn.hongchang.demo.utils;

import cn.hongchang.demo.RetrofitService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private RetrofitService retrofitService;

    private RetrofitUtil(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitService = mRetrofit.create(RetrofitService.class);
    }
    public static RetrofitUtil getInstance(){
        if (sInstance == null){
            synchronized(RetrofitUtil.class){
                if (sInstance == null){
                    sInstance = new RetrofitUtil();
                }
            }
        }
        return sInstance;
    }
    public RetrofitService getRetrofitService(){
        return retrofitService;
    }
}

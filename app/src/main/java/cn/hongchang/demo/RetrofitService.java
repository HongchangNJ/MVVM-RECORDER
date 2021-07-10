package cn.hongchang.demo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import cn.hongchang.demo.entity.LoginResult;
/**
 * @author Bob
 */
public interface RetrofitService {

    /**
     * 地址是测试地址，外网ping不通
     */
    String HOST = "http://10.6.152.9:8091/";

    /**
     * 登录</p>
     * 作者：Bob</p>
     * 日期：2020-03-07</p>
     *
     * @param body {"loginName":"111","loginPass":"123456"}
     * @return 是否登录成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("user/login")
    Observable<LoginResult> appLogin(@Body RequestBody body);

}

package cn.hongchang.demo.model;

import com.google.gson.Gson;

import cn.hongchang.demo.RequestBodyEntity;
import cn.hongchang.demo.entity.LoginResult;
import cn.hongchang.demo.utils.INetWorkCallback;
import cn.hongchang.demo.utils.RetrofitUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginModel {

    public void appLogin(final String loginName, final String pass, final INetWorkCallback<LoginResult> callback) {

        final RequestBodyEntity.Login loginRequest = new RequestBodyEntity.Login();
        loginRequest.loginName = loginName;
        loginRequest.loginPass = pass;
        final Gson gson = new Gson();
        String gsonStr = gson.toJson(loginRequest);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gsonStr);

        RetrofitUtil.getInstance().getRetrofitService()
                .appLogin(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginResult>() {
                    @Override
                    public void onNext(LoginResult loginResult) {
                        callback.onCallApiSuccess(loginResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onCallApiFailure(e);
                    }

                    @Override
                    public void onComplete() {
                        callback.onCompleted();
                    }
                });
    }
}

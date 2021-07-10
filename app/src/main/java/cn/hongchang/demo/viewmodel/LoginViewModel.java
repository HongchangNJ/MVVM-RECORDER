package cn.hongchang.demo.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import cn.hongchang.demo.entity.UserInfo;
import cn.hongchang.demo.model.LoginModel;


public class LoginViewModel extends ViewModel {

    public UserInfo userInfo;
    private static final String TAG = "LoginViewModel";
    private LoginModel model;

    public LoginViewModel() {
        userInfo = new UserInfo();
        model = new LoginModel();
    }

    public View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 尝试1：view --> model单向绑定测试，改变EditText的值，查看bean中的对应属性值是否发生变化。
            //Log.e("hongchang", userInfo.name.get() + "--" + userInfo.pwd.get());

            // 尝试2：model --> view单向绑定测试，Model层属性的变更，也会改变View层的显示
            userInfo.name.set("Owen");
            userInfo.pwd.set("0410");

            /*model.appLogin(userInfo.name.get(), userInfo.pwd.get(), new INetWorkCallback<LoginResult>() {
                @Override
                public void onCallApiSuccess(LoginResult loginResult) {

                    android.util.Log.e(TAG, "---onCallApiSuccess---");
                }

                @Override
                public void onCallApiFailure(Throwable throwable) {
                    android.util.Log.e(TAG, "---onCallApiFailure---");
                }

                @Override
                public void onCompleted() {
                    android.util.Log.e(TAG, "---onCompleted---");
                }
            });*/
        }
    };
}
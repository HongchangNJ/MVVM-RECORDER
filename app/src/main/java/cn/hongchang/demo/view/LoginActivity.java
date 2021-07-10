package cn.hongchang.demo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import cn.hongchang.demo.R;
import cn.hongchang.demo.databinding.LoginActivityBinding;
import cn.hongchang.demo.viewmodel.LoginViewModel;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    LoginActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.login_activity);
        binding.setLoginViewModel(new LoginViewModel());
    }
}
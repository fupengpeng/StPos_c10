package com.sangto.stpos_c10.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.sangto.stpos_c10.R;
import com.sangto.stpos_c10.bean.User;
import com.sangto.stpos_c10.interf.LoginContract;
import com.sangto.stpos_c10.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author fpp
 * @Description:
 * @date 2018/11/19 9:38
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {


    @BindView(R.id.et_aty_login_account)
    EditText etAtyLoginAccount;
    @BindView(R.id.et_aty_login_password)
    EditText etAtyLoginPassword;

    @Inject
    LoginPresenter mLoginPresenter;

//    @Inject
    public LoginActivity() {
        DaggerLoginActivityComponent.create().injectLoginActivity(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // 初始化数据
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
//        DaggerLoginActivityComponent.create().injectLoginActivity(this);
    }

    /**
     * 登录成功
     *
     * @param user 用户
     */
    @Override
    public void loginSuccess(User user) {
        Toast.makeText(this, user.getUsername() + "登录成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 失败
     *
     * @param msg 失败信息
     */
    @Override
    public void error(String msg) {
        Toast.makeText(this, "登录失败！" + msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录点击事件处理
     */
    @OnClick(R.id.btn_aty_login)
    public void onViewClicked() {

        mLoginPresenter.login(etAtyLoginAccount.getText().toString()
                , etAtyLoginPassword.getText().toString());
    }
}

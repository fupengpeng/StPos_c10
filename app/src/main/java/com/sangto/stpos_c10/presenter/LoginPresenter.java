package com.sangto.stpos_c10.presenter;

import com.sangto.stpos_c10.bean.User;
import com.sangto.stpos_c10.interf.Login;
import com.sangto.stpos_c10.ui.LoginActivity;


/**
 * @author fpp
 * @Description:
 * @date 2018/11/19 9:43
 */

public class LoginPresenter implements Login.Presenter {

    private LoginActivity mLoginActivity;

    public LoginPresenter(LoginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
    }


    /**
     * 登录
     *
     * @param account  账号
     * @param password 密码
     */
    @Override
    public void login(String account, String password) {
        if ("123456".equals(account) && "000000".equals(password)) {
            User user = new User();
            user.setId(1L);
            user.setAddress("北京市西城区");
            user.setUsername("张三");
            // 登录成功响应
            mLoginActivity.loginSuccess(user);
        } else {
            // 失败响应
            mLoginActivity.error("登录失败!");
        }

    }
}

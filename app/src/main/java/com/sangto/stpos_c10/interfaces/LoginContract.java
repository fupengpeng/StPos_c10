package com.sangto.stpos_c10.interfaces;

import com.sangto.stpos_c10.bean.User;

/**
 * @author fpp
 * @Description:
 * @date 2018/11/19 9:42
 */

public interface LoginContract {

    interface View {

        /**
         * 登录成功
         *
         * @param user 用户
         */
        void loginSuccess(User user);

        /**
         * 失败
         *
         * @param msg 失败信息
         */
        void error(String msg);

    }


    interface Presenter {

        /**
         * 登录
         *
         * @param account  账号
         * @param password 密码
         */
        void login(String account, String password);

    }


}

package com.sangto.stpos_c10.utils;


import com.sangto.stpos_c10.bean.User;

/**
 * 角色工具类
 */
public class RoleUitl {
    private static RoleUitl roleUitl = null;
    public int temp=-1;
    private RoleUitl() {

    }
    public static RoleUitl getInstance() {
        if (roleUitl == null) {
            roleUitl = new RoleUitl();
        }
        return roleUitl;
    }
    /**
     * 保存用户信息
     * @param user
     */
    public void saveUserInfo(final User user) {
        roleUitl=null;
        SPUtils.setObject(Constant.USER_INFO, user);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public User getUserInfo() {
        return SPUtils.getObject(Constant.USER_INFO, User.class);
    }
    public void logOut() {
        SPUtils.remove(Constant.USER_INFO);
    }
}
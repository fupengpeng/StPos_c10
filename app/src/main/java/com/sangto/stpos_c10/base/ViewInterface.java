package com.sangto.stpos_c10.base;


import com.sangto.stpos_c10.bean.Result;

import rx.Subscription;


public interface ViewInterface<T> {
    /**
     * 布局资源id
     *
     * @return 资源id
     */
    int getViewResourceId();

    /***
     * 初始化view
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 处理这个
     *
     * @param result
     */
    void initResultData(Result result);

    /**
     * 隐藏加载进度提示
     */
    void hideProgressDialog();

    /**
     * 显示加载进度提示
     */
    void showProgressDialog();

    void addSubcrib(Subscription s);

    void initResultDataString(String result);

}

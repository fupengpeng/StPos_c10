package com.sangto.stpos_c10.base;

/**
 *  MVP   view层
 *
 */
public interface BaseView<T> {
    /**
     * @param basePresenter  获取到P层的引用
     */
    void setPresenter(T basePresenter);
}

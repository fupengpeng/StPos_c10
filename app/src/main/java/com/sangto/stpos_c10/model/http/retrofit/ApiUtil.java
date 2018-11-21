package com.sangto.stpos_c10.model.http.retrofit;


import com.sangto.stpos_c10.base.BaseActivity;
import com.sangto.stpos_c10.base.BaseFragment;
import com.sangto.stpos_c10.base.ViewInterface;
import com.sangto.stpos_c10.bean.Result;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  请求网络数据 工具栏
 */
public class ApiUtil {
    /**
     * @param o    activity  or  fragment  现实的接口
     * @param observable       网络请求生成的  observable
     * @param resultSubscriber 回调
     * @param <T>              返回值得泛型
     */
    public static <T> void getData(Object o, Observable<Result<T>> observable, ResultSubscriber<Result<T>> resultSubscriber) {
        ViewInterface viewInterface=null;
        if (o!=null&&(o instanceof BaseActivity ||   o instanceof BaseFragment)) {
            viewInterface= (ViewInterface) o;
        }
        final ViewInterface finalViewInterface = viewInterface;
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    if (finalViewInterface != null) {
                        finalViewInterface.showProgressDialog();
                    }
                })
                .doAfterTerminate(() -> {
                    if (finalViewInterface != null) {
                        finalViewInterface.hideProgressDialog();
                    }
                })
                .subscribe(resultSubscriber);
    }
}

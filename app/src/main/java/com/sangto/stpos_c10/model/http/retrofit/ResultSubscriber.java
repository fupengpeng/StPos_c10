package com.sangto.stpos_c10.model.http.retrofit;

import android.content.Intent;
import android.text.TextUtils;


import com.sangto.stpos_c10.BuildConfig;
import com.sangto.stpos_c10.app.App;
import com.sangto.stpos_c10.bean.Result;
import com.sangto.stpos_c10.utils.UIUtil;

import org.xutils.common.util.LogUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 结果回调
 */
public abstract class ResultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        try {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(e.getMessage())) {
                return;
            }
            LogUtil.e("===ResultSubscriber===onError=====:" + e.getMessage());
            String msg = "";
            if (e instanceof ConnectException) {
                msg = "网络未连接";
                UIUtil.toast(msg);
            } else if (e instanceof SocketTimeoutException) {
                msg = "网络连接超时，请稍后再试";
                UIUtil.toast(msg);
            } else {
//                msg = e.getMessage();
                //TODO 发布的时候打开
                msg = "网络连接超时，请稍后再试";
                LogUtil.e(e.getMessage());
            }
            if (!UIUtil.isEmpty(msg)) {
                UIUtil.toast(msg);   //                msg = e.getMessage();
            }
        } catch (Exception ee) {

        }
    }

    @Override
    public void onNext(T o) {
        int code = ((Result) o).code;
        switch (code) {
            case 1: //成功
                initOkData(o);
                break;
            case 10001://token失效
                Intent intent = new Intent();
                intent.setAction("EXIT_APP");
                App.getContext().sendBroadcast(intent);
                break;
            case 0:  // 其他错误结果
                UIUtil.toast(((Result) o).msg);
                LogUtil.e("===ResultSubscriber===onNext=====:" + ((Result) o).msg);
                break;
            case 10002://强制更新
//                UIUtil.updataVersion(((Result) o).msg);
                break;
        }
    }

    protected abstract void initOkData(T data);
}

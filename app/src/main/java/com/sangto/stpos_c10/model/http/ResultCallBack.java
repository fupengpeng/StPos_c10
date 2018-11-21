package com.sangto.stpos_c10.model.http;

import android.content.Intent;
import android.text.TextUtils;


import com.sangto.stpos_c10.app.App;
import com.sangto.stpos_c10.base.ViewInterface;
import com.sangto.stpos_c10.utils.UIUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;

import java.net.SocketTimeoutException;

/**
 * 网络请求回调
 */
public class ResultCallBack implements Callback.CommonCallback<String> {
    private final boolean needProgressDialog;// 加载进度条
    private final ViewInterface netCallBack;    // 有了结果 用户 的处理的回调
    private String url;
    private String result;

    public ResultCallBack(boolean needProgressDialog, ViewInterface netCallBack, String url) {

        this.needProgressDialog = needProgressDialog;
        this.netCallBack = netCallBack;
        this.url = url;
        if (needProgressDialog) {
            this.netCallBack.showProgressDialog();
        }
    }

    @Override
    public void onSuccess(String result) {
        // 解析数据并回调
        LogUtil.d("======result====" + result);
//        Result dataResult = JSON.parseObject(result, netCallBack.getResultClass()==null? Result.class:netCallBack.getResultClass());
//        netCallBack.initResultData(dataResult);
        this.result = result;
        netCallBack.initResultDataString(this.result);
    }

    private boolean isEquals(String b) {
        return TextUtils.equals(url, b);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        String message = ex.getMessage();
        try {
            switch (((HttpException) ex).getCode()) {
                case 500:
                    message = "服务器异常";
                    UIUtil.toast(message);
                    break;
            }
        } catch (Exception e) {
            if (ex instanceof SocketTimeoutException) {
                message = "网络连接超时";
                UIUtil.toast(message);
            } else if (isOnCallback) {
                if (result.contains("10001")) {
                    Intent intent = new Intent();
                    intent.setAction("EXIT_APP");
                    App.getContext().sendBroadcast(intent);
                } else {
                    message = "解析错误";
                    LogUtil.e(message);
                }
            } else if (message.contains("ENETUNREACH")) {
                message = "网络连接不可用";
                UIUtil.toast(message);
            } else if (message.contains("ECONNREFUSED")) {
                message = "服务器不可用";
                UIUtil.toast(message);
            }else {
                //TODO 发布的时候打开
                message = "网络连接超时，请稍后再试";
//                message = e.getMessage();
                UIUtil.toast(message);
            }
        }
        LogUtil.e("===xutil===onError=====:"+message);
    }
    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {
        if (needProgressDialog) {
            netCallBack.hideProgressDialog();
        }
    }
}
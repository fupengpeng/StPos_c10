package com.sangto.stpos_c10.model.http;

import android.widget.ImageView;


import com.sangto.stpos_c10.base.ViewInterface;

import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.Map;
import java.util.Set;

/**
 * xutils的网络请求
 */
public class xutilProtocol {
    private static xutilProtocol protocol = new xutilProtocol();

    private xutilProtocol() {
        //no instance
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static xutilProtocol getInstance() {
        return protocol;
    }

    public void bingImage(ImageView imageView, String address, Object o) {
        x.image().bind(imageView, address, (ImageOptions) o);
    }

    public void bingImage(final ImageView imageView, final String address) {
        bingImage(imageView, address, null);
    }

    /**
     * 请求网络
     *
     * @param url                url地址
     * @param map                参数
     * @param netCallBack        结果集回调
     * @param needProgressDialog 是否需要加载提示
     */
    public void post(String url, Map map, ViewInterface netCallBack, boolean needProgressDialog) {
        //
        if (map == null) {
            return;
        }
        System.out.println("======请求参数====" + map);
        RequestParams requestParams = new RequestParams(url);
         // 普通的请求
            Set<String> set = map.keySet();
            // 拼接参数
            for (String key : set) {
                requestParams.addBodyParameter(key, ((String) map.get(key)));
            }

      x.http().post(requestParams, new ResultCallBack(needProgressDialog, netCallBack, url));
    }
    /**
     * 请求网络
     *
     * @param url                url地址
     * @param map                参数
     * @param netCallBack        结果集回调
     */
    public void post(String url, Map map, ViewInterface netCallBack) {
      post(url, map, netCallBack,true);
    }


}

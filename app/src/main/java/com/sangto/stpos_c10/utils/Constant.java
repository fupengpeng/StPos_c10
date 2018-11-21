package com.sangto.stpos_c10.utils;

import android.os.Environment;


import com.sangto.stpos_c10.app.App;


public class Constant {

    /**
     * 文件下载基路径
     */
    public static final String DOWNLOAD_BASE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/";

    public static final String DOWNLOAD_CACHE_BASE_PATH = App.getContext().getCacheDir().getAbsolutePath() + "/";


    public static final String REFRESH = "REFRESH";  // 刷新
    public static final String LOAD_MORE = "LOAD_MORE";  // 加载更多
    public static final String INIT = "INIT";  // 初始化

    public static final int NONE = 0x1000; //无状态
    public static final int START = 0x1001; //准备下载
    public static final int PROGRESS = 0x1002; //下载中
    public static final int PAUSE = 0x1003; //暂停
    public static final int RESUME = 0x1004; //继续下载
    public static final int CANCEL = 0x1005; //取消
    public static final int RESTART = 0x1006; //重新下载
    public static final int FINISH = 0x1007; //下载完成
    public static final int ERROR = 0x1008; //下载出错
    public static final int WAIT = 0x1009; //等待中
    public static final int DESTROY = 0x1010; //释放资源


    public static final String TEXT_NORMAL = "TEXT_NORMAL";//标准字体
    public static final String TEXT_BIG = "TEXT_BIG";//大号字体
    public static final String TEXT_LARGE_BIG = "TEXT_LARGE_BIG";//特大字体


    /**
     * 用户信息
     */
    public static final String USER_INFO = "USER_INFO";

    //退出
    public static String EXIT_APP = "EXIT_APP";



}

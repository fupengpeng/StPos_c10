package com.sangto.stpos_c10.app;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.sangto.stpos_c10.greendao.DaoMaster;
import com.sangto.stpos_c10.greendao.DaoSession;
import com.sangto.stpos_c10.model.db.DbManager;
import com.sangto.stpos_c10.utils.FileUtils;
import com.sangto.stpos_c10.utils.UIUtil;


import com.vondear.rxtools.RxAppTool;
import com.vondear.rxtools.RxTool;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 主要初始化对象以及捕获异常
 */
public class App extends MultiDexApplication implements Thread.UncaughtExceptionHandler {

    private static Context context;
    private static Toast mToast;
    // 收集日志信息
    private static Map<String, String> infos = new HashMap<>();
    private final String TAG = this.getClass().getSimpleName();
    // 用于格式化日期,作为日志文件名的一部分

    // greenDao  session
    public static DaoSession daoSession;

    // 题目更新动画是否显示
    public static boolean showUpdateAnim = true;

    /**
     * 标识网络是否连接
     */
    private boolean isNetConnected;

    /**
     * Application实例
     */
    private static App instance;

    /**
     * 全局的context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
//      Thread.setDefaultUncaughtExceptionHandler(this);
        context = getApplicationContext();
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        }

        UIUtil.init(context, mToast);
        RxTool.init(this);

        // 初始化数据库
        initDatabase();
        super.onCreate();
        // 初始化实例
        instance = this;


    }


    /**
     * 获取Application实例
     *
     * @return Application实例
     */
    public static App getInstance() {
        return instance;
    }


    private void initDatabase() {
        //初始化数据库
        daoSession = DbManager.getDaoSession(context);

    }


    /**
     * 处理异常
     *
     * @param thread 当前线程
     * @param ex     异常
     */
//
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 收集设备参数信息
        collectDeviceInfo();

    }



    private void collectDeviceInfo() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            // 版本号
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";

                infos.put("========================================================", "\n\n\n\n\n\n\n\n\n");
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
                infos.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

    }


    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public int getAppSatus(Context context, String pageName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }

}


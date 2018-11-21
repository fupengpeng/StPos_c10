package com.sangto.stpos_c10.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sangto.stpos_c10.R;
import com.sangto.stpos_c10.bean.Result;
import com.sangto.stpos_c10.model.http.xutilProtocol;
import com.sangto.stpos_c10.ui.LoginActivity;
import com.sangto.stpos_c10.utils.AppUtils;
import com.sangto.stpos_c10.utils.Constant;
import com.sangto.stpos_c10.utils.RoleUitl;
import com.sangto.stpos_c10.view.TitleTab;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 *  activity基类
 * 1. 初始头布局
 * 2. 加载/隐藏进度条
 * 3. 网络加载并回调
 */
public abstract class BaseActivity extends AppCompatActivity implements ViewInterface {
    protected Map map = new HashMap();
    protected CompositeSubscription s;
    // 网络请求时的加载进度条
    private ProgressDialog dialog;
    private ImageView back;  //返回键
    private ImageView back2;  //备用的返回键
    protected ImageView func;  // 最右边的键
    private ImageView close;  // 右边关闭键
    private ImageView func2;   // 右边倒数第二个
    private TextView tv_center;  //  标题
    private RelativeLayout head_view;//顶部标题
    private int verticalMinDistance = 20;
    private int minVelocity = 0;
    private TitleTab title_tab_center;
    private Button func_tv;
    private RelativeLayout frameLayout;
    private ImageView school_share,answer_setting;
    private TextView func_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {}
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //初始butterknife
        View view = initContentView();
        ButterKnife.bind(this, view);
        //   竖屏显示
        setContentView(view);
        // 设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 状态栏标识颜色变黑
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //透明状态栏
            getWindow().setStatusBarColor(Color.WHITE);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // 加载进度条
        s = new CompositeSubscription();
        AppUtils.addActivity(this);

        // 初始化
        initView();
        initDialog();
        initData();

        registerBroadcast();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化进度条
     */
    protected void initDialog() {
//        dialog = new ProgressDialog(BaseActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog = new ProgressDialog(BaseActivity.this, AlertDialog.THEME_HOLO_LIGHT);
//        dialog = new ProgressDialog(BaseActivity.this, AlertDialog.BUTTON_NEUTRAL);
//        dialog = MyProgressDialog.createDialog(BaseActivity.this);
//        dialog.setContentView(R.layout.window_progress);
//        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        dialog.setContentView(R.layout.window_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("正在加载中...");
    }

    /**
     * 初始化全局的内容布局
     *
     * @return
     */
    private View initContentView() {
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // 添加头布局
        View view = null;
        if (needAddHeader()) {
            View headerView = View.inflate(this, R.layout.include_head_view, linearLayout);
            setupHeaderView(headerView);
        }
        // 添加内容布局
        view = View.inflate(this, getViewResourceId(), null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        linearLayout.addView(view, params);
        return linearLayout;
    }

    /**
     * 是否需要添加headerview
     *
     * @return 默认是true
     */
    protected boolean needAddHeader() {
        return true;
    }

    /**
     * 取消headerview
     */
    protected void setAddHeaderHide(){
        head_view.setVisibility(View.GONE);
    }
    /**
     * 显示headerview
     */
    protected void setAddHeaderSeek(){
        head_view.setVisibility(View.VISIBLE);
    }
    /**
     * 设置headerview
     *
     * @param view
     */
    private void setupHeaderView(View view) {
        // 初始化
        head_view = view.findViewById(R.id.head_view);
        back =  view.findViewById(R.id.back);
//        back2 =  view.findViewById(R.id.back2);

        func =  view.findViewById(R.id.func);
        close = view.findViewById(R.id.close);
        func2 = view.findViewById(R.id.func_left);
        tv_center = view.findViewById(R.id.tv_center);
        school_share = view.findViewById(R.id.school_share);
        answer_setting = view.findViewById(R.id.answer_setting);
        title_tab_center =  view.findViewById(R.id.title_tab_center);
        func_tv = view.findViewById(R.id.func_tv);
        frameLayout = view.findViewById(R.id.head_view);
        func_right = view.findViewById(R.id.func_right);

        // 暂时隐藏
        back.setVisibility(View.GONE);
        func.setVisibility(View.GONE);
        func2.setVisibility(View.GONE);
        title_tab_center.setVisibility(View.GONE);
        func_tv.setVisibility(View.GONE);

        func_right.setVisibility(View.GONE);

        // 点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public ImageView getBack() {
        return back;
    }
    public ImageView getHideBack() {
        return back2;
    }

    public ImageView getFunc() {
        return func;
    }

    public ImageView getFunc2() {
        return func2;
    }
    public TextView getFunc_right(){
        return func_right;
    }

    public TextView getTv_center() {
        return tv_center;
    }

    public TitleTab getTitle_tab_center() {
        return title_tab_center;
    }

    public ImageView getClose() {
        return close;
    }

    public void setClose(ImageView close) {
        this.close = close;
    }

    public ProgressDialog getDialog() {
        return dialog;
    }

    public Button getFunc_tv() {
        return func_tv;
    }

    public RelativeLayout getHeaderView() {
        return frameLayout;
    }

    /**
     * 初始化标题  并设置点击事件
     *
     * @param showBack  是否展示返回键
     * @param title     标题名字
     * @param showFunc2  右边倒数第二个点击事件
     * @param showFunc  功能键的点击事件
     */
    protected void initHeaderView(boolean showBack, String title, View.OnClickListener showFunc2, View.OnClickListener showFunc) {
        back.setVisibility(showBack ? View.VISIBLE : View.GONE);
        tv_center.setText(title);
        if (showFunc2 != null) {
            func2.setVisibility(View.VISIBLE);
            func2.setOnClickListener(showFunc2);
        }
        if (showFunc != null) {
            func.setVisibility(View.VISIBLE);
            func.setOnClickListener(showFunc);
        }
    }

    /**
     * 分享驾校
     * @return
     */
    protected ImageView getShareIconId(){
        return school_share;
    }
    /**
     * 答题设置
     * @return
     */
    public ImageView getAnswer_setting(){
        return answer_setting;
    }
    /**
     * 初始化标题  并设置点击事件
     *
     * @param title 标题名字
     */
    protected void initHeaderView(String title) {
        initHeaderView(true, title, null, null);
    }

    /**
     * 关闭网页使用初始化标题
     * @param title
     * @param isShow
     */
    protected void initHeaderView(String title , boolean isShow) {
        initHeaderView(true,isShow, title, null, null);
    }

    protected void initHeaderView(boolean showBack,boolean isShow, String title, View.OnClickListener showFunc2, View.OnClickListener showFunc) {
        back.setVisibility(showBack ? View.VISIBLE : View.GONE);
        close.setVisibility(isShow? View.VISIBLE:View.GONE);
        tv_center.setText(title);
        if (showFunc2 != null) {
            func2.setVisibility(View.VISIBLE);
            func2.setOnClickListener(showFunc2);
        }
        if (showFunc != null) {
            func.setVisibility(View.VISIBLE);
            func.setOnClickListener(showFunc);
        }
    }

    protected void initHeaderView(boolean b, String title) {
        initHeaderView(false, title, null, null);
    }

    /**
     * 显示选项卡标题
     * @param radioButtonTitle
     * @param onCheckedListener
     */
    @SuppressLint("ResourceType")
    protected void initHeaderView(String[] radioButtonTitle , TitleTab.onCheckedListener onCheckedListener) {
        back.setVisibility(View.VISIBLE);
        title_tab_center.setVisibility(View.VISIBLE);
        title_tab_center.setTitleNames(radioButtonTitle)
                .setBgColor(getResources().getColor(R.color.blue_button))
                .setTextSelectedColor(Color.WHITE)
                .setRadius_DP(20)
                .setTextSize_SP(14)
                .build();


        title_tab_center.setOnMyCheckedChangeListener((TitleTab.onCheckedListener)
                onCheckedListener);
    }

    @Override
    public abstract int getViewResourceId();

    @Override
    public abstract void initView();

    @Override
    public abstract void initData();

    @Override
    public abstract void initResultData(Result result);

    @Override
    public void initResultDataString(String result) {

    }


    /**
     * 添加Fragment
     *
     * @param fragment    新增Fragment
     * @param containerId Fragment容器Id
     */
    public void addFragment(BaseFragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction()
                .add(containerId, fragment).commitAllowingStateLoss();
    }

    /**
     * 替换Fragment
     *
     * @param fragment    要被替换成的Fragment
     * @param containerId Fragment容器Id
     */
    public void replaceFragment(BaseFragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment).commitAllowingStateLoss();
    }

    /**
     * 显示Fragment
     *
     * @param containerId
     * @param oldFragment
     * @param newFragment
     */
    public void showFragment(int containerId, BaseFragment oldFragment, BaseFragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!newFragment.isAdded()) {
            transaction.add(containerId, newFragment);
        }
        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        transaction.show(newFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏Fragment
     *
     * @param fragment 被隐藏的Fragment
     */
    public void hideFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment)
                .commitAllowingStateLoss();
    }
    @Override
    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }
    @Override
    public void showProgressDialog() {
        if (dialog != null) {
//            System.out.print("=================弹出加载框=====================");
//            UIUtil.toast("=========弹出加载框==============");
//            dialog.show();
        }
    }
    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
        try {
            s.unsubscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
    /**
     * 获取网络数据
     *
     */
    protected void getResultData(String url, Map map, boolean needProgressDialog) {
        xutilProtocol.getInstance().post(url, map, this, needProgressDialog);
    }

    /**
     * 获取网络数据
     */
    protected void getResultData(String url, Map map) {
        getResultData(url, map, true);
    }
//
    /**
     *  添加  rx....
     * @param subscription
     */
    @Override
    public void addSubcrib(Subscription subscription) {
        s.add(subscription);
    }

    ExitReceiver receiver;
    private void registerBroadcast() {
        // 注册广播接收者
        receiver = new ExitReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("EXIT_APP");
        registerReceiver(receiver,filter);
    }

    class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constant.EXIT_APP)){
                RoleUitl.getInstance().logOut();
                startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                Log.e("zs","退出登陆");
                finish();
            }
        }
    }
}

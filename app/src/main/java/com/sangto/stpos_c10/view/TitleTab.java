package com.sangto.stpos_c10.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.sangto.stpos_c10.R;

import java.util.ArrayList;

/**
 * 详情: 标题里面 两个以及多个  tab  （类似qq界面的消息和电话）
 *  例子：
 * titleTab.setTitleNames(new String[]{"电话", "会议"...})
 * .setBgColor(Color.BLUE)
 * .setTextSelectedColor(Color.WHITE)
 * .setRadius_DP(20)
 * .setTextSize_SP(14)
 * .setViewPager(mViewPager)
 * .build()
 * .setOnMyCheckedChangeListener(new TitleTab.onCheckedListener() {
 *       @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
 *                        Toast.makeText(TitleTabAct.this, "点击了"+checkedId, Toast.LENGTH_SHORT).show();
 *      System.out.println( "点击了"+checkedId);
 *      }
 * });
 */
public class TitleTab extends RadioGroup {
    // 1  需要外界传入：  文字  文字大小   选中时的tab背景颜色  tab 背景图片圆角角度的大小
    //  2 需要关联的  viewpager
    /**
     * 文字集合
     */
    private String[] titleNames;
    /**
     * 文字大小   sp
     */
    private int textSize;
    /**
     * tab背景颜色
     */
    private int bgColor = Color.BLUE;
    /**
     * 文字选中时的颜色（默认是白色）
     */
    private int textSelectedColor = Color.WHITE;
    /**
     * tab圆角角度
     */
    private float radius;
    /**
     * 关联的viewpager
     */
    private ViewPager viewPager;
    /**
     * 边线的宽度  默认为1px
     */
    private int strokeWithe = 1;
    private Context context;
    private LayoutParams params;
    /**
     * 正常状态下的背景
     */
    ArrayList<GradientDrawable> normalList;
    /**
     * 选中状态下的背景
     */
    ArrayList<GradientDrawable> checkedList;
    /**
     * 上一个选中的button id
     */
    private int preIndex;
    private StateListDrawable[] stateListDrawables;
    private onCheckedListener listener;
    // viewpager 是否能手动滑动
    private boolean canScroll = true;

    private int checkedIndex = 0;

    public TitleTab(Context context) {
        super(context, null);
    }

    public TitleTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        // 设置方向
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
    }

    // 获取到了需要的参数   开始操作
    public TitleTab build() {
        // 设置padding值
        setPadding(strokeWithe, strokeWithe, strokeWithe, strokeWithe);
        // 设置背景
        setBackgroundDrawable(getGradientDrawable(bgColor, getRadios(3), strokeWithe));
        params.leftMargin = strokeWithe;
        // 先生产背景图片
        stateListDrawables = generateState();
        //  颜色状态选择器  ( 选中时 透明    未选中时  显示背景颜色)
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        {android.R.attr.state_checked},
                        {}},
                new int[]{
                        // 选中时的颜色
                        textSelectedColor
                        // 正常显示的颜色
                        , bgColor});
        //     动态添加radioBut
        //   设置文字  以及  大小
        RadioButton button;
        for (int i = 0; i < titleNames.length; i++) {
            button = (RadioButton) View.inflate(context, R.layout.but, null);
            button.setText(titleNames[i]);
            button.setId(i);
            button.setTextColor(colorStateList);
            if (textSize != 0) {
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }
            button.setButtonDrawable(android.R.color.transparent);
            button.setGravity(Gravity.CENTER);
            //  设置背景
            setButBg(button, i, titleNames.length - 1);
            // 设置监听
            setListener(button);
            // 添加view
//            if (i==0)params.leftMargin=0;
            addView(button, params);
        }
        return this;
    }

    private void setButBg(RadioButton button, int currentIndex, int totleIndex) {
        StateListDrawable drawable;
        if (currentIndex == 0) {
            drawable = stateListDrawables[0];
        } else if (currentIndex == totleIndex) {
            drawable = stateListDrawables[2];
        } else {
            drawable = stateListDrawables[1];
        }
        button.setBackgroundDrawable(drawable);
    }

    /**
     * radiogroup 以及  viewpager 都设置 监听回调
     *
     * @param button
     */
    private void setListener(RadioButton button) {
        // check对象变化时  调用外界的事件方法
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null && isChecked) {
                    listener.onCheckedChanged(TitleTab.this, buttonView.getId());
                }
            }
        });
        if (viewPager == null) return;
        //     内部封装 事件   点击条目  viewpager 跟着切换
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewPager.setCurrentItem(checkedId);
            }
        });
        //  和viewpager关联
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                TitleTab.this.check(position);
            }
        });
    }

    public void setViewPagerCurrent(int id){
        if (id<titleNames.length){
            viewPager.setCurrentItem(id);
        }

    }

    /**
     * 初始生成 三种 drawable 背景
     *
     * @return 正常  选中的效果 各三种
     */
    private StateListDrawable[] generateState() {
        StateListDrawable[] drawables = new StateListDrawable[3];
        // 状态机选择器
        for (int i = 0; i < 3; i++) {
            StateListDrawable singleDrawable = new StateListDrawable();
            float[] radii = getRadios(i);
//            //  添加选中的状态
            singleDrawable.addState(new int[]{android.R.attr.state_checked}, getGradientDrawable(Color.TRANSPARENT, radii, 0));
            //  添加正常的的状态
            singleDrawable.addState(new int[]{}, getGradientDrawable(textSelectedColor, radii, 0));
            drawables[i] = singleDrawable;
        }
        return drawables;
    }

    @NonNull
    private GradientDrawable getGradientDrawable(int color, float[] radii, int strokeWithe) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadii(radii);
        if (strokeWithe != 0) {
            gd.setStroke(strokeWithe, bgColor);
        }
        return gd;
    }

    //  根据索引获取 圆角 角度
    private float[] getRadios(int index) {
        float[] floats = null;
        switch (index) {
            case 0:
                floats = new float[]{radius, radius, 0, 0, 0, 0, radius, radius};
                break;
            case 1:
                floats = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
                break;
            case 2:
                floats = new float[]{0, 0, radius, radius, radius, radius, 0, 0};
                break;
            // 次为 设置总体的背景
            case 3:
                floats = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
                break;
        }
        return floats;
    }

    ///////////////////////////////////////////////////////////////////////////
//  构建TitleTab需要传入的参数
///////////////////////////////////////////////////////////////////////////
    public TitleTab setTitleNames(String[] titleNames_) {
        titleNames = titleNames_;
        return this;
    }

    public TitleTab setTextSize_SP(int textSize_) {
        textSize = textSize_;
        return this;
    }

    public TitleTab setBgColor(int bgColor_) {
        bgColor = bgColor_;
        return this;
    }

    public TitleTab setStrokeWithe(int strokeWithe) {
        this.strokeWithe = strokeWithe;
        return this;
    }

    public TitleTab setRadius_DP(int radius_) {
        radius = getPxFromDp(radius_);
        return this;
    }


    public TitleTab setViewPager(ViewPager viewPager_) {
        viewPager = viewPager_;
        return this;
    }

    /**
     * @param viewPager_
     * @param canScroll_ viewpager 是否能滑动
     * @return
     */

    private TitleTab setViewPager(ViewPager viewPager_, boolean canScroll_) {
        canScroll = canScroll_;
        viewPager = viewPager_;
        return this;
    }

    public TitleTab setTextSelectedColor(int textSelectedColor) {
        this.textSelectedColor = textSelectedColor;
        return this;
    }

    private float getPxFromDp(int radius_) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius_, context.getResources().getDisplayMetrics());
    }

    /**
     * 外部调用请使用   use {@link #setOnMyCheckedChangeListener(onCheckedListener}}
     */
    @Deprecated
    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(listener);
    }

    /**
     * 供外界  调用的   事件监听
     * 请勿调用 系统的setOnCheckedChangeListener
     *
     * @param listener
     */
    public void setOnMyCheckedChangeListener(onCheckedListener listener) {
        this.listener = listener;
    }

    public interface onCheckedListener {
        /**
         * @param group     radiogroup
         * @param checkedId 和选中的index保持一致  角标值
         */
        void onCheckedChanged(RadioGroup group, int checkedId);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        check(checkedIndex);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

}

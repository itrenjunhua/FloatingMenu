package com.renj.floatingmenu.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-06-14   13:28
 * <p>
 * 描述：自定义浮动菜单样式控件
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FloatingMenu extends ViewGroup {
    // 子菜单和母菜单图标距离
    private final static int RUDIOS = 250;
    // 当前菜单状态
    private MenuStatu currentStatu = MenuStatu.STATU_CLOSE;

    // 菜单状态枚举
    public enum MenuStatu {
        STATU_OPEN, STATU_CLOSE
    }

    /**
     * 子菜单被点击回调接口
     */
    public interface OnItemMenuClickListener {
        void onItemMenuClick(View view, int position);
    }

    // 子菜单被点击回调接口
    private OnItemMenuClickListener onItemMenuClickListener;

    public void setOnItemMenuClickListener(OnItemMenuClickListener onItemMenuClickListener) {
        this.onItemMenuClickListener = onItemMenuClickListener;
    }

    public FloatingMenu(Context context) {
        super(context);
    }

    public FloatingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();

        if (childCount < 3)
            throw new IllegalStateException("当前控件的孩子控件至少需要3个");

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int tempWidth = getChildAt(1).getMeasuredWidth();
        int tempHeight = getChildAt(childCount - 1).getMeasuredHeight();
        setMeasuredDimension(RUDIOS + tempWidth, RUDIOS + tempHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View childAt = getChildAt(i);
                int childWidth = childAt.getMeasuredWidth();
                int childHeight = childAt.getMeasuredHeight();

                if (i == 0) {
                    int left = measuredWidth - childWidth;
                    int top = measuredHeight - childHeight;
                    childAt.layout(left, top, measuredWidth, measuredHeight);

                    childAt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            changeStatuAnim(300);
                        }
                    });
                } else {
                    final int temp = i;
                    float jiao = 90 / (childCount - 1 - 1);
                    float jiJiao = (i - 1) * jiao;
                    double centerX = RUDIOS * Math.cos(Math.PI / 180 * jiJiao);
                    double centerY = RUDIOS * Math.sin(Math.PI / 180 * jiJiao);
                    int left = (int) (measuredWidth - centerX - childWidth);
                    int top = (int) (measuredHeight - centerY - childHeight);
                    int right = (int) (measuredWidth - centerX);
                    int bottom = (int) (measuredHeight - centerY);
                    childAt.layout(left, top, right, bottom);

                    childAt.setVisibility(View.GONE);

                    childAt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onItemMenuClickListener != null) {
                                onItemMenuClickListener.onItemMenuClick(childAt, temp);
                            }
                            clickItemAnim(temp);
                        }
                    });
                }
            }
        }
    }

    /**
     * 点击子菜单时的动画效果
     *
     * @param position
     */
    private void clickItemAnim(int position) {
        for (int i = 1; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (i == position) {
                childAt.startAnimation(toBig());
            } else {
                childAt.startAnimation(toSmall());
            }
            childAt.setVisibility(GONE);
        }
        changeStatu();
    }

    /**
     * 变小，变透明
     */
    private Animation toSmall() {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.setDuration(200);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    /**
     * 变大，变透明
     *
     * @return
     */
    private Animation toBig() {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 3, 1, 3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.setDuration(200);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    /**
     * 子菜单状态改变动画效果
     *
     * @param durationMillis 动画执行时间
     */
    private void changeStatuAnim(int durationMillis) {
        int childCount = getChildCount();
        for (int i = 1; i < childCount; i++) {
            final View view = getChildAt(i);

            float jiao = 90 / (childCount - 1 - 1);
            float jiJiao = (i - 1) * jiao;
            float toX = (float) (RUDIOS * Math.cos(Math.PI / 180 * jiJiao));
            float toY = (float) (RUDIOS * Math.sin(Math.PI / 180 * jiJiao));

            AnimationSet animationSet = new AnimationSet(true);
            RotateAnimation rotateAnim = new RotateAnimation(
                    0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            TranslateAnimation translateAnimation;
            AlphaAnimation alphaAnimation;
            if (!isOpen()) {
                translateAnimation = new TranslateAnimation(toX, 0, toY, 0);
                alphaAnimation = new AlphaAnimation(0, 1);
            } else {
                translateAnimation = new TranslateAnimation(0, toX, 0, toY);
                alphaAnimation = new AlphaAnimation(1, 0);
            }

            // 注意先添加旋转动画，再添加平移动画
            animationSet.addAnimation(rotateAnim);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setDuration(durationMillis);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentStatu == MenuStatu.STATU_OPEN) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animationSet);
        }
        changeStatu();
    }

    /**
     * 改变状态
     */
    private void changeStatu() {
        currentStatu = (currentStatu == MenuStatu.STATU_OPEN) ? MenuStatu.STATU_CLOSE : MenuStatu.STATU_OPEN;
    }

    /**
     * 对外暴露的方法，关闭子菜单
     */
    public void closeMenu() {
        changeStatuAnim(300);
    }

    /**
     * 判断是否打开子菜单
     *
     * @return
     */
    public boolean isOpen() {
        return currentStatu == MenuStatu.STATU_OPEN;
    }
}

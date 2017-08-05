package com.hailer.news.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.socks.library.KLog;

/**
 * Created by moma on 17-8-4.
 */

public class InputMethodLayout extends LinearLayout{

    private static final String TAG = "InputMethodLayout";
    //初始化状态
    public static final byte KEYBOARD_STATE_INIT = -1;
    //隐藏状态
    public static final byte KEYBOARD_STATE_HIDE = -2;
    //打开状态
    public static final byte KEYBOARD_STATE_SHOW = -3;
    // 是否为初始化状态
    private boolean isInit;
    // 标识是否打开了软键盘
    private boolean hasKeybord;
    // 布局高度
    private onKeyboardsChangeListener keyboarddsChangeListener;// 键盘状态监听
    private int viewHeight;

    public InputMethodLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InputMethodLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputMethodLayout(Context context) {
        super(context);
    }

    /**
     * 设置软键盘状态监听
     *
     * @param listener
     */
    public void setOnkeyboarddStateListener(onKeyboardsChangeListener listener) {
        keyboarddsChangeListener = listener;
    }

    /**
     * 布局状态发生改变时，会触发onLayout
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isInit) {
            isInit = true;
            viewHeight = b;
            keyboardSateChange(KEYBOARD_STATE_INIT);
        } else {
            viewHeight = viewHeight < b ? b : viewHeight;
        }
        if (isInit && viewHeight > b) {
            if(hasKeybord == false){
                hasKeybord = true;
                keyboardSateChange(KEYBOARD_STATE_SHOW);
                KLog.w(TAG, "显示软键盘");
            }
        }
        if (isInit && hasKeybord && viewHeight == b) {
            hasKeybord = false;
            keyboardSateChange(KEYBOARD_STATE_HIDE);
            KLog.w(TAG, "隐藏软键盘");
        }
    }

    /**
     * 切换软键盘状态
     * @param state
     */
    public void keyboardSateChange(int state) {
        if (keyboarddsChangeListener != null) {
            keyboarddsChangeListener.onKeyBoardStateChange(state);
        }
    }

    /**
     * 软键盘状态切换监听
     */
    public interface onKeyboardsChangeListener {
        public void onKeyBoardStateChange(int state);
    }
}

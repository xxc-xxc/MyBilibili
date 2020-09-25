package com.uos.mybilibili.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By xxc
 * Date: 2020/9/25 10:44
 * Desc: 实现Activity统一管理
 */
public class ActivitiesManager {

    public static List<Activity> sActivityList = new ArrayList<>();

    /**
     * 打开一个新的Activity时，添加Activity
     * @param activity
     */
    public static void addActivity(Activity activity) {
        sActivityList.add(activity);
    }

    /**
     * 销毁一个Activity时，移除Activity
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        hideSoftKeyboard(activity);
        activity.finish();
        sActivityList.remove(activity);
    }

    /**
     * 退出APP时，销毁所有Activity
     */
    public static void removeAllActivity() {
        for (Activity activity : sActivityList) {
            hideSoftKeyboard(activity);
            activity.finish();
        }
        sActivityList.clear();
    }

    /**
     * 判断是否有指定Activity
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends Activity> boolean hasActivity(Class<T> tClass) {
        for (Activity activity : sActivityList) {
            if (tClass.getName().equals(activity.getClass().getName())) {
                return !activity.isDestroyed() || !activity.isFinishing();
            }
        }

        return false;
    }

    /**
     * 隐藏软键盘
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        View localView = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (localView != null && imm != null) {
            imm.hideSoftInputFromWindow(localView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}

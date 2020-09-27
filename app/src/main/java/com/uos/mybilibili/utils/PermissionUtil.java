package com.uos.mybilibili.utils;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions3.RxPermissions;

/**
 * Create By xxc
 * Date: 2020/8/26 14:10
 * Desc:
 */
public class PermissionUtil {
    private static RxPermissions sRxPermissions;

    private PermissionUtil() {
    }

    public static RxPermissions getRxPermissions(FragmentActivity fragmentActivity) {
        if (sRxPermissions == null) {
            synchronized (PermissionUtil.class) {
                if (sRxPermissions == null) {
                    sRxPermissions = new RxPermissions(fragmentActivity);
                }
            }
        }

        return sRxPermissions;
    }
}

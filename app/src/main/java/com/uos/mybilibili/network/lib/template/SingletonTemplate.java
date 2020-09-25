package com.uos.mybilibili.network.lib.template;

/**
 * Create By xxc
 * Date: 2020/9/25 16:43
 * Desc:
 */
public abstract class SingletonTemplate<T> {

    private T mInstance;
    protected abstract T create();
    public final T getInstance() {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null) {
                    mInstance = create();
                }
            }
        }
        return mInstance;
    }

}

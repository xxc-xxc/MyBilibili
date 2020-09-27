package com.uos.mybilibili.network.response;

/**
 * @author xxc
 * @date 2020年9月27日17:10:35
 * 描述:统一处理HttpResponse
 */

public class HttpResponse<T> {
    public T data;//数据
    public T result;//数据
    public String message;//信息
    public int code;//服务器状态

    //只有全区排行 json 有这个 字段
    public T rank;//数据

}

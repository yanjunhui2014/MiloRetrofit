package com.milo.retrofit.data;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/2/21
 */
public enum  MainItem {

    OkHttp(0, "okHttp请求"), Retrofit(1, "retrofit请求");

    public final int type;
    public final String name;

    MainItem(int type, String name){
        this.type = type;
        this.name = name;
    }

}

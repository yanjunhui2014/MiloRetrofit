package com.milo.retrofit.net;

import com.milo.libretrofit.Retrofit;

import java.net.Proxy;

import okhttp3.OkHttpClient;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/8/21
 */
public class ApiFactory {

    private static Api api;

    public static Api obtainApi() {
        if (api == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .proxy(Proxy.NO_PROXY)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://apis.juhe.cn/")
                    .build();
            api = retrofit.create(Api.class);
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        }

        return api;
    }

}

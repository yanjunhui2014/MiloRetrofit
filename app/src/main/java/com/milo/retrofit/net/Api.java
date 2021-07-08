package com.milo.retrofit.net;

import com.milo.libretrofit.http.GET;
import com.milo.libretrofit.http.Query;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/8/21
 */
public interface Api {

//    @GET("simpleWeather/query")
//    Single<String> simpleWeatherQuery(@Query("city") String city, @Query("key") String key);

    @GET("simpleWeather/query")
    String simpleWeatherQuery(@Query("city") String city, @Query("key") String key);

}

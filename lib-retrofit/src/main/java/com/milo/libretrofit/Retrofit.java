package com.milo.libretrofit;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.HttpUrl;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/1/21
 */
public final class Retrofit {

    private final Map<Method, ServiceMethod<?, ?>> serviceMethodCache = new ConcurrentHashMap<>();

    final Call.Factory              callFactory;
    final HttpUrl                   baseUrl;
    final List<Converter.Factory>   converterFactories;
    final List<CallAdapter.Factory> adapterFactories;
    @Nullable
    final Executor                  callbackExecutor;
    final boolean                   validateEagerly;

    public Retrofit(Call.Factory callFactory, HttpUrl baseUrl, List<Converter.Factory> converterFactories,
                    List<CallAdapter.Factory> adapterFactories, @Nullable Executor callbackExecutor, boolean validateEagerly) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
        this.converterFactories = converterFactories;
        this.adapterFactories = adapterFactories;
        this.callbackExecutor = callbackExecutor;
        this.validateEagerly = validateEagerly;
    }
}

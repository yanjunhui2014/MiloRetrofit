package com.milo.libretrofit;

import android.util.Log;

import androidx.annotation.Nullable;

import com.milo.libretrofit.http.GET;
import com.milo.libretrofit.http.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

    final okhttp3.Call.Factory callFactory;
    final HttpUrl baseUrl;
    //    final List<Converter.Factory>   converterFactories;
//    final List<CallAdapter.Factory> adapterFactories;
    @Nullable
    final Executor callbackExecutor;
    final boolean validateEagerly;

    public Retrofit(Call.Factory callFactory, HttpUrl baseUrl, @Nullable Executor callbackExecutor, boolean validateEagerly) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
        this.callbackExecutor = callbackExecutor;
        this.validateEagerly = validateEagerly;
    }

//    public Retrofit(Call.Factory callFactory, HttpUrl baseUrl, List<Converter.Factory> converterFactories,
//                    List<CallAdapter.Factory> adapterFactories, @Nullable Executor callbackExecutor, boolean validateEagerly) {
//        this.callFactory = callFactory;
//        this.baseUrl = baseUrl;
//        this.converterFactories = converterFactories;
//        this.adapterFactories = adapterFactories;
//        this.callbackExecutor = callbackExecutor;
//        this.validateEagerly = validateEagerly;
//    }

    public <T> T create(final Class<T> service) {
        Utils.validateServiceInterface(service);
        if (validateEagerly) {
            eagerlyValidateMethods(service);
        }
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    private final Platform platform = Platform.get();

                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        if (platform.isDefaultMethod(method)) {
                            return platform.invokeDefaultMethod(method, service, proxy, args);
                        }
//                        ServiceMethod<Object, Object> serviceMethod =
//                                (ServiceMethod<Object, Object>) loadServiceMethod(method);
//                        OkHttpCall<Object> okHttpCall = new OkHttpCall<>(serviceMethod, args);
//                        return serviceMethod.adapt(okHttpCall);

                        Class<?>[] parameterTypes = method.getParameterTypes();
                        List<String> keyList = new ArrayList<>();

                        Annotation[][] annotationss = method.getParameterAnnotations();
                        for (Annotation[] annotations : annotationss) {
                            for (int i = 0; i < annotations.length; i++) {
                                Annotation itemAnno = annotations[i];
                                if (itemAnno instanceof Query) {
                                    keyList.add(((Query) itemAnno).value());
                                }
                            }
                        }

                        String methodName = ((GET) method.getAnnotations()[0]).value();

                        StringBuilder stringBuilder = new StringBuilder(baseUrl.toString())
                                .append(methodName)
                                .append("?");
                        for (int i = 0; i < args.length; i++) {
                            stringBuilder.append(i == 0 ? "" : "&")
                                    .append(keyList.get(i))
                                    .append("=")
                                    .append(args[i]);
                        }
                        Request request = new Request.Builder()
                                .url(stringBuilder.toString())
                                .get()
                                .build();
                        Log.d("Retrofit", "url == " + stringBuilder.toString());

                        Call call = new OkHttpClient().newCall(request);
                        Response response = call.execute();

                        return String.format("code == %s \n msg == %s \n body == %s", response.code(), response.message(), response.body().string());
                    }
                });
    }

    public okhttp3.Call.Factory callFactory() {
        return callFactory;
    }

    public HttpUrl baseUrl() {
        return baseUrl;
    }

    private void eagerlyValidateMethods(Class<?> service) {
//        Platform platform = Platform.get();
//        for (Method method : service.getDeclaredMethods()) {
//            if (!platform.isDefaultMethod(method)) {
//                loadServiceMethod(method);
//            }
//        }
//    }
//
//    ServiceMethod<?, ?> loadServiceMethod(Method method) {
//        ServiceMethod<?, ?> result = serviceMethodCache.get(method);
//        if (result != null) return result;
//
//        synchronized (serviceMethodCache) {
//            result = serviceMethodCache.get(method);
//            if (result == null) {
//                result = new ServiceMethod.Builder<>(this, method).build();
//                serviceMethodCache.put(method, result);
//            }
//        }
//        return result;
    }

    ServiceMethod<?, ?> loadServiceMethod(Method method) {
        ServiceMethod<?, ?> result = serviceMethodCache.get(method);
        if (result != null) return result;

        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder<>(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public CallAdapter<?, ?> callAdapter(Type returnType, Annotation[] annotations) {
        return nextCallAdapter(null, returnType, annotations);
    }

    public CallAdapter<?, ?> nextCallAdapter(@Nullable CallAdapter.Factory skipPast, Type returnType,
                                             Annotation[] annotations) {
        Utils.checkNotNull(returnType, "returnType == null");
        Utils.checkNotNull(annotations, "annotations == null");

//        int start = callAdapterFactories.indexOf(skipPast) + 1;
//        for (int i = start, count = callAdapterFactories.size(); i < count; i++) {
//            CallAdapter<?, ?> adapter = callAdapterFactories.get(i).get(returnType, annotations, this);
//            if (adapter != null) {
//                return adapter;
//            }
//        }
//
//        StringBuilder builder = new StringBuilder("Could not locate call adapter for ")
//                .append(returnType)
//                .append(".\n");
//        if (skipPast != null) {
//            builder.append("  Skipped:");
//            for (int i = 0; i < start; i++) {
//                builder.append("\n   * ").append(callAdapterFactories.get(i).getClass().getName());
//            }
//            builder.append('\n');
//        }
//        builder.append("  Tried:");
//        for (int i = start, count = callAdapterFactories.size(); i < count; i++) {
//            builder.append("\n   * ").append(callAdapterFactories.get(i).getClass().getName());
//        }
//        throw new IllegalArgumentException(builder.toString());
        return null;
    }

    public <T> Converter<T, String> stringConverter(Type type, Annotation[] annotations) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotations, "annotations == null");

//        for (int i = 0, count = converterFactories.size(); i < count; i++) {
//            Converter<?, String> converter =
//                    converterFactories.get(i).stringConverter(type, annotations, this);
//            if (converter != null) {
//                //noinspection unchecked
//                return (Converter<T, String>) converter;
//            }
//        }
//
//        // Nothing matched. Resort to default converter which just calls toString().
//        //noinspection unchecked
//        return (Converter<T, String>) BuiltInConverters.ToStringConverter.INSTANCE;
        return null;
    }

    public <T> Converter<T, RequestBody> requestBodyConverter(Type type,
                                                              Annotation[] parameterAnnotations, Annotation[] methodAnnotations) {
        return nextRequestBodyConverter(null, type, parameterAnnotations, methodAnnotations);
    }

    public <T> Converter<T, RequestBody> nextRequestBodyConverter(
            @Nullable Converter.Factory skipPast, Type type, Annotation[] parameterAnnotations,
            Annotation[] methodAnnotations) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(parameterAnnotations, "parameterAnnotations == null");
        Utils.checkNotNull(methodAnnotations, "methodAnnotations == null");

//        int start = converterFactories.indexOf(skipPast) + 1;
//        for (int i = start, count = converterFactories.size(); i < count; i++) {
//            Converter.Factory factory = converterFactories.get(i);
//            Converter<?, RequestBody> converter =
//                    factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, this);
//            if (converter != null) {
//                //noinspection unchecked
//                return (Converter<T, RequestBody>) converter;
//            }
//        }
//
//        StringBuilder builder = new StringBuilder("Could not locate RequestBody converter for ")
//                .append(type)
//                .append(".\n");
//        if (skipPast != null) {
//            builder.append("  Skipped:");
//            for (int i = 0; i < start; i++) {
//                builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
//            }
//            builder.append('\n');
//        }
//        builder.append("  Tried:");
//        for (int i = start, count = converterFactories.size(); i < count; i++) {
//            builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
//        }
//        throw new IllegalArgumentException(builder.toString());
        return null;
    }

    public <T> Converter<ResponseBody, T> responseBodyConverter(Type type, Annotation[] annotations) {
        return nextResponseBodyConverter(null, type, annotations);
    }

    public <T> Converter<ResponseBody, T> nextResponseBodyConverter(
            @Nullable Converter.Factory skipPast, Type type, Annotation[] annotations) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotations, "annotations == null");

//        int start = converterFactories.indexOf(skipPast) + 1;
//        for (int i = start, count = converterFactories.size(); i < count; i++) {
//            Converter<ResponseBody, ?> converter =
//                    converterFactories.get(i).responseBodyConverter(type, annotations, this);
//            if (converter != null) {
//                //noinspection unchecked
//                return (Converter<ResponseBody, T>) converter;
//            }
//        }
//
//        StringBuilder builder = new StringBuilder("Could not locate ResponseBody converter for ")
//                .append(type)
//                .append(".\n");
//        if (skipPast != null) {
//            builder.append("  Skipped:");
//            for (int i = 0; i < start; i++) {
//                builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
//            }
//            builder.append('\n');
//        }
//        builder.append("  Tried:");
//        for (int i = start, count = converterFactories.size(); i < count; i++) {
//            builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
//        }
//        throw new IllegalArgumentException(builder.toString());
        return null;
    }

    public static final class Builder {
        private final Platform platform;
        private Call.Factory callFactory;
        private HttpUrl baseUrl;
        private Executor callbackExecutor;
        private boolean validateEagerly;

        Builder(Platform platform) {
            this.platform = platform;
        }

        public Builder() {
            this(Platform.get());
        }

        public Builder client(OkHttpClient client) {
            return callFactory(Utils.checkNotNull(client, "client == null"));
        }

        public Builder callFactory(okhttp3.Call.Factory factory) {
            this.callFactory = Utils.checkNotNull(factory, "factory == null");
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            Utils.checkNotNull(baseUrl, "baseurl == null");
            HttpUrl httpUrl = HttpUrl.parse(baseUrl);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Illegal URL: " + baseUrl);
            }
            return baseUrl(httpUrl);
        }

        public Builder baseUrl(HttpUrl baseUrl) {
            Utils.checkNotNull(baseUrl, "baseurl == null");
            List<String> pathSegments = baseUrl.pathSegments();
            if (!"".equals(pathSegments.get(pathSegments.size() - 1))) {
                throw new IllegalArgumentException("baseUrl must end in /: " + baseUrl);
            }
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder validateEagerly(boolean validateEagerly) {
            this.validateEagerly = validateEagerly;
            return this;
        }

        public Retrofit build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }

            okhttp3.Call.Factory callFactory = this.callFactory;
            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }

            Executor callbackExecutor = this.callbackExecutor;
            if (callbackExecutor == null) {
                callbackExecutor = platform.defaultCallbackExecutor();
            }

            // Make a defensive copy of the adapters and add the default Call adapter.
//            List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>(this.callAdapterFactories);
//            callAdapterFactories.add(platform.defaultCallAdapterFactory(callbackExecutor));

            // Make a defensive copy of the converters.
//            List<Converter.Factory> converterFactories =
//                    new ArrayList<>(1 + this.converterFactories.size());
//
//            // Add the built-in converter factory first. This prevents overriding its behavior but also
//            // ensures correct behavior when using converters that consume all types.
//            converterFactories.add(new BuiltInConverters());
//            converterFactories.addAll(this.converterFactories);

            return new Retrofit(callFactory, baseUrl, callbackExecutor, validateEagerly);
        }
    }


}

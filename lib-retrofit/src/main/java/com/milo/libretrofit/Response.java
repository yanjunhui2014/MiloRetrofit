package com.milo.libretrofit;

import androidx.annotation.Nullable;

import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 6/25/21
 */
public final class Response<T> {

    private final okhttp3.Response rawResponse;
    private final @Nullable T            body;
    private final @Nullable
    ResponseBody errorBody;

    private Response(okhttp3.Response rawResponse, @Nullable T body,
                     @Nullable ResponseBody errorBody) {
        this.rawResponse = rawResponse;
        this.body = body;
        this.errorBody = errorBody;
    }

    public okhttp3.Response raw() {
        return rawResponse;
    }

    public int code() {
        return rawResponse.code();
    }

    public String message() {
        return rawResponse.message();
    }

    public Headers headers() {
        return rawResponse.headers();
    }

    public boolean isSuccessful() {
        return rawResponse.isSuccessful();
    }

    public @Nullable T body() {
        return body;
    }

    public @Nullable ResponseBody errorBody() {
        return errorBody;
    }

    @Override public String toString() {
        return rawResponse.toString();
    }

    /**
     * 以 {@code body} 作为反序列化 body 创建一个合成的 successful response。
     *
     * @param body 反序列化 body
     * @param <T> 反序列化泛型
     * @return
     */
    public static <T> Response<T> success(@Nullable T body){
        return success(body, new okhttp3.Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    public static <T> Response<T> success(@Nullable T body, Headers headers) {
        Utils.checkNotNull(headers, "headers == null");
        return success(body, new okhttp3.Response.Builder() //
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .headers(headers)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    public static <T> Response<T> success(@Nullable T body, okhttp3.Response rawResponse){
        Utils.checkNotNull(rawResponse, "rawResponse == null");
        if(!rawResponse.isSuccessful()){
            throw new IllegalArgumentException("rawResponse must be successful response");
        }
        return new Response<>(rawResponse, body, null);
    }

    public static <T> Response<T> error(int code, ResponseBody body) {
        if (code < 400) throw new IllegalArgumentException("code < 400: " + code);
        return error(body, new okhttp3.Response.Builder() //
                .code(code)
                .message("Response.error()")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    public static <T> Response<T> error(ResponseBody body, okhttp3.Response rawResponse) {
        Utils.checkNotNull(body, "body == null");
        Utils.checkNotNull(rawResponse, "rawResponse == null");
        if (rawResponse.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse should not be successful response");
        }
        return new Response<>(rawResponse, null, body);
    }

}

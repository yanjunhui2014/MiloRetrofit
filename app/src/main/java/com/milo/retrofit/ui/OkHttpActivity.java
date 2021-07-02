package com.milo.retrofit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.milo.retrofit.R;
import com.milo.retrofit.databinding.ActivityOkhttpBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.schedulers.IoScheduler;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/2/21
 */
public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityOkhttpBinding viewBinding;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, OkHttpActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityOkhttpBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());
        setTitle("OkHttp");

        viewBinding.btnRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == viewBinding.btnRequest) {
            Single.create(new SingleOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                    Request request = new Request.Builder()
                            .url("http://apis.juhe.cn/simpleWeather/query?city=%E6%9D%AD%E5%B7%9E&key=cbca5d500753d443578759a3f87ad4b2")
                            .get()
                            .build();

                    Call call = new OkHttpClient().newCall(request);
                    Response response = call.execute();
                    emitter.onSuccess(String.format("code == %s \n msg == %s \n body == %s", response.code(), response.message(), response.body().string()));
                }
            }).subscribeOn(new IoScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Throwable {
                            viewBinding.tvResponse.setText(s);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            viewBinding.tvResponse.setText(String.format("异常%s", throwable.getMessage()));
                        }
                    });

        }
    }
}

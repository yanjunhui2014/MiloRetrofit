package com.milo.retrofit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.milo.retrofit.R;
import com.milo.retrofit.databinding.ActivityRetrofitBinding;
import com.milo.retrofit.net.ApiFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
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
public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRetrofitBinding viewBinding;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, RetrofitActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityRetrofitBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());
        viewBinding.btnRequest.setOnClickListener(this);
        setTitle("Retrofit");
    }

    @Override
    public void onClick(View v) {
        if (v == viewBinding.btnRequest) {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                    emitter.onNext(ApiFactory.obtainApi().simpleWeatherQuery("%E6%9D%AD%E5%B7%9E", "cbca5d500753d443578759a3f87ad4b2"));
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

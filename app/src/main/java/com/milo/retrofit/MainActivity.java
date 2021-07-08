package com.milo.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milo.retrofit.data.MainItem;
import com.milo.retrofit.databinding.ActivityMainBinding;
import com.milo.retrofit.ui.OkHttpActivity;
import com.milo.retrofit.ui.RetrofitActivity;
import com.milo.retrofit.vh.MainItemVH;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private RecyclerView.Adapter<MainItemVH> adapter;

    private List<MainItem> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        dataList.add(MainItem.OkHttp);
        dataList.add(MainItem.Retrofit);

        adapter = new RecyclerView.Adapter<MainItemVH>() {

            @NonNull
            @Override
            public MainItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.vh_main_item, null);
                MainItemVH itemVH = new MainItemVH(rootView, new MainItemVH.OnClickListener() {
                    @Override
                    public void onClick(MainItem data) {
                        if (data == MainItem.OkHttp) {
                            startActivity(OkHttpActivity.createIntent(MainActivity.this));
                        } else if (data == MainItem.Retrofit) {
                            startActivity(RetrofitActivity.createIntent(MainActivity.this));
                        }
                    }
                });
                return itemVH;
            }

            @Override
            public void onBindViewHolder(@NonNull MainItemVH holder, int position) {
                holder.updateData(dataList.get(position), position);
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }
        };
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
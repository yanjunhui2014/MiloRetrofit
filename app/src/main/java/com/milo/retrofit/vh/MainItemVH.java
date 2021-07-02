package com.milo.retrofit.vh;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milo.retrofit.R;
import com.milo.retrofit.data.MainItem;
import com.milo.retrofit.ui.OkHttpActivity;
import com.milo.retrofit.ui.RetrofitActivity;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/2/21
 */
public class MainItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvName;

    private MainItem data;

    public MainItemVH(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvName = itemView.findViewById(R.id.tv_name);
    }

    public void updateData(MainItem data, int position) {
        this.data = data;
        tvName.setText(data.name);
    }

    @Override
    public void onClick(View v) {
        if (data != null) {
            if (data == MainItem.OkHttp) {
                v.getContext().startActivity(OkHttpActivity.createIntent(v.getContext()));
            } else if (data == MainItem.Retrofit) {
                v.getContext().startActivity(RetrofitActivity.createIntent(v.getContext()));
            }
        }
    }

}

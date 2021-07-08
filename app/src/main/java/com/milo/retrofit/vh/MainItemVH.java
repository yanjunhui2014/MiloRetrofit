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
    private OnClickListener onClickListener;

    public interface OnClickListener{
        void onClick(MainItem data);
    }

    public MainItemVH(@NonNull View itemView, OnClickListener onClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.onClickListener = onClickListener;
        tvName = itemView.findViewById(R.id.tv_name);
    }

    public void updateData(MainItem data, int position) {
        this.data = data;
        tvName.setText(data.name);
    }

    @Override
    public void onClick(View v) {
        this.onClickListener.onClick(data);
    }

}

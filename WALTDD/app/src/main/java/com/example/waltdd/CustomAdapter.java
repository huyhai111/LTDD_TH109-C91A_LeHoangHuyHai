package com.example.waltdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> thoiTiets;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> thoiTiets) {
        this.context = context;
        this.thoiTiets = thoiTiets;
    }

    @Override
    public int getCount() {
        return thoiTiets.size();
    }

    @Override
    public Object getItem(int position) {
        return thoiTiets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview,null);
        ThoiTiet thoiTiet = thoiTiets.get(position);
        TextView txtviewNgay = (TextView) convertView.findViewById(R.id.txtviewNgay);
        TextView txtviewTrangThai = (TextView) convertView.findViewById(R.id.txtviewTrangThai);
        TextView txtNDMax = (TextView) convertView.findViewById(R.id.txtNDmax);
        TextView txtNDmin = (TextView) convertView.findViewById(R.id.txtNDmin);
        ImageView imgviewTrangThai = (ImageView) convertView.findViewById(R.id.imgviewTrangThai);
        txtviewNgay.setText(thoiTiet.Day);
        txtviewTrangThai.setText(thoiTiet.Status);
        txtNDMax.setText(thoiTiet.MaxND + "°C");
        txtNDmin.setText(thoiTiet.MinND+ "°C");
        Picasso.with(context).load("http://openweathermap.org/img/wn/" + thoiTiet.Image +".png").into(imgviewTrangThai);
        return convertView;
    }
}

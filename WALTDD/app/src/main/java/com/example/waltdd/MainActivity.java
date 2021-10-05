package com.example.waltdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtTimKiem;
    Button btnTimKiem, btnXemThem;
    TextView txtTenTP, txtTenQG, txtNhietDo, txtThoiTiet, txtDoAm, txtMay, txtGio, txtNgay;
    ImageView imgThoiTiet;
    String ThanhPho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetCurrentData("Saigon");
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTP = edtTimKiem.getText().toString();
                if (tenTP.equals("")){
                    ThanhPho = "Saigon";
                    GetCurrentData(ThanhPho);
                } else {
                    ThanhPho = tenTP;
                    GetCurrentData(ThanhPho);
                }
            }
        });
        btnXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTP = edtTimKiem.getText().toString();
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("name",tenTP);
                startActivity(intent);
            }
        });
    }
    public void GetCurrentData(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url ="https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=fcbbdfc69d57332afa56eb105406a467";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsObject = new JSONObject(response);
                            String ngay = jsObject.getString("dt");
                            String tenTP = jsObject.getString("name");
                            txtTenTP.setText("City: " + tenTP);
                            long lg = Long.valueOf(ngay);
                            Date date = new Date(lg*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String day = simpleDateFormat.format(date);
                            txtNgay.setText(day);

                            JSONArray jsonArrayThoiTiet = jsObject.getJSONArray("weather");
                            JSONObject jsonObjectThoiTiet = jsonArrayThoiTiet.getJSONObject(0);
                            String trangthaiTT = jsonObjectThoiTiet.getString("main");
                            String icon = jsonObjectThoiTiet.getString("icon");
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + icon +".png").into(imgThoiTiet);
                            txtThoiTiet.setText(trangthaiTT);

                            JSONObject jsonObjectMain = jsObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam =jsonObjectMain.getString("humidity");
                            Double nhiet = Double.valueOf(nhietdo);
                            String doC = String.valueOf(nhiet.intValue());
                            txtDoAm.setText(doam +"%");
                            txtNhietDo.setText(doC +"°C");

                            JSONObject jsonObjectGio = jsObject.getJSONObject("wind");
                            String gio = jsonObjectGio.getString("speed");
                            txtGio.setText(gio +"m/s");

                            JSONObject jsonObjectMay = jsObject.getJSONObject("clouds");
                            String may = jsonObjectMay.getString("all");
                            txtMay.setText(may + "%");

                            JSONObject jsonObjectQG = jsObject.getJSONObject("sys");
                            String quocgia = jsonObjectQG.getString("country");
                            txtTenQG.setText("Country: "+ quocgia);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    private void AnhXa() {
        edtTimKiem = (EditText) findViewById(R.id.edtTimKiem);
        btnTimKiem = (Button) findViewById(R.id.btnTimKiem);
        btnXemThem = (Button) findViewById(R.id.btnXemThem);
        txtTenTP = (TextView) findViewById(R.id.txtTenTP);
        txtTenQG = (TextView) findViewById(R.id.txtTenQG);
        txtNhietDo = (TextView) findViewById(R.id.txtNhietDo);
        txtThoiTiet = (TextView) findViewById(R.id.txtThoiTiet);
        txtDoAm = (TextView) findViewById(R.id.txtDoAm);
        txtMay = (TextView) findViewById(R.id.txtMay);
        txtGio = (TextView) findViewById(R.id.txtGio);
        txtNgay = (TextView) findViewById(R.id.txtNgay);
        imgThoiTiet = (ImageView) findViewById(R.id.imgThoiTiet);
    }
}
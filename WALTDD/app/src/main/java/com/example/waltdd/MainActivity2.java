package com.example.waltdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    ImageView imgback;
    TextView txtTen;
    ListView listView;
    String ten = "";
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> arrayThoiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        Intent intent = getIntent();
        String tenTP = intent.getStringExtra("name");
        if (tenTP.equals("")){
            ten="saigon";
            GetData7Day(ten);
        }else {
            ten = tenTP;
            GetData7Day(ten);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        imgback = (ImageView) findViewById(R.id.imgback);
        txtTen = (TextView) findViewById(R.id.txtTen);
        listView = (ListView) findViewById(R.id.listview);
        arrayThoiTiet = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(MainActivity2.this, arrayThoiTiet);
        listView.setAdapter(customAdapter);
    }

    private void GetData7Day(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+ data + "&units=metric&cnt=14&appid=fcbbdfc69d57332afa56eb105406a467";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String ten = jsonObjectCity.getString("name");
                            txtTen.setText(ten);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i=0; i < jsonArrayList.length(); i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");
                                long lg = Long.valueOf(ngay);
                                Date date = new Date(lg*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE \n yyyy-MM-dd HH:mm:ss");
                                String day = simpleDateFormat.format(date);

                                JSONObject jsonObjectND = jsonObjectList.getJSONObject("main");
                                String ndmax = jsonObjectND.getString("temp_max");
                                String ndmin = jsonObjectND.getString("temp_min");
                                Double nhietmax = Double.valueOf(ndmax);
                                String ndmaxdoC = String.valueOf(nhietmax.intValue());
                                Double nhietmin = Double.valueOf(ndmin);
                                String ndmindoC = String.valueOf(nhietmin.intValue());

                                JSONArray jsonArrayThoiTiet = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectThoiTiet = jsonArrayThoiTiet.getJSONObject(0);
                                String thoitiet = jsonObjectThoiTiet.getString("description");
                                String icon = jsonObjectThoiTiet.getString("icon");

                                arrayThoiTiet.add(new ThoiTiet(day,thoitiet,icon,ndmaxdoC,ndmindoC));
                            }
                            customAdapter.notifyDataSetChanged();
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
}
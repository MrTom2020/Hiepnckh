package com.example.nckh;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.core.lineargauge.pointers.Bar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class tranghienthi extends Activity {

    private  BarChart barChart;
    private CheckBox c1,c2,c3,c4,c5,c6,c7;
    private Button btnmdbui,btnmdkk;
    private TextView txtnd,txtda,txtmq135;
    private ImageButton imageButton;
    private String tt ="";
    private dulieusqllite dl;
    private RelativeLayout relativeLayout;
    private Cursor cursor;
    private String chuoi = "https://hiep2020.000webhostapp.com/nhut/ESPselectdatabase.php";
    private ArrayList<BarEntry> arrayList = new ArrayList<>();
    private  LineChart lineChart;
    private ArrayList<thongtin> arrayList5;
    private ArrayList<thongtin> arrayListtt;
    private trangAdp adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranghienthi);
        dangkynut();
        check();
        taodt();
        doc(chuoi);
        //axx();
        dl.truyvankhongtrakq("delete FROM ThongTin");
        dangkysukien();

    }
    private void dangkynut()
    {
        relativeLayout = (RelativeLayout)findViewById(R.id.rl);
        barChart = (BarChart)findViewById(R.id.bc);
        c1 = (CheckBox)findViewById(R.id.c1);
        c2 = (CheckBox)findViewById(R.id.c2);
        c3 = (CheckBox)findViewById(R.id.c3);
        c4 = (CheckBox)findViewById(R.id.c4);
        c5 = (CheckBox)findViewById(R.id.c5);
        c6 = (CheckBox)findViewById(R.id.c6);
        c7 = (CheckBox)findViewById(R.id.c7);
        imageButton = (ImageButton)findViewById(R.id.imgluu);
        txtnd = (TextView)findViewById(R.id.txtnd);
        txtda =(TextView)findViewById(R.id.txtda);
        btnmdbui = (Button)findViewById(R.id.btnmdbui);
        btnmdkk = (Button)findViewById(R.id.btnmdkk);
        txtnd.setTextSize(30f);
        txtmq135 = (TextView)findViewById(R.id.textView9);
        c1.setEnabled(false);
        c2.setEnabled(false);
        c3.setEnabled(false);
        c4.setEnabled(false);
        c5.setEnabled(false);
        c6.setEnabled(false);
        c7.setEnabled(false);
        Button button = new Button(this);
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        button.setX((float) (metrics.widthPixels/1.7));
        button.setText("Danh sách đã được lưu");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialoglistData();
            }
        });
        relativeLayout.addView(button);
    }
    private void taodt()
    {
        dl = new dulieusqllite(this,"dulieunguoidung.sqlite",null,1);
        dl.truyvankhongtrakq("CREATE TABLE IF NOT EXISTS ThongTin(ID INTEGER PRIMARY KEY AUTOINCREMENT,nhietdo VARCHAR(50),doam VARCHAR(50),mq135 VARCHAR(50),density VARCHAR(50),time VARCHAR(50),date VARCHAR(50))");
    }
    private void dangkysukien()
    {
        btnmdkk.setOnClickListener(new sukiencuatoi());
        btnmdbui.setOnClickListener(new sukiencuatoi());
        imageButton.setOnClickListener(new sukiencuatoi());
    }
    private void check()
    {
        boolean ret = ConnectionReceiver.isConnected();
        String ms;
        if(ret == true)
        {
            ms = "Thiết bị có kết nối Internet và có thể tiến hành online";
            tt = "ok";
            btnmdkk.setEnabled(true);
            btnmdbui.setEnabled(true);
            imageButton.setEnabled(true);
        }
        else
        {
            ms = "Thiết bị không có kết nối Internet và có thể tiến hành offline";
            tt = "ko";
            btnmdkk.setEnabled(false);
            btnmdbui.setEnabled(false);
            imageButton.setEnabled(false);
        }
        Toast.makeText(tranghienthi.this,ms,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart()
    {
        check();
        super.onStart();
    }

    private class sukiencuatoi implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if(view.equals(btnmdkk))
            {
                doc(chuoi);
            }
            if(view.equals(btnmdbui))
            {
                doc2(chuoi);
            }
            if(view.equals(imageButton))
            {
                Toast.makeText(tranghienthi.this,"Lưu dữ liệu thành công",Toast.LENGTH_SHORT).show();
                axx();
                arrayList5.clear();
            }
        }
    }
    private void DialoglistData()
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_danhsachluu);
        int w = ViewGroup.LayoutParams.MATCH_PARENT - 100;
        int h = ViewGroup.LayoutParams.MATCH_PARENT -  100;
        dialog.getWindow().setLayout(w,h);
        ListView listView = (ListView)dialog.findViewById(R.id.listdata);
        d();
        adapter = new trangAdp(tranghienthi.this,R.layout.danhsach,arrayListtt);
        listView.setAdapter(adapter);
        dialog.show();
    }
    private void d()
    {
        arrayListtt = new ArrayList<>();
        cursor = dl.truyvancoketqua("SELECT * FROM ThongTin");
       if(cursor !=null)
        {
           while (cursor.moveToNext())
            {
                Toast.makeText(tranghienthi.this,cursor.getString(2).toString(),Toast.LENGTH_SHORT).show();
               String id =cursor.getString(0).toString();
               String nhietdo= cursor.getString(1).toString();
               String doam = cursor.getString(2).toString();
               String mq135 = cursor.getString(3).toString();
               String density =cursor.getString(4).toString();
              String time = cursor.getString(5).toString();
               String date = cursor.getString(6).toString();
             arrayListtt.add(new thongtin(id,nhietdo,doam,mq135,density,time,date));
            }
        }
    }
    private void doc(String chuoi)
    {
        arrayList5 = new ArrayList<>();
        barChart.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,chuoi,null, new Response.Listener<JSONArray>()
        {
            double d = 0.0;
            @Override
            public void onResponse(JSONArray response)
            {
                BarData barData = new BarData();
                BarDataSet barDataSet1;
                BarDataSet barDataSet;
                if (response.length() > 1) {
                    for (int i = response.length() - 1; i > 0  ; --i)
                    {
                        try {
                            {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String nd = jsonObject.getString("mq135").trim();
                                String doam = jsonObject.getString("doam");
                                String nhietdo = jsonObject.getString("nhietdo").trim();
                                String density = jsonObject.getString("density");
                                String time = jsonObject.getString("time").trim();
                                String date = jsonObject.getString("date");
                               arrayList5.add(new thongtin(id,nhietdo,doam,nd,density,time,date));
                                if(nd == "")
                                {
                                    d = 0.0;
                                }
                                else {
                                    d = Double.parseDouble(nd);
                                }
                                    arrayList.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    if (d > 0 && d <= 51) {
                                        ArrayList<BarEntry> arrayList2 = new ArrayList<>();
                                        arrayList2.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                        barDataSet1 = new BarDataSet(arrayList2, " " + jsonObject.getString("time") + "            ");
                                        barDataSet1.setColors(0xff01b0f1);
                                        barData.addDataSet(barDataSet1);
                                    } else if (d > 50 && d < 101) {
                                        ArrayList<BarEntry> arrayList3 = new ArrayList<>();
                                        arrayList3.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                        barDataSet = new BarDataSet(arrayList3, " " + jsonObject.getString("time") + "            ");
                                        barDataSet.setColors(0xffffff01);
                                        barData.addDataSet(barDataSet);

                                    } else if(d > 100 && d <201){
                                        ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                        arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                        barDataSet = new BarDataSet(arrayList4, " " + jsonObject.getString("time") + "            ");
                                        barDataSet.setColors(0xffffbe00);
                                        barDataSet.setFormSize(3f);
                                    }
                                    else if(d > 200 && d < 301)
                                    {
                                        ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                        arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                        barDataSet = new BarDataSet(arrayList4, " " + jsonObject.getString("time") + "            ");
                                        //barDataSet.setFormLineWidth(3f);
                                        barDataSet.setColors(0xfffe0000);
                                        barData.addDataSet(barDataSet);
                                    }
                                    else
                                    {
                                        ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                        arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                       // barData.setBarWidth(2.1f);
                                        barDataSet = new BarDataSet(arrayList4," " + jsonObject.getString("time") + "             ");
                                       // barDataSet.setFormLineWidth(3f);
                                        barDataSet.setColors(0xffcc9900);
                                        barData.addDataSet(barDataSet);
                                        barChart.setFitBars(true);
                                    }
                                    txtnd.setText(jsonObject.getString("nhietdo") + " C ");
                                    txtda.setText(jsonObject.getString("doam") + " % ");
                                    txtmq135.setText(jsonObject.getString("density")+ "μg/m3");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    barData.setBarWidth(.3f);
                    barChart.setData(barData);
                    barChart.getDescription().setText("Mật độ không khí");
                    barChart.invalidate();
                    barChart.animateY(2000);
                  // axx();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
               Toast.makeText(tranghienthi.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        c1.setBackgroundColor(0xff01b0f1);
        c2.setBackgroundColor(0xffffff01);
        c3.setBackgroundColor(0xffffbe00);
        c4.setBackgroundColor(0xfffe0000);
        c5.setBackgroundColor(0xffcc9900);
        c1.setText("Tốt");
        c2.setText("Trung Bình");
        c3.setText("Kém");
        c4.setText("Xấu");
        c5.setText("Nguy Hại");
        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }
    public void axx()
    {
        if(arrayList5.size() > 0) {
            for (int i = 0; i < arrayList5.size(); ++i) {
                String nd = arrayList5.get(i).getNhietdo().toString() + " ";
                String da = arrayList5.get(i).getDoam().toString() + " ";
                String mq135 = arrayList5.get(i).getMq135().toString() + " ";
                String time = arrayList5.get(i).getTime().toString() + " ";
                String date = arrayList5.get(i).getDate().toString() + " ";
                String density = arrayList5.get(i).getTime().toString() + " ";
                Toast.makeText(tranghienthi.this, nd, Toast.LENGTH_SHORT).show();
                dl.truyvankhongtrakq("INSERT INTO ThongTin VALUES(null,'" + nd + "','" + da + "','" + mq135 + "','" + density + "','" + time + "','" + date + "')");
            }
        }
    }
    private void doc2(String chuoi)
    {
        barChart.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,chuoi,null, new Response.Listener<JSONArray>()
        {
            double d = 0.0;
            @Override
            public void onResponse(JSONArray response)
            {
                BarData barData = new BarData();
                BarDataSet barDataSet1;
                BarDataSet barDataSet;
                if (response.length() > 1) {
                    for (int i = response.length() - 1; i > 0  ; --i)
                    {
                        try {
                            {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String nd = jsonObject.getString("density").trim();
                                if(nd == "")
                                {
                                    d = 0.0;
                                }
                                else {
                                    d = Double.parseDouble(nd);
                                }
                                // float d = 120;
                                arrayList.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                if (d >= 0.0 || d <= 15.4) {
                                    ArrayList<BarEntry> arrayList2 = new ArrayList<>();
                                    arrayList2.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet1 = new BarDataSet(arrayList2, " " + jsonObject.getString("time") + "            ");
                                    barDataSet1.setColors(0xff01b0f1);
                                    barData.addDataSet(barDataSet1);
                                } else if (d >= 15.5 || d <= 40.4) {
                                    ArrayList<BarEntry> arrayList3 = new ArrayList<>();
                                    arrayList3.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList3, " " + jsonObject.getString("time") + "            ");
                                    barDataSet.setColors(0xffffff01);
                                    barData.addDataSet(barDataSet);

                                } else if(d >= 40.5 || d <= 65.4){
                                    ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                    arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList4, " " + jsonObject.getString("time") + "            ");
                                    barDataSet.setColors(0xffffbe00);
                                    barDataSet.setFormSize(3f);
                                }
                                else if(d >= 65.5 || d <= 150.4)
                                {
                                    ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                    arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList4, " " + jsonObject.getString("time") + "            ");
                                    barDataSet.setColors(0xfffe0000);
                                    barData.addDataSet(barDataSet);
                                }
                                else if(d >= 150.5 || d <= 250.4)
                                {
                                    ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                    arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList4," " + jsonObject.getString("time") + "             ");
                                    barDataSet.setColors(0xffcc9900);
                                    barData.addDataSet(barDataSet);
                                    barChart.setFitBars(true);
                                }
                                else if(d >= 250.5 && d <= 350.4)
                                {
                                    ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                    arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList4," " + jsonObject.getString("time") + "             ");
                                    barDataSet.setColors(0xffff0000);
                                    barData.addDataSet(barDataSet);
                                    barChart.setFitBars(true);
                                }
                                else if(d >= 350.5 || d <= 500.4)
                                {
                                    ArrayList<BarEntry> arrayList4 = new ArrayList<>();
                                    arrayList4.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList4," " + jsonObject.getString("time") + "             ");
                                    barDataSet.setColors(0xffa60331);
                                    barData.addDataSet(barDataSet);
                                    barChart.setFitBars(true);
                                }
                                txtnd.setText(jsonObject.getString("nhietdo") + " C ");
                                txtda.setText(jsonObject.getString("doam") + " % ");
                                txtmq135.setText(jsonObject.getString("mq135") +  "µm");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    barData.setBarWidth(.3f);
                    barChart.setData(barData);
                    barChart.getDescription().setText("Nồng độ bụi");
                    barChart.invalidate();
                    barChart.animateY(2000);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(tranghienthi.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
        c1.setBackgroundColor(0xff01b0f1);
        c2.setBackgroundColor(0xffffff01);
        c3.setBackgroundColor(0xffffbe00);
        c4.setBackgroundColor(0xfffe0000);
        c5.setBackgroundColor(0xffcc9900);
        c6.setBackgroundColor(0xffff0000);
        c7.setBackgroundColor(0xffa60331);
        c1.setText("Tốt");
        c2.setText("Trung Bình");
        c3.setText("Ảnh hưởng đến nhóm nhạy cảm");
        c4.setText("Tác động xấu đến sức khỏe");
        c5.setText("Tác động rất xấu đến sức khỏe");
        c6.setText("Nguy hiễm");
        c7.setText("Rất nguy hiểm");
        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }
}
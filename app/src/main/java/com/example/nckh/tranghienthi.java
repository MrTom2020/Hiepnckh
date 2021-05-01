package com.example.nckh;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nckh.Adapter.trangAdp;
import com.example.nckh.SQL.dulieusqllite;
import com.example.nckh.Service.ConnectionReceiver;
import com.example.nckh.model.thongtin;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class tranghienthi extends Activity {


    private  BarChart barChart;
    private CheckBox c1,c2,c3,c4,c5,c6,c7;
    private Button btnmdbui,btnmdkk;
    private TextView txtnd,txtda,txtmq135,txttt;
    private ImageView imageView;
    private ImageButton imageButton2;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String tt ="";
    private Double d;
    private int nn = 1;
    private Double kk;
    private dulieusqllite dl;
    private RelativeLayout relativeLayout;
    private BarData barData = new BarData();
    private BarDataSet barDataSet;
    private Cursor cursor;
    private String chuoi = "https://hiep2020.000webhostapp.com/nhut/ESPselectdatabase.php";
    private ArrayList<BarEntry> arrayList = new ArrayList<>();
    private  LineChart lineChart;
    private ArrayList<thongtin> arrayList5 = new ArrayList<>(),arrayListtt = new ArrayList<>();
    private NotificationManagerCompat notificationManagerCompat;
    private trangAdp adapter;
    private ProgressDialog progressDialog;
    private String kq = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranghienthi);
        dangkynut();
        notificationManagerCompat =NotificationManagerCompat.from(this);
        check();
        taodt();
        ax3();
        doc2(chuoi);
        registerForContextMenu(imageButton2);
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
        txtnd = (TextView)findViewById(R.id.txtnd);
        txtda =(TextView)findViewById(R.id.txtda);
        btnmdbui = (Button)findViewById(R.id.btnmdbui);
        btnmdkk = (Button)findViewById(R.id.btnmdkk);
        imageButton2 = (ImageButton)findViewById(R.id.imageButton);
        txtnd.setTextSize(13f);
        txttt = (TextView)findViewById(R.id.txtttht);
        imageView = (ImageView)findViewById(R.id.imageView2);
        txtmq135 = (TextView)findViewById(R.id.textView9);
        c1.setEnabled(false);
        c2.setEnabled(false);
        c3.setEnabled(false);
        c4.setEnabled(false);
        c5.setEnabled(false);
        c6.setEnabled(false);
        c7.setEnabled(false);
    }
    private void taodt()
    {
        dl = new dulieusqllite(this,"dulieunguoidung.sqlite",null,1);
        dl.truyvankhongtrakq("CREATE TABLE IF NOT EXISTS ThongTin(ID INTEGER PRIMARY KEY AUTOINCREMENT,nhietdo VARCHAR(50),doam VARCHAR(50),mq135 VARCHAR(50),density VARCHAR(50),time VARCHAR(50),date VARCHAR(50))");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case  R.id.mottuan:
                ClearAllDataInOneWeenk();
                break;
            case R.id.motthang:
                ClearAllDataInOneMonth();
                break;
            case R.id.clear_t:
                ClearAllData();
                break;
            case R.id.tatca:
                ClearAllDatA();
            case R.id.dddl:
                DialoglistData();
                break;
            case R.id.battb:
                break;
            case R.id.save:
                GetAskUser();
                break;
        }
        return super.onContextItemSelected(item);
    }
    public void ax3()
    {
        barChart.clear();
        barData.clearValues();
        arrayList = new ArrayList<>();
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("lichsu");
            databaseReference.limitToLast(3).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
                {
                    Random r1 = new Random(),r2 = new Random();
                    int k11 = r1.nextInt(100000);
                    int k22 = r2.nextInt(100000);
                    String kkk = String.valueOf(nn);
                    Toast.makeText(tranghienthi.this,snapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
                    String nd = snapshot.child("nhietdo").getValue().toString();
                    String da= snapshot.child("doam").getValue().toString();
                    String tg= snapshot.child("Thoigian").getValue().toString();
                    String clkk= snapshot.child("chatluongkk").getValue().toString();
                    String mdb= snapshot.child("matdobui").getValue().toString();
                    double dd = Double.parseDouble(clkk);
                    doc3(kkk,nd,da,clkk,mdb,tg,dd);
                    nn++;
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        c1.setBackgroundColor(0xff01b0f1);
        c2.setBackgroundColor(0xffffff01);
        c3.setBackgroundColor(0xffffbe00);
        c4.setBackgroundColor(0xfffe0000);
        c5.setBackgroundColor(0xffcc9900);
        c1.setText ("Good");
        c2.setText ("Average");
        c3.setText ("Poor");
        c4.setText ("Bad");
        c5.setText ("Dangerous");
    }
    private void ClearAllData()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("DO YOU WANT TO DELETE ALL DATA");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dl.truyvankhongtrakq("delete FROM ThongTin");
                Toast.makeText(tranghienthi.this,"The data was successfully deleted",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog dialog2 = builder.create();
        dialog2.show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }


    private void ClearAllDataInOneMonth()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to delete data for a month or more");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                OneMonth();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    private void OneMonth()
    {
        String chain_sql = "https://hiep2020.000webhostapp.com/nhut/ClearDataAllDataOneMonth.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, chain_sql, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(response.trim().equals("YES"))
                {
                    Toast.makeText(tranghienthi.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(tranghienthi.this,"Deletion failed",Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
    private void ClearAllDataInOneWeenk()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to delete data for a week or more");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                OneWeenk();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    private void OneWeenk()
    {
        String chain_sql = "https://hiep2020.000webhostapp.com/nhut/ClearDataOneInWeenk.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, chain_sql, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(response.trim().equals("YES"))
                {
                    Toast.makeText(tranghienthi.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(tranghienthi.this,"Deletion failed",Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
    private void ClearAllDatA()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to delete all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ALL();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    private void ALL()
    {
        String chain_sql = "https://hiep2020.000webhostapp.com/nhut/ClearAllData.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, chain_sql, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(response.trim().equals("YES"))
                {
                    Toast.makeText (tranghienthi.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(tranghienthi.this, "Deletion failed", Toast.LENGTH_SHORT).show ();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(tranghienthi.this,"Please reload the app",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void dangkysukien()
    {
        btnmdkk.setOnClickListener(new sukiencuatoi());
        btnmdbui.setOnClickListener(new sukiencuatoi());
    }
    private void check()
    {
        boolean ret = ConnectionReceiver.isConnected();
        String ms;
        if(ret == true)
        {
            ms = "The device has an Internet connection and can be done online";
            tt = "ok";
            btnmdkk.setEnabled(true);
            btnmdbui.setEnabled(true);
        }
        else
        {
            ms = "The device does not have an Internet connection and can be performed offline";
            tt = "ko";
            btnmdkk.setEnabled(false);
            btnmdbui.setEnabled(false);
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
                //doc(chuoi);
                ax3();
            }
            if(view.equals(btnmdbui))
            {
                doc2(chuoi);
            }
        }
    }
    private void DialoglistData()
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_danhsachluu);
        int w = ViewGroup.LayoutParams.MATCH_PARENT;
        int h = ViewGroup.LayoutParams.MATCH_PARENT;
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
    private void doc3(String key,String nhietdo,String doam,String clkk,String mdbui,String tg,double t)
    {
        ArrayList<BarEntry> arrayList3 = new ArrayList<>();
        //arrayList3.clear();
        arrayList3.add(new BarEntry(Float.parseFloat(key), Float.parseFloat(clkk)));
        barDataSet = new BarDataSet(arrayList3, " " + tg + "            ");
        if (t >= 0 && t <= 51)
        {
            barDataSet.setColors(0xff01b0f1);
            barDataSet.setValueTextSize(3f);
        } else if (t > 50 && t < 101)
        {
            barDataSet.setColors(0xffffff01);
            barDataSet.setValueTextSize(3f);
        }
        else if ( t > 100 && t < 201)
        {
            barDataSet.setColors(0xffffbe00);
            barDataSet.setValueTextSize(3f);
        }
        else if ( t > 200 && t < 301)
        {
            barDataSet.setColors(0xfffe0000);
            barDataSet.setValueTextSize(3f);
        }
        else
            {
                barDataSet.setColors(0xffcc9900);
                barDataSet.setBarBorderWidth(1f);
            }
        barData.addDataSet(barDataSet);
        kk = mdbui == "" ? Double.parseDouble(mdbui) : 0;
        txtnd.setText(nhietdo + " C ");
        txtnd.setCompoundDrawablesWithIntrinsicBounds(R.drawable.thermometer, 0, 0, 0);
        txtda.setText(" : " + doam + " % ");
        txtda.setCompoundDrawablesWithIntrinsicBounds(R.drawable.droplets, 0, 0, 0);
        txtmq135.setText(" PM 2.5 " + mdbui + "μg/m3");
        //GetRults(d,kk);
        barChart.setData(barData);
        barChart.setBackgroundColor(0xff333333);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setXEntrySpace(19f);
        barChart.getXAxis().setEnabled(false);
        barChart.getDescription().setText("AQI");
        barChart.invalidate();

    }
    private void doc(String chuoi)
    {
        arrayList5 = new ArrayList<>();
        barChart.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,chuoi,null, new Response.Listener<JSONArray>()
        {
            double d = 0.0;
            Double kk = 0.0;
            @Override
            public void onResponse(JSONArray response)
            {
                int sl = 5;
                if (response.length() > 1) {
                    for (int i = response.length() - 1; i > 0  ; --i)
                    {
                        try {
                            {
                                if(sl > 0) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String nd = jsonObject.getString("mq135").trim();
                                    String doam = jsonObject.getString("doam");
                                    String nhietdo = jsonObject.getString("nhietdo").trim();
                                    String density = jsonObject.getString("density");
                                    String time = jsonObject.getString("time").trim();
                                    String date = jsonObject.getString("date");
                                    arrayList5.add(new thongtin(id, nhietdo, doam, nd, density, time, date));
                                    if (nd == "")
                                    {
                                        d = 0.0;
                                    } else {
                                        d = Double.parseDouble(nd);
                                    }
                                    ArrayList<BarEntry> arrayList3 = new ArrayList<>();
                                    arrayList3.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList3, " " + jsonObject.getString("time") + "            ");
                                    if (d > 0 && d <= 51) {
                                        barDataSet.setColors(0xff01b0f1);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 50 && d < 101) {
                                        barDataSet.setColors(0xffffff01);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 100 && d < 201) {
                                        barDataSet.setColors(0xffffbe00);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 200 && d < 301) {
                                        barDataSet.setColors(0xfffe0000);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else {
                                        barDataSet.setColors(0xffcc9900);
                                        barDataSet.setBarBorderWidth(1f);
                                    }
                                    barData.addDataSet(barDataSet);
                                    kk = jsonObject.getString("density") == "" ? Double.parseDouble(jsonObject.getString("density")) : 0;
                                    txtnd.setText(jsonObject.getString("nhietdo") + " C ");
                                    txtnd.setCompoundDrawablesWithIntrinsicBounds(R.drawable.thermometer, 0, 0, 0);
                                    txtda.setText(" : " + jsonObject.getString("doam") + " % ");
                                    txtda.setCompoundDrawablesWithIntrinsicBounds(R.drawable.droplets, 0, 0, 0);
                                    txtmq135.setText(" PM 2.5 " + jsonObject.getString("density") + "μg/m3");
                                    sl--;
                                }
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                    GetRults(d,kk);
                    barChart.setData(barData);
                    barChart.getAxisRight().setEnabled(false);
                    barChart.getLegend().setXEntrySpace(19f);
                    barChart.getXAxis().setEnabled(false);
                    barChart.getDescription().setText("AQI");
                    barChart.invalidate();
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
        c1.setText ("Good");
        c2.setText ("Average");
        c3.setText ("Poor");
        c4.setText ("Bad");
        c5.setText ("Dangerous");
        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }

    private void GetRults(double kk, double bui)
    {
        txttt.setText("");
        if (kk > 0 && kk <= 51)
        {
            txttt.setText("AQI: Clear Air \n");
        }
         if (kk > 50 && kk < 101)
        {
            txttt.setText("AQI : Sensitive groups should limit out. \n");
        }
        if(kk > 100 && kk <201)
        {
            txttt.setText("AQI : Sensitive groups should limit out. \n");
        }
        if(kk > 200 && kk < 301)
        {
            txttt.setText("AQI : Sensitive groups should limit out. \n");
        }
        if(kk > 300)
        {
            txttt.setText("AQI : Everyone should stay indoors \n");
        }
        if (bui > -1 && bui < 15.5)
        {
            txttt.append("PM 2.5: Good \n");
            imageView.setBackgroundResource(R.drawable.userfuny);
        }
        if (bui > 15.4 && bui <= 40.5)
        {
            txttt.append("PM 2.5 : Medium \n");
            imageView.setBackgroundResource(R.drawable.userfuny);
        }
        if(bui > 40.4 && bui <= 65.5)
        {
            txttt.append("PM 2.5 :  Affects sensitive groups \n");
            imageView.setBackgroundResource(R.drawable.annoyeduser);
        }
        if(bui > 65.4 && bui <= 150.5)
        {
            txttt.append("PM 2.5 : Bad effects on health. ");
            imageView.setBackgroundResource(R.drawable.rattoite);
        }
        if(bui > 150.4 && bui <= 250.5)
        {
            txttt.append("PM 2.5 : Bad effects on health. \n");
            imageView.setBackgroundResource(R.drawable.rattoite);
        }
        if(bui > 250.4 && bui < 350.5)
        {
            txttt.append("PM 2.5 : Danger \n");
            imageView.setBackgroundResource(R.drawable.rattoite);
        }
        if(bui > 350.4 && bui < 500.5)
        {
            txttt.append("PM 2.5 : Very dangerous \n");
            imageView.setBackgroundResource(R.drawable.rattoite);
        }
    }
    public void axx()
    {
        try {
            taodt();
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Waiting please.....");
            progressDialog.show();
            if (arrayList5.size() > 0) {
                for (int i = 0; i < arrayList5.size(); ++i) {
                    String nd = arrayList5.get(i).getNhietdo().toString() + " ";
                    String da = arrayList5.get(i).getDoam().toString() + " ";
                    String mq135 = arrayList5.get(i).getMq135().toString() + " ";
                    String time = arrayList5.get(i).getTime().toString() + " ";
                    String date = arrayList5.get(i).getDate().toString() + " ";
                    String density = arrayList5.get(i).getDensity().toString() + " ";
                    dl.truyvankhongtrakq("INSERT INTO ThongTin VALUES(null,'" + nd + "','" + da + "','" + mq135 + "','" + density + "','" + time + "','" + date + "')");
                }
            }
            arrayList5.clear();
            progressDialog.dismiss();
        }
        catch (Exception e)
        {
            Toast.makeText(tranghienthi.this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    private void GetAskUser()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NOTICE");
        builder.setMessage("Do you have save file today ??");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                axx();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    private void doc2(String chuoi)
    {
        arrayList5 = new ArrayList<>();
        barChart.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,chuoi,null, new Response.Listener<JSONArray>()
        {
            double d = 0.0;
            Double kk = 0.0;
            @Override
            public void onResponse(JSONArray response)
            {
                BarData barData = new BarData();
                BarDataSet barDataSet1;
                BarDataSet barDataSet;
                int sl = 5;
                if (response.length() > 1)
                {
                    for (int i = response.length() - 1; i >= 0  ; --i)
                    {
                        try {
                            {
                                if(sl > 0) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String nd = jsonObject.getString("density").trim();
                                    String doam = jsonObject.getString("doam");
                                    String nhietdo = jsonObject.getString("nhietdo").trim();
                                    String density = jsonObject.getString("density");
                                    String time = jsonObject.getString("time").trim();
                                    String date = jsonObject.getString("date");
                                    arrayList5.add(new thongtin(id, nhietdo, doam, nd, density, time, date));
                                    if (nd == "") {
                                        d = 0.0;
                                    } else {
                                        d = Double.parseDouble(nd);
                                    }
                                    ArrayList<BarEntry> arrayList2 = new ArrayList<>();
                                    arrayList2.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    barDataSet = new BarDataSet(arrayList2, " " + jsonObject.getString("time") + "            ");
                                    arrayList.add(new BarEntry(Float.parseFloat(id), Float.parseFloat(nd)));
                                    if (d > -1 && d < 15.5) {
                                        barDataSet.setColors(0xff01b0f1);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 15.4 && d <= 40.5) {
                                        barDataSet.setColors(0xffffff01);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 40.4 && d <= 65.5) {
                                        barDataSet.setColors(0xffffbe00);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 65.4 && d <= 150.5) {
                                        barDataSet.setColors(0xfffe0000);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 150.4 && d <= 250.5) {
                                        barDataSet.setColors(0xffcc9900);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 250.4 && d < 350.5) {
                                        barDataSet.setColors(0xffff0000);
                                        barDataSet.setBarBorderWidth(1f);
                                    } else if (d > 350.4 && d < 500.5) {
                                        barDataSet.setColors(0xffa60331);
                                        barDataSet.setBarBorderWidth(1f);
                                    }
                                    barData.addDataSet(barDataSet);
                                    kk = jsonObject.getString("mq135").toString() == "" ? Double.parseDouble(jsonObject.getString("mq135")) : 0;
                                    txtnd.setText(jsonObject.getString("nhietdo") + " C ");
                                    txtnd.setCompoundDrawablesWithIntrinsicBounds(R.drawable.thermometer, 0, 0, 0);
                                    txtda.setText("  : " + jsonObject.getString("doam") + " % ");
                                    txtda.setCompoundDrawablesWithIntrinsicBounds(R.drawable.droplets, 0, 0, 0);
                                    txtmq135.setText(" AQI : " + jsonObject.getString("mq135"));
                                    sl--;
                                }
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                    GetRults(d,kk);
                    barChart.setData(barData);
                    barChart.getAxisRight().setEnabled(false);
                    barChart.getLegend().setXEntrySpace(19f);
                    barChart.getXAxis().setEnabled(false);
                    barChart.getDescription().setText("PM 2.5");
                    barChart.invalidate();
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
        c1.setText ("Good");
        c2.setText ("Average");
        c3.setText ("Affect sensitive groups");
        c4.setText ("Adverse to health");
        c5.setText ("Very bad impact on health");
        c6.setText ("Dangerous");
        c7.setText ("Very dangerous");
        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }
}
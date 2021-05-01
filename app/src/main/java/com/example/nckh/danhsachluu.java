package com.example.nckh;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nckh.Adapter.trangAdp;
import com.example.nckh.SQL.dulieusqllite;
import com.example.nckh.model.thongtin;

import java.util.ArrayList;

public class danhsachluu extends Activity
{
    private ListView listView;
    private ArrayList<thongtin> arrayList;
    private trangAdp adapter;
    private Cursor cursor;
    private dulieusqllite dl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachluu);
    }
}
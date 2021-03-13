package com.example.nckh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class tranghai extends TabActivity {

    private TabHost tabHost;
    private TabHost.TabSpec tabSpec;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranghai);
        dangkynut();
        ax();
    }
    private void dangkynut()
    {
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
    }
    private void dangkysk()
    {

    }
    private void check()
    {
        boolean ret = ConnectionReceiver.isConnected();
        String ms;
        if(ret == true)
        {
            ms = "Thiết bị có kết nối Internet và có thể tiến hành online";
        }
        else
        {
            ms = "Thiết bị không có kết nối Internet và có thể tiến hành offline";
        }
        Toast.makeText(tranghai.this,ms,Toast.LENGTH_SHORT).show();
    }
    private void ax()
    {
      tabSpec = tabHost.newTabSpec("Thông tin");
       tabSpec.setIndicator("Thông tin");
       intent = new Intent(this,tranghienthi.class);
       tabSpec.setContent(intent);
       tabHost.addTab(tabSpec);

       tabSpec = tabHost.newTabSpec("Tài khoản");
       tabSpec.setIndicator("Tài khoản");
       intent = new Intent(this,trangcanhan.class);
       tabSpec.setContent(intent);
       tabHost.addTab(tabSpec);
       tabHost.setCurrentTab(1);

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

        }
    }
}
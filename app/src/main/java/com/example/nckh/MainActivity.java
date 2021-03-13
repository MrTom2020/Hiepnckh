package com.example.nckh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {

    private   EditText edtten,edtmk;
    private Button btndn,btnfb,btngg,btndk;
    private FirebaseAuth firebaseAuth;
    private Intent intent;
    private CheckBox checkBox;
    static  int c = 12345;
    static String tend,ten1;
    private Cursor cursor;
    private dulieusqllite dl;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dangkynut();
        check();
        ax();
        dangkysukien();
        
    }
    private void check()
    {
        boolean ret = ConnectionReceiver.isConnected();
        String ms;
        if(ret == true)
        {
            ms = "Thiết bị có kết nối Internet và có thể tiến hành online";
            ten1= "ok";
        }
        else
        {
            ms = "Thiết bị không có kết nối Internet và có thể tiến hành offline";
            ten1 = "ko";
            btndk.setEnabled(false);
            btnfb.setEnabled(false);
        }
        Toast.makeText(MainActivity.this,ms,Toast.LENGTH_SHORT).show();
    }
    private void kiemtra()
    {
        dl = new dulieusqllite(MainActivity.this,"dulieunguoidung.sqlite",null,1);
        cursor = dl.truyvancoketqua("SELECT * FROM nguoidung WHERE ten='"+edtten.getText().toString()+"' AND matkhau='"+edtmk.getText().toString()+"'");
        if(cursor != null)
        {
            intent = new Intent(MainActivity.this,tranghai.class);
            while (cursor.moveToNext())
            {
                 tend = cursor.getString(0).toString();
                Toast.makeText(MainActivity.this,tend,Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onStart()
    {
        check();
        super.onStart();
    }

    private void dangkynut()
    {
        edtten = (EditText)findViewById(R.id.use);
        edtmk = (EditText)findViewById(R.id.pass);
        btndn = (Button)findViewById(R.id.button);
        btnfb = (Button)findViewById(R.id.fb);
        btngg = (Button)findViewById(R.id.gg);
        btndk = (Button)findViewById(R.id.btndangkytk);
        checkBox = (CheckBox)findViewById(R.id.ck1);
        edtten.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_UP)
                {
                    if(edtten.getText().toString().trim().length() < 1)
                    {
                        btndn.setEnabled(false);
                        edtten.setBackgroundColor(0xffffffff);
                    }
                    else
                    {
                        edtten.setBackgroundColor(0xfffff000);
                    }
                }
                return false;
            }
        });
        edtmk.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_UP)
                {
                    if(edtmk.getText().toString().trim().length() < 1)
                    {
                        btndn.setEnabled(false);
                       edtmk.setBackgroundColor(0xffffffff);
                    }
                    else
                    {
                        edtmk.setBackgroundColor(0xfffff000);
                        btndn.setEnabled(true);
                    }
                }
                return false;
            }
        });

    }
    private void dangkysukien()
    {
        btndn.setOnClickListener(new sukiencuatoi());
        btngg.setOnClickListener(new sukiencuatoi());
        btnfb.setOnClickListener(new sukiencuatoi());
        btndk.setOnClickListener(new sukiencuatoi());
    }

    private class sukiencuatoi implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
          if(view.equals(btndn))
          {
             if(ten1 == "ok")
             {
                dangnhap();
             }
            if(ten1 == "ko")
             {
                // Toast.makeText(MainActivity.this,ten1,Toast.LENGTH_SHORT).show();
                 kiemtra();
             }
          }
          if(view.equals(btnfb))
          {

          }
          if (view.equals(btngg))
          {

          }
          if(view.equals(btndk))
          {
              Intent intent = new Intent(MainActivity.this,trangdangky.class);
              startActivity(intent);
          }
        }

    }
    private void ax()
    {

    }
    private void dangnhap()
    {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Vui lòng chờ trong giây lát");
        progressDialog.show();
        String ten = edtten.getText().toString();
        String mk = edtmk.getText().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(ten.trim(),mk.trim()).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    intent = new Intent(MainActivity.this,tranghai.class);
                    tend = firebaseAuth.getCurrentUser().getUid().toString();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Đăng nhập không thành công",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

        });
    }
}
package com.example.nckh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {

    private   EditText edtten,edtmk;
    private Button btndn,btndk;
    private TextView txt;
    private FirebaseAuth firebaseAuth;
    private Intent intent;
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
        dangkysukien();
        
    }
    private void check()
    {
        boolean ret = ConnectionReceiver.isConnected();
        String ms;
        if (ret == true)
        {
            ms = "The device has an Internet connection and can be done online";
            ten1 = "ok";
        }
        else
        {
            ms = "The device has no Internet connection and can be performed offline";
            ten1 = "ko";
            btndk.setEnabled (false);
        }
        Toast.makeText(MainActivity.this,ms,Toast.LENGTH_SHORT).show();
    }
    private void kiemtra()
    {
        try {
            dl = new dulieusqllite(MainActivity.this, "dulieunguoidung.sqlite", null, 1);
            cursor = dl.truyvancoketqua("SELECT * FROM nguoidung WHERE ten='" + edtten.getText().toString().trim() + "' AND matkhau='" + edtmk.getText().toString().trim() + "'");
            if (cursor != null)
            {
               if(cursor.getCount() == 0)
                {
                    Toast.makeText(MainActivity.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
                }
                intent = new Intent(MainActivity.this, tranghai.class);
                while (cursor.moveToNext())
                {
                    tend = cursor.getString(0).toString();
                    Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    edtten.setText("");
                    edtmk.setText("");
                    startActivity(intent);
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,"Login unsuccessful",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        Log.i("key pressed", String.valueOf(event.getKeyCode()));
        return super.dispatchKeyEvent(event);
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
        btndk = (Button)findViewById(R.id.btndangkytk);
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
        btndk.setOnClickListener(new sukiencuatoi());
    }

    private class sukiencuatoi implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
          if(view.equals(btndn))
          {
             if(ten1.equals("ok"))
             {
                dangnhap();
             }
            if(ten1.equals("ko"))
             {
                 kiemtra();
             }
          }
          if(view.equals(btndk))
          {
              Intent intent = new Intent(MainActivity.this,trangdangky.class);
              startActivity(intent);
          }
        }

    }

    private void dangnhap()
    {
        try {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait a moment");
            progressDialog.show();
            String ten = edtten.getText().toString();
            String mk = edtmk.getText().toString();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(ten.trim(), mk.trim()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        intent = new Intent(MainActivity.this, tranghai.class);
                        tend = firebaseAuth.getCurrentUser().getUid().toString();
                        startActivity(intent);
                    } else
                        {
                           progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                    }
                    progressDialog.dismiss();
                }

            });
        }
        catch (Exception e)
        {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this,"Login unsuccessful",Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.nckh;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class trangAdp extends BaseAdapter
{
    public Context context;
    public int layout;
    public List<thongtin> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class viewholer
    {
        TextView txtthoigian,txtngay,txtnhietdo,txtclkk,txtmdbui,txtdam,txttt;
        ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewholer vh;
        if(convertView == null)
        {
            vh = new viewholer();
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);
            vh.txtthoigian = (TextView)convertView.findViewById(R.id.txttgioluu);
            vh.txtngay = (TextView)convertView.findViewById(R.id.txtthoigian);
            vh.txtdam  = (TextView)convertView.findViewById(R.id.txtdam);
            vh.txtclkk = (TextView)convertView.findViewById(R.id.txtclkk);
            vh.txtnhietdo = (TextView)convertView.findViewById(R.id.txtnhietdo);
            vh.txtmdbui = (TextView)convertView.findViewById(R.id.txtmdbui);
            vh.txttt = (TextView)convertView.findViewById(R.id.txttt);
            vh.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(vh);
        }
        else {
            vh = (viewholer)convertView.getTag();
        }
        thongtin tt = list.get(position);
        vh.txtthoigian.setText(tt.getTime());
        vh.txtmdbui.setText(tt.getDensity());
        vh.txtnhietdo.setText(tt.getNhietdo()+ "C");
        vh.txtngay.setText(tt.getDate());
        vh.txtclkk.setText(tt.getMq135());
        vh.txtdam.setText(tt.getDoam() + "%");
        double d = vh.txtclkk.getText().toString() == "" ? Double.parseDouble(vh.txtclkk.getText().toString()):0;
        double bui = vh.txtmdbui.getText().toString() == "" ? Double.parseDouble(vh.txtmdbui.getText().toString()):0;
        if(d > -1 && d < 51)
        {
            vh.txtclkk.setBackgroundColor(0xff01b0f1);
            vh.imageView.setBackgroundResource(R.drawable.userfuny);
            vh.txttt.setText("Air quality : " + "Does not affect health");
        }
        if(d > 50 && d < 101)
        {
            vh.txtclkk.setBackgroundColor(0xffffff01);
            vh.txttt.setText("Air quality : " + "Sensitive groups should limit their time outside");
        }
        if(d >=100 && d < 201)
        {
            vh.txtclkk.setBackgroundColor(0xffffbe00);
            vh.imageView.setBackgroundResource(R.drawable.userfuny);
            vh.txttt.setText("Air quality : " + "Sensitive groups should limit their time outside");
        }
        if(d > 200 && d < 301)
        {
            vh.txtclkk.setBackgroundColor(0xfffe0000);
            vh.imageView.setBackgroundResource(R.drawable.userfuny);
            vh.txttt.setText("Air quality : " + "Sensitive groups should limit their time outside");
        }
        if(d > 301)
        {
            vh.txtclkk.setBackgroundColor(0xffcc9900);
            vh.imageView.setBackgroundResource(R.drawable.userfuny);
            vh.txttt.setText("Air quality : " + "Everyone should stay indoors");
        }
        if(bui > -1 && bui < 15.5)
        {
            vh.txtmdbui.setBackgroundColor(0xff01b0f1);
            vh.imageView.setBackgroundResource(R.drawable.userfuny);
            vh.txttt.append("Dust density : " +"Good");
        }
        if (bui> 15.4 && bui <40.5)
        {
            vh.txtmdbui.setBackgroundColor (0xffffff01);
            vh.imageView.setBackgroundResource (R.drawable.userfuny);
            vh.txttt.append ("Dust density:" + "Average");
        }
        if (bui> 40.4 && bui <65.5)
        {
            vh.txtmdbui.setBackgroundColor (0xffffbe00);
            vh.imageView.setBackgroundResource (R.drawable.annoyeduser);
            vh.txttt.append ("Dust density:" + "Affect sensitive group");
        }
        if (bui> 65.4 && bui <150.5)
        {
            vh.txtmdbui.setBackgroundColor (0xfffe0000);
            vh.imageView.setBackgroundResource (R.drawable.annoyeduser);
            vh.txttt.append ("Dust density:" + "Adverse to health");
        }
        if (bui> 150.4 && bui <250.5)
        {
            vh.txtmdbui.setBackgroundColor (0xffcc9900);
            vh.imageView.setBackgroundResource (R.drawable.rattoite);
            vh.txttt.append ("Dust density:" + "Very bad impact on health");
        }
        if (bui> 250.4 && bui <350.5)
        {
            vh.txtmdbui.setBackgroundColor (0xffff0000);
            vh.imageView.setBackgroundResource (R.drawable.rattoite);
            vh.txttt.append ("Dust density:" + "Danger");
        }
        if (bui> 350.4 && bui <500.5)
        {
            vh.txtmdbui.setBackgroundColor (0xffa60331);
            vh.imageView.setBackgroundResource (R.drawable.rattoite);
            vh.txttt.append ("Dust density:" + "Very dangerous");
        }
        return convertView;
    }

    public trangAdp(Context context, int layout, List<thongtin> list)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }
}

package com.example.nckh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView txtthoigian,txtngay,txtnhietdo,txtclkk,txtmdbui,txtdam;
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
            convertView.setTag(vh);
        }
        else {
            vh = (viewholer)convertView.getTag();
        }
        thongtin tt = list.get(position);
        vh.txtthoigian.setText(tt.getTime());
        vh.txtmdbui.setText(tt.getDensity());
        vh.txtnhietdo.setText(tt.getNhietdo());
        vh.txtngay.setText(tt.getDate());
        vh.txtclkk.setText(tt.getMq135());
        vh.txtdam.setText(tt.getDoam());
        return convertView;
    }

    public trangAdp(Context context, int layout, List<thongtin> list)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }
}

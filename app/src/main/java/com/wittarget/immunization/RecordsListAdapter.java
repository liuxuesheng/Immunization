package com.wittarget.immunization;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


class ViewHolder {
    public ImageView img;
    public TextView title;
    public TextView detail;
    public Button book;
}

public class RecordsListAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    public RecordsListAdapter(Context context){
        super();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 50;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_records_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.info_img);
            holder.title = (TextView) convertView.findViewById(R.id.info_title);
            holder.detail = (TextView) convertView.findViewById(R.id.info_details);
           // holder.book = (Button) convertView.findViewById(R.id.info_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.img.setImageResource(R.drawable.ic_home_black_24dp);
        holder.title.setText("This is a record test!");
        holder.detail.setText("This is record description test");

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put your code here
                System.out.println("Click on the speaker image on ListItem ");
            }
        });

        return convertView;
    }

}
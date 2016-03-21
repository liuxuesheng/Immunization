package com.wittarget.immunization.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wittarget.immunization.R;

import java.util.List;
import java.util.Map;


public class ShowProfileAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listItems;
    private LayoutInflater listContainer;

    public ShowProfileAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;
    }

    public int getCount() {
        return listItems.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int selectID = position;
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();

            convertView = listContainer.inflate(R.layout.layout_profile_item, null);

            listItemView.profile_img = (ImageView) convertView.findViewById(R.id.profile_img);
            listItemView.profile_name = (TextView) convertView.findViewById(R.id.profile_name);
            convertView.setTag(listItemView);

            listItemView.profile_img.setBackgroundResource((Integer) listItems.get(
                    position).get("profile_img"));
            listItemView.profile_name.setText((String) listItems.get(
                    position).get("profile_name"));

            if(listItemView.profile_name.getText() == "Babies Management"){
                listItemView.profile_name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        //intent.setClass(context, BabyProfileDisplayActivity.class);
                        intent.setClass(context,BabyManagementActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
            if(listItemView.profile_name.getText() =="About App"){
                listItemView.profile_name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setClass(context, BabyProfileDisplayActivity.class);
//                        context.startActivity(intent);
                    }
                });
            }

        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
        return convertView;
    }


    public final class ListItemView {
        public ImageView profile_img;
        public TextView profile_name;
    }
}

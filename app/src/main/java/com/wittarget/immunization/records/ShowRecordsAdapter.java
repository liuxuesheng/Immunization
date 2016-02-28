package com.wittarget.immunization.records;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wittarget.immunization.LoginActivity;
import com.wittarget.immunization.MainActivity;
import com.wittarget.immunization.R;

import java.util.List;
import java.util.Map;

public class ShowRecordsAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listItems;
    private LayoutInflater listContainer;
    private boolean[] hasChecked;

    public ShowRecordsAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;
        hasChecked = new boolean[getCount()];
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
            //get layout_records_item.xml
            convertView = listContainer.inflate(R.layout.layout_records_item, null);

            listItemView.records_img = (ImageView) convertView.findViewById(R.id.info_img);
            listItemView.records_name = (TextView) convertView.findViewById(R.id.info_title);
            listItemView.records_detail = (TextView) convertView.findViewById(R.id.info_details);
            listItemView.records_check = (CheckBox) convertView.findViewById(R.id.checkBox);

            convertView.setTag(listItemView);

            listItemView.records_img.setBackgroundResource((Integer) listItems.get(
                    position).get("record_img"));
            listItemView.records_name.setText((String) listItems.get(
                    position).get("records_name"));
            listItemView.records_detail.setText((String) listItems.get(
                    position).get("records_detail"));

            listItemView.records_name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //showDetailInfo(selectID);
//                    Intent intent = new Intent();
//                    intent.setClass(context, LoginActivity.class);
//                    context.startActivity(intent);
                }
            });

            listItemView.records_check
                    .setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            checkedChange(selectID);
                        }
                    });
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
        return convertView;
    }

    private void checkedChange(int checkedID) {
        hasChecked[checkedID] = !hasChecked(checkedID);
    }

    public boolean hasChecked(int checkedID) {
        return hasChecked[checkedID];
    }

    private void showDetailInfo(int clickID) {
        new AlertDialog.Builder(context)
                .setIcon(Integer.parseInt(listItems.get(clickID).get("record_img").toString()))
                .setTitle(listItems.get(clickID).get("records_name") + "")
                .setMessage("records:" + listItems.get(clickID).get("records_detail").toString())
                .setPositiveButton("Click me", null)
                .show();
    }

    public final class ListItemView {
        public ImageView records_img;
        public TextView records_name;
        public TextView records_detail;
        public CheckBox records_check;
    }
}
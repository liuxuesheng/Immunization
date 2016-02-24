package com.wittarget.immunization.mainPageFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wittarget.immunization.R;
import com.wittarget.immunization.RecordsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordsFragment extends Fragment {

    //分别定义通讯录中的用户名、电话、地区等信息
    private String[] info_titles = {"DTap-IPV-Hib", "Pneu-C-13", "Rota", "MMR"};
    private String[] info_details = {"recommended for children under 5 years old", "recommended for children under 3 years old", "recommended for children under 2 years old", "recommended for children under 1 years old"};
    //定义一个ArrayList数组，每一条数据对应通讯录中的一个联系人信息
    private ArrayList<Map<String, Object>> mInfos = new ArrayList<Map<String, Object>>();

    //define records info
    private Activity activity = null;
    private String TAG = RecordsFragment.class.getName();
    private ListView list;
    private RecordsListAdapter adapter;

    public static RecordsFragment newInstance(String text) {
        RecordsFragment f = new RecordsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_records, container, false);

        ListView mListView = (ListView) v.findViewById(R.id.recordList);
        //添加联系人信息
        for (int i = 0; i < info_titles.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("img", R.drawable.ic_home_black_24dp);
            item.put("title", info_titles[i]);
            item.put("detail", info_details[i]);
            mInfos.add(item);
        }
        MyAdapter adapter = new MyAdapter(activity, mInfos);
        mListView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    private class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Map<String, Object>> listItems;
        private LayoutInflater listContainer;
        private boolean[] hasChecked;

        public MyAdapter(Context context, List<Map<String, Object>> listItems) {
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
                //获取list_item布局文件的视图
                convertView = listContainer.inflate(R.layout.layout_records_item, null);
                //获取控件对象
                listItemView.img = (ImageView) convertView.findViewById(R.id.info_img);
                listItemView.name = (TextView) convertView.findViewById(R.id.info_title);
                listItemView.region = (TextView) convertView.findViewById(R.id.info_details);
                listItemView.check = (CheckBox) convertView.findViewById(R.id.checkBox);
                //设置控件集到convertView
                convertView.setTag(listItemView);
                //设置联系人信息
                listItemView.img.setBackgroundResource((Integer) listItems.get(
                        position).get("img"));
                listItemView.name.setText((String) listItems.get(
                        position).get("title"));
                listItemView.region.setText((String) listItems.get(
                        position).get("detail"));

                listItemView.check
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
                    .setIcon(Integer.parseInt(listItems.get(clickID).get("img").toString()))
                    .setTitle(listItems.get(clickID).get("name") + "详细信息")
                    .setMessage("电话:" + listItems.get(clickID).get("phone").toString() + " 地区：" + listItems.get(clickID).get("region").toString())
                    .setPositiveButton("确定", null)
                    .show();
        }

        public final class ListItemView {
            public ImageView img;
            public TextView name;
            public TextView phone;
            public TextView region;
            public CheckBox check;
        }
    }


}

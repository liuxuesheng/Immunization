package com.wittarget.immunization.mainPageFragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wittarget.immunization.R;
import com.wittarget.immunization.RecordsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordsFragment extends ListFragment {
    //define records info
    private String TAG = RecordsFragment.class.getName();
    private ListView list;
    private  RecordsListAdapter adapter;

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

        return v;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //分别定义通讯录中的用户名、电话、地区等信息
//        String[] info_titles = {"史珍香", "赖月京", "秦寿生", "刘产", "扬伟", "范剑"};
//        String[] info_details = {"火星", "水星", "木星", "月球", "美国", "未知"};
//        ArrayList<Map<String, Object>> mInfos = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < info_titles.length; i++) {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("img", R.drawable.ic_home_black_24dp);
//            item.put("title", info_titles[i]);
//            item.put("detail", info_details[i]);
//            mInfos.add(item);
//        }
//        adapter = new SimpleAdapter(getActivity(), mInfos, R.layout.layout_records_item, new String[]{"img", "title", "detail"}, new int[]{R.id.info_img, R.id.info_title, R.id.info_details});
//        setListAdapter(adapter);
//
//    }
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // TODO Auto-generated method stub
//        super.onListItemClick(l, v, position, id);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecordsListAdapter(getActivity());
        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("Click On List Item!!!");
        super.onListItemClick(l, v, position, id);
    }

}

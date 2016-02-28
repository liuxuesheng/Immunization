package com.wittarget.immunization.mainPageFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wittarget.immunization.R;
import com.wittarget.immunization.records.ShowRecordsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class recordsFragment extends Fragment {
    //create 2 string array to store records_name and record_detail
    private String[] records_names = {"DTap-IPV-Hib", "Pneu-C-13", "Rota", "MMR"};
    private String[] records_details = {"recommended for children under 5 years old", "recommended for children under 3 years old", "recommended for children under 2 years old", "recommended for children under 1 years old"};
    //minfo is to store records data
    private ArrayList<Map<String, Object>> mInfos = new ArrayList<Map<String, Object>>();

    //define records info
    private Activity activity = null;
    private String TAG = recordsFragment.class.getName();
    private ListView mListView;

    public static recordsFragment newInstance(String text) {
        recordsFragment f = new recordsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_records, container, false);

        //create listview to hold all record data that include: image, immunization name,immunization details
        mListView = (ListView) v.findViewById(R.id.recordList);
        for (int i = 0; i < records_names.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("record_img", R.drawable.ic_home_black_24dp);
            item.put("records_name", records_names[i]);
            item.put("records_detail", records_details[i]);
            mInfos.add(item);
        }
        ShowRecordsAdapter adapter = new ShowRecordsAdapter(activity, mInfos);
        mListView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

}

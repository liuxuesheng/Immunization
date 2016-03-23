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
import com.wittarget.immunization.profile.ShowProfileAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private String[] info_names = {"Babies Management","About App"};
    private ArrayList<Map<String, Object>> mInfos = new ArrayList<Map<String, Object>>();

    private Activity activity = null;
    private String TAG = RecordsFragment.class.getName();
    private ListView mListView;

    public static ProfileFragment newInstance(String text) {
        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView mListView = (ListView) v.findViewById(R.id.profile_list);
        //add new baby info
        for (int i = 0; i < info_names.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            if(i==0){
                item.put("profile_img", R.drawable.ic_baby_36dp);
            }
            else{
                item.put("profile_img", R.drawable.ic_aboutus_36dp);
            }
            item.put("profile_name", info_names[i]);
            mInfos.add(item);
        }
        ShowProfileAdapter adapter = new ShowProfileAdapter(activity, mInfos);
        mListView.setAdapter(adapter);
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }
}

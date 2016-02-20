package com.wittarget.immunization.mainPageFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wittarget.immunization.R;

public class immuchartFragment extends Fragment {

    public static immuchartFragment newInstance(String text) {
        immuchartFragment f = new immuchartFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_immuchart, container, false);
        return v;
    }

}

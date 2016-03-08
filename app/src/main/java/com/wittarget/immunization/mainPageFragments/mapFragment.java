package com.wittarget.immunization.mainPageFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.config;

public class mapFragment extends Fragment
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity = null;

    public static mapFragment newInstance(String text) {
        mapFragment f = new mapFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    public void onMapReady(GoogleMap googleMap) {
        Float[] currentLocation = config.getLocation(activity);
        Float lat = currentLocation[0];
        Float lot = currentLocation[1];

        mMap = googleMap;
        LatLng currentPlace = null;

        if (lat != 0 & lot != 0) {
            // Add a marker in Sydney and move the camera
            currentPlace = new LatLng(lat, lot);
        } else {
            currentPlace = new LatLng(-34, 151);
        }
        mMap.addMarker(new MarkerOptions().position(currentPlace).title("I'm here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPlace, 13));
    }

}

package com.wittarget.immunization.mainPageFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapFragment extends Fragment
        implements OnMapReadyCallback, AsyncResponse, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Activity activity = null;
    private JSONArray arr = null;

    public static MapFragment newInstance(String text) {
        MapFragment f = new MapFragment();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Float[] currentLocation = config.getLocation(activity);
        Float lat = currentLocation[0];
        Float lot = currentLocation[1];

        mMap = googleMap;
        LatLng currentPlace = null;

        if (lat != 0 & lot != 0) {
            // Add a marker in Sydney and move the camera
            //currentPlace = new LatLng(lat, lot);
            currentPlace = new LatLng(43.57, -79.63);
        } else {
            currentPlace = new LatLng(43.57, -79.63);
        }
        mMap.addMarker(new MarkerOptions().position(currentPlace).title("I'm here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPlace, 13));

        mMap.setOnInfoWindowClickListener(this);

        ServerResponse pud = new ServerResponse(this);
        pud.execute(config.SERVERADDRESS + "/nearby/nearby.php?latitude=43.57&longitude=-79.63&distance=10");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(activity, "Info window clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskComplete(Object out) {
        try {
            arr = new JSONArray((String) out);
            JSONObject item = null;

            for (int i = 0; i < arr.length(); i++) {
                try {
                    item = arr.getJSONObject(i);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(item.getDouble("latitude"), item.getDouble("longitude"))).title(item.getString("name")).snippet(item.getString("address")));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTaskStart() {
    }
}

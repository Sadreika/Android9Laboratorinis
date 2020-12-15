package com.example.a9laboratorinis;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db = new Database(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
        LatLng Lithuania = new LatLng(55,24);
        LatLng Estonia = new LatLng(58, 25);
        LatLng Canada = new LatLng(56, -106);
        map.addMarker(new MarkerOptions().position(Lithuania).title("Marker in Lithuania"));
        map.addMarker(new MarkerOptions().position(Estonia).title("Marker in Estonia"));
        map.addMarker(new MarkerOptions().position(Canada).title("Marker in Canada"));

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latlng)
            {
                LatLng location = new LatLng(latlng.latitude, latlng.longitude);
                map.addMarker(new MarkerOptions().position(location));
                db.insertMarker(latlng.latitude, latlng.longitude);
            }
        });

        ArrayList<LatLng> markers = db.getMarkers();
        for(LatLng latLng : markers){
            LatLng location = new LatLng(latLng.latitude, latLng.longitude);
            map.addMarker(new MarkerOptions()
                    .position(location));

            db.insertMarker(latLng.latitude, latLng.longitude);
        }
    }

}
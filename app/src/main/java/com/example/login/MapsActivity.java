package com.example.login;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.login.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            // TODO Auto-generated method stub
                            try {
                                LatLng myPoss = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.clear();
                                mMap.addMarker(new MarkerOptions().position(myPoss).title("moja lokalizacja"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(myPoss));
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {
                            // TODO Auto-generated method stub
                        }
                    });
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }

        }, 100);

        //tv.setText(String.valueOf(sumOfDistance));
    }

    public void drawTrack(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(15).color(Color.BLUE);
        Polyline routePolyline;
        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }

    }



    public void sumOfDistance(ArrayList<Location> locations) {
        Location oldLoc = null, newLoc = null;
        float[] result = new float[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            if (i == 0) {
                oldLoc = locations.get(0);
                i++;
            } else {
                newLoc = locations.get(i);
                Location.distanceBetween(oldLoc.getLatitude(), oldLoc.getLongitude(),
                        newLoc.getLatitude(), newLoc.getLongitude(), result);
                oldLoc = newLoc;
            }
        }
    }
}
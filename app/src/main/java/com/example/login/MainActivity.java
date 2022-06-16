package com.example.login;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.login.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    long pause;
    boolean firstLocation = true;
    Location oldLocation = null;
    Location newLocation = null;
    float distance = 0.0f;
    Chronometer chronometer;
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ArrayList<LatLng> directionsPoints = new ArrayList<>();

        Button btnS, btnStop;
        chronometer = findViewById(R.id.chronometerView);
        chronometer.setFormat("Time: %ms");
        chronometer.setBase(SystemClock.elapsedRealtime());
        btnS = findViewById(R.id.startRunButton);
        btnStop = findViewById(R.id.stopRunButton);

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pause);
                    chronometer.start();
                    running = true;
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        try {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    if (running) {
                                        try {
                                            newLocation = location;
                                            if (firstLocation) {
                                                firstLocation = false;
                                                oldLocation = location;
                                            } else {
                                                distance += oldLocation.distanceTo(newLocation);
                                                ((TextView) findViewById(R.id.textViewDistance)).setText("distance: " + (int) distance + "m");

                                                LatLng myPoss = new LatLng(location.getLatitude(), location.getLongitude());
                                                directionsPoints.add(myPoss);
                                                showPolyline(directionsPoints);
                                                mMap.clear();
                                                directionsPoints.add(myPoss);
                                                showPolyline(directionsPoints);
                                                mMap.addMarker(new MarkerOptions().position(myPoss).title("my location"));
                                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
                                            }
                                            oldLocation = newLocation;
                                        } catch (SecurityException e) {
                                            e.printStackTrace();
                                        }
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

                }, 1000);
            }

        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    chronometer.stop();
                    pause = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });
    }


    private void showPolyline(ArrayList<LatLng> points) {
        if (points.size() < 2)
            return;

        int ix = 0;
        LatLng currentPoint = points.get(ix);
        ArrayList<LatLng> currentSegment = new ArrayList<>();
        currentSegment.add(currentPoint);
        ix++;

        while (ix < points.size()) {
            currentPoint = points.get(ix);
            currentSegment.add(currentPoint);
            mMap.addPolyline(new PolylineOptions()
                    .addAll(currentSegment)
                    .color(Color.BLUE)
                    .width(20));
            currentSegment.clear();
            currentSegment.add(currentPoint);
            ix++;
        }

        mMap.addPolyline(new PolylineOptions()
                .addAll(currentSegment)
                .color(Color.BLUE)
                .width(20));

    }

}
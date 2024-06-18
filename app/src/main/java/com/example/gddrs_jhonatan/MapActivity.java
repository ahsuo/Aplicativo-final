package com.example.gddrs_jhonatan;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap googleMap;
    private List<Detrito> detritoList;
    private DatabaseReference databaseDetritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        detritoList = new ArrayList<>();
        databaseDetritos = FirebaseDatabase.getInstance().getReference("detritos");

        loadDetritos();
    }

    private void loadDetritos() {
        databaseDetritos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                detritoList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Detrito detrito = postSnapshot.getValue(Detrito.class);
                    if (detrito != null) {
                        detritoList.add(detrito);
                    }
                }
                addMarkers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to load detritos", databaseError.toException());
            }
        });
    }

    private void addMarkers() {
        if (googleMap == null) return;

        googleMap.clear();
        for (Detrito detrito : detritoList) {
            LatLng location = new LatLng(detrito.getLatitude(), detrito.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(location).title(detrito.getNomePessoa()));
        }

        if (!detritoList.isEmpty()) {
            Detrito firstDetrito = detritoList.get(0);
            LatLng firstLocation = new LatLng(firstDetrito.getLatitude(), firstDetrito.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        addMarkers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
}

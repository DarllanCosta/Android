package com.example.hellcife_;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hellcife_.ui.Denuncia;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener  {
    private static final int FINE_LOCATION_REQUEST = 986;
    private GoogleMap mMap;
    private Boolean fine_location;
    public final static String EXTRA_MESSAGE = "coordenadas";
    FirebaseDatabase fbDB;
    DatabaseReference bdDenuncia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        requestPermission();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        fbDB = FirebaseDatabase.getInstance();
        bdDenuncia = fbDB.getReference("Denuncia");




        mMap = googleMap;

        LatLng recife = new LatLng(-8.05, -34.9);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(recife));

        mMap.setOnMapClickListener(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();

                return false;
            }

        });




        bdDenuncia.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Denuncia denuncia = dataSnapshot.getValue(Denuncia.class);
                if(denuncia != null) {
                    LatLng latLng = new LatLng(Double.parseDouble(denuncia.getLatitude()),Double.parseDouble(denuncia.getLongitude()));
                    System.out.println(latLng.toString());
                    mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(35)));
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }




    private void requestPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        boolean hasPermission = (permissionCheck == PackageManager.PERMISSION_GRANTED);

        if (hasPermission) return;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_LOCATION_REQUEST);
    }

    private void onRequestPermissionResult(int requestCode, String permission[], int[] grantResult){
        boolean granted = (grantResult.length > 0) && (grantResult[0] == PackageManager.PERMISSION_GRANTED);

        fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(35)));
        Intent intent = new Intent(this, CadastroCoordenadas.class);
        Bundle args = new Bundle();
        args.putParcelable("coordenadas", latLng);
        intent.putExtra("cd" , args);


        startActivity(intent);
    }
}

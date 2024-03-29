package com.example.hellcife_;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener{

    private static final int FINE_LOCATION_REQUEST = 986;
    private GoogleMap mMap;
    private Boolean fine_location;
    public final static String EXTRA_MESSAGE = "coordenadas";
    FirebaseDatabase fbDB;
    DatabaseReference bdDenuncia;
    List<Denuncia> denuncias = new ArrayList<>();
    private int filtro = 0;
;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent abrirMapa = getIntent();
         filtro = abrirMapa.getIntExtra("filtro", 0 );

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
        mMap.setMyLocationEnabled(true);
        final Intent intent = new Intent(this, ViewCoodenadas.class);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Denuncia abrir = null ;
                for(Denuncia denuncia: denuncias){

                   Double latitude = Double.parseDouble(denuncia.getLatitude());
                   Double longitude = Double.parseDouble(denuncia.getLongitude());

                    if((latitude  == marker.getPosition().latitude)&&(longitude  == marker.getPosition().longitude)){
                        abrir  = denuncia;
                    }
                }

                Bundle args = new Bundle();
                args.putString("latitude", abrir.getLatitude());
                args.putString("longitude", abrir.getLongitude());
                args.putString("tipo", abrir.getTipo());
                args.putString("descricao", abrir.getDescricao());

                intent.putExtra("den" , args);


                startActivity(intent);

                return true;
            }

        });




        bdDenuncia.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Denuncia denuncia = dataSnapshot.getValue(Denuncia.class);
                if(denuncia != null) {
                    denuncias.add(denuncia);
                    float tipoMarcador = 0;
                    String tipo = denuncia.getTipo();
                    boolean mostrar = true;

                    if (tipo.equals("Furto") && ((filtro == 0) || (filtro == 1))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_VIOLET;
                    } else if (tipo.equalsIgnoreCase("Roubo") && ((filtro == 0) || (filtro == 2))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_BLUE;
                    } else if (tipo.equalsIgnoreCase("Desaparecimento de Pessoa") && ((filtro == 0) || (filtro == 3))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_GREEN;
                    } else if (tipo.equalsIgnoreCase("Estupro") && ((filtro == 0) || (filtro == 4))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_RED;
                    } else if (tipo.equalsIgnoreCase("Briga") && ((filtro == 0) || (filtro == 5))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_YELLOW;
                    } else if (tipo.equalsIgnoreCase("Homicidio") && ((filtro == 0) || (filtro == 6))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_ORANGE;
                    } else if (tipo.equalsIgnoreCase("Arrastao") && ((filtro == 0) || (filtro == 7))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_MAGENTA;
                    } else if (tipo.equalsIgnoreCase("Latrocinio") && ((filtro == 0) || (filtro == 8))) {
                        tipoMarcador = BitmapDescriptorFactory.HUE_CYAN;
                    } else {
                        mostrar = false;
                    }


                    if (mostrar == true) {
                        LatLng latLng = new LatLng(Double.parseDouble(denuncia.getLatitude()), Double.parseDouble(denuncia.getLongitude()));
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(tipoMarcador))


                        );
                    }
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

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }




    private void requestPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        boolean hasPermission = (permissionCheck == PackageManager.PERMISSION_GRANTED);

        if (hasPermission) return;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_LOCATION_REQUEST);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        boolean granted = (grantResults.length > 0) &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;
        mMap.setMyLocationEnabled(fine_location);
    }

    public void abrirFiltro(View view){
        Intent intent = new Intent(this, Filtro.class);
        startActivity(intent);
    }



    @Override
    public void onMapClick(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
       // mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(35)));
        Intent intent = new Intent(this, CadastroCoordenadas.class);
        Bundle args = new Bundle();
        args.putParcelable("coordenadas", latLng);
        intent.putExtra("cd" , args);


        startActivity(intent);
    }







    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Você está aqui!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Indo para a sua localização.", Toast.LENGTH_SHORT).show();
        return false;
    }




}

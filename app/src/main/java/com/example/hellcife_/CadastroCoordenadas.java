package com.example.hellcife_;

import android.content.Intent;
import android.os.Bundle;

import com.example.hellcife_.ui.Denuncia;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class CadastroCoordenadas extends AppCompatActivity {

    EditText editLatitude;
    EditText editLongitude;
    EditText editDescricao;
    Spinner editTipo;
    FirebaseDatabase fbDB;
    DatabaseReference bdDenuncia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_coordenadas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fbDB = FirebaseDatabase.getInstance();
        bdDenuncia = fbDB.getReference("Denuncia");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editLatitude = (EditText) findViewById(R.id.latitude);
        editLongitude = (EditText) findViewById(R.id.longitude);
        editDescricao = (EditText) findViewById(R.id.descricao);
        editTipo = (Spinner) findViewById(R.id.tipoDenuncia);
        Intent intent = getIntent();
        Bundle bundle = intent.getParcelableExtra("cd");
        LatLng coordenadas = bundle.getParcelable("coordenadas");
        double latitude = coordenadas.latitude;
        double longitude = coordenadas.longitude;
        editLongitude.setText(Double.toString(longitude));
        editLatitude.setText(Double.toString(latitude));



    }



    public void clickDenuncia(View view){
        final String latitude = editLatitude.getText().toString();
        final String longitude = editLongitude.getText().toString();
        final String descricao = editDescricao.getText().toString();
        final String tipo = editTipo.getSelectedItem().toString();
        Denuncia denuncia = new Denuncia(latitude, longitude, descricao, tipo);
        bdDenuncia.push().setValue(denuncia);
    }




}

package com.example.hellcife_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hellcife_.ui.Denuncia;

public class ViewCoodenadas extends AppCompatActivity {

    TextView ViewLatitude;
    TextView ViewLongitude;
    TextView ViewDescricao;
    TextView ViewTipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coodenadas);
        ViewLatitude =  findViewById(R.id.latitude);
        ViewLongitude =  findViewById(R.id.longitude);
        ViewDescricao =  findViewById(R.id.descricao);
        ViewTipo = findViewById(R.id.tipoDenuncia);
        Intent intent = getIntent();
        Bundle bundle = intent.getParcelableExtra("den");
        ViewLatitude.setText(bundle.getString("latitude"));
        ViewLongitude.setText(bundle.getString("longitude"));
        ViewDescricao.setText(bundle.getString("descricao"));
        ViewTipo.setText(bundle.getString("tipo"));


    }



    public void clickVoltar(View view){
        final Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}

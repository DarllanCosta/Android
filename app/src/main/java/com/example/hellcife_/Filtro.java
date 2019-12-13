package com.example.hellcife_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Filtro extends AppCompatActivity {

    private static final String [] ocorrencias = {"Todos","Furto","Roubo","Desaparecimento de Pessoa", "Estupro", "Briga", "Homicidio","Arrastao","Latrocinio"};


     //Intent intent = new Intent(this, MapsActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent = new Intent(this, MapsActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ocorrencias));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

              intent.putExtra("filtro", position);
              startActivity(intent);
            }
        });


    }
}
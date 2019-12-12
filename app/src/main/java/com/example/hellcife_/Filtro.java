package com.example.hellcife_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Filtro extends AppCompatActivity {

    private static final String [] ocorrencias = {"Furto","Roubo","Desaparecimento de Pessoa", "Homicidio",
       "Estupro", "Briga", "Latrocinio", "Arrastao"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ocorrencias));

    }
}

package com.example.hellcife_.ui;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class Denuncia {
    String descricao;
    String latitude;
    String longitude;
    String tipo;


    public Denuncia(){

    }

    public Denuncia(String latitude, String longitude, String descricao, String tipo){
        this.descricao = descricao;
        this.tipo = tipo;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLongitude() {return longitude; }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

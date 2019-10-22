package com.example.hellcife_.ui;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Denuncia {
    String descricao;
    String tipo;
    Date data;
    LatLng localizacao;

    public Denuncia(){

    }

    public Denuncia(String descricao, String tipo, Date data, LatLng localizacao){
        this.descricao = descricao;
        this.tipo = tipo;
        this.data = data;
        this.localizacao = localizacao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setLocalizacao(LatLng localizacao) {
        this.localizacao = localizacao;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public LatLng getLocalizacao() {
        return localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }
}

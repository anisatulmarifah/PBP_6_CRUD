package com.example.myapplicationdatabasekelompok6;

public class datas {
    int id,kapasitas;
    String namaRuang="";
    datas(int id, String namaRuang, int kapasitas){
        this.id = id;
        this.namaRuang = namaRuang;
        this.kapasitas = kapasitas;
    }

    public int getId() {
        return id;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public String getNamaRuang() {
        return namaRuang;
    }
}
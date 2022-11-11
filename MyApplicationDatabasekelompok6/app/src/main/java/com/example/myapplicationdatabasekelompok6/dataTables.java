package com.example.myapplicationdatabasekelompok6;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class dataTables extends SQLiteOpenHelper {

    Context context;
    Cursor cursor;
    SQLiteDatabase database;

    public static String db_name = "db_pbp_tugas3";
    public static String tb_name = "tb_ruang";

    public dataTables(@Nullable Context context) {
        super(context, db_name, null, 3);
        this.context = context;
        database = getReadableDatabase();
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE IF NOT EXISTS "+tb_name+" (id int(10), nama_ruang varchar(254), kapasitas int(10))";
        sqLiteDatabase.execSQL(query);
    }

    int idRandom(){
        return new Random().nextInt(888+1)+100;
    }

    void UPDATE(String namaRuang, int kapasitas ){
        database.execSQL(
                "INSERT INTO "+tb_name+" values" +
                        "('"+idRandom()+"','"+namaRuang+"','"+kapasitas+"')"
        );
        Toast.makeText(context,"Data Berhasil Tersimpan!", Toast.LENGTH_SHORT).show();
    }

    void UPDATE_DATA(int id,String namaRuang, int kapasitas){
        database.execSQL(
                "UPDATE "+tb_name+
                        " SET nama_ruang='"+namaRuang+"',kapasitas='"+kapasitas+"'"+
                        " WHERE id='"+id+"'"
        );
        Toast.makeText(context,"Data Berhasil di Update!", Toast.LENGTH_SHORT).show();
    }

    void DELETE(int id){
        database.execSQL("DELETE FROM "+tb_name+" WHERE id='"+id+"'");
        Toast.makeText(context,"Data Berhasil di Hapus!", Toast.LENGTH_SHORT).show();
    }

    Cursor READ(){
        Cursor cursor = database.rawQuery("SELECT * FROM "+tb_name,null);
        return  cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.example.myapplicationdatabasekelompok6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDataActivity extends AppCompatActivity {
    private View decorView;
    dataTables dataTables;
    EditText namaRuang,kapasitas;
    Button update_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setup
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });
        //setup ends
        //inisialisasi
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        namaRuang=findViewById(R.id.ruangkelas);
        kapasitas = findViewById(R.id.kapasitas);
        update_btn = findViewById(R.id.update_data_btn);

        dataTables = new dataTables(getApplicationContext());
        namaRuang.setText(getIntent().getStringExtra("namaRuang"));
        kapasitas.setText(getIntent().getStringExtra("kapasitas"));

        //kalo update button di click ke update
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataTables.UPDATE_DATA(
                        Integer.parseInt(getIntent().getStringExtra("id")),
                        namaRuang.getText().toString(),
                        Integer.parseInt(kapasitas.getText().toString())
                );
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}
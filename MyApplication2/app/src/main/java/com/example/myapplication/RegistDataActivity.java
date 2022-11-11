package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistDataActivity extends AppCompatActivity {
    private View decorView;
    dataTables dataTables;
    EditText ruang,kapasitas;
    Button save_btn;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_data);
        ruang = findViewById(R.id.ruangkelas);
        kapasitas = findViewById(R.id.kapasitas);
        save_btn = findViewById(R.id.save_btn);

        dataTables = new dataTables(getApplicationContext());

        save_btn.setOnClickListener(view -> {
            UPDATE_DATA();
            finish();
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

    private void UPDATE_DATA() {
        dataTables.UPDATE(
                ruang.getText().toString(),
                Integer.parseInt(kapasitas.getText().toString())
        );
    }

}
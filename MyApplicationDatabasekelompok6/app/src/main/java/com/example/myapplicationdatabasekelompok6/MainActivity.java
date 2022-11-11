package com.example.myapplicationdatabasekelompok6;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private View decorView;
    ListView listView;
    Button add_data;
    ArrayList<datas>list;
    SQLiteDatabase database;
    Cursor cursor;
    dataTables dataTables;
    dataList_Adapter adapter;
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
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        add_data=findViewById(R.id.tambah_data);
        dataTables=new dataTables(getApplicationContext());

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistDataActivity.class));
            }
        });
        get_datas();
    }
    void get_datas(){
        list = new ArrayList<datas>();
        cursor=dataTables.READ();
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String namaRuang = cursor.getString(1);
                int kapasitas = cursor.getInt(2);
                list.add(new datas(id,
                        namaRuang,
                        kapasitas));
            }
            adapter=new dataList_Adapter(getApplicationContext(),list,MainActivity.this);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_datas();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
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
class dataList_Adapter extends BaseAdapter{
    dataTables dataTables;
    Activity activity;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<datas> model;
    dataList_Adapter(Context context,ArrayList<datas> list,Activity activity){
        this.context = context;
        this.model = list;
        this.activity = activity;
        layoutInflater=LayoutInflater.from(context);
        dataTables = new dataTables(context);
    }
    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    TextView id,namaRuang,kapasitas;
    Button btn_edit,btn_delete;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = layoutInflater.inflate(R.layout.data_list,viewGroup,false);

        //id = view1.findViewById(R.id.id);
        namaRuang = view1.findViewById(R.id.ruangkelas);
        kapasitas = view1.findViewById(R.id.kapasitas);

        //id.setText(String.valueOf(model.get(i).getId()));
        namaRuang.setText(model.get(i).getNamaRuang());
        kapasitas.setText("Kapasitas : "+String.valueOf(model.get(i).getKapasitas()));

        btn_edit = view1.findViewById(R.id.edit);
        btn_delete = view1.findViewById(R.id.hapus);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditDataActivity.class);
                intent.putExtra("id",String.valueOf(model.get(i).getId()));
                intent.putExtra("namaRuang",model.get(i).getNamaRuang());
                intent.putExtra("kapasitas",String.valueOf(model.get(i).getKapasitas()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("ATTENTION");
                builder.setMessage("Are you sure to delete this data?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        dataTables.DELETE(model.get(i).getId());
                        Intent intent=new Intent(context,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        return view1;
    }
}
package com.example.dawid.kalkulatorwilksa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity {

    int klasa ;
    int kat;
    float waga;
    float wynik1;
    float wynik2;
    float wynik3;
    List<Zawodnik> lista = new ArrayList<Zawodnik>();
    int nr;

    TextView wagaD;
    Button kolejnyBtn;
    Button obliczBtn;

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        //wagaD=(TextView)(findViewById(R.id.wagaD));
        kolejnyBtn=(Button)(findViewById(R.id.kolejnyBtn));
        obliczBtn=(Button)(findViewById(R.id.obliczBtn));

        nr=0;

        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);



        adapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, arrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 80;

                view.setLayoutParams(params);

                return view;
            }
        };
        list.setAdapter(adapter);



        SQLiteDatabase myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS zawodnicy (klasa INT, kategoria INT, waga FLOAT, wynik1 FLOAT, wynik2 FLOAT, wynik3 FLOAT)"
        );

        String[] kolumny={"klasa","kategoria", "waga", "wynik1", "wynik2", "wynik3"};


        Cursor myCursor =
                myDB.query("zawodnicy",kolumny,null,null,null,null,null,null);

        myCursor.moveToNext();
        while(myCursor.moveToNext())
        {
            nr++;
            klasa=myCursor.getInt(0);
            kat=myCursor.getInt(1);
            waga=myCursor.getFloat(2);
            wynik1=myCursor.getFloat(3);
            wynik2=myCursor.getFloat(4);
            wynik3=myCursor.getFloat(5);

            arrayList.add("Przeciwnik nr "+nr);
            arrayList.add("Waga:"+waga);
            arrayList.add("Wyniki: 1)"+wynik1+" 2)"+wynik2+" 3)"+wynik3);
            adapter.notifyDataSetChanged();
            //wagaD.setText(wagaD.getText()+"\nPrzeciwnik nr "+nr+"\nWaga:"+waga+"\nWyniki: 1)"+wynik1+" 2)"+wynik2+" 3)"+wynik3);

        }

        myCursor.close();

        myDB.close();


        kolejnyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDodaj();
            }
        });

        obliczBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWynik();
            }
        });

    }

    void openDodaj(){
        Intent intent = new Intent(this, DodawaniePrzeciwnika.class);
        startActivity(intent);
    }
    void openWynik(){
        Intent intent = new Intent(this, Wynik.class);
        startActivity(intent);
    }
}

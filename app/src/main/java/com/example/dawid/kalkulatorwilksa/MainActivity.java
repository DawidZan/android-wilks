package com.example.dawid.kalkulatorwilksa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;



public class MainActivity extends AppCompatActivity  {
    private static final String DB_PATH = "data/data/com.example.dawid.kalkulatorwilksa/databases/my.db";
    EditText wagaIn;
    Button dodajBtn;
    Spinner spinner;
    Zawodnik zawodnik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            File file = new File(DB_PATH);
            file.delete();
        }catch(Exception ex)
        {}
        setContentView(R.layout.activity_main);

        final SQLiteDatabase myDB =
                openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS zawodnicy (klasa INT, kategoria INT, waga FLOAT, wynik1 FLOAT, wynik2 FLOAT, wynik3 FLOAT)"
        );
        final ContentValues row1 = new ContentValues();
        row1.put("klasa", 1);

        zawodnik=new Zawodnik();

        wagaIn=(EditText)(findViewById(R.id.wagaIn));
        spinner = (Spinner) findViewById(R.id.katSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dodajBtn=(Button)(findViewById(R.id.dodajBtn));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {
                zawodnik.katId=(int)position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        dodajBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zawodnik.setWaga(Float.valueOf(wagaIn.getText().toString()));
                row1.put("kategoria", zawodnik.getKat());
                row1.put("waga", zawodnik.getWaga());
                myDB.insert("zawodnicy", null, row1);
                myDB.close();
                openDodaj();
            }
        });

    }
    void openDodaj(){
        Intent intent = new Intent(this, DodawaniePrzeciwnika.class);
        startActivity(intent);
    }
}

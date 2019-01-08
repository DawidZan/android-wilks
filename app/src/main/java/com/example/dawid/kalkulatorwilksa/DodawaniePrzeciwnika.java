package com.example.dawid.kalkulatorwilksa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DodawaniePrzeciwnika extends AppCompatActivity {
    EditText wagaIn;
    EditText wynikIn;
    TextView liczbaWynikow;
    Button dodajBtn;

    int licznik=0;
    Zawodnik przeciwnik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_przeciwnika);

        wagaIn=(EditText)(findViewById(R.id.wagaIn));
        wynikIn=(EditText)(findViewById(R.id.wynikIn));
        liczbaWynikow=(TextView)(findViewById(R.id.liczbaWynikow));
        dodajBtn=(Button)(findViewById(R.id.dodajBtn));

        przeciwnik= new Zawodnik();

        //baza danych
        final SQLiteDatabase myDB =
                openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS zawodnicy (klasa INT, kategoria INT, waga FLOAT, wynik1 FLOAT, wynik2 FLOAT, wynik3 FLOAT)"
        );
        final ContentValues row1 = new ContentValues();
        row1.put("klasa", 2);


        liczbaWynikow.setText(licznik+"/3");
        dodajBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(licznik==0)
                {
                    przeciwnik.setWaga(Float.valueOf(wagaIn.getText().toString()));
                    przeciwnik.dodajWynik(Float.valueOf(wagaIn.getText().toString()));
                    row1.put("waga", Float.valueOf(wagaIn.getText().toString()));
                    row1.put("wynik1", Float.valueOf(wynikIn.getText().toString()));
                    wynikIn.setText("");
                }
                if(licznik==1)
                {
                    przeciwnik.dodajWynik(Float.valueOf(wynikIn.getText().toString()));
                    row1.put("wynik2", Float.valueOf(wynikIn.getText().toString()));
                    wynikIn.setText("");
                }
                if(licznik==2)
                {
                    przeciwnik.dodajWynik(Float.valueOf(wynikIn.getText().toString()));
                    row1.put("wynik3", Float.valueOf(wynikIn.getText().toString()));
                    myDB.insert("zawodnicy", null, row1);
                    myDB.close();
                    openLista();
                }
                licznik++;
                liczbaWynikow.setText(licznik+"/3");
            }
        });
    }
    void openLista(){
        Intent intent = new Intent(this, Lista.class);
        startActivity(intent);
    }
}


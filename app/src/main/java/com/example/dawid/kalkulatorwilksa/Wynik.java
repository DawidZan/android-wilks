package com.example.dawid.kalkulatorwilksa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Wynik extends AppCompatActivity {

    TextView wynikTxt;
    String value;
    Double rekord;
    LinkedList<Zawodnik> przeciwnicy;
    float max1=0, max2=0, max3=0;
    LinkedList<Float> temp2=new LinkedList<Float>();
    Zawodnik ja;
    Button nowyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wynik);
        przeciwnicy = new LinkedList<Zawodnik>();
        ja= new Zawodnik();
        Zawodnik temp= new Zawodnik();
        int klasa;



        SQLiteDatabase myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS zawodnicy (klasa INT, kategoria INT, waga FLOAT, wynik1 FLOAT, wynik2 FLOAT, wynik3 FLOAT)"
        );

        String[] kolumny={"klasa","kategoria", "waga", "wynik1", "wynik2", "wynik3"};


        Cursor myCursor =
                myDB.query("zawodnicy",kolumny,null,null,null,null,null,null);


        while(myCursor.moveToNext())
        {

            klasa=myCursor.getInt(0);
            if (klasa == 1) {
                ja.setKat(myCursor.getInt(1));
                ja.setWaga(myCursor.getFloat(2));
            }
            else{
                temp.setWaga(myCursor.getFloat(2));
                temp.dodajWynik(myCursor.getFloat(3));
                temp.dodajWilksa((float)obliczWilksa(myCursor.getFloat(2),myCursor.getFloat(3)));
                temp.dodajWynik(myCursor.getFloat(4));
                temp.dodajWilksa((float)obliczWilksa(myCursor.getFloat(2),myCursor.getFloat(4)));
                temp.dodajWynik(myCursor.getFloat(5));
                temp.dodajWilksa((float)obliczWilksa(myCursor.getFloat(2),myCursor.getFloat(5)));
                przeciwnicy.add(temp);
            }
        }


        myCursor.close();

        myDB.close();

        String katW=checkKat(ja.getWaga());
        String key=Integer.toString(ja.getKat())+katW;

        wynikTxt=(TextView)(findViewById(R.id.wynikTxt));
        nowyBtn=(Button)(findViewById(R.id.nowyBtn));



        /*firebase.......*/


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference katRef= myRef.child(key);

        katRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rekord=dataSnapshot.getValue(Double.class);

                for(int i=0;i<przeciwnicy.size();i++)
                {
                    temp2=przeciwnicy.get(i).getWilksy();
                    if(temp2.get(0)>max1)max1=temp2.get(0);
                    if(temp2.get(1)>max2)max2=temp2.get(1);
                    if(temp2.get(2)>max3)max3=temp2.get(2);
                }

                ja.dodajWynik(znajdzWieksza(max1/(float)obliczWsp(ja.getWaga()),rekord));
                ja.dodajWynik(znajdzWieksza(max2/(float)obliczWsp(ja.getWaga()),rekord));
                ja.dodajWynik(znajdzWieksza(max3/(float)obliczWsp(ja.getWaga()),rekord));

                wynikTxt.setText("1: "+Float.toString(ja.getWyniki().get(0))+"\n2: "+Float.toString(ja.getWyniki().get(1))+"\n3: "+Float.toString(ja.getWyniki().get(2)));



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        nowyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNowy();
            }
        });







    }

    double obliczWilksa(Float waga, Float wynik)
    {
        double a=-216.0475144;
        double b=16.2606339;
        double c=-0.002388645;
        double d=-0.00113732;
        double e=7.01863e-6;
        double f=-1.291e-8;
        double wsp=500/(a+b*waga+c*waga*waga+d*waga*waga*waga+e*waga*waga*waga*waga+f*waga*waga*waga*waga*waga);
        double wilks=wynik*wsp;
        return wilks;
    }

    double obliczWsp(Float waga)
    {
        double a=-216.0475144;
        double b=16.2606339;
        double c=-0.002388645;
        double d=-0.00113732;
        double e=7.01863e-6;
        double f=-1.291e-8;
        double wsp=500/(a+b*waga+c*waga*waga+d*waga*waga*waga+e*waga*waga*waga*waga+f*waga*waga*waga*waga*waga);
        return wsp;
    }

    float znajdzWieksza(float wynik, double rekord)
    {
        double i=0;
        while (i<=wynik && i<rekord)
        {
            i=i+2.5;
        }
        if(i>rekord+0.5)i=rekord+0.5;
        while (i<=wynik)
        {
            i=i+0.5;
        }
        return (float)i;
    }

    String checkKat(Float waga)
    {
        if(waga<=59)return "59";
        if(waga<=66)return "66";
        if(waga<=74)return "74";
        if(waga<=83)return "83";
        if(waga<=93)return "93";
        if(waga<=105)return "105";
        if(waga<=120)return "120";
        return "1200";
    }

    void openNowy(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

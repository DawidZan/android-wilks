package com.example.dawid.kalkulatorwilksa;

import java.util.LinkedList;
import java.util.List;

public class Zawodnik {

    private
    float waga;
    LinkedList<Float> wyniki;
    LinkedList<Float> wilksy;
    int katId;
    int klasa;

    public
    Zawodnik()
    {
        waga=0;
        katId=0;
        wyniki= new LinkedList<Float>();
        klasa=0;
        wilksy= new LinkedList<Float>();

    }

    Zawodnik(int klasa, float waga, int kat)
    {
        this.waga=waga;
        this.katId=kat;
        this.klasa=klasa;
        wyniki= new LinkedList<Float>();
        wilksy= new LinkedList<Float>();
    }

    void setWaga(float waga)
    {
        this.waga=waga;
    }

    void setKat(int katId)
    {
        this.katId=katId;
    }

    void dodajWynik(float wynik)
    {
        wyniki.add(wynik);
    }

    void dodajWilksa(float wilks)
    {
        wilksy.add(wilks);

    }

    float getWaga()
    {
        return waga;
    }

    int getKat()
    {
        return katId;
    }

    LinkedList<Float> getWyniki()
    {
        return wyniki;
    }
    LinkedList<Float> getWilksy()
    {
        return wilksy;
    }


}

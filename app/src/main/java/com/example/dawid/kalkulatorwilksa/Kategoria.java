package com.example.dawid.kalkulatorwilksa;

public class Kategoria {

    private
    String nazwa;
    int id;
    float rekord;

    public
    Kategoria()
    {
        nazwa=" ";
        id=0;
        rekord=0;
    }

    Kategoria(String nazwa, int id, float rekord)
    {
        this.nazwa=nazwa;
        this.id=id;
        this.rekord=rekord;
    }
}

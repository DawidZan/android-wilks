package com.example.dawid.kalkulatorwilksa;

public class Rekord {
    String wynik;
    String kategoria;
    String waga;

    public Rekord()
    {
       wynik="";
       kategoria="";
       waga="";
    }

    public String getWynik() {
        return wynik;
    }

    public void setWynik(String wynik) {
        this.wynik = wynik;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getWaga() {
        return waga;
    }

    public void setWaga(String waga) {
        this.waga = waga;
    }
}

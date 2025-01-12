package com.studia.Plansza;

public class Pole {
    private int wiersz;
    private int kolumna;
    private int nrGracza;
    private int nrGraczZwycieski;

    Pole(int wiersz, int kolumna, int nrGracza) {
        this.wiersz = wiersz;
        this.kolumna = kolumna;
        this.nrGracza = nrGracza;
        this.nrGraczZwycieski = 0;
    }

    public int getGracz() {
        return nrGracza;
    }

    public boolean zajete() {
        return nrGracza > 0 && nrGracza <= 6;
    }

    public String koordynaty() {
        return "Wiersz: " + wiersz + " Kolumna: " + kolumna + " Gracz: " + nrGracza + " Zwycięstwo: "
                + nrGraczZwycieski;
    }

    public void setGracz(int gracz) {
        nrGracza = gracz;
    }

    public void setGraczZwycieski(int gracz) {
        nrGraczZwycieski = gracz;
    }

    public int getGraczZwycieski() {
        return nrGraczZwycieski;
    }
}

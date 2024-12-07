package com.studia;

public class Pole {
    private int wiersz;
    private int kolumna;
    private int nrGracza;

    Pole(int wiersz, int kolumna, int nrGracza) {
        this.wiersz = wiersz;
        this.kolumna = kolumna;
        this.nrGracza = nrGracza;
    }

    public int getGracz() {
        return nrGracza;
    }

    public boolean zajete() {
        return nrGracza > 0 && nrGracza <= 6;
    }

    public String koordynaty() {
        return "Wiersz: " + wiersz + " Kolumna: " + kolumna + " Gracz: " + nrGracza;
    }
}

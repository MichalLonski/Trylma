package com.studia.Plansza;

public class Pole {
    private int nrGracza;
    private int domekGracza;

    Pole(int nrGracza) {
        this.nrGracza = nrGracza;
        this.domekGracza = 0;
    }

    Pole(Pole pole) {
        this.nrGracza = pole.nrGracza;
        this.domekGracza = pole.domekGracza;
    }

    public int getGracz() {
        return nrGracza;
    }

    public boolean zajete() {
        return nrGracza > 0 && nrGracza <= 6;
    }

    public void setGracz(int gracz) {
        nrGracza = gracz;
    }

    public void setDomek(int gracz) {
        domekGracza = gracz;
    }

    public int getDomek() {
        return domekGracza;
    }
}

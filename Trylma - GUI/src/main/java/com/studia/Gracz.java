package com.studia;

public class Gracz {
    private int miejscePrzyStole;
    private Gra gra;

    public Gracz() {
    }

    public void zajmijMiejsce(int miejsce) {
        miejscePrzyStole = miejsce;
    }

    public void wykonajRuch(int[][] sekwencjaRuchow) {
        gra.wykonajRuch(miejscePrzyStole, sekwencjaRuchow);
    }

    public void opuscGre() {
        gra.usunGracza(this);
    }

    public int ktoreMiejsce() {
        return miejscePrzyStole;
    }

    public Gra dajGre() {
        return gra;
    }

    public void przypiszGre(Gra gra) {
        this.gra = gra;
    }

}

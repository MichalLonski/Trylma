package com.studia;

/**
 * Klasa reprezentująca gracza w grze.
 */
public class Gracz {
    private int miejscePrzyStole;
    private Gra gra;

    /**
     * Konstruktor gracza.
     */
    public Gracz() {
    }

    /**
     * Przypisuje graczowi miejsce przy stole.
     *
     * @param miejsce numer miejsca przy stole.
     */
    public void zajmijMiejsce(int miejsce) {
        miejscePrzyStole = miejsce;
    }

    /**
     * Wykonuje ruch w grze.
     *
     * @param sekwencjaRuchow sekwencja ruchów gracza.
     */
    public void wykonajRuch(int[][] sekwencjaRuchow) {
        gra.wykonajRuch(miejscePrzyStole, sekwencjaRuchow);
    }

    /**
     * Gracz opuszcza grę.
     */
    public void opuscGre() {
        gra.usunGracza(this);
    }

    /**
     * Zwraca numer miejsca gracza przy stole.
     *
     * @return numer miejsca.
     */
    public int ktoreMiejsce() {
        return miejscePrzyStole;
    }

    /**
     * Zwraca obiekt gry, w której bierze udział gracz.
     *
     * @return obiekt gry.
     */
    public Gra dajGre() {
        return gra;
    }

    /**
     * Przypisuje gracza do danej gry.
     *
     * @param gra gra, do której gracz jest przypisany.
     */
    public void przypiszGre(Gra gra) {
        this.gra = gra;
    }
}

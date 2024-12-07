package com.studia;

public class StandardoweZasadyGry implements ZasadyGry {
    private int liczbaGraczy;

    StandardoweZasadyGry(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
    }

    @Override
    public int ileGraczy() {
        return liczbaGraczy;
    }

    @Override
    public boolean ruchJestPoprawny(Plansza plansza, String pozycjaPoczatkowa, String pozycjaKoncowa) {
        return true;
    }

}

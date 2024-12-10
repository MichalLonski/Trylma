package com.studia.Zasady;

import com.studia.Plansza.Plansza;

public class StandardoweZasadyGry implements ZasadyGry {
    private int liczbaGraczy;

    StandardoweZasadyGry(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
    }

    @Override
    public int ileGraczy() {
        return liczbaGraczy;
    }

    // TODO: implementacja ruchów
    @Override
    public boolean ruchJestPoprawny(Plansza plansza, String pozycjaPoczatkowa, String pozycjaKoncowa) {
        return true;
    }

}

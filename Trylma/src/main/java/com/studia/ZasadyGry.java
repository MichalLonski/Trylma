package com.studia;

// Mogą pojawić się dodatkowe zasady, lepiej mieć to z tyłu głowy
public interface ZasadyGry {
    public int ileGraczy();
    public boolean ruchJestPoprawny(Plansza plansza, String pozycjaPoczatkowa, String pozycjaKoncowa);
}

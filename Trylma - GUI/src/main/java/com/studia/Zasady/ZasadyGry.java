package com.studia.Zasady;

import com.studia.Plansza.Plansza;

// Mogą pojawić się dodatkowe zasady, lepiej mieć to z tyłu głowy
public interface ZasadyGry {
    public int ileGraczy();

    public boolean ruchJestPoprawny(Plansza plansza, String[] sekwencjaRuchow, int gracz);
}

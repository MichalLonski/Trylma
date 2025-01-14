package com.studia.Zasady;

import com.studia.Plansza.Plansza;

// Mogą pojawić się dodatkowe zasady, lepiej mieć to z tyłu głowy
public abstract class ZasadyGry {
    protected int liczbaGraczy;
    protected int[] warunkiZwyciestwa;

    public ZasadyGry(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
        warunkiZwyciestwa = new int[liczbaGraczy+1];
        for (int i = 0; i < warunkiZwyciestwa.length; i++) {
            warunkiZwyciestwa[i] = 0;
        }
    }

    public int ileGraczy() {
        return liczbaGraczy;
    }

    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        return true;
    };

    public String opisZasad() {
        return "";
    };

    public boolean checkWin(int gracz) {
        return warunkiZwyciestwa[gracz] > 0;
    }

    protected boolean skokJestLegalny(Plansza plansza, int wierszP, int kolumnaP, int wierszK, int kolumnaK){
        return true;
    }
}

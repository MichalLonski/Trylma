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
    public boolean ruchJestPoprawny(Plansza plansza, String[] sekwencjaRuchow, int gracz) {

        if (gracz != plansza.sprawdzPole((int) (sekwencjaRuchow[0].toUpperCase().charAt(0)) - 65,
                Integer.parseInt(sekwencjaRuchow[0].substring(1)) - 1).getGracz()) {
            return false;
            // czy zaczynamy od swojego piona
        }

        boolean rezultat = true;
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int wierszP = (int) (sekwencjaRuchow[i].toUpperCase().charAt(0)) - 65;
            int kolumnaP = Integer.parseInt(sekwencjaRuchow[i].substring(1)) - 1;
            int wierszK = (int) (sekwencjaRuchow[i + 1].toUpperCase().charAt(0)) - 65;
            int kolumnaK = Integer.parseInt(sekwencjaRuchow[i + 1].substring(1)) - 1;

            if (plansza.sprawdzPole(wierszK, kolumnaK).zajete()) {
                rezultat = false;
                // czy chcemy przejść na puste pole
            } else if (kolumnaP == kolumnaK) {
                if (Math.abs(wierszK - wierszP) == 2) {
                    // ruch w naszym pionie
                } else if (Math.abs(wierszK - wierszP) == 4) {
                    rezultat = plansza.jestMiedzyPolami(wierszP, kolumnaP, wierszK, kolumnaK);
                    // skok w pionie
                }
            } else if (Math.abs(wierszK - wierszP) == 1 && Math.abs(kolumnaK - kolumnaP) == 1) {
                // ruch po naszych ukosach
            } else if (Math.abs(wierszK - wierszP) == 2 && Math.abs(kolumnaK - kolumnaP) == 2) {
                rezultat = plansza.jestMiedzyPolami(wierszP, kolumnaP, wierszK, kolumnaK);
                // skok po skosie
            } else {
                rezultat = false;
            }

            if (!rezultat) {
                break;
            }
        }

        return rezultat;
    }

}

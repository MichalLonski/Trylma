package com.studia.Zasady;

import com.studia.Plansza.Plansza;
import com.studia.Plansza.Pole;

public class StandardoweZasadyGry implements ZasadyGry {
    private int liczbaGraczy;

    StandardoweZasadyGry(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
    }

    @Override
    public int ileGraczy() {
        return liczbaGraczy;
    }

    /*
     * Jakie mamy ograniczenia:
     * - trzeba zacząć od pola ze swoim pionem
     * - trzeba skończyć na polu pustym
     * - można ruszyć się na sąsiednie pole
     * - można skoczyć na pole w odległości 2, jeśli pomiędzy polami jest pion
     * - nie można opuszczać strefy zwycięskiej, chyba że wrócimy do niej w tym
     * samym ruchu
     */
    @Override
    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {

        Pole obecne = plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]);
        if (gracz != obecne.getGracz()) {
            return false;
            // czy zaczynamy od swojego piona
        }

        boolean rezultat = true;
        boolean czyNaKoncu = (obecne.getGraczZwycieski() == gracz); // czy jesteśmy w domku
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int wierszP = sekwencjaRuchow[i][0];
            int kolumnaP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];

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
        if (rezultat) {
            if (czyNaKoncu) {

                Pole ostatnie = plansza.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                        sekwencjaRuchow[sekwencjaRuchow.length - 1][1]);
                rezultat = (ostatnie.getGraczZwycieski() == gracz);
                // jeśli zaczynamy w domku to musi tam już kończyć
            }
        }
        return rezultat;
    }

    @Override
    public String opisZasad() {
        return "1. Trzeba zacząć od pola ze swoim pionem &" +
                "2. Trzeba skończyć na polu pustym &" +
                "3. Można ruszyć się na sąsiednie pole &" +
                "4. Można skoczyć na pole w odległości 2, jeśli pomiędzy polami jest pion &" +
                "5. Nie można opuszczać strefy zwycięskiej, chyba że wrócimy do niej w tym samym ruchu &";
    }

}

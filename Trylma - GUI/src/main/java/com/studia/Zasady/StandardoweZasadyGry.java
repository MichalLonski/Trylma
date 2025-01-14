package com.studia.Zasady;

import com.studia.Plansza.Plansza;
import com.studia.Plansza.Pole;

public class StandardoweZasadyGry extends ZasadyGry {

    public StandardoweZasadyGry(int liczbaGraczy) {
        super(liczbaGraczy);
    }

    @Override
    protected boolean skokJestLegalny(Plansza plansza, int wierszP, int kolumnaP, int wierszK, int kolumnaK) {
        if (wierszP % 2 != wierszK % 2 || kolumnaP % 2 != kolumnaK % 2) {
            return false; // nie ma pola pomiędzy
        }
        return plansza.sprawdzPole((wierszP + wierszK) / 2, (kolumnaP + kolumnaK) / 2).zajete();
    }

    @Override
    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {

        Pole obecne = plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]);
        if (gracz != obecne.getGracz()) {
            return false;
            // czy zaczynamy od swojego piona
        }

        boolean rezultat = true;
        int kogoDomStart = obecne.getGraczZwycieski(); // w czyim jesteśmy domku
        boolean robiRuch = false;
        boolean robiSkok = false;
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int wierszP = sekwencjaRuchow[i][0];
            int kolumnaP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];

            if (plansza.sprawdzPole(wierszK, kolumnaK).zajete()) {
                rezultat = false;
                // czy chcemy przejść na puste pole
            } else if ((kolumnaP == kolumnaK && Math.abs(wierszK - wierszP) == 2)
                    || ((Math.abs(wierszK - wierszP) == 1 && Math.abs(kolumnaK - kolumnaP) == 1))) {
                // ruch w naszym poziomie lub po ukosach, musi być ostatni
                rezultat = ((i + 1) == sekwencjaRuchow.length - 1);
                robiRuch = true;

            } else if ((Math.abs(wierszK - wierszP) == 4 && Math.abs(kolumnaK - kolumnaP) == 0)
                    || (Math.abs(wierszK - wierszP) == 2 && Math.abs(kolumnaK - kolumnaP) == 2)) {
                rezultat = skokJestLegalny(plansza, wierszP, kolumnaP, wierszK, kolumnaK);
                // skok w poziomie lub skosie
                robiSkok = true;
            }

            // trzeba robić tylko jedno
            rezultat = rezultat && (robiRuch ^ robiSkok);

            if (!rezultat) {
                break;
            }
        }
        if (rezultat) {
            // pole gdzie lądujemy
            Pole ostatnie = plansza.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                    sekwencjaRuchow[sekwencjaRuchow.length - 1][1]);
            int kogoDomKoniec = ostatnie.getGraczZwycieski();
            if (kogoDomStart == gracz) {
                // jeśli zaczynamy w swoim domku to musi tam już kończyć
                rezultat = (kogoDomKoniec == gracz);
            } else {
                // nie można kończyć na cudzym domku (za wyjątkiem swojego startu)
                warunkiZwyciestwa[gracz] += (kogoDomKoniec == gracz ? 1 : 0);
                rezultat = (kogoDomKoniec == gracz || kogoDomKoniec == 0);
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
                "5. Nie można opuszczać strefy zwycięskiej, chyba że wrócimy do niej w tym samym ruchu &" +
                "6. Nie można wejść do cudzej strzefy zwycięskiej, chyba że wyjdziemy z niej w tym samym ruchu";
    }

    @Override
    public boolean checkWin(int gracz) {
        return warunkiZwyciestwa[gracz] == 10;
    }

}

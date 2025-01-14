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
        Plansza kopiaPlanszy = new Plansza(plansza); // jakieś patologiczne sytuacje gdybyśmy chcieli skakać nad sobą
        boolean rezultat = true;
        int kogoDomStart = obecne.getDomek(); // w czyim jesteśmy domku
        boolean robiRuch = false;
        boolean robiSkok = false;
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int kolumnaP = sekwencjaRuchow[i][0];
            int wierszP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];

            if (kopiaPlanszy.sprawdzPole(wierszK, kolumnaK).zajete()) {
                rezultat = false;
                // czy chcemy przejść na puste pole
            } else if ((wierszP == kolumnaK && Math.abs(wierszK - kolumnaP) == 2)
                    || ((Math.abs(wierszK - kolumnaP) == 1 && Math.abs(kolumnaK - wierszP) == 1))) {
                // ruch w naszym poziomie lub po ukosach, musi być ostatni
                rezultat = ((i + 1) == sekwencjaRuchow.length - 1);
                robiRuch = true;

            } else if ((Math.abs(wierszK - kolumnaP) == 4 && Math.abs(kolumnaK - wierszP) == 0)
                    || (Math.abs(wierszK - kolumnaP) == 2 && Math.abs(kolumnaK - wierszP) == 2)) {
                rezultat = skokJestLegalny(kopiaPlanszy, kolumnaP, wierszP, wierszK, kolumnaK);
                // skok w poziomie lub skosie
                robiSkok = true;
            }

            // trzeba robić tylko jedno
            rezultat = rezultat && (robiRuch ^ robiSkok);

            if (!rezultat) {
                break;
            }
            wykonajRuch(kopiaPlanszy, new int[][] { sekwencjaRuchow[i], sekwencjaRuchow[i + 1] }, gracz);

        }
        if (rezultat) {
            // pole gdzie lądujemy
            Pole ostatnie = kopiaPlanszy.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                    sekwencjaRuchow[sekwencjaRuchow.length - 1][1]);
            int kogoDomKoniec = ostatnie.getDomek();

            // nie można kończyć na cudzym domku (za wyjątkiem swojego startu)
            rezultat = (kogoDomKoniec == kogoDomStart || (kogoDomKoniec == 0 && kogoDomStart != gracz)
                    || kogoDomKoniec == gracz);
        }
        return rezultat;
    }

    @Override
    public String toString() {
        return "Standardowe";
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
    public boolean graSkonczona(int gracz) {
        return warunkiZwyciestwa[gracz] == 10;
    }

    @Override
    public void wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        if (plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]).getDomek() != gracz
                && plansza.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                        sekwencjaRuchow[sekwencjaRuchow.length - 1][1]).getDomek() == gracz) {
            warunkiZwyciestwa[gracz]++;
        }
        super.wykonajRuch(plansza, sekwencjaRuchow, gracz);
    }

}

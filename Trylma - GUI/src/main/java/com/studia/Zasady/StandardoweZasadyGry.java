package com.studia.Zasady;

import com.studia.Plansza.Plansza;
import com.studia.Plansza.Pole;

/**
 * Klasa implementująca standardowe zasady gry.
 * Klasa ta rozszerza abstrakcyjną klasę ZasadyGry i implementuje konkretne
 * zasady dotyczące ruchów, zwycięstwa oraz opisu zasad gry.
 */
public class StandardoweZasadyGry extends ZasadyGry {

    /**
     * Konstruktor klasy StandardoweZasadyGry.
     * Inicjalizuje zasady gry dla określonej liczby graczy.
     * 
     * @param liczbaGraczy Liczba graczy w grze.
     */
    public StandardoweZasadyGry(int liczbaGraczy) {
        super(liczbaGraczy);
    }

    /**
     * Zwraca opis zasad gry.
     * 
     * @return String z opisem zasad gry.
     */
    @Override
    public String opisZasad() {
        return "1. Trzeba zacząć od pola ze swoim pionem &" +
                "2. Trzeba skończyć na polu pustym &" +
                "3. Można ruszyć się na sąsiednie pole &" +
                "4. Można skoczyć na pole w odległości 2, jeśli pomiędzy polami jest pion &" +
                "5. Nie można opuszczać strefy zwycięskiej, chyba że wrócimy do niej w tym samym ruchu &" +
                "6. Nie można wejść do cudzej strzefy zwycięskiej, chyba że wyjdziemy z niej w tym samym ruchu";
    }

    /**
     * Sprawdza, czy ruch gracza jest poprawny.
     * 
     * @param plansza         Plansza, na której rozgrywa się gra.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli ruch jest poprawny, w przeciwnym razie false.
     */
    @Override
    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {

        Pole obecne = plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]);
        if (gracz != obecne.getGracz()) {
            return false;
        }

        Plansza kopiaPlanszy = new Plansza(plansza);
        boolean rezultat = true;
        int kogoDomStart = obecne.getStrefa();
        boolean robiRuch = false;
        boolean robiSkok = false;
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int wierszP = sekwencjaRuchow[i][0];
            int kolumnaP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];
            if (kopiaPlanszy.sprawdzPole(wierszK, kolumnaK).zajete()) {
                rezultat = false;
            } else if ((kolumnaP == kolumnaK && Math.abs(wierszK - wierszP) == 2)
                    || ((Math.abs(wierszK - wierszP) == 1 && Math.abs(kolumnaK - kolumnaP) == 1))) {
                rezultat = ((i + 1) == sekwencjaRuchow.length - 1);
                robiRuch = true;
            } else if ((Math.abs(wierszK - wierszP) == 4 && Math.abs(kolumnaK - kolumnaP) == 0)
                    || (Math.abs(wierszK - wierszP) == 2 && Math.abs(kolumnaK - kolumnaP) == 2)) {
                rezultat = skokJestLegalny(kopiaPlanszy, wierszP, kolumnaP, wierszK, kolumnaK);
                robiSkok = true;
            }

            rezultat = rezultat && (robiRuch ^ robiSkok);

            if (!rezultat) {
                break;
            }
            int temp = warunkiZwyciestwa[gracz];
            wykonajRuch(kopiaPlanszy, new int[][] { sekwencjaRuchow[i], sekwencjaRuchow[i + 1] }, gracz);
            warunkiZwyciestwa[gracz] = temp;
        }

        if (rezultat) {
            Pole ostatnie = kopiaPlanszy.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                    sekwencjaRuchow[sekwencjaRuchow.length - 1][1]);
            int kogoDomKoniec = ostatnie.getStrefa();
            rezultat = (kogoDomKoniec == kogoDomStart || (kogoDomKoniec == 0 && kogoDomStart != gracz)
                    || kogoDomKoniec == gracz);
        }
        return rezultat;
    }

    /**
     * Sprawdza, czy skok jest legalny.
     * Skok jest legalny, jeśli pomiędzy polami znajduje się pionek.
     * 
     * @param plansza  Plansza, na której rozgrywa się gra.
     * @param wierszP  Wiersz początkowy skoku.
     * @param kolumnaP Kolumna początkowa skoku.
     * @param wierszK  Wiersz końcowy skoku.
     * @param kolumnaK Kolumna końcowa skoku.
     * @return true, jeśli skok jest legalny, w przeciwnym razie false.
     */
    @Override
    protected boolean skokJestLegalny(Plansza plansza, int wierszP, int kolumnaP, int wierszK, int kolumnaK) {
        if (wierszP % 2 != wierszK % 2 || kolumnaP % 2 != kolumnaK % 2) {
            return false;
        }
        return plansza.sprawdzPole((wierszP + wierszK) / 2, (kolumnaP + kolumnaK) / 2).zajete();
    }

    /**
     * Zwraca nazwę zasad gry.
     * 
     * @return "Standardowe" - nazwa zasad gry.
     */
    @Override
    public String toString() {
        return "Standardowe";
    }

    /**
     * Wykonuje ruch na planszy, zmieniając stan gry.
     * Jeśli gracz skończy swój ruch na pustym polu w swojej strefie zwycięstwa,
     * jego liczba punktów
     * zwycięstwa zostaje zwiększona.
     * 
     * @param plansza         Plansza, na której rozgrywa się gra.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli gracz wygrał (osiągnął 10 punktów zwycięstwa), w
     *         przeciwnym razie false.
     */
    @Override
    public boolean wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        if (plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]).getStrefa() != gracz
                && plansza.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                        sekwencjaRuchow[sekwencjaRuchow.length - 1][1]).getStrefa() == gracz) {
            warunkiZwyciestwa[gracz]++;
        }
        super.wykonajRuch(plansza, sekwencjaRuchow, gracz);
        return warunkiZwyciestwa[gracz] == 10;
    }
}

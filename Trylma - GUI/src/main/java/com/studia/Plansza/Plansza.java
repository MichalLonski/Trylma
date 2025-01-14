package com.studia.Plansza;

import org.json.JSONArray;

public class Plansza {

    public final static int LICZBA_WIERSZY = 25;
    public final static int LICZBA_KOLUMN = 17;

    private Pole planszaDoGry[][];

    public Plansza() {
    }

    private Pole[] odczytajPole(int nrWiersza, JSONArray list) {
        Pole[] wiersz = new Pole[list.length()];
        for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
            wiersz[nrKolumny] = new Pole(list.getInt(nrKolumny));
        }
        return wiersz;
    }

    public void utworzPlansze(JSONArray mapa) {

        planszaDoGry = new Pole[LICZBA_WIERSZY][];
        Pole wiersz[] = new Pole[LICZBA_KOLUMN];
        for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
            wiersz = odczytajPole(nrWiersza, mapa.getJSONArray(nrWiersza));
            planszaDoGry[nrWiersza] = wiersz;
        }

        for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
            int odbityWiersz = LICZBA_WIERSZY - nrWiersza - 1;
            for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
                int odbitaKolumna = LICZBA_KOLUMN - nrKolumny - 1;
                if (sprawdzPole(nrWiersza, nrKolumny).zajete()) {
                    sprawdzPole(odbityWiersz, odbitaKolumna)
                            .setDomek(sprawdzPole(nrWiersza, nrKolumny).getGracz());
                }
            }
        }

    }

    public Pole sprawdzPole(int wiersz, int kolumna) {
        return planszaDoGry[wiersz][kolumna];
    }

    public void wykonajRuch(int[] pozycjaPoczatkowa, int[] pozycjaKoncowa, int gracz) {
        int wierszP = pozycjaPoczatkowa[0];
        int kolumnaP = pozycjaPoczatkowa[1];
        int wierszK = pozycjaKoncowa[0];
        int kolumnaK = pozycjaKoncowa[1];
        sprawdzPole(wierszK, kolumnaK).setGracz(gracz);
        sprawdzPole(wierszP, kolumnaP).setGracz(0);
    }

}

package com.studia.Plansza;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Plansza {

    public final static int LICZBA_WIERSZY = 25;
    public final static int LICZBA_KOLUMN = 17;

    private Pole planszaDoGry[][];
    private int liczbaGraczy;

    public Plansza(int liczbaGraczy) {
        if (liczbaGraczy == 2 || liczbaGraczy == 3 || liczbaGraczy == 4 || liczbaGraczy == 6) {
            this.liczbaGraczy = liczbaGraczy;
        } else {
            throw new IllegalArgumentException("Zła liczba graczy");
        }
    }

    private Pole[] odczytajPole(int nrWiersza, ArrayList<Integer> list) {
        Pole[] wiersz = new Pole[list.size()];
        for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
            wiersz[nrKolumny] = new Pole(nrWiersza + 1, nrKolumny + 1, list.get(nrKolumny));
        }
        return wiersz;
    }

    // Inny układ planszy w zależności od liczby graczy, przechowywane w pliku JSON
    // Tylko dla standardowych zasad
    // TODO: zrobić tak żeby można było korzystać z innych zasad
    public void utworzPlansze() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/config.json");
            // rzutowanie z JSON
            @SuppressWarnings("unchecked")
            Map<String, ArrayList<ArrayList<Integer>>> map = mapper.readValue(file, Map.class);
            ArrayList<ArrayList<Integer>> mapa = map.get(String.valueOf(liczbaGraczy));
            planszaDoGry = new Pole[LICZBA_WIERSZY][];
            Pole wiersz[] = new Pole[LICZBA_KOLUMN];
            for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
                wiersz = odczytajPole(nrWiersza, mapa.get(nrWiersza));
                planszaDoGry[nrWiersza] = wiersz;
            }

            for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
                int odbityWiersz = LICZBA_WIERSZY - nrWiersza - 1;
                for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
                    int odbitaKolumna = LICZBA_KOLUMN - nrKolumny - 1;
                    if (sprawdzPole(nrWiersza, nrKolumny).zajete()) {
                        sprawdzPole(odbityWiersz, odbitaKolumna)
                                .setGraczZwycieski(sprawdzPole(nrWiersza, nrKolumny).getGracz());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public boolean jestMiedzyPolami(int wiersz1, int kolumna1, int wiersz2, int kolumna2) {
        if (wiersz1 % 2 != wiersz2 % 2 || kolumna1 % 2 != kolumna2 % 2) {
            return false; // nie ma pola pomiędzy
        }
        return sprawdzPole((wiersz1 + wiersz2) / 2, (kolumna1 + kolumna2) / 2).zajete();
    }

    public void wypiszPlansze() {
        for (Pole[] poles : planszaDoGry) {
            for (Pole pole : poles) {
                System.out.println(pole.koordynaty());
            }
            System.err.println("=================");
        }
    }

}

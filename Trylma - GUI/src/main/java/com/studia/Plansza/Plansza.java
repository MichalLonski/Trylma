package com.studia.Plansza;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Plansza {

    public final static int LICZBA_WIERSZY = 13;
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

    private static Pole[] odczytajPole(int nrWiersza, ArrayList<Integer> list) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Wypisywanie planszy
         * for (Pole[] wiersz : planszaDoGry) {
         * String s = "";
         * for (Pole pole : wiersz) {
         * if (pole.getGracz() > 6) {
         * s += "█ ";
         * } else {
         * s += String.valueOf(pole.getGracz());
         * s += " ";
         * }
         * }
         * System.out.println(s);
         * }
         * System.out.println("============================================");
         */
    }

    public String sprawdzPole(int wiersz, int kolumna) {
        return (planszaDoGry[wiersz][kolumna].koordynaty());
    }

    @SuppressWarnings("unused")
    public boolean wykonajRuch(String pozycjaPoczatkowa, String pozycjaKoncowa, int gracz) {
        int wierszP = (int) (pozycjaPoczatkowa.toUpperCase().charAt(0)) - 65;
        int kolumnaP = Integer.parseInt(pozycjaPoczatkowa.substring(1)) - 1;
        int wierszK = (int) (pozycjaKoncowa.toUpperCase().charAt(0)) - 65;
        int kolumnaK = Integer.parseInt(pozycjaKoncowa.substring(1)) - 1;
        /*
         * System.out.println("Gracz: " + (gracz + 1) + " wykonuje ruch: " +
         * pozycjaPoczatkowa + " -> " + pozycjaKoncowa);
         * System.out.println("Pod pozycją " + pozycjaPoczatkowa + " znajduje się " +
         * sprawdzPole(wierszP, kolumnaP));
         * System.out.println("Pod pozycją " + pozycjaKoncowa + " znajduje się " +
         * sprawdzPole(wierszK, kolumnaK));
         */
        return true;
    }
}

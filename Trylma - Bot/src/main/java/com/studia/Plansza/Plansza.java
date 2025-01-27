package com.studia.Plansza;

import org.json.JSONArray;

/**
 * Klasa reprezentująca planszę do gry.
 * Plansza składa się z pól, które mogą być zajmowane przez graczy.
 * Zawiera metody do tworzenia planszy, wykonywania ruchów oraz zarządzania
 * stanem gry.
 */
public class Plansza {

    /**
     * Liczba wierszy w planszy.
     */
    public final static int LICZBA_WIERSZY = 25;

    /**
     * Liczba kolumn w planszy.
     */
    public final static int LICZBA_KOLUMN = 17;

    /**
     * Tablica pól reprezentujących planszę.
     */
    private Pole planszaDoGry[][];

    /**
     * Konstruktor domyślny. Tworzy pustą planszę.
     */
    public Plansza() {
    }

    /**
     * Konstruktor kopiujący planszę.
     * Tworzy nową planszę na podstawie istniejącej planszy.
     * 
     * @param doKopiowania Plansza, którą chcemy skopiować.
     */
    public Plansza(Plansza doKopiowania) {
        this.planszaDoGry = new Pole[LICZBA_WIERSZY][LICZBA_KOLUMN];
        for (int i = 0; i < LICZBA_WIERSZY; i++) {
            for (int j = 0; j < LICZBA_KOLUMN; j++) {
                this.planszaDoGry[i][j] = new Pole(doKopiowania.sprawdzPole(i, j));
            }
        }
    }

    /**
     * Odczytuje wiersz z JSON i tworzy tablicę pól.
     * 
     * @param nrWiersza Numer wiersza w planszy.
     * @param list      Lista pól w wierszu w formacie JSON.
     * @return Tablica pól reprezentujących wiersz planszy.
     */
    private Pole[] odczytajPole(int nrWiersza, JSONArray list) {
        Pole[] wiersz = new Pole[list.length()];
        for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
            wiersz[nrKolumny] = new Pole(list.getInt(nrKolumny));
        }
        return wiersz;
    }

    /**
     * Tworzy planszę na podstawie danych z formatu JSON.
     * 
     * @param mapa JSON zawierający dane o planszy.
     */
    public void utworzPlansze(JSONArray mapa) {
        planszaDoGry = new Pole[LICZBA_WIERSZY][];
        Pole wiersz[] = new Pole[LICZBA_KOLUMN];
        for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
            wiersz = odczytajPole(nrWiersza, mapa.getJSONArray(nrWiersza));
            planszaDoGry[nrWiersza] = wiersz;
        }
        ustawStrefy();
        //drukujPlanszeTest();
    }

    /**
     * Ustawia strefy zwycięstwa dla graczy na planszy na podstawie zajętych pól.
     */
    private void ustawStrefy() {
        for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
            int odbityWiersz = LICZBA_WIERSZY - nrWiersza - 1;
            for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
                int odbitaKolumna = LICZBA_KOLUMN - nrKolumny - 1;
                if (sprawdzPole(nrWiersza, nrKolumny).zajete()) {
                    sprawdzPole(odbityWiersz, odbitaKolumna)
                            .setStrefa(sprawdzPole(nrWiersza, nrKolumny).getGracz());
                }
            }
        }
    }

    /**
     * Sprawdza pole w danej pozycji na planszy.
     * 
     * @param wiersz  Numer wiersza.
     * @param kolumna Numer kolumny.
     * @return Pole w danej pozycji na planszy.
     */
    public Pole sprawdzPole(int wiersz, int kolumna) {
        return planszaDoGry[wiersz][kolumna];
    }

    /**
     * Wykonuje ruch na planszy, przenosząc pionek z jednej pozycji na drugą.
     * 
     * @param pozycjaPoczatkowa Tablica z współrzędnymi początkowej pozycji.
     * @param pozycjaKoncowa    Tablica z współrzędnymi końcowej pozycji.
     * @param gracz             Indeks gracza, który wykonuje ruch.
     */
    public void wykonajRuch(int[] pozycjaPoczatkowa, int[] pozycjaKoncowa, int gracz) {
        int wierszP = pozycjaPoczatkowa[0];
        int kolumnaP = pozycjaPoczatkowa[1];
        int wierszK = pozycjaKoncowa[0];
        int kolumnaK = pozycjaKoncowa[1];
        sprawdzPole(wierszK, kolumnaK).setGracz(gracz);
        sprawdzPole(wierszP, kolumnaP).setGracz(0);
    }

    public Pole[][] dajPlanszaDoGry(){
        return planszaDoGry;
    }

    public void drukujPlanszeTest(){
        for(Pole[] a : planszaDoGry){
            for(Pole pole : a){
                System.out.print(pole.getGracz()+",");
            }
            System.out.println();
        }
    }

    public int[] dajKoordyOdPola(Pole pole){
        int x = 0;
        int y = 0;
        for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
            for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
                if(sprawdzPole(nrWiersza,nrKolumny) == pole){
                    x = nrWiersza;
                    y = nrKolumny;
                }
            }
        }
        return new int[]{x,y};
    }

//    public String  serializePlansza(){
//        StringBuilder sb = new StringBuilder();
//        for (int nrWiersza = 0; nrWiersza < LICZBA_WIERSZY; nrWiersza++) {
//            for (int nrKolumny = 0; nrKolumny < LICZBA_KOLUMN; nrKolumny++) {
//                if(sprawdzPole(nrWiersza,nrKolumny) == pole){
//                    x = nrWiersza;
//                    y = nrKolumny;
//                }
//            }
//        }
//    }

}

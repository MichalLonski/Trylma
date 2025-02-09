package com.studia;

import com.studia.Plansza.Plansza;
import com.studia.Zasady.FabrykaZasad;
import com.studia.Zasady.TypGry;
import com.studia.Zasady.ZasadyGry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa reprezentująca grę, która zarządza zasadami, planszą, kolejką graczy i
 * ruchem.
 */
public class Gra {
    private static int ID = 0;
    private final int ID_GRY;
    private List<Gracz> listaGraczy;
    private ZasadyGry zasadyGry;
    private KolejkaGraczy kolejka;
    private Plansza planszaGry;
    private boolean graWTrakcie = false;
    private int[][] ruchWPoprzedniejTurze = new int[][] {
            new int[] { 0, 0 },
            new int[] { 0, 0 },
    };

    /**
     * Konstruktor gry.
     *
     * @param typ          typ gry.
     * @param liczbaGraczy liczba graczy uczestniczących w grze.
     * @throws IllegalArgumentException gdy liczba graczy jest niewłaściwa.
     */
    public Gra(TypGry typ, int liczbaGraczy) {
        if (liczbaGraczy == 2 || liczbaGraczy == 3 || liczbaGraczy == 4 || liczbaGraczy == 6) {
            this.zasadyGry = FabrykaZasad.stworzZasadyGry(typ, liczbaGraczy);
            this.listaGraczy = new ArrayList<>();
            ID_GRY = ID;
            ID++;
        } else {
            throw new IllegalArgumentException("Zła liczba graczy");
        }
    }

    /**
     * Rozpoczyna grę, jeśli wszyscy gracze są dodani.
     *
     * @return Indeks obecnego gracza, który zaczyna grę, lub -1, jeśli gra nie jest
     *         pełna.
     */
    public int ZacznijGre() {
        if (listaGraczy.size() != zasadyGry.ileGraczy()) {
            System.err.println("Gra nie jest pełna");
            return -1;
        }
        kolejka = new KolejkaGraczy(listaGraczy);
        kolejka.ustawLosowo();
        planszaGry = new Plansza();
        planszaGry.utworzPlansze(zasadyGry.infoJSON());
        graWTrakcie = true;
        return kolejka.obecnyGracz();
    }

    /**
     * Klasa wewnętrzna zarządzająca kolejką graczy.
     */
    private class KolejkaGraczy {
        private List<Gracz> zakolejkowaniGracze;
        private int tura;

        /**
         * Konstruktor kolejki graczy.
         *
         * @param lista lista graczy.
         */
        KolejkaGraczy(List<Gracz> lista) {
            this.zakolejkowaniGracze = lista;
        }

        /**
         * Losowo ustawia kolejkę graczy i przypisuje im miejsca.
         *
         * @return Gracz, który zaczyna grę.
         */
        public Gracz ustawLosowo() {
            Collections.shuffle(zakolejkowaniGracze);
            tura = 0;
            for (int miejsce = 1; miejsce <= zakolejkowaniGracze.size(); miejsce++) {
                zakolejkowaniGracze.get(miejsce - 1).zajmijMiejsce(miejsce);
            }
            return zakolejkowaniGracze.get(tura);
        }

        /**
         * Przechodzi do kolejnego gracza w kolejce.
         */
        public void wykonanoRuch() {
            tura = (tura + 1) % zakolejkowaniGracze.size();
            if (zakolejkowaniGracze.get(tura) == null) {
                wykonanoRuch();
            }
        }

        /**
         * Usuwa gracza z kolejki.
         *
         * @param miejsce miejsce gracza w kolejce.
         */
        public void usunGracza(int miejsce) {
            zakolejkowaniGracze.set(miejsce, null);
        }

        /**
         * Zwraca indeks obecnego gracza w kolejce.
         *
         * @return indeks obecnego gracza.
         */
        public int obecnyGracz() {
            return tura + 1;
        }
    }

    /**
     * Przechodzi do kolejnego gracza.
     *
     * @param miejsceGracza miejsce gracza w kolejce.
     */
    public void passTury(int miejsceGracza) {
        if (trwaTuraGracza(miejsceGracza)) {
            kolejka.wykonanoRuch();
        }
    }

    /**
     * Wykonuje ruch gracza.
     *
     * @param miejsceGracza   miejsce gracza w kolejce.
     * @param sekwencjaRuchow sekwencja ruchów wykonanych przez gracza.
     */
    public void wykonajRuch(int miejsceGracza, int[][] sekwencjaRuchow) {
        if (ruchJestPoprawny(sekwencjaRuchow, miejsceGracza)) {
            if (zasadyGry.wykonajRuch(planszaGry, sekwencjaRuchow, miejsceGracza)) {
                int wygrany = zasadyGry.zwyciezca(miejsceGracza);
                graczWygrywa(wygrany);
                if (zasadyGry.graSkonczona(planszaGry)) {
                    koniecGry();
                } else {
                    kolejka.usunGracza(wygrany);
                }
            }
            kolejka.wykonanoRuch();

            ruchWPoprzedniejTurze = new int[][] {
                    sekwencjaRuchow[0],
                    sekwencjaRuchow[sekwencjaRuchow.length - 1]
            };
        }
    }

    /**
     * Kończy grę.
     */
    private void koniecGry() {
        throw new UnsupportedOperationException("Unimplemented method 'koniecGry'");
    }

    /**
     * Obsługuje sytuację, gdy gracz wygrywa.
     *
     * @param miejsceGracza miejsce gracza w kolejce.
     */
    private void graczWygrywa(int miejsceGracza) {
        throw new UnsupportedOperationException("Unimplemented method 'graczWygrywa'");
    }

    /**
     * Sprawdza, czy trwa tura danego gracza.
     *
     * @param miejsceGracza miejsce gracza w kolejce.
     * @return true, jeśli trwa tura gracza; false w przeciwnym wypadku.
     */
    public boolean trwaTuraGracza(int miejsceGracza) {
        return (miejsceGracza == kolejka.obecnyGracz());
    }

    /**
     * Dodaje nowego gracza do gry.
     *
     * @param gracz gracz do dodania.
     */
    public synchronized void dodajGracza(Gracz gracz) {
        if (listaGraczy.size() == zasadyGry.ileGraczy()) {
            System.err.println("Nie można dołączyć do trwającej gry");
            return;
        }
        this.listaGraczy.add(gracz);
        gracz.przypiszGre(this);
    }

    /**
     * Usuwa gracza z gry.
     *
     * @param gracz gracz do usunięcia.
     */
    public void usunGracza(Gracz gracz) {
        if (listaGraczy.contains(gracz)) {
            listaGraczy.remove(gracz);
        }
    }

    /**
     * Zwraca ID gry.
     *
     * @return ID gry.
     */
    public int dajID() {
        return ID_GRY;
    }

    /**
     * Sprawdza, czy gra została rozpoczęta.
     *
     * @return true, jeśli gra trwa; false w przeciwnym wypadku.
     */
    public boolean czyGraSieZaczela() {
        return graWTrakcie;
    }

    /**
     * Zwraca listę graczy.
     *
     * @return lista graczy.
     */
    public List<Gracz> dajListeGraczy() {
        return listaGraczy;
    }

    /**
     * Zwraca zasady gry.
     *
     * @return zasady gry.
     */
    public ZasadyGry dajZasadyGry() {
        return zasadyGry;
    }

    /**
     * Zwraca ruch z poprzedniej tury.
     *
     * @return ruch z poprzedniej tury.
     */
    public int[][] dajRuchZPoprzedniejTury() {
        return ruchWPoprzedniejTurze;
    }

    /**
     * Zwraca indeks obecnego gracza.
     *
     * @return indeks obecnego gracza.
     */
    public int dajObecnegoGracza() {
        return kolejka.obecnyGracz();
    }

    /**
     * Sprawdza, czy ruch jest poprawny.
     *
     * @param sekwencjaRuchow sekwencja ruchów do sprawdzenia.
     * @param gracz           gracz wykonujący ruch.
     * @return true, jeśli ruch jest poprawny; false w przeciwnym wypadku.
     */
    public boolean ruchJestPoprawny(int[][] sekwencjaRuchow, int gracz) {
        return trwaTuraGracza(gracz) && zasadyGry.ruchJestPoprawny(planszaGry, sekwencjaRuchow, gracz);
    }

    /**
     * Zwraca opis gry.
     *
     * @return opis gry.
     */
    public String opis() {
        return "ID gry: " + ID_GRY + " | Zapełnienie: " + listaGraczy.size() + "/" + zasadyGry.ileGraczy();
    }
}

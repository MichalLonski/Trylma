package com.studia;

import com.studia.Plansza.Plansza;
import com.studia.Zasady.FabrykaZasad;
import com.studia.Zasady.TypGry;
import com.studia.Zasady.ZasadyGry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public Gra(TypGry typ, int liczbaGraczy) {
        this.zasadyGry = FabrykaZasad.stworzZasadyGry(typ, liczbaGraczy);
        this.listaGraczy = new ArrayList<>();
        ID_GRY = ID;
        ID++;
    }

    // TODO: info do graczy
    public int ZacznijGre() {
        if (listaGraczy.size() != zasadyGry.ileGraczy()) {
            System.err.println("Gra nie jest pełna");
            return -1;
        }
        kolejka = new KolejkaGraczy(listaGraczy);
        kolejka.ustawLosowo();
        planszaGry = new Plansza(zasadyGry.ileGraczy());
        planszaGry.utworzPlansze();
        graWTrakcie = true;
        return kolejka.obecnyGracz();
    }

    private class KolejkaGraczy {
        private List<Gracz> zakolejkowaniGracze;
        private int tura;
        private int graczyPozostalo;

        KolejkaGraczy(List<Gracz> lista) {
            this.zakolejkowaniGracze = lista;
            graczyPozostalo = lista.size();
        }

        // gracze w kolejce są 0-(n-1), ale ich numery to 1-n, bo 0 ma oznaczać puste
        // pole
        public Gracz ustawLosowo() {
            Collections.shuffle(zakolejkowaniGracze);
            tura = 0;
            for (int miejsce = 1; miejsce <= zakolejkowaniGracze.size(); miejsce++) {
                zakolejkowaniGracze.get(miejsce - 1).zajmijMiejsce(miejsce);
            }
            return zakolejkowaniGracze.get(tura);
        }

        public void wykonanoRuch() {
            tura = (tura + 1) % zakolejkowaniGracze.size();
            if (zakolejkowaniGracze.get(tura) == null) {
                wykonanoRuch();
            }
        }

        public int usunGracza(int miejsce) {
            zakolejkowaniGracze.set(miejsce, null);
            graczyPozostalo--;
            return graczyPozostalo;
        }

        public int obecnyGracz() {
            return tura + 1;
        }
    }

    public void wykonajRuch(int miejsceGracza, int[][] sekwencjaRuchow) {
        if (ruchJestPoprawny(sekwencjaRuchow, miejsceGracza)) {
            planszaGry.wykonajRuch(sekwencjaRuchow[0], sekwencjaRuchow[sekwencjaRuchow.length - 1], miejsceGracza);
            if (zasadyGry.checkWin(miejsceGracza)) {
                // TODO: gracz wygrywa - jakiś victory screen czy coś
                if (kolejka.usunGracza(miejsceGracza) == 1) {
                    // TODO: wszyscy wygrali prócz ostatniego gracza
                }
            }
            kolejka.wykonanoRuch();

            ruchWPoprzedniejTurze = new int[][] {
                    sekwencjaRuchow[0],
                    sekwencjaRuchow[sekwencjaRuchow.length - 1]
            };
        }
    }

    public boolean trwaTuraGracza(int miejsceGracza) {
        return (miejsceGracza == kolejka.obecnyGracz());
    }

    // TODO: synchroniczna - sprawdzić
    public synchronized void dodajGracza(Gracz gracz) {
        if (listaGraczy.size() == zasadyGry.ileGraczy()) {
            System.err.println("Nie można dołączyć do trwającej gry");
            return;
        }
        this.listaGraczy.add(gracz);
        // Moja modyfikacja
        gracz.przypiszGre(this);
    }

    // TODO: co jak wyjdzie podczas gry
    public void usunGracza(Gracz gracz) {
        if (listaGraczy.contains(gracz)) {
            listaGraczy.remove(gracz);
        }
    }

    /*
     * Chyba zbędne
     * public void informujGraczy(String wiadomosc) {
     * for (Gracz gracz : listaGraczy) {
     * gracz.informacjaOdGry(wiadomosc);
     * }
     * }
     */

    ///////////////////////////////////////////////
    ////////////// Gettery i Settery////////////////
    ///////////////////////////////////////////////

    // Dodany co by był jakiś dostęp do ID_Gry
    public int dajID() {
        return ID_GRY;
    }

    // Zwraca fakt czy gra się zaczełą czy nie
    public boolean czyGraSieZaczela() {
        return graWTrakcie;
    }

    // Zwraca liste graczy
    public List<Gracz> dajListeGraczy() {
        return listaGraczy;
    }

    // Zwraca zasady
    public ZasadyGry dajZasadyGry() {
        return zasadyGry;
    }

    // Zwraca ruch z poprzedniej tury
    public int[][] dajRuchZPoprzedniejTury() {
        return ruchWPoprzedniejTurze;
    }

    // Zwraca miejsce przy stole gracza którego tura trwa
    public int dajObecnegoGracza() {
        return kolejka.obecnyGracz();
    }

    public boolean ruchJestPoprawny(int[][] sekwencjaRuchow, int gracz) {
        return trwaTuraGracza(gracz) && zasadyGry.ruchJestPoprawny(planszaGry, sekwencjaRuchow, gracz);
    }

    public String opis() {
        return "ID gry: " + ID_GRY + " | Zapełnienie: " + listaGraczy.size() + "/" + zasadyGry.ileGraczy();
    }

}

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

    public Gra(TypGry typ, int[] parametry) {
        this.zasadyGry = FabrykaZasad.stworzZasadyGry(typ, parametry);
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
        kolejka = new KolejkaGraczy(this, listaGraczy);
        kolejka.ustawLosowo();
        planszaGry = new Plansza(zasadyGry.ileGraczy());
        planszaGry.utworzPlansze();
        informujGraczy("Gra się zaczyna");
        return kolejka.obecnyGracz();
    }

    private class KolejkaGraczy {
        private List<Gracz> zakolejkowaniGracze;
        private int tura;
        private Gra gra;

        KolejkaGraczy(Gra gra, List<Gracz> lista) {
            this.gra = gra;
            this.zakolejkowaniGracze = lista;
        }

        public Gracz ustawLosowo() {
            Collections.shuffle(zakolejkowaniGracze);
            tura = 0;
            for (int miejsce = 0; miejsce < zakolejkowaniGracze.size(); miejsce++) {
                zakolejkowaniGracze.get(miejsce).zajmijMiejsce(gra, miejsce);
            }
            return zakolejkowaniGracze.get(tura);
        }

        public void wykonanoRuch() {
            tura = (tura + 1) % zakolejkowaniGracze.size();
            if (zakolejkowaniGracze.get(tura) == null) {
                wykonanoRuch();
            }
        }

        // TODO: po wygranej
        @SuppressWarnings("unused")
        public void usunGracza(int miejsce) {
            zakolejkowaniGracze.set(miejsce, null);
        }

        public int obecnyGracz() {
            return tura;
        }
    }

    /*
     * Wiersz: A-M; korzystamy z rzutowania na int; 'A' = 65
     * Kolumna: 1-13
     * Pozycja wiersz + kolumna (konkatenacja, bez spacji, np A1, M12)
     */
    public void wykonajRuch(int miejsceGracza, String pozycjaPoczatkowa, String pozycjaKoncowa) {
        if (miejsceGracza != kolejka.obecnyGracz()) {
            System.err.println("Poczekaj na swoją kolej");
        } else {
            boolean udanyRuch = planszaGry.wykonajRuch(pozycjaPoczatkowa, pozycjaKoncowa, kolejka.obecnyGracz());
            if (udanyRuch) {
                kolejka.wykonanoRuch();
            }
        }
    }

    // TODO: synchroniczna - sprawdzić
    public synchronized void dodajGracza(Gracz gracz) {
        if (listaGraczy.size() == zasadyGry.ileGraczy()) {
            System.err.println("Nie można dołączyć do trwającej gry");
            return;
        }
        this.listaGraczy.add(gracz);
    }

    // TODO: co jak wyjdzie podczas gry
    public void usunGracza(Gracz gracz) {
        if (listaGraczy.contains(gracz)) {
            listaGraczy.remove(gracz);
        }
    }

    public void informujGraczy(String wiadomosc) {
        for (Gracz gracz : listaGraczy) {
            gracz.informacjaOdGry(wiadomosc);
        }
    }

    //Dodany co by był jakiś dostęp do ID_Gry
    public int dajID(){
        return ID_GRY;
    }
}

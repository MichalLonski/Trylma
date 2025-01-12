package com.studia.Komunikacja;

import com.studia.Gra;
import com.studia.Gracz;
import com.studia.Zasady.TypGry;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/*
    Klasa do zarządzania grami, aby na serwerze mogła się odbywać wiecej niż jedna gra
    plus jest singletonem wiec kolejny ten wzorzec z poprzedniej listy i guess
*/
public class ManagerGier {

    final private List<Gra> ListaGier = new ArrayList<>();
    final private Map<Gracz, PrintWriter> konsolaGraczy = new HashMap<>();
    // To raczej potrzebne nie będzie
    // List<Gracz> ListaGracz = new ArrayList<>();

    private ManagerGier() {
    }

    // Ogarnięcie Singletona
    private static class SingletonHelper {
        volatile static ManagerGier INSTANCE = new ManagerGier();
    }

    public static ManagerGier dajInstancje() {
        return SingletonHelper.INSTANCE;
    }

    // Inicjuje nową gre i dodaje do niego gracza który ją stworzył
    public synchronized void inicjujNowaGre(TypGry typGry, int[] parametry, Gracz inicjujacyGracz) {
        Gra nowaGra = new Gra(typGry, parametry);
        ListaGier.add(nowaGra);
        nowaGra.dodajGracza(inicjujacyGracz);
    }

    // Dodaje gracza do istniejącej gry, zwraca 1 dla sukcesu i -1 gdy gra jest
    // pełna lub nie isnieje
    public synchronized int dolaczDoGry(Gracz gracz, String id) {

        int iD = Integer.parseInt(id);

        if (znajdzGrePoID(iD) == null) {
            return -1;
        } else if (znajdzGrePoID(iD).dajZasadyGry().ileGraczy() == znajdzGrePoID(iD).dajListeGraczy().size()) {
            return -1;
        } else {
            Gra gra = znajdzGrePoID(iD);
            gra.dodajGracza(gracz);
            komunikatDlaGraczyGry(gra, "Gracz dołączył do gry &");
            jesliGraPelnaRozpocznij(gracz.dajGre());
            return 1;
        }
    }

    // Wywołuje rozpocznijGre jeśli gra jest pełna
    public synchronized void jesliGraPelnaRozpocznij(Gra gra) {
        if (gra.dajZasadyGry().ileGraczy() == gra.dajListeGraczy().size()) {
            rozpocznijGre(gra);
        }
    }

    // Wywołuje rozpoczęcie gry oraz wysyła komunikat do wszystkich o rozpoczeciu
    private synchronized void rozpocznijGre(Gra gra) {
        gra.ZacznijGre();
        komunikatDlaGraczyGry(gra, "Ostatni gracz dołączył &Gra rozpoczyna się &Wciśnij ENTER by odświeżyć");
    }

    // Wywołuje wykonanie ruchu na graczu oraz wysyła komunikat do wszystkich o
    // wykonaniu ruchu
    public synchronized void wykonajRuch(Gracz gracz, String[] sekwencjaRuchow) {
        gracz.wykonajRuch(sekwencjaRuchow);
        komunikatDlaGraczyGry(gracz.dajGre(),
                "Gracz " + gracz.ktoreMiejsce() + " wykonał ruch &wciśnij Enter by odświeżyć");
    }

    // Zwraca gre po podanym ID
    public Gra znajdzGrePoID(int id) {
        for (Gra g : ListaGier) {
            if (g.dajID() == id) {
                return g;
            }
        }
        return null;
    }

    // Wysyła wiadomośc każdemu graczowi w grze
    private void komunikatDlaGraczyGry(Gra gra, String komunikat) {
//        for (Gracz g : gra.dajListeGraczy()) {
//            PrintWriter out = konsolaGracza(g);
//            out.println(komunikat);
//        }
        //TODO do zmainy by wypisywało się gdzieś na jakimś czacie czy czymś takim
    }

    // Dodaje gracza do mapy
    public void zarejestrujGracza(Gracz gracz, PrintWriter printWriter) {
        konsolaGraczy.put(gracz, printWriter);
    }

    // Zwraca PrintWriter gracza
    public PrintWriter konsolaGracza(Gracz gracz) {
        return konsolaGraczy.get(gracz);
    }

    public String wypiszGry(){
        String wynik = "";
        for (Gra gra : ListaGier) {
            wynik += gra.opis() + "&";
        }
        return wynik;
    }
}

package com.studia;

import com.studia.Komunikacja.KlientApplication;

/**
 * Klasa odpowiedzialna za uruchomienie aplikacji klienta gry.
 * Jest to punkt wejścia do aplikacji klienta, który inicjuje instancję klienta gry i uruchamia go.
 */
public class Klient {

    /**
     * Punkt wejścia do programu. Tworzy nową instancję aplikacji klienta i uruchamia ją.
     * 
     * @param args Argumenty wiersza poleceń (nieużywane w tej klasie).
     */
    public static void main(String[] args) {
        KlientApplication klient = new KlientApplication();
        klient.start(args);
    }
}

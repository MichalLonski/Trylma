package com.studia;

import com.studia.Komunikacja.SerwerGry;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Klasa odpowiedzialna za uruchomienie serwera gry.
 * Jest to punkt wejścia do aplikacji, który inicjuje instancję serwera gry i uruchamia ją.
 */
@SpringBootApplication
public class WlaczSerwer {

    /**
     * Punkt wejścia do programu. Tworzy nową instancję serwera gry i uruchamia go.
     * 
     * @param args Argumenty wiersza poleceń (nieużywane w tej klasie).
     */
    public static void main(String[] args) {
        SerwerGry serwerGry = new SerwerGry();
        serwerGry.start();
    }
}

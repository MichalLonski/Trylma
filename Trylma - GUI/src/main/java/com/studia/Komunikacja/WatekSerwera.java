package com.studia.Komunikacja;

import java.util.Scanner;

/**
 * Wątek odpowiedzialny za komunikację z serwerem gry.
 * Obsługuje wejście użytkownika z konsoli i umożliwia wykonywanie różnych poleceń.
 */
public class WatekSerwera extends Thread {

    /**
     * Serwer gry, z którym komunikuje się wątek.
     */
    private SerwerGry serwerGry;

    /**
     * Przechowuje wprowadzoną wiadomość.
     */
    private String wiadomosc;

    /**
     * Obiekt Scanner do odczytu danych z konsoli.
     */
    private Scanner scanner;

    /**
     * Konstruktor tworzący nowy wątek serwera.
     * 
     * @param serwer Instancja serwera gry, z którym będzie współpracować wątek.
     */
    WatekSerwera(SerwerGry serwer) {
        serwerGry = serwer;
        scanner = new Scanner(System.in);
    }

    /**
     * Metoda uruchamiana po starcie wątku. 
     * Oczekuje na wprowadzenie wiadomości przez użytkownika i reaguje na polecenia.
     */
    @Override
    public void run() {
        while (true) {
            wiadomosc = scanner.nextLine().stripLeading().stripTrailing().toLowerCase();
            if ("".equals(wiadomosc)) {
                continue;
            } else if ("exit".equals(wiadomosc)) {
                quit();
                break;
            }
        }
    }

    /**
     * Zamyka wątek i kończy działanie serwera gry.
     * Zamyka również obiekt Scanner.
     */
    public void quit() {
        scanner.close();
        serwerGry.koniec();
    }
}

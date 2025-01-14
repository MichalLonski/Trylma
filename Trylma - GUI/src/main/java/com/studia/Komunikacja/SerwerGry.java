package com.studia.Komunikacja;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca serwer gry. Odpowiada za obsługę połączeń przychodzących
 * od klientów,
 * zarządzanie wątkami dla graczy i zakończenie pracy serwera.
 * 
 * Serwer uruchamia nasłuch na określonym porcie i czeka na połączenia od
 * klientów.
 * Każde połączenie obsługiwane jest przez osobny wątek, który komunikuje się z
 * klientem,
 * obsługując jego komendy.
 */
public class SerwerGry {

    /** Flaga wskazująca, czy serwer jest włączony */
    public static boolean SerwerWlaczony = false;

    /** Numer portu, na którym nasłuchuje serwer */
    public static final int nrPortu = 8000;

    /** Adres hosta, na którym działa serwer */
    public static final String host = "localhost";

    /** Lista przechowująca wątki graczy obsługiwanych przez serwer */
    private List<WatekGracza> listaWatkow;

    /**
     * Konstruktor serwera gry. Inicjalizuje listę przechowującą wątki graczy.
     */
    public SerwerGry() {
        listaWatkow = new ArrayList<WatekGracza>();
    }

    /**
     * Uruchamia serwer gry. Tworzy gniazdo serwera i nasłuchuje na połączenia
     * przychodzące.
     * Każde połączenie od klienta jest obsługiwane przez osobny wątek.
     */
    public void start() {
        SerwerWlaczony = true;

        WatekSerwera watekKonsolaSerwera = new WatekSerwera(this);
        watekKonsolaSerwera.start();

        try (ServerSocket serverSocket = new ServerSocket(nrPortu)) {
            System.out.println("Serwer włączony");

            while (SerwerWlaczony) {
                Socket clientSocket = serverSocket.accept();

                if (SerwerWlaczony) {
                    WatekGracza watek = new WatekGracza(clientSocket);
                    watek.start();

                    listaWatkow.add(watek);
                }
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd serwera: " + e.getMessage());
            watekKonsolaSerwera.quit();
        }
    }

    /**
     * Zamyka serwer gry, kończy wszystkie połączenia i wątki graczy.
     */
    public void koniec() {
        SerwerWlaczony = false;

        try {
            new Socket(host, nrPortu).close();
        } catch (Exception e) {
        }

        for (WatekGracza watekGracza : listaWatkow) {
            watekGracza.quit();
        }

        System.out.println("Serwer zamknięty");
    }
}

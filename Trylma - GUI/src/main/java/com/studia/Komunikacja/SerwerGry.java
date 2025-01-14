package com.studia.Komunikacja;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * Klasa servera i wątku,klasa wątku przechowuje gracza za którego odpowiada
 * (Do zmiany jak klient będzie brał udział w wiecej niż jednej grze)
 * generalnie wątek działa tak że switchuje się po enumie Stan
 * Aby serwer mógł jednorazowy output przesłać w jednej linijce
 * zamiast znaków \n wysyła &które klient sam zamienia na \n.
 * Głównie istaniało to zanim tu zrobiłem po prostu osobny wątek do odczytywania tego
 * i trochę dużo tego do poprawy się zrobiło, jak to się jakkolwiek problemem okaże to to poprawie
 */

public class SerwerGry {
    public static boolean SerwerWlaczony = false;
    public static final int nrPortu = 8000;
    public static final String host = "localhost";
    private List<WatekGracza> listaWatkow;

    public SerwerGry() {
        listaWatkow = new ArrayList<WatekGracza>();
    }

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

    public void koniec() {
        SerwerWlaczony = false;
        try {
            new Socket(host, nrPortu).close();
        } catch (Exception e) {}
        for (WatekGracza watekGracza : listaWatkow) {
            watekGracza.quit();
        }
        System.out.println("Serwer zamknięty");
    }
}

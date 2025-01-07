package com.studia.Komunikacja;

import java.io.*;
import java.net.*;

/* 
 Taki najprostszt na świecie klient ale na razie nie czułem potrzeby tego jakkolwiek zmieniać
 Aby serwer mógł jednorazowy output przesłać w jednej linijce zamiast znaków \n wysyła &
 które klient sam zamienia na \n. Głównie istaniało to zanim tu zrobiłem po prostu osobny
 wątek do odczytywania tego i trochę dużo tego do poprawy się zrobiło,
 jak to sie jakkolwiek problemem okaże to to poprawie
 */
public class Klient {

    private static BufferedReader in;
    private static PrintWriter out;
    private static BufferedReader userInput;

    private static Thread watekPisacz;

    public static void main(String[] args) {

        try (Socket socket = new Socket(SerwerGry.host, SerwerGry.nrPortu)) {
            System.out.println("Połączono z serwerem: " + SerwerGry.host + ":" + SerwerGry.nrPortu);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            userInput = new BufferedReader(new InputStreamReader(System.in));

            // Wątek który ciągle zczytuje wyjście z serwera
            Thread watekCzytacz = new Thread(() -> {
                try {
                    String wiadomoscOdServer;
                    while ((wiadomoscOdServer = in.readLine()) != null) { // Zczytanie odpowiedzi
                        wiadomoscOdServer = wiadomoscOdServer.replace("&", "\n"); // zmienienie & na \n aby dało sie
                                                                                  // wszystko w jednej linijce wysłać
                        if ("exit".equals(wiadomoscOdServer)) {
                            out.println(wiadomoscOdServer);
                            quit();
                            watekPisacz.interrupt();
                            break;
                        }
                        System.out.println(wiadomoscOdServer); // i jej druk
                    }
                } catch (IOException e) {
                    System.err.println("Błąd odczytu z serwera: " + e.getMessage());
                }
            });
            watekCzytacz.start();

            // Klient najpierw czekan na wiadomośc z serwera
            String wiadomoscOdServer = in.readLine(); // Czekanie na wiadomość od serwera
            System.out.println("Otrzymano od serwera: " + wiadomoscOdServer.replace("&", "\n"));
            watekPisacz = new Thread(() -> {
                try {
                    String message;
                    while ((message = userInput.readLine()) != null) {
                        out.println(message); // Wysyłanie wiadomości
                        if ("exit".equals(message.stripLeading().stripTrailing().toLowerCase())) {
                            quit();
                            break;
                        }
                    }
                } catch (IOException e) {
                }
            });
            watekPisacz.start();

            try {
                watekPisacz.join();
            } catch (InterruptedException e) {
            }

        } catch (IOException e) {
            System.err.println("Błąd klienta: " + e.getMessage());
        }
    }

    private static void quit() throws IOException {
        in.close();
        out.close();
        userInput.close();
    }
}

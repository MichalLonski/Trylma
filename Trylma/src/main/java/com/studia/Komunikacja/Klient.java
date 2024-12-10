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

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Połączono z serwerem: " + host + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            //Wątek który ciągle zczytuje wyjście z serwera
            Thread watekCzytacz = new Thread(() -> {
                try {
                    String wiadomoscOdServer;
                    while ((wiadomoscOdServer = in.readLine()) != null) { // Zczytanie odpowiedzi
                        wiadomoscOdServer = wiadomoscOdServer.replace("&", "\n"); // zmienienie & na \n co by dało śie wszystko w jednej linijce wysłąć
                        System.out.println(wiadomoscOdServer); // i jej druk
                    }
                } catch (IOException e) {
                    System.err.println("Błąd odczytu z serwera: " + e.getMessage());
                }
            });
            watekCzytacz.start();

            //Klient najpierw czekan na wiadomośc z serwera
            String wiadomoscOdServer = in.readLine(); // Czekanie na wiadomość od serwera
            System.out.println("Otrzymano od serwera: " + wiadomoscOdServer.replace("&", "\n"));
            String message;

            //Pętla wysyła treść linijki na wyjście
            while ((message = userInput.readLine()) != null) {
                out.println(message); // Wysyłanie wiadomości
            }

        } catch (IOException e) {
            System.err.println("Błąd klienta: " + e.getMessage());
        }

    }



}

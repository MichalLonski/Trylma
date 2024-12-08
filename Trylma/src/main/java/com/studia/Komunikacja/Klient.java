package com.studia.Komunikacja;
import java.io.*;
import java.net.*;

/*
Taki najprostszt na świecie klient ale na razie nie czułem potrzeby tego jakkolwiek zmieniać
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

            String message;
            System.out.println("Wpisz wiadomość:");

            //Czyta pierwszą wiadomość od servera
            String wiadomoscOdServer = in.readLine(); // Odpowiedź
            TekstMenu.drukujTekst(wiadomoscOdServer); // Dekoduj odpowiedź

            while ((message = userInput.readLine()) != null) {
                out.println(message); // Wysyłanie wiadomości
                wiadomoscOdServer = in.readLine(); // Odpowiedź
                TekstMenu.drukujTekst(wiadomoscOdServer); // Dekoduj odpowiedź


            }
        } catch (IOException e) {
            System.err.println("Błąd klienta: " + e.getMessage());
        }
    }
}

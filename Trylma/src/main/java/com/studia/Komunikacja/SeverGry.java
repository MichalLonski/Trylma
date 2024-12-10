package com.studia.Komunikacja;

import com.studia.Gracz;
import com.studia.Zasady.TypGry;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/*
Klasa servera i wątku,klasa wątku przechowuje gracza za którego odpowiada
(Do zmiany jak klient będzie brał udział w wiecej niż jednej grze)
generalnie wątek działa tak że switchuje się po enumie Stan
Aby serwer mógł jednorazowy output przesłać w jednej linijce zamiast znaków \n wysyła &
które klient sam zamienia na \n. Głównie istaniało to zanim tu zrobiłem po prostu osobny
wątek do odczytywania tego i trochę dużo tego do poprawy się zrobiło,
jak to sie jakkolwiek problemem okaże to to poprawie
 */
public class SeverGry {
    static boolean SerwerWlaczony = true;
    public static void main(String[] args) throws Exception {

        //Prosta obsługa konsoli serwera, nie wiem co tam jeszcze dodać ale jak coś to jest
        Thread watekKonsolaSerwera = new Thread(() -> {
            String wiadomosc;
            Scanner scanner = new Scanner(System.in);
            while(true){

                wiadomosc = scanner.nextLine();


                if ("exit".equalsIgnoreCase(wiadomosc)) {
                    System.out.println("Serwer wyłączony");
                    SerwerWlaczony = false;
                    break;
                } else {
                    System.out.println("Wpisano: " + wiadomosc);
                }
            }
        });
        watekKonsolaSerwera.start();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Serwer nasłuchuje...");
            while (SerwerWlaczony) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowy klient połączony: " + clientSocket.getInetAddress().getHostAddress());
                new WatekGracz(clientSocket).start();
            }
        }
    }

}

class WatekGracz extends Thread {
    private StanKlienta stan = StanKlienta.MENUGLOWNE;
    private final Socket clientSocket;
    private final Gracz gracz = new Gracz();

    public WatekGracz(Socket socket){
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            ManagerGier.dajInstancje().zarejestrujGracza(gracz,out);
            String wiadamoscKlienta;
            out.println(TekstMenu.drukujTekst("a",gracz));// Drukuje menu główne
            while ((wiadamoscKlienta = in.readLine()) != null) {
                String[] slowa = wiadamoscKlienta.split(" ");

                switch (stan){

                    case StanKlienta.MENUGLOWNE:

                        switch(slowa[0]){
                            case "1":
                                stan = StanKlienta.TWORZYGRE;
                                out.println(TekstMenu.drukujTekst("b",gracz));
                                break;
                            case "2":
                                if(ManagerGier.dajInstancje().znajdzGrePoID(Integer.parseInt(slowa[1])) == null){
                                    out.println("Taka gra nie isnieje &" + TekstMenu.drukujTekst("a1",gracz));
                                }else if(ManagerGier.dajInstancje().znajdzGrePoID(Integer.parseInt(slowa[1])).czyGraSieZaczela()){
                                    out.println("Ta gra jest pełna &" + TekstMenu.drukujTekst("a1",gracz));
                                }else {
                                    stan = StanKlienta.WGRZE;
                                    ManagerGier.dajInstancje().dolaczDoGry(gracz,slowa[1]);
                                    out.println(TekstMenu.drukujTekst("d",gracz));
                                    ManagerGier.dajInstancje().jesliGraPelnaRozpocznij(gracz.dajGre());
                                }
                                break;
                            case "3":
                                out.println(TekstMenu.drukujTekst("c",gracz));
                                break;
                            default:
                                out.println(TekstMenu.drukujTekst("a",gracz));
                                break;
                        }
                        break;

                    case StanKlienta.TWORZYGRE:

                        //TODO: Sprawdzenie czy input jest poprawny ale to tam kiedyś

                        TypGry typgry = switch (slowa[1]) {
                            case "1" -> TypGry.STANDARDOWA;
                            case "2" -> TypGry.ZESPOLOWA;
                            case "3" -> TypGry.ROZSZERZONA;
                            default -> null;
                        };

                        int[] parametry = new int[slowa.length-2];
                        for(int i = 2;i < slowa.length;i++){
                            parametry[i-2] = Integer.parseInt(slowa[i]);
                        }

                        ManagerGier.dajInstancje().inicjujNowaGre(typgry,parametry,gracz);
                        stan = StanKlienta.WGRZE;
                        out.println(TekstMenu.drukujTekst("d",gracz));

                        break;

                    case StanKlienta.WGRZE:

                        //Jesli gra się nie zaczeła wyświetlaj ekran o oczekiwaniu na gracza
                        //Gdy się zaczeła jeśli nie twoja tura dostajesz ekran z informacją jaki gracz mature
                        //Jeśli jest twoja dostajesz ekran z instrukcją wykonania ruchu
                        if(gracz.dajGre().czyGraSieZaczela()) {

                            //Aby tekst drukował się tylko gdy klikasz enter na pustej linii
                            //a nie na przykład jak wpisujesz twój ruch
                            if(slowa[0].isEmpty()){
                                out.println(TekstMenu.drukujTekst("e",gracz));
                            }

                            //Warunek sprawdza czy podane koordynaty mieszczą się w istniejących zakresach
                            if(slowa[0].equals("ruch") && String.valueOf(slowa[1].charAt(0)).matches("[A-M]")
                                    && String.valueOf(slowa[1].charAt(1)).matches("(1[0-2]|[1-9])")
                                    && String.valueOf(slowa[2].charAt(0)).matches("[A-M]")
                                    && String.valueOf(slowa[2].charAt(1)).matches("(1[0-2]|[1-9])")
                                    && (gracz.ktoreMiejsce() == gracz.dajGre().dajObecnegoGracza())){

                                ManagerGier.dajInstancje().wykonajRuch(gracz,slowa[1],slowa[2]);

                            }
//
                        }else {
                            out.println(TekstMenu.drukujTekst("d",gracz));

                        }

                        break;
                }

            }

        } catch (IOException e) {
            System.err.println("Błąd podczas obsługi klienta: " + e.getMessage());
        }

    }

}

package com.studia;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/*
Klasa servera i wątku,klasa wątku przechowuje gracza za którego odpowiada
(Do zmiany jak klient będzie brał udział w wiecej niż jednej grze)
generalnie wątek działa tak że switchuje się po enumie Stan
 */
public class SeverGry {

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Serwer nasłuchuje...");
            while (true) {
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
            String wiadamoscKlienta;
            out.println("a");// Drukuje menu główne
            while ((wiadamoscKlienta = in.readLine()) != null) {
                String[] slowa = wiadamoscKlienta.split(" ");

                switch (stan){

                    case StanKlienta.MENUGLOWNE:

                        switch(slowa[0]){
                            case "1":
                                stan = StanKlienta.TWORZYGRE;
                                out.println("b");
                                break;
                            case "2":
                                if(ManagerGier.dajInstancje().dolaczDoGry(gracz,slowa[1]) == -1){
                                    out.println("a 1");
                                }else {
                                    stan = StanKlienta.WGRZE;
                                    out.println("b");
                                }
                                out.flush();
                                break;
                            case "3":
                                OpcjaDrukujGry();
                                out.println("a");
                                break;
                            default:
                                out.println("a");
                                break;
                        }
                        break;

                    case StanKlienta.TWORZYGRE:

                        TypGry typgry = switch (slowa[1]) {
                            case "1" -> TypGry.STANDARDOWA;
                            case "2" -> TypGry.ZESPOLOWA;
                            case "3" -> TypGry.ROZSZERZONA;
                            default -> null;
                        };

                        int[] parametry = new int[slowa.length-1];
                        for(int i = 1;i < slowa.length;i++){
                            parametry[i-1] = Integer.parseInt(slowa[i]);
                        }

                        ManagerGier.dajInstancje().inicjujNowaGre(typgry,parametry,gracz);
                        stan = StanKlienta.WGRZE;

                        break;

                    case StanKlienta.WGRZE:

                        break;
                }

            }

        } catch (IOException e) {
            System.err.println("Błąd podczas obsługi klienta: " + e.getMessage());
        }

    }

    private void OpcjaDrukujGry(){
        //TODO: Print gier z managera
    }
}

package com.studia.Komunikacja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.studia.Gracz;
import com.studia.Zasady.TypGry;

public class WatekGracza extends Thread {

    private final Socket clientSocket;
    private final Gracz gracz;
    private BufferedReader in;
    private PrintWriter out;

    public WatekGracza(Socket socket) {
        this.clientSocket = socket;
        this.gracz = new Gracz();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            ManagerGier.dajInstancje().zarejestrujGracza(gracz, out);
            String wiadomoscOdKlienta;
            while ((wiadomoscOdKlienta = in.readLine()) != null) {
                if ("exit".equals(wiadomoscOdKlienta)) {
                    in.close();
                    out.close();
                    quit();
                    break;
                }
                String[] slowa = wiadomoscOdKlienta.split(" ");
                switch (slowa[0]) {

                        //Przycisk dołącz do gry
                    case "join":
                        int i = ManagerGier.dajInstancje().dolaczDoGry(gracz,slowa[1]);
                        if(i == 1){
                            out.println("success");
                        }else{
                            out.println("fail");
                        }
                        break;

                        //Przycisk dołącz do gry
                    case "create":

                        TypGry typgry = switch (slowa[1]) {
                            case "Standardowy" -> TypGry.STANDARDOWA;
                            case "Wariant 2" -> TypGry.ZESPOLOWA;
                            case "Wariant 3" -> TypGry.ROZSZERZONA;
                            default -> null;
                        };

                        int[] parametry = new int[slowa.length - 2];
                        for (int j = 2; j < slowa.length; j++) {
                            parametry[j - 2] = Integer.parseInt(slowa[j]);
                        }
                        ManagerGier.dajInstancje().inicjujNowaGre(typgry, parametry, gracz);
                        out.println("success");

                        break;

                        //Przycisk odśwież
                    case "refresh":
                        out.println(ManagerGier.dajInstancje().wypiszGry());
                        break;

                    case "#players":
                        out.println(gracz.dajGre().dajZasadyGry().ileGraczy());
                        break;
                }

            }
        } catch (IOException e) {
            System.err.println("Błąd podczas obsługi klienta: " + e.getMessage());
        }

    }

    public Gracz getGracz() {
        return this.gracz;
    }

    public void quit() {
        out.println("exit");
    }

}

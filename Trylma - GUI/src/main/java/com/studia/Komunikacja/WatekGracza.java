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

                    // Przycisk dołącz do gry
                    case "join":
                        int i = ManagerGier.dajInstancje().dolaczDoGry(gracz, slowa[1]);
                        if (i == 1) {
                            out.println("success");
                        } else {
                            out.println("fail");
                        }
                        break;

                    // Przycisk dołącz do gry
                    case "create":

                        TypGry typgry = switch (slowa[1]) {
                            case "Standardowy" -> TypGry.STANDARDOWA;
                            case "SuperChineseCheckers" -> TypGry.FAST_PACED;
                            case "Capture" -> TypGry.CAPTURE;
                            default -> null;
                        };

                        int liczbaGraczy = Integer.parseInt(slowa[2]);
                        ManagerGier.dajInstancje().inicjujNowaGre(typgry, liczbaGraczy, gracz);
                        out.println("success");

                        break;

                    // Przycisk odśwież
                    case "refresh":
                        out.println(ManagerGier.dajInstancje().wypiszGry());
                        break;
                    case "#players":
                        out.println(gracz.dajGre().dajZasadyGry().ileGraczy());
                        break;
                    case "currentPlayer":
                        out.println(gracz.dajGre().dajObecnegoGracza());
                        break;
                    case "playerSeat":
                        out.println(gracz.ktoreMiejsce());
                        break;
                    case "gameRules":
                        out.println(gracz.dajGre().dajZasadyGry().toString());
                        break;
                    case "hasStarted":
                        out.println(gracz.dajGre().czyGraSieZaczela());
                        break;
                    case "#playersGame":
                        out.println(gracz.dajGre().dajListeGraczy().size());
                        break;
                    case "checkMove":
                        int[][] sekwencjaRuchow = new int[slowa.length - 1][2];

                        for (int j = 0; j < slowa.length - 1; j++) {
                            sekwencjaRuchow[j] = new int[] {
                                    Integer.parseInt(slowa[j + 1].split("!")[0]),
                                    Integer.parseInt(slowa[j + 1].split("!")[1]) };

                        }

                        out.println(gracz.dajGre().ruchJestPoprawny(sekwencjaRuchow, gracz.ktoreMiejsce()));
                        break;
                    case "lastMove":
                        int[][] ostatniRuchInt = gracz.dajGre().dajRuchZPoprzedniejTury();
                        String ostatniRuch = ostatniRuchInt[0][0] + "!" + ostatniRuchInt[0][1] + " " +
                                ostatniRuchInt[1][0] + "!" + ostatniRuchInt[1][1];
                        out.println(ostatniRuch);
                        break;
                    case "doMove":
                        int[][] sekwencjaRuchow1 = new int[slowa.length - 1][2];

                        for (int j = 0; j < slowa.length - 1; j++) {
                            sekwencjaRuchow1[j] = new int[] {
                                    Integer.parseInt(slowa[j + 1].split("!")[0]),
                                    Integer.parseInt(slowa[j + 1].split("!")[1]) };

                        }

                        gracz.dajGre().wykonajRuch(gracz.ktoreMiejsce(), sekwencjaRuchow1);
                        out.println("success");
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

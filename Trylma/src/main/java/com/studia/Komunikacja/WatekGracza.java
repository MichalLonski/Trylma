package com.studia.Komunikacja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.studia.Gracz;
import com.studia.Zasady.TypGry;

public class WatekGracza extends Thread {
    private StanKlienta stan = StanKlienta.MENUGLOWNE;
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
            out.println(TekstMenu.drukujTekst("a", gracz)); // Drukuje menu główne
            while ((wiadomoscOdKlienta = in.readLine()) != null) {
                if ("exit".equals(wiadomoscOdKlienta)) {
                    in.close();
                    out.close();
                    quit();
                    break;
                }
                String[] slowa = wiadomoscOdKlienta.split(" ");
                switch (stan) {
                    case StanKlienta.MENUGLOWNE:
                        switch (slowa[0]) {
                            case "1":
                                stan = StanKlienta.TWORZYGRE;
                                out.println(TekstMenu.drukujTekst("b", gracz));
                                break;
                            case "2":
                                if (ManagerGier.dajInstancje().znajdzGrePoID(Integer.parseInt(slowa[1])) == null) {
                                    out.println("Taka gra nie isnieje &" + TekstMenu.drukujTekst("a1", gracz));
                                } else if (ManagerGier.dajInstancje().znajdzGrePoID(Integer.parseInt(slowa[1]))
                                        .czyGraSieZaczela()) {
                                    out.println("Ta gra jest pełna &" + TekstMenu.drukujTekst("a1", gracz));
                                } else {
                                    stan = StanKlienta.WGRZE;
                                    ManagerGier.dajInstancje().dolaczDoGry(gracz, slowa[1]);
                                    out.println(TekstMenu.drukujTekst("d", gracz));
                                    ManagerGier.dajInstancje().jesliGraPelnaRozpocznij(gracz.dajGre());
                                }
                                break;
                            case "3":
                                out.println(TekstMenu.drukujTekst("c", gracz));
                                break;
                            default:
                                out.println(TekstMenu.drukujTekst("a", gracz));
                                break;
                        }
                        break;

                    case StanKlienta.TWORZYGRE:
                        // TODO: Sprawdzenie czy input jest poprawny ale to tam kiedyś

                        TypGry typgry = switch (slowa[1]) {
                            case "1" -> TypGry.STANDARDOWA;
                            case "2" -> TypGry.ZESPOLOWA;
                            case "3" -> TypGry.ROZSZERZONA;
                            default -> null;
                        };
                        if (typgry == null){
                            continue;
                        }
                        int[] parametry = new int[slowa.length - 2];
                        for (int i = 2; i < slowa.length; i++) {
                            parametry[i - 2] = Integer.parseInt(slowa[i]);
                        }
                        ManagerGier.dajInstancje().inicjujNowaGre(typgry, parametry, gracz);
                        stan = StanKlienta.WGRZE;
                        out.println(TekstMenu.drukujTekst("d", gracz));
                        break;
                    case StanKlienta.WGRZE:
                        // Jesli gra się nie zaczeła wyświetlaj ekran o oczekiwaniu na gracza
                        // Gdy się zaczeła jeśli nie twoja tura dostajesz ekran z informacją jaki gracz
                        // mature
                        // Jeśli jest twoja dostajesz ekran z instrukcją wykonania ruchu
                        if (gracz.dajGre().czyGraSieZaczela()) {
                            // Aby tekst drukował się tylko gdy klikasz enter na pustej linii
                            // a nie na przykład jak wpisujesz twój ruch
                            if (slowa[0].isEmpty()) {
                                out.println(TekstMenu.drukujTekst("e", gracz));
                            }
                            // Warunek sprawdza czy podane koordynaty mieszczą się w istniejących zakresach
                            if (slowa[0].equals("ruch") && String.valueOf(slowa[1].charAt(0)).matches("[A-M]")
                                    && String.valueOf(slowa[1].charAt(1)).matches("(1[0-2]|[1-9])")
                                    && String.valueOf(slowa[2].charAt(0)).matches("[A-M]")
                                    && String.valueOf(slowa[2].charAt(1)).matches("(1[0-2]|[1-9])")
                                    && (gracz.ktoreMiejsce() == gracz.dajGre().dajObecnegoGracza())) {

                                ManagerGier.dajInstancje().wykonajRuch(gracz, slowa[1], slowa[2]);

                            }
                        } else {
                            out.println(TekstMenu.drukujTekst("d", gracz));
                        }
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
        out.println(TekstMenu.drukujTekst("exit", gracz));
    }

}

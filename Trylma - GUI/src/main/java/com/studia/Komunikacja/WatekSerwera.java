package com.studia.Komunikacja;

import java.util.Scanner;

public class WatekSerwera extends Thread {

    private SerwerGry serwerGry;
    private String wiadomosc;
    private Scanner scanner;

    WatekSerwera(SerwerGry serwer) {
        serwerGry = serwer;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            wiadomosc = scanner.nextLine().stripLeading().stripTrailing().toLowerCase();
            if ("".equals(wiadomosc)) {
                continue;
            } else if ("exit".equals(wiadomosc)) {
                quit();
                break;
            } else if (wiadomosc.startsWith("create game")) {
                System.err.println("create game");
            } else {
                System.out.println("Wpisano: " + wiadomosc);
            }
        }
    }

    public void quit() {
        scanner.close();
        serwerGry.koniec();
    }
}

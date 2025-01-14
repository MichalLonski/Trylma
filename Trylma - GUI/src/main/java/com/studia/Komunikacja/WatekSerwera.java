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
            }
        }
    }

    public void quit() {
        scanner.close();
        serwerGry.koniec();
    }
}

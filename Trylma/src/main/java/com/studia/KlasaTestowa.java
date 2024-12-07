package com.studia;

/*
 * Moja wizja komunikacji jest taka +-:
 * Serwer się włącza, potem mogą dołącząć klienci
 * W konsoli serwera jest opcja dodania gry z zadanymi zasadami
 * Klient może zobaczyć aktywne gry ich zapełnienie i zasady
 * Klient może dołączyć podając ID gry
 * Gdy gra się zapełni wysyła wiadomość do graczy i czeka aż wszyscy potwierdzą
 * 
 */


// Klasa testowa
public class KlasaTestowa {

    public static void main(String[] args) {
        Gra g = new Gra(TypGry.STANDARDOWA, new int[]{2});
        Gracz p1 = new Gracz();
        Gracz p2 = new Gracz();
        g.dodajGracza(p1);
        g.dodajGracza(p2);
        g.ZacznijGre();
        p1.wykonajRuch("A1", "A2");
        p1.wykonajRuch("B1", "B2");
        p2.wykonajRuch("C1", "C2");
        p1.wykonajRuch("D1", "D2");
    }

}
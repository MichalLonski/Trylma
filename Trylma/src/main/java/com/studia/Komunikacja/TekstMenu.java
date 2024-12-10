package com.studia.Komunikacja;

import com.studia.Gra;
import com.studia.Gracz;

/*
Klasa istnieje po to aby ten sporo switch w wątkuServera wyglądał trochę czytalniej,
można potem te kody jakoś czytelniej napisać, czy jakies lepsze rowiązanie zastosować
bo a,b itd nie znaczą za dużo ale na razeie imo jest okej

a-menu główne
b-menu tworzenia gry
c-print listy gier
d-oczekiwanie na gre
e-gra trwa

Aby serwer mógł jednorazowy output przesłać w jednej linijce zamiast znaków \n wysyła &
które klient sam zamienia na \n. Głównie istaniało to zanim tu zrobiłem po prostu osobny
wątek do odczytywania tego i trochę dużo tego do poprawy się zrobiło,
jak to sie jakkolwiek problemem okaże to to poprawie
*/
public class TekstMenu {

    public static String drukujTekst(String string, Gracz gracz) {
        String[] podzielnyString = string.split(" ");
        return switch (podzielnyString[0]) {
            case "a" -> menuGlowneTekst();
            case "a1" -> nieMaTakiejGry();
            case "b" -> stworzGreTeskt();
            case "c" -> listaGierTekst();
            case "d" -> oczekiwanieNaGreTekst(gracz);
            case "e" -> graczWGrze(gracz);
            default -> string;
        };
    }

    private static String nieMaTakiejGry() {
        return ("Nie udało się dołączyć do gry &") + menuGlowneTekst();
    }

    private static String menuGlowneTekst() {

        return ("Wybierz Opcje: &" +
                "By stworzyć nową gre wpisz: \"1\" &" +
                "By dołączyć do istniejącej gry wpisz \"2 <id gry do dołączenia> \" &" +
                "By pokazać istniejące gry \"3\" &");

    }

    private static String stworzGreTeskt() {
        return ("Tworzenie Gry &" +
                "Wpisz komende w formacie: \"start <TypGry> <parametr1> <parametr2> ..\" &" +
                "Gdzie typ gry to STANDARDOWA - 1 ZESPOLOWA - 2 ROZSZERZONA - 3 &" +
                "Pierwszy patametr to liczba graczy");

    }

    private static String listaGierTekst() {
        String listaGier = ManagerGier.dajInstancje().wypiszGry();
        //"tu będzie lista gier &&";
        return listaGier + menuGlowneTekst();
    }

    private static String oczekiwanieNaGreTekst(Gracz gracz) {
        return ("Oczekiwanie na reszte graczy &" +
                "Jest ich " + gracz.dajGre().dajListeGraczy().size() + "/" + gracz.dajGre().dajZasadyGry().ileGraczy());
    }

    private static String drukujPlansze(Gra gra) {
        return "Tu będzie wydrukowana plansza";
    }

    private static String graczWGrze(Gracz gracz) {
        String ostatnioWykonanyRuch = gracz.dajGre().dajRuchZPoprzedniejTury();
        String wygladPlanszy = drukujPlansze(gracz.dajGre()) + "&";
        String wiadomoscOKolejce;
        if (gracz.ktoreMiejsce() == gracz.dajGre().dajObecnegoGracza()) {
            wiadomoscOKolejce = "Teraz twoja tura, &Wykonaj ruch według podanych zasad &" +
                    "Wiersz: A-M &" +
                    "Kolumna: 1-13 &" +
                    "Koordynaty: np. A4 F12&" +
                    "Komenda: \"ruch <koordynaty początkowe> <koordynaty końcowe>\"";
        } else {
            wiadomoscOKolejce = "Teraz trwa tura gracza " + gracz.dajGre().dajObecnegoGracza();
        }
        return ostatnioWykonanyRuch + wygladPlanszy + wiadomoscOKolejce;
    }

}

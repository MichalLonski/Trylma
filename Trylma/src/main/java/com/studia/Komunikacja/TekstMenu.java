package com.studia.Komunikacja;
/*
dekoduje wiadomość serwera, po to tak zrobione żeby obejść jakoś
to że serwer wysyła tylko jedną linijkę do klienta i czeka
można potem te kody jakoś czytelniej napisać bo a,b itd nie znaczą za dużo ale na razeie imo jest okej
 */
public class TekstMenu {

    public static void drukujTekst(String string){
        String[] podzielnyString = string.split(" ");
        switch (podzielnyString[0]){
            case "a":
                menuGlowneTekst(podzielnyString);
                break;
            case "b":
                stworzGreTeskt(podzielnyString);
                break;
            default:
                System.out.println(string);
                break;
        }
    }

    private static void menuGlowneTekst(String[] params){

        switch (params[1]){
            case "1":
                System.out.println("Nie udało się dołączyć do gry \n");
                break;
        }

        System.out.println("""
                             Wybierz Opcje:\\n
                             By stworzyć nową gre wpisz: "1"\\n
                             By dołączyć do istniejącej gry wpisz "2 <id gry do dołączenia>"\\n
                             By pokazać istniejące gry "3>"\\n """);

    }

    private static void stworzGreTeskt(String[] params){
        System.out.println("""
                                Tworzenie Gry\\n
                                Wpisz komende w formacie: "start <TypGry> <parametr1> <parametr2> ..\\n
                                Gdzie typt gry to STANDARDOWA - 1 ZESPOLOWA - 2 ROZSZERZONA - 3\\n
                                Pierwszy patametr to liczba graczy\\n""");

    }


}

package com.studia;

import java.util.UUID;

public class Gracz {
    private String identyfikatorGracza;
    private int miejscePrzyStole;
    private Gra gra;
    public Gracz() {
        this.identyfikatorGracza = UUID.randomUUID().toString();
    }

    public void zajmijMiejsce(Gra gra, int miejsce) {
        //To usunąłem stad i do osobnej funkcji włożyłem
        //this.gra = gra;
        miejscePrzyStole = miejsce;
    }

    public void wykonajRuch(String pozycjaPoczatkowa, String pozycjaKoncowa) {
        gra.wykonajRuch(miejscePrzyStole, pozycjaPoczatkowa, pozycjaKoncowa);
    }

    public void opuscGre(){
        gra.usunGracza(this);
    }

    public int ktoreMiejsce(){
        return miejscePrzyStole;
    }

    public String przedstawSie(){
        return identyfikatorGracza;
    }

    public void informacjaOdGry(String wiadomosc){
        System.out.println("Gracz: " + identyfikatorGracza + " otrzymał wiadomość: " + wiadomosc);
    }

    //Dodane aby był dostęp do gry w której bierze udział gracz
    public Gra dajGre(){
        return gra;
    }

    //Ustawia zmienną gre gracza, pozwoliłem se to zmienić
    //bo potrzebuje tego do struktury przypisywania do nowej
    //Wywoływane w Gra.dodajGracza
    public void przypiszGre(Gra gra){
        this.gra = gra;
    }

}

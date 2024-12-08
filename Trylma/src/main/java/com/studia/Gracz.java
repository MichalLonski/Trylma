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
        this.gra = gra;
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
}

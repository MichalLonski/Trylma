package com.studia;

import com.studia.Plansza.Pole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class Bot {
    private Gracz gracz;
    private int miejsceGracza; // Miejsce gracza w kolejce
    private int turaGracza; // Numer aktualnej tur
    private Pole[] polaDocelowe = new Pole[10];
    private boolean[] czyDocelowePoleSpełnione = new boolean[10];
    public Bot(){
        gracz = new Gracz();
    }

    private class poleXdouble {
        public Pole i;
        public double d;
        public poleXdouble(double D, Pole I){
            d = D;
            i = I;
        }
    }

    public void startBot(){
        System.out.println("Bot Żyjeee!");
        dziala.setDaemon(true);
        dziala.start();
        getAndSortPolaDocelowe();


    }

    Thread dziala = new Thread(() -> {
        try{
            while (gracz.dajGre().czyGraSieZaczela()){
                sleep(10);
                if(gracz.dajGre().trwaTuraGracza(gracz.ktoreMiejsce())){
                    wykonajRuch();
                }
            }
        }catch (Exception e){

        }

    });

    private void getAndSortPolaDocelowe(){
        ArrayList<poleXdouble> tempPole = new ArrayList<>();
        for (int x = 0;x < gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry().length ; x++){
            for (int y = 0;y < gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry()[1].length;y++){
                if(gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry()[x][y].getStrefa() == gracz.ktoreMiejsce()){
                    tempPole.add(new poleXdouble(Math.sqrt(Math.pow(x - 9, 2) + Math.pow(y - 13, 2)),
                            gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry()[x][y]));
                }
            }
        }

        tempPole.sort(Comparator.comparingDouble(p -> p.d));
        for(int i = 0;i < tempPole.size();i++){
            polaDocelowe[i] = tempPole.get(i).i;
        }
    }

    private void wykonajRuch(){
        //temp
        gracz.dajGre().passTury(gracz.ktoreMiejsce());
        System.out.println("Bot Pasuje");
    }

    public Gracz getGracz() {
        return gracz;
    }
}

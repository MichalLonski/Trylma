package com.studia.Komunikacja;

import com.studia.Gra;
import com.studia.Gracz;
import com.studia.Zasady.TypGry;

import java.util.ArrayList;
import java.util.List;

/*
Klasa do zarządzania grami, aby na serwerze mogła się odbywać wiecej niż jedna gra
plus jest singletonem wiec kolejny ten wzorzec z poprzeniej listy i guess
 */
public class ManagerGier {

    List<Gra> ListaGier = new ArrayList<>();
    //To raczej potrzebne nie będzie
    //List<Gracz> ListaGracz = new ArrayList<>();

    private ManagerGier(){}

    //Ogarnięcie Singletona
    private static class SingletonHelper {
        volatile static ManagerGier INSTANCE = new ManagerGier();
    }

    public static ManagerGier dajInstancje(){
        return SingletonHelper.INSTANCE;
    }

    //Inicjuje nową gre i dodaje do niego gracza który ją stworzył
    public synchronized void inicjujNowaGre(TypGry typGry, int[] parametry, Gracz inicjujacyGracz){
        Gra nowaGra = new Gra(typGry,parametry);
        ListaGier.add(nowaGra);
        nowaGra.dodajGracza(inicjujacyGracz);
    }

    //Dodaje gracza do istniejącej gry, zwraca 1 dla sukcesu i -1 dla porażki
    public synchronized int dolaczDoGry(Gracz gracz,String id){

        int iD = Integer.parseInt(id);

        if(znajdzGrePoID(iD) == null){
            return -1;
        }else {
            znajdzGrePoID(iD).dodajGracza(gracz);
            return 1;
        }
    }

    //Zwraca gre po ID
    private Gra znajdzGrePoID(int id){
        for(Gra g : ListaGier){
            if(g.dajID() == id){return g;}
        }
        return null;
    }
}

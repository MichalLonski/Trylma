package com.studia.Plansza;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.studia.Gra;
import com.studia.Gracz;
import com.studia.Zasady.TypGry;

public class TestPlanszy {
    
    @Test
    @Disabled
    public void zobaczJakWygladaPlansza(){
        Gracz g1 = new Gracz();
        Gracz g2 = new Gracz();
        Gracz g3 = new Gracz();
        Gracz g4 = new Gracz();
        Gracz g5 = new Gracz();
        Gracz g6 = new Gracz();
        int[] param = new int[]{6};
        Gra gra = new Gra(TypGry.STANDARDOWA, param);
        gra.dodajGracza(g1);
        gra.dodajGracza(g2);
        gra.dodajGracza(g3);
        gra.dodajGracza(g4);
        gra.dodajGracza(g5);
        gra.dodajGracza(g6);
        gra.ZacznijGre();
        gra.printPlansza();
    }
}

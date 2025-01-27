package com.studia.Plansza;

import com.studia.Zasady.FabrykaZasad;
import com.studia.Zasady.TypGry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestPola13i1 {
    private Plansza plansza;

    @BeforeEach
    void setUp() {
        plansza = new Plansza();
        plansza.utworzPlansze(FabrykaZasad.stworzZasadyGry(TypGry.CAPTURE, 2).infoJSON());
    }

    @Test
    void testCzyDobrePolaDoRuchu(){

        assertEquals(true, plansza.dajPlanszaDoGry()[1][13].zajete() ^ plansza.dajPlanszaDoGry()[13][1].zajete(), "Jak kolwiek zepsute koordynaty planszy nie są, pola 13,1 i 1,13 powinny mieć różne stany \"zajętości\"");

    }
}

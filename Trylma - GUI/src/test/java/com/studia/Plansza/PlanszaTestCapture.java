package com.studia.Plansza;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.studia.Zasady.FabrykaZasad;
import com.studia.Zasady.TypGry;

public class PlanszaTestCapture {
    private Plansza plansza;

    @BeforeEach
    void setUp() {
        plansza = new Plansza();
        plansza.utworzPlansze(FabrykaZasad.stworzZasadyGry(TypGry.CAPTURE, 2).infoJSON());
    }

    @Test
    void testUtworzPlansze() {

        assertNotNull(plansza, "Plansza nie powinna być null");

        for (int wiersz = 0; wiersz < Plansza.LICZBA_WIERSZY; wiersz++) {
            for (int kolumna = 0; kolumna < Plansza.LICZBA_KOLUMN; kolumna++) {

                assertNotNull(plansza.sprawdzPole(wiersz, kolumna),
                        "Pole na pozycji (" + wiersz + ", " + kolumna + ") nie powinno być null");
            }
        }
    }

    @Test
    void testCzyDobrePolaDoRuchu(){
        assertEquals(1, plansza.sprawdzPole(9, 5).getGracz(), "Na polu (9, 5) powinien stać gracz 1");

        assertEquals(0, plansza.sprawdzPole(12, 8).getGracz(), "Pole (12, 8) powinno być puste");

    }

    @Test
    void testWykonajRuch() {


        plansza.wykonajRuch(new int[] { 18, 4 }, new int[] { 16, 4 }, 1);

        assertEquals(0, plansza.sprawdzPole(10, 6).getGracz(), "Pole (10, 6) powinno być puste");

        assertEquals(1, plansza.sprawdzPole(12, 8).getGracz(), "Na polu (12, 8) powinien stać gracz 1");
    }
}

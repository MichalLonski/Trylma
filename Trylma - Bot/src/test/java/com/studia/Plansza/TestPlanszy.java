package com.studia.Plansza;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.studia.Zasady.FabrykaZasad;
import com.studia.Zasady.TypGry;

class PlanszaTest {

    private Plansza plansza;

    @BeforeEach
    void setUp() {
        plansza = new Plansza();
        plansza.utworzPlansze(FabrykaZasad.stworzZasadyGry(TypGry.STANDARDOWA, 2).infoJSON());
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
    void testWykonajRuch() {

        assertEquals(1, plansza.sprawdzPole(18, 4).getGracz(), "Na polu (18, 4) powinien stać gracz 1");

        assertEquals(0, plansza.sprawdzPole(16, 4).getGracz(), "Pole (16, 4) powinno być puste");

        plansza.wykonajRuch(new int[] { 18, 4 }, new int[] { 16, 4 }, 1);

        assertEquals(0, plansza.sprawdzPole(18, 4).getGracz(), "Pole (18, 4) powinno być puste");

        assertEquals(1, plansza.sprawdzPole(16, 4).getGracz(), "Na polu (16, 4) powinien stać gracz 1");
    }
}

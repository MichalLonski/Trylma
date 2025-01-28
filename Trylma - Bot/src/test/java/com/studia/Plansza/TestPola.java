package com.studia.Plansza;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PoleTest {

    private Pole pole;

    @BeforeEach
    void setUp() {
        pole = new Pole(1);
    }

    @Test
    void testZajete() {
        assertTrue(pole.zajete(), "Pole powinno być zajęte przez gracza 1");

        pole.setGracz(0);
        assertFalse(pole.zajete(), "Pole nie powinno być zajęte po ustawieniu gracza na 0");

        pole.setGracz(7);
        assertTrue(pole.zajete(), "Pole powinno być zajęte po ustawieniu gracza na 7");

    }

    @Test
    void testSetGracz() {
        pole.setGracz(4);
        assertEquals(4, pole.getGracz(), "Gracz nie został prawidłowo ustawiony");
    }

    @Test
    void testSetDomek() {
        pole.setStrefa(2);
        assertEquals(2, pole.getStrefa(), "Domek nie został prawidłowo ustawiony");
    }

    @Test
    void testGetDomek() {
        assertEquals(0, pole.getStrefa(), "Domki powinny być początkowo ustawione na 0");
    }
}

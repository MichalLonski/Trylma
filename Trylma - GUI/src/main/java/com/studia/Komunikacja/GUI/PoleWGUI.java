package com.studia.Komunikacja.GUI;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Klasa reprezentująca pole w interfejsie graficznym (GUI) gry.
 * Każde pole posiada współrzędne, prostokąt, okrąg, obwódkę okręgu oraz typ,
 * które są wykorzystywane do rysowania elementów na planszy gry.
 */
public class PoleWGUI {

    /** Współrzędne pola w postaci tablicy dwóch liczb (x, y) */
    private int[] koordynaty;

    /** Prostokąt reprezentujący tło pola */
    private Rectangle rect;

    /** Okrąg reprezentujący główny element pola */
    private Circle circ;

    /** Obwódka okręgu, mogąca służyć do zaznaczenia pola */
    private Circle obwodkaCirc;

    /** Typ pola, wykorzystywany do rozróżnienia różnych rodzajów pól */
    private int typ;

    /**
     * Konstruktor klasy, inicjalizujący pole GUI.
     *
     * @param koor      współrzędne pola w postaci tablicy (x, y)
     * @param rectangle prostokąt, który będzie reprezentował tło pola
     * @param circle    okrąg, który będzie reprezentował główny element pola
     * @param obw       okrąg, który będzie stanowił obwódkę okręgu
     * @param Typ       typ pola, który może reprezentować np. różne stany lub typy
     */
    public PoleWGUI(int[] koor, Rectangle rectangle, Circle circle, Circle obw, int Typ) {
        koordynaty = koor;
        rect = rectangle;
        circ = circle;
        obwodkaCirc = obw;
        typ = Typ;
    }

    /**
     * Zwraca współrzędne pola.
     *
     * @return współrzędne pola w postaci tablicy {x, y}
     */
    public int[] getKoordynaty() {
        return koordynaty;
    }

    /**
     * Zwraca okrąg reprezentujący główny element pola.
     *
     * @return okrąg (Circle)
     */
    public Circle getCirc() {
        return circ;
    }

    /**
     * Zwraca typ pola.
     *
     * @return typ pola
     */
    public int getTyp() {
        return typ;
    }

    /**
     * Zwraca prostokąt reprezentujący tło pola.
     *
     * @return prostokąt (Rectangle)
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Zwraca obwódkę okręgu.
     *
     * @return obwódka okręgu (Circle)
     */
    public Circle getObwodkaCirc() {
        return obwodkaCirc;
    }
}

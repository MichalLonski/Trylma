package com.studia.Komunikacja.GUI;

import javafx.scene.shape.Circle;

import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class PoleWGUI {
    private int[] koordynaty;
    private Rectangle rect;
    private Circle circ;
    private Circle obwodkaCirc;
    private int typ;

    public PoleWGUI(int[] koor, Rectangle rectangle, Circle circle, Circle obw, int Typ) {
        koordynaty = koor;
        rect = rectangle;
        circ = circle;
        obwodkaCirc = obw;
        typ = Typ;
    }

    public int[] getKoordynaty() {
        return koordynaty;
    }

    public Circle getCirc() {
        return circ;
    }

    public int getTyp() {
        return typ;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Circle getObwodkaCirc() {
        return obwodkaCirc;
    }
}

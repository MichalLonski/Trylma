package com.studia.Komunikacja.GUI;

import javafx.scene.shape.Circle;

import javafx.scene.shape.Rectangle;

public class PoleWGUI {
    String koordynaty;
    Rectangle rect;
    Circle circ;
    int typ;

    public PoleWGUI(String koor, Rectangle rectangle, Circle circle, int Typ) {
        koordynaty = koor;
        rect = rectangle;
        circ = circle;
        typ = Typ;
    }

    public String getKoordynaty() {
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
}

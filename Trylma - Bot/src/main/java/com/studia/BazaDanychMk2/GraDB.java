package com.studia.BazaDanychMk2;

public class GraDB {
    private int id;
    private String typ;
    private int iloscGraczy;

    // Gettery i Settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public int getIloscGraczy() {
        return iloscGraczy;
    }

    public void setIloscGraczy(int iloscGraczy) {
        this.iloscGraczy = iloscGraczy;
    }
}
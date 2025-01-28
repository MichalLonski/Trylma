package com.studia.BazaDanych;

import javax.persistence.*;

@Entity
public class RuchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "gra_id")
    private GraEntity gra;

    public GraEntity getGra() {
        return gra;
    }

    public void setGra(GraEntity gra) {
        this.gra = gra;
    }

    private int[][] sekwencjaRuchow;

    public int[][] getSekwencjaRuchow() {
        return sekwencjaRuchow;
    }

    public void setSekwencjaRuchow(int[][] sekwencjaRuchow) {
        this.sekwencjaRuchow = sekwencjaRuchow;
    }

    private int gracz;

    public int getGracz() {
        return gracz;
    }

    public void setGracz(int gracz) {
        this.gracz = gracz;
    }

    // Gettery i settery
}
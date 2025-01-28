package com.studia.BazaDanych;

import java.util.List;

import javax.persistence.*;

@Entity
public class GraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int idGry;
    private boolean graWTrakcie;
    private String statusGry;

    public int getIdGry() {
        return idGry;
    }

    public void setIdGry(int idGry) {
        this.idGry = idGry;
    }

    public boolean isGraWTrakcie() {
        return graWTrakcie;
    }

    public void setGraWTrakcie(boolean graWTrakcie) {
        this.graWTrakcie = graWTrakcie;
    }

    public String getStatusGry() {
        return statusGry;
    }

    public void setStatusGry(String statusGry) {
        this.statusGry = statusGry;
    }

    @OneToMany(mappedBy = "gra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuchEntity> ruchy;

}
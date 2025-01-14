package com.studia.Zasady;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import com.studia.Plansza.Plansza;

public abstract class ZasadyGry {
    protected int liczbaGraczy;
    protected int[] warunkiZwyciestwa;

    public ZasadyGry(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
        warunkiZwyciestwa = new int[liczbaGraczy + 1];
        for (int i = 0; i < warunkiZwyciestwa.length; i++) {
            warunkiZwyciestwa[i] = 0;
        }
    }

    public int ileGraczy() {
        return liczbaGraczy;
    }

    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        return true;
    };

    public String opisZasad() {
        return "";
    };

    public boolean graSkonczona(int gracz) {
        int graczeKoniec = 0;
        for (int i = 1; i <= liczbaGraczy; i++) {
            if (warunkiZwyciestwa[i] == 10){
                graczeKoniec++;
            }
        }
        return (graczeKoniec == liczbaGraczy-1);
    }

    public int zwyciezca(){
        return 1;
    }

    protected boolean skokJestLegalny(Plansza plansza, int wierszP, int kolumnaP, int wierszK, int kolumnaK) {
        return true;
    }

    public void wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        plansza.wykonajRuch(sekwencjaRuchow[0], sekwencjaRuchow[sekwencjaRuchow.length - 1], gracz);
    }

    public JSONArray infoJSON() {
        JSONArray mapa = new JSONArray();
        try {

            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/config.json")));
            JSONObject jsonObject = new JSONObject(content);
            JSONObject standard = jsonObject.getJSONObject("standard");
            mapa = standard.getJSONArray(String.valueOf(liczbaGraczy));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapa;
    }
}

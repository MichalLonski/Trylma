package com.studia.Zasady;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import com.studia.Plansza.Plansza;

/**
 * Abstrakcyjna klasa reprezentująca zasady gry.
 * Klasa ta zarządza liczbą graczy, warunkami zwycięstwa oraz sprawdza
 * poprawność ruchów w grze.
 */
public abstract class ZasadyGry {

    /** Liczba graczy */
    protected int liczbaGraczy;
    /** Tablica przechowująca informacje o stanie zwycięstwa*/
    protected int[] warunkiZwyciestwa;

    /**
     * Konstruktor klasy ZasadyGry.
     * Ustawia liczbę graczy i inicjalizuje tablicę warunków zwycięstwa.
     * 
     * @param liczbaGraczy Liczba graczy w grze.
     */
    public ZasadyGry(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
        warunkiZwyciestwa = new int[liczbaGraczy + 1];
        for (int i = 0; i < warunkiZwyciestwa.length; i++) {
            warunkiZwyciestwa[i] = 0;
        }
    }

    /**
     * Sprawdza, czy gra została zakończona.
     * Gra kończy się, gdy wszyscy gracze oprócz jednego osiągną warunki zwycięstwa.
     * 
     * @param plansza Plansza, na której rozgrywa się gra.
     * @return True, jeśli gra jest zakończona, w przeciwnym razie false.
     */
    public boolean graSkonczona(Plansza plansza) {
        int graczeKoniec = 0;
        for (int i = 1; i <= liczbaGraczy; i++) {
            if (warunkiZwyciestwa[i] == 10) {
                graczeKoniec++;
            }
        }
        return (graczeKoniec == liczbaGraczy - 1);
    }

    /**
     * Zwraca liczbę graczy w grze.
     * 
     * @return Liczba graczy.
     */
    public int ileGraczy() {
        return liczbaGraczy;
    }

    /**
     * Zwraca informacje o zasadach gry w formacie JSON.
     * 
     * @return JSONArray zawierający informacje o zasadach dla określonej liczby
     *         graczy.
     */
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

    /**
     * Zwraca pusty opis zasad gry.
     * 
     * @return Opis zasad gry (w tym przypadku pusty ciąg znaków).
     */
    public String opisZasad() {
        return "";
    }

    /**
     * Sprawdza, czy ruch jest poprawny.
     * 
     * @param plansza         Plansza, na której rozgrywa się gra.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli ruch jest poprawny, w przeciwnym razie false.
     */
    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        return true;
    }

    /**
     * Sprawdza, czy skok jest legalny.
     * 
     * @param plansza  Plansza, na której rozgrywa się gra.
     * @param wierszP  Wiersz początkowy skoku.
     * @param kolumnaP Kolumna początkowa skoku.
     * @param wierszK  Wiersz końcowy skoku.
     * @param kolumnaK Kolumna końcowa skoku.
     * @return True, jeśli skok jest legalny, w przeciwnym razie false.
     */
    protected boolean skokJestLegalny(Plansza plansza, int wierszP, int kolumnaP, int wierszK, int kolumnaK) {
        return true;
    }

    /**
     * Wykonuje ruch na planszy.
     * 
     * @param plansza         Plansza, na której rozgrywa się gra.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli ruch został wykonany poprawnie, w przeciwnym razie false.
     */
    public boolean wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        return false;
    }

    /**
     * Zwraca zwycięzcę gry na podstawie warunków zwycięstwa.
     * 
     * @param gracz Indeks gracza, którego warunki zwycięstwa są sprawdzane.
     * @return Indeks gracza, jeśli jest zwycięzcą, w przeciwnym razie 0.
     */
    public int zwyciezca(int gracz) {
        return (warunkiZwyciestwa[gracz] == 10 ? gracz : 0);
    }

    /**
     * Zwraca zwycięzcę tablice pionków do zbicia.
     *
     * @return Tablica pionków do zbicia.
     */
    public int[][] getPionkiDoZbicia(){
        return null;
    }
}

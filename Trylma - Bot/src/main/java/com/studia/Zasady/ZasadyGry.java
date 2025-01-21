package com.studia.Zasady;

import java.util.List;

import org.json.JSONArray;
import com.studia.Plansza.Plansza;

/**
 * Abstrakcyjna klasa reprezentująca zasady gry.
 * Klasa ta zarządza liczbą graczy, warunkami zwycięstwa oraz sprawdza
 * poprawność ruchów w grze.
 */
public abstract class ZasadyGry {

    /** Liczba graczy */
    protected int liczbaGraczy;
    /** Tablica przechowująca informacje o stanie zwycięstwa */
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
    abstract public boolean graSkonczona(Plansza plansza);

    /**
     * Zwraca liczbę graczy w grze.
     * 
     * @return Liczba graczy.
     */
    public final int ileGraczy() {
        return liczbaGraczy;
    }

    /**
     * Zwraca informacje o zasadach gry w formacie JSON.
     * 
     * @return JSONArray zawierający informacje o zasadach dla określonej liczby
     *         graczy.
     */
    abstract public JSONArray infoJSON();

    /**
     * Zwraca pusty opis zasad gry.
     * 
     * @return Opis zasad gry (w tym przypadku pusty ciąg znaków).
     */
    abstract public String opisZasad();

    /**
     * Sprawdza, czy ruch jest poprawny.
     * 
     * @param plansza         Plansza, na której rozgrywa się gra.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli ruch jest poprawny, w przeciwnym razie false.
     */
    abstract public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz);

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
    abstract protected boolean skokJestLegalny(Plansza plansza, int wierszP, int kolumnaP, int wierszK, int kolumnaK);

    /**
     * Wykonuje ruch na planszy.
     * 
     * @param plansza         Plansza, na której rozgrywa się gra.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli ruch został wykonany poprawnie, w przeciwnym razie false.
     */
    public boolean wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        plansza.wykonajRuch(sekwencjaRuchow[0], sekwencjaRuchow[sekwencjaRuchow.length - 1], gracz);
        return false;
    }

    /**
     * Zwraca zwycięzcę gry na podstawie warunków zwycięstwa.
     * 
     * @param gracz Indeks gracza, którego warunki zwycięstwa są sprawdzane.
     * @return Indeks gracza, jeśli jest zwycięzcą, w przeciwnym razie 0.
     */
    abstract public int zwyciezca(int gracz);

    /**
     * Zwraca zwycięzcę tablice pionków do zbicia.
     *
     * @return Tablica pionków do zbicia.
     */
    public int[][] getPionkiDoZbicia() {
        return null;
    }

    /**
     * 
     * @param gracz   Indeks gracza, dla którego sprawdzane są dostępne posunięcia.
     * @param plansza Plansza, którą przeszukujemy.
     * @return Tablica dostępnych ruchów.
     */
    abstract public List<int[][]> possibleMoves(int gracz, Plansza plansza);

    /**
     * Możliwe kierunki, w jakich gracz może się poruszać.
     * 
     * @return Tablica z kierunkami ruchów.
     */
    protected final int[][] legalneKierunki() {
        return new int[][] {
                { -2, 0 }, { 2, 0 }, { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 }
        };
    }
}

package com.studia.Plansza;

/**
 * Klasa reprezentująca jedno pole na planszy.
 * Każde pole może zawierać pionek przypisany do gracza, oraz informacje o domku
 * gracza.
 */
public class Pole {
    private int nrGracza;
    private int strefaGracza;

    /**
     * Konstruktor tworzący pole dla danego gracza.
     * 
     * @param nrGracza Numer gracza przypisanego do pola.
     */
    Pole(int nrGracza) {
        this.nrGracza = nrGracza;
        this.strefaGracza = 0;
    }

    /**
     * Konstruktor kopiujący.
     * Tworzy nowe pole na podstawie istniejącego.
     * 
     * @param pole Istniejące pole, z którego kopiowane są dane.
     */
    Pole(Pole pole) {
        this.nrGracza = pole.nrGracza;
        this.strefaGracza = pole.strefaGracza;
    }

    /**
     * Zwraca numer gracza przypisanego do pola.
     * 
     * @return Numer gracza, który posiada pole. 0 oznacza brak gracza.
     */
    public int getGracz() {
        return nrGracza;
    }

    /**
     * Sprawdza, czy pole jest zajęte przez gracza.
     * 
     * @return True, jeśli pole jest zajęte przez gracza, w
     *         przeciwnym razie false.
     */
    public boolean zajete() {
        return nrGracza > 0 && nrGracza <= 6;
    }

    /**
     * Ustawia gracza przypisanego do pola.
     * 
     * @param gracz Numer gracza, który ma być przypisany do pola. Wartość 0 oznacza
     *              brak gracza, wartość >6 oznacza, że pole nie jest polem
     *              faktycznym.
     */
    public void setGracz(int gracz) {
        nrGracza = gracz;
    }

    /**
     * Ustawia strefę dla gracza na tym polu.
     * 
     * @param gracz Numer gracza przypisanego do domku na tym polu.
     */
    public void setStrefa(int gracz) {
        strefaGracza = gracz;
    }

    /**
     * Zwraca numer strefy przypisanego do pola.
     * 
     * @return Numer strefy przypisanej do pola. Wartość 0 oznacza brak strefy.
     */
    public int getStrefa() {
        return strefaGracza;
    }
}

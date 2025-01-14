package com.studia.Komunikacja.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Klasa bazowa dla kontrolerów GUI, która zapewnia mechanizm komunikacji z
 * serwerem.
 * Umożliwia wysyłanie komend do serwera oraz odbieranie odpowiedzi.
 * Ponadto zawiera funkcje pomocnicze do zamykania połączenia z serwerem.
 */
public abstract class GUIController {

    /** Strumień wejściowy do komunikacji z serwerem */
    public BufferedReader in;

    /** Strumień wyjściowy do komunikacji z serwerem */
    public PrintWriter out;

    /**
     * Ustawia strumienie komunikacyjne (wejściowy i wyjściowy).
     * 
     * @param IN  strumień wejściowy
     * @param OUT strumień wyjściowy
     */
    public void setInOut(BufferedReader IN, PrintWriter OUT) {
        in = IN;
        out = OUT;
    }

    /**
     * Wysyła komendę do serwera i oczekuje odpowiedzi.
     * Używa mechanizmu synchronizacji, aby uniknąć problemów w przypadku
     * równoczesnego dostępu.
     * 
     * @param komenda komenda do wysłania do serwera
     * @return odpowiedź z serwera
     */
    public synchronized String sendCommand(String komenda) {
        try {
            String odp;
            synchronized (this) {
                out.println(komenda);
                odp = in.readLine();
            }
            return odp;
        } catch (IOException e) {
            System.out.println("Błąd w komunikacji z serwerem\n");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sprawdza, czy podany ciąg znaków reprezentuje liczbę całkowitą.
     * 
     * @param s ciąg znaków do sprawdzenia
     * @return true jeśli ciąg jest liczbą całkowitą, false w przeciwnym razie
     */
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Zamyka połączenie z serwerem przez zamknięcie strumieni wejściowego i
     * wyjściowego.
     * 
     * @throws IOException w przypadku problemów z zamknięciem strumieni
     */
    public void quit() throws IOException {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

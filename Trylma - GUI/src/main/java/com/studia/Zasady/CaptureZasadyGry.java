package com.studia.Zasady;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import com.studia.Plansza.Plansza;
import com.studia.Plansza.Pole;

/**
 * Klasa reprezentująca zasady gry w wersji "Capture".
 * Zasady te opierają się na zbijaniu pionów.
 */
public class CaptureZasadyGry extends ZasadyGry {

    /**
     * Konstruktor klasy CaptureZasadyGry.
     * Inicjalizuje liczbę graczy biorących udział w grze.
     * 
     * @param liczbaGraczy Liczba graczy biorących udział w grze.
     */
    public CaptureZasadyGry(int liczbaGraczy) {
        super(liczbaGraczy);
    }

    /**
     * Sprawdza, czy gra została zakończona na podstawie dostępnych ruchów.
     * 
     * @param plansza Plansza, na której wykonywane są ruchy.
     * @return True, jeśli gra jest zakończona (brak dostępnych ruchów), false w
     *         przeciwnym razie.
     */
    @Override
    public boolean graSkonczona(Plansza plansza) {
        return !istniejaRuchy(plansza);
    }

    /**
     * Zwraca dane w formacie JSON związane z grą "Capture".
     * 
     * @return JSONArray zawierający dane konfiguracyjne dla trybu gry.
     */
    public JSONArray infoJSON() {
        JSONArray mapa = new JSONArray();
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/config.json")));
            JSONObject jsonObject = new JSONObject(content);
            mapa = jsonObject.getJSONArray("capture");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapa;
    }

    /**
     * Sprawdza, czy istnieją możliwe ruchy na planszy.
     * 
     * @param plansza Plansza, na której sprawdzane są dostępne ruchy.
     * @return True, jeśli istnieją możliwe ruchy, false w przeciwnym razie.
     */
    private boolean istniejaRuchy(Plansza plansza) {
        for (int wiersz = 0; wiersz < Plansza.LICZBA_WIERSZY; wiersz++) {
            for (int kolumna = 0; kolumna < Plansza.LICZBA_KOLUMN; kolumna++) {
                for (int[] ruch : legalneKierunki()) {
                    int nowaKolumna = kolumna + ruch[0];
                    int nowyWiersz = wiersz + ruch[1];
                    if (0 <= nowaKolumna && nowaKolumna < Plansza.LICZBA_KOLUMN && 0 <= nowyWiersz
                            && nowyWiersz < Plansza.LICZBA_WIERSZY) {
                        System.out.println("wiersz: " + wiersz + " kolumna: " + kolumna);
                        System.out.println("nowy wiersz: " + nowyWiersz + " nowa kolumna: " + nowaKolumna);
                        if (plansza.sprawdzPole(nowyWiersz, nowaKolumna).zajete()) {
                            if (plansza.sprawdzPole((kolumna + nowaKolumna) / 2, (wiersz + nowyWiersz) / 2).zajete()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Możliwe kierunki, w jakich gracz może się poruszać.
     * 
     * @return Tablica z kierunkami ruchów.
     */
    private int[][] legalneKierunki() {
        return new int[][] {
                { -2, 0 }, { 2, 0 }, { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 }
        };
    }

    /**
     * Zwraca opis zasad gry w wersji "Capture".
     * 
     * @return String z opisem zasad gry.
     */
    @Override
    public String opisZasad() {
        return "1. Trzeba zacząć od pola ze swoim pionem &" +
                "2. Trzeba skończyć na polu pustym &" +
                "3. Można ruszyć się na sąsiednie pole &" +
                "4. Można skoczyć na pole w odległości 2, jeśli pomiędzy polami jest pion &" +
                "5. Nie można opuszczać strefy zwycięskiej, chyba że wrócimy do niej w tym samym ruchu &" +
                "6. Nie można wejść do cudzej strefy zwycięskiej, chyba że wyjdziemy z niej w tym samym ruchu";
    }

    /**
     * Sprawdza, czy dany ruch jest poprawny zgodnie z zasadami gry.
     * 
     * @param plansza         Plansza, na której wykonuje się ruch.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli ruch jest poprawny, false w przeciwnym razie.
     */
    @Override
    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {

        Pole obecne = plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]);
        if (!obecne.zajete()) {
            return false;
        }

        Plansza kopiaPlanszy = new Plansza(plansza);
        boolean rezultat = true;
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int kolumnaP = sekwencjaRuchow[i][0];
            int wierszP = sekwencjaRuchow[i][1];
            int kolumnaK = sekwencjaRuchow[i + 1][0];
            int wierszK = sekwencjaRuchow[i + 1][1];

            if (kopiaPlanszy.sprawdzPole(kolumnaK, wierszK).zajete()) {
                rezultat = false;
            } else if ((Math.abs(kolumnaK - kolumnaP) == 4 && Math.abs(wierszK - wierszP) == 0)
                    || (Math.abs(kolumnaK - kolumnaP) == 2 && Math.abs(wierszK - wierszP) == 2)) {
                rezultat = skokJestLegalny(kopiaPlanszy, kolumnaP, wierszP, kolumnaK, wierszK);
            } else {
                rezultat = false;
            }

            if (!rezultat) {
                break;
            }
            int temp = warunkiZwyciestwa[gracz];
            wykonajRuch(kopiaPlanszy, new int[][] { sekwencjaRuchow[i], sekwencjaRuchow[i + 1] }, gracz);
            warunkiZwyciestwa[gracz] = temp;
        }

        return rezultat;
    }

    /**
     * Zwraca nazwę trybu gry.
     * 
     * @return Nazwa trybu gry "Capture".
     */
    @Override
    public String toString() {
        return "Capture";
    }

    /**
     * Wykonuje ruch na planszy i aktualizuje stan gry.
     * Zbija pion przeciwnika i zwiększa liczbę punktów gracza.
     * 
     * @param plansza         Plansza, na której wykonywany jest ruch.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli gra powinna być kontynuowana (istnieją dalsze ruchy),
     *         false w przeciwnym razie.
     */
    @Override
    public boolean wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {

        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int kolumnaP = sekwencjaRuchow[i][0];
            int wierszP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];

            plansza.sprawdzPole((kolumnaK + kolumnaP) / 2, (wierszK + wierszP) / 2).setGracz(0);
            warunkiZwyciestwa[gracz]++;
        }
        plansza.wykonajRuch(sekwencjaRuchow[0], sekwencjaRuchow[sekwencjaRuchow.length - 1], gracz);
        return istniejaRuchy(plansza);
    }

    /**
     * Zwraca zwycięzcę na podstawie liczby punktów zdobytych przez graczy.
     * 
     * @param gracz Indeks gracza, którego wynik jest brany pod uwagę.
     * @return Indeks zwycięzcy.
     */
    @Override
    public int zwyciezca(int gracz) {
        int maks = 0;
        int idx = 0;
        for (int i = 1; i < liczbaGraczy; i++) {
            if (warunkiZwyciestwa[i] > maks) {
                idx = i;
            }
        }
        return idx;
    }
}

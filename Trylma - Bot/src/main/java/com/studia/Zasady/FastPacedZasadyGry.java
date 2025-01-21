package com.studia.Zasady;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.studia.Plansza.Plansza;
import com.studia.Plansza.Pole;

/**
 * Klasa reprezentująca zasady gry w wersji "Fast Paced".
 * Zasady te różnią się od standardowych głównie w sposobie skakania nad
 * pionami.
 */
public class FastPacedZasadyGry extends ZasadyGry {

    /**
     * Konstruktor klasy FastPacedZasadyGry.
     * Inicjalizuje liczbę graczy oraz warunki zwycięstwa.
     * 
     * @param liczbaGraczy Liczba graczy biorących udział w grze.
     */
    public FastPacedZasadyGry(int liczbaGraczy) {
        super(liczbaGraczy);
    }

    /**
     * Sprawdza, czy gra została zakończona.
     * Gra kończy się, gdy wszyscy gracze oprócz jednego osiągną warunki zwycięstwa.
     * 
     * @param plansza Plansza, na której rozgrywa się gra.
     * @return True, jeśli gra jest zakończona, w przeciwnym razie false.
     */
    @Override
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
     * Zwraca opis zasad gry w wersji "Fast Paced".
     * 
     * @return String z opisem zasad gry.
     */
    @Override
    public String opisZasad() {
        return "1. Trzeba zacząć od pola ze swoim pionem &" +
                "2. Trzeba skończyć na polu pustym &" +
                "3. Można ruszyć się na sąsiednie pole &" +
                "4. Można skoczyć nad pionem, jeśli po drugiej stronie jest tyle samo wolnych pól, co przed nim &" +
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
        if (gracz != obecne.getGracz()) {
            return false;
        }

        Plansza kopiaPlanszy = new Plansza(plansza);
        boolean rezultat = true;
        int kogoDomStart = obecne.getStrefa();
        boolean robiRuch = false;
        boolean robiSkok = false;
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int wierszP = sekwencjaRuchow[i][0];
            int kolumnaP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];

            if (kopiaPlanszy.sprawdzPole(wierszK, kolumnaK).zajete()) {
                rezultat = false;
            } else if ((kolumnaP == kolumnaK && Math.abs(wierszK - wierszP) == 2)
                    || (Math.abs(wierszK - wierszP) == 1 && Math.abs(kolumnaK - kolumnaP) == 1)) {
                rezultat = ((i + 1) == sekwencjaRuchow.length - 1);
                robiRuch = true;
            } else {
                rezultat = skokJestLegalny(kopiaPlanszy, wierszP, kolumnaP, wierszK, kolumnaK);
                robiSkok = true;
            }

            rezultat = rezultat && (robiRuch ^ robiSkok);

            if (!rezultat) {
                break;
            }
            wykonajRuch(kopiaPlanszy, new int[][] { sekwencjaRuchow[i], sekwencjaRuchow[i + 1] }, gracz);
        }
        if (rezultat) {
            Pole ostatnie = plansza.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                    sekwencjaRuchow[sekwencjaRuchow.length - 1][1]);
            int kogoDomKoniec = ostatnie.getStrefa();
            rezultat = (kogoDomKoniec == kogoDomStart || (kogoDomKoniec == 0 && kogoDomStart != gracz)
                    || kogoDomKoniec == gracz);
        }
        return rezultat;
    }

    /**
     * Sprawdza, czy skok wykonany przez gracza jest legalny.
     * 
     * @param plansza  Plansza, na której sprawdzany jest skok.
     * @param wierszP  Wiersz, z którego gracz startuje.
     * @param kolumnaP Kolumna, z której gracz startuje.
     * @param wierszK  Wiersz, na który gracz chce skoczyć.
     * @param kolumnaK Kolumna, na którą gracz chce skoczyć.
     * @return True, jeśli skok jest legalny, false w przeciwnym razie.
     */
    @Override
    protected boolean skokJestLegalny(Plansza plansza, int wierszP, int kolumnaP, int wierszK, int kolumnaK) {
        if (wierszP % 2 != wierszK % 2 || kolumnaP % 2 != kolumnaK % 2) {
            return false;
        }

        int wierszSrodek = (wierszP + wierszK) / 2;
        int kolumnaSrodek = (kolumnaP + kolumnaK) / 2;

        if (!plansza.sprawdzPole(wierszSrodek, kolumnaSrodek).zajete()) {
            return false;
        }

        int deltaWiersz = Integer.signum(wierszK - wierszP);
        int deltaKolumna = Integer.signum(kolumnaK - kolumnaP);

        int liczbaPustychPrzed = 0;
        int liczbaPustychZa = 0;
        boolean srodekOsiagniety = false;

        int wiersz = wierszP + deltaWiersz;
        int kolumna = kolumnaP + deltaKolumna;

        while (wiersz != wierszK || kolumna != kolumnaK) {
            if (wiersz == wierszSrodek && kolumna == kolumnaSrodek) {
                srodekOsiagniety = true;
            } else if (plansza.sprawdzPole(wiersz, kolumna).zajete()) {
                if (srodekOsiagniety) {
                    return false;
                }
            } else {
                if (srodekOsiagniety) {
                    liczbaPustychZa++;
                } else {
                    liczbaPustychPrzed++;
                }
            }

            wiersz += deltaWiersz;
            kolumna += deltaKolumna;
        }

        return liczbaPustychPrzed == liczbaPustychZa;
    }

    /**
     * Zwraca nazwę trybu gry.
     * 
     * @return Nazwa trybu gry "FastPaced".
     */
    @Override
    public String toString() {
        return "FastPaced";
    }

    /**
     * Wykonuje ruch na planszy i aktualizuje stan gry.
     * Sprawdza, czy gracz osiągnął warunki zwycięstwa.
     * 
     * @param plansza         Plansza, na której wykonywany jest ruch.
     * @param sekwencjaRuchow Tablica zawierająca sekwencję ruchów.
     * @param gracz           Indeks gracza wykonującego ruch.
     * @return True, jeśli gracz wygrał, false w przeciwnym razie.
     */
    @Override
    public boolean wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {
        if (plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]).getStrefa() != gracz
                && plansza.sprawdzPole(sekwencjaRuchow[sekwencjaRuchow.length - 1][0],
                        sekwencjaRuchow[sekwencjaRuchow.length - 1][1]).getStrefa() == gracz) {
            warunkiZwyciestwa[gracz]++;
        }
        super.wykonajRuch(plansza, sekwencjaRuchow, gracz);
        return warunkiZwyciestwa[gracz] == 10;
    }

    /**
     * Zwraca informacje o zasadach gry w formacie JSON.
     * 
     * @return JSONArray zawierający informacje o zasadach dla określonej liczby
     *         graczy.
     */
    @Override
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
     * Zwraca zwycięzcę gry na podstawie warunków zwycięstwa.
     * 
     * @param gracz Indeks gracza, którego warunki zwycięstwa są sprawdzane.
     * @return Indeks gracza, jeśli jest zwycięzcą, w przeciwnym razie 0.
     */
    @Override
    public int zwyciezca(int gracz) {
        return (warunkiZwyciestwa[gracz] == 10 ? gracz : 0);
    }

    @Override
    public List<int[][]> possibleMoves(int gracz, Plansza plansza) {
        // TODO Auto-generated method stub
        return null;
    }
}

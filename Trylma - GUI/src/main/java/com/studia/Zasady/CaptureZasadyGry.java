package com.studia.Zasady;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import com.studia.Plansza.Plansza;
import com.studia.Plansza.Pole;

public class CaptureZasadyGry extends ZasadyGry {

    public CaptureZasadyGry(int liczbaGraczy) {
        super(liczbaGraczy);
    }

    @Override
    public String toString() {
        return "Capture";
    }

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

    @Override
    public String opisZasad() {
        return "1. Trzeba zacząć od pola ze swoim pionem &" +
                "2. Trzeba skończyć na polu pustym &" +
                "3. Można ruszyć się na sąsiednie pole &" +
                "4. Można skoczyć na pole w odległości 2, jeśli pomiędzy polami jest pion &" +
                "5. Nie można opuszczać strefy zwycięskiej, chyba że wrócimy do niej w tym samym ruchu &" +
                "6. Nie można wejść do cudzej strzefy zwycięskiej, chyba że wyjdziemy z niej w tym samym ruchu";
    }

    @Override
    public boolean ruchJestPoprawny(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {

        Pole obecne = plansza.sprawdzPole(sekwencjaRuchow[0][0], sekwencjaRuchow[0][1]);
        if (!obecne.zajete()) {
            // musi po prostu być pionem
            return false;
        }

        Plansza kopiaPlanszy = new Plansza(plansza);
        boolean rezultat = true;
        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int kolumnaP = sekwencjaRuchow[i][0];
            int wierszP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];

            if (kopiaPlanszy.sprawdzPole(wierszK, kolumnaK).zajete()) {
                rezultat = false;
                // czy chcemy przejść na puste pole
            } else if ((Math.abs(wierszK - kolumnaP) == 4 && Math.abs(kolumnaK - wierszP) == 0)
                    || (Math.abs(wierszK - kolumnaP) == 2 && Math.abs(kolumnaK - wierszP) == 2)) {
                rezultat = skokJestLegalny(kopiaPlanszy, kolumnaP, wierszP, wierszK, kolumnaK);
                // tylko skoki są legalne
            }else{
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

    @Override
    public void wykonajRuch(Plansza plansza, int[][] sekwencjaRuchow, int gracz) {

        for (int i = 0; i < sekwencjaRuchow.length - 1; i++) {

            int kolumnaP = sekwencjaRuchow[i][0];
            int wierszP = sekwencjaRuchow[i][1];
            int wierszK = sekwencjaRuchow[i + 1][0];
            int kolumnaK = sekwencjaRuchow[i + 1][1];

            // każdy ruch to zbicie, więc usuwamy piona i zwiększamy punkt
            plansza.sprawdzPole((wierszK + wierszP) / 2, (kolumnaK + kolumnaP) / 2).setGracz(0);
            warunkiZwyciestwa[gracz]++;
        }
        super.wykonajRuch(plansza, sekwencjaRuchow, gracz);
    }
    @Override
    public boolean checkWin(int gracz) {
        return false;
    }
}

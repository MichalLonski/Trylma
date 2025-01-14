package com.studia.Komunikacja.GUI;

import com.studia.Zasady.FabrykaZasad;
import com.studia.Zasady.TypGry;
import com.studia.Zasady.ZasadyGry;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import static java.lang.Thread.sleep;

/**
 * Klasa kontrolera odpowiedzialna za zarządzanie interfejsem użytkownika w grze.
 * Obejmuje logikę dla planszy gry, interakcji z użytkownikiem oraz komunikację z serwerem gry.
 */
public class GraGUIController extends GUIController {

    private ZasadyGry zasady; // Zasady gry, używane do generowania planszy
    private Stage taScena; // Scena gry
    private volatile boolean wTymMenu = true; // Flaga wskazująca, czy nadal jesteśmy w menu oczekiwania
    private volatile boolean graRozpoczeta = false; // Flaga wskazująca, czy gra już się rozpoczęła
    private int miejsceGracza; // Miejsce gracza w kolejce
    private int turaGracza; // Numer aktualnej tury
    private int[][] ostatniRuch = new int[][] {
            new int[] { 0, 0 },
            new int[] { 0, 0 },
    };
    private final static int LICZBA_WIERSZY = 25; // Liczba wierszy na planszy
    private final static int LICZBA_KOLUMN = 17; // Liczba kolumn na planszy
    private final static int ROZMIAR_POLA = 30; // Rozmiar pojedynczego pola na planszy
    private int SZEROKOSC_MENU; // Szerokość menu gry
    private ArrayList<int[]> sekwencjaRuchow = new ArrayList<>(); // Sekwencja ruchów gracza
    private final ArrayList<PoleWGUI> pola = new ArrayList<>(); // Lista pól na planszy
    private final HashMap<Integer, Color> kolorGracza = new HashMap<>() {
        {
            put(9, Color.LIGHTGRAY);
            put(0, Color.WHITE);
            put(1, Color.BLUE);
            put(2, Color.GOLD);
            put(3, Color.GREEN);
            put(4, Color.YELLOW);
            put(5, Color.VIOLET);
            put(6, Color.RED);
        }
    };

    @FXML
    Label oczekiwanieLabel;

    @FXML
    Label iloscGraczyLabel;

    @FXML
    Label czyjaTuraLabel;

    @FXML
    TextArea kolejkaTextArea;

    @FXML
    TextArea zasadyTextArea;

    @FXML
    Pane menuPane;

    @FXML
    GridPane planszaGridPane;

    @FXML
    Pane planszaPane;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Pane oczekiwaniePane;

    @FXML
    Button resetButton;

    @FXML
    Button wykonajRuchButton;

    @FXML
    Rectangle twojKolorRectangle;

    @FXML
    Rectangle turaKolorRectangle;

    /**
     * Generuje planszę gry na podstawie zasad gry.
     * Tworzy odpowiednie pola i pionki na planszy, ustawiając je w odpowiednich miejscach.
     */
    public void GenerateBoard() {
        SZEROKOSC_MENU = (int) menuPane.getPrefWidth(); // Szerokość menu
        planszaGridPane = new GridPane();
        planszaGridPane.setGridLinesVisible(true);
        planszaPane.getChildren().add(planszaGridPane);
        int typ;

        JSONArray mapa = zasady.infoJSON();

        for (int wiersz = 0; wiersz < LICZBA_WIERSZY; wiersz++) {
            for (int kolumna = 0; kolumna < LICZBA_KOLUMN; kolumna++) {
                typ = mapa.getJSONArray(wiersz).getInt(kolumna);
                Pane pane = stworzPole(wiersz, kolumna, typ);
                planszaGridPane.add(pane, wiersz, LICZBA_KOLUMN - kolumna - 1);
            }
        }

        int Width = 15 + SZEROKOSC_MENU + LICZBA_KOLUMN * ROZMIAR_POLA;
        int Height = 10 + LICZBA_KOLUMN * ROZMIAR_POLA;

        anchorPane.setPrefWidth(Width);
        anchorPane.setPrefHeight(Height);

        taScena.sizeToScene();
    }

    /**
     * Tworzy pole na planszy o określonym typie.
     * Dla każdego typu pola, generuje odpowiedni wygląd pionka.
     * 
     * @param X współrzędna X pola
     * @param Y współrzędna Y pola
     * @param typ typ pola
     * @return stworzone pole jako obiekt Pane
     */
    public Pane stworzPole(int X, int Y, int typ) {
        Pane pane = new Pane();
        Rectangle rect = new Rectangle();

        pane.getChildren().add(rect);

        rect.setWidth(ROZMIAR_POLA);
        rect.setHeight(ROZMIAR_POLA);
        switch (typ) {
            case 9 -> stworzPionek(9, rect, pane, X, Y);
            case 0 -> stworzPionek(0, rect, pane, X, Y);
            case 1 -> stworzPionek(1, rect, pane, X, Y);
            case 2 -> stworzPionek(2, rect, pane, X, Y);
            case 3 -> stworzPionek(3, rect, pane, X, Y);
            case 4 -> stworzPionek(4, rect, pane, X, Y);
            case 5 -> stworzPionek(5, rect, pane, X, Y);
            case 6 -> stworzPionek(6, rect, pane, X, Y);
        }

        return pane;
    }

    /**
     * Tworzy pionek na danym polu w zależności od jego typu.
     * Dodaje odpowiedni kolor i obwódkę zaznaczenia.
     * 
     * @param typ typ pionka
     * @param rect prostokąt reprezentujący pole
     * @param pane pane zawierające pole
     * @param X współrzędna X
     * @param Y współrzędna Y
     */
    private void stworzPionek(int typ, Rectangle rect, Pane pane, int X, int Y) {
        if (typ == 9) {
            rect.setFill(Color.LIGHTGRAY);
        } else if (typ == 0) {
            rect.setFill(Color.WHITE);
            rect.toFront();
            Circle circle = new Circle();
            Circle obwodkaCircle = new Circle();
            pane.getChildren().add(circle);
            pane.getChildren().add(obwodkaCircle);

            // Reprezentacja pionka
            circle.setRadius((double) ROZMIAR_POLA * 0.45);
            circle.setCenterX((double) ROZMIAR_POLA / 2);
            circle.setCenterY((double) ROZMIAR_POLA / 2);

            circle.setStroke(Color.WHITE);
            circle.setFill(Color.WHITE);
            circle.setMouseTransparent(true);

            // Obwódka zaznaczenia
            obwodkaCircle.setRadius((double) ROZMIAR_POLA * 0.48);
            obwodkaCircle.setCenterX((double) ROZMIAR_POLA / 2);
            obwodkaCircle.setCenterY((double) ROZMIAR_POLA / 2);

            obwodkaCircle.setStroke(Color.CYAN);
            obwodkaCircle.setFill(Color.TRANSPARENT);
            obwodkaCircle.setMouseTransparent(true);
            obwodkaCircle.setVisible(false);
            obwodkaCircle.toFront();

            int[] pole = { X, Y };

            pola.add(new PoleWGUI(pole, rect, circle, obwodkaCircle, typ));
            rect.setOnMouseClicked((MouseEvent event) -> poleKlikniete(pole, obwodkaCircle, typ));
        } else {
            // Dla innych typów pionków
            rect.setFill(kolorGracza.get(typ));
            rect.toFront();
            Circle circle = new Circle();
            Circle obwodkaCircle = new Circle();
            pane.getChildren().add(circle);
            pane.getChildren().add(obwodkaCircle);

            // Reprezentacja pionka
            circle.setRadius((double) ROZMIAR_POLA * 0.45);
            circle.setCenterX((double) ROZMIAR_POLA / 2);
            circle.setCenterY((double) ROZMIAR_POLA / 2);

            circle.setStroke(Color.BLACK);
            circle.setFill(kolorGracza.get(typ));
            circle.setMouseTransparent(true);

            // Obwódka zaznaczenia
            obwodkaCircle.setRadius((double) ROZMIAR_POLA * 0.48);
            obwodkaCircle.setCenterX((double) ROZMIAR_POLA / 2);
            obwodkaCircle.setCenterY((double) ROZMIAR_POLA / 2);

            obwodkaCircle.setStroke(Color.CYAN);
            obwodkaCircle.setFill(Color.TRANSPARENT);
            obwodkaCircle.setMouseTransparent(true);
            obwodkaCircle.setVisible(false);
            obwodkaCircle.toFront();

            int[] pole = { X, Y };

            pola.add(new PoleWGUI(pole, rect, circle, obwodkaCircle, typ));
            rect.setOnMouseClicked((MouseEvent event) -> poleKlikniete(pole, obwodkaCircle, typ));
        }
    }

    /**
     * Obsługuje kliknięcie na pole na planszy.
     * Dodaje dane pole do sekwencji ruchów, jeśli gracz wykonuje ruch w swojej turze.
     * 
     * @param pole współrzędne klikniętego pola
     * @param obwodkaCircle obwódka zaznaczenia pola
     * @param typ typ pionka na tym polu
     */
    private void poleKlikniete(int[] pole, Circle obwodkaCircle, int typ) {
        if (turaGracza == miejsceGracza && graRozpoczeta) {
            sekwencjaRuchow.add(pole);
            obwodkaCircle.setVisible(true);
        }
    }

    // Wątek odpowiedzialny za komunikację z serwerem
    Thread komunikacja = new Thread(() -> {
        try {
            while (wTymMenu) {
                sleep(10);
                if (graRozpoczeta) {
                    turaGracza = Integer.parseInt(sendCommand("currentPlayer"));
                    turaKolorRectangle.setFill(kolorGracza.get(turaGracza));
                    Platform.runLater(this::aktualizujPlansze);

                    if (turaGracza == miejsceGracza) {
                        Platform.runLater(() -> {
                            resetButton.setDisable(false);
                            czyMoznaWykonacRuch();
                        });

                    } else {
                        Platform.runLater(() -> {
                            resetButton.setDisable(true);
                            wykonajRuchButton.setDisable(true);
                        });
                    }

                } else {
                    String odp = sendCommand("hasStarted");

                    Platform.runLater(() -> iloscGraczyLabel
                            .setText("Gracze: " + sendCommand("#playersGame") + "/" + sendCommand("#players")));

                    if (odp.equals("true")) {

                        Platform.runLater(() -> {
                            oczekiwanieLabel.setVisible(false);
                            miejsceGracza = Integer.parseInt(sendCommand("playerSeat"));
                            turaGracza = Integer.parseInt(sendCommand("currentPlayer"));
                            turaKolorRectangle.setFill(kolorGracza.get(turaGracza));
                            twojKolorRectangle.setFill(kolorGracza.get(miejsceGracza));
                            graRozpoczeta = true;

                        });
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    /**
     * Inicjalizuje grę na podstawie sceny i zasad gry.
     * Ustawia odpowiednie zasady gry oraz uruchamia wątek komunikacji.
     * 
     * @param stage scena gry
     */
    public void setInfo(Stage stage) {
        taScena = stage;
        String odp = sendCommand("gameRules");
        int lg = Integer.valueOf(sendCommand("#players"));
        switch (odp) {
            case "Standardowe":
                zasady = FabrykaZasad.stworzZasadyGry(TypGry.STANDARDOWA, lg);
                break;
            case "FastPaced":
                zasady = FabrykaZasad.stworzZasadyGry(TypGry.FAST_PACED, lg);
                break;
            case "Capture":
                zasady = FabrykaZasad.stworzZasadyGry(TypGry.CAPTURE, lg);
                break;
            default:
                throw new IllegalArgumentException("Błąd w ustawianiu zasad");
        }
        zasadyTextArea.setText(zasady.opisZasad().replaceAll("&", "\n"));
        komunikacja.setDaemon(true);
        komunikacja.start();
    }

    /**
     * Obsługuje kliknięcie przycisku "Wykonaj ruch".
     * Wysyła dane ruchu do serwera.
     */
    public void wykonajRuchButtonKlik() {
        String doWyslania = "";
        for (int[] pole : sekwencjaRuchow) {
            doWyslania = doWyslania + " " + pole[0] + "!" + pole[1];
        }
        sendCommand("doMove" + doWyslania);
        resetButtonKlik();
    }

    /**
     * Resetuje stan gry po wykonaniu ruchu.
     * Czyści sekwencję ruchów i ukrywa obwódki zaznaczenia.
     */
    public void resetButtonKlik() {
        sekwencjaRuchow = new ArrayList<>();
        for (PoleWGUI pole : pola) {
            Circle circ = pole.getObwodkaCirc();
            circ.setVisible(false);
        }
    }

    /**
     * Sprawdza, czy gracz może wykonać ruch.
     * Jeśli sekwencja ruchów jest poprawna, umożliwia wykonanie ruchu.
     */
    private void czyMoznaWykonacRuch() {
        if (sekwencjaRuchow.size() > 1) {
            String doWyslania = "";
            for (int[] pole : sekwencjaRuchow) {
                doWyslania = doWyslania + " " + pole[0] + "!" + pole[1];
            }

            if (sendCommand("checkMove" + doWyslania).equals("true")) {
                wykonajRuchButton.setDisable(false);
            } else {
                wykonajRuchButton.setDisable(true);
            }

        } else {
            wykonajRuchButton.setDisable(true);
        }
    }

    /**
     * Aktualizuje planszę gry na podstawie ostatniego ruchu.
     * Przemieszcza pionek na odpowiednie pole.
     */
    private void aktualizujPlansze() {
        String odp = sendCommand("lastMove");
        String[] koords1 = odp.split(" ");
        String[] koords2 = new String[] {
                koords1[0].split("!")[0],
                koords1[0].split("!")[1],
                koords1[1].split("!")[0],
                koords1[1].split("!")[1]
        };
        int[][] nowyOstatniRuch = {
                new int[] { Integer.parseInt(koords2[0]), Integer.parseInt(koords2[1]) },
                new int[] { Integer.parseInt(koords2[2]), Integer.parseInt(koords2[3]) }
        };

        if (!(nowyOstatniRuch[0][0] == ostatniRuch[0][0]
                && nowyOstatniRuch[0][1] == ostatniRuch[0][1]
                && nowyOstatniRuch[1][0] == ostatniRuch[1][0]
                && nowyOstatniRuch[1][1] == ostatniRuch[1][1])) {

            PoleWGUI poleStart = null, poleKoniec = null;

            ostatniRuch[0][0] = nowyOstatniRuch[0][0];
            ostatniRuch[0][1] = nowyOstatniRuch[0][1];
            ostatniRuch[1][0] = nowyOstatniRuch[1][0];
            ostatniRuch[1][1] = nowyOstatniRuch[1][1];

            for (PoleWGUI pole : pola) {
                if (pole.getKoordynaty()[0] == nowyOstatniRuch[0][0]
                        && pole.getKoordynaty()[1] == nowyOstatniRuch[0][1]) {
                    poleStart = pole;
                }
                if (pole.getKoordynaty()[0] == nowyOstatniRuch[1][0]
                        && pole.getKoordynaty()[1] == nowyOstatniRuch[1][1]) {
                    poleKoniec = pole;
                }
            }
            poleKoniec.getCirc().setStroke(Color.BLACK);
            poleKoniec.getCirc().setFill(poleStart.getCirc().getFill());

            poleStart.getCirc().setStroke(kolorGracza.get(poleStart.getTyp()));
            poleStart.getCirc().setFill(kolorGracza.get(poleStart.getTyp()));
        }
    }
}

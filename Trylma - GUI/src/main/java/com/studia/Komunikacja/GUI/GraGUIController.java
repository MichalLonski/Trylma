package com.studia.Komunikacja.GUI;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

/*
Klasa kontrolera dla Okienka oczekiwania na gre i gry
 */
public class GraGUIController extends GUIController {

    private volatile boolean wTymMenu = true;
    private volatile boolean graRozpoczeta = false;
    private int miejsceGracza;
    private int turaGracza;
    private final static int LICZBA_WIERSZY = 25;
    private final static int LICZBA_KOLUMN = 17;
    private Stage taScena;
    public final static int ROZMIAR_POLA = 20;
    public int SZEROKOSC_MENU;// 240
    public int WYSOKOSC_MENU;// 280
    private ArrayList<int[]> sekwencjaRuchow = new ArrayList<>();
    private ArrayList<PoleWGUI> Pola = new ArrayList<>();
    private HashMap<Integer, Color> kolorGracza = new HashMap<>() {
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

    public void GenerateBoard() {
        SZEROKOSC_MENU = (int) menuPane.getPrefWidth();// 240
        WYSOKOSC_MENU = (int) menuPane.getPrefHeight();// 280
        planszaGridPane = new GridPane();
        planszaGridPane.setGridLinesVisible(true);
        planszaPane.getChildren().add(planszaGridPane);
        int liczbaGraczy = Integer.parseInt(sendCommand("#players"));

        int X = LICZBA_WIERSZY;
        int Y = LICZBA_KOLUMN;
        int typ;///

        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/config.json");
            Map<String, ArrayList<ArrayList<Integer>>> map = mapper.readValue(file, Map.class);
            ArrayList<ArrayList<Integer>> mapa = map.get(String.valueOf(liczbaGraczy));

            for (int y = 0; y < Y; y++) {
                for (int x = 0; x < X; x++) {
                    typ = mapa.get(x).get(y);
                    Pane pane = stworzPole(x, y, typ);
                    planszaGridPane.add(pane, x, y);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int Width = 15 + SZEROKOSC_MENU + X * ROZMIAR_POLA;
        int Height = 10 + Y * ROZMIAR_POLA;

        anchorPane.setPrefWidth(Width);
        anchorPane.setPrefHeight(Height);

        taScena.sizeToScene();

        // TODO: Tu będzie framet kodu który będzie kolorować pola, czy tam kolorować
        // pionki na nim, to się zobaczy

    }

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

    private void stworzPionek(int typ, Rectangle rect, Pane pane, int X, int Y) {
        if (typ == 9) {
            rect.setFill(Color.LIGHTGRAY);
        } else if (typ == 0) {
            rect.setFill(Color.WHITE);
            Circle cric = new Circle();
            pane.getChildren().add(cric);

            cric.setRadius((double) ROZMIAR_POLA * 0.45);
            cric.setCenterX((double) ROZMIAR_POLA / 2);
            cric.setCenterY((double) ROZMIAR_POLA / 2);

            cric.setStroke(Color.WHITE);
            cric.setFill(Color.WHITE);
            cric.setMouseTransparent(true);

            int[] pole = {X,Y};

            Pola.add(new PoleWGUI(pole, rect, cric, typ));
            rect.setOnMouseClicked((MouseEvent event) -> {
                poleKlikniete(pole, cric, typ);
            });
        } else {
            rect.setFill(kolorGracza.get(typ));
            Circle cric = new Circle();
            pane.getChildren().add(cric);

            cric.setRadius((double) ROZMIAR_POLA * 0.45);
            cric.setCenterX((double) ROZMIAR_POLA / 2);
            cric.setCenterY((double) ROZMIAR_POLA / 2);

            cric.setStroke(Color.BLACK);
            cric.setFill(kolorGracza.get(typ));
            cric.setMouseTransparent(true);

            int[] pole = {X,Y};

            Pola.add(new PoleWGUI(pole, rect, cric, typ));
            rect.setOnMouseClicked((MouseEvent event) -> {
                poleKlikniete(pole, cric, typ);
            });
        }
    }

    private void poleKlikniete(int[] pole, Circle circ, int typ) {
        if (turaGracza == miejsceGracza && !graRozpoczeta) {
            sekwencjaRuchow.add(pole);
            circ.setStroke(kolorGracza.get(typ).invert());
        }
    }

    Thread komunikacja = new Thread(() -> {

        try {
            while (wTymMenu) {
                if (graRozpoczeta) {
                    Thread.sleep(10);
                    if (turaGracza == miejsceGracza) {
                        resetButton.setDisable(true);
                    }

                    czyMoznaWykonacRuch();

                } else {
                    Thread.sleep(10);
                    String odp = sendCommand("hasStarted");
                    if (odp.equals("true")) {

                        Platform.runLater(() -> {
                            oczekiwanieLabel.setVisible(false);
                            czyjaTuraLabel.setVisible(true);
                            czyjaTuraLabel.setText("Kolej Gracza " + sendCommand("currentPlayer"));
                            graRozpoczeta = true;
                            if (turaGracza == miejsceGracza) {
                                resetButton.setDisable(false);
                                wykonajRuchButton.setDisable(false);
                            }
                        });
                        System.out.println("dziaaałaam");

                    }

                    Platform.runLater(() -> {
                        iloscGraczyLabel
                                .setText("Gracze: " + sendCommand("#playersGame") + "/" + sendCommand("#players"));
                    });
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    public void setInfo(Stage stage) {
        taScena = stage;
        miejsceGracza = Integer.parseInt(sendCommand("playerSeat"));
        zasadyTextArea.setText(sendCommand("gameRules").replaceAll("&", "\n"));
        komunikacja.setDaemon(true);
        komunikacja.start();
    }

    public void wykonajRuchButtonKlik() {
        String doWyslania = "";
        for (int[] pole : sekwencjaRuchow) {
            doWyslania = doWyslania + " " + pole[0] + "!" + pole[1];
        }
        sendCommand("doMove " + doWyslania);
        // TODO: na jutroooooo
    }

    public void resetButtonKlik() {
        sekwencjaRuchow = new ArrayList<>();
    }

    private void czyMoznaWykonacRuch() {

        String doWyslania = "";
        for (int[] pole : sekwencjaRuchow) {
            doWyslania = doWyslania + " " + pole[0] +"!"+ pole[1];
        }
        if (sendCommand("checkMove " + doWyslania).equals("true")) {
            wykonajRuchButton.setDisable(true);
        }
    }

}

package com.studia.Komunikacja.GUI;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/*
Klasa kontrolera dla Okienka oczekiwania na gre i gry
 */
public class GraGUIController extends GUIController {


    private Stage taScena;
    public final static int ROZMIAR_POLA = 30;
    public int SZEROKOSC_MENU;//240
    public int WYSOKOSC_MENU;//280
    //private final Pane[][] siatkaPol = new Pane[LICZBA_WIERSZY][LICZBA_KOLUMN];

    @FXML
    TextArea kolejkaTextArea;

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

    public void GenerateBoard() {
        SZEROKOSC_MENU = (int) menuPane.getPrefWidth();//240
        WYSOKOSC_MENU = (int) menuPane.getPrefHeight();//280
        planszaGridPane = new GridPane();
        planszaGridPane.setGridLinesVisible(true);
        planszaPane.getChildren().add(planszaGridPane);


        //TODO: Zczytanie z zasad Gry rozmiaru planszy
        int Y = 6; //placeholder do testów
        int X = 6; //placeholder do testów

        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                Pane pane = stworzPole(x,y);
                planszaGridPane.add(pane, x, y);
            }
        }
        int Width = 15+SZEROKOSC_MENU+X*ROZMIAR_POLA;
        int Height = 10+Y*ROZMIAR_POLA;

        anchorPane.setPrefWidth(Width);
        anchorPane.setPrefHeight(Height);

        taScena.sizeToScene();

        //TODO: Tu będzie framet kodu który będzie kolorować pola, czy tam kolorować pionki na nim, to się zobaczy

    }

    public Pane stworzPole(int X, int Y) {
        Label label = new Label(X+""+Y);
        Pane pane = new Pane();
        Rectangle rect = new Rectangle();
        pane.getChildren().add(rect);
        pane.getChildren().add(label);
        rect.setWidth(ROZMIAR_POLA);
        rect.setHeight(ROZMIAR_POLA);
        rect.setFill(Color.GOLD);
        label.setMouseTransparent(true);
        rect.setOnMouseClicked((MouseEvent event) -> {
            poleKlikniete(Y+ " " + X);
            rect.setFill(Color.BLUE);
        });
        return pane;
    }

    public void poleKlikniete(String pole){
        //sendCommand("clicked" + pole);
    }

    public void setStage(Stage stage){
        taScena = stage;
    }

}

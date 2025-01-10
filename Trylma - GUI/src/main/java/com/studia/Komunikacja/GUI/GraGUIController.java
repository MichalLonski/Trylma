package com.studia.Komunikacja.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/*
Klasa kontrolera dla Okienka oczekiwania na gre i gry
 */
public class GraGUIController extends GUIController {

    @FXML
    GridPane planszaGridPane;

    @FXML
    Pane oczekiwaniePane;

    @FXML
    Pane wGrzePane;

    public void GenerateBoard() {
       String odp = sendCommand("#players");
       int iloscGraczy = Integer.parseInt(odp);
       //TODO: zczytuje z pliku schemat tablicy i wsadza go do GridPane
    }

}

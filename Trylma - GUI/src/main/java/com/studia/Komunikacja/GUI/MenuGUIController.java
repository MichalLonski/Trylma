package com.studia.Komunikacja.GUI;

import com.studia.Komunikacja.KlientApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
Klasa kontrolera dla Menu głównego i ekranu tworzenia gry
Nazwy raczej same tłumaczą co robią poszczególne funkcje i obiekty
Także nie ma co tu pisać
 */
public class MenuGUIController {

    private BufferedReader in;
    private PrintWriter out;

    @FXML
    private Pane menuGlownePane;

    @FXML
    private TextField IDGryDoDoloczeniaTextField;

    @FXML
    private TextArea listaGierTextArea;

    @FXML
    private Pane stworzGrePane;

    @FXML
    private ComboBox<String> iloscGraczyComboBox;

    @FXML
    private ComboBox<String> wariantGryComboBox;

    @FXML
    private TextArea opisWariantuTextArea;

    public void setInOut(BufferedReader IN, PrintWriter OUT){
        in = IN;
        out = OUT;
    }



    private String sendCommand(String komenda) {
        try {
            out.println(komenda);
            String odp = in.readLine();
            return odp;
        } catch (IOException e) {
            System.out.println("Błąd w komunikacji z serwerem\n");
            e.printStackTrace();
        }
        return null;
    }

    public void stworzGreButtonKlik(){
        menuGlownePane.setVisible(false);
        stworzGrePane.setVisible(true);
    }

    public void doloczdoGryButtonKlik(){
        if(isInteger(IDGryDoDoloczeniaTextField.getText())){
            String odpowiedz = sendCommand("join"+" "+IDGryDoDoloczeniaTextField.getText());
            if("success".equals(odpowiedz)){
                KlientApplication.MenuDoGry();
            }
        }else{
            listaGierTextArea.setText("Podaj poprawne ID\n");
        }
    }

    public void odswiezButtonKlik(){
        String odpowiedz = sendCommand("refresh");
        listaGierTextArea.setText(odpowiedz.replaceAll("&","\n"));
    }

    public void wyjdzButtonKlik(){
        try {
            quit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stworzGreFinalButtonKlik(){
        String odpowiedz = sendCommand("create" + " " + wariantGryComboBox.getValue() + " " + iloscGraczyComboBox.getValue());
        KlientApplication.MenuDoGry();
    }

    public void cofnijButtonKlik(){
        menuGlownePane.setVisible(true);
        stworzGrePane.setVisible(false);
    }

    public void wyswietlOpisZasad(){
        switch (wariantGryComboBox.getValue()){
            case "Standardowy":
                opisWariantuTextArea.setText("Opis Standardowego wariantu gry");
                break;
            case "Wariant 2":
                opisWariantuTextArea.setText("Opis 2 wariantu gry");
                break;
            case "Wariant 3":
                opisWariantuTextArea.setText("Opis 3 wariantu gry");
                break;
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    private void quit() throws IOException {
        try{
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = (Stage) menuGlownePane.getScene().getWindow();
        stage.close();
    }

}

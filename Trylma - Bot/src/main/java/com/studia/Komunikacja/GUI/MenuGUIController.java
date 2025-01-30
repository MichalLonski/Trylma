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
import java.util.Objects;

/**
 * Klasa kontrolera dla menu głównego i ekranu tworzenia gry.
 * Odpowiada za obsługę interakcji użytkownika w aplikacji GUI, w tym:
 * - Tworzenie gry
 * - Dołączanie do istniejącej gry
 * - Odświeżanie listy dostępnych gier
 * - Zamykanie aplikacji
 */
public class MenuGUIController extends GUIController {

    /** Ilośc botów do dodania w tworzonej właśnie grze */
    private int iloscBotowDoDodania = 0;

    /** Strumień wejściowy do komunikacji z serwerem */
    private BufferedReader in;

    /** Strumień wyjściowy do komunikacji z serwerem */
    private PrintWriter out;

    /** Główne okno menu */
    @FXML
    private Pane menuGlownePane;

    /** Pole tekstowe do wpisania ID gry do dołączenia */
    @FXML
    private TextField IDGryDoDoloczeniaTextField;

    @FXML
    private TextField IDGryDoOdtworzeniaTextField;

    @FXML
    private TextArea DoOdtworzeniaTextArea;

    /** Obszar tekstowy do wyświetlania listy dostępnych gier */
    @FXML
    private TextArea listaGierTextArea;

    /** Okno do tworzenia nowej gry */
    @FXML
    private Pane stworzGrePane;

    /** ComboBox do wyboru liczby graczy */
    @FXML
    private ComboBox<String> iloscGraczyComboBox;

    /** ComboBox do wyboru wariantu gry */
    @FXML
    private ComboBox<String> wariantGryComboBox;

    /** Obszar tekstowy wyświetlający opis wybranego wariantu gry */
    @FXML
    private TextArea opisWariantuTextArea;

    @FXML
    private Pane odtworzGrePane;

    /**
     * Metoda wywoływana po kliknięciu przycisku "Stwórz grę".
     * Przełącza widok na ekran tworzenia gry.
     */
    public void stworzGreButtonKlik() {
        menuGlownePane.setVisible(false);
        stworzGrePane.setVisible(true);
    }

    /**
     * Metoda wywoływana po kliknięciu przycisku "Dołącz do gry".
     * Sprawdza, czy podane ID gry jest poprawne i dołącza do gry.
     */
    public void doloczdoGryButtonKlik() {
        try {
            if (isInteger(IDGryDoDoloczeniaTextField.getText())) {
                String odpowiedz = sendCommand("join" + " " + IDGryDoDoloczeniaTextField.getText());
                if ("success".equals(odpowiedz)) {
                    KlientApplication.MenuDoGry();
                }
            } else {
                listaGierTextArea.setText("Podaj poprawne ID\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda wywoływana po kliknięciu przycisku "Odśwież".
     * Odświeża listę dostępnych gier.
     */
    public void odswiezButtonKlik() {
        String odpowiedz = sendCommand("refresh");
        listaGierTextArea.setText(odpowiedz.replaceAll("&", "\n"));
    }

    /**
     * Metoda wywoływana po kliknięciu przycisku "Wyjdź".
     * Zamyka połączenie z serwerem i kończy działanie aplikacji.
     */
    public void wyjdzButtonKlik() {
        try {
            quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda wywoływana po kliknięciu przycisku "Stwórz grę (finalizowanie)".
     * Tworzy nową grę na podstawie wybranego wariantu i liczby graczy.
     */
    public void stworzGreFinalButtonKlik() {
        try {
            if (wariantGryComboBox.getValue() != null && iloscGraczyComboBox.getValue() != null) {
                sendCommand("create" + " " + wariantGryComboBox.getValue().replace(" ", "") + " "
                        + iloscGraczyComboBox.getValue() + " " + iloscBotowDoDodania);
                KlientApplication.MenuDoGry();
                //System.out.println(iloscBotowDoDodania);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda wywoływana po kliknięciu przycisku "Cofnij".
     * Powraca do głównego menu.
     */
    public void cofnijButtonKlik() {
        menuGlownePane.setVisible(true);
        stworzGrePane.setVisible(false);
    }

    public void dodajBotaButtonKlik(){
        iloscBotowDoDodania++;
        System.out.println(iloscBotowDoDodania);
    }

    /**
     * Metoda wyświetlająca opis zasad w zależności od wybranego wariantu gry.
     */
    public void wyswietlOpisZasad() {
        switch (wariantGryComboBox.getValue()) {
            case "Standardowy":
                opisWariantuTextArea.setText("Standardowa rozgrywka");
                break;
            case "Super Chinese Checkers":
                opisWariantuTextArea.setText("Super Chinese Checkers: można skakać jak się chcę, byle symetrycznie");
                break;
            case "Capture":
                opisWariantuTextArea
                        .setText("Capture: zdobywaj punkty przez zbijanie pionów poprzez skakanie nad nimi");
                break;
        }
    }

    /**
     * Zamyka połączenie z serwerem i zamyka okno aplikacji.
     * 
     * @throws IOException w przypadku problemów z zamknięciem połączenia
     */
    @Override
    public void quit() throws IOException {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) menuGlownePane.getScene().getWindow();
        stage.close();
    }

    public void odtworzButtonKlik(){
        zczytajGry();
        menuGlownePane.setVisible(false);
        odtworzGrePane.setVisible(true);
    }

    public void odtworzCofnijButtonKlik(){
        menuGlownePane.setVisible(true);
        odtworzGrePane.setVisible(false);
    }

    private void zczytajGry(){
        String odp = sendCommand("savedGames").replaceAll("&","\n");
        DoOdtworzeniaTextArea.setText(odp);
    }

    public void odtworzFinalButtonKlik(){
        try{
            if(Objects.equals(sendCommand("gameExists " + IDGryDoOdtworzeniaTextField.getText()), "success")) {
                KlientApplication.MenuDoOdtwarzania(Integer.parseInt(IDGryDoOdtworzeniaTextField.getText()));
            }
        }catch (Exception e){ e.printStackTrace();}
    }
}

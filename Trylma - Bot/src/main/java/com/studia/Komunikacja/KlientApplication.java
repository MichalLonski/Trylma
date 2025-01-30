package com.studia.Komunikacja;

import com.studia.Komunikacja.GUI.GraGUIController;
import com.studia.Komunikacja.GUI.MenuGUIController;
import com.studia.Komunikacja.GUI.OdtwarzanieGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klasa aplikacji klienta, która odpowiada za zarządzanie interfejsem
 * użytkownika (GUI) oraz komunikację z serwerem.
 * Aplikacja umożliwia przełączanie się między dwoma głównymi ekranami: menu
 * oraz ekranem gry.
 * 
 * Rozpoczyna połączenie z serwerem oraz umożliwia interakcję z grą poprzez
 * wysyłanie i odbieranie danych z serwera.
 */
public class KlientApplication extends Application {

    /** Główne okno aplikacji (Stage) */
    static Stage glownaScena;

    /** Bufor odczytu danych z serwera */
    private static BufferedReader in;

    /** Obiekt do wysyłania danych do serwera */
    private static PrintWriter out;

    /** Scena z menu głównym */
    static Scene menuScene;

    /** Scena z ekranem gry */
    static Scene wGrzeScene;

    static Scene odtwarzanieScene;

    static OdtwarzanieGUIController odtwarzanieGUIController;

    /** Kontroler dla sceny menu */
    static MenuGUIController menuGUIController;

    /** Kontroler dla sceny gry */
    static GraGUIController graGUIController;

    /**
     * Metoda uruchamiająca aplikację i inicjalizująca interfejs użytkownika.
     * Ładuje i wyświetla ekran menu oraz nawiązuje połączenie z serwerem.
     *
     * @param stage główna scena aplikacji
     * @throws IOException jeśli wystąpi błąd podczas ładowania plików FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        
        FXMLLoader fxmlLoaderMain = new FXMLLoader(KlientApplication.class.getResource("/MenuGUI.fxml"));
        menuScene = new Scene(fxmlLoaderMain.load());
        menuGUIController = fxmlLoaderMain.getController();

        stage.setResizable(false);
        stage.setTitle("Menu");
        stage.setScene(menuScene);
        stage.show();
        glownaScena = stage;

        poloczZSerwerem();

        menuGUIController.setInOut(in, out);
    }

    /**
     * Metoda uruchamiająca aplikację.
     *
     * @param args argumenty przekazywane do aplikacji
     */
    public void start(String[] args) {
        launch(args);
    }

    /**
     * Nawiązuje połączenie z serwerem poprzez gniazdo sieciowe.
     * Ustawia strumienie wejścia/wyjścia do komunikacji z serwerem.
     */
    @SuppressWarnings("resource")
    private void poloczZSerwerem() {
        try {
            Socket socket = new Socket("localhost", 8000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Połączono z serwerem");
        } catch (IOException e) {
            System.out.println("Nie udało się połączyć z serwerem.");
            e.printStackTrace();
        }
    }

    /**
     * Przełącza aplikację do sceny gry, inicjalizując wszystkie niezbędne elementy.
     *
     * @throws IOException jeśli wystąpi błąd podczas ładowania plików FXML
     */
    public static void MenuDoGry() throws IOException {
        
        FXMLLoader fxmlLoaderSec = new FXMLLoader(KlientApplication.class.getResource("/WGrzeGUI.fxml"));
        wGrzeScene = new Scene(fxmlLoaderSec.load());
        graGUIController = fxmlLoaderSec.getController();

        graGUIController.setInOut(in, out);
        graGUIController.setInfo(glownaScena);

        glownaScena.setScene(wGrzeScene);

        graGUIController.GenerateBoard();
    }

    /**
     * Przełącza aplikację z powrotem do sceny menu.
     */
    public static void GraDoMenu() {
        glownaScena.setScene(menuScene);
    }

    public static void MenuDoOdtwarzania(int idGry) throws IOException{
        FXMLLoader fxmlLoaderSec = new FXMLLoader(KlientApplication.class.getResource("/OdtwarzanieGUI.fxml"));
        odtwarzanieScene = new Scene(fxmlLoaderSec.load());
        odtwarzanieGUIController = fxmlLoaderSec.getController();

        odtwarzanieGUIController.setInOut(in, out);
        odtwarzanieGUIController.setInfo(glownaScena,idGry);

        glownaScena.setScene(odtwarzanieScene);

        odtwarzanieGUIController.GenerateBoard();
    }
}

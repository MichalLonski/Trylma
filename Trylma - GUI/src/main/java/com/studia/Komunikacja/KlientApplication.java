package com.studia.Komunikacja;
import com.studia.Komunikacja.GUI.GraGUIController;
import com.studia.Komunikacja.GUI.MenuGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
Klasa klienta, która także odpowiada za zarządanie naszymi dwoma okienkami
 */
public class KlientApplication extends Application{

    static Stage glownaScena;
    private static BufferedReader in;
    private static PrintWriter out;
    static Scene menuScene;
    static Scene wGrzeScene;
    static MenuGUIController menuGUIController;
    static GraGUIController graGUIController;

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
        menuGUIController.setInOut(in,out);

    }

    public void start(String[] args){
        launch(args);
    }

    private void poloczZSerwerem() {
        System.out.println("Zyje!");
        try {
            Socket socket = new Socket("localhost", 8000);
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Połączono z serwerem");
        } catch (IOException e) {
            System.out.println("Nie udało się połączyć z serwerem.");
            e.printStackTrace();
        }
    }

    public static void MenuDoGry() throws IOException{
        FXMLLoader fxmlLoaderSec = new FXMLLoader(KlientApplication.class.getResource("/WGrzeGUI.fxml"));
        wGrzeScene = new Scene(fxmlLoaderSec.load());
        graGUIController = fxmlLoaderSec.getController();
        graGUIController.setInOut(in,out);
        graGUIController.setStage(glownaScena);

        glownaScena.setScene(wGrzeScene);
        graGUIController.GenerateBoard();
    }

    public static void GraDoMenu(){
        glownaScena.setScene(menuScene);
    }
}

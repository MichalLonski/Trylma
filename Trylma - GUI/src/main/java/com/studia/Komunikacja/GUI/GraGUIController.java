package com.studia.Komunikacja.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
Klasa kontrolera dla Okienka oczekiwania na gre i gry
 */
public class GraGUIController {

    private BufferedReader in;
    private PrintWriter out;

    public void setInOut(BufferedReader IN, PrintWriter OUT){
        in = IN;
        out = OUT;
    }

    private void quit() throws IOException {
        try{
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

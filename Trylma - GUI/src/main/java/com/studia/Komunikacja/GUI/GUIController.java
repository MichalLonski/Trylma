package com.studia.Komunikacja.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class GUIController {

    public BufferedReader in;
    public PrintWriter out;

    public void setInOut(BufferedReader IN, PrintWriter OUT) {
        in = IN;
        out = OUT;
    }

    public synchronized String sendCommand(String komenda) {
        try {
            String odp;
            synchronized (this) {
                out.println(komenda);
                odp = in.readLine();
            }
            return odp;
        } catch (IOException e) {
            System.out.println("Błąd w komunikacji z serwerem\n");
            e.printStackTrace();
        }
        return null;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void quit() throws IOException {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

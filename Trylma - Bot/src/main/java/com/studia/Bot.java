package com.studia;

import com.studia.Plansza.Pole;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class Bot {
    private Gracz gracz;
    private poleXkoordyXczyzajete[] Pola = new poleXkoordyXczyzajete[10];
    private poleXkoordyXczyzajete poleDocelowe = new poleXkoordyXczyzajete(null,null);

    public Bot(){
        gracz = new Gracz();
    }

    private class poleXdouble {
        public Pole i;
        public double d;
        public poleXdouble(double D, Pole I){
            d = D;
            i = I;
        }
    }

    private class poleXkoordyXczyzajete {
        public Pole i;
        public int[] k;
        public boolean b = false;
        public poleXkoordyXczyzajete(Pole I, int[] K){
            k = K;
            i = I;
        }
    }

    private class StatyRuchu {
        int[][] sekwencjaRuchu;
        int[] poleDocelowe;
        boolean poleDoceloweCzyUkończone = false;
        double delta;
        double jakBardzoZTylu;
        double score;

        double w1 = 5;
        double w2 = 1;
        double deltaW1 = 0.25;

        public StatyRuchu(int[][] x,int[] y){
            sekwencjaRuchu = x;
            poleDocelowe = y;

            for(poleXkoordyXczyzajete pole : Pola){
                w1 += deltaW1;
            }

            obliczScore();
        }

        private void obliczScore(){
            int[] poleStart = sekwencjaRuchu[0];
            int[] poleKoniec = sekwencjaRuchu[sekwencjaRuchu.length-1];
            int factor = 1;
            delta = Math.sqrt(Math.pow(poleDocelowe[0] - poleStart[0], 2) + Math.pow(poleDocelowe[1] - poleStart[1], 2))
                    - Math.sqrt(Math.pow(poleDocelowe[0] - poleKoniec[0], 2) + Math.pow(poleDocelowe[1] - poleKoniec[1], 2));
            jakBardzoZTylu = Math.sqrt(Math.pow(gracz.dajGre().getSredniaPozycjaPionka(gracz.ktoreMiejsce())[0] - poleStart[0], 2)
                    + Math.pow(gracz.dajGre().getSredniaPozycjaPionka(gracz.ktoreMiejsce())[1] - poleStart[1], 2));

            double odlegloscGrupyOdCelu = Math.sqrt(Math.pow(gracz.dajGre().getSredniaPozycjaPionka(gracz.ktoreMiejsce())[0] - poleDocelowe[0], 2)
                    + Math.pow(gracz.dajGre().getSredniaPozycjaPionka(gracz.ktoreMiejsce())[1] - poleDocelowe[1], 2));
            double odlegloscOdGrupyNasza =Math.sqrt(Math.pow(poleStart[0] - poleDocelowe[0], 2)
                    + Math.pow(poleStart[1] - poleDocelowe[1], 2));

            if(odlegloscGrupyOdCelu > odlegloscOdGrupyNasza){
                factor = -1;
            }

            score = w1 * delta + factor * w2 * jakBardzoZTylu;

            for(poleXkoordyXczyzajete pole : Pola) {
                if (poleStart[0] == pole.k[0] & poleStart[1] == pole.k[1] & pole.b) {
                    score = -1000;
                    break;
                }
            }
            for(poleXkoordyXczyzajete pole : Pola) {
                if (pole.k[0] == poleDocelowe[0] & pole.k[1] == poleDocelowe[1] & pole.b) {
                    poleDoceloweCzyUkończone = true;
                }
            }

            if(poleKoniec[0] == poleDocelowe[0] & poleKoniec[1] == poleDocelowe[1] & poleDoceloweCzyUkończone){
                score = 1000;
            }

            //printResults();
        }

        public void printResults(){
            System.out.print("Sekwencja: ");
            for(int[] s : sekwencjaRuchu){
                System.out.print("[" + s[0] + "," + s[1] + "]" );
            }
            System.out.print("Pole docelowe: " + "[" + poleDocelowe[0] + "," + poleDocelowe[1] + "]");
            System.out.print(" delta:" + delta + " jakBardzoZTylu:" + jakBardzoZTylu + " score:" + score);
            System.out.println();
        }


    }

    public void startBot(){
        //System.out.println("Bot Żyjeee!");
        dziala.setDaemon(true);
        dziala.start();
        getAndSortPolaDocelowe();


    }

    Thread dziala = new Thread(() -> {
        try{
            while (gracz.dajGre().czyGraSieZaczela()){
                sleep(100);
                if(gracz.dajGre().trwaTuraGracza(gracz.ktoreMiejsce())){
                    sleep(200);
                    wykonajRuch();
                }
            }
        }catch (Exception e){

        }

    });

    private void getAndSortPolaDocelowe(){
        ArrayList<poleXdouble> tempPole = new ArrayList<>();
        for (int x = 0;x < gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry()[1].length ; x++){
            for (int y = 0;y < gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry().length;y++){
                if(gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry()[y][x].getStrefa() == gracz.ktoreMiejsce()){
                    tempPole.add(new poleXdouble(Math.sqrt(Math.pow(x - 12, 2) + Math.pow(y - 8, 2)),
                            gracz.dajGre().dajPlanszaGry().dajPlanszaDoGry()[y][x]));
                }
            }
        }

        tempPole.sort(Comparator.comparingDouble(p -> p.d));
        for(int i = 0;i < tempPole.size();i++){
            Pola[i] = new poleXkoordyXczyzajete(tempPole.get(tempPole.size() - 1 -i ).i,gracz.dajGre().dajPlanszaGry().dajKoordyOdPola(tempPole.get(tempPole.size() - 1 -i).i)) ;
        }

    }

    private synchronized void wykonajRuch(){

        List<int[][]> possibleMoves = gracz.dajGre().dajZasadyGry().possibleMoves(gracz.ktoreMiejsce(),gracz.dajGre().dajPlanszaGry());
        List<StatyRuchu> mozliweRuchy = new ArrayList<>();

        if(poleDocelowe.b){
            for (poleXkoordyXczyzajete pole : Pola){
                Pole pole1 = gracz.dajGre().dajPlanszaGry().sprawdzPole(pole.k[0],pole.k[1]);
                if(pole1.getGracz() == pole1.getStrefa()){
                    pole.b = true;
                }
            }
        }

        for (int i = 0;i < 10;i++){
            if(!Pola[i].b){
                poleDocelowe = Pola[i];
                break;
            }
        }

        for(int[][] sekwencjaRuchow : possibleMoves){
            StatyRuchu temp = new StatyRuchu(sekwencjaRuchow,poleDocelowe.k);
            mozliweRuchy.add(temp);
        }

        double maxScore = -1000;
        StatyRuchu ruchDoWykonania = null;
        for(StatyRuchu statyRuchu : mozliweRuchy){
            if(maxScore < statyRuchu.score){
                maxScore = statyRuchu.score;
                ruchDoWykonania = statyRuchu;
            }
        }


        if((poleDocelowe.k == null & poleDocelowe.i == null) || ruchDoWykonania == null){
            gracz.dajGre().passTury(gracz.ktoreMiejsce());
            System.out.println("Bot zpassował");
        }else{

            if(ruchDoWykonania.sekwencjaRuchu[ruchDoWykonania.sekwencjaRuchu.length-1][0] == poleDocelowe.k[0] &
                    ruchDoWykonania.sekwencjaRuchu[ruchDoWykonania.sekwencjaRuchu.length-1][1] == poleDocelowe.k[1]){
                poleDocelowe.b = true;
            }

            gracz.dajGre().wykonajRuch(gracz.ktoreMiejsce(),ruchDoWykonania.sekwencjaRuchu);
            System.out.println("Bot wykonał ruch:"); ruchDoWykonania.printResults();
        }

    }

    public Gracz getGracz() {
        return gracz;
    }
}

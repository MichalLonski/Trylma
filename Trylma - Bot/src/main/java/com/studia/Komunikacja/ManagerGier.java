package com.studia.Komunikacja;

import com.studia.BazaDanychMk2.GraService;
import com.studia.BazaDanychMk2.JDBCTemplate;
import com.studia.BazaDanychMk2.RuchService;
import com.studia.Gra;
import com.studia.Gracz;
import com.studia.Zasady.TypGry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa odpowiedzialna za zarządzanie grami na serwerze. Umożliwia tworzenie
 * nowych gier, dołączanie graczy do istniejących gier,
 * wykonywanie ruchów w grach oraz komunikowanie się z graczami.
 * 
 * Jest to implementacja wzorca Singleton, dzięki czemu istnieje tylko jedna
 * instancja tej klasy w systemie.
 */
public class ManagerGier {

    ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    private JDBCTemplate JDBCTemplate;

    GraService graService = (GraService) context.getBean("graService");
    RuchService ruchService = (RuchService) context.getBean("ruchService");




    /** Lista wszystkich gier dostępnych na serwerze */
    final private List<Gra> ListaGier = new ArrayList<>();

    /** Mapa przechowująca skojarzenie gracza z jego PrintWriter do komunikacji */
    final private Map<Gracz, PrintWriter> konsolaGraczy = new HashMap<>();

    private ManagerGier() {
        JDBCTemplate = (JDBCTemplate)context.getBean("JDBCTemplate");

        graService.setJdbcTemplate(JDBCTemplate.getJdbcTemplateObject());
        ruchService.setJdbcTemplate(JDBCTemplate.getJdbcTemplateObject());
    }

    /**
     * Klasa pomocnicza zapewniająca implementację Singletona.
     */
    private static class SingletonHelper {
        /** Jedyna instancja klasy ManagerGier */
        volatile static ManagerGier INSTANCE = new ManagerGier();
    }

    /**
     * Zwraca instancję klasy ManagerGier (singleton).
     * 
     * @return instancja klasy ManagerGier
     */
    public static ManagerGier dajInstancje() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Inicjuje nową grę i dodaje do niej gracza, który ją stworzył.
     * 
     * @param typGry          typ gry, którą chcemy stworzyć
     * @param liczbaGraczy    liczba graczy, którzy wezmą udział w grze
     * @param inicjujacyGracz gracz, który tworzy grę
     */
    public synchronized void inicjujNowaGre(TypGry typGry, int liczbaGraczy, Gracz inicjujacyGracz,int iloscBotow) {
        Gra nowaGra = new Gra(typGry, liczbaGraczy,iloscBotow);
        ListaGier.add(nowaGra);
        dolaczDoGry(inicjujacyGracz,String.valueOf(nowaGra.dajID()));
    }

    /**
     * Dodaje gracza do istniejącej gry.
     * 
     * @param gracz gracz, który chce dołączyć do gry
     * @param id    ID gry, do której gracz chce dołączyć
     * @return 1 jeśli dołączenie do gry się udało, -1 jeśli gra jest pełna lub nie
     *         istnieje
     */
    public synchronized int dolaczDoGry(Gracz gracz, String id) {
        int iD = Integer.parseInt(id);

        if (znajdzGrePoID(iD) == null) {
            return -1;
        } else if (znajdzGrePoID(iD).dajZasadyGry().ileGraczy() == znajdzGrePoID(iD).dajListeGraczy().size()) {
            return -1;
        } else {
            Gra gra = znajdzGrePoID(iD);
            gra.dodajGracza(gracz);
            jesliGraPelnaRozpocznij(gracz.dajGre());
            return 1;
        }
    }

    /**
     * Sprawdza, czy gra jest pełna, i jeśli tak, wywołuje rozpoczęcie gry.
     * 
     * @param gra gra, którą należy sprawdzić
     */
    public synchronized void jesliGraPelnaRozpocznij(Gra gra) {
        if (gra.dajZasadyGry().ileGraczy() == gra.dajListeGraczy().size()) {
            rozpocznijGre(gra);
        }
    }

    /**
     * Rozpoczyna grę i wysyła komunikat do wszystkich graczy o rozpoczęciu gry.
     * 
     * @param gra gra, która ma zostać rozpoczęta
     */
    private synchronized void rozpocznijGre(Gra gra) {
        gra.ZacznijGre();
    }

    /**
     * Wykonuje ruch w grze i wysyła komunikat do wszystkich graczy o wykonaniu
     * ruchu.
     * 
     * @param gracz           gracz, który wykonał ruch
     * @param sekwencjaRuchow sekwencja ruchów, które zostały wykonane
     */
    public synchronized void wykonajRuch(Gracz gracz, int[][] sekwencjaRuchow) {
        gracz.wykonajRuch(sekwencjaRuchow);
    }

    /**
     * Zwraca grę po podanym ID.
     * 
     * @param id ID gry, którą chcemy znaleźć
     * @return gra o podanym ID, lub null, jeśli gra o takim ID nie istnieje
     */
    public Gra znajdzGrePoID(int id) {
        for (Gra g : ListaGier) {
            if (g.dajID() == id) {
                return g;
            }
        }
        return null;
    }

    /**
     * Rejestruje gracza w systemie, przypisując mu odpowiedni PrintWriter do
     * komunikacji.
     * 
     * @param gracz       gracz, którego rejestrujemy
     * @param printWriter PrintWriter, który będzie używany do komunikacji z graczem
     */
    public void zarejestrujGracza(Gracz gracz, PrintWriter printWriter) {
        konsolaGraczy.put(gracz, printWriter);
    }

    /**
     * Zwraca PrintWriter skojarowany z danym graczem.
     * 
     * @param gracz gracz, dla którego chcemy uzyskać PrintWriter
     * @return PrintWriter skojarzony z graczem
     */
    public PrintWriter konsolaGracza(Gracz gracz) {
        return konsolaGraczy.get(gracz);
    }

    /**
     * Zwraca opis wszystkich gier dostępnych na serwerze.
     * 
     * @return lista opisów gier
     */
    public String wypiszGry() {
        String wynik = "";
        for (Gra gra : ListaGier) {
            wynik += gra.opis() + "&";
        }
        return wynik;
    }

    public GraService getGraService() {
        return graService;
    }

    public RuchService getRuchService() {
        return ruchService;
    }
}

package com.studia.BazaDanych;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studia.Gra;

@Service
public class GraService {

    @Autowired
    private GraRepository graRepository;

    @Autowired
    private RuchRepository ruchRepository;

    // Zapisanie stanu gry
    public void zapiszGre(Gra gra) {
        GraEntity graEntity = new GraEntity();
        graEntity.setIdGry(gra.dajID());
        graEntity.setGraWTrakcie(gra.czyGraSieZaczela());
        graEntity.setStatusGry(gra.opis());

        // Zapisanie ruchów
        for (int[][] ruch : gra.dajRuchyZHistorii()) {
            RuchEntity ruchEntity = new RuchEntity();
            ruchEntity.setSekwencjaRuchow(ruch);
            ruchEntity.setGracz(gra.dajObecnegoGracza());
            ruchEntity.setGra(graEntity);
            ruchRepository.save(ruchEntity);
        }

        graRepository.save(graEntity);
    }

    // Zapisanie ruchu gracza
    public void zapiszRuch(int[][] sekwencjaRuchow, int gracz, Gra gra) {
        RuchEntity ruchEntity = new RuchEntity();
        ruchEntity.setSekwencjaRuchow(sekwencjaRuchow);
        ruchEntity.setGracz(gracz);

        GraEntity graEntity = graRepository.findByIdGry(gra.dajID());
        ruchEntity.setGra(graEntity);
        ruchRepository.save(ruchEntity);
    }

    
}

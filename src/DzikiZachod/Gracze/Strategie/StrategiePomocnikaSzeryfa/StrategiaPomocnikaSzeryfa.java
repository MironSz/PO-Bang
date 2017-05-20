package DzikiZachod.Gracze.Strategie.StrategiePomocnikaSzeryfa;

import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.Gracze.Gracz;
import DzikiZachod.Gracze.Strategie.Strategia;
import DzikiZachod.StrukturyDanych.Wydarzenie;

import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public abstract class StrategiaPomocnikaSzeryfa extends Strategia {
    public Wydarzenie planuj(List<Akcja> reka) {
        if(gracz.lewy().jestSzeryfem())
            if(gracz.lewy().otrzymaneObrazenia()>0)
                return new Wydarzenie(Akcja.ULECZ,gracz,gracz.lewy());
        if(gracz.prawy().jestSzeryfem())
            if(gracz.prawy().otrzymaneObrazenia()>0)
                return new Wydarzenie(Akcja.ULECZ,gracz,gracz.prawy());
        return super.planuj(reka);
    }

    protected boolean wartoRzucacDynamit() {
        int ilPodejrzanych;
        List<Gracz> graczeDoSzeryfa=gracz.graczeDoSzeryfa();
        if(graczeDoSzeryfa.size()>4) {
            if (3 * gracz.gra().historia().podejrzani(graczeDoSzeryfa).size() >= 2 * graczeDoSzeryfa.size())
                return true;
        }
        return false;
    }
}

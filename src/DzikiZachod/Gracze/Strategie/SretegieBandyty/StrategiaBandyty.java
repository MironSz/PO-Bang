package DzikiZachod.Gracze.Strategie.SretegieBandyty;

import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.Gracze.Gracz;
import DzikiZachod.Gracze.Strategie.Strategia;
import DzikiZachod.StrukturyDanych.Wydarzenie;

import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public abstract class StrategiaBandyty extends Strategia {
    protected boolean wartoRzucacDynamit() {
        List<Gracz> graczeDoSzeryfa=gracz.graczeDoSzeryfa();
        return graczeDoSzeryfa.size()<=4;
    }

    protected Wydarzenie strzela(List<Akcja> reka) {
        if(gracz.osobyWZasiegu().contains(gracz.gra().szeryf()))
            return new Wydarzenie(Akcja.STRZEL,gracz,gracz.gra().szeryf());
        return null;
    }
}

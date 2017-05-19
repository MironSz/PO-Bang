package DzikiZachod.Gracze.Strategie.StrategieSzeryfa;

import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.Gracze.Gracz;
import DzikiZachod.StrukturyDanych.Wydarzenie;

import java.util.Collections;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaSzeryfaZliczajaca extends StrategiaSzeryfa{
    protected Wydarzenie strzela(List<Akcja> reka) {
        if(reka.contains(Akcja.STRZEL)) {
            List<Gracz> osobyWZasiegu = gracz.osobyWZasiegu();
            osobyWZasiegu=gracz.gra().historia.podejrzani(osobyWZasiegu);
            Collections.shuffle(osobyWZasiegu);
            return new Wydarzenie(Akcja.STRZEL,gracz,osobyWZasiegu.get(0));

        }
        return null;
    }
}

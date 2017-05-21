package DzikiZachod.Gracze.Strategie.SretegieBandyty;

import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.Gracze.Gracz;
import DzikiZachod.StrukturyDanych.Wydarzenie;

import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaBandytyDomyslna extends StrategiaBandyty{
    protected Wydarzenie strzela(List<Akcja> reka) {
        if(super.strzela(reka)!=null)
            return super.strzela(reka);
        List<Gracz> osobyWZasiegu=gracz.osobyWZasiegu();

        for(Gracz sasiad:osobyWZasiegu)
            if (gracz.czyJestBandyta( sasiad) == false)
                return new Wydarzenie(Akcja.STRZEL,gracz,sasiad);

        return null;
    }
}

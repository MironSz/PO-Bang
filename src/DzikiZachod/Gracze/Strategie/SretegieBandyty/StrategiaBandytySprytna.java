package DzikiZachod.Gracze.Strategie.SretegieBandyty;

import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.Gracze.Gracz;
import DzikiZachod.Gracze.Bandyta;
import DzikiZachod.StrukturyDanych.Wydarzenie;

import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaBandytySprytna extends StrategiaBandyty{
    private StrategiaBandytyDomyslna strategiaBandytyDomyslna;
    private Bandyta ofiarnyBandyta=null;

    public void ustalGracza(Gracz gracz) {
        super.ustalGracza(gracz);
        strategiaBandytyDomyslna = new StrategiaBandytyDomyslna();
        strategiaBandytyDomyslna.ustalGracza(gracz);
    }

    protected Wydarzenie strzela(List<Akcja> reka) {
        if(super.strzela(reka)!=null)
            return super.strzela(reka);
        if (ofiarnyBandyta != null && ofiarnyBandyta.zyje() == false)
            return strategiaBandytyDomyslna.planuj(reka);
        if (ofiarnyBandyta != null && ofiarnyBandyta.zyje())
            return new Wydarzenie(Akcja.STRZEL,gracz,ofiarnyBandyta);
        List<Gracz> osobyWZasiegu=gracz.osobyWZasiegu();
        for(Gracz sasiad:osobyWZasiegu) {
            if (gracz.gra().historia().czyJestBandytą(gracz, sasiad) && strzalyWRece(reka) >= sasiad.zycie()) {
                ofiarnyBandyta=(Bandyta)sasiad;
                return new Wydarzenie(Akcja.STRZEL,gracz,sasiad);
            }
        }
        return null;
    }
}

package DzikiZachod.Gracze.Strategie;

import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.Gracze.Gracz;
import DzikiZachod.StrukturyDanych.Wydarzenie;

import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public abstract class Strategia {
    protected Gracz gracz;

    protected abstract boolean wartoRzucacDynamit();

    protected abstract Wydarzenie strzela(List<Akcja> reka);


    public void ustalGracza(Gracz gracz) {
        this.gracz=gracz;
    }

    protected int strzalyWRece(List<Akcja> reka) {
        int wyn=0;
        for(Akcja akcja:reka)
            if(akcja==Akcja.STRZEL)
                wyn++;
        return wyn;
    }

    public Wydarzenie planuj(List<Akcja> reka) {
        //System.out.println("DzikiZachod.Gracze.Gracz "+gracz.nrGracza()+"posiada ulecz:"+ reka.contains(DzikiZachod.StrukturyDanych.Akcja.ULECZ));
        if(reka.contains(Akcja.ULECZ)&&gracz.otrzymaneObrazenia()!=0) {
            return new Wydarzenie(Akcja.ULECZ,gracz,gracz);
        } else if(reka.contains(Akcja.ZASIEG_PLUS_DWA)) {
            return new Wydarzenie(Akcja.ZASIEG_PLUS_DWA, gracz);
        } else if(reka.contains(Akcja.ZASIEG_PLUS_JEDEN)) {
            return new Wydarzenie(Akcja.ZASIEG_PLUS_JEDEN, gracz);
        } else if(reka.contains(Akcja.STRZEL)&&strzela(reka)!=null) {
            return strzela(reka);
        } else if(reka.contains(Akcja.DYNAMIT)&&wartoRzucacDynamit()==true) {
            return new Wydarzenie(Akcja.DYNAMIT,gracz);
        } else
            return null;
    }
}

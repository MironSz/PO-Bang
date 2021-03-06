package DzikiZachod.Gracze;

import DzikiZachod.Gra;
import DzikiZachod.Gracze.Strategie.SretegieBandyty.StrategiaBandyty;
import DzikiZachod.Gracze.Strategie.SretegieBandyty.StrategiaBandytyDomyslna;

import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class Bandyta extends Gracz {
    private List<Bandyta> bandyci;

    public Bandyta(StrategiaBandyty strategiaBandyty) {
        super(strategiaBandyty);
    }

    public  Bandyta() {
        super(new StrategiaBandytyDomyslna());
    }

    public void umrzyj() {
        super.umrzyj();
        gra().usunBandyte(this);
    }

    public void ustawGrę(Gra gra) {
        this.gra=gra;
        bandyci=gra.dodajBandytę(this);
    }

    public String frakcja() {
        return "Bandyta";
    }
}

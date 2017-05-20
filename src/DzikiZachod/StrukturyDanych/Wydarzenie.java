package DzikiZachod.StrukturyDanych;

import DzikiZachod.Gracze.Gracz;

/**
 * Created by Miron on 16.05.2017.
 * Klasa przechowująca zagrania graczy.
 * Klasa przetrzymuje jaką akcję zagrał który
 * gracz i ewentualnie na kim.
 */
public class Wydarzenie {
    public final Akcja akcja;
    public final Gracz naKim;
    public final Gracz kto;
    public final boolean umarł;

    public String toString() {
        String res=akcja.toString();
        if(naKim!=null)
            res=res+" "+naKim.nrGracza();
        return res;
    }

    public Wydarzenie(Akcja akcja, Gracz kto) {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=null;
        umarł=false;
    }

    public Wydarzenie(Akcja akcja,Gracz kto,Gracz naKim) {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=naKim;
        umarł=false;
    }

    public  Wydarzenie(Akcja akcja,Gracz kto,Gracz naKim,boolean umarł) {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=naKim;
        this.umarł=umarł;
    }

}

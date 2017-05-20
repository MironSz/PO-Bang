package DzikiZachod.Gracze;

import DzikiZachod.Gra;
import DzikiZachod.Gracze.Strategie.StrategieSzeryfa.StrategiaSzeryfa;
import DzikiZachod.Gracze.Strategie.StrategieSzeryfa.StrategiaSzeryfaDomyslna;

/**
 * Created by Miron on 16.05.2017.
 */
public class Szeryf extends Gracz {
    private Szeryf(StrategiaSzeryfa strategia) {
        super(strategia);
        this.maxZycie=5;
        this.zycie=5;
    }

    public Szeryf() {
        this(new StrategiaSzeryfaDomyslna());
    }

    public  boolean jestSzeryfem() {
        return true;
    }

    public String frakcja() {
        return "Szeryf";
    }
}

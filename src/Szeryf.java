/**
 * Created by Miron on 16.05.2017.
 */
public class Szeryf extends Gracz{
    public Szeryf(StrategiaSzeryfa strategia)
    {
        super(strategia);
        this.maxZycie=5;
        this.zycie=5;
    }
    public Szeryf()
    {
        this(new StrategiaSzeryfaDomyslna());
    }
    public void ustawGrę(Gra gra)
    {
        this.gra=gra;
        gra.ustalSzeryfa(this);
    }
    public  boolean jestSzeryfem()
    {
        return true;
    }
    public String frakcja()
    {
        return "Szeryf";
    }
}

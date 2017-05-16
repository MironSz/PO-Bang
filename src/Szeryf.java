/**
 * Created by Miron on 16.05.2017.
 */
public class Szeryf extends Gracz{
    public void ustawGrę(Gra gra)
    {
        this.gra=gra;
        gra.ustalSzeryfa(this);
    }
    public  boolean jestSzeryfem()
    {
        return true;
    }
}

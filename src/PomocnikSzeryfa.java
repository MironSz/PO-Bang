/**
 * Created by Miron on 16.05.2017.
 */
public class PomocnikSzeryfa extends Gracz{
    public PomocnikSzeryfa(StrategiaPomocnikaSzeryfa strategiaPomocnikaSzeryfa)
    {
        super(strategiaPomocnikaSzeryfa);
    }
    public  PomocnikSzeryfa()
    {
        super(new StrategiaPomocnikaSzeryfaDomyslna());
    }
    public String frakcja()
    {
        return "Pomocnik Szeryfa";
    }
}

import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaBandytyCierpliwa extends StrategiaBandyty{
    protected Wydarzenie strzela(List<Akcja> reka)
    {
        if(gracz.osobyWZasiegu().contains(gracz.gra().szeryf()))
            return new Wydarzenie(Akcja.STRZEL,gracz,gracz.gra().szeryf());
        return null;
    }
}

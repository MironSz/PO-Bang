import java.util.Collections;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaPomocnikaSzeryfaDomyslna extends StrategiaPomocnikaSzeryfa {
    protected Wydarzenie strzela(List<Akcja> reka)
    {
        List<Gracz> osobyWZasiegu=gracz.osobyWZasiegu();
        Collections.shuffle(osobyWZasiegu);
        for(Gracz sasiad:osobyWZasiegu)
            if(sasiad.jestSzeryfem()==false)
            {
                return new Wydarzenie(Akcja.STRZEL,gracz,sasiad);
            }
        return null;
    }

}

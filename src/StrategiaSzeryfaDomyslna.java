import java.util.Collections;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaSzeryfaDomyslna extends StrategiaSzeryfa{
    protected Wydarzenie strzela(List<Akcja> reka)
    {

        List<Gracz> osobyWZasiegu = gracz.osobyWZasiegu();
        Collections.shuffle(osobyWZasiegu);
        for (Gracz sasiad : osobyWZasiegu) {
            if (gracz.gra().historia.strzalyDoSzeryfa(sasiad) > 0) {
                return new Wydarzenie(Akcja.STRZEL,gracz,sasiad);
            }
        }
        if(osobyWZasiegu.isEmpty()==false)
            return new Wydarzenie(Akcja.STRZEL,gracz,osobyWZasiegu.get(0));
        else
            return null;
    }



}

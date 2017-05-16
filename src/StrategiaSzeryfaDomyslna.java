import java.util.Collections;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaSzeryfaDomyslna extends StrategiaSzeryfa{
    public Wydarzenie planuj(List<Akcja> reka)
    {
        Wydarzenie res=super.planuj((reka));
        if(res!=null)
            return res;
        if(reka.contains(Akcja.STRZEL)) {
            List<Gracz> osobyWZasiegu = gracz.osobyWZasiegu();
            Collections.shuffle(osobyWZasiegu);
            for (Gracz sasiad : osobyWZasiegu) {
                if (gracz.gra().historia.strzalyDoSzeryfa(sasiad) > 0) {
                    return new Wydarzenie(Akcja.STRZEL,gracz,sasiad);
                }
            }
            return new Wydarzenie(Akcja.STRZEL,gracz,osobyWZasiegu.get(0));
        }
        else
            return null;
    }
}

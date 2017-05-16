import java.util.Collections;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaSzeryfaZliczajaca extends StrategiaSzeryfa{
    public Wydarzenie planuj(List<Akcja> reka)
    {
        Wydarzenie res=super.planuj((reka));
        if(res!=null)
            return res;
        if(reka.contains(Akcja.STRZEL)) {
            List<Gracz> osobyWZasiegu = gracz.osobyWZasiegu();
            Collections.shuffle(osobyWZasiegu);
            for (Gracz sasiad : osobyWZasiegu) {
                if (gracz.gra().historia.strzalyDoSzeryfa(sasiad) > 0|| (gracz.gra().historia.bilansSmierci(sasiad))>0) {
                    return new Wydarzenie(Akcja.STRZEL,gracz,sasiad);
                }
            }
        }
        return null;
    }
}

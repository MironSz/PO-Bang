import java.util.Collections;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaPomocnikaSzeryfaZliczajaca extends StrategiaPomocnikaSzeryfa{
    protected Wydarzenie strzela(List<Akcja> reka){

        List<Gracz> osobyWZasiegu=gracz.osobyWZasiegu();
        osobyWZasiegu=gracz.gra().historia.podejrzani(osobyWZasiegu);
        Collections.shuffle(osobyWZasiegu);
        if(osobyWZasiegu.isEmpty()==false)
           return new Wydarzenie(Akcja.STRZEL,gracz,osobyWZasiegu.get(0));
        else
            return  null;
    }
}

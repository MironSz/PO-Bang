import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class StrategiaBandytyDomyslna extends StrategiaBandyty{
    protected Wydarzenie strzela(List<Akcja> reka)
    {
        if(super.strzela(reka)!=null)
            return super.strzela(reka);
        List<Gracz> osobyWZasiegu=gracz.osobyWZasiegu();

        for(Gracz sasiad:osobyWZasiegu)
            if(gracz.gra().historia.czyJestBandytą(gracz,sasiad)==false)
                return new Wydarzenie(Akcja.STRZEL,gracz,sasiad);

        return null;
    }
}

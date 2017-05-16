import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public abstract class Strategia {
    protected Gracz gracz;
    protected abstract boolean wartoRzucacDynamit();
    public Wydarzenie planuj(List<Akcja> reka)
    {
        if(reka.contains(reka.contains(Akcja.ULECZ)))
        {
            if(gracz.otrzymaneObrazenia()!=0)
            {
                return new Wydarzenie(Akcja.ULECZ,gracz,gracz);
            }
        }
        else if(reka.contains(reka.contains(Akcja.ZASIEG_PLUS_DWA)))
        {
            return new Wydarzenie(Akcja.ZASIEG_PLUS_DWA,gracz,gracz);
        }
        else if(reka.contains(reka.contains(Akcja.ZASIEG_PLUSJ_JEDEN)))
        {
            return new Wydarzenie(Akcja.ZASIEG_PLUSJ_JEDEN,gracz,gracz);
        }
        else if(wartoRzucacDynamit()==true)
        {
            return new Wydarzenie(Akcja.DYNAMIT,gracz);
        }
        return null;
    }
}

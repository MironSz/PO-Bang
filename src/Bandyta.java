import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class Bandyta extends Gracz{
    protected List<Bandyta> bandyci;
    public void ustawGrę(Gra gra)
    {
        this.gra=gra;
        bandyci=gra.dodajBandytę(this);
    }
}

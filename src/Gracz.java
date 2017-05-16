import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public abstract class Gracz {
    private  Gra gra;
    private Strategia strategia;
    private List<Akcja> reka;
    public void zagrajTure()
    {
        Wydarzenie ruch=strategia.planuj(reka);
        while(ruch!=null)
        {
            Akcja.zagraj(ruch);
            reka.remove(ruch.akcja);
            ruch=strategia.planuj(reka);
        }
    }
    public void ustawGrę(Gra gra)
    {
        this.gra=gra;
    }
    public Gra gra(){
        return gra;
    }
}

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.max;
import static java.lang.Math.min;

/**
 * Created by Miron on 16.05.2017.
 */
public abstract class Gracz {

    private int zasieg=1;
    private static Random r;
    protected Gra gra;
    private int maxZycie;
    private int życie;
    private Strategia strategia;
    private List<Akcja> reka;
    public List<Gracz> osobyWZasiegu()
    {
        List<Gracz> res=new LinkedList<>();
        int i,j;
        for(i=0,j=gra.gracze().size()-1; (i < zasieg)&& (i<=j); i++) {
            res.add(gra.gracze().get(i));
            if (i != j)
                res.add(gra.gracze().get(j));
        }
        return res;
    }
    public int otrzymaneObrazenia()
    {
        return (maxZycie-życie);
    }
    public boolean jestSzeryfem()
    {
        return false;
    }
    public void zwiekszZasieg(int oIle)
    {
        zasieg+=oIle;
    }
    public void ulecz(){
        życie=min(życie+1,maxZycie);
    }
    public void otrzymajObrażenia(int obrazenia)
    {
        życie=max(0,życie-obrazenia);
    }
    private void sprawdzDynamit()
    {
        if(gra.dynamitWGrze())
        {
            if(r.nextInt(6)+1==1)//wybuchł
            {
                otrzymajObrażenia(3);
                gra.zmianaStanuDynamitu();
            }
        }
    }
    public void zagrajTure()
    {
        sprawdzDynamit();
        if(żyje())
        {
            Wydarzenie ruch=strategia.planuj(reka);
            while(ruch!=null)
            {
                gra.zagraj(ruch);
                reka.remove(ruch.akcja);
                ruch=strategia.planuj(reka);
            }
        }
    }
    public boolean żyje()
    {
        return życie>0;
    }
    public void ustawGrę(Gra gra)
    {
        this.gra=gra;
    }
    public Gra gra(){
        return gra;
    }
    public Gracz lewy()
    {
        return gra.gracze().get(gra.gracze().size()-1);
    }
    public Gracz prawy()
    {
        return gra.gracze().get(0);
    }
}

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
    private static Random r=new Random();
    protected Gra gra;
    protected int maxZycie;
    protected int zycie;
    protected Strategia strategia;
    protected LinkedList<Akcja> reka;
    private LinkedList<Akcja> pierwotnaReka;
    private  int nrGracza;
    public abstract String frakcja();
    public int nrGracza()

    {
        return nrGracza;
    }
    public void setNrGracza(int nr){ nrGracza=nr;}
    protected Gracz(Strategia strategia)
    {
        this.reka=new LinkedList<>();
        this.pierwotnaReka=new LinkedList<>();
        this.strategia=strategia;
        this.maxZycie=r.nextInt(2)+3;
        this.zycie=maxZycie;
        strategia.ustalGracza(this);
    }
    public String wypiszReke()
    {
        return pierwotnaReka.toString();
    }

    private void umrzyj(){
        for(Akcja akcja:reka)
            gra().pula().dodajZagrana(akcja);
    }

    public int zycie() {
        return zycie;
    }

    protected List<Gracz> graczeDoSzeryfa()
    {
        List<Gracz> graczeDoSzeryfa=new LinkedList<>();
        for(Gracz sasiad:gra().gracze())
        {
            graczeDoSzeryfa.add(sasiad);
            if(sasiad.jestSzeryfem())
                break;
        }
        return graczeDoSzeryfa;
    }

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
        return (maxZycie- zycie);
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
        zycie =min(zycie +1,maxZycie);
    }

    public void otrzymajObrażenia(int obrazenia)
    {
        zycie =max(0, zycie -obrazenia);
        if(zyje()==false)
            umrzyj();
    }

    private void sprawdzDynamit()
    {
        if(gra.dynamitWGrze())
        {
            if(r.nextInt(6)+1==1)//wybuchł
            {
                otrzymajObrażenia(3);
                gra().historia.wybuchlDynamit(this);
            }
        }
    }
    public void zagrajTure()
    {
        sprawdzDynamit();
        if(zyje())
        {
            while(reka.size()<5)
                reka.add(gra().pula().dobierz());
           // pierwotnaReka=new LinkedList<>();
           // pierwotnaReka=(LinkedList<Akcja>)reka.clone();
           // for(Akcja akcja:reka) {
           //     pierwotnaReka.add(akcja);
          //  }
            Wydarzenie ruch=strategia.planuj(reka);
            while(ruch!=null)
            {
                gra.zagraj(ruch);
                reka.remove(ruch.akcja);
                ruch=strategia.planuj(reka);
            }
        }
    }
    public boolean zyje()
    {
        return zycie >0;
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

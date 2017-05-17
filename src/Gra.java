import java.util.LinkedList;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 */
public class Gra {


    private Gracz obecnyGracz;
    private boolean dynamitWGrze;
    private Szeryf szeryf;
    private PulaAkcji pula;
    public  Historia historia;
    private List<Gracz> gracze;
    private List<Bandyta> bandyci;
    private Strategia strategia;
    public PulaAkcji pula(){ return pula;}
    public boolean dynamitWGrze(){return dynamitWGrze;}
    public Gra(List<Gracz> gracze,PulaAkcji pulaAkcji) {
        bandyci=new LinkedList<>();
        historia=new Historia(bandyci);
        pula=pulaAkcji;
        this.gracze=gracze;
        for(Gracz gracz:gracze)
            gracz.ustawGrę(this);
    }
    public void zmianaStanuDynamitu(){ dynamitWGrze=dynamitWGrze^true;}
    public List<Bandyta> dodajBandytę(Bandyta bandyta)
    {
        bandyci.add(bandyta);
        return bandyci;
    }

    public Szeryf szeryf(){ return szeryf;}

    public void ustalSzeryfa(Szeryf szeryf){ this.szeryf=szeryf;}

    public void przeprowadzGrę()
    {
        while(szeryf.żyje()&&bandyci.isEmpty()==false)
        {
            obecnyGracz=gracze.get(0);
            gracze.remove(0);

            if(obecnyGracz.żyje())
                obecnyGracz.zagrajTure();

            if(obecnyGracz.żyje())
                gracze.add(obecnyGracz);
            else if(bandyci.contains(obecnyGracz))
                bandyci.remove(obecnyGracz);
        }
    }

    public void zagraj(Wydarzenie wydarzenie)
    {
        historia.dodajWydarzenie(wydarzenie);
        pula.dodajZagrana(wydarzenie.akcja);
        if(wydarzenie.akcja==Akcja.ULECZ)
        {
            wydarzenie.naKim.ulecz();
        }
        else if(wydarzenie.akcja==Akcja.ZASIEG_PLUS_DWA)
        {
            wydarzenie.naKim.zwiekszZasieg(2);
        }
        else if(wydarzenie.akcja==Akcja.ZASIEG_PLUSJ_JEDEN)
        {
            wydarzenie.naKim.zwiekszZasieg(1);
        }
        else if(wydarzenie.akcja==Akcja.DYNAMIT)
        {
            dynamitWGrze=true;
        }
        else if(wydarzenie.akcja==Akcja.STRZEL)
        {
            wydarzenie.naKim.otrzymajObrażenia(1);
            if(wydarzenie.naKim.żyje()==false)
            {
                gracze.remove(wydarzenie.naKim);
            }
        }
        else ;//throw exception
    }
    public List<Gracz> gracze()
    {
        return gracze;
    }

}

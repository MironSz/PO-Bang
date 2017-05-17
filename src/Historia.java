import java.util.LinkedList;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 * Klasa pokazująca aktualny stan wiedzy o innych graczach
 */
public class Historia {
    private List<Wydarzenie> wydarzenia;
    private List<Bandyta> bandyci;
    public Historia(List<Bandyta> bandyci)
    {
        this.bandyci=bandyci;
    }
    public void dodajWydarzenie(Wydarzenie wydarzenie){
        System.out.println(wydarzenie.toString());
        wydarzenia.add(wydarzenie);
    }
    public boolean czyJestBandytą(Gracz pytek, Gracz podejrzany)
    {
        if(bandyci.contains(pytek)||podejrzany.żyje()==false)
            return bandyci.contains(podejrzany);
        return false;
    }
    public int bilansSmierci(Gracz kto)
    {
        int bilans=0;
        for(Wydarzenie wyd:wydarzenia)
        {
            if(wyd.umarł==true&&wyd.kto==kto) {
                if (bandyci.contains(wyd.naKim))
                    bilans++;
                else
                    bilans--;
            }
        }
        return bilans;
    }
    public  int strzalyDoSzeryfa(Gracz kto)
    {
        int bilans=0;
        for(Wydarzenie wyd:wydarzenia)
        {
            if(wyd.akcja==Akcja.STRZEL&&wyd.kto.jestSzeryfem()==true)
                bilans++;
        }
        return bilans;
    }
    public List<Gracz> podejrzani(List<Gracz> gracze)
    {
        List<Gracz> podejrzani=new LinkedList<>();
        for(Gracz gracz:gracze)
        {
            if(bilansSmierci(gracz)<0||strzalyDoSzeryfa(gracz)>0)
                podejrzani.add(gracz);
        }
        return podejrzani;
    }
}

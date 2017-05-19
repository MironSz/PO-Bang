import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 * Klasa pokazująca aktualny stan wiedzy o innych graczach
 */
public class Historia {
    private Gra gra;
    private List<Wydarzenie> wydarzenia;
    private List<Wydarzenie> buforTury;
    private List<Bandyta> bandyci;
    private List<Gracz> gracze;
    private Gracz poprzedniGracz;

    public void wybuchlDynamit(Gracz gracz)
    {
        System.out.println("Dynamit: WYBUCHŁ");
        gra.zmianaStanuDynamitu();
    }
    public Historia(List<Bandyta> bandyci, List<Gracz> gracze,Gra gra)
    {
        this.gracze=gracze;
        this.bandyci=bandyci;
        this.gra=gra;
        wydarzenia=new LinkedList<>();
        buforTury=new LinkedList<>();
        System.out.println("** START\n  Gracze:");
        for(Gracz gracz:gracze)
            System.out.println(gracz.nrGracza()+": "+gracz.frakcja()+" (liczba żyć: "+gracz.zycie()+")");
        System.out.println("\n");
        poprzedniGracz=null;
    }
    private String tab(int n)
    {
        String res="";
        for(int i=0;i<n;i++)
            res+="  ";
        return res;
    }

    public void dodajWydarzenie(Wydarzenie wydarzenie){
       // buforTury.add(wydarzenie);
        wydarzenia.add(wydarzenie);
        if(wydarzenie.kto!=poprzedniGracz)//rozpoczeła się jego tura
        {
            wypiszMartwych(poprzedniGracz);
            poprzedniGracz=wydarzenie.kto;
            if(gra.dynamitWGrze())
                System.out.println("Dynamit: PRZECHODZI DALEJ");
            if(gracze.indexOf(wydarzenie.kto)==0)
            {
                System.out.println("**TURA "+gra.nrTury());
            }
            System.out.println(tab(1)+"GRACZ "+wydarzenie.kto.nrGracza()+" ("+wydarzenie.kto.frakcja()+"):");
            System.out.println(tab(2)+"AKCJE:"+wydarzenie.kto.wypiszReke()+"\n"+tab(2)+"RUCHY:");
        }
        System.out.println(tab(3)+wydarzenie.toString());

    }
    public void zakonczGre()
    {
        boolean bandyciZyja=false;
        boolean szeryfZyje=false;
        for(Gracz gracz:gracze) {
            if (gracz.zyje()) {
                szeryfZyje = szeryfZyje || gracz.jestSzeryfem();
                bandyciZyja = bandyciZyja || bandyci.contains(gracz);
            }

        }
        if(szeryfZyje&&bandyciZyja)
        {
            System.out.println("REMIS - OSIĄGNIĘTO LIMIT TUR");
        }
        else if (szeryfZyje)
        {
            System.out.println("WYFRNANA STRONA: SZERYF i POMOCNICY");
        }
        else if(bandyciZyja)
        {
            System.out.println("WYGRANA STRONA: BANDYCI");
        }
        else
        {
            System.out.println("COŚ SIĘ POPSUŁO I WSZYSCY UMARLI");
        }
    }
    public boolean czyJestBandytą(Gracz pytek, Gracz podejrzany)
    {
        if(bandyci.contains(pytek)||podejrzany.zyje()==false)
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
            if(wyd.akcja==Akcja.STRZEL&&wyd.naKim.jestSzeryfem())
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
    private void wypiszMartwych(Gracz poprzedniGracz)
    {
        boolean drukuj=false;
        for(Gracz gracz:gracze)
        {
            if(gracz==poprzedniGracz)
            {
                drukuj=true;
            }
            else if(drukuj==true&&gracz.zyje()==false)
            {
                System.out.println("GRACZ "+gracz.nrGracza()+" ("+gracz.frakcja()+")\n"+tab(1)+"MARTWY");
            }
        }
    }
}

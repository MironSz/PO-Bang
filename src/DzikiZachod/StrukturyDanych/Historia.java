package DzikiZachod.StrukturyDanych;

import DzikiZachod.Gracze.Gracz;
import DzikiZachod.Gracze.Bandyta;
import DzikiZachod.Gra;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Miron on 16.05.2017.
 * Klasa pokazująca aktualny stan wiedzy o innych graczach
 */
public class Historia {
    private Gra gra;
    private List<Wydarzenie> wydarzenia;
    private List<Gracz> graczePierwotni;
    private List<Gracz> gracze;
    private List<Bandyta> bandyci;
    private Gracz poprzedniGracz;

    public void wybuchlDynamit(Gracz gracz)
    {
        System.out.println("Dynamit: WYBUCHŁ");
        gra.zmianaStanuDynamitu();
    }

    public Historia(List<Bandyta> bandyci, List<Gracz> gracze, Gra gra)
    {
        this.gracze=gracze;
        this.bandyci = new LinkedList<>();
        graczePierwotni = new LinkedList<>();
        for (Bandyta bandyta : bandyci)
            this.bandyci.add(bandyta);
        //graczePierwotni=gracze;

        for (Gracz gracz : gracze)
            graczePierwotni.add(gracz);
        this.gra=gra;
        wydarzenia=new LinkedList<>();

        wypiszStanGraczy();
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
        wydarzenia.add(wydarzenie);
        if (wydarzenie == null)///Wyświetl stan gry.
        {
            wypiszStanGraczy();
            wydarzenia.remove(null);
        } else {
            if (wydarzenie.kto != poprzedniGracz)//rozpoczeła się jego tura
            {
                if (wydarzenie.kto.jestSzeryfem())
                    System.out.println("\n\n** TURA " + gra.nrTury());
                wypiszMartwych(poprzedniGracz);
                poprzedniGracz = wydarzenie.kto;
                if (gra.dynamitWGrze())
                    System.out.println("Dynamit: PRZECHODZI DALEJ");
                System.out.println("\n" + tab(1) + "GRACZ " + wydarzenie.kto.nrGracza() + " (" + wydarzenie.kto.frakcja() + "):");
                System.out.println(tab(2) + "AKCJE:" + wydarzenie.kto.wypiszReke() + "\n" + tab(2) + "RUCHY:");
            }
            System.out.println(tab(3) + wydarzenie.toString());
        }
    }

    private void wypiszStanGraczy() {
        System.out.println(tab(1) + "\nGracze:");
        for (int i = 1; i <= gracze.size() + 1; i++) {
            for (Gracz gracz : graczePierwotni) {
                if (gracz.nrGracza() == i) {
                    System.out.println(gracz.nrGracza() + " " + gracz.frakcja() + " (liczba żyć: " + gracz.zycie() + ")");
                    break;
                }
            }
        }
    }
    public void zakonczGre()
    {
        boolean bandyciZyja=false;
        boolean szeryfZyje=false;
        for (Gracz gracz : graczePierwotni) {
            if (gracz.zyje()) {
                //System.out.println("Żyje:" + gracz.frakcja());
                if (gracz.jestSzeryfem())
                    szeryfZyje = true;
                if (bandyci.contains(gracz))
                    bandyciZyja = true;
            }
        }
        if(szeryfZyje&&bandyciZyja)
        {
            System.out.println("REMIS - OSIĄGNIĘTO LIMIT TUR");
        }
        else if (szeryfZyje)
        {
            System.out.println("WYGRANA STRONA: SZERYF I POMOCNICY");
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
                System.out.println("\n" + tab(1) + "GRACZ " + gracz.nrGracza() + " (" + gracz.frakcja() + ")\n" + tab(2) + "<MARTWY>");
            }
        }
    }
}

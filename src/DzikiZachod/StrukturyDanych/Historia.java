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
    private final Gra gra;
    private final List<Wydarzenie> wydarzenia;
    private final List<Gracz> graczePierwotni;
    private final List<Gracz> gracze;
    private final List<Bandyta> bandyci;
    private Gracz poprzedniGracz;

    /*
        Funkcja odpowiedzialna za wypisanie,
        i zanotowanie, że dynamit wybuchł.
     */
    public void wybuchlDynamit(Gracz gracz)
    {
        wypiszMartwych(poprzedniGracz);
        poprzedniGracz = gracz;
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

        for (Gracz gracz : gracze)
            graczePierwotni.add(gracz);

        this.gra=gra;
        wydarzenia=new LinkedList<>();

        wypiszStanGraczy();
        poprzedniGracz=null;
    }

    /*
        Pomocnicza funkcja, zwracająca stringa składającego
        się z 2*n spacji.
     */
    private String tab(int n) {
        String res="";
        for(int i = 0; i<n; i++)
            res+="  ";
        return res;
    }

    /*
        Dodaje wydarzenie do historii i odpowiada za większość
        wypisywania komunikatów o stanie gry.
     */
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
                System.out.println(tab(1) + "GRACZ " + wydarzenie.kto.nrGracza() + " (" + wydarzenie.kto.frakcja() + "):");
                System.out.println(tab(2) + "AKCJE:" + wydarzenie.kto.wypiszReke() + "\n" + tab(2) + "RUCHY:");
            }
            System.out.println(tab(3) + wydarzenie.toString());
        }
    }

    /*
        Wypisuje listę graczy, zgodnie z specyfikacją z treści.
     */
    private void wypiszStanGraczy() {
        System.out.println(tab(1) + "\nGracze:");
        for (int i = 1; i <= gracze.size() + 1; i++) {
            for (Gracz gracz : graczePierwotni) {
                if (gracz.nrGracza() == i) {
                    if (gracz.zyje())
                        System.out.println(gracz.nrGracza() + ": " + gracz.frakcja() + " (liczba żyć: " + gracz.zycie() + ")");
                    else
                        System.out.println(gracz.nrGracza() + ": X(" + gracz.frakcja() + ")");
                    break;
                }
            }
        }
        System.out.println("\n");
    }

    /*
        Funkcja wywoływana gdy gra dobiegnie końca.
        Drukuje wynik.
     */
    public void zakonczGre() {
        boolean bandyciZyja = false;
        boolean szeryfZyje = false;
        for (Gracz gracz : graczePierwotni) {
            if (gracz.zyje()) {
                //System.out.println("Żyje:" + gracz.frakcja());
                if (gracz.jestSzeryfem())
                    szeryfZyje = true;
                if (bandyci.contains(gracz))
                    bandyciZyja = true;
            }
        }
        System.out.println("** KONIEC");
        if (szeryfZyje && bandyciZyja) {
            System.out.println("REMIS - OSIĄGNIĘTO LIMIT TUR");
        } else if (szeryfZyje) {
            System.out.println("WYGRANA STRONA: SZERYF I POMOCNICY");
        } else if (bandyciZyja) {
            System.out.println("WYGRANA STRONA: BANDYCI");
        } else {
            System.out.println("COŚ SIĘ POPSUŁO I WSZYSCY UMARLI");
        }
    }

    /*
        Zwraca stan wiedzy o graczu.
        Wszyscy wiedzą, że martwy bandyta jest bandytą.
        Bandyci wiedzą, kto jest bandytą.
        Gdy gracz nie zna frakcji podejrzanego, funkcja zwróci
        false=> pytek dostanie informację, że podejrzany jest
        pomocnikiem szeryfa. Nie użyta w implementacji, ale mogłaby
        być użyteczna w dalszej rozbudowie programu
    */
    public boolean czyJestBandytą(Gracz pytek, Gracz podejrzany) {
        if (bandyci.contains(pytek) || !podejrzany.zyje())
            return bandyci.contains(podejrzany);
        return false;
    }

    private int bilansSmierci(Gracz kto)
    {
        int bilans=0;
        for(Wydarzenie wyd:wydarzenia)
        {
            if (wyd.umarł && wyd.kto == kto) {
                if (bandyci.contains(wyd.naKim))
                    bilans++;
                else
                    bilans--;
            }
        }
        return bilans;
    }

    /*
        Zwraca ile dany gracz oddał strzałów do szeryfa.
     */
    public  int strzalyDoSzeryfa(Gracz kto) {
        int bilans=0;
        for(Wydarzenie wyd:wydarzenia) {
            if(wyd.akcja==Akcja.STRZEL&&wyd.naKim.jestSzeryfem())
                bilans++;
        }
        return bilans;
    }

    /*
        Zwraca listę podejrzanych o bycie bandytą, zgodnie z strategią zliczającą.
     */
    public List<Gracz> podejrzani(List<Gracz> gracze) {
        List<Gracz> podejrzani=new LinkedList<>();
        for(Gracz gracz:gracze) {
            if(bilansSmierci(gracz)<0||strzalyDoSzeryfa(gracz)>0)
                podejrzani.add(gracz);
        }
        return podejrzani;
    }

    /*
        Funkcja odpowiedzialna za wydrukowanie martwych
        graczy od poprzedniego gracza który wykonał akcję do obecnego.
     */
    private void wypiszMartwych(Gracz poprzedniGracz) {
        boolean drukuj=false;
        for(Gracz gracz:gracze) {
            if(gracz==poprzedniGracz) {
                drukuj=true;
            } else if (drukuj && !gracz.zyje()) {
                System.out.println(tab(1) + "GRACZ " + gracz.nrGracza() + " (" + gracz.frakcja() + ")\n" + tab(2) + "<MARTWY>" + "\n");
            }
        }
    }
}

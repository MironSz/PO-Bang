package DzikiZachod;

import DzikiZachod.Gracze.Bandyta;
import DzikiZachod.Gracze.Gracz;
import DzikiZachod.Gracze.Szeryf;
import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.StrukturyDanych.Historia;
import DzikiZachod.StrukturyDanych.PulaAkcji;
import DzikiZachod.StrukturyDanych.Wydarzenie;

import java.util.Collections;
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
    private Historia historia;
    private List<Gracz> gracze;
    private List<Bandyta> bandyci;
    private int nrTury=0;
    private Gracz pierwszyGracz;

    /*
        Zwraca historię rozgrywki.
     */
    public Historia historia() {
        return historia;
    }

    /*
        Zwraca numer obecnej tury.
     */
    public int nrTury() {   return nrTury;}

    /*
        Zwraca pulę akcji.
     */
    public PulaAkcji pula(){ return pula;}

    /*
        Zwraca czy dynamit znajduje się obecnie na stole.
     */
    public boolean dynamitWGrze(){  return dynamitWGrze;}

    /*
        Wywoływana w trakcie zagrania lub wybuchu dynamitu.
        Pamięta czy dynamit znajduje się obecnie w grze.
     */
    public void zmianaStanuDynamitu() {
        dynamitWGrze = !dynamitWGrze;
    }

    /*
        Funkcja odpowiedzialna za inicjację rozgrywki.
     */
    public void rozgrywka(List<Gracz> gracze, PulaAkcji pulaAkcji) {
        int nr=1;

        bandyci=new LinkedList<>();
        pula=pulaAkcji;
        this.gracze=gracze;
        Collections.shuffle(this.gracze);
        for(Gracz gracz:gracze) {
            if(gracz.jestSzeryfem())
                this.szeryf=(Szeryf)gracz;
            gracz.ustawGrę(this);
            gracz.setNrGracza(nr);
            nr++;
        }
        while (gracze.get(0) != szeryf) {
            Gracz pomoc = gracze.get(0);
            gracze.remove(0);
            gracze.add(pomoc);
        }
        pierwszyGracz=gracze.get(0);
        historia=new Historia(bandyci,gracze,this);
        przeprowadzGrę();
    }

    /*
        Funkcja wywoływana przez bandytę, gdy ten zgłasza się,
        że jest bandytą. Dodaje bandytę do listy bandytów.
     */
    public List<Bandyta> dodajBandytę(Bandyta bandyta) {
        bandyci.add(bandyta);
        return bandyci;
    }

    /*
        Usuwa bandytę z listy bandytów, po jego śmierci.
     */
    public void usunBandyte(Gracz gracz) {
        bandyci.remove(gracz);
    }
    /*
        Zwraca gracza będącego szeryfem.
     */
    public Szeryf szeryf(){ return szeryf;}

    /*
        Zwraca czy gra dobiegła końca.
     */
    public boolean graDobieglaKonca() {
        return !(bandyci.size() > 0 && szeryf.zyje() && nrTury < 42);
    }
    /*
       Przeprowadza grę.
     */
    private void przeprowadzGrę() {
        while (!graDobieglaKonca()) {
            obecnyGracz=gracze.get(0);
            gracze.remove(0);

            if(obecnyGracz==pierwszyGracz)
                nrTury++;

            if(obecnyGracz.zyje())
                obecnyGracz.zagrajTure();

            gracze.add(gracze.size(),obecnyGracz);
        }
        historia.zakonczGre();
    }
    /*
        Funkcja wywoływana gdy gracz zagra akcję.
        Odpowiada za obsługę tego.
     */
    public void zagrajAkcje(Wydarzenie wydarzenie) {
        historia.dodajWydarzenie(wydarzenie);
        if (wydarzenie != null) {
            pula.dodajZagrana(wydarzenie.akcja);
            if (wydarzenie.akcja == Akcja.ULECZ) {
                wydarzenie.naKim.ulecz();
            } else if (wydarzenie.akcja == Akcja.ZASIEG_PLUS_DWA) {
                wydarzenie.kto.zwiekszZasieg(2);
            } else if (wydarzenie.akcja == Akcja.ZASIEG_PLUS_JEDEN) {
                wydarzenie.kto.zwiekszZasieg(1);
            } else if (wydarzenie.akcja == Akcja.DYNAMIT) {
                dynamitWGrze = true;
            } else if (wydarzenie.akcja == Akcja.STRZEL) {
                wydarzenie.naKim.otrzymajObrazenia(1);
                if (!wydarzenie.naKim.zyje()) {
                    wydarzenie.naKim.umrzyj();
                }
            }
        } else if (wydarzenie == null) {

        }
        //throw exception
    }

    /*
        Zwraca listę graczy.
     */
    public List<Gracz> gracze() {
        return gracze;
    }

}

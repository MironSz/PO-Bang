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

    private int iloscBandytow;
    private Gracz obecnyGracz;
    private boolean dynamitWGrze;
    private Szeryf szeryf;
    private PulaAkcji pula;
    public Historia historia;
    private List<Gracz> gracze;
    private List<Gracz> wszyscyGracze;
    private List<Bandyta> bandyci;
    private int nrTury=0;
    private Gracz pierwszyGracz;

    public int nrTury() {   return nrTury;}

    public PulaAkcji pula(){ return pula;}

    public boolean dynamitWGrze(){  return dynamitWGrze;}

    public void zmianaStanuDynamitu(){ dynamitWGrze=dynamitWGrze^true;}

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
        wszyscyGracze = gracze;
        pierwszyGracz=gracze.get(0);
        historia=new Historia(bandyci,gracze,this);
        iloscBandytow = bandyci.size();
        przeprowadzGrę();
    }

    public List<Bandyta> dodajBandytę(Bandyta bandyta) {
        bandyci.add(bandyta);
        return bandyci;
    }

    public Szeryf szeryf(){ return szeryf;}

    public void ustalSzeryfa(Szeryf szeryf){ this.szeryf=szeryf;}

    public void przeprowadzGrę() {
        while (szeryf.zyje() && bandyci.size() > 0 && nrTury <= 42) {
            obecnyGracz=gracze.get(0);
            gracze.remove(0);
            if(obecnyGracz==pierwszyGracz)
                nrTury++;
            if(obecnyGracz.zyje())
                obecnyGracz.zagrajTure();


            gracze.add(gracze.size(),obecnyGracz);
            if (obecnyGracz.zyje() == false)
                if (bandyci.contains(obecnyGracz))
                    bandyci.remove(obecnyGracz);


        }
        historia.zakonczGre();
    }

    public void zagraj(Wydarzenie wydarzenie) {
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
                wydarzenie.naKim.otrzymajObrażenia(1);
                if (wydarzenie.naKim.zyje() == false) {
                    wydarzenie.naKim.umrzyj();
                }
            }
        } else if (wydarzenie == null) {

        }
        ;//throw exception
    }

    public List<Gracz> gracze() {
        return gracze;
    }

}

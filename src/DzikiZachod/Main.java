package DzikiZachod;

import DzikiZachod.Gracze.*;
import DzikiZachod.Gracze.Strategie.StrategiePomocnikaSzeryfa.PomocnikSzeryfa;
import DzikiZachod.Gracze.Strategie.SretegieBandyty.StrategiaBandytyCierpliwa;
import DzikiZachod.Gracze.Strategie.StrategiePomocnikaSzeryfa.StrategiaPomocnikaSzeryfaZliczajaca;
import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.StrukturyDanych.PulaAkcji;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miron on 18.05.2017.
 */
public class Main {
    public static void main(String[] Args) {

        List<Gracz> gracze = new ArrayList<Gracz>();
        gracze.add(new Szeryf());
        for (int i = 0; i < 4; i++) gracze.add(new PomocnikSzeryfa());
        for (int i = 0; i < 3; i++) gracze.add(new Bandyta());
        gracze.add(new PomocnikSzeryfa(new StrategiaPomocnikaSzeryfaZliczajaca()));
        gracze.add(new Bandyta(new StrategiaBandytyCierpliwa()));

// Kod wspólny dla obu wersji:
        PulaAkcji pulaAkcji = new PulaAkcji();
        pulaAkcji.dodaj(Akcja.ULECZ, 29);
        pulaAkcji.dodaj(Akcja.STRZEL, 68);
        pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_JEDEN, 9);
        pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_DWA, 3);
        pulaAkcji.dodaj(Akcja.DYNAMIT, 1);

        Gra gra = new Gra();
        gra.rozgrywka(gracze, pulaAkcji);
    }
}

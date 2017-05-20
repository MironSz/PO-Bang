package DzikiZachod.Gracze;

import DzikiZachod.Gra;
import DzikiZachod.Gracze.Strategie.Strategia;
import DzikiZachod.StrukturyDanych.Akcja;
import DzikiZachod.StrukturyDanych.Wydarzenie;

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
    private static final Random r = new Random();
    Gra gra;
    int maxZycie;
    int zycie;
    private final Strategia strategia;
    private final LinkedList<Akcja> reka;
    private LinkedList<Akcja> pierwotnaReka;
    private  int nrGracza;

    /*
        Zwraca stringa z frakcją gracza.
     */
    public abstract String frakcja();

    /*
        Zwraca numer gracz w rozgrywce.
     */
    public int nrGracza()

    {
        return nrGracza;
    }

    /*
        Ustawia graczowi jego numer w rozgrywce.
     */
    public void setNrGracza(int nr){ nrGracza=nr;}

    /*
        Konstruktor gracza przyjmujący za parametr strategię.
     */
    Gracz(Strategia strategia) {
        this.reka=new LinkedList<>();
        this.pierwotnaReka=new LinkedList<>();
        this.strategia=strategia;
        this.maxZycie=r.nextInt(2)+3;
        this.zycie=maxZycie;
        strategia.ustalGracza(this);
    }

    /*
        Zwraca stringa, listę akcji na ręce.
     */
    public String wypiszReke() {
        return pierwotnaReka.toString();
    }

    /*
        Funkcja odpowiada za wszystkie akcje powiązane ze śmiercią gracza.
     */
    public void umrzyj(){
        for(Akcja akcja:reka)
            gra().pula().dodajZagrana(akcja);
    }

    /*
        Zwraca obecny stan życia.
     */
    public int zycie() {
        return zycie;
    }

    /*
        Zwraca listę osób pomiędzy obecnym graczem a szeryfem.
     */
    public List<Gracz> graczeDoSzeryfa() {
        List<Gracz> graczeDoSzeryfa=new LinkedList<>();
        for(Gracz sasiad:gra().gracze()) {
            if(sasiad.zyje())
                graczeDoSzeryfa.add(sasiad);
            if(sasiad.jestSzeryfem())
                break;
        }
        return graczeDoSzeryfa;
    }

    /*
        Zwraca listę osób do ktorych gracz może strzelić.
     */
    public List<Gracz> osobyWZasiegu() {
        List<Gracz> res=new LinkedList<>();
        int i,j;
        for(i=0,j=0; i<zasieg&&j< gra().gracze().size(); j++) {
            if(gra().gracze().get(j).zyje()) {
                i++;
                res.add(gra().gracze().get(j));
            }
        }
        for (i = 0, j = gra().gracze().size() - 1; i < zasieg && j >= 0; j--) {
            if(gra().gracze().get(j).zyje()) {
                i++;
                res.add(gra().gracze().get(j));
            }
        }
        return res;
    }

    /*
        Zwraca różnicę sumy otrzymanych obrażen i sumy uleczeń w grze.
     */
    public int otrzymaneObrazenia() {
        return (maxZycie- zycie);
    }

    /*
        Zwraca czy gracz jest szeryfem.
     */
    public boolean jestSzeryfem() {
        return false;
    }

    /*
        Funkcja odpowiada za zwiększenie zasięgu,
        zasięg można zwiększać dowolnie wiele razy.
     */
    public void zwiekszZasieg(int oIle) {
        zasieg+=oIle;
    }

    /*
        Funkcja odpowiada za uleczenie gracza.
     */
    public void ulecz(){
        if(zyje())
            zycie =min(zycie +1,maxZycie);
        else
            System.out.println("ERROR: LECZE TRUPA");
    }

    /*
        Funkcja odpowiada za przyjęcie obrażen
        i ewentualną śmierć gracz.
     */
    public void otrzymajObrazenia(int obrazenia) {
        zycie =max(0, zycie -obrazenia);
        if (!zyje())
            umrzyj();
    }

    /*
        Zwraca stan wiedzy o frakcji gracza.
        Wszyscy wiedzą, że martwy bandyta jest bandytą.
        Bandyci wiedzą, kto jest bandytą.
        Gdy gracz nie zna frakcji podejrzanego, funkcja zwróci
        false=> pytek dostanie informację, że podejrzany jest
        pomocnikiem szeryfa. Nie użyta w implementacji, ale mogłaby
        być użyteczna w dalszej rozbudowie programu
     */
    protected final boolean czyJestBandyta(Gracz podejrzany) {
        return gra().historia().czyJestBandytą(this, podejrzany);
    }

    /*
        Sprawdza czy dynamit wybucha.
        Wykonuje wszystkie powiązane z ewentualnym
        wybuchem operacje.
     */
    private void sprawdzDynamit() {
        if(gra.dynamitWGrze()) {
            if(r.nextInt(6)+1==1)//wybuchł
            {
                otrzymajObrazenia(3);
                gra().historia().wybuchlDynamit(this);
            }
        }
    }

    /*
        Wykonuje całą turę gracza.
     */
    public void zagrajTure() {
        sprawdzDynamit();
        if(zyje()) {
            while(reka.size()<5)
                reka.add(gra().pula().dobierzAkcje());

            pierwotnaReka=(LinkedList<Akcja>)reka.clone();

            Wydarzenie ruch=strategia.planuj(reka);

            while(ruch!=null) {
                gra.zagrajAkcje(ruch);
                reka.remove(ruch.akcja);
                ruch=strategia.planuj(reka);
            }
            gra.zagrajAkcje(null);
        }
    }

    /*
        Zwraca czy gracz żyje w obecnym momencie gry.
     */
    public boolean zyje() {
        return zycie >0;
    }

    /*
        Zapisuje w której grze bierze gracz udział.
     */
    public void ustawGrę(Gra gra) {
        this.gra=gra;
    }

    /*
        Zwraca w którą grę gracz gra.
    */
    public Gra gra(){
        return gra;
    }

    /*
        Zwraca pierwszego żywego gracza po lewej.
     */
    public Gracz lewy() {
        for(int i = gra().gracze().size()-1; i>=0; i--)
            if(gra().gracze().get(i).zyje())
                return gra().gracze().get(i);
        System.out.println("funkcja lewy nie działa");
        return null;
    }

    /*
        Zwraca pierwszego żywego gracza po prawej.
     */
    public Gracz prawy() {
        for(int i = 0; i<=gra().gracze().size()-1; i++)
            if(gra().gracze().get(i).zyje())
                return gra().gracze().get(i);
        System.out.println("funkcja prawy nie działa");
        return null;
    }

}

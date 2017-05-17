import java.lang.reflect.AccessibleObject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Miron on 16.05.2017.
 */
public class PulaAkcji {
    private List<Akcja> pula;
    private List<Akcja> dek;
    private List<Akcja> zagrane;
    private boolean dynamit;
    public Akcja dobierz()
    {
        if(dek.isEmpty())
            tosuj();
        Akcja akcja=dek.get(0);
        dek.remove(akcja);
        return akcja;
    }
    public void dodajZagrana(Akcja akcja)
    {
        if(akcja!=Akcja.DYNAMIT)
            zagrane.add(akcja);
    }
    public void dodaj(Akcja akcja, int ilosc)
    {
        for(int i=0;i<ilosc;i++)
            pula.add(akcja);
    }

    public PulaAkcji()
    {
        this.pula=new LinkedList<>();
    }

    public void przygotujDoRozgrywki()
    {
        zagrane=new LinkedList<>();
        dek=new LinkedList<>();
        for(Akcja akcja:pula)
            dek.add(akcja);
        Collections.shuffle(dek,new Random());
    }

    public void tosuj()
    {
        dek=zagrane;
        zagrane=new LinkedList<>();
        Collections.shuffle(dek,new Random());
    }


}

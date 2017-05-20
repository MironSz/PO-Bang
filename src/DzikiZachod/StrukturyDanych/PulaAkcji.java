package DzikiZachod.StrukturyDanych;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Miron on 16.05.2017.
 */
public class PulaAkcji {
    private final List<Akcja> pula;
    private List<Akcja> dek;
    private List<Akcja> stosOdrzuconych;
    private boolean dynamit;
    public PulaAkcji()
    {
        this.pula=new LinkedList<>();
        this.dek=new LinkedList<>();
        this.stosOdrzuconych = new LinkedList<>();
    }

    /*
        Zwraca akcję ze deku kart. Gdy dek jest pusty,
        przetosowuje stos odrzuconych kart.
        Zakłada, że gra posiada dość kart dla wszystkich graczy.
     */
    public Akcja dobierzAkcje()
    {
        if(dek.isEmpty())
            tosujStosOdrzuconych();
        Akcja akcja=dek.get(0);
        dek.remove(akcja);
        return akcja;
    }

    /*
        Dodaje zagraną akcje do stosu odrzuconych kart.
     */
    public void dodajZagrana(Akcja akcja) {
        if(akcja!=Akcja.DYNAMIT)
            stosOdrzuconych.add(akcja);
    }

    /*
        Dodaje do puli nową akcję.
     */
    public void dodaj(Akcja akcja, int ilosc) {
        for(int i = 0; i<ilosc; i++) {
            pula.add(akcja);
            stosOdrzuconych.add(akcja);
        }
    }

    /*
        Tosuje odrzucone karty i dodaje je do deku.
     */
    private void tosujStosOdrzuconych()
    {
        dek = stosOdrzuconych;
        stosOdrzuconych = new LinkedList<>();
        Collections.shuffle(dek,new Random());
    }


}

/**
 * Created by Miron on 16.05.2017.
 */
public class Wydarzenie {
    public final Akcja akcja;
    public final Gracz naKim;
    public final Gracz kto;
    public Wydarzenie(Akcja akcja,Gracz kto)
    {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=null;
    }
    public Wydarzenie(Akcja akcja,Gracz kto,Gracz naKim)
    {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=naKim;
    }
}

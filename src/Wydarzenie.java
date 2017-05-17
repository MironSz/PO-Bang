/**
 * Created by Miron on 16.05.2017.
 */
public class Wydarzenie {
    public final Akcja akcja;
    public final Gracz naKim;
    public final Gracz kto;
    public final boolean umarł;
    public Wydarzenie(Akcja akcja,Gracz kto)
    {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=null;
        umarł=false;
    }
    public Wydarzenie(Akcja akcja,Gracz kto,Gracz naKim)
    {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=naKim;
        umarł=false;
    }
    public  Wydarzenie(Akcja akcja,Gracz kto,Gracz naKim,boolean umarł)
    {
        this.akcja=akcja;
        this.kto=kto;
        this.naKim=naKim;
        this.umarł=umarł;
    }
    public String toString()
    {
        if(naKim!=null)
            return "           "+akcja.toString()+" "+kto.toString();
        else
            return "           "+akcja.toString();
    }
}

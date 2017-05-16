/**
 * Created by Miron on 16.05.2017.
 */

public enum Akcja {
    ULECZ,
    STRZEL,
    ZASIEG_PLUSJ_JEDEN,
    ZASIEG_PLUS_DWA,
    DYNAMIT;
    static void zagraj(Wydarzenie wydarzenie)
    {
        wydarzenie.kto.gra().historia.dodajWydarzenie(wydarzenie);
        if(wydarzenie.akcja==ULECZ)
        {

        }
        else if(wydarzenie.akcja==ZASIEG_PLUS_DWA)
        {

        }
        else if(wydarzenie.akcja==ZASIEG_PLUSJ_JEDEN)
        {

        }
        else if(wydarzenie.akcja==DYNAMIT)
        {

        }
        else if(wydarzenie.akcja==STRZEL)
        {

        }
        else ;//throw exception
    }

}

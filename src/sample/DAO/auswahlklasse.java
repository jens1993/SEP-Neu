package sample.DAO;

import sample.Spieler;
import sample.Turnier;
import sample.Verein;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by jens on 03.08.2017.
 */
public class auswahlklasse
{
    private Dictionary<Integer, Turnier> turnierliste = new Hashtable<Integer,Turnier>();
    private Dictionary<Integer, Verein> vereine = new Hashtable<Integer,Verein>();
    private Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();
    TurnierDAO t = new TurnierDAOimpl();

    static Turnier aktuelleTurnierAuswahl = null;

    public Turnier getAktuelleTurnierAuswahl()
    {
        return aktuelleTurnierAuswahl;
    }

    public Dictionary<Integer, Turnier> getTurnierliste() {
        turnierliste=t.getAllTurniere();
        return turnierliste;
    }
    public Dictionary<Integer, Verein> getVereine() {
        vereine=aktuelleTurnierAuswahl.getVereine();
        return vereine;
    }
    public Dictionary<Integer, Spieler> getSpieler() {
        spieler=aktuelleTurnierAuswahl.getSpieler();
        return spieler;
    }



    public void setTurnierliste(Dictionary<Integer, Turnier> turnierliste) {
        this.turnierliste = turnierliste;
    }



    public void setVereine(Dictionary<Integer, Verein> vereine) {
        this.vereine = vereine;
    }



    public void setSpieler(Dictionary<Integer, Spieler> spieler) {
        this.spieler = spieler;
    }

    public void turnierAuswahlSpeichern (Turnier turnier)
    {
        aktuelleTurnierAuswahl = turnier;
    }


}

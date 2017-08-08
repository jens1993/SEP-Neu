package sample.DAO;

import sample.*;

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
    private Dictionary<Integer, Spielklasse> spielklassen = new Hashtable<Integer,Spielklasse>();
    TurnierDAO turnierDAO = new TurnierDAOimpl();
    //SpielerDAO spielerDAO = new SpielerDAOimpl();
    //private SpielklasseDAO spielklasseDAO=new SpielklasseDAOimpl();
    private static Spielklasse aktuelleSpielklassenAuswahl = null;
    private static Turnier aktuelleTurnierAuswahl = null;



    public static Spielklasse getAktuelleSpielklassenAuswahl() {


        return aktuelleSpielklassenAuswahl;
    }

    public static void setAktuelleSpielklassenAuswahl(Spielklasse aktuelleSpielklassenAuswahl) {
        auswahlklasse.aktuelleSpielklassenAuswahl = aktuelleSpielklassenAuswahl;
    }


    /*public SpielklasseDAO getSpielklasseDAO() {
        return spielklasseDAO;
    }*/




    public static void setAktuelleTurnierAuswahl(Turnier aktuellesTurnier) {
        aktuelleTurnierAuswahl = aktuellesTurnier;
    }


    public void addSpieler(Spieler sp) {
        spieler.put(sp.getSpielerID(), sp);
    }
    public void UpdateSpieler(Spieler sp) {
        spieler.put(sp.getSpielerID(),sp);
    }


    /*public SpielerDAO getSpielerDAO() {
        return spielerDAO;
    }
    */




    public TurnierDAO getTurnierDAO() {
        return turnierDAO;
    }

    public void setTurnierDAO(TurnierDAO turnierDAO) {
        this.turnierDAO = turnierDAO;
    }



    public Turnier getAktuelleTurnierAuswahl()
    {
        return aktuelleTurnierAuswahl;
    }

    public Dictionary<Integer, Turnier> getTurnierliste() {
        turnierliste= turnierDAO.getAllTurniere();
        return turnierliste;
    }
    public Dictionary<Integer, Verein> getVereine() {
        vereine=aktuelleTurnierAuswahl.getVereine();
        return vereine;
    }
    /*public Dictionary<Integer, Spielklasse> getSpielklasseDict() {
        spielklassen=spielklasseDAO.getAllSpielklassenDict();
        return spielklassen;
    }*/
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
    public void spielklassenAuswahlSpeichern (Spielklasse spielklasse)
    {
        aktuelleSpielklassenAuswahl = spielklasse;
    }


}

package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import sample.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by jens on 03.08.2017.
 */
public class auswahlklasse
{
    private TurnierDAO turnierDAO = new TurnierDAOimpl();
    private static Dictionary<Integer, Turnier> turnierliste = new Hashtable<Integer,Turnier>();
    private static Dictionary<Integer, Verein> vereine = new Hashtable<Integer,Verein>();
    private static Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();
    private static Spielklasse aktuelleSpielklassenAuswahl = null;
    private static Turnier aktuelleTurnierAuswahl = null;
    private static Spieler SpielerzumHinzufeuegen=null;
    private static ArrayList<Spieler> vorhandeneSpieler;
    private static ArrayList<Stage> stages = new ArrayList<>();
    private static Spiel SpielAuswahlErgebniseintragen;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();


    public auswahlklasse() {
        turnierliste= turnierDAO.getAllTurniere();
    }

    public static Spiel getSpielAuswahlErgebniseintragen() {
        return SpielAuswahlErgebniseintragen;
    }

    public static void setSpielAuswahlErgebniseintragen(Spiel spielAuswahlErgebniseintragen) {
        SpielAuswahlErgebniseintragen = spielAuswahlErgebniseintragen;
    }

    public static ObservableList<Spieler> getObs_spieler() {
        return obs_spieler;
    }

    public static int getSprachid() {
        return sprachid;
    }

    public static void setSprachid(int sprachid) {
        auswahlklasse.sprachid = sprachid;
    }

    private static int sprachid;
    public static void setUpdateSpieler(Spieler updateSpieler) {
        auswahlklasse.updateSpieler = updateSpieler;
    }

    public static Spieler getUpdateSpieler() {
        return updateSpieler;
    }

    public static int getTab() {
        return tab;
    }

    public static void setTab(int tab) {
        auswahlklasse.tab = tab;
    }

    private static Spieler updateSpieler;
    private static int tab;

    public static void setTabAuswahl(Spieler spieler, int tabspeichern)
    {
        updateSpieler=spieler;
        tab=tabspeichern;
        stages.get(0).close();

    }


    public static ArrayList<Spieler> getVorhandeneSpieler() {
        return vorhandeneSpieler;
    }

    public static void setVorhandeneSpieler(ArrayList<Spieler> vorhandeneSpieler) {
        auswahlklasse.vorhandeneSpieler = vorhandeneSpieler;
    }

    public static ArrayList<Stage> getStages() {
        return stages;
    }

    public static void addStage(Stage stage){
        auswahlklasse.stages.add(0,stage);
    }

    public static Spieler getSpielerzumHinzufeuegen() {
        return SpielerzumHinzufeuegen;
    }

    public static void setSpielerzumHinzufeuegen(Spieler spielerzumHinzufeuegen) {
        SpielerzumHinzufeuegen = spielerzumHinzufeuegen;
    }

    public static Spielklasse getAktuelleSpielklassenAuswahl() {
        return aktuelleSpielklassenAuswahl;
    }

    public static void setAktuelleSpielklassenAuswahl(Spielklasse aktuelleSpielklassenAuswahl) {
        auswahlklasse.aktuelleSpielklassenAuswahl = aktuelleSpielklassenAuswahl;
    }

    public static void setAktuelleTurnierAuswahl(Turnier aktuellesTurnier) {
        aktuelleTurnierAuswahl = aktuellesTurnier;
    }

    public static void addSpieler(Spieler sp) {
        spieler.put(sp.getSpielerID(), sp);
    }

    public static Turnier getAktuelleTurnierAuswahl()
    {
        return aktuelleTurnierAuswahl;
    }

    public static Dictionary<Integer, Turnier> getTurnierliste() {
        return turnierliste;
    }
    public static Dictionary<Integer, Verein> getVereine() {
        vereine=aktuelleTurnierAuswahl.getVereine();
        return vereine;
    }
    public static void addVerein(Verein verein) {
        vereine.put(verein.getVereinsID(),verein);
    }
    /*public Dictionary<Integer, Spielklasse> getSpielklasseDict() {
        spielklassen=spielklasseDAO.getAllSpielklassenDict();
        return spielklassen;
    }*/
    public static Dictionary<Integer, Spieler> getSpieler() {
        spieler=aktuelleTurnierAuswahl.getSpieler();
        return spieler;
    }



    public static void setSpieler(Dictionary<Integer, Spieler> spieler) {
        auswahlklasse.spieler = spieler;
    }

    public static void turnierAuswahlSpeichern (Turnier turnier)
    {
        aktuelleTurnierAuswahl = turnier;
    }

    public static void spielklassenAuswahlSpeichern (Spielklasse spielklasse)
    {
        aktuelleSpielklassenAuswahl = spielklasse;
    }


}

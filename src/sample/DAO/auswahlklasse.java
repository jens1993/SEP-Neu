package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
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
    private static Dictionary<String, Stage> stagesdict = new Hashtable<String,Stage>();
    private static Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();
    private static Spielklasse aktuelleSpielklassenAuswahl = null;
    private static Turnier aktuelleTurnierAuswahl = null;
    private static Spieler SpielerzumHinzufeuegen=null;
    private static ArrayList<Spieler> vorhandeneSpieler;

    private static Spiel SpielAuswahlErgebniseintragen;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();
    private static Notifications noteficationBuilder;

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


    }


    public static ArrayList<Spieler> getVorhandeneSpieler() {
        return vorhandeneSpieler;
    }

    public static void setVorhandeneSpieler(ArrayList<Spieler> vorhandeneSpieler) {
        auswahlklasse.vorhandeneSpieler = vorhandeneSpieler;
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
        return vereine;
    }
    public static void addVerein(Verein verein) {
        vereine.put(verein.getVereinsID(),verein);
    }



    public static Dictionary<String, Stage> getStagesdict() {

        return stagesdict;
    }

    public static void addStagesdict(Stage stage, String string) {
        stagesdict.put(string,stage);
    }
    /*public Dictionary<Integer, Spielklasse> getSpielklasseDict() {
        spielklassen=spielklasseDAO.getAllSpielklassenDict();
        return spielklassen;
    }*/
    public static Dictionary<Integer, Spieler> getSpieler() {
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
    public static void InfoBenachrichtigung(String titel, String text)
    {
         noteficationBuilder = Notifications.create()
                .title(titel)
                .text(text)

                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Klick auf die Notifiaction");
                    }
                });
        noteficationBuilder.showInformation();
    }
    public static void WarnungBenachrichtigung(String titel, String text)
    {
        noteficationBuilder = Notifications.create()
                .title(titel)
                .text(text)

                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Klick auf die Notifiaction");
                    }
                });
        noteficationBuilder.showWarning();
    }



}

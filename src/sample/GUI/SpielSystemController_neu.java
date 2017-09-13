package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import sample.DAO.*;
import sample.Spieler;
import sample.Spielklasse;
import sample.Spielsysteme.Gruppe;
import sample.Spielsysteme.GruppeMitEndrunde;
import sample.Spielsysteme.KO;
import sample.Spielsysteme.Spielsystem;
import sample.Team;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Manuel Hüttermann on 04.08.2017.
 */

public class SpielSystemController_neu implements Initializable
{
    TableColumn<Team,Integer> setzplatz = new TableColumn("Setzplatz");
    //Dictionary<Integer, Team> dicttest = new Hashtable<>();
    @FXML
    private Tab tabsperst;

    @FXML
    private Menu menu_rlp;
    //Tab1
    @FXML
    private TextField t_suchleistespieler;
    @FXML
    private TextField t_suchleistesetzliste;
    @FXML
    private Label l_meldungsetzliste1;

    @FXML
    private TextField textField_anzahlWeiterkommender;
    @FXML
    private TextField textField_gruppenGroesse;

    @FXML
    private TableView spielsystem_spielerliste_alleSpieler;
    @FXML
    private TableView spielsystem_setzliste;
    @FXML
    private Button btnentf;

    //Tab2
    @FXML
    private Button btn_spielklasseStarten;
    @FXML
    private RadioButton radio_gruppe;

    @FXML
    private RadioButton radio_gruppeMitE;

    @FXML
    private RadioButton radio_ko;

    @FXML
    private RadioButton radio_schweizer;

    @FXML
    private RadioButton radio_trostJa;

    @FXML
    private RadioButton radio_trostNein;

    @FXML
    private AnchorPane gruppe;

    @FXML
    private AnchorPane gruppeMitEndrunde;

    @FXML
    private AnchorPane koSystem;

    @FXML
    private AnchorPane schweizerSystem;

    @FXML
    private AnchorPane koTrostRundeJa;

    @FXML
    private AnchorPane koTrostRundeNein;

    @FXML
    private RadioButton radio_platzDreiAusspielen;

    private ArrayList<Team> team_setzliste = new ArrayList<>();

    Dictionary<Integer,Spielklasse> turnierauswahlspielklassendict = null;
    Spielklasse ausgewaehlte_spielklasse=  auswahlklasse.getAktuelleSpielklassenAuswahl();
    Spieler spieler_m1=null;
    Spieler spieler_m2=null;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();
    private static ObservableList<Team> obs_setzliste =  FXCollections.observableArrayList();
    private static boolean befuellem1=true;
    private static Team teams = null;
    private SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
    Team team = new Team();

    //Linke Tabelle füllen beim Laden
    private void fuelleobs_setzliste() {
        obs_setzliste.clear();


        Enumeration enumTeams = auswahlklasse.getAktuelleTurnierAuswahl().getTeams().keys();
        while (enumTeams.hasMoreElements()) {
            int key = (int) enumTeams.nextElement();
            Team team = auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key);

            if (team.getSpielklasse().getSpielklasseID()==ausgewaehlte_spielklasse.getSpielklasseID()&&team.toStringKomplett()!="") {
                obs_setzliste.add(team);
            }


        }


/*            for(int i=1;i<=auswahlklasse.getAktuelleTurnierAuswahl().getTeams().size();i++)
        {

            if(auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(i)!=null) {
                if (auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(i).getSpielklasse().getSpielklasseID()==ausgewaehlte_spielklasse.getSpielklasseID()) {
*//*                    if (!auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(i).getSetzplatzString2().equals("")) {
                        obs_setzliste.add(auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(i));
                    }*//*
                    obs_setzliste.add(auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(i));
                }

            }
        }*/
        System.out.println();
        pruefeAnzahlRLPItems();
/*        if (obs_setzliste!=null) {
            for (int j = 0; j < dicttest.size(); j++) {
                obs_setzliste.add(dicttest.get(j));
            }
        }*/
    }

    private void printSpielerSpielklasseHinzuTable() throws Exception {
        boolean weiblich=false;
        boolean maennlich=false;
        boolean alle=false;
        System.out.println(auswahlklasse.getAktuelleTurnierAuswahl());
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {
            obs_spieler.clear();
            System.out.println("Anzahl spielklassen = "+auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
            Enumeration enumSpielerIDs = auswahlklasse.getSpieler().keys();
            while (enumSpielerIDs.hasMoreElements()){
                int key = (int)enumSpielerIDs.nextElement();
                Spieler spieler = auswahlklasse.getSpieler().get(key);

                if (!istInSetzListe(spieler)){
                    if(auswahlklasse.getAktuelleSpielklassenAuswahl().toString().toUpperCase().contains("DAMEN"))
                    {

                        if(!spieler.getGeschlecht())
                        {
                            obs_spieler.add(spieler);
                            weiblich=false;
                        }
                        if(obs_spieler.size()==0)
                        {
                            weiblich=true;
                            //auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine weiblichen Spieler gefunden!");
                        }
                    }
                    if(auswahlklasse.getAktuelleSpielklassenAuswahl().toString().toUpperCase().contains("HERREN"))
                    {

                        if(spieler.getGeschlecht())
                        {
                            obs_spieler.add(spieler);
                            maennlich=false;
                        }
                        if(obs_spieler.size()==0)
                        {
                            maennlich=true;
                            //auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine weiblichen Spieler gefunden!");
                        }

                    }
                    if(auswahlklasse.getAktuelleSpielklassenAuswahl().toString().toUpperCase().contains("MIXED"))
                    {
                        if(spieler!=null) {
                            obs_spieler.add(spieler);
                            alle = false;
                        }
                        if(obs_spieler.size()==0)
                        {
                            alle=true;
                            //auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine Spieler gefunden!");
                        }

                    }

                    //sp.getDisziplin().contains("einzel")



                }
            }
            if(weiblich)
            {
                auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine weiblichen Spieler gefunden!");
            }
            if(maennlich)
            {
                auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine männlichen Spieler gefunden!");
            }
            if(alle)
            {
                auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine Spieler gefunden!");
            }
            TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            spielerVornameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
            TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            spielerNachnameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
            TableColumn<Spieler,String> spielerVereinSpalte = new TableColumn("Verein");
            spielerVereinSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
            TableColumn<Spieler,Date> spielerGeburtsdatumSpalte = new TableColumn("Geburtsdatum");
            spielerGeburtsdatumSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));

            spielsystem_spielerliste_alleSpieler.setItems(obs_spieler);
            spielsystem_spielerliste_alleSpieler.getColumns().addAll(spielerVornameSpalte,spielerNachnameSpalte,spielerVereinSpalte,spielerGeburtsdatumSpalte);
        }
        else{
            System.out.println("kann Spielerliste nicht laden");
        }
    }
    private boolean istInSetzListe(Spieler spieler){
        for (int i=0;i<obs_setzliste.size();i++){
            if (obs_setzliste.get(i).istImTeam(spieler)){
                return true;
            }
        }
        return false;
    }
    @FXML
    private void pressbtn_SpielerEntfernen(ActionEvent event)
    {

    }
    @FXML
    private void pressbtn_spielklasseStarten(ActionEvent event){
        if(ausgewaehlte_spielklasse.getSetzliste().size()>0&&ausgewaehlte_spielklasse.getSpiele().size()<=0)
        {


            if(radio_gruppe.isSelected()){
                gruppenSystemStarten();
            }
            if(radio_ko.isSelected()){
                koSystemStarten();
            }
            if (radio_gruppeMitE.isSelected()){
                gruppeMitEndrundeStarten();
            }
        }
    }

    private void gruppeMitEndrundeStarten() {
        try{
            int gruppenGroesse = Integer.valueOf(textField_gruppenGroesse.getText());
            int anzahlGruppen = ausgewaehlte_spielklasse.getSetzliste().size()/gruppenGroesse;
            int anzahlWeiterkommender = Integer.valueOf(textField_anzahlWeiterkommender.getText());
            GruppeMitEndrunde gruppeMitEndrunde = new GruppeMitEndrunde(ausgewaehlte_spielklasse,anzahlGruppen,anzahlWeiterkommender);
            l_meldungsetzliste1.setText("ERFOLG");
            auswahlklasse.InfoBenachrichtigung("Spielsystem start","Das Spielsystem wurde erfolgreich gestartet");
            TurnierladenController t = new TurnierladenController("Badminton Turnierverwaltung - "+auswahlklasse.getAktuelleTurnierAuswahl().getName());
        }
        catch (NumberFormatException e){
            System.out.println("Bitte nur zahlen eintragen");
        }
        catch (Exception e) {
            l_meldungsetzliste1.setText("Fehlschlag");
            auswahlklasse.InfoBenachrichtigung("Fehler","Das Spielsystem konnte nicht erfolgreich gestartet werden");
            e.printStackTrace();
        }
    }

    private void gruppenSystemStarten() {
        Gruppe gruppe = new Gruppe (ausgewaehlte_spielklasse.getSetzliste(),ausgewaehlte_spielklasse);
        try {
            ausgewaehlte_spielklasse.setSpielsystem( gruppe);

            l_meldungsetzliste1.setText("ERFOLG");
            auswahlklasse.InfoBenachrichtigung("Spielsystem start","Das Spielsystem wurde erfolgreich gestartet");
            TurnierladenController t = new TurnierladenController("Badminton Turnierverwaltung - "+auswahlklasse.getAktuelleTurnierAuswahl().getName());


            //a.getStages().get(0).close();

            //a.getStages().get(2).close();

        } catch (Exception e) {
            l_meldungsetzliste1.setText("Fehlschlag");
            auswahlklasse.InfoBenachrichtigung("Fehler","Das Spielsystem konnte nicht erfolgreich gestartet werden");
            e.printStackTrace();
        }
    }

    private void koSystemStarten() {
        boolean platzDreiAusspielen = radio_platzDreiAusspielen.isSelected() ;
        Spielsystem ko = new KO(ausgewaehlte_spielklasse.getSetzliste(),ausgewaehlte_spielklasse, platzDreiAusspielen);
        try {

            ausgewaehlte_spielklasse.setSpielsystem(ko);

            l_meldungsetzliste1.setText("ERFOLG");
            auswahlklasse.InfoBenachrichtigung("Spielsystem start","Das Spielsystem wurde erfolgreich gestartet");
            TurnierladenController t = new TurnierladenController("Badminton Turnierverwaltung - "+auswahlklasse.getAktuelleTurnierAuswahl().getName());


            //a.getStages().get(0).close();

            //a.getStages().get(2).close();

        } catch (Exception e) {
            l_meldungsetzliste1.setText("Fehlschlag");
            auswahlklasse.InfoBenachrichtigung("Fehler","Das Spielsystem konnte nicht erfolgreich gestartet werden");
            e.printStackTrace();
        }
    }

    @FXML
    private void trostSwitch(ActionEvent event) throws IOException {
        if(radio_trostNein.isSelected()){
            koTrostRundeNein.setVisible(true);
            koTrostRundeJa.setVisible(false);
        }
        else{
            koTrostRundeNein.setVisible(false);
            koTrostRundeJa.setVisible(true);
        }
    }

    @FXML
    void erstelleSetzlisteNormal(ActionEvent event) {
        spielsystem_setzliste.setEditable(false);
        System.out.println(obs_setzliste);
        SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
        boolean erfolg=false;
        for(int i=0;i<obs_setzliste.size();i++)
        {
            if(obs_setzliste.get(i).getSetzplatz()>0)
            {

                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(obs_setzliste.get(i));
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(obs_setzliste.get(i),obs_setzliste.get(i).getSetzplatz());
                erfolg= setzlisteDAO.create(obs_setzliste.get(i).getSetzplatz(),obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
            }
            if(obs_setzliste.get(i).getSetzplatz()==0)
            {
                erfolg= setzlisteDAO.create(i+1,obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(obs_setzliste.get(i),i+1);
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(obs_setzliste.get(i));
            }
        }
        if(erfolg)
        {
            auswahlklasse.InfoBenachrichtigung("Erfolg","Erfolg");
            auswahlklasse.getAktuelleSpielklassenAuswahl().setSetzliste_gesperrt(true);
            pruefeSperrungSetzliste();
        }
        if(!erfolg)
        {
            auswahlklasse.WarnungBenachrichtigung("Fehler","Fehler");
        }
        fuelleobs_setzliste();
        sortiereTabelleSetzliste();
    }
    @FXML
    void erstelleSetzlisteRLP(ActionEvent event) {
        spielsystem_setzliste.setEditable(false);
        SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
        boolean erfolg=false;
        ArrayList <Team> teamsohnesetzplatz=new ArrayList<>();
        ObservableList<Team> obs_teamsohne = FXCollections.observableArrayList();
        int index=1;
        for(int i=0;i<obs_setzliste.size();i++)
        {
            if(obs_setzliste.get(i).getSetzplatz()>0)
            {
                erfolg= setzlisteDAO.create(obs_setzliste.get(i).getSetzplatz(),obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
                index++;
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(obs_setzliste.get(i),i+1);
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(obs_setzliste.get(i));
            }
            if(obs_setzliste.get(i).getSetzplatz()==0)
            {
                teamsohnesetzplatz.add(obs_setzliste.get(i));
                obs_teamsohne.add(obs_setzliste.get(i));

                //erfolg= setzlisteDAO.create(i+1,obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
            }
        }

        teamsohnesetzplatz.sort(new Comparator<Team>() {
            @Override
            public int compare(Team z1, Team z2) {
                if (z1.getRLPanzeigen() > z2.getRLPanzeigen())
                    return -1;
                if (z1.getRLPanzeigen() < z2.getRLPanzeigen())
                    return 1;
                return 0;
            }
        });


        for (int i=0;i<teamsohnesetzplatz.size();i++)
        {
            erfolg=setzlisteDAO.create(index+i,teamsohnesetzplatz.get(i),teamsohnesetzplatz.get(i).getSpielklasse());
            auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(teamsohnesetzplatz.get(i),index+i);
            auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(teamsohnesetzplatz.get(i));
        }
        if(erfolg)
        {
            auswahlklasse.InfoBenachrichtigung("Erfolg","Erfolg");
            auswahlklasse.getAktuelleSpielklassenAuswahl().setSetzliste_gesperrt(true);
            pruefeSperrungSetzliste();
        }
        if(!erfolg)
        {
            auswahlklasse.WarnungBenachrichtigung("Fehler","Fehler");
        }
        fuelleobs_setzliste();
        sortiereTabelleSetzliste();

    }
    @FXML
    private void klassenSwitch(ActionEvent event) throws IOException, InterruptedException {

        if(radio_gruppe.isSelected()) {
            gruppe.toFront();
            gruppe.setVisible(true);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(false);
        }

        else if(radio_gruppeMitE.isSelected()) {
            gruppeMitEndrunde.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(true);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(false);
        }

        else if(radio_ko.isSelected()){
            koSystem.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(true);
            schweizerSystem.setVisible(false);
        }

        else if(radio_schweizer.isSelected()){
            schweizerSystem.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(true);
        }
        else{
            //label1.setText("");
            //label2.setText("");
            //label3.setText("");
            //hbox_1.getChildren().clear();
            //hbox_2.getChildren().clear();
        }
    }



    private void printSpielerSpielklasseVorhandenTable() throws Exception {

        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {
            turnierauswahlspielklassendict = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen();

            obs_setzliste.clear();
            //for (int i=0;i<turnierauswahlspielklassendict.size();i++){ //Warum?
            //System.out.println(ausgewaehlte_spielklasse.getSpiele().get(i));
            //obsvorhanden_spieler.add(ausgewaehlte_spielklasse.getSetzliste().get(i-1));

            System.out.println("ausgewählte Spielklasse = "+ausgewaehlte_spielklasse.getSpielklasseID()+" "+ausgewaehlte_spielklasse.getDisziplin());
            fuelleobs_setzliste();
            //}

            setzplatz.setCellValueFactory(new PropertyValueFactory<Team,Integer>("Setzplatz"));
            setzplatz.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

            if(!ausgewaehlte_spielklasse.isSetzliste_gesperrt())
            {
                spielsystem_setzliste.setEditable(true);
            }

            setzplatz.setEditable(true);
            setzplatz.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Team, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Team, Integer> event) {

                }
            });
            setzplatz.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Team, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Team, Integer> event) {
                    Team t = (Team) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    try
                    {
                        event.getNewValue();
                    }
                    catch (Exception e)
                    {
                        event.getTableView().getItems().set(event.getTablePosition().getRow(), t);
                        return;
                    }
                    if (!event.getNewValue().equals(event.getOldValue())&&event.getNewValue() > 0 && event.getNewValue() <= obs_setzliste.size())
                    {
                        int freiersetzplatz=-1;
                        boolean erfolg=false;
                        SetzlisteDAO setzlisteDAO=new SetzlisteDAOimpl();
                        boolean doppelt = false;
                        List<Team> list = FXCollections.observableList(obs_setzliste);
                        for(int i=0;i<list.size();i++) {
                            if (list.get(i) != null) {
                                boolean loeschen = false;


                                if (!doppelt && list.get(i).getSetzplatz() == event.getNewValue()) {
                                    doppelt = true;
                                    System.out.println("Doppelter Sezuplatz gefunden");
                                    i = 0;
                                }


                                Team to = event.getTableView().getItems().get(event.getTablePosition().getRow());

                                if (!doppelt && list.get(i).getSetzplatz() < event.getNewValue() && list.get(i).getSetzplatz() != i + 1) {
                                    System.out.println("Kleinerer Setzplatz gefunden");
                                    freiersetzplatz = i;
                                    break;
                                }

                                if (doppelt && list.get(i).getSetzplatz() >= event.getNewValue()) {
                                    System.out.println("Setzplatz wird verschoben");
                                    list.get(i).setSetzplatz(list.get(i).getSetzplatz() + 1);
                                    list.get(i).getTeamDAO().update(list.get(i));
                                }
/*
                            if (doppelt && list.get(i).getSetzplatz() != i + 1) {
                                freiersetzplatz = i;
                                erfolg=true;
                                break;
                            }*/



/*                                for(int j =0;j<list.size();j++)
                                {
                                    if(list.get(j).getSetzplatz()>=event.getNewValue())
                                    {

                                        System.out.println("Setzplatz wird verschoben");
                                        list.get(j).setSetzplatz(list.get(j).getSetzplatz()+1);
                                        list.get(j).getTeamDAO().update(list.get(j));
                                        erfolg=true;
                                        //list.get(j).set
                                    }

                                }*/

/*                                event.getTableView().getItems().set(event.getTablePosition().getRow(), to);
                                ObservableList<Team> obs_neu=FXCollections.observableArrayList(obs_setzliste);
                                */


/*
                            else if(obs_setzliste.get(i).equals(t))
                            {
                                System.out.println("true"+t);
                                obs_setzliste.remove(i);
                                loeschen=true;
                            }*/

                            }
                        }
                        if(freiersetzplatz>-1)
                        {
                            System.out.println("freiersetzplatz"+freiersetzplatz);
                            t.setSetzplatz(freiersetzplatz+1);
                        }

                        else if(!erfolg)
                        {
                            t.setSetzplatz(event.getNewValue());
                        }


                        t.getTeamDAO().update(t);

/*                        if(erfolg)
                        {
                            t.setSetzplatz(event.getNewValue());
                            obs_setzliste.add(t);
                            t.getTeamDAO().update(t);
                            System.out.println(obs_setzliste);
                            sortiereTabelleSetzliste();

                        }*/




                       /* if(!event.getOldValue().equals(""))
                        {
                            int alterplatz = Integer.parseInt(event.getOldValue());
                            ausgewaehlte_spielklasse.removeSetzlistedict(Integer.parseInt(event.getOldValue()), t);
                            System.out.println("tttt");
                            ausgewaehlte_spielklasse.addSetzlistedict(Integer.parseInt(event.getNewValue()), alterplatz, t);
                            System.out.println(event.getNewValue());
                            TurnierDAO turnierDAO = new TurnierDAOimpl();

                            System.out.println(t + "wurde bearbeitet");
                            fuelleobs_setzliste();
                            sortiereTabelleSetzliste();
                            spielsystem_setzliste.refresh();
                            spielsystem_setzliste.getSelectionModel().select(t);
                        }
                        else
                        {

                        }*/

                    }
                    else
                    {

                        Team to = event.getTableView().getItems().get(event.getTablePosition().getRow());


                        event.getTableView().getItems().set(event.getTablePosition().getRow(), to);





                        auswahlklasse.WarnungBenachrichtigung("Wertebereich","Bitte nur Zahlen zwischen 1-"+obs_setzliste.size()+" eingeben");
                    }
                    sortiereTabelleSetzliste();
                }
            });





            TableColumn<Team,String> spielerEinsSpalte = new TableColumn("Spieler");
            spielerEinsSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerEins"));
            TableColumn<Team,String> spielerZweiSpalte = new TableColumn("Partner");
            spielerZweiSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerZwei"));
            TableColumn<Team,Integer> RLPSpalte = new TableColumn("Ranglistenpunkte");
            RLPSpalte.setCellValueFactory(new PropertyValueFactory<Team,Integer>("RLPanzeigen"));


            spielsystem_setzliste.setItems(obs_setzliste);
            System.out.println("einzel = "+ausgewaehlte_spielklasse.isEinzel());
            System.out.println("Spielklasse = "+ausgewaehlte_spielklasse.getDisziplin());
            if (ausgewaehlte_spielklasse.isEinzel()){
                spielsystem_setzliste.getColumns().addAll(setzplatz,spielerEinsSpalte,RLPSpalte);
            }
            else{
                spielsystem_setzliste.getColumns().addAll(setzplatz,spielerEinsSpalte,spielerZweiSpalte,RLPSpalte);
            }

        }
        else{
            System.out.println("kann Setzliste nicht laden");
            l_meldungsetzliste1.setText("kann Setzliste nicht laden");
        }

    }



    private void removeTeam(Team team)
    {
        int altersetzplatz=team.getSetzplatz();

        System.out.println(team.toStringKomplett());
        obs_setzliste.remove(team);
        if(ausgewaehlte_spielklasse.isEinzel()) {
            obs_spieler.addAll(team.getSpielerEins());
        }
        else {
            obs_spieler.addAll(team.getSpielerEins(),team.getSpielerZwei());
        }


        boolean keinsetzplatz=false;
        if(team.getSetzplatz()==0)
        {
            keinsetzplatz=true;
        }

        team.getTeamDAO().delete(team);
        obs_setzliste.remove(team);
        if(!keinsetzplatz) {
            for (int i = altersetzplatz - 1; i < obs_setzliste.size(); i++) {
                if (obs_setzliste.get(i).getSetzplatz() > 0) {
                    System.out.println("Senke Setzplatz um 1");
                    System.out.println(obs_setzliste.get(i).getSetzplatz() + "          -1");
                    obs_setzliste.get(i).setSetzplatz(obs_setzliste.get(i).getSetzplatz() - 1);
                    obs_setzliste.get(i).getTeamDAO().update(obs_setzliste.get(i));
                }
            }
        }
        //dicttest.put(dicttest.size(),team);
        //ausgewaehlte_spielklasse.addSetzliste(team);

/*                boolean erfolg = setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size(),team,ausgewaehlte_spielklasse);
                if(erfolg) {
                    //dicttest.put(team.getTeamid(),team)
                    l_meldungsetzliste1.setText(team.getSpielerEins().getVName() + " " + team.getSpielerEins().getNName() + " wurde der Setzliste hinzugefügt!");
                }
                else
                {
                    l_meldungsetzliste1.setText("fehler");
                }*/

            /*ausgewaehlte_spielklasse.removeSetzliste(team);
            setzlisteDAO.deleteSetzplatz(ausgewaehlte_spielklasse.getSpielklasseID(),team.getTeamid());*/



        //ausgewaehlte_spielklasse.addSetzliste(team);

        //team.getTeamDAO().addSpieler(team, false);
        //setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size()+1,team,ausgewaehlte_spielklasse);


        //l_meldungsetzliste1.setText(team.getSpielerEins().getVName()+" "+team.getSpielerEins().getNName()+" und "+team.getSpielerZwei().getVName()+" "+team.getSpielerZwei().getNName()+" wurden aus der Setzliste entfernt!");


    }




    private void sortiereTabelleSetzliste() {
        obs_setzliste.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                if(o1.getSetzplatzString2().equals("")&&o2.getSetzplatzString2().equals(""))
                {
                    return 0;
                }
                else if(o1.getSetzplatzString2().equals(""))
                {
                    return 1000000 - Integer.parseInt(o2.getSetzplatzString2());
                }
                else if(o2.getSetzplatzString2().equals(""))
                {
                    return Integer.parseInt(o1.getSetzplatzString2())-1000000;
                }
                else
                {
                    return Integer.parseInt(o1.getSetzplatzString2())- Integer.parseInt(o2.getSetzplatzString2());
                }

            }
        });
        spielsystem_setzliste.setItems(obs_setzliste);
        /*setzplatz.setSortType(TableColumn.SortType.ASCENDING);
        spielsystem_setzliste.getSortOrder().add(setzplatz);*/
        spielsystem_setzliste.refresh();
    }
    private void addSpieler(Spieler spielerneu)
    {

//        System.out.println(spielerneu.getNName());
        obs_spieler.remove(spielerneu);
        if(befuellem1) {
            if(ausgewaehlte_spielklasse.isEinzel()){
                team = new Team(spielerneu,ausgewaehlte_spielklasse);
                obs_setzliste.add(team);
                //dicttest.put(dicttest.size(),team);
                //ausgewaehlte_spielklasse.addSetzliste(team);

/*  Setzliste DAO wird benötigt
               boolean erfolg = setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size(),team,ausgewaehlte_spielklasse);
                if(erfolg) {
                    //dicttest.put(team.getTeamid(),team)
                    l_meldungsetzliste1.setText(team.getSpielerEins().getVName() + " " + team.getSpielerEins().getNName() + " wurde der Setzliste hinzugefügt!");
                }
                else
                {
                    l_meldungsetzliste1.setText("fehler");
                }*/
            }

            else{
                team = new Team(spielerneu,ausgewaehlte_spielklasse,false);
                System.out.println("doppelklasse");
                befuellem1=false;
                obs_setzliste.add(team);
                //dicttest.put(dicttest.size(),team);


            }

        }
        else
        {

            // team.setTeamid(ausgewaehlte_spielklasse.getSetzliste());
            team.addSpieler(spielerneu);

            team.getTeamDAO().create(team);


            befuellem1=true;
            //System.out.println(ausgewaehlte_spielklasse.getSetzliste().size()+1+"-------------");

            //ausgewaehlte_spielklasse.addSetzliste(team);

            //team.getTeamDAO().addSpieler(team, false);
            //setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size(),team,ausgewaehlte_spielklasse);


            l_meldungsetzliste1.setText(team.getSpielerEins().getVName()+" "+team.getSpielerEins().getNName()+" und "+team.getSpielerZwei().getVName()+" "+team.getSpielerZwei().getNName()+" wurden der Setzliste hinzugefügt!");

        }
        spielsystem_setzliste.refresh();
        pruefeAnzahlRLPItems();
    }

    private TableColumn getTableColumn(
            final TableColumn  column, int offset) {
        int columnIndex = spielsystem_setzliste.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return spielsystem_setzliste.getVisibleLeafColumn(newColumnIndex);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabsperst.setDisable(true);
        spielsystem_spielerliste_alleSpieler.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        System.out.println("------------------"+ausgewaehlte_spielklasse.isSetzliste_gesperrt());

/*        for (int i = 0; i < ausgewaehlte_spielklasse.getSetzliste().size(); i++) {
            dicttest.put(i, ausgewaehlte_spielklasse.getSetzliste().get(i));
            *//*System.out.println("Dict: " + dicttest.size());
            System.out.println(ausgewaehlte_spielklasse.getSetzliste().size());*//*
        }*/
        try {
            printSpielerSpielklasseVorhandenTable();
            printSpielerSpielklasseHinzuTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ausgewaehlte_spielklasse.isSetzliste_gesperrt()||ausgewaehlte_spielklasse.getSetzliste().size()>0)
        {
            pruefeSperrungSetzliste();
        }
        else {
            ContextMenu contextMenu = new ContextMenu();


            spielsystem_setzliste.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        contextMenu.hide();
                    }

                    if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                        Team clickedRow = (Team) row.getItem();
                        //(((Node)(event.getSource())).getScene().getWindow().hide();
                        MenuItem item1 = new MenuItem("Setzplatz bearbeiten");
                        item1.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                row.setEditable(true);
                                //row.getTableView().getEditingCell().
                                TablePosition<Team, ?> pos = spielsystem_setzliste.getFocusModel().getFocusedCell() ;
                                spielsystem_setzliste.setEditable(true);
                                spielsystem_setzliste.edit(pos.getRow(),setzplatz);

                            }
                        });
                        MenuItem item2 = new MenuItem("Aus Setzliste entfernen");
                        item2.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                                //FuelleFelder(clickedRow);

                                removeTeam(clickedRow);

                            }
                        });

                        // Add MenuItem to ContextMenu
                        contextMenu.getItems().clear();
                        contextMenu.getItems().addAll(item1, item2);

                        // When user right-click on Circle
                        spielsystem_setzliste.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                if(!ausgewaehlte_spielklasse.isSetzliste_gesperrt())
                                {
                                    contextMenu.show(spielsystem_setzliste, event.getScreenX(), event.getScreenY());
                                }

                            }
                        });
                    }
                });
                return row;
            });


            spielsystem_spielerliste_alleSpieler.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        contextMenu.hide();
                    }
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                            && event.getClickCount() == 2) {
                        Spieler clickedRow = (Spieler) row.getItem();
                        //(((Node)(event.getSource())).getScene().getWindow().hide();
                        addSpieler(clickedRow);
                    }
                    if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                        Spieler clickedRow = (Spieler) row.getItem();
                        //(((Node)(event.getSource())).getScene().getWindow().hide();
                        MenuItem item1 = new MenuItem("Spieler hinzufügen");
                        item1.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();

                                    auswahlklasse.getStagesdict().put("SpielerHinzu",stage);
                                    stage.setScene(new Scene(root1));
                                    stage.show();
                                    stage.setTitle("Spieler");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Fehler beim laden");
                                }
                                //tabpane_spieler.getSelectionModel().select(tab_sphin);
                            }
                        });
                        MenuItem item2 = new MenuItem("Spieler bearbeiten");
                        item2.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                                //FuelleFelder(clickedRow);

                                try {
                                    auswahlklasse.setUpdateSpieler(clickedRow);
                                    auswahlklasse.setTab(3);
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();

                                    auswahlklasse.getStagesdict().put("SpielerHinzu",stage);
                                    stage.setScene(new Scene(root1));
                                    stage.show();
                                    stage.setTitle("Spieler");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Fehler beim laden");
                                }


                            }
                        });
                        MenuItem item3 = new MenuItem("Spieler löschen");
                        item3.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {

                                boolean loeschespieler = clickedRow.getSpielerDAO().delete(clickedRow);
                                if (loeschespieler) {
                                    obs_spieler.remove(clickedRow);
                                    auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().remove(clickedRow);
                                    auswahlklasse.getSpieler().remove(clickedRow);
                                    //tabelle_spielerliste.refresh();
                                    System.out.println("Lösche   " + clickedRow.getNName());
                                    l_meldungsetzliste1.setText(clickedRow.getVName() + " " + clickedRow.getNName() + " wurde erfolgreich gelöscht!");
                                } else {

                                    //l_meldungsetzliste.setTextFill(Color.web("#048d46"));
                                    l_meldungsetzliste1.setText(clickedRow.getVName() + " " + clickedRow.getNName() + " kann nicht gelöscht werden!");
                                }

                            }
                        });
                        MenuItem item4 ;
                        if(spielsystem_spielerliste_alleSpieler.getSelectionModel().getSelectedItems().size()>1)
                        {
                            item4 = new MenuItem("Alle markierten Teams zur Setzliste hinzufügen");
                        }
                        else
                        {
                            item4 = new MenuItem("Team zur Setzliste hinzufügen");
                        }

                        item4.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {

                                System.out.println(spielsystem_spielerliste_alleSpieler.getSelectionModel().getSelectedItems());
                                ObservableList markierteTeams=FXCollections.observableArrayList(spielsystem_spielerliste_alleSpieler.getSelectionModel().getSelectedItems());

                                for(int i=0;i<markierteTeams.size();i++)
                                {
                                    Spieler spieler = (Spieler) markierteTeams.get(i);
                                    addSpieler(spieler);
                                }
                                //addSpieler(clickedRow);


                            }
                        });
                        // Add MenuItem to ContextMenu
                        contextMenu.getItems().clear();
                        contextMenu.getItems().addAll(item4, item1, item2, item3);

                        // When user right-click on Circle
                        spielsystem_spielerliste_alleSpieler.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                contextMenu.show(spielsystem_spielerliste_alleSpieler, event.getScreenX(), event.getScreenY());
                            }
                        });
                    }
                });
                return row;
            });
            t_suchleistespieler.textProperty().addListener((observable, oldValue, newValue) -> {
                // System.out.println("textfield changed from " + oldValue + " to " + newValue);
                //obs_spieler.clear();

                obs_spieler.clear();


                spielsystem_spielerliste_alleSpieler.refresh();
                Enumeration e = auswahlklasse.getSpieler().keys();
                while (e.hasMoreElements()) {
                    int key = (int) e.nextElement();
                    if (auswahlklasse.getSpieler().get(key).toString().toUpperCase().contains(t_suchleistespieler.getText().toUpperCase())) {
                        if (!istInSetzListe(auswahlklasse.getSpieler().get(key))) {
                            obs_spieler.add(auswahlklasse.getSpieler().get(key));
                        }
                    }
                    ;
                }


            });




        }

/*    Suchleiste wird noch benötigt

    t_suchleistesetzliste.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();
            //ausgewaehlte_spielklasse.getSetzliste().
            obs_setzliste.clear();
            for (int i = 0; i < obs_setzliste.size(); i++) {
                dicttest.put(i, ausgewaehlte_spielklasse.getSetzliste().get(i));
                System.out.println("Dict: " + dicttest.size());
                System.out.println(ausgewaehlte_spielklasse.getSetzliste().size());
            }
            spielsystem_setzliste.refresh();
            Enumeration e = dicttest.keys();
            while (e.hasMoreElements()) {
                int key = (int) e.nextElement();
                if (ausgewaehlte_spielklasse.getSetzliste().get(key).toString() != null) {
                    if (ausgewaehlte_spielklasse.getSetzliste().get(key).toString().toUpperCase().contains(t_suchleistesetzliste.getText().toUpperCase())) {
                        obs_setzliste.add(ausgewaehlte_spielklasse.getSetzliste().get(key));
                    }
                    ;
                }
            }


        });*/


        auswahlklasse.getStagesdict();

        sortiereTabelleSetzliste();
    }//Ende Initialize

    private void pruefeAnzahlRLPItems()
    {
        menu_rlp.getItems().clear();
        MenuItem alle=new MenuItem("Alle");
        menu_rlp.getItems().add(alle);
        if(obs_setzliste.size()>2)
        {


            int anzahl = (int) (Math.log( obs_setzliste.size() ) / Math.log( 2.0 ));
            System.out.println(anzahl);
            MenuItem[] item = new MenuItem[anzahl];

            for(int i=0;i<anzahl;i++)
            {
                item[i]=new MenuItem(String.valueOf(Math.round(Math.pow(2,anzahl)))+" Setzplätze erstellen");
                //item[anzahl].setText(String.valueOf(Math.round(Math.pow(2,anzahl)))+" Setzplätze erstellen");
                int finalI = i;
                item[finalI].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(event.getEventType().getName());
                        System.out.println(finalI);
                    }
                });
            }

            while(anzahl>0)
            {


                item[anzahl-1].setText(String.valueOf(Math.round(Math.pow(2,anzahl)))+" Setzplätze erstellen");
                menu_rlp.getItems().add(item[anzahl-1]);
                anzahl--;
            }

        }
    }

    private void pruefeSperrungSetzliste() {
        tabsperst.setDisable(false);
        l_meldungsetzliste1.setText("Setzliste gesperrt!!!");
        spielsystem_spielerliste_alleSpieler.setVisible(false);

        t_suchleistespieler.setVisible(false);

        btnentf.setVisible(false);
    }

}

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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DAO.SetzlisteDAO;
import sample.DAO.SetzlisteDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Spieler;
import sample.Spielklasse;
import sample.Team;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Manuel Hüttermann on 04.08.2017.
 */

public class SpielSystemController_neu implements Initializable
{
    //Tab1
    @FXML
    private TextField t_suchleistespieler;
    @FXML
    private Label l_meldungsetzliste1;

    @FXML
    private TableView spielsystem_spielerliste_alleSpieler;
    @FXML
    private TableView spielsystem_setzliste;


    //Tab2
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

    Dictionary<Integer,Spielklasse> turnierauswahlspielklassen = null;
    auswahlklasse a = new auswahlklasse();
    Spielklasse ausgewaehlte_spielklasse=  auswahlklasse.getAktuelleSpielklassenAuswahl();
    static Spieler spieler_neu=null;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();
    private static ObservableList<Team> obs_setzliste =  FXCollections.observableArrayList();
    private static boolean befuellem1=true;
    private static Team team = null;
    private SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();

    private void printSpielerSpielklasseHinzuTable() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {
            obs_spieler.clear();
            for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpieler().size();i++){
                Spieler spieler = a.getAktuelleTurnierAuswahl().getSpieler().get(i);
                if (!istInSetzListe(spieler)){
                    obs_spieler.add(spieler);

                }
            }
            TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            spielerVornameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
            TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            spielerNachnameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
            TableColumn<Spieler,String> spielerVereinSpalte = new TableColumn("Nachname");
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

        if(a.getAktuelleTurnierAuswahl()!=null) {
            turnierauswahlspielklassen= a.getAktuelleTurnierAuswahl().getSpielklassen();
            ArrayList<Team> setzliste = ausgewaehlte_spielklasse.getSetzliste();

            obs_setzliste.clear();
            //for (int i=0;i<turnierauswahlspielklassen.size();i++){ //Warum?
                //System.out.println(ausgewaehlte_spielklasse.getSpiele().get(i));
                //obsvorhanden_spieler.add(ausgewaehlte_spielklasse.getSetzliste().get(i-1));

                System.out.println("ausgewählte Spielklasse = "+ausgewaehlte_spielklasse.getSpielklasseID()+" "+ausgewaehlte_spielklasse.getDisziplin());

                for(int j = 0;j<setzliste.size();j++)
                {
                    obs_setzliste.add(setzliste.get(j));
                }
            //}
            TableColumn<Team,String> spielerEinsSpalte = new TableColumn("Spieler");
            spielerEinsSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerEins"));
            TableColumn<Team,String> spielerZweiSpalte = new TableColumn("Partner");
            spielerZweiSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerZwei"));

            spielsystem_setzliste.setItems(obs_setzliste);
            System.out.println("einzel = "+ausgewaehlte_spielklasse.isEinzel());
            System.out.println("Spielklasse = "+ausgewaehlte_spielklasse.getDisziplin());
            if (ausgewaehlte_spielklasse.isEinzel()){
                System.out.println("erfolgreich");
                spielsystem_setzliste.getColumns().addAll(spielerEinsSpalte);
            }
            else{
                spielsystem_setzliste.getColumns().addAll(spielerEinsSpalte,spielerZweiSpalte);
            }

        }
        else{
            System.out.println("kann Setzliste nicht laden");
        }

    }
    private void addSpieler(Spieler spielerneu)
    {
        System.out.println(spielerneu.getNName());
        obs_spieler.remove(spielerneu);
        if(befuellem1) {
            team = new Team(spielerneu,ausgewaehlte_spielklasse);
            obs_setzliste.add(team);
            if(ausgewaehlte_spielklasse.isEinzel()){
                ausgewaehlte_spielklasse.addSetzliste(team);
                setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size(),team,ausgewaehlte_spielklasse);
                l_meldungsetzliste1.setText(team.getSpielerEins().getVName()+" "+team.getSpielerEins().getNName()+" wurde der Setzliste hinzugefügt!");
            }
            else{
                befuellem1=false;
            }

        }
        else
        {
            team.addSpieler(spielerneu);
            befuellem1=true;
            System.out.println(ausgewaehlte_spielklasse.getSetzliste().size()+1+"-------------");
            spielsystem_setzliste.refresh();
            ausgewaehlte_spielklasse.addSetzliste(team);
            setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size()+1,team,ausgewaehlte_spielklasse);
            team.getTeamDAO().addSpieler(team, false);
            l_meldungsetzliste1.setText(team.getSpielerEins().getVName()+" "+team.getSpielerEins().getNName()+" und "+team.getSpielerZwei().getVName()+" "+team.getSpielerZwei().getNName()+" wurden der Setzliste hinzugefügt!");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();
        spielsystem_setzliste.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {

            });
            return row ;
        });
        spielsystem_spielerliste_alleSpieler.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY)
                {
                    contextMenu.hide();
                }
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spieler clickedRow = (Spieler) row.getItem();
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                    addSpieler(clickedRow);
                }
                if (! row.isEmpty() && event.getButton()== MouseButton.SECONDARY) {
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
                                a.addStage(stage);
                                stage.setScene(new Scene(root1));
                                stage.show();
                                stage.setTitle("Spieler");
                            } catch(Exception e) {
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
                                a.setUpdateSpieler(clickedRow);
                                a.setTab(3);
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
                                Parent root1 = (Parent) fxmlLoader.load();
                                Stage stage = new Stage();
                                a.addStage(stage);
                                stage.setScene(new Scene(root1));
                                stage.show();
                                stage.setTitle("Spieler");
                            } catch(Exception e) {
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
                           if(loeschespieler)
                           {
                               obs_spieler.remove(clickedRow);
                               a.getAktuelleSpielklassenAuswahl().getSetzliste().remove(clickedRow);
                               a.getSpieler().remove(clickedRow);
                               //tabelle_spielerliste.refresh();
                               System.out.println("Lösche   "+ clickedRow.getNName());
                               l_meldungsetzliste1.setText(clickedRow.getVName()+" "+clickedRow.getNName()+" wurde erfolgreich gelöscht!");
                           }
                           else
                           {

                               //l_meldungsetzliste.setTextFill(Color.web("#048d46"));
                               l_meldungsetzliste1.setText(clickedRow.getVName()+" "+clickedRow.getNName()+" kann nicht gelöscht werden!");
                           }

                        }
                    });

                    // Add MenuItem to ContextMenu
                    contextMenu.getItems().clear();
                    contextMenu.getItems().addAll(item1, item2, item3);

                    // When user right-click on Circle
                    spielsystem_spielerliste_alleSpieler.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                        @Override
                        public void handle(ContextMenuEvent event) {

                            contextMenu.show(spielsystem_spielerliste_alleSpieler, event.getScreenX(), event.getScreenY());
                        }
                    });
                }
            });
            return row ;
        });
        spielsystem_setzliste.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Team clickedRow = (Team) row.getItem();
                    //(((Node)(event.getSource())).getScene().getWindow().hide();

                }
            });
            return row ;
        });
        try {
            printSpielerSpielklasseVorhandenTable();
            printSpielerSpielklasseHinzuTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        t_suchleistespieler.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            obs_spieler.clear();

            spielsystem_spielerliste_alleSpieler.refresh();
            Enumeration e = a.getSpieler().keys();
            while (e.hasMoreElements()){
                int key = (int) e.nextElement();
                if(a.getSpieler().get(key).toString().toUpperCase().contains(t_suchleistespieler.getText().toUpperCase()))
                {
                    obs_spieler.add(a.getSpieler().get(key));
                }
                              ;
            }


        });
    }
}

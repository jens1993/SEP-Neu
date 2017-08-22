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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DAO.SetzlisteDAO;
import sample.DAO.SetzlisteDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spieler;
import sample.Spielklasse;
import sample.Team;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Manuel Hüttermann on 04.08.2017.
 */

public class SpielSystemController implements Initializable
{
    //Tab1
    @FXML
    private Label l_meldungsetzliste;
    @FXML
    private TableView spielsystem_spielerliste_m1;
    @FXML
    private TableColumn spielsystem_spielerliste_vorname1;
    @FXML
    private TableColumn spielsystem_spielerliste_nachname1;

    @FXML
    private TableView spielsystem_spielerliste_alleSpieler;
    @FXML
    private TableColumn spielsystem_spielerliste_verein;
    @FXML
    private TableColumn spielsystem_spielerliste_geburtstag;
    @FXML
    private TableColumn spielsystem_spielerliste_vorname;
    @FXML
    private TableColumn spielsystem_spielerliste_geschlecht;
    @FXML
    private TableColumn spielsystem_spielerliste_nachname;
    @FXML
    private TableView spielsystem_spielerliste_m2;

    @FXML
    private TableColumn spielsystem_spielerliste_vorname11;
    @FXML
    private TableColumn spielsystem_spielerliste_nachname11;


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
    private static ObservableList<Spieler> setzliste_spielerm1 =  FXCollections.observableArrayList();
    private static ObservableList<Spieler> setzliste_spielerm2 =  FXCollections.observableArrayList();
    private static boolean befuellem1=true;
    private static Spieler spielerm1 = null;
    private static Spieler spielerm2 = null;

    private void printSpielerSpielklasseHinzuTable() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {
            obs_spieler.clear();
            Enumeration enumSpielerIDs = auswahlklasse.getSpieler().keys();
            while (enumSpielerIDs.hasMoreElements()){
                int key = (int)enumSpielerIDs.nextElement();
                obs_spieler.add(auswahlklasse.getSpieler().get(key));
            }
                        //TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            spielsystem_spielerliste_nachname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
            spielsystem_spielerliste_vorname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
            spielsystem_spielerliste_geschlecht.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
            spielsystem_spielerliste_verein.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
            //TableColumn<Spieler,Date> spielerGeburtsdatumSpalte = new TableColumn("Geburtsdatum");
            spielsystem_spielerliste_geburtstag.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));

            spielsystem_spielerliste_alleSpieler.setItems(obs_spieler);
        }
        else{
            System.out.println("kann Spielerliste nicht laden");
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

            setzliste_spielerm1.clear();
            setzliste_spielerm2.clear();
            Enumeration enumSpielklasseIDs = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().keys();
            while (enumSpielklasseIDs.hasMoreElements()){
                int key = (int)enumSpielklasseIDs.nextElement();
                //System.out.println(ausgewaehlte_spielklasse.getSpiele().get(i));
                //obsvorhanden_spieler.add(ausgewaehlte_spielklasse.getSetzliste().get(i-1));

                System.out.println("ausgewählte Spielklasse = "+ausgewaehlte_spielklasse.getSpielklasseID()+" "+ausgewaehlte_spielklasse.getDisziplin());
                System.out.println(ausgewaehlte_spielklasse.getSetzliste());

                for(int j = 0;j<setzliste.size();j++)
                {
                    System.out.println("M1 = "+setzliste.get(j).getSpielerEins().getVName()+" "+setzliste.get(j).getSpielerEins().getNName());
                    System.out.println("M2 = "+setzliste.get(j).getSpielerZwei().getVName()+" "+setzliste.get(j).getSpielerZwei().getNName());
                    System.out.println(setzliste.size());

                    if(setzliste.get(j).getSpielerEins()!=null)
                    {
                        setzliste_spielerm1.add(setzliste.get(j).getSpielerEins());
                        obs_spieler.remove(setzliste.get(j).getSpielerEins());
                    }
                    if(setzliste.get(j).getSpielerZwei()!=null)
                    {
                        setzliste_spielerm2.add(setzliste.get(j).getSpielerZwei());
                        obs_spieler.remove(setzliste.get(j).getSpielerZwei());
                    }




                }


//                obsvorhanden_spieler.add(ausgewaehlte_spielklasse.getSetzliste().get(i).getSpielerEins());
//
//                obsvorhanden_spieler.add(ausgewaehlte_spielklasse.getSetzliste().get(i).getSpielerZwei());
//
//                System.out.println("Spieler 1="+ausgewaehlte_spielklasse.getSetzliste().get(i).getSpielerEins());
//
//                System.out.println("Spieler 2="+ausgewaehlte_spielklasse.getSetzliste().get(i).getSpielerZwei());
            }
            //TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            spielsystem_spielerliste_vorname1.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
            //TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            spielsystem_spielerliste_nachname1.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
            spielsystem_spielerliste_vorname11.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
            //TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            spielsystem_spielerliste_nachname11.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));

            spielsystem_spielerliste_m1.setItems(setzliste_spielerm1);
            spielsystem_spielerliste_m2.setItems(setzliste_spielerm2);
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
            setzliste_spielerm1.add(spielerneu);
            spielerm1=spielerneu;
            befuellem1=false;
        }
        else
        {
            setzliste_spielerm2.add(spielerneu);
            spielerm2=spielerneu;
            befuellem1=true;
            //System.out.println(ausgewaehlte_spielklasse.getSetzliste().size()+1+"-------------");
            Team t = new Team(spielerm1,spielerm2,ausgewaehlte_spielklasse);
            SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
            ausgewaehlte_spielklasse.addSetzliste(t);
            setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size()+1,t,ausgewaehlte_spielklasse);
            l_meldungsetzliste.setText(t.getSpielerEins().getVName()+" "+t.getSpielerEins().getNName()+" und "+t.getSpielerZwei().getVName()+" "+t.getSpielerZwei().getNName()+" wurden der Setzliste hinzugefügt!");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();
        spielsystem_spielerliste_m2.setRowFactory(tv -> {
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
                                a.getStagesdict().put("SpielerHinzu",stage);
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
                                a.getStagesdict().put("SpielerHinzu",stage);
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
                               l_meldungsetzliste.setText(clickedRow.getVName()+" "+clickedRow.getNName()+" wurde erfolgreich gelöscht!");
                           }
                           else
                           {

                               //l_meldungsetzliste.setTextFill(Color.web("#048d46"));
                               l_meldungsetzliste.setText(clickedRow.getVName()+" "+clickedRow.getNName()+" kann nicht gelöscht werden!");
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
        spielsystem_spielerliste_m2.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spieler clickedRow = (Spieler) row.getItem();
                    //(((Node)(event.getSource())).getScene().getWindow().hide();

                }
            });
            return row ;
        });
        try {
            printSpielerSpielklasseHinzuTable();
            printSpielerSpielklasseVorhandenTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

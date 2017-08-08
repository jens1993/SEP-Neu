package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spieler;
import sample.Spielklasse;
import sample.Team;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.ResourceBundle;

/**
 * Created by Manuel Hüttermann on 04.08.2017.
 */

public class SpielSystemController implements Initializable
{
    //Tab1
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
            for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpieler().size();i++){
                obs_spieler.add(a.getAktuelleTurnierAuswahl().getSpieler().get(i));
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
            for (int i=0;i<turnierauswahlspielklassen.size();i++){
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
            System.out.println(ausgewaehlte_spielklasse.getSetzliste().size()+1+"-------------");
            Team t = new Team(ausgewaehlte_spielklasse.getSetzliste().size()+1,spielerm1,spielerm2,ausgewaehlte_spielklasse);
            ausgewaehlte_spielklasse.addSetzliste(t);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        spielsystem_spielerliste_alleSpieler.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spieler clickedRow = (Spieler) row.getItem();
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                    addSpieler(clickedRow);
                }
            });
            return row ;
        });
        spielsystem_spielerliste_m1.setRowFactory(tv -> {
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

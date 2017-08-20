package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spieler;
import sample.Verein;

import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class TeamUebersichtController implements Initializable, Cloneable
{
    @FXML
    private Tab tab_sp;
    @FXML
    private Tab tab_teamueber;
    @FXML
    private Tab tab_teamerst;
    @FXML
    private TabPane tabpane_spieler;

//Tab2
    @FXML
    private TableView tabelle_spielerliste2;
    @FXML
    private TableColumn tabelle_spielerliste_vorname2;
    @FXML
    private TableColumn tabelle_spielerliste_nachname2;
    @FXML
    private TableColumn tabelle_spielerliste_geschlecht2;
    @FXML
    private TableColumn tabelle_spielerliste_verein2;
    @FXML
    private TableColumn tabelle_spielerliste_geburtstag2;

    @FXML
    private TextField team1;
    @FXML
    private TextField team2;

    auswahlklasse a = new auswahlklasse();

    static Spieler spieler1=null;
    static Spieler spieler2=null;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();
    static boolean teammitglied1 = false;
    static boolean teammitglied2 = false;
    private void printSpielerZuordnenTable() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {

            Enumeration enumSpielerIDs = auswahlklasse.getSpieler().keys();
            while (enumSpielerIDs.hasMoreElements()){
                int key = (int)enumSpielerIDs.nextElement();
                obs_spieler.add(auswahlklasse.getSpieler().get(key));

            }

            //TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            tabelle_spielerliste_vorname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));

            //TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            tabelle_spielerliste_nachname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
            tabelle_spielerliste_geschlecht2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
            tabelle_spielerliste_verein2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));

            //TableColumn<Spieler,Date> spielerGeburtsdatumSpalte = new TableColumn("Geburtsdatum");
            tabelle_spielerliste_geburtstag2.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
            tabelle_spielerliste2.setItems(obs_spieler);


        }
        else{
            System.out.println("kann Turnier nicht laden");
        }

    }
    @FXML
    public void pressBtn_PLUS1(ActionEvent event) throws Exception {
        System.out.println("test");
        tab_sp.setDisable(false);
        tab_teamerst.setDisable(true);
        tab_teamueber.setDisable(true);
        tabpane_spieler.getSelectionModel().select(tab_sp);
        teammitglied1=true;
        teammitglied2=false;

    }
    @FXML
    public void pressBtn_PLUS2(ActionEvent event) throws Exception {
        System.out.println("test");
        tab_sp.setDisable(false);
        tab_teamerst.setDisable(true);
        tab_teamueber.setDisable(true);
        tabpane_spieler.getSelectionModel().select(tab_sp);
        teammitglied2=true;
        teammitglied1=false;

    }
    @FXML
    public void pressBtn_ErstelleTeam(ActionEvent event) throws Exception {
        System.out.println(spieler1.getNName()+"   "+spieler2.getNName());

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tabelle_spielerliste2.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spieler clickedRow = (Spieler) row.getItem();
//                    System.out.println(clickedRow.getNName());
//                    //(((Node)(event.getSource())).getScene().getWindow().hide();
//                    System.out.println(clickedRow.getVName()+" "+ clickedRow.getNName());
//                    System.out.println(team1.getText());
//                    System.out.println(team2.getText());

                    if(teammitglied1)
                    {
                        team1.setText(clickedRow.getVName()+" "+ clickedRow.getNName());
                        spieler1=clickedRow;
                    }
                    if(teammitglied2)
                    {
                        team2.setText(clickedRow.getVName()+" "+ clickedRow.getNName());
                        spieler2=clickedRow;
                    }
                    tab_sp.setDisable(true);
                    tab_teamerst.setDisable(false);
                    tab_teamueber.setDisable(false);
                    tabpane_spieler.getSelectionModel().select(tab_teamerst);
                }
            });
            return row ;
        });

        try {


            printSpielerZuordnenTable();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}

package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Enums.AnzahlRunden;
import sample.Enums.Disziplin;
import sample.Enums.Niveau;
import sample.Spieler;
import sample.Turnier;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Dictionary;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class klasseHinzuGruppeController implements Initializable
{

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
    private AnchorPane koSystem;
    @FXML
    private AnchorPane koTrostRundeJa;
    @FXML
    private AnchorPane koTrostRundeNein;
    @FXML
    private Pane gruppe;
    @FXML
    private AnchorPane gruppeMitEndrunde;
    @FXML
    private AnchorPane schweizerSystem;
    @FXML
    private TableView tabelle_SpielerZuordnen;
    @FXML
    private TableColumn spielerVornameSpalte;
    @FXML
    private TableColumn spielerNachnameSpalte;
    @FXML
    private TableColumn spielerGeburstdatumSpalte;


    private static int index_niveau=100;
    private static int index_diszipin=100;
    private static int index_anzahlRunden=100;

    auswahlklasse a = new auswahlklasse();
    TurnierDAO t = new TurnierDAOimpl();
    Dictionary<Integer,Turnier> turnierliste = t.getAllTurniere();
    @FXML
    public ComboBox<Disziplin> combo_disziplin = new ComboBox<>();
    @FXML
    public ComboBox<AnzahlRunden> combo_anzahlRunden = new ComboBox<>();
    @FXML
    private void setDisziplin_auswahl(ActionEvent event){
        index_diszipin = combo_disziplin.getSelectionModel().getSelectedIndex();
    }
    @FXML
    public  ComboBox<Niveau> combo_niveau = new ComboBox<>();
    @FXML
    public void comboBoxFill() throws IOException {
        try{
            combo_niveau.getItems().setAll(Niveau.values());
            combo_disziplin.getItems().setAll(Disziplin.values());
            //combo_anzahlRunden.getItems().setAll("1", "2", "3");
            combo_niveau.getSelectionModel().select(index_niveau);
            combo_disziplin.getSelectionModel().select(index_diszipin);
            //combo_anzahlRunden.getSelectionModel().select(1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //combo_niveau.getSelectionModel().select(0);
        //combo_niveau.setItems( FXCollections.observableArrayList((E[]) Niveau.values()));
        //combo_disziplin.setItems(Disziplin.Herreneinzel);
    }
    @FXML
    private void setNiveau_auswahl(ActionEvent event){
        //SingleSelectionModel niveau_auswahl = combo_niveau.getSelectionModel();
        //a = combo_niveau.getSelectionModel().getSelectedIndex();
        //System.out.println("hallo -----"+niveau_auswahl.toString());
        //combo_niveau.getSelectionModel().select(niveau_auswahl);
        //test1=combo_niveau.getItems().get(a);
        // System.out.println(test1.toString());
        //System.out.println(niveau_auswahl.toString());
        //System.out.println(a);
        //combo_niveau.setSelectionModel();
        index_niveau = combo_niveau.getSelectionModel().getSelectedIndex();
    }
    @FXML
    private void trostSwitch(ActionEvent event) throws IOException{
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

    private void printSpielerZuordnenTable() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {
            ObservableList<Spieler> spieler = FXCollections.observableArrayList();

            for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpieler().size();i++){
                spieler.add(a.getAktuelleTurnierAuswahl().getSpieler().get(i));
                //System.out.println("test");
                System.out.println(spieler.get(i-1).getVName());
            }



            //TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            spielerVornameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));

            //TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            spielerNachnameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));

            //TableColumn<Spieler,Date> spielerGeburtsdatumSpalte = new TableColumn("Geburtsdatum");
            spielerGeburstdatumSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
            tabelle_SpielerZuordnen.setItems(spieler);
            //TableColumn<Spieler,String> spielerExtSpielerIDSpalte = new TableColumn("ExtSpielerID");
            //spielerExtSpielerIDSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("extSpielerID"));

            //tabelle_SpielerZuordnen.getColumns().addAll(spielerVornameSpalte,spielerNachnameSpalte,spielerGeburtsdatumSpalte);


        }
        else{
            System.out.println("kann Turnier nicht laden");
        }

    }
    @FXML
    public void auswahlSpeichernSpieler(ActionEvent event) throws Exception {
        try {

            if(tabelle_SpielerZuordnen.getSelectionModel().getSelectedItem()!=null)
            {
                Spieler spieler = (Spieler) tabelle_SpielerZuordnen.getSelectionModel().getSelectedItem();
                System.out.println("Ausgewählter Spieler "+spieler.getVName());
                tabelle_SpielerZuordnen.getSelectionModel().getSelectedCells();
                System.out.println(tabelle_SpielerZuordnen.getSelectionModel().toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            printSpielerZuordnenTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
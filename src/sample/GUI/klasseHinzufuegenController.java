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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Enums.AnzahlRunden;
import sample.Enums.Disziplin;
import sample.Enums.Niveau;
import sample.Spieler;
import sample.Spielklasse;
import sample.Turnier;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by jens on 03.08.2017.
 */
public class klasseHinzufuegenController implements Initializable
{

    String baseName = "resources.Main";
    String titel ="";

    @FXML
    private ChoiceBox<Niveau> combo_niveau=new ChoiceBox<>();
    @FXML
    private ChoiceBox <Disziplin> combo_disziplin=new ChoiceBox<>();
//
//    @FXML
//    public ComboBox<Disziplin> combo_disziplin = new ComboBox<>();
//    @FXML
//    public  ComboBox<Niveau> combo_niveau = new ComboBox<>();
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


    @FXML
    private Text t_disziplin;
    @FXML
    private Text t_niveau;
    @FXML
    private Text t_zaehlweise;
    @FXML
    private RadioButton rb_mitVerlaengerung;
    @FXML
    private RadioButton rb_ohneVerlaengerung;
    @FXML
    private Button b_klasseSpeichern;
    @FXML
    private Button b_tabelleLaden;
    @FXML
    private Button b_gebeSpielernameAus;
    @FXML
    private Tab tab_klassendaten;
    @FXML
    private Tab tab_spielerZuordnen;


    private static int index_niveau=0;
    private static int index_diszipin=0;
    private static int index_anzahlRunden=0;

    TurnierDAO t = new TurnierDAOimpl();
    Dictionary<Integer,Turnier> turnierliste = t.getAllTurniere();

    @FXML
    public ComboBox<AnzahlRunden> combo_anzahlRunden = new ComboBox<>();
    @FXML
    private void setDisziplin_auswahl(ActionEvent event){
        index_diszipin = combo_disziplin.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void comboBoxFill() throws IOException {
        try{
            combo_niveau.setItems(FXCollections.observableArrayList(Niveau.values()));
            combo_disziplin.setItems(FXCollections.observableArrayList(Disziplin.values()));


            //combo_anzahlRunden.getItems().setAll("1", "2", "3");
            combo_niveau.getSelectionModel().select(index_niveau);
            combo_disziplin.getSelectionModel().select(index_diszipin);
            //combo_anzahlRunden.getSelectionModel().select(1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void setNiveau_auswahl(ActionEvent event){
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
    private void pressBtn_KlasseSpeichern(ActionEvent event) throws IOException
    {



        Spielklasse spklasse = new Spielklasse(combo_disziplin.getValue(),Niveau.valueOf(String.valueOf(combo_niveau.getValue())),auswahlklasse.getAktuelleTurnierAuswahl());
        spklasse.getSpielklasseDAO().create(spklasse);
        auswahlklasse.getAktuelleTurnierAuswahl().addObs_spielklassen(spklasse);
        auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().put(spklasse.getSpielklasseID(),spklasse);
        //a.getAktuelleTurnierAuswahl().addObs_spielklassen(spklasse);
        System.out.println("------------------Größe = "+auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
        MainController m = new MainController();
        try {
            //m.fuelleCheckCombo();
            //m.reloadcheckbox();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseUebersicht.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
//        for (int i=0; i<a.getStages().size()-1;i++){
//            a.getStages().get(i).close();
//        }

        auswahlklasse.getStagesdict().put("KlasseUebersicht",stage);
        stage.setScene(new Scene(root1));
        stage.show();


        stage.setTitle("Klassenuebersicht: "+auswahlklasse.getAktuelleTurnierAuswahl().getName());

        auswahlklasse.InfoBenachrichtigung("erf","klasse erstellt");
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
    @FXML
    public void printSpielerZuordnenTable() throws Exception {
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {
            ObservableList<Spieler> spieler = FXCollections.observableArrayList();
            Enumeration enumKeys = auswahlklasse.getSpieler().keys();
            while (enumKeys.hasMoreElements()){
                int key = (int) enumKeys.nextElement();
                spieler.add(auswahlklasse.getSpieler().get(key));
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

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_disziplin");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        t_disziplin.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_niveau");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        t_niveau.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_zaehlweise");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        t_zaehlweise.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("rb_mitVerlaengerung");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        rb_mitVerlaengerung.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("rb_ohneVerlaengerung");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        rb_ohneVerlaengerung.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_klasseSpeichern");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_klasseSpeichern.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_klassendaten");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_klassendaten.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_spielerZuordnen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_spielerZuordnen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_tabelleLaden");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_tabelleLaden.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_gebeSpielernameAus");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_gebeSpielernameAus.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("spielerVornameSpalte");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        spielerVornameSpalte.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("spielerNachnameSpalte");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        spielerNachnameSpalte.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("spielerGeburstdatumSpalte");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        spielerGeburstdatumSpalte.setText(titel);



        try {
            printSpielerZuordnenTable();
            comboBoxFill();
            combo_niveau.getSelectionModel().select(index_niveau);
            combo_disziplin.getSelectionModel().select(index_diszipin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

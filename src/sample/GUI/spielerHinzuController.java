package sample.GUI;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import sample.*;
import sample.DAO.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by jens on 03.08.2017.
 */
public class spielerHinzuController implements Initializable, Cloneable
{
//region Deklaration
@FXML
private TextField t_suchleistespielerhinzu;
    @FXML
    private Label l_Meldung;
    @FXML
    private Tab tab_sphin;
    @FXML
    private Tab tab_spbea;
    @FXML
    private Tab tab_spupdate;
    @FXML
    private TabPane tabpane_spieler;
    @FXML
    private ChoiceBox<Verein> combo_verein;
    @FXML
    private TextField t_vn;
    @FXML
    private TextField t_nn;
    @FXML
    private DatePicker d_geb;
    @FXML
    private TextField t_spid;
    @FXML
    private TextField t_re;
    @FXML
    private TextField t_rd;
    @FXML
    private TextField t_rm;
    @FXML
    private RadioButton r_m;
    @FXML
    private RadioButton r_w;

    //Tab2
    @FXML
    private TableView tabelle_spielerliste;
    @FXML
    private TableColumn tabelle_spielerliste_vorname;
    @FXML
    private TableColumn tabelle_spielerliste_nachname;
    @FXML
    private TableColumn tabelle_spielerliste_geschlecht;
    @FXML
    private TableColumn tabelle_spielerliste_verein;
    @FXML
    private TableColumn tabelle_spielerliste_geburtstag;
    @FXML
    private TableColumn tabelle_spielerliste_SpielerID;

    //Tab3
    @FXML
    private ChoiceBox<Verein> combo_verein1;
    @FXML
    private TextField t_vn1;
    @FXML
    private TextField t_nn1;
    @FXML
    private DatePicker d_geb1;
    @FXML
    private TextField t_spid1;
    @FXML
    private TextField t_re1;
    @FXML
    private TextField t_rd1;
    @FXML
    private TextField t_rm1;
    @FXML
    private RadioButton r_m1;
    @FXML
    private RadioButton r_w1;

    //endregion


    HashMap<Integer, Spieler> spielerhash = new HashMap<Integer, Spieler>();

    private static Spieler spieler_neu=null;
    private static ObservableList<Spieler> obs_spieler = auswahlklasse.getObs_spieler();


    private void printSpielerZuordnenTableNeu() throws Exception {

        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {

            obs_spieler.clear();
            Enumeration enumSpielerIDs = auswahlklasse.getSpieler().keys();
            while (enumSpielerIDs.hasMoreElements()){
                int key = (int)enumSpielerIDs.nextElement();
                obs_spieler.add(auswahlklasse.getSpieler().get(key));
                spielerhash.put(auswahlklasse.getSpieler().get(key).getSpielerID(),auswahlklasse.getSpieler().get(key));
            }

            //region PropertyValueFactory
            tabelle_spielerliste_vorname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
            tabelle_spielerliste_geschlecht.setCellValueFactory(new PropertyValueFactory<ImageView,String>("iGeschlecht"));

            tabelle_spielerliste_SpielerID.setCellValueFactory(new PropertyValueFactory<Spieler,String>("ExtSpielerID"));


            tabelle_spielerliste_nachname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));

            tabelle_spielerliste_verein.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));

            tabelle_spielerliste_geburtstag.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
            tabelle_spielerliste.setItems(obs_spieler);
            //endregion

        }
        else{
            System.out.println("kann Turnier nicht laden");
        }

    }

    private void felderLeeren()
    {
        t_vn.setText("");
        t_nn.setText("");
        d_geb.setValue(null);
        t_spid.setText("");

        combo_verein.getSelectionModel().select(0);
        t_re.setText("0");
        t_rd.setText("0");
        t_rm.setText("0");
        r_m.setSelected(true);
        r_w.setSelected(false);


    }

    public static ObservableList<Spieler> getObs_spieler() {
        return obs_spieler;
    }

    public static void setObs_spieler(ObservableList<Spieler> obs_spielerr) {
        obs_spieler = obs_spielerr;
    }

    @FXML
    public void pressBtn_SpielerSpeichern(ActionEvent event) throws Exception
    {


        if(t_vn.getText().equals("")||t_nn.getText().equals("")||t_re.getText().equals("")||t_rd.getText().equals("")||t_rm.getText().equals("")||d_geb.getValue()==null)
        {
            System.out.println("Spielerdaten unvollständig");
            l_Meldung.setText("Spielerdaten unvollständig!");
        }
        else
        {
            //region Spieler erstellen
            l_Meldung.setText("");
            boolean geschlecht = false;
            if (r_m.isSelected())
            {
                geschlecht=true;
            }
            else
            {
                geschlecht=false;
            }
            int []rpunkte = new int[3];
            rpunkte[0]=Integer.parseInt(t_re.getText());
            rpunkte[1]=Integer.parseInt(t_rd.getText());
            rpunkte[2]=Integer.parseInt(t_rm.getText());


            Verein verein = combo_verein.getSelectionModel().getSelectedItem();
            System.out.println(auswahlklasse.getSpieler().size());

        spieler_neu= new Spieler(t_vn.getText(),t_nn.getText(),d_geb.getValue(),geschlecht,rpunkte,verein,t_spid.getText(),"");
        //endregion

        //ArrayList<Spieler> vorhandeneSpieler = new ArrayList<>();

            felderLeeren();
            Enumeration e = auswahlklasse.getSpieler().elements();
            ObservableList obs_vorhanden=FXCollections.observableArrayList();
            while(e.hasMoreElements())
            {
                Spieler sp = (Spieler) e.nextElement();
                if(sp!=null&&spieler_neu!=null&&sp.getVName()!=null&&sp.getNName()!=null) {
                    if (sp.getNName().equalsIgnoreCase(spieler_neu.getNName()) && sp.getVName().equalsIgnoreCase(spieler_neu.getVName())) {

                        System.out.println("Übereinstimmung gefunden:");
                        System.out.println(sp.getVName() + " " + sp.getNName() + " --- " + spieler_neu.getVName() + " " + spieler_neu.getNName());
                        TurnierDAO t;
                        //t = new TurnierDAOimpl();

                        obs_vorhanden.add(sp);

                    }
                }
            }
            ExcelImport.getDict_doppelte_spieler().put(spieler_neu,obs_vorhanden);




        if(obs_vorhanden.size()>0)
        {
           /* ExcelImport.setSpielerzumHinzufeuegen(spieler_neu);
            ExcelImport.setVorhandeneSpieler(vorhandeneSpieler);*/

            pressBtn_Popup(event);
        }
        else
        {
            spieler_neu.getSpielerDAO().create(spieler_neu);
            auswahlklasse.addSpieler(spieler_neu);
            auswahlklasse.getSpieler().put(spieler_neu.getSpielerID(),spieler_neu);
            printSpielerZuordnenTableNeu();
            auswahlklasse.InfoBenachrichtigung("erf",spieler_neu.toString());
        }



        }
    }


//    @FXML
//    public void SpielerSpeichern(ActionEvent event)
//    {
//        obs_spieler.add(spieler_neu);
//        a.addSpieler(spieler_neu);
//        spielerTabelleAktualisieren();
//        felderLeeren();
//
//    }

    public void  spielerTabelleAktualisieren()
    {
        tabelle_spielerliste.refresh();
    }

    public void pressBtn_Popup (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerVorhanden.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        auswahlklasse.getStagesdict().put("SpielerVorhanden",stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.show();
        stage.show();
        stage.setTitle("Spieler vorhanden");
    }

    private void zeigePopup()
    {
        Popup popup = new Popup();
        //CustomController controller = new CustomController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("spielerVorhanden.fxml"));
        loader.setController(this);
        try {
            popup.getContent().add((Parent)loader.load());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void pressBtn_SpielerUpdaten(ActionEvent event) throws Exception
    {

        tab_sphin.setDisable(false);
        tab_spbea.setDisable(false);
        tab_spupdate.setDisable(true);

        tabpane_spieler.getSelectionModel().select(tab_spbea);
        boolean geschlecht = false;
        if (r_m1.isSelected())
        {
            geschlecht=true;
        }
        else
        {
            geschlecht=false;
        }
        int []rpunkte = new int[3];
        rpunkte[0]=Integer.parseInt(t_re1.getText());
        rpunkte[1]=Integer.parseInt(t_rd1.getText());
        rpunkte[2]=Integer.parseInt(t_rm1.getText());




        Verein v = combo_verein1.getSelectionModel().getSelectedItem();


        spieler_neu.setvName(t_vn1.getText());
        spieler_neu.setnName(t_nn1.getText());
        spieler_neu.setgDatum(d_geb1.getValue());
        spieler_neu.setExtSpielerID(t_spid1.getText());
        spieler_neu.setrPunkte(rpunkte);
        spieler_neu.setGeschlecht(geschlecht);
        spieler_neu.setVerein(v);

        boolean erfolg = spieler_neu.getSpielerDAO().update(spieler_neu);

        if(!erfolg)
        {
            auswahlklasse.WarnungBenachrichtigung("Spieler Update Fehler","fehler");
        }
        else
        {
            auswahlklasse.InfoBenachrichtigung("erfolg","erfolg");
        }


        auswahlklasse.getSpieler().remove(spieler_neu.getSpielerID());
        auswahlklasse.getSpieler().put(spieler_neu.getSpielerID(),spieler_neu);
        int index = obs_spieler.indexOf(spieler_neu);
        obs_spieler.remove(spieler_neu);
        obs_spieler.add(index, spieler_neu);
        //Spieler spieler = new Spieler(t_vn1.getText(),t_nn1.getText(),d_geb1.getValue(),a.getSpieler().size()+1,geschlecht,rpunkte,verein,t_spid.getText());
        //System.out.println(spieler.getNName());
        //a.addSpieler(spieler);



        spielerTabelleAktualisieren();
    }


    @FXML
    private void choiceclick(ActionEvent event)
    {
        try {
            ladeVereine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ladeVereine() throws Exception
    {

        System.out.println(auswahlklasse.getVereine().size());
        ObservableList vereine = FXCollections.observableArrayList();
        Enumeration enumKeys = auswahlklasse.getVereine().keys();
        while (enumKeys.hasMoreElements()){
            String key = (String) enumKeys.nextElement();
            vereine.add(auswahlklasse.getVereine().get(key));

        }
        try {
            combo_verein.setItems(vereine);
            combo_verein1.setItems(vereine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        ContextMenu contextMenu = new ContextMenu();



        l_Meldung.setText("");

        //region Tabelle Spielerliste RowFactory
        tabelle_spielerliste.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY)
                {
                    contextMenu.hide();
                }
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spieler clickedRow = (sample.Spieler) row.getItem();
                    UpdateSpieler(clickedRow);
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                }
                if(! row.isEmpty() && event.getButton()== MouseButton.SECONDARY)
                {
                    Spieler clickedRow = (Spieler) row.getItem();
                    MenuItem item1 = new MenuItem("Spieler hinzufügen");
                    item1.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            tabpane_spieler.getSelectionModel().select(tab_sphin);
                        }
                    });
                    MenuItem item2 = new MenuItem("Spieler bearbeiten");
                    item2.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            tabpane_spieler.getSelectionModel().select(tab_spupdate);
                            FuelleFelder(clickedRow);
                            spieler_neu=clickedRow;
                        }
                    });
                    MenuItem item3 = new MenuItem("Spieler löschen");
                    item3.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                                boolean loeschespieler = clickedRow.getSpielerDAO().delete(clickedRow);
                            if(loeschespieler) {
                                obs_spieler.remove(clickedRow);
                                //auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().remove(clickedRow);
                                auswahlklasse.getSpieler().remove(clickedRow);
                                tabelle_spielerliste.refresh();
                                System.out.println("Lösche   " + clickedRow.getNName());
                                auswahlklasse.InfoBenachrichtigung("erf","erfolg");

                            }
                            else
                            {
                                auswahlklasse.WarnungBenachrichtigung("Fehler","fehler");
                            }
                        }
                    });

                    // Add MenuItem to ContextMenu
                    contextMenu.getItems().clear();
                    contextMenu.getItems().addAll(item1, item2, item3);

                    // When user right-click on Circle
                    tabelle_spielerliste.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                        @Override
                        public void handle(ContextMenuEvent event) {

                            contextMenu.show(tabelle_spielerliste, event.getScreenX(), event.getScreenY());
                        }
                    });
                }
            });
            return row ;
        });
//endregion

        try {
            ladeVereine();
            combo_verein.getSelectionModel().select(0);

            printSpielerZuordnenTableNeu();
            spielerTabelleAktualisieren();
        } catch (Exception e) {
            e.printStackTrace();
        }




        if(auswahlklasse.getTab()==3)
        {

            tab_sphin.setDisable(true);
            tab_spbea.setDisable(true);
            tab_spupdate.setDisable(false);
            tabpane_spieler.getSelectionModel().select(tab_spupdate);
            FuelleFelder(auswahlklasse.getUpdateSpieler());
        }

        t_suchleistespielerhinzu.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            obs_spieler.clear();

            tabelle_spielerliste.refresh();
            Enumeration e = auswahlklasse.getSpieler().keys();
            while (e.hasMoreElements()){
                int key = (int) e.nextElement();

                try {
                    if(auswahlklasse.getSpieler().get(key).toString().toUpperCase().contains(t_suchleistespielerhinzu.getText().toUpperCase()))
                    {
                        obs_spieler.add(auswahlklasse.getSpieler().get(key));
                        System.out.println(auswahlklasse.getSpieler().get(key));
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                ;
            }


        });

    }
    private void FuelleFelder(Spieler clickedRow)
    {
        tab_spupdate.setDisable(false);
        tab_spbea.setDisable(true);
        tab_sphin.setDisable(true);
        tabpane_spieler.getSelectionModel().select(tab_spupdate);
        t_vn1.setText(clickedRow.getVName());
        t_nn1.setText(clickedRow.getNName());
        d_geb1.setValue(clickedRow.getGDatum());
        t_spid1.setText(clickedRow.getExtSpielerID());
        t_re1.setText(String.valueOf(clickedRow.getrPunkte()[0]));
        t_rd1.setText(String.valueOf(clickedRow.getrPunkte()[1]));
        t_rm1.setText(String.valueOf(clickedRow.getrPunkte()[2]));
        combo_verein1.getSelectionModel().select(clickedRow.getVerein());
        if(clickedRow.getGeschlecht())
        {
            r_m1.setSelected(true);
        }
        else
        {
            r_w1.setSelected(true);
        }
    }
    private void UpdateSpieler(Spieler clickedRow) {

        if(tabelle_spielerliste.getSelectionModel().getSelectedItem()!=null )
        {
            FuelleFelder(clickedRow);
            System.out.println("geklickt: "+clickedRow.getNName());

            System.out.println("turnier="+auswahlklasse.getAktuelleTurnierAuswahl().getName());

            spieler_neu=clickedRow;
        }

    }
    @FXML
    public void pressBtn_neuerVerein(ActionEvent event) throws Exception {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("neuerVerein.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            auswahlklasse.getStagesdict().put("NeuerVerein",stage);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Neuer Verein");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void pressBtn_Abbrechen(ActionEvent event) throws Exception {
        try {
           // a.getStages().get(0).close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pressBtn_AbbrechenBearbeiten (ActionEvent event) throws Exception {
        try {
            tab_sphin.setDisable(false);
            tab_spbea.setDisable(false);
            tab_spupdate.setDisable(true);
            tabpane_spieler.getSelectionModel().select(tab_spbea);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

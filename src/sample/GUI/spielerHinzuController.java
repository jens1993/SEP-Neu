package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import sample.*;
import sample.DAO.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by jens on 03.08.2017.
 */
public class spielerHinzuController implements Initializable, Cloneable
{

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
    HashMap<Integer, Spieler> spielerhash = new HashMap<Integer, Spieler>();
    auswahlklasse a = new auswahlklasse();

    private static Spieler spieler_neu=null;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();

    private void printSpielerZuordnenTableNeu() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {

            obs_spieler.clear();

            for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpieler().size();i++){
                obs_spieler.add(a.getAktuelleTurnierAuswahl().getSpieler().get(i));
                spielerhash.put(a.getAktuelleTurnierAuswahl().getSpieler().get(i).getSpielerID(),a.getAktuelleTurnierAuswahl().getSpieler().get(i));

            }



            //TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            tabelle_spielerliste_vorname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));

            //TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            tabelle_spielerliste_nachname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
            tabelle_spielerliste_geschlecht.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
            tabelle_spielerliste_verein.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));

            //TableColumn<Spieler,Date> spielerGeburtsdatumSpalte = new TableColumn("Geburtsdatum");
            tabelle_spielerliste_geburtstag.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
            tabelle_spielerliste.setItems(obs_spieler);


            //TableColumn<Spieler,String> spielerExtSpielerIDSpalte = new TableColumn("ExtSpielerID");
            //spielerExtSpielerIDSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("extSpielerID"));

            //tabelle_SpielerZuordnen.getColumns().addAll(spielerVornameSpalte,spielerNachnameSpalte,spielerGeburtsdatumSpalte);


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
            System.out.println(a.getSpieler().size());

        spieler_neu= new Spieler(t_vn.getText(),t_nn.getText(),d_geb.getValue(),a.getSpieler().size()+1,geschlecht,rpunkte,verein,t_spid.getText());
        ArrayList<Spieler> vorhandeneSpieler = new ArrayList<>();

            felderLeeren();
        for(Enumeration e = a.getSpieler().elements();e.hasMoreElements();)
        {
            Spieler sp = (Spieler) e.nextElement();
            if(sp.getNName().equalsIgnoreCase(spieler_neu.getNName()) && sp.getVName().equalsIgnoreCase(spieler_neu.getVName()))
            {

                System.out.println("Übereinstimmung gefunden:");
                System.out.println(sp.getVName()+" "+sp.getNName()+" --- "+spieler_neu.getVName()+" "+spieler_neu.getNName());
                TurnierDAO t;
                t = new TurnierDAOimpl();

                vorhandeneSpieler.add(sp);

            }
        }


        if(vorhandeneSpieler.size()>0)
        {
            a.setSpielerzumHinzufeuegen(spieler_neu);
            a.setVorhandeneSpieler(vorhandeneSpieler);

            pressBtn_Popup(event);
        }
        else
        {
            a.addSpieler(spieler_neu);
            a.getAktuelleTurnierAuswahl().getSpieler().put(spieler_neu.getSpielerID(),spieler_neu);
            printSpielerZuordnenTableNeu();


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
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Popup.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.addStage(stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Spieler vorhanden");
    }
    private void zeigePopup()
    {
        Popup popup = new Popup();
        //CustomController controller = new CustomController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
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
        spieler_neu.getSpielerDAO().update(spieler_neu);

        System.out.println("erf");

        a.getSpieler().remove(spieler_neu.getSpielerID());
        a.getSpieler().put(spieler_neu.getSpielerID(),spieler_neu);
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

        System.out.println(a.getAktuelleTurnierAuswahl().getVereine().size());
        ObservableList vereine = FXCollections.observableArrayList();
        for (int i=1;i<=a.getVereine().size();i++){
            vereine.add(a.getVereine().get(i));

        }
        try {
            System.out.println(vereine.size());

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
                        }
                    });
                    MenuItem item3 = new MenuItem("Spieler löschen");
                    item3.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                                boolean loeschespieler = clickedRow.getSpielerDAO().delete(clickedRow);
                            if(loeschespieler) {
                                obs_spieler.remove(clickedRow);
                                a.getAktuelleSpielklassenAuswahl().getSetzliste().remove(clickedRow);
                                a.getSpieler().remove(clickedRow);
                                tabelle_spielerliste.refresh();
                                System.out.println("Lösche   " + clickedRow.getNName());

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

        try {
            ladeVereine();
            combo_verein.getSelectionModel().select(0);

            printSpielerZuordnenTableNeu();
            spielerTabelleAktualisieren();
        } catch (Exception e) {
            e.printStackTrace();
        }




        if(a.getTab()==3)
        {

            tab_sphin.setDisable(true);
            tab_spbea.setDisable(true);
            tab_spupdate.setDisable(false);
            tabpane_spieler.getSelectionModel().select(tab_spupdate);
            FuelleFelder(a.getUpdateSpieler());
        }

        t_suchleistespielerhinzu.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            obs_spieler.clear();

            tabelle_spielerliste.refresh();
            Enumeration e = a.getSpieler().keys();
            while (e.hasMoreElements()){
                int key = (int) e.nextElement();
                if(a.getSpieler().get(key).toString().toUpperCase().contains(t_suchleistespielerhinzu.getText().toUpperCase()))
                {
                    obs_spieler.add(a.getSpieler().get(key));
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

            System.out.println("turnier="+a.getAktuelleTurnierAuswahl().getName());

            spieler_neu=clickedRow;
        }

    }
    @FXML
    public void pressBtn_neuerVerein(ActionEvent event) throws Exception {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("neuerVerein.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            a.addStage(stage);
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
            a.getStages().get(0).close();
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

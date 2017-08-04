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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.*;
import sample.DAO.*;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class spielerHinzuController implements Initializable, Cloneable
{
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

    auswahlklasse a = new auswahlklasse();

    static Spieler spieler_neu=null;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();

    private void printSpielerZuordnenTable() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {


            for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpieler().size();i++){
                obs_spieler.add(a.getAktuelleTurnierAuswahl().getSpieler().get(i));

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


    @FXML
    public void pressBtn_SpielerSpeichern(ActionEvent event) throws Exception
    {
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
        Spieler spieler = new Spieler(t_vn.getText(),t_nn.getText(),d_geb.getValue(),a.getSpieler().size()+1,geschlecht,rpunkte,verein,t_spid.getText());
        System.out.println(spieler.getNName());
        obs_spieler.add(spieler);
        tabelle_spielerliste.refresh();
        //a.addSpieler(spieler);
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
        a.getSpielerDAO().update(spieler_neu);

        System.out.println("erf");

        a.getSpieler().remove(spieler_neu.getSpielerID());
        a.getSpieler().put(spieler_neu.getSpielerID(),spieler_neu);
        int index = obs_spieler.indexOf(spieler_neu);
        obs_spieler.remove(spieler_neu);
        obs_spieler.add(index, spieler_neu);
        //Spieler spieler = new Spieler(t_vn1.getText(),t_nn1.getText(),d_geb1.getValue(),a.getSpieler().size()+1,geschlecht,rpunkte,verein,t_spid.getText());
        //System.out.println(spieler.getNName());
        //a.addSpieler(spieler);



        tabelle_spielerliste.refresh();
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

        tabelle_spielerliste.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spieler clickedRow = (Spieler) row.getItem();
                    UpdateSpieler(clickedRow);
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                }
            });
            return row ;
        });

        try {
            ladeVereine();
            combo_verein.getSelectionModel().select(0);

            printSpielerZuordnenTable();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void UpdateSpieler(Spieler clickedRow) {

        if(tabelle_spielerliste.getSelectionModel().getSelectedItem()!=null )
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
            System.out.println("geklickt: "+clickedRow.getNName());
            spieler_neu=clickedRow;
                    }

    }


}

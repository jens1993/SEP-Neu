package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.ExcelImport;
import sample.Spiel;
import sample.Spieler;
import sample.Verein;

import javax.jws.Oneway;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class spielerVorhandenController implements Initializable
{

//region Deklaration Fxml
    @FXML TableView popup_tabelle;
    @FXML TableView popup_tabelle2;

    @FXML TableColumn popup_vorname;
    @FXML TableColumn popup_vorname2;

    @FXML TableColumn popup_nachname;
    @FXML TableColumn popup_nachname2;

    @FXML TableColumn popup_nationalitaet;
    @FXML TableColumn popup_nationalitaet2;

    @FXML TableColumn popup_gdatum;
    @FXML TableColumn popup_gdatum2;

    @FXML TableColumn popup_geschlecht;
    @FXML TableColumn popup_geschlecht2;

    @FXML TableColumn popup_verein;
    @FXML TableColumn popup_verein2;

    @FXML TableColumn popup_spielerid;
    @FXML TableColumn popup_spielerid2;

//endregion
ExcelImport excelImport = new ExcelImport();
    Spieler updateSpieler = null;

    //ArrayList<Spieler> vorhandeneSpieler;
    ObservableList<Spieler> obs_vorhandeneSpieler = FXCollections.observableArrayList();
    ObservableList<Spieler> obs_neuerSpieler = FXCollections.observableArrayList();

    @FXML
    public  void btn_UpdateSpielerPopup (ActionEvent event)
    {
        if(updateSpieler!=null)
        {


        updateSpieler.setvName(excelImport.getAktuellerSpieler().getVName());
        updateSpieler.setnName(excelImport.getAktuellerSpieler().getNName());
        updateSpieler.setGeschlecht(excelImport.getAktuellerSpieler().getGeschlecht());
        updateSpieler.setgDatum(excelImport.getAktuellerSpieler().getGDatum());
        updateSpieler.setVerein(excelImport.getAktuellerSpieler().getVerein());
        updateSpieler.setNationalitaet(excelImport.getAktuellerSpieler().getNationalitaet());



        updateSpieler.getSpielerDAO().update(updateSpieler);

        if(excelImport.getDict_doppelte_spieler().size()>0)
        {
            System.out.println("Neuer Frame");
            obs_neuerSpieler.clear();
            obs_vorhandeneSpieler.clear();
            auswahlklasse.getStagesdict().get("SpielerVorhanden").close();
            auswahlklasse.InfoBenachrichtigung("Spieler erfolreich aktualisiert",excelImport.getAktuellerSpieler().toString()+" wurde aktualisiert.");
            excelImport.getSpielerupdate().put(excelImport.getAktuellerSpieler().toString(),excelImport.getAktuellerSpieler());

            Tabellefuelle();

        }
        else {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            try {
                pressBtn_LadeSpielerHinzu(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        }
        else
        {
            auswahlklasse.WarnungBenachrichtigung("Update fehlgeschlagen","Bitte einen Spieler der unteren Tabelle auswählen");
        }
    }
    @FXML
    public void btn_SpeicherSpielerPopup(ActionEvent event)
    {
        Spieler spielerzumHinzufuegen = excelImport.getAktuellerSpieler();
        System.out.println(spielerzumHinzufuegen.getNName());
        spielerzumHinzufuegen.getSpielerDAO().create(spielerzumHinzufuegen);
        if(auswahlklasse.getSpieler().get(spielerzumHinzufuegen)==null) {
            auswahlklasse.addSpieler(spielerzumHinzufuegen);
        }
        System.out.println("Erfolg");

        auswahlklasse.getStagesdict().get("SpielerVorhanden").close();
        auswahlklasse.getSpieler().put(spielerzumHinzufuegen.getSpielerID(),spielerzumHinzufuegen);
        try {
            if(excelImport.getDict_doppelte_spieler().size()>0&&spielerzumHinzufuegen!=null)
            {
                System.out.println("Neuer Frame");
                obs_neuerSpieler.clear();
                obs_vorhandeneSpieler.clear();

                Tabellefuelle();
                auswahlklasse.InfoBenachrichtigung("Spieler erfolreich hinzugefügt",spielerzumHinzufuegen.toString()+" wurde hinzugefügt.");

                if(excelImport.getSpielererfolgreich()!=null)
                {
                if(excelImport.getSpielererfolgreich().get(spielerzumHinzufuegen.toString())==null) {
                    excelImport.getSpielererfolgreich().put(spielerzumHinzufuegen.toString(), spielerzumHinzufuegen);
                }}
                else
                {
                    excelImport.setObs_vorh(null);
                }
            }
            else {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                pressBtn_LadeSpielerHinzu(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //region Button
    public void pressBtn_LadeSpielerHinzu (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielerHinzu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        auswahlklasse.getStagesdict().put("SpielerHinzu",stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Spieler hinzufügen");
    }

    public void SpielerVorhanden (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielerVorhanden.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        auswahlklasse.getStagesdict().put("SpielerVorhanden",stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("SpielerVorhanden");
    }
    //Endregion
    private void Tabellefuelle()
    {
        obs_vorhandeneSpieler=excelImport.getDict_doppelte_spieler().get(excelImport.getAktuellerSpieler());


        obs_neuerSpieler.add(excelImport.getAktuellerSpieler());
        setTable();

        //ExcelImport.getDict_doppelte_spieler().remove(ExcelImport.getAktuellerSpieler());


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        obs_neuerSpieler.clear();
        obs_neuerSpieler.add(excelImport.getAktuellerSpieler());
        obs_vorhandeneSpieler=excelImport.getDict_doppelte_spieler().get(excelImport.getAktuellerSpieler());

        setTable();





      /*  popup_tabelle2.requestFocus();
        popup_tabelle2.getSelectionModel().selectFirst();
        popup_tabelle2.getFocusModel().focus(0);*/
        //popup_tabelle2.refresh();
    }


    private void setTable()
    {
        popup_tabelle2.setItems(obs_vorhandeneSpieler);
        popup_vorname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
        popup_nachname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
        popup_geschlecht2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
        popup_verein2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
        popup_gdatum2.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
        popup_nationalitaet2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("Nationalitaet"));
        popup_spielerid.setCellValueFactory(new PropertyValueFactory<Spieler,Integer>("ExtSpielerID"));

        popup_tabelle.setItems(obs_neuerSpieler);
        popup_vorname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
        popup_nachname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
        popup_geschlecht.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
        popup_verein.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
        popup_gdatum.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
        popup_nationalitaet.setCellValueFactory(new PropertyValueFactory<Spieler,String>("Nationalitaet"));
        popup_spielerid2.setCellValueFactory(new PropertyValueFactory<Spieler,Integer>("ExtSpielerID"));
        popup_tabelle2.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY) {
                    updateSpieler = (Spieler) row.getItem();
                    System.out.println("Spieler ausgewählt "+updateSpieler.getSpielerID()
                            +" "+updateSpieler.getNName());
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                }
            });
            return row ;
        });
    }
}

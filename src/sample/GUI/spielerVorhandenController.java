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
import javafx.stage.Popup;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spieler;
import sample.Verein;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class spielerVorhandenController implements Initializable
{

   @FXML TableView popup_tabelle;
   @FXML TableView popup_tabelle2;

   @FXML TableColumn popup_vorname;
   @FXML TableColumn popup_nachname;
   @FXML TableColumn popup_vorname2;
   @FXML TableColumn popup_nachname2;
    @FXML TableColumn popup_nationalitaet;
    @FXML TableColumn popup_nationalitaet2;
    @FXML TableColumn popup_gdatum2;
    @FXML TableColumn popup_gdatum;
    @FXML TableColumn popup_geschlecht2;
    @FXML TableColumn popup_geschlecht;
    @FXML TableColumn popup_verein;
    @FXML TableColumn popup_verein2;
    @FXML TableColumn popup_spielerid2;



    Spieler updateSpieler = new Spieler();
    auswahlklasse a = new auswahlklasse();
    ArrayList<Spieler> vorhandeneSpieler;
    ObservableList<Spieler> obs_vorhandeneSpieler = FXCollections.observableArrayList();
    ObservableList<Spieler> obs_neuerSpieler = FXCollections.observableArrayList();


    @FXML
    public  void btn_UpdateSpielerPopup (ActionEvent event)
    {
        updateSpieler.setvName(a.getSpielerzumHinzufeuegen().getVName());
        updateSpieler.setnName(a.getSpielerzumHinzufeuegen().getNName());
        updateSpieler.setGeschlecht(a.getSpielerzumHinzufeuegen().getGeschlecht());
        updateSpieler.setgDatum(a.getSpielerzumHinzufeuegen().getGDatum());
        updateSpieler.setVerein(a.getSpielerzumHinzufeuegen().getVerein());
        updateSpieler.setNationalitaet(a.getSpielerzumHinzufeuegen().getNationalitaet());



        updateSpieler.getSpielerDAO().update(updateSpieler);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        try {
            pressBtn_LadeSpielerHinzu(event);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @FXML
    public void btn_SpeicherSpielerPopup(ActionEvent event)
    {
        Spieler spielerzumHinzufuegen = a.getSpielerzumHinzufeuegen();
        System.out.println(spielerzumHinzufuegen.getNName());
        a.addSpieler(spielerzumHinzufuegen);
        a.getAktuelleTurnierAuswahl().getSpieler().put(spielerzumHinzufuegen.getSpielerID(),spielerzumHinzufuegen);
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            pressBtn_LadeSpielerHinzu(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void pressBtn_LadeSpielerHinzu (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielerHinzu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.addStage(stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Spieler hinzufügen");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vorhandeneSpieler=a.getVorhandeneSpieler();

        for(int i=0;i<vorhandeneSpieler.size();i++)
        {

            obs_vorhandeneSpieler.add(vorhandeneSpieler.get(i));

        }
        obs_neuerSpieler.add(a.getSpielerzumHinzufeuegen());
        popup_tabelle2.setItems(obs_vorhandeneSpieler);
        popup_vorname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
        popup_nachname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
        popup_geschlecht2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
        popup_verein2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
        popup_gdatum2.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
        popup_nationalitaet2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("Nationalitaet"));

        System.out.println("Größe = "+obs_neuerSpieler.size());
        popup_tabelle.setItems(obs_neuerSpieler);
        popup_vorname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
        popup_nachname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
        popup_geschlecht.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
        popup_verein.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
        popup_gdatum.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
        popup_nationalitaet.setCellValueFactory(new PropertyValueFactory<Spieler,String>("Nationalitaet"));
        popup_spielerid2.setCellValueFactory(new PropertyValueFactory<Spieler,Integer>("spielerID"));



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

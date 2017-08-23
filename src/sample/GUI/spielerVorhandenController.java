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



    Spieler updateSpieler = new Spieler();

    //ArrayList<Spieler> vorhandeneSpieler;
    ObservableList<Spieler> obs_vorhandeneSpieler = FXCollections.observableArrayList();
    ObservableList<Spieler> obs_neuerSpieler = FXCollections.observableArrayList();
    auswahlklasse a = new auswahlklasse();

    @FXML
    public  void btn_UpdateSpielerPopup (ActionEvent event)
    {
        updateSpieler.setvName(ExcelImport.getAktuellerSpieler().getVName());
        updateSpieler.setnName(ExcelImport.getAktuellerSpieler().getNName());
        updateSpieler.setGeschlecht(ExcelImport.getAktuellerSpieler().getGeschlecht());
        updateSpieler.setgDatum(ExcelImport.getAktuellerSpieler().getGDatum());
        updateSpieler.setVerein(ExcelImport.getAktuellerSpieler().getVerein());
        updateSpieler.setNationalitaet(ExcelImport.getAktuellerSpieler().getNationalitaet());



        updateSpieler.getSpielerDAO().update(updateSpieler);

        if(ExcelImport.getDict_doppelte_spieler().size()>0)
        {
            System.out.println("Neuer Frame");
            obs_neuerSpieler.clear();
            obs_vorhandeneSpieler.clear();

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
    @FXML
    public void btn_SpeicherSpielerPopup(ActionEvent event)
    {
        Spieler spielerzumHinzufuegen = ExcelImport.getAktuellerSpieler();
        System.out.println(spielerzumHinzufuegen.getNName());
        spielerzumHinzufuegen.getSpielerDAO().create(spielerzumHinzufuegen);
        a.addSpieler(spielerzumHinzufuegen);
        System.out.println("Erfolg");
        a.InfoBenachrichtigung("Spieler erfolreich hinzugefügt",spielerzumHinzufuegen.toString()+" wurde hinzugefügt.");
        auswahlklasse.getSpieler().put(spielerzumHinzufuegen.getSpielerID(),spielerzumHinzufuegen);
        try {
            if(ExcelImport.getDict_doppelte_spieler().size()>0)
            {
                System.out.println("Neuer Frame");
                obs_neuerSpieler.clear();
                obs_vorhandeneSpieler.clear();

                Tabellefuelle();

            }
            else {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                pressBtn_LadeSpielerHinzu(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void pressBtn_LadeSpielerHinzu (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielerHinzu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        a.getStagesdict().put("SpielerHinzu",stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Spieler hinzufügen");
    }
    public void SpielerVorhanden (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielerVorhanden.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        a.getStagesdict().put("SpielerVorhanden",stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("SpielerVorhanden");
    }
    private void Tabellefuelle()
    {
        if(a.getDictvorhandenespieler().size()>0) {
            Enumeration e = a.getDictvorhandenespieler().keys();
            Spieler sp = (Spieler) e.nextElement();

            obs_vorhandeneSpieler = ExcelImport.getDict_doppelte_spieler().get(sp.getExtSpielerID());


            obs_neuerSpieler.add(sp);


            setTable();
            ExcelImport.getDict_doppelte_spieler().remove(sp.getExtSpielerID());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (null != ExcelImport.getAktuellerSpieler() && null != ExcelImport.getDict_doppelte_spieler().get(ExcelImport.getAktuellerSpieler())) {
            System.out.println("BBBBBBBBBBBBTest   " + ExcelImport.getDict_doppelte_spieler());

            if (ExcelImport.getDict_doppelte_spieler().get(ExcelImport.getAktuellerSpieler()).size() > 0) {
                setTable();
                Tabellefuelle();

                Enumeration e = auswahlklasse.getDictvorhandenespieler().keys();
                while (e.hasMoreElements()) {
                    Spieler sp = (Spieler) e.nextElement();
                    int key = sp.getSpielerID();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("---------" + auswahlklasse.getDictvorhandenespieler().size());
                    System.out.println("Spieler " + auswahlklasse.getDictvorhandenespieler().get(sp));
                    System.out.println("---------" + auswahlklasse.getDictvorhandenespieler().get(sp).size());
                    obs_neuerSpieler.add(sp);
                    setTable();
                }
                obs_neuerSpieler.add(ExcelImport.getAktuellerSpieler());
            }
        popup_tabelle2.setItems(ExcelImport.getDict_doppelte_spieler().get(ExcelImport.getAktuellerSpieler()));
            popup_tabelle.setItems(obs_neuerSpieler);
        }
    }
       /*                                   vorhandeneSpieler=auswahlklasse.getDictvorhandenespieler().get(sp);

                }*//*


        }
        else if(ExcelImport.getVorhandeneSpieler()!=null&&ExcelImport.getVorhandeneSpieler().size()>0)
        {
        vorhandeneSpieler=ExcelImport.getVorhandeneSpieler();

        for(int i=0;i<vorhandeneSpieler.size();i++)
        {

            obs_vorhandeneSpieler.add(vorhandeneSpieler.get(i));

        }
        obs_neuerSpieler.add(ExcelImport.getSpielerzumHinzufeuegen());






*/






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

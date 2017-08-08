package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spieler;
import sample.Team;

import javax.swing.text.TableView;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class MainController implements Initializable
{
    @FXML
    private javafx.scene.control.TableView tabelle_spiele;


    auswahlklasse a = new auswahlklasse();

    public void pressBtn_laden(ActionEvent event) throws Exception{
        try{
            printSpielTable();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim laden");
        }
    }

    public void pressBtn_spieler(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Spieler");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim laden");
        }
    }
    public void pressBtn_klassen(ActionEvent event) throws Exception {
        try {
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzuGruppe.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseUebersicht.fxml"));
            System.out.println("test");
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Klassenübersicht");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void pressBtn_neuesTurnier(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("neuesTurnier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Neues Turnier");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pressBtn_turnierLaden (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");
    }
    public void pressBtn_teamLaden (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeamUebersicht.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");
    }

    private void printSpielTable() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {
            ObservableList<Spiel> obs_spiele = FXCollections.observableArrayList();

            for (int i=0;i<a.getAktuelleTurnierAuswahl().getGespielteSpiele().size();i++){
                obs_spiele.add(a.getAktuelleTurnierAuswahl().getGespielteSpiele().get(i));
            }
            for (int i=0;i<a.getAktuelleTurnierAuswahl().getAktiveSpiele().size();i++){
                obs_spiele.add(a.getAktuelleTurnierAuswahl().getAktiveSpiele().get(i));
            }
            for (int i=0;i<a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().size();i++){
                obs_spiele.add(a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i));
            }




            TableColumn<Spiel,String> spielHeimSpalte = new TableColumn("Heim");
            spielHeimSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("Heim"));

            TableColumn<Spiel,String> spielGastSpalte = new TableColumn("Gast");
            spielGastSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("Gast"));

            tabelle_spiele.setItems(obs_spiele);


            tabelle_spiele.getColumns().addAll(spielHeimSpalte,spielGastSpalte);


        }
        else{
            System.out.println("kann Turnier nicht laden");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            printSpielTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

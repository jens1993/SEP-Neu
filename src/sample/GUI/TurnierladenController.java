
package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Main;
import sample.Turnier;

import java.net.URL;
import java.util.Date;
import java.util.Dictionary;
import java.util.ResourceBundle;




/**
 * Created by jens on 03.08.2017.
 */

public class TurnierladenController implements Initializable
{
    @FXML
    private TableView <Turnier> TurnierlisteTabelle;
    @FXML
    public TableColumn TurnierDatumSpalte;
    @FXML
    public TableColumn TurnierNameSpalte;
    @FXML
    public TableColumn TurnierIDSpalte;

    auswahlklasse a = new auswahlklasse();
    TurnierDAO t = new TurnierDAOimpl();
    Dictionary<Integer,Turnier> turnierliste = t.getAllTurniere();


    @FXML
    public void auswahlSpeichern(ActionEvent event) throws Exception {
        try {
            System.out.println("Auswahl speichern");

            if(TurnierlisteTabelle.getSelectionModel().getSelectedItem()!=null && (Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem()!= a.getAktuelleTurnierAuswahl())
            {
                a.turnierAuswahlSpeichern((Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem());
                t.read(a.getAktuelleTurnierAuswahl());
                System.out.println(a.getAktuelleTurnierAuswahl().getName());
                a.turnierAuswahlSpeichern(a.getAktuelleTurnierAuswahl());
                System.out.println("Das aktuelle Turnier lautet"+a.getAktuelleTurnierAuswahl().toString());
                Main.getInstance().updateTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());


            }
        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void zeigeTabelle() {
        System.out.println("Print table");
        ObservableList<Turnier> turniere = FXCollections.observableArrayList();
        for (int i = 1; i <= a.getTurnierliste().size(); i++) {
            turniere.add(a.getTurnierliste().get(i));

        }
        TurnierlisteTabelle.setItems(turniere);


        TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Date>("datum"));
        TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, String>("name"));
        TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Integer>("turnierid"));




    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zeigeTabelle();
    }
}

package sample.GUI;

import javafx.application.Application;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Main;
import sample.Spieler;
import sample.Turnier;

import java.net.URL;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.ResourceBundle;


/**
 * Created by jens on 03.08.2017.
 */

public class TurnierladenController implements Initializable
{

    @FXML
    private TextField t_turniersuche;

    @FXML
    public TableView TurnierlisteTabelle;
    @FXML
    public TableColumn TurnierDatumSpalte;
    @FXML
    public TableColumn TurnierNameSpalte;
    @FXML
    public TableColumn TurnierIDSpalte;
    ObservableList<Turnier> turniere = FXCollections.observableArrayList();
    auswahlklasse a = new auswahlklasse();
    TurnierDAO t = new TurnierDAOimpl();
    Dictionary<Integer,Turnier> turnierliste = t.getAllTurniere();
    private static Stage primaryStage;
    public TurnierladenController()
    {
        System.out.println();
    }

    public TurnierladenController(String refresh)
    {

        primaryStage.setTitle(refresh);
    }

    @FXML
    private void zeigeTabelle() {
        System.out.println("Print table");

        for (int i = 1; i <= a.getTurnierliste().size()+1; i++) {
            turniere.add(a.getTurnierliste().get(i));

        }
        TurnierlisteTabelle.setItems(turniere);
        TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,Integer>("turnierid"));
        //TurnierIDSpalte.setCellFactory(integerCellFactory);
        TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,Date>("datum"));
        TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,String>("name"));

        //TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Date>("datum"));
        //TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, String>("name"));

        //TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Integer>("turnierid"));

            }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zeigeTabelle();
        TurnierlisteTabelle.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Turnier clickedRow = (Turnier) row.getItem();
                    printRow(clickedRow);
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                 //   a.getStagesdict().get("")
                }
                else if(! row.isEmpty() && event.getButton()== MouseButton.PRIMARY)
                {
                    System.out.println("KLICK");


                }
            });
            return row ;
        });
        t_turniersuche.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            turniere.clear();

            TurnierlisteTabelle.refresh();
            Enumeration e = a.getTurnierliste().keys();
            while (e.hasMoreElements()){
                int key = (int) e.nextElement();
                if(a.getTurnierliste().get(key).getName().toUpperCase().contains(t_turniersuche.getText().toUpperCase()))
                {
                    turniere.add(a.getTurnierliste().get(key));
                }
                ;
            }


        });
    }
    private void printRow(Turnier item) {

        if(TurnierlisteTabelle.getSelectionModel().getSelectedItem()!=null && (Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem()!= a.getAktuelleTurnierAuswahl())
        {
            a.turnierAuswahlSpeichern((Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem());
            t.read(a.getAktuelleTurnierAuswahl());
            //System.out.println(a.getAktuelleTurnierAuswahl().getName());
            a.turnierAuswahlSpeichern(a.getAktuelleTurnierAuswahl());
            //System.out.println("Das aktuelle Turnier lautet"+a.getAktuelleTurnierAuswahl().toString());
            //Main.getInstance().updateTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());
            //a.setAktuelleTurnierAuswahl
            System.out.println("Turnierauswahl durch Doppelklick: = "+item.getName());
            //((Node)(event.getSource())).getScene().getWindow().hide();
            try {
                MainLaden();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void MainLaden() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        primaryStage = new Stage();
        for (int i=0; i<a.getStages().size();i++){
            a.getStages().get(i).close();
        }
        a.addStage(primaryStage);
        a.addStagesdict(primaryStage,"Main");
        primaryStage.setScene(new Scene(root1));

        primaryStage.show();
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());
    }

    public void pressBtn_neuesTurnier(ActionEvent event) throws Exception {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("neuesTurnier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            a.addStage(stage);
            a.addStagesdict(stage,"NeuesTurnier");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Neues Turnier");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

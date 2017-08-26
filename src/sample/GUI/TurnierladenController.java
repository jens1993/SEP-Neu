
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import sample.DAO.*;
import sample.Feld;
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
    ContextMenu contextMenu=new ContextMenu();
    @FXML
    public TableView TurnierlisteTabelle;
    @FXML
    public TableColumn TurnierDatumSpalte;
    @FXML
    public TableColumn TurnierNameSpalte;
    @FXML
    public TableColumn TurnierIDSpalte;
    ObservableList<Turnier> turniere = FXCollections.observableArrayList();

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
        //System.out.println("Print table");



        Enumeration enumKeys = auswahlklasse.getTurnierliste().keys();

                    while(enumKeys.hasMoreElements()){
                        int key = (int) enumKeys.nextElement();
                        //index = 0-->Altes Turnier oben!
                        turniere.add(0,auswahlklasse.getTurnierliste().get(key));

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
        auswahlklasse a = new auswahlklasse();
        a.readTurnierListe();
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().clear();
        }
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
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY ){
                 contextMenu.hide();
                }
                else if(! row.isEmpty() && event.getButton()== MouseButton.SECONDARY)
                { Turnier clickedRow = (Turnier) row.getItem();

                    //System.out.println("R-KLICK");
                    MenuItem item1 = new MenuItem("Turnier auswählen");
                    item1.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            Turnier clickedRow = (Turnier) row.getItem();
                            printRow(clickedRow);

                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                        }
                    });
                    MenuItem item2 = new MenuItem("Turnier bearbeiten");
                    item2.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                           auswahlklasse.setTurnierzumupdaten(clickedRow);
                            try {
                                pressBtn_neuesTurnier(event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    MenuItem item3 = new MenuItem("Turnier löschen");
                    item3.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            auswahlklasse.setTurnierzumupdaten(clickedRow);
                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                            t.readFelder_Neu(auswahlklasse.getTurnierzumupdaten());
                            int anzahlturnierfelderalt = auswahlklasse.getTurnierzumupdaten().getFelder().size();
                            boolean erfolg = false;
                            FeldDAO feldDAO=new FeldDAOimpl();
                            for (int i = 0; i < anzahlturnierfelderalt; i++) {

                                System.out.println("Lösche Feld");
                                erfolg = feldDAO.deleteFeld(auswahlklasse.getTurnierzumupdaten().getFelder().get(i));

                                if(!erfolg)
                                    break;
                            }
                            if(erfolg)
                                auswahlklasse.InfoBenachrichtigung("Felder gelöscht","erfolg");
                            if (!erfolg)
                                auswahlklasse.WarnungBenachrichtigung("fehler", "fehler");



                            //Spielklasse löschen
                            int anzahlspielklassen = auswahlklasse.getTurnierzumupdaten().getSpielklassen().size();
                            erfolg = false;
                            SpielklasseDAO spielklasseDAO = new SpielklasseDAOimpl();;
                            for (int i = 0; i < anzahlspielklassen; i++) {

                                System.out.println("Lösche Spielklasse");
                                erfolg=spielklasseDAO.delete(auswahlklasse.getTurnierzumupdaten().getSpielklassen().get(i));


                                int anzahlsetzlisten = auswahlklasse.getTurnierzumupdaten().getSpielklassen().get(i).getSetzliste().size();
                                boolean setzlisteloeschen=false;
                                SetzlisteDAO setzlisteDAO=new SetzlisteDAOimpl();



                                if(!erfolg)
                                    break;
                            }
                            if(erfolg)
                                auswahlklasse.InfoBenachrichtigung("Spielklassen gelöscht","erfolg");
                            if (!erfolg)
                                auswahlklasse.WarnungBenachrichtigung("fehler", "fehler");


                            //Turnier löschen
                            boolean erfolg2 = t.delete(clickedRow);
                            if(erfolg2)
                            {
                                auswahlklasse.InfoBenachrichtigung("Löschen erolfreich",clickedRow.getName()+" wurde gelöscht.");
                                auswahlklasse.getTurnierliste().remove(auswahlklasse.getTurnierzumupdaten().getTurnierid());

                            }
                            else
                            {
                                auswahlklasse.WarnungBenachrichtigung("Löschen nicht erolfreich",clickedRow.getName()+" konnte nicht gelöscht werden!");
                            }
                            auswahlklasse.setTurnierzumupdaten(null);
                            turniere.clear();
                            zeigeTabelle();

                        }
                    });
                    if(!contextMenu.isShowing()) {
                        contextMenu.getItems().clear();
                        contextMenu.getItems().addAll(item1, item2, item3);
                    }
                    TurnierlisteTabelle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                        @Override
                        public void handle(ContextMenuEvent event) {

                            if(!contextMenu.isShowing())
                            {
                                contextMenu.show(TurnierlisteTabelle, event.getScreenX(), event.getScreenY());
                            }

                        }
                    });
                }
            });

            return row ;
        });
        t_turniersuche.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            turniere.clear();

            TurnierlisteTabelle.refresh();
            Enumeration e = auswahlklasse.getTurnierliste().keys();
            while (e.hasMoreElements()){
                int key = (int) e.nextElement();
                if(auswahlklasse.getTurnierliste().get(key).getName().toUpperCase().contains(t_turniersuche.getText().toUpperCase()))
                {
                    turniere.add(0,auswahlklasse.getTurnierliste().get(key));
                }
                ;
            }


        });


        TurnierlisteTabelle.getSelectionModel().select(0);

    }
    private void printRow(Turnier item) {

        if(TurnierlisteTabelle.getSelectionModel().getSelectedItem()!=null && (Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem()!= auswahlklasse.getAktuelleTurnierAuswahl())
        {
            auswahlklasse.turnierAuswahlSpeichern((Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem());
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();

            t.read(auswahlklasse.getAktuelleTurnierAuswahl());
            //System.out.println(a.getAktuelleTurnierAuswahl().getName());
            auswahlklasse.turnierAuswahlSpeichern(auswahlklasse.getAktuelleTurnierAuswahl());
            //System.out.println("Das aktuelle Turnier lautet"+a.getAktuelleTurnierAuswahl().toString());
            //Main.getInstance().updateTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());
            //a.setAktuelleTurnierAuswahl
            //System.out.println("Turnierauswahl durch Doppelklick: = "+item.getName());
            //auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
            //System.out.println("t");


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

        auswahlklasse.addStagesdict(primaryStage,"Main");
        primaryStage.setScene(new Scene(root1));

        primaryStage.show();
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Badminton Turnierverwaltung - Turnier: "+auswahlklasse.getAktuelleTurnierAuswahl().getName());
    }

    public void pressBtn_neuesTurnier(ActionEvent event) throws Exception {
        try {
            //((Node)(event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();


            auswahlklasse.addStagesdict(stage,"NeuesTurnier");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("neuesTurnier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            stage.setScene(new Scene(root1));
            stage.show();
            if(auswahlklasse.getTurnierzumupdaten()!=null)
            {
                stage.setTitle(auswahlklasse.getTurnierzumupdaten().getName() +" bearbeiten");
            }
            else {
                stage.setTitle("Neues Turnier");
            }
        } catch(Exception e) {
            e.printStackTrace();

        }
    }

}

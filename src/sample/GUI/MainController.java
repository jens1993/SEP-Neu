package sample.GUI;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import sample.*;
import sample.DAO.auswahlklasse;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static sample.DAO.auswahlklasse.getAktuelleTurnierAuswahl;
import static sample.DAO.auswahlklasse.setSpielAuswahlErgebniseintragen;

/**
 * Created by jens on 03.08.2017.
 */
public class MainController implements Initializable, Observable
{
    final ObservableList<Spielklasse> strings = FXCollections.observableArrayList();
    final CheckComboBox<Spielklasse> checkComboBox = new CheckComboBox<Spielklasse>();
    private Label lspielklassen;

    @FXML
    private HBox hbox_main;
    @FXML
    private CheckBox check_gespielteSpiele= new CheckBox();
    @FXML
    private CheckBox check_aktiveSpiele= new CheckBox();
    @FXML
    private CheckBox check_ausstehendeSpiele= new CheckBox();
    @FXML
    private ChoiceBox choice_spielklassen= new ChoiceBox();
    @FXML
    private javafx.scene.control.TableView tabelle_spiele;

    auswahlklasse a = new auswahlklasse();
    ObservableList <Spielklasse> obs_spielklassen = a.getAktuelleTurnierAuswahl().getObs_spielklassen();
    ObservableList<Spiel> obs_spiele = a.getAktuelleTurnierAuswahl().getObs_spiele();
    ObservableList<Integer> obs_spielklassen_auswahl = a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl();



    public void spieleRefresh(){
        //tabelle_spiele.refresh();

    }

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
            a.addStage(stage);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Spieler hinzufügen");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim laden");
        }
    }
    public void pressBtn_klassen(ActionEvent event) throws Exception {
        try {
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzuGruppe.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseUebersicht.fxml"));
            //System.out.println("test");
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            a.addStage(stage);
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
            a.addStage(stage);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Neues Turnier");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pressBtn_turnierLaden (ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.addStage(stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");
        for(int i=1;i<a.getStages().size();i++){
            a.getStages().get(i).close();
        }
    }
    @FXML
    public void reloadcheckbox()
    {

        obs_spielklassen.clear();
        checkComboBox.getItems().clear();

        //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());

        for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpielklassen().size();i++){
            obs_spielklassen.add(a.getAktuelleTurnierAuswahl().getSpielklassen().get(i));
            System.out.println("größe = "+obs_spielklassen.size());
            //checkComboBox.getItems().add(obs_spielklassen.get(i-1));

        }
        checkComboBox.getItems().setAll(obs_spielklassen);


        System.out.println("Lade CheckCombobox ohne Button");

    }
    @FXML
    public void reloadcheckbox(ActionEvent event)
    {
        obs_spielklassen.clear();
        checkComboBox.getItems().clear();

        //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());

        for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpielklassen().size();i++){
            obs_spielklassen.add(a.getAktuelleTurnierAuswahl().getSpielklassen().get(i));
            System.out.println("größe = "+obs_spielklassen.size());
//            checkComboBox.getItems().add(obs_spielklassen.get(i-1));

        }
        checkComboBox.getItems().setAll(obs_spielklassen);


        System.out.println("test");

    }
    public void pressBtn_Einstellungen (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Einstellungen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.addStage(stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Einstellungen");
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    public void pressBtn_Statistik (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Statistik.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.addStage(stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Statistiken: "+a.getAktuelleTurnierAuswahl().getName());
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }
    public void pressBtn_teamLaden (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeamUebersicht.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.addStage(stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");
    }
    public void pressBtn_drucken(ActionEvent event) throws Exception{
        Turnier turnier = getAktuelleTurnierAuswahl();
        ArrayList<Spiel> spiele = new ArrayList<>();
        spiele.add(turnier.getSpiele().get(1));
        spiele.add(turnier.getSpiele().get(2));
        spiele.add(turnier.getSpiele().get(3));
        spiele.add(turnier.getSpiele().get(4));
        spiele.add(turnier.getSpiele().get(5));
        spiele.add(turnier.getSpiele().get(6));
        turnier.sechSpielzettelDrucken(spiele);
    }
    private final List<InvalidationListener> listeners = new LinkedList<>();
    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }
    public void invalidate() {
        for (InvalidationListener listener : listeners) {
            try {
                listener.invalidated(this);
            } catch (RuntimeException ex) {
            }
        }
    }
    @FXML
    private void fuelleSpielElemente() throws Exception{





        obs_spiele.clear();
        obs_spiele.removeAll();


        int id=0;
        if(obs_spielklassen_auswahl!=null&&obs_spielklassen_auswahl.size()>0) {
            if (check_gespielteSpiele.isSelected()) {
                for (int j = 0; j <= obs_spielklassen_auswahl.size(); j++) {

                    id = obs_spielklassen_auswahl.get(j) + 1;
                    //System.out.println("id= " + id);
                    for (int i = 0; i < a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size(); i++) {


                        if (a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        if (id != 0 && id == a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            obs_spiele.add(a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i));
                            // System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }

            if (check_aktiveSpiele.isSelected()) {
                for (int j = 0; j <= obs_spielklassen_auswahl.size(); j++) {
                    id = obs_spielklassen_auswahl.get(j) + 1;
                    //System.out.println("id= " + id);
                    for (int i = 0; i < a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size(); i++) {


                        if (a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        if (id != 0 && id == a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            obs_spiele.add(a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i));
                            //System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }
            if (check_ausstehendeSpiele.isSelected()) {
                for (int j = 0; j <= obs_spielklassen_auswahl.size(); j++) {
                    id = obs_spielklassen_auswahl.get(j) + 1;
                    //System.out.println("id= " + id);
                    for (int i = 0; i < a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size(); i++) {


                        if (a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        boolean frei = a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getGast().isFreilos();

                        if (id != 0 && id == a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getSpielsystem().getSpielklasse() != null) {

                            if(a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getGastString()=="Freilos"||
                                    a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getGast()==null)
                            {}
                            else {
                                obs_spiele.add(a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i));
                            }
                            //System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }
        }


        obs_spiele.addListener(new ListChangeListener<Spiel>() {
            @Override
            public void onChanged(Change<? extends Spiel> c) {
                // System.out.println("Changed on " + c.toString());
                tabelle_spiele.refresh();

                if (c.next()) {
                    //System.out.println(c.getFrom());
                }
            }
        });
/*        obs_spielklassen.addListener(new ListChangeListener<Spielklasse>() {
            @Override
            public void onChanged(Change<? extends Spielklasse> c) {
                // System.out.println("Changed on " + c.toString());
                tabelle_spiele.refresh();
                System.out.println("aufruf listener");
                reloadcheckbox();
                if (c.next()) {
                    if(obs_spielklassen.get(c.getFrom())!=null)
                    {
                    //checkComboBox.getItems().add(obs_spielklassen.get(c.getFrom()));

                        System.out.println(checkComboBox.getItems().size()+"*-");}
                }
            }
        });*/
    }

    private void printSpielTable() throws Exception {
        tabelle_spiele.getColumns().removeAll();
        if(a.getAktuelleTurnierAuswahl()!=null) {



            TableColumn < Spiel, String > spielFeldSpalte = new TableColumn("Feld");
            spielFeldSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("FeldNr"));
            spielFeldSpalte.setCellFactory(column -> {
                return new TableCell<Spiel, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty); //This is mandatory

                        if (item == null || empty) { //If the cell is empty
                            setText(null);
                            setStyle("");
                        } else { //If the cell is not empty

                            setText(item); //Put the String data in the cell

                            //We get here all the info of the Person of this row
                            Spiel spiel = getTableView().getItems().get(getIndex());

                            // Style all persons wich name is "Edgard"
                            if (spiel.getStatus()==3) {
                                setTextFill(Color.RED);

                            }
                            else if (spiel.getStatus()==2) {
                                setTextFill(Color.DARKBLUE);
                            }
                            else {
                                //Here I see if the row of this cell is selected or not
                                if(getTableView().getSelectionModel().getSelectedItems().contains(spiel))
                                    setTextFill(Color.WHITE);
                                else
                                    setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
            });

            TableColumn<Spiel,String> spielHeimSpalte = new TableColumn("Heim");
            spielHeimSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("HeimString"));
            spielHeimSpalte.setCellFactory(column -> {
                return new TableCell<Spiel, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty); //This is mandatory

                        if (item == null || empty) { //If the cell is empty
                            setText(null);
                            setStyle("");
                        } else { //If the cell is not empty

                            setText(item); //Put the String data in the cell

                            //We get here all the info of the Person of this row
                            Spiel spiel = getTableView().getItems().get(getIndex());

                            // Style all persons wich name is "Edgard"
                            setAlignment(Pos.CENTER_RIGHT);
                            if (spiel.getStatus()==3) {
                                setTextFill(Color.RED);

                            }
                            else if (spiel.getStatus()==2) {
                                setTextFill(Color.DARKBLUE);
                            }
                            else if (spiel.getStatus()==1) {
                                setTextFill(Color.DARKGREEN);
                            }
                            else {
                                //Here I see if the row of this cell is selected or not
                                if(getTableView().getSelectionModel().getSelectedItems().contains(spiel))
                                    setTextFill(Color.WHITE);
                                else
                                    setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
            });

            TableColumn<Spiel,String> spielGastSpalte = new TableColumn("Gast");
            spielGastSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("GastString"));
            spielGastSpalte.setCellFactory(column -> {
                return new TableCell<Spiel, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty); //This is mandatory

                        if (item == null || empty) { //If the cell is empty
                            setText(null);
                            setStyle("");
                        } else { //If the cell is not empty

                            setText(item); //Put the String data in the cell

                            //We get here all the info of the Person of this row
                            Spiel spiel = getTableView().getItems().get(getIndex());

                            // Style all persons wich name is "Edgard"
                            if (spiel.getStatus()==3) {
                                setTextFill(Color.RED);
                            }
                            else if (spiel.getStatus()==2) {
                                setTextFill(Color.DARKBLUE);
                            }
                            else if (spiel.getStatus()==1) {
                                setTextFill(Color.DARKGREEN);
                            }
                            else {
                                //Here I see if the row of this cell is selected or not
                                if(getTableView().getSelectionModel().getSelectedItems().contains(spiel))
                                    setTextFill(Color.WHITE);
                                else
                                    setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
            });
            TableColumn<Spiel,String> spielErgebnisSpalte = new TableColumn("Ergebnis");
            spielErgebnisSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("ErgebnisString"));
            spielErgebnisSpalte.setCellFactory(column -> {
                return new TableCell<Spiel, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty); //This is mandatory

                        if (item == null || empty) { //If the cell is empty
                            setText(null);
                            setStyle("");
                        } else { //If the cell is not empty

                            setText(item); //Put the String data in the cell

                            //We get here all the info of the Person of this row
                            Spiel spiel = getTableView().getItems().get(getIndex());

                            // Style all persons wich name is "Edgard"
                            if (spiel.getStatus()==3) {
                                setTextFill(Color.RED);
                            }
                            else if (spiel.getStatus()==2) {
                                setTextFill(Color.DARKBLUE);
                            }
                            else if (spiel.getStatus()==1) {
                                setTextFill(Color.DARKGREEN);
                            } else {
                                //Here I see if the row of this cell is selected or not
                                if(getTableView().getSelectionModel().getSelectedItems().contains(spiel))
                                    setTextFill(Color.WHITE);
                                else
                                    setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
            });
            TableColumn<Spiel,String> spielSpielklasseSpalte = new TableColumn("Spielklasse");
            spielSpielklasseSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("SpielklasseString"));
            spielSpielklasseSpalte.setCellFactory(column -> {
                return new TableCell<Spiel, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty); //This is mandatory

                        if (item == null || empty) { //If the cell is empty
                            setText(null);
                            setStyle("");
                        } else { //If the cell is not empty

                            setText(item); //Put the String data in the cell

                            //We get here all the info of the Person of this row
                            Spiel spiel = getTableView().getItems().get(getIndex());

                            // Style all persons wich name is "Edgard"
                            if (spiel.getStatus()==3) {
                                setTextFill(Color.RED);
                            }
                            else if (spiel.getStatus()==2) {
                                setTextFill(Color.DARKBLUE);
                            }
                            else if (spiel.getStatus()==1) {
                                setTextFill(Color.DARKGREEN);
                            } else {
                                //Here I see if the row of this cell is selected or not
                                if(getTableView().getSelectionModel().getSelectedItems().contains(spiel))
                                    setTextFill(Color.WHITE);
                                else
                                    setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
            });

            tabelle_spiele.setItems(obs_spiele);

            tabelle_spiele.getColumns().addAll(spielFeldSpalte,spielHeimSpalte,spielGastSpalte,spielErgebnisSpalte,spielSpielklasseSpalte);
            /*tabelle_spiele.setRowFactory( tv -> {
                TableRow<Spiel> row = new TableRow<>();
                if(row.getItem().getStatus()==3){
                    row.setStyle("-fx-background-color: deeppink");
                }
                return row;
            });*/

/*
            tabelle_spiele.setRowFactory(row -> new TableRow<Spiel>(){
                @Override
                public void updateItem(Spiel spiel, boolean empty){
                    super.updateItem(spiel, empty);

                    if (spiel == null || empty) {
                        setStyle("");
                    } else {
                        //Now 'spiel' has all the info of the Person in this row
                        if (spiel.getStatus()==3) {
                            //We apply now the changes in all the cells of the row
                            for(int i=0; i<getChildren().size();i++){
                                ((Labeled) getChildren().get(i)).setTextFill(Color.AQUAMARINE);
                                ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: deeppink");
                            }
                        } else {
                            if(getTableView().getSelectionModel().getSelectedItems().contains(spiel)){
                                for(int i=0; i<getChildren().size();i++){
                                    ((Labeled) getChildren().get(i)).setTextFill(Color.WHITE);;
                                }
                            }
                            else{
                                for(int i=0; i<getChildren().size();i++){
                                    ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);;
                                }
                            }
                        }
                    }
                }
            });*/
        }

        else{
            System.out.println("kann Turnier nicht laden");
        }
    }

    private void turnierLaden() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setAlwaysOnTop(true);
        stage.show();
        stage.setTitle("Turnier auswählen");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();


        tabelle_spiele.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY)
                {
                    contextMenu.hide();
                }
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spiel clickedRow = (Spiel) row.getItem();
                    //UpdateSpieler(clickedRow);
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                }
                if(! row.isEmpty() && event.getButton()== MouseButton.SECONDARY)
                {
                    Spiel clickedRow = (Spiel) row.getItem();
                    MenuItem item1 = new MenuItem("Spieldetails anzeigen");
                    item1.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                        }
                    });
                    MenuItem item2 = new MenuItem("Ergebnisse eintragen");
                    item2.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("ERGEBNISSSSSE");
                            //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                            //FuelleFelder(clickedRow);
                            a.setSpielAuswahlErgebniseintragen(clickedRow);
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielergebnisEingeben.fxml"));
                            Parent root1 = null;
                            try {
                                root1 = (Parent) fxmlLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root1));
                            stage.setAlwaysOnTop(true);
                            stage.show();
                            stage.setTitle("Ergebnisse eintragen - "+a.getSpielAuswahlErgebniseintragen().getFeld() );

                        }
                    });
                    Menu  item3 = new Menu ("Felder zuweisen");
                    item3.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    Menu  item4 = new Menu("in Vorbereitung setzen");
                    item4.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item5 = new MenuItem("Spiel zurückziehen");
                    item5.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item6 = new MenuItem("Spielzettel drucken");
                    item6.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item7 = new MenuItem("Spiel auf anderes Feld verlegen");
                    item7.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item8 = new MenuItem("Ergebnis korrigieren");
                    item8.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });

                    contextMenu.getItems().clear();
                    //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt


                    if(clickedRow.getStatus()==1)
                    {
                        //ausstehend
                        Feld[] feld = new Feld[a.getAktuelleTurnierAuswahl().getFelder().size()];
                        Feld[] feld2 = new Feld[a.getAktuelleTurnierAuswahl().getFelder().size()];
                        Spiel spiel;
                        for(int i=0;i<a.getAktuelleTurnierAuswahl().getFelder().size();i++)
                        {

                            spiel=a.getAktuelleTurnierAuswahl().getFelder().get(i).getAktivesSpiel();

                            if(spiel==null)
                            {
                                feld[i]=a.getAktuelleTurnierAuswahl().getFelder().get(i);
                            }
                            else
                            {
                                spiel=a.getAktuelleTurnierAuswahl().getFelder().get(i).getInVorbereitung();
                                if(spiel==null)
                                {
                                    feld2[i]=a.getAktuelleTurnierAuswahl().getFelder().get(i);
                                }
                            }




                        }
                        MenuItem[] childMenu1 = new MenuItem[feld.length];
                        MenuItem[] childMenu2 = new MenuItem[feld2.length];
                        if(feld.length>0)
                        {
                            for(int i =0;i<feld.length;i++) {
                                final int ii = i;

                                childMenu1[i] = new MenuItem(feld[i].toString());

                                childMenu1[i].setOnAction(new EventHandler<ActionEvent>() {

                                    @Override
                                    public void handle(ActionEvent event) {
                                        System.out.println("Feld = " + feld[ii]);
                                        clickedRow.setFeld(feld[ii]);
                                        clickedRow.setStatus(2);
                                        a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().remove(clickedRow);
                                        a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().add(clickedRow);
                                    }
                                });


                                item3.getItems().add(childMenu1[i]);
                            }
                        }
                        if(feld2.length>0)
                        {
                            for(int i =0;i<feld2.length;i++)
                            {
                                if(feld2[i]!=null) {
                                    childMenu2[i] = new MenuItem(feld2[i].toString());
                                    item4.getItems().add(childMenu2[i]);
                                }
                            }}
                        contextMenu.getItems().addAll(item3, item4);
                    }
                    if(clickedRow.getStatus()==2)
                    {   //aktiv
                        contextMenu.getItems().addAll(item1, item2,item5,item6,item7);
                    }
                    if(clickedRow.getStatus()==3)
                    {
                        //gespielt
                        contextMenu.getItems().addAll(item1, item8);
                    }
                    // Add MenuItem to ContextMenu



                    // When user right-click on Circle
                    tabelle_spiele.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                        @Override
                        public void handle(ContextMenuEvent event) {

                            contextMenu.show(tabelle_spiele, event.getScreenX(), event.getScreenY());
                        }
                    });
                }
            });
            return row ;
        });

        // create the data to show in the CheckComboBox

        // Create the CheckComboBox with the data


        // and listen to the relevant events (e.g. when the selected indices or
        // selected items change).
        checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Spielklasse>() {
            public void onChanged(ListChangeListener.Change<? extends Spielklasse> c) {
                //System.out.println(checkComboBox.getCheckModel().getCheckedIndices());
                obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedIndices();
                //System.out.println(obs_spielklassen_auswahl);
                try {
                    fuelleSpielElemente();
                    tabelle_spiele.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        lspielklassen = new Label("Spielklassen");

        hbox_main.getChildren().add(lspielklassen);
        hbox_main.getChildren().add(checkComboBox);
        hbox_main.getChildren().add(check_aktiveSpiele);
        hbox_main.getChildren().add(check_ausstehendeSpiele);
        hbox_main.getChildren().add(check_gespielteSpiele);
        check_aktiveSpiele.setText("Aktive Spiele");
        check_aktiveSpiele.setSelected(true);
        check_ausstehendeSpiele.setText("Ausstehende Spiele");
        check_ausstehendeSpiele.setSelected(true);
        check_gespielteSpiele.setText("Gespielte Spiele");
        check_gespielteSpiele.setSelected(true);
        checkComboBox.setMaxWidth(250);
        checkComboBox.getItems().setAll(obs_spielklassen);

        check_aktiveSpiele.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    fuelleSpielElemente();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        check_ausstehendeSpiele.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    fuelleSpielElemente();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        check_gespielteSpiele.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    fuelleSpielElemente();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        reloadcheckbox();
        try {
            //urnierLaden();

            printSpielTable();

            fuelleSpielElemente();
            for(int i=0;i<obs_spielklassen.size();i++)
            {
                checkComboBox.getCheckModel().check(i);
            }
            obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedIndices();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

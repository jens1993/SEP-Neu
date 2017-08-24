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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private VBox vbox_main;
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
    @FXML
    private Tab tab_turnierbaum = new Tab();

    auswahlklasse a = new auswahlklasse();

//wieso integer?
    //wenn integer dann die ids abgehen


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

            a.addStagesdict(stage,"SpielerHinzu");
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

            a.addStagesdict(stage,"Klassenuebersicht");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Klassenübersicht");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void pressBtn_Felder(ActionEvent event) throws Exception {
        try {
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzuGruppe.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FeldZuweisung.fxml"));
            //System.out.println("test");
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            a.addStagesdict(stage,"FeldZuweisung");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Felder zuweisen");
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
            a.addStagesdict(stage,"NeuesTurnier");
            stage.setTitle("Neues Turnier");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pressBtn_turnierLaden (ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        a.addStagesdict(stage,"Turnierladen");
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");
        a.getStagesdict().get("Main").close();
    }
    /*
    @FXML
    public void reloadcheckbox()
    {

        a.getAktuelleTurnierAuswahl().getObs_spiele().clear();
        checkComboBox.getItems().clear();

        //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());
        Enumeration enumKeys = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().keys();

        while(enumKeys.hasMoreElements()){
            int key = (int) enumKeys.nextElement();
            a.getAktuelleTurnierAuswahl().getObs_spielklassen().add(a.getAktuelleTurnierAuswahl().getSpielklassen().get(key));
            //System.out.println("größe = "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
            //checkComboBox.getItems().add(obs_spielklassen.get(i-1));

        }
//        hbox_main.getChildren().remove(checkComboBox);
//        hbox_main.getChildren().add(checkComboBox);
        checkComboBox.getItems().setAll(a.getAktuelleTurnierAuswahl().getObs_spielklassen());


        System.out.println("Lade CheckCombobox ohne Button");

    }*/
    @FXML
    public void reloadcheckbox(ActionEvent event)
    {
        a.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
        checkComboBox.getItems().clear();

        //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());

        Enumeration enumKeys = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().keys();
        while(enumKeys.hasMoreElements()){
            int key = (int) enumKeys.nextElement();
            a.getAktuelleTurnierAuswahl().getObs_spielklassen().add(a.getAktuelleTurnierAuswahl().getSpielklassen().get(key));
            System.out.println("größe = "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
//            checkComboBox.getItems().add(obs_spielklassen.get(i-1));

        }
        checkComboBox.getItems().setAll(a.getAktuelleTurnierAuswahl().getObs_spielklassen());


        System.out.println("test");

    }
    public void pressBtn_Einstellungen (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Einstellungen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        a.addStagesdict(stage,"Einstellungen");
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Einstellungen");
        //((Node)(event.getSource())).
        // getScene().getWindow().hide();
    }
    @FXML
    public void pressBtn_Statistik (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Statistik.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.getStagesdict().put("Statistik",stage);
        stage.setScene(new Scene(root1));
        stage.show();
        a.addStagesdict(stage,"Statistiken");
        stage.setTitle("Statistiken: "+a.getAktuelleTurnierAuswahl().getName());
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }
    public void pressBtn_teamLaden (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeamUebersicht.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();


        stage.setScene(new Scene(root1));
        stage.show();
        a.addStagesdict(stage,"TurnierAuswählen");
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
        spiele.get(1).spielzettelDrucken();
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

        a.getAktuelleTurnierAuswahl().getObs_spiele().clear();
        a.getAktuelleTurnierAuswahl().getObs_spiele().removeAll();


        int id=0;
        if(a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl()!=null&&a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size()>0) {
            if (check_gespielteSpiele.isSelected()) {
                for (int j = 0; j < a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); j++) { //<=?
                    id = a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(j); //+1?
                    //System.out.println("id= " + id);
                    for (int i = 0; i < a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size(); i++) {


                        if (a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        if (id != 0 && id == a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            a.getAktuelleTurnierAuswahl().getObs_spiele().add(a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i));
                            // System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }

            if (check_aktiveSpiele.isSelected()) {
                for (int j = 0; j < a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); j++) { //<=?
                    id = a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(j);//+1
                    //System.out.println("id= " + id);
                    for (int i = 0; i < a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size(); i++) {


                        if (a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        if (id != 0 && id == a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            a.getAktuelleTurnierAuswahl().getObs_spiele().add(a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i));
                            //System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }
            if (check_ausstehendeSpiele.isSelected()) {
                for (int j = 0; j < a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); j++) { //<=?
                    id = a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(j); //+1
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
                                a.getAktuelleTurnierAuswahl().getObs_spiele().add(a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i));
                            }
                            //System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }
        }

//
//        obs_spiele.addListener(new ListChangeListener<Spiel>() {
//            @Override
//            public void onChanged(Change<? extends Spiel> c) {
//                // System.out.println("Changed on " + c.toString());
//                tabelle_spiele.refresh();
//
//                if (c.next()) {
//                    //System.out.println(c.getFrom());
//                }
//            }
//        });
//        obs_spielklassen.addListener(new ListChangeListener<Spielklasse>() {
//            @Override
//            public void onChanged(Change<? extends Spielklasse> c) {
//                // System.out.println("Changed on " + c.toString());
//                tabelle_spiele.refresh();
//                System.out.println("aufruf listener");
//                reloadcheckbox();
//                if (c.next()) {
//                    if(obs_spielklassen.get(c.getFrom())!=null)
//                    {
//                    //checkComboBox.getItems().add(obs_spielklassen.get(c.getFrom()));
//
//                        System.out.println(checkComboBox.getItems().size()+"*-");}
//                }
//            }
//        });

        tabelle_spiele.setItems(a.getAktuelleTurnierAuswahl().getObs_spiele());

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

            //tabelle_spiele.setItems(obs_spiele);

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
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
        a.addStagesdict(stage,"TurnierAuswählen");
        stage.setTitle("Turnier auswählen");

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();

        canvasKlassenvisualisierungErstellen();


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
                        ArrayList<Feld> feld =new ArrayList<>();
                        ArrayList<Feld> feld2 =new ArrayList<>();

                        Spiel spiel;
                        for(int i=0;i<a.getAktuelleTurnierAuswahl().getFelder().size();i++)
                        {

                            spiel=a.getAktuelleTurnierAuswahl().getFelder().get(i).getAktivesSpiel();

                            if(spiel==null)
                            {
                                feld.add(a.getAktuelleTurnierAuswahl().getFelder().get(i));
                            }
                            else
                            {
                                spiel=a.getAktuelleTurnierAuswahl().getFelder().get(i).getInVorbereitung();
                                if(spiel==null)
                                {
                                    feld2.add(a.getAktuelleTurnierAuswahl().getFelder().get(i));
                                }
                            }




                        }
                        MenuItem[] childMenu1 = new MenuItem[feld.size()];
                        MenuItem[] childMenu2 = new MenuItem[feld2.size()];
                        if(feld.size()>0)
                        {
                            for(int i =0;i<feld.size();i++) {
                                final int ii = i;

                                if(feld.get(i)!=null) {
                                    childMenu1[i] = new MenuItem(feld.get(i).toString());

                                    childMenu1[i].setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("Feld = " + feld.get(ii));
                                            clickedRow.setFeld(feld.get(ii));
                                            clickedRow.setStatus(2);
                                            a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().remove(clickedRow);
                                            a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().add(clickedRow);
                                        }
                                    });


                                    item3.getItems().add(childMenu1[i]);
                                }
                            }
                        }
                        if(feld2.size()>0)
                        {
                            for(int i =0;i<feld2.size();i++)
                            {
                                if(feld2.get(i)!=null) {
                                    childMenu2[i] = new MenuItem(feld2.get(i).toString());
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
                //obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedIndices();
                a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
                for(int i=0;i<checkComboBox.getCheckModel().getCheckedItems().size();i++)
                {
                    a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().add(checkComboBox.getCheckModel().getCheckedItems().get(i).getSpielklasseID());

                }
                //System.out.println(obs_spielklassen_auswahl);
                try {
                    System.out.println("Auswahl geändert"+ checkComboBox.getCheckModel().getCheckedIndices());
                    fuelleSpielElemente();
//                    tabelle_spiele.getItems().clear();
//                    tabelle_spiele.setItems(obs_spiele);
//                    tabelle_spiele.refresh();
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
        checkComboBox.getItems().setAll(a.getAktuelleTurnierAuswahl().getObs_spielklassen());

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
        tabelle_spiele.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("maus event");


                if(check_gespielteSpiele.isSelected())
                {
                    check_gespielteSpiele.setSelected(false);
                    check_gespielteSpiele.setSelected(true);
                }
                else
                {
                    check_gespielteSpiele.setSelected(true);
                    check_gespielteSpiele.setSelected(false);
                }
                if(check_ausstehendeSpiele.isSelected())
                {
                    check_ausstehendeSpiele.setSelected(false);
                    check_ausstehendeSpiele.setSelected(true);
                }
                else
                {
                    check_ausstehendeSpiele.setSelected(true);
                    check_ausstehendeSpiele.setSelected(false);
                }
                if(check_aktiveSpiele.isSelected())
                {
                    check_aktiveSpiele.setSelected(false);
                    check_aktiveSpiele.setSelected(true);
                }
                else
                {
                    check_aktiveSpiele.setSelected(true);
                    check_aktiveSpiele.setSelected(false);
                }



                //System.out.println(tabelle_spiele.getItems().size()+"   "+a.getAktuelleTurnierAuswahl().getObs_spiele().size());

//                for(int i=0;i<a.getAktuelleTurnierAuswahl().getObs_spiele().size();i++)
//                {
//
//                    //System.out.println(a.getAktuelleTurnierAuswahl().getObs_spiele().get(i).getGast().isFreilos());
//                    System.out.println(a.getAktuelleTurnierAuswahl().getObs_spiele().get(i).toString().toUpperCase().contains("FREILOS"));
//                }


                System.out.println(checkComboBox.getItems().size()+"   "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());

//////////EVENTUELL STATT DICTIONARY OBS LIST
                if(checkComboBox.getItems().size()!=a.getAktuelleTurnierAuswahl().getObs_spielklassen().size())
                {
                    System.out.println("Ungleiche spielklassen anzahl");
                    //checkComboBox.getItems().setAll(a.getAktuelleTurnierAuswahl().getObs_spielklassen());
                    //a.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
                    checkComboBox.getItems().clear();

                    //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());
//                    Enumeration enumKeys = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().keys();
//
//                    while(enumKeys.hasMoreElements()){
//                        int key = (int) enumKeys.nextElement();
//                        a.getAktuelleTurnierAuswahl().getObs_spielklassen().add(a.getAktuelleTurnierAuswahl().getSpielklassen().get(key));
//                        System.out.println("größe = "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
//                        //checkComboBox.getItems().add(obs_spielklassen.get(i-1));
//
//                    }
//        hbox_main.getChildren().remove(checkComboBox);
//        hbox_main.getChildren().add(checkComboBox);
                    checkComboBox.getItems().setAll(a.getAktuelleTurnierAuswahl().getObs_spielklassen());

                    for(int i =0;i<a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size();i++)
                    {
                        checkComboBox.getCheckModel().check(a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(i));
                    }

                }





            }
        });

        //reloadcheckbox();
        try {
            //urnierLaden();

            printSpielTable();

            fuelleSpielElemente();
            for(int i=0;i<a.getAktuelleTurnierAuswahl().getObs_spielklassen().size();i++)
            {
                checkComboBox.getCheckModel().check(i);
            }
            //obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedItems();
            a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
            for(int i=0;i<checkComboBox.getCheckModel().getCheckedItems().size();i++)
            {
                a.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().add(checkComboBox.getCheckModel().getCheckedItems().get(i).getSpielklasseID());
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
//        System.out.println("----------------");
//        for(int i=0;i<obs_spiele.size();i++)
//        {
//            System.out.println("Staus: "+obs_spiele.get(i).getStatus());
//            System.out.println("Klasse: "+obs_spiele.get(i).getSpielklasseString());
//
//        }
//
//        System.out.println("----------------");
//        System.out.println("----------------");
//        System.out.println("----------------");
//        System.out.println(obs_spielklassen);
//        System.out.println("----------------");
//        System.out.println("----------------");
//        System.out.println("----------------");
//        System.out.println(obs_spielklassen_auswahl);

    }

    private void canvasKlassenvisualisierungErstellen() {
        Canvas canvas = new Canvas(1600,800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(0);
        Dictionary<Integer,Spiel> alleSpiele = spielklasse.getSpiele();

        Enumeration e = alleSpiele.keys();
        while(e.hasMoreElements()){
            int key =(int) e.nextElement();
            Spiel spiel = alleSpiele.get(key);
        }
        //////////////////////////////////////////////////////////////////
        int anzahlSpiele = alleSpiele.size();
        double anzahlTeilnehmerDouble = (((Math.sqrt(1 + anzahlSpiele*2*4))/2*2)+1)/2;     //(1/2) + (((1/4) + anzahlSpiele*2)^(1/2))
        int anzahlTeilnehmer = (int) anzahlTeilnehmerDouble;
        //System.out.println(anzahlTeilnehmer);

        // vierer-übersicht ->
        gc.beginPath();
        gc.setStroke(Color.RED); gc.setLineWidth(5);
        gc.moveTo(60,60); gc.lineTo(660,60); gc.lineTo(660,310);
        gc.lineTo(60,310); gc.lineTo(60,60); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(5);gc.moveTo(180,60); gc.lineTo(180,310); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);gc.moveTo(300,60); gc.lineTo(300,310); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);gc.moveTo(420,60); gc.lineTo(420,310); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);gc.moveTo(540,60); gc.lineTo(540,310); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);gc.moveTo(660,60); gc.lineTo(660,310); gc.stroke();
        gc.closePath();
////////////////////////////////////
        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(5); gc.moveTo(60,110); gc.lineTo(660,110); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2); gc.moveTo(60,160); gc.lineTo(660,160); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2); gc.moveTo(60,210); gc.lineTo(660,210); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2); gc.moveTo(60,260); gc.lineTo(660,260); gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2); gc.moveTo(60,310); gc.lineTo(660,310); gc.stroke();
        gc.closePath();
        //////////////////////////////////////
        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(50); gc.moveTo(205,135);gc.lineTo(275,135);gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN);gc.setLineWidth(50);gc.moveTo(325,185); gc.lineTo(395,185);gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN);gc.setLineWidth(50);gc.moveTo(445,235);gc.lineTo(515,235);gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN);gc.setLineWidth(50);gc.moveTo(565,285);gc.lineTo(635,285);gc.stroke();
        gc.closePath();

        // 4-Teams-Turnierbaum ->

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);
        gc.moveTo(60,330);
        gc.lineTo(180,330);
        gc.lineTo(180,380);
        gc.lineTo(60,380);
        gc.lineTo(60,330);
        gc.setStroke(Color.GREEN); gc.setLineWidth(1);
        gc.moveTo(60,355);
        gc.lineTo(180,355);
        gc.moveTo(120,355);
        gc.lineTo(120,380);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.RED); gc.setLineWidth(1);
        gc.moveTo(190,355);
        gc.lineTo(270,355);
        gc.lineTo(270,415);
        gc.lineTo(190,415);
        gc.setStroke(Color.RED); gc.setLineWidth(1);
        gc.moveTo(270,385);
        gc.lineTo(310,385);
        gc.stroke();
        gc.closePath();


        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);
        gc.moveTo(60,390);
        gc.lineTo(180,390);
        gc.lineTo(180,440);
        gc.lineTo(60,440);
        gc.lineTo(60,390);
        gc.setStroke(Color.GREEN); gc.setLineWidth(1);
        gc.moveTo(60,415);
        gc.lineTo(180,415);
        gc.moveTo(120,415);
        gc.lineTo(120,440);
        gc.stroke();
        gc.closePath();


        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);
        gc.moveTo(60,460);
        gc.lineTo(180,460);
        gc.lineTo(180,510);
        gc.lineTo(60,510);
        gc.lineTo(60,460);
        gc.setStroke(Color.GREEN); gc.setLineWidth(1);
        gc.moveTo(60,485);
        gc.lineTo(180,485);
        gc.moveTo(120,485);
        gc.lineTo(120,510);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.RED); gc.setLineWidth(1);
        gc.moveTo(190,485);
        gc.lineTo(270,485);
        gc.lineTo(270,545);
        gc.lineTo(190,545);
        gc.setStroke(Color.RED); gc.setLineWidth(1);
        gc.moveTo(270,515);
        gc.lineTo(310,515);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);
        gc.moveTo(60,520);
        gc.lineTo(180,520);
        gc.lineTo(180,570);
        gc.lineTo(60,570);
        gc.lineTo(60,520);
        gc.setStroke(Color.GREEN); gc.setLineWidth(1);
        gc.moveTo(60,545);
        gc.lineTo(180,545);
        gc.moveTo(120,545);
        gc.lineTo(120,570);
        gc.stroke();
        gc.closePath();

        ////////////////////////

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);
        gc.moveTo(320,360);
        gc.lineTo(440,360);
        gc.lineTo(440,410);
        gc.lineTo(320,410);
        gc.lineTo(320,360);
        gc.setStroke(Color.GREEN); gc.setLineWidth(1);
        gc.moveTo(320,385);
        gc.lineTo(440,385);
        gc.moveTo(380,385);
        gc.lineTo(380,410);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.RED); gc.setLineWidth(1);
        gc.moveTo(450,385);
        gc.lineTo(530,385);
        gc.lineTo(530,515);
        gc.lineTo(450,515);
        gc.setStroke(Color.RED); gc.setLineWidth(1);
        gc.moveTo(530,450);
        gc.lineTo(570,450);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);
        gc.moveTo(320,490);
        gc.lineTo(440,490);
        gc.lineTo(440,540);
        gc.lineTo(320,540);
        gc.lineTo(320,490);
        gc.setStroke(Color.GREEN); gc.setLineWidth(1);
        gc.moveTo(320,515);
        gc.lineTo(440,515);
        gc.moveTo(380,515);
        gc.lineTo(380,540);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN); gc.setLineWidth(2);
        gc.moveTo(580,425);
        gc.lineTo(700,425);
        gc.lineTo(700,475);
        gc.lineTo(580,475);
        gc.lineTo(580,425);
        gc.setStroke(Color.GREEN); gc.setLineWidth(1);
        gc.moveTo(580,450);
        gc.lineTo(700,450);
        gc.moveTo(640,450);
        gc.lineTo(640,475);
        gc.stroke();
        gc.closePath();
        ////////////////////////////////////////////

        int spaltenBreite = 60;
        int zeilenBreite = 50;
        for(int zeile = 0; zeile < anzahlTeilnehmer; zeile++ )
        {
            for(int spalte = 0;spalte< anzahlTeilnehmer;spalte++){
                //Hier die Zellen der Tabelle erstellen

            }
        }

        //gc.strokeText("DUfiuzefuzis isatgwafaw", 300,200);
        /////////////////////////////////////////////////////////////////////
        tab_turnierbaum.setContent(canvas);

    }

    public void focus(MouseEvent mouseEvent)
    {
//        System.out.println("..---.."+a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size());
//        System.out.println(a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele());
//        System.out.println("---------");
//        System.out.println(a.getAktuelleTurnierAuswahl().getObs_spiele());
//        System.out.println("---------"+a.getAktuelleTurnierAuswahl().getObs_spiele().size());
//        System.out.println(a.getAktuelleTurnierAuswahl().getObs_spiele().size());
//        System.out.println(a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
//        System.out.println(obs_spielklassen.size());





    }
}

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import sample.*;
import sample.DAO.auswahlklasse;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.TableView;
import java.io.File;
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
    private GridPane gridPane_main;
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

            auswahlklasse.addStagesdict(stage,"SpielerHinzu");
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

            auswahlklasse.addStagesdict(stage,"Klassenuebersicht");
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

            auswahlklasse.addStagesdict(stage,"FeldZuweisung");
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
            auswahlklasse.addStagesdict(stage,"NeuesTurnier");
            stage.setTitle("Neues Turnier");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pressBtn_turnierLaden (ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        auswahlklasse.addStagesdict(stage,"Turnierladen");
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");
        auswahlklasse.getStagesdict().get("Main").close();
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
        auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
        checkComboBox.getItems().clear();

        //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());

        Enumeration enumKeys = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().keys();
        while(enumKeys.hasMoreElements()){
            int key = (int) enumKeys.nextElement();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().add(auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().get(key));
            //System.out.println("größe = "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
//            checkComboBox.getItems().add(obs_spielklassen.get(i-1));

        }
        checkComboBox.getItems().setAll(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen());


        //System.out.println("test");

    }
    public void pressBtn_Einstellungen (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Einstellungen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        auswahlklasse.addStagesdict(stage,"Einstellungen");
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
        auswahlklasse.getStagesdict().put("Statistik",stage);
        stage.setScene(new Scene(root1));
        stage.show();
        auswahlklasse.addStagesdict(stage,"Statistiken");
        stage.setTitle("Statistiken: "+auswahlklasse.getAktuelleTurnierAuswahl().getName());
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }
    public void pressBtn_teamLaden (ActionEvent event) throws Exception {
        //System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeamUebersicht.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();


        stage.setScene(new Scene(root1));
        stage.show();
        auswahlklasse.addStagesdict(stage,"TurnierAuswählen");
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

        auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();
        auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().removeAll();


        int id=0;
        if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl()!=null&&auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size()>0) {
            if (check_gespielteSpiele.isSelected()) {
                for (int j = 0; j < auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); j++) { //<=?
                    id = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(j); //+1?
                    //System.out.println("id= " + id);
                    for (int i = 0; i < auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size(); i++) {
                        boolean frei=false;
                        if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getGast() != null) {
                            frei = auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getGast().isFreilos();
                        }
                        if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getHeim() != null && !frei) {
                            frei = auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getHeim().isFreilos();
                        }

                        if (auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        if (id != 0 && id == auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            if(!frei) {
                                auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().add(auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().get(i));
                            }
                            // System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }

                    }
                }
            }

            if (check_aktiveSpiele.isSelected()) {
                for (int j = 0; j < auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); j++) { //<=?
                    id = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(j);//+1
                    //System.out.println("id= " + id);
                    for (int i = 0; i < auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size(); i++) {


                        if (auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        if (id != 0 && id == auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().add(auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().get(i));
                            //System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }
            if (check_ausstehendeSpiele.isSelected()) {
                for (int j = 0; j < auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); j++) { //<=?
                    id = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(j); //+1
                    //System.out.println("id= " + id);
                    for (int i = 0; i < auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size(); i++) {


                        if (auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getSpielsystem().getSpielklasse() != null) {
                            //   System.out.println("spid= "+a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }
                        boolean frei=false;
                        if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getGast() != null) {
                            frei = auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getGast().isFreilos();
                        }
                        if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getHeim() != null && !frei) {
                            frei = auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getHeim().isFreilos();
                        }
                        if (id != 0 && id == auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getSpielsystem().getSpielklasse() != null) {

                            /*if(a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getGastString()=="Freilos"||
                                    a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getGast()==null||
                                    a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getHeimString()=="Freilos"||
                                    a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i).getHeim()==null)*/
                            if(frei)
                            {}
                            else {
                                auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().add(auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().get(i));
                            }
                            //System.out.println("id =" + id + " spid= " + obs_spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID());
                        }


                    }
                }
            }
        }

        ObservableList<Spiel> sortListe = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele();
        sortListe.sort(new Comparator<Spiel>() {
            @Override
            public int compare(Spiel o1, Spiel o2) {
                return o1.getZeitplanNummer()-o2.getZeitplanNummer();
            }
        });
        tabelle_spiele.setItems(sortListe);

    }

    private void printSpielTable() throws Exception {
        tabelle_spiele.getColumns().removeAll();
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {


            TableColumn<Spiel,String> spielNummerSpalte = new TableColumn("#");
            spielNummerSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("spielNummer"));
            spielNummerSpalte.setCellFactory(column -> {
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

            TableColumn < Spiel, String > spielRundeSpalte = new TableColumn("Runde");
            spielRundeSpalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>("RundenName"));
            spielRundeSpalte.setCellFactory(column -> {
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

            //tabelle_spiele.setItems(obs_spiele);

            tabelle_spiele.getColumns().addAll(spielNummerSpalte,spielFeldSpalte,spielHeimSpalte,spielGastSpalte,spielErgebnisSpalte,spielSpielklasseSpalte,spielRundeSpalte);

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
        auswahlklasse.addStagesdict(stage,"TurnierAuswählen");
        stage.setTitle("Turnier auswählen");

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<ArrayList<Spiel>> alleRunden = Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl());
        ContextMenu contextMenu = new ContextMenu();
        //auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();
        Zeitplan.zeitplanErstellen(auswahlklasse.getAktuelleTurnierAuswahl()); //vergebe Zeitplannummern für die Spiele


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
                            auswahlklasse.setSpielAuswahlErgebniseintragen(clickedRow);
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
                            stage.setTitle("Ergebnisse eintragen - "+auswahlklasse.getSpielAuswahlErgebniseintragen().getFeld() );

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
                        for(int i=0;i<auswahlklasse.getAktuelleTurnierAuswahl().getFelder().size();i++)
                        {

                            spiel=auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i).getAktivesSpiel();

                            if(spiel==null)
                            {
                                feld.add(auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i));
                            }
                            else
                            {
                                spiel=auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i).getInVorbereitung();
                                if(spiel==null)
                                {
                                    feld2.add(auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i));
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
                                            //System.out.println("Feld = " + feld.get(ii));
                                            clickedRow.setFeld(feld.get(ii));
                                            clickedRow.setStatus(2);
                                            auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().remove(clickedRow);
                                            auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().add(clickedRow);
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
            public void onChanged(Change<? extends Spielklasse> c) {
                //System.out.println(checkComboBox.getCheckModel().getCheckedIndices());
                //obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedIndices();
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
                for(int i=0;i<checkComboBox.getCheckModel().getCheckedItems().size();i++)
                {
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().add(checkComboBox.getCheckModel().getCheckedItems().get(i).getSpielklasseID());

                }
                //System.out.println(obs_spielklassen_auswahl);
                try {
                    //System.out.println("Auswahl geändert"+ checkComboBox.getCheckModel().getCheckedIndices());
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

        gridPane_main.getChildren().add(lspielklassen);
        GridPane.setColumnIndex(lspielklassen,2);
        GridPane.setRowIndex(lspielklassen,0);
        gridPane_main.getChildren().add(checkComboBox);
        GridPane.setColumnIndex(checkComboBox,3);
        GridPane.setRowIndex(checkComboBox,0);
        gridPane_main.getChildren().add(check_aktiveSpiele);
        GridPane.setColumnIndex(check_aktiveSpiele,4);
        GridPane.setRowIndex(check_aktiveSpiele,0);
        gridPane_main.getChildren().add(check_ausstehendeSpiele);
        GridPane.setColumnIndex(check_ausstehendeSpiele,5);
        GridPane.setRowIndex(check_ausstehendeSpiele,0);
        gridPane_main.getChildren().add(check_gespielteSpiele);
        GridPane.setColumnIndex(check_gespielteSpiele,6);
        GridPane.setRowIndex(check_gespielteSpiele,0);
        check_aktiveSpiele.setText("Aktive Spiele");
        check_aktiveSpiele.setSelected(true);
        check_ausstehendeSpiele.setText("Ausstehende Spiele");
        check_ausstehendeSpiele.setSelected(true);
        check_gespielteSpiele.setText("Gespielte Spiele");
        check_gespielteSpiele.setSelected(true);
        checkComboBox.setMaxWidth(250);
        checkComboBox.getItems().setAll(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen());

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
                //("maus event");


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


               // System.out.println(checkComboBox.getItems().size()+"   "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());

//////////EVENTUELL STATT DICTIONARY OBS LIST
                if(checkComboBox.getItems().size()!=auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size())
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
                    checkComboBox.getItems().setAll(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen());

                    for(int i =0;i<auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size();i++)
                    {
                        checkComboBox.getCheckModel().check(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(i));
                    }

                }





            }
        });

        //reloadcheckbox();
        try {
            //urnierLaden();

            printSpielTable();

            fuelleSpielElemente();
            for(int i=0;i<auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size();i++)
            {
                checkComboBox.getCheckModel().check(i);
            }
            //obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedItems();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
            for(int i=0;i<checkComboBox.getCheckModel().getCheckedItems().size();i++)
            {
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().add(checkComboBox.getCheckModel().getCheckedItems().get(i).getSpielklasseID());
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        //System.out.println(tabelle_spiele.getSortOrder());
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
    @FXML
    public void pressBtn_ExcelImport (ActionEvent event) throws Exception {
        try {

            FileChooser fileChooser = new FileChooser();

            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                if (ExcelImport.importExcelData(file.getAbsolutePath())) {
                    /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ExcelImportAbgeschlossen.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage2 = new Stage();
                    a.addStage(stage2);
                    stage2.setScene(new Scene(root1));
                    stage2.show();*/
                    if(ExcelImport.getObs_erf_spieler().size()>0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Spielerimport - Neue Spieler");
                        alert.setHeaderText("Spieler erfolgreich eingelesen! ");
                        alert.setContentText(String.valueOf(ExcelImport.getObs_erf_spieler()));
                        alert.showAndWait();
                        ExcelImport.getObs_erf_spieler().clear();
                    }
                    if(ExcelImport.getObs_upd_f_spieler().size()>0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Spielerimport - Update");
                        alert.setHeaderText("Spieler erfolgreich aktualisiert! ");
                        alert.setContentText(String.valueOf(ExcelImport.getObs_upd_f_spieler()));
                        alert.showAndWait();
                        ExcelImport.getObs_upd_f_spieler().clear();
                    }
                    //ExcelImport ex = new ExcelImport();
                    //ex.pressBtn_Popup();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Spielerimport");
                    alert.setHeaderText("Spieler konnten nicht eingelesen werden!");
                    alert.setContentText("Schade :(");

                    alert.showAndWait();
                }
            }
        }
        catch (Exception e)
        {

        }
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

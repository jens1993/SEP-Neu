package sample.GUI;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spieler;
import sample.Spielklasse;
import sample.Team;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.TableView;
import java.net.URL;
import java.util.*;

/**
 * Created by jens on 03.08.2017.
 */
public class MainController implements Initializable, Observable
{

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
    ObservableList obs_spielklassen = FXCollections.observableArrayList();
    private void ladeSpielklassen() throws Exception
    {

        System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());

        for (int i=1;i<=a.getAktuelleTurnierAuswahl().getSpielklassen().size();i++){
            obs_spielklassen.add(a.getVereine().get(i));

        }
        try {
            System.out.println(obs_spielklassen.size());

            choice_spielklassen.setItems(obs_spielklassen);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
            System.out.println("test");
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
    public void pressBtn_Einstellungen (ActionEvent event) throws Exception {
        System.out.println("test");
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
        System.out.println("test");
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
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeamUebersicht.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        a.addStage(stage);
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");
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

            ObservableList<Spiel> ob = obs_spiele;
            ob.addListener(new ListChangeListener<Spiel>() {
                               @Override
                               public void onChanged(Change<? extends Spiel> c) {
                                   System.out.println("Changed on " + c.toString());
                                   if(c.next()){
                                       System.out.println(c.getFrom());
                                   }
                               }
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
            tabelle_spiele.setItems(obs_spiele);

            tabelle_spiele.getColumns().addAll(spielFeldSpalte,spielHeimSpalte,spielGastSpalte,spielErgebnisSpalte);
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
                            //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                            //FuelleFelder(clickedRow);
                        }
                    });


                    // Add MenuItem to ContextMenu
                    contextMenu.getItems().clear();
                    contextMenu.getItems().addAll(item1, item2);

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
        final ObservableList<Spielklasse> strings = FXCollections.observableArrayList();
        for (int i = 1; i <= a.getAktuelleTurnierAuswahl().getSpielklassen().size(); i++) {
            strings.add(a.getAktuelleTurnierAuswahl().getSpielklassen().get(i));
        }

        // Create the CheckComboBox with the data
        final CheckComboBox<Spielklasse> checkComboBox = new CheckComboBox<Spielklasse>(strings);

        // and listen to the relevant events (e.g. when the selected indices or
        // selected items change).
        checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Spielklasse>() {
            public void onChanged(ListChangeListener.Change<? extends Spielklasse> c) {
                System.out.println(checkComboBox.getCheckModel().getCheckedIndices());
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

      
        for(int i=0;i<strings.size();i++)
        {
            checkComboBox.getCheckModel().check(i);
        }

        try {
            //urnierLaden();
            printSpielTable();
            ladeSpielklassen();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

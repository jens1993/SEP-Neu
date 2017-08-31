package sample.GUI;

import javafx.animation.PauseTransition;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import sample.*;
import sample.DAO.auswahlklasse;
import sample.GUI.Visualisierung.GruppenTabelle;
import sample.GUI.Visualisierung.Turnierbaum;
import sample.Spielsysteme.Spielsystem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by jens on 03.08.2017.
 */
public class MainController implements Initializable
{
    final ObservableList<Spielklasse> strings = FXCollections.observableArrayList();
    final CheckComboBox<Spielklasse> checkComboBox = new CheckComboBox<Spielklasse>();
    private Label lspielklassen;
    ArrayList<Integer> index_neu = new ArrayList<Integer>();

    //region FXML Deklaration
    @FXML
    private VBox vbox_main;
    @FXML
    private GridPane gridPane_main;
    @FXML
    private Button btn_klassen;
    @FXML
    private Button btn_turnierLaden;
    @FXML
    private Button btn_spieler;
    @FXML
    private Button btn_teams;
    @FXML
    private Button btn_zeitplan;
    @FXML
    private Button btn_statistik;
    @FXML
    private CheckBox check_gespielteSpiele= new CheckBox();
    @FXML
    private CheckBox check_aktiveSpiele= new CheckBox();
    @FXML
    private CheckBox check_ausstehendeSpiele= new CheckBox();
    @FXML
    private CheckBox check_zukuenftigeSpiele = new CheckBox();
    @FXML
    private ChoiceBox choice_spielklassen= new ChoiceBox();
    @FXML
    private javafx.scene.control.TableView tabelle_spiele;
    @FXML
    private Tab tab_spieluebersicht = new Tab();
    @FXML
    private Tab tab_turnierbaum = new Tab();
    @FXML
    private HBox hbox_felder;
    @FXML
    TabPane tabPane_spielklassen = new TabPane();

    @FXML
    private TextField tspielsuche;

    //endregion

    ObservableList<Spiel> sortListe = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele();
//wieso integer?
    //wenn integer dann die ids abgehen



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

    @FXML
    private void fuelleSpielElemente() throws Exception{
        auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();
        if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl()!=null&&auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size()>0) {
            fuelleCheckboxSpielElemente(check_gespielteSpiele,auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele());
            fuelleCheckboxSpielElemente(check_aktiveSpiele,auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele());
            fuelleCheckboxSpielElemente(check_ausstehendeSpiele,auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele());
            fuelleCheckboxSpielElemente(check_zukuenftigeSpiele,auswahlklasse.getAktuelleTurnierAuswahl().getObs_zukuenftigeSpiele());
        }
        sortiereTabelleSpiele();
    }

    private void sortiereTabelleSpiele() {
        sortListe.sort(new Comparator<Spiel>() {
            @Override
            public int compare(Spiel o1, Spiel o2) {
                return o1.getZeitplanNummer()-o2.getZeitplanNummer();
            }
        });
        tabelle_spiele.setItems(sortListe);
    }

    private void fuelleCheckboxSpielElemente(CheckBox checkBox, ObservableList<Spiel> spiele) {
        int id;
        if (checkBox.isSelected()) {
            for (int j = 0; j < auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); j++) {
                id = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(j);
                for (int i = 0; i < spiele.size(); i++) {
                    if (spiele.get(i).getSpielsystem().getSpielklasse() != null) {
                    }
                    boolean frei=false;
                    if(spiele.get(i).getGast() != null) {
                        frei = spiele.get(i).getGast().isFreilos();
                    }
                    if(spiele.get(i).getHeim() != null && !frei) {
                        frei = spiele.get(i).getHeim().isFreilos();
                    }
                    if (id != 0 && id == spiele.get(i).getSpielsystem().getSpielklasse().getSpielklasseID() && spiele.get(i).getSpielsystem().getSpielklasse() != null) {
                        if(frei)
                        {}
                        else {
                            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().add(spiele.get(i));
                        }
                    }
                }
            }
        }
    }

    private void printSpielTable() throws Exception {
        tabelle_spiele.getColumns().removeAll();
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {
            TableColumn<Spiel,String> spielNummerSpalte = tableColoumnsetCellFactory("#","SpielNummer");
            TableColumn<Spiel,String> spielFeldSpalte = tableColoumnsetCellFactory("Feld","FeldNr");
            TableColumn<Spiel,String> spielHeimSpalte = tableColoumnsetCellFactory("Heim","HeimString");
            TableColumn<Spiel,String> spielGastSpalte = tableColoumnsetCellFactory("Gast","GastString");
            TableColumn<Spiel,String> spielErgebnisSpalte = tableColoumnsetCellFactory("Ergebnis","ErgebnisString");
            TableColumn<Spiel,String> spielSpielklasseSpalte = tableColoumnsetCellFactory("Spielklasse","SpielklasseString");
            TableColumn<Spiel,String> spielRundeSpalte = tableColoumnsetCellFactory("Runde","RundenName");
            tabelle_spiele.getColumns().addAll(spielNummerSpalte,spielFeldSpalte,spielHeimSpalte,spielGastSpalte,spielErgebnisSpalte,spielSpielklasseSpalte,spielRundeSpalte);
        }
        else{
            System.out.println("kann Turnier nicht laden");
        }
    }

    private TableColumn<Spiel, String> tableColoumnsetCellFactory(String tabellenspaltentext, String getFunktion) {
        TableColumn<Spiel,String> spalte = new TableColumn(tabellenspaltentext);

        spalte.setCellValueFactory(new PropertyValueFactory<Spiel,String>(getFunktion));
        spalte.setCellFactory(column -> {
            return new TableCell<Spiel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        Spiel spiel = getTableView().getItems().get(getIndex());
                        if(tabellenspaltentext.equals("Heim")||tabellenspaltentext.equals("#")) {
                            setAlignment(Pos.CENTER_RIGHT);
                        }
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
                            setTextFill(Color.BLACK);
                        }
                    }
                }
            };
        });
        return spalte;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tooltipsHinzufuegen();
        //auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();
        Zeitplan.zeitplanErstellen(auswahlklasse.getAktuelleTurnierAuswahl()); //vergebe Zeitplannummern für die Spiele
        klassenTabsErstellen();
        felderHinzufuegen();

        tabelleSpieleContextMenu();

        checkComboBoxListener();


        layoutErstellen();
        suchleisteListener();


        checkboxListener(check_aktiveSpiele);
        checkboxListener(check_ausstehendeSpiele);
        checkboxListener(check_gespielteSpiele);
        checkboxListener(check_zukuenftigeSpiele);

        tabelle_spiele.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AllesNeuLaden();
            }
            });


        checkComboBoxFuellen();


        //tabelle_spiele.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    private void checkComboBoxFuellen() {
        try {

            printSpielTable();

            fuelleSpielElemente();

            for(int i = 0; i< auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size(); i++)
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
    }

    private void checkboxListener(CheckBox checkBox) {
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    fuelleSpielElemente();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void layoutErstellen() {
        lspielklassen = new Label("Spielklassen");
        tspielsuche=new TextField("");
        tspielsuche.setPromptText("Spielsuche");
        gridPane_main.getChildren().add(tspielsuche);
        GridPane.setColumnIndex(tspielsuche,0);
        GridPane.setRowIndex(tspielsuche,0);


        gridPane_main.getChildren().add(lspielklassen);
        GridPane.setColumnIndex(lspielklassen,1);
        GridPane.setRowIndex(lspielklassen,0);
        gridPane_main.getChildren().add(checkComboBox);
        GridPane.setColumnIndex(checkComboBox,2);
        GridPane.setRowIndex(checkComboBox,0);
        gridPane_main.getChildren().add(check_aktiveSpiele);
        GridPane.setColumnIndex(check_aktiveSpiele,3);
        GridPane.setRowIndex(check_aktiveSpiele,0);
        gridPane_main.getChildren().add(check_ausstehendeSpiele);
        GridPane.setColumnIndex(check_ausstehendeSpiele,4);
        GridPane.setRowIndex(check_ausstehendeSpiele,0);
        gridPane_main.getChildren().add(check_gespielteSpiele);
        GridPane.setColumnIndex(check_gespielteSpiele,5);
        GridPane.setRowIndex(check_gespielteSpiele,0);
        gridPane_main.getChildren().add(check_zukuenftigeSpiele);
        GridPane.setColumnIndex(check_zukuenftigeSpiele,6);
        GridPane.setRowIndex(check_zukuenftigeSpiele,0);
        GridPane.setColumnIndex(check_zukuenftigeSpiele,7);
        GridPane.setRowIndex(check_zukuenftigeSpiele,0);
        check_aktiveSpiele.setText("Aktive Spiele");
        check_aktiveSpiele.setSelected(true);
        check_ausstehendeSpiele.setText("Ausstehende Spiele");
        check_ausstehendeSpiele.setSelected(true);
        check_gespielteSpiele.setText("Gespielte Spiele");
        check_gespielteSpiele.setSelected(true);
        check_zukuenftigeSpiele.setText("Zukünftige Spiele");
        check_zukuenftigeSpiele.setSelected(true);
        checkComboBox.setMaxWidth(400);
        checkComboBox.getItems().setAll(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen());
    }

    private void suchleisteListener() {
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        tspielsuche.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            pause.setOnFinished(event -> CheckeSpielsuche());
            pause.playFromStart();

        });
    }

    private void checkComboBoxListener() {
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
    }

    private void tabelleSpieleContextMenu() {
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
                    AllesNeuLaden();
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
                    Menu item3 = new Menu ("Felder zuweisen");
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
                    MenuItem item9 = new MenuItem("Kompletten Block markieren");
                    item9.setOnAction(new EventHandler<ActionEvent>() {
                    ArrayList<Spiel> spielselect = new ArrayList<>();
                        @Override
                        public void handle(ActionEvent event) {
                        for(int i=0;i<sortListe.size();i++)
                        {
                            if(sortListe.get(i).getSpielklasseString().equals(clickedRow.getSpielklasseString()))
                            {
                                System.out.println(sortListe.get(i));
                                spielselect.add(sortListe.get(i));
                                tabelle_spiele.getSelectionModel().select(sortListe.get(i));
                            }
                        }


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
                        contextMenu.getItems().addAll(item3, item4,item9);
                    }
                    if(clickedRow.getStatus()==2)
                    {   //aktiv
                        contextMenu.getItems().addAll(item1, item2,item5,item6,item7,item9);
                    }
                    if(clickedRow.getStatus()==3)
                    {
                        //gespielt
                        contextMenu.getItems().addAll(item1, item8,item9);
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
    }

    private void felderHinzufuegen() {
        for (int i=1; i<=auswahlklasse.getAktuelleTurnierAuswahl().getFelder().size();i++){
            Button feld = new Button(i+"");
            feld.getStyleClass().add("feld");
            feld.setMaxSize(100,300);
            feld.setPrefSize(100,300);
            hbox_felder.getChildren().add(feld);
            hbox_felder.setSpacing(20);
        }
    }

    private void tooltipsHinzufuegen() {
        Tooltip klassenTooltip = new Tooltip();
        klassenTooltip.setText("Hier klassen erstellen\n und Setzliste hinzufügen");
        btn_klassen.setTooltip(klassenTooltip);

        Tooltip turnieruebersichtTooltip = new Tooltip();
        turnieruebersichtTooltip.setText("Turnier laden oder neues Turnier erstellen");
        btn_turnierLaden.setTooltip(turnieruebersichtTooltip);

        Tooltip spielerTooltip = new Tooltip();
        spielerTooltip.setText("Turnier laden oder neues Turnier erstellen");
        btn_spieler.setTooltip(spielerTooltip);


        Tooltip zeitplanTooltip = new Tooltip();
        zeitplanTooltip.setText("Turnier laden oder neues Turnier erstellen");
        btn_zeitplan.setTooltip(zeitplanTooltip);

        Tooltip statistikTooltip = new Tooltip();
        statistikTooltip.setText("Turnier laden oder neues Turnier erstellen");
        btn_statistik.setTooltip(statistikTooltip);
    }

    private void klassenTabsErstellen() {
        tab_turnierbaum.setContent(tabPane_spielklassen);
        for(int i=0;i<auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size();i++){
            Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(i);
            if(spielklasse.getSpielsystem()!=null) {
                Tab tab = new Tab(spielklasse.toString());
                tab.setClosable(false);
                tabPane_spielklassen.getTabs().add(tab);
                klassenVisualisierung(spielklasse.getSpielsystem(),tab);
            }
        }
    }

    private void klassenVisualisierung(Spielsystem spielsystem, Tab tab) {
        if (spielsystem.getSpielSystemArt()==3){

            //gc.setFill(Color.rgb(216,216,216));
            //gc.fillRect(0,0,5000,5000);
            Turnierbaum turnierbaum = new Turnierbaum(20,20,180,50,100,20);
            if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size()>1) {
                turnierbaum.erstelleTurnierbaum(spielsystem.getSpielklasse(), tab);
            }

        }
        if (spielsystem.getSpielSystemArt()==1){
            GruppenTabelle gruppenTabelle = new GruppenTabelle(spielsystem.getSpielklasse(), tab);
            if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size()>1) {
                gruppenTabelle.erstelleGruppenTabelle();
            }
        }
    }

    public void zoomIn(){
        ScrollPane aktuellesScrollPane = (ScrollPane) tabPane_spielklassen.getSelectionModel().getSelectedItem().getContent();
        aktuellesScrollPane.setScaleX(2);
        aktuellesScrollPane.setScaleY(2);
    }

    private void CheckeSpielsuche()
    {
        //System.out.println(tspielsuche.getText());

        tabelle_spiele.refresh();
        Enumeration e = auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().keys();
        sortListe.clear();
        while (e.hasMoreElements()){
            int key = (int) e.nextElement();

            if(auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key).toString().toUpperCase().contains(tspielsuche.getText().toUpperCase()))
            {
                sortListe.add(auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key));
            }
        }
        sortiereTabelleSpiele();

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
                    if(ExcelImport.getSpielererfolgreich().size()>0) {
                        String s ="";
                        Enumeration e = ExcelImport.getSpielererfolgreich().keys();
                        while(e.hasMoreElements())
                        {
                            s+=e.nextElement().toString();
                            if(e.hasMoreElements())
                            {
                                s+=" --- ";
                            }
                        }

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Spielerimport - Neue Spieler");
                        alert.setHeaderText("Spieler erfolgreich eingelesen! ");
                        alert.setContentText(s);
                        alert.showAndWait();
                        //ExcelImport.setSpielererfolgreich(null);
                    }

                    if(ExcelImport.getSpielerupdate().size()>0) {
                        Enumeration eu = ExcelImport.getSpielerupdate().keys();
                        String s ="";
                        while(eu.hasMoreElements())
                        {
                            s+=eu.nextElement().toString();
                            if(eu.hasMoreElements())
                            {
                                s+=" --- ";
                            }
                        }

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Spielerimport - Update");
                        alert.setHeaderText("Spieler erfolgreich aktualisiert! ");
                        alert.setContentText(String.valueOf(s));
                        alert.showAndWait();
                        //ExcelImport.setSpielerupdate(null);
                    }
                    if(ExcelImport.getObs_vereine_erfolgreich().size()>0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Vereinimport - Neue Vereine");
                        alert.setHeaderText("Vereine erfolgreich hinzugefügt ");
                        alert.setContentText(String.valueOf(ExcelImport.getObs_vereine_erfolgreich()));
                        alert.showAndWait();
                        //ExcelImport.getObs_vereine_erfolgreich().clear();
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
                ExcelImport.resetteAlles();
            }
        }
        catch (Exception e)
        {

        }
    }



    private void AllesNeuLaden() {
        int indexalt = tabelle_spiele.getSelectionModel().getSelectedIndex();
        if (!tspielsuche.getText().equals("")) {
            CheckeSpielsuche();
        } else {
            //("maus event");
            //ObservableList index = tabelle_spiele.getSelectionModel().getSelectedIndices();
            /*index_neu.clear();
            for (int i = 0; i < index.size(); i++) {
                index_neu.add((Integer) index.get(i));
            }*/

            //region checkbox
            if (check_gespielteSpiele.isSelected()) {
                check_gespielteSpiele.setSelected(false);
                check_gespielteSpiele.setSelected(true);
            } else {
                check_gespielteSpiele.setSelected(true);
                check_gespielteSpiele.setSelected(false);
            }
            if (check_ausstehendeSpiele.isSelected()) {
                check_ausstehendeSpiele.setSelected(false);
                check_ausstehendeSpiele.setSelected(true);
            } else {
                check_ausstehendeSpiele.setSelected(true);
                check_ausstehendeSpiele.setSelected(false);
            }
            if (check_aktiveSpiele.isSelected()) {
                check_aktiveSpiele.setSelected(false);
                check_aktiveSpiele.setSelected(true);
            } else {
                check_aktiveSpiele.setSelected(true);
                check_aktiveSpiele.setSelected(false);
            }
            if (check_zukuenftigeSpiele.isSelected()) {
                check_zukuenftigeSpiele.setSelected(false);
                check_zukuenftigeSpiele.setSelected(true);
            } else {
                check_zukuenftigeSpiele.setSelected(true);
                check_zukuenftigeSpiele.setSelected(false);
            }
//endregion


            //System.out.println(tabelle_spiele.getItems().size()+"   "+a.getAktuelleTurnierAuswahl().getObs_spiele().size());

//                for(int i=0;i<a.getAktuelleTurnierAuswahl().getObs_spiele().size();i++)
//                {
//
//                    //System.out.println(a.getAktuelleTurnierAuswahl().getObs_spiele().get(i).getGast().isFreilos());
//                    System.out.println(a.getAktuelleTurnierAuswahl().getObs_spiele().get(i).toString().toUpperCase().contains("FREILOS"));
//                }


            // System.out.println(checkComboBox.getItems().size()+"   "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());

//////////EVENTUELL STATT DICTIONARY OBS LIST

            if (checkComboBox.getItems().size() != auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size()) {
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

                for (int i = 0; i < auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().size(); i++) {
                    checkComboBox.getCheckModel().check(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().get(i));
                }


            }

            /*for (int i = 0; i < index_neu.size(); i++) {*/

                tabelle_spiele.getSelectionModel().select(indexalt);
            /*}*/


        }
    }
        /*Später für Drag & DROP
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
    */
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

    }
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
    */
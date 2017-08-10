package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spieler;
import sample.Team;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.TableView;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            a.addStage(stage);
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

    private void printSpielTable() throws Exception {
        if(a.getAktuelleTurnierAuswahl()!=null) {
            ObservableList<Spiel> obs_spiele = FXCollections.observableArrayList();



            TableColumn<Spiel,String> spielFeldSpalte = new TableColumn("Feld");
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
            /*obs_spiele.clear();
            for(int i=0;i<a.getAktuelleTurnierAuswahl().getGespielteSpiele().size();i++)
            {
                obs_spiele.add(a.getAktuelleTurnierAuswahl().getGespielteSpiele().get(i));
            }
            for(int i=0;i<a.getAktuelleTurnierAuswahl().getAktiveSpiele().size();i++)
            {
                obs_spiele.add(a.getAktuelleTurnierAuswahl().getAktiveSpiele().get(i));
            }
            for(int i=0;i<a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().size();i++)
            {
                obs_spiele.add(a.getAktuelleTurnierAuswahl().getAusstehendeSpiele().get(i));
            }
*/
            /*Aggregate<ObservableList<Integer>> aggregate = new Aggregate(a.getAktuelleTurnierAuswahl().getAktiveSpiele(), a.getAktuelleTurnierAuswahl().getAusstehendeSpiele(),a.getAktuelleTurnierAuswahl().getGespielteSpiele());
            aggregate.addListener(new ListChangeListener<ObservableList<Integer>>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends ObservableList<Integer>> c) {
                    System.out.println("changed " + c);
                }
            });
            */
            obs_spiele = FXCollections.concat(a.getAktuelleTurnierAuswahl().getGespielteSpiele(),a.getAktuelleTurnierAuswahl().getAktiveSpiele(),a.getAktuelleTurnierAuswahl().getAusstehendeSpiele());

            obs_spiele.addListener(new ListChangeListener<Spiel>() {
                @Override
                public void onChanged(Change<? extends Spiel> c) {
                    System.out.println("concat changed " + c);
                }
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
        try {
            //urnierLaden();
            printSpielTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static class Aggregate<T> {

        List<ObservableList<T>> lists = new ArrayList<>();
auswahlklasse a = new auswahlklasse();

        public Aggregate(ObservableList<T>... lists) {
            for (ObservableList<T> list : lists) {
                this.lists.add(list);
            }
        }

        public final void addListener(ListChangeListener<? super T> listener) {
            for (ObservableList<T> list : lists) {
                list.addListener(listener);
            }
        }

        public final void removeListener(ListChangeListener<? super T> listener) {
            for (ObservableList<T> list : lists) {
                list.removeListener(listener);
            }
        }
    }
}

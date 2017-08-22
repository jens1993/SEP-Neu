package sample.GUI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.DAO.SpielDAO;
import sample.DAO.SpielDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spielklasse;

import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class FeldZuweisungController implements Initializable
{
    @FXML
    private javafx.scene.control.TableView tabelle_felder;

    @FXML
    private HBox hbox_feld;

    ArrayList<Hyperlink> hp =  new ArrayList<>();
    SpielDAO spielDao = new SpielDAOimpl();
    Dictionary<Integer,Integer> dictfelder = new Hashtable<>();
    private void printSpielTable() throws Exception {
        tabelle_felder.getColumns().removeAll();
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {



            TableColumn <Spiel, String > spielFeldSpalte = new TableColumn("Feld");
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

            tabelle_felder.getColumns().addAll(spielFeldSpalte,spielHeimSpalte,spielGastSpalte,spielErgebnisSpalte,spielSpielklasseSpalte);

        }

        else{
            System.out.println("kann Turnier nicht laden");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i=0; i<auswahlklasse.getAktuelleTurnierAuswahl().getFelder().size();i++)
        {
            hp.add(new Hyperlink(i+1+". Feld"));
            //hp2 = new Hyperlink(String.valueOf(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(i).getSpielklasseID()));
            //hbox_feld.getChildren().add(hp2);
            hbox_feld.getChildren().add(hp.get(i));

            dictfelder.put(i+1,auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i).getFeldID());

            hp.get(i).setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    if(event.getDragboard().hasString())
                    {
                        event.acceptTransferModes(TransferMode.ANY);

                    }

                }
            });
            int finalI = i;
            hp.get(i).setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    if(event.getDragboard().hasString())
                    {
                       // System.out.println(event.getDragboard().getString()+"---");
                        int aktuellesspiel = Integer.parseInt(event.getDragboard().getString());
                        Spiel spiel = auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(aktuellesspiel);

                        int id = Integer.parseInt(hp.get(finalI-1).getText().substring(0,1));
                        int id2 = dictfelder.get(id);
                        System.out.println(spiel.getHeim()+"----"+id2);
                        spiel.setFeld(auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(id2));
                        spiel.setStatus(2);
                    }
                }
            });

            tabelle_felder.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                   Dragboard db = tabelle_felder.startDragAndDrop(TransferMode.ANY);

                   ClipboardContent clipboardContent = new ClipboardContent();


                       int a = tabelle_felder.getSelectionModel().getSelectedIndex();
                       //System.out.println(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().get(a));

                    clipboardContent.putString(String.valueOf(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().get(a).getSpielID()));
                   //Daten in Clipboard
                    db.setContent(clipboardContent);
                }
            });
        }

        hbox_feld.setSpacing(10);

        try {
            printSpielTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Tabelle

        tabelle_felder.setItems(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele());



    }
}
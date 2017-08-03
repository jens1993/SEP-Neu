
package sample.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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

public class TurnierladenControllerNeuer implements Initializable
{

    ContextMenu context = new ContextMenu();
    MenuItem item1 = new MenuItem("Option1");
    MenuItem item2 = new MenuItem("Option2");
    MenuItem item3 = new MenuItem("Option3");
    //context
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


    @FXML
    public void auswahlSpeichern(ActionEvent event) throws Exception {
        try {
            System.out.println("Auswahl speichern");

            if(TurnierlisteTabelle.getSelectionModel().getSelectedItem()!=null && (Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem()!= a.getAktuelleTurnierAuswahl())
            {
                a.turnierAuswahlSpeichern((Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem());
                t.read(a.getAktuelleTurnierAuswahl());
                //System.out.println(a.getAktuelleTurnierAuswahl().getName());
                a.turnierAuswahlSpeichern(a.getAktuelleTurnierAuswahl());
                //System.out.println("Das aktuelle Turnier lautet"+a.getAktuelleTurnierAuswahl().toString());
                Main.getInstance().updateTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());


            }
        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void zeigeTabelle() {
        System.out.println("Print table");

        for (int i = 1; i <= a.getTurnierliste().size(); i++) {
            turniere.add(a.getTurnierliste().get(i));

        }

        Callback<TableColumn, TableCell> integerCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        TurnierladenControllerNeuer.MyIntegerTableCell cell = new TurnierladenControllerNeuer.MyIntegerTableCell();
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new TurnierladenControllerNeuer.MyEventHandler());
                        return cell;
                    }
                };

        Callback<TableColumn, TableCell> stringCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        TurnierladenControllerNeuer.MyStringTableCell cell = new TurnierladenControllerNeuer.MyStringTableCell();
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new TurnierladenControllerNeuer.MyEventHandler());
                        return cell;
                    }
                };
        Callback<TableColumn, TableCell> DateCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        TurnierladenControllerNeuer.MyDateTableCell cell = new TurnierladenControllerNeuer.MyDateTableCell();
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new TurnierladenControllerNeuer.MyEventHandler());
                        return cell;
                    }
                };


        TurnierlisteTabelle.setItems(turniere);


        TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,Integer>("turnierid"));
        TurnierIDSpalte.setCellFactory(integerCellFactory);
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
                }
                else if(! row.isEmpty() && event.getButton()== MouseButton.PRIMARY)
                {
                    System.out.println("KLICK");
                }
            });
            return row ;
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
            Main.getInstance().updateTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());
            System.out.println("Turnierauswahl durch Doppelklick: = "+item.getName());


        }

    }


    class MyIntegerTableCell extends TableCell<Turnier, Integer>
    {

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? null : getString());
            setGraphic(null);
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
    class MyDateTableCell extends TableCell<Turnier, Date>
    {

        @Override
        public void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? null : getString());
            setGraphic(null);
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
    class MyStringTableCell extends TableCell<Turnier, String> {

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? null : getString());
            setGraphic(null);
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class MyEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent t) {
            TableCell c = (TableCell) t.getSource();
            int index = c.getIndex();
            System.out.println("Name = " + turniere.get(index).getName());

        }
    }
}

package sample.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
public class TurnierladenControllerNeu extends Application implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    public TableColumn TurnierDatumSpalte;
    @FXML
    public TableColumn TurnierNameSpalte;
    @FXML
    public TableColumn TurnierIDSpalte;
    ObservableList<Turnier> turniere = FXCollections.observableArrayList();
    auswahlklasse a = new auswahlklasse();
    TurnierDAO t = new TurnierDAOimpl();
    Dictionary<Integer, Turnier> turnierliste = t.getAllTurniere();


    @FXML
    public void auswahlSpeichernneu(ActionEvent event) throws Exception {
        try {
            System.out.println("Auswahl speichern");

            if (tableView.getSelectionModel().getSelectedItem() != null && (Turnier) tableView.getSelectionModel().getSelectedItem() != a.getAktuelleTurnierAuswahl()) {
                a.turnierAuswahlSpeichern((Turnier) tableView.getSelectionModel().getSelectedItem());
                t.read(a.getAktuelleTurnierAuswahl());
                System.out.println(a.getAktuelleTurnierAuswahl().getName());
                a.turnierAuswahlSpeichern(a.getAktuelleTurnierAuswahl());
                System.out.println("Das aktuelle Turnier lautet" + a.getAktuelleTurnierAuswahl().toString());
                Main.getInstance().updateTitle("Badminton Turnierverwaltung - Turnier: " + a.getAktuelleTurnierAuswahl().getName());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //zeigeTabelleneu();
    }
    @FXML
    private void zeigeTabelleneu() {
        System.out.println("Print table");
        ObservableList<Turnier> turniere = FXCollections.observableArrayList();
        for (int i = 1; i <= a.getTurnierliste().size(); i++) {
            turniere.add(a.getTurnierliste().get(i));

        }
//        tableView.setItems(turniere);
//
//
//        TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Date>("datum"));
//        TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, String>("name"));
//        TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Integer>("turnierid"));


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("start");
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("http://java-buddy.blogspot.com/");
        primaryStage.setWidth(400);
        primaryStage.setHeight(500);
        tableView.setEditable(false);
        Callback<TableColumn, TableCell> integerCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        TurnierladenControllerNeu.MyIntegerTableCell cell = new TurnierladenControllerNeu.MyIntegerTableCell();
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new TurnierladenControllerNeu.MyEventHandler());
                        return cell;
                    }
                };

        Callback<TableColumn, TableCell> stringCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        TurnierladenControllerNeu.MyStringTableCell cell = new TurnierladenControllerNeu.MyStringTableCell();
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new TurnierladenControllerNeu.MyEventHandler());
                        return cell;
                    }
                };
        Callback<TableColumn, TableCell> DateCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        TurnierladenControllerNeu.MyDateTableCell cell = new TurnierladenControllerNeu.MyDateTableCell();
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new TurnierladenControllerNeu.MyEventHandler());
                        return cell;
                    }
                };
//        TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Date>("datum"));
//        TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, String>("name"));
//        TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Integer>("turnierid"));

        System.out.println("start2");
        TableColumn colturnierid = new TableColumn("turnierid");
        colturnierid.setCellValueFactory(new PropertyValueFactory<Turnier,Integer> ("turnierid"));
        colturnierid.setCellFactory(integerCellFactory);

        TableColumn colName= new TableColumn("name");
        colName.setCellValueFactory(new PropertyValueFactory<Turnier,String> ("name"));
        colName.setCellFactory(stringCellFactory);



        TableColumn coldatum = new TableColumn("datum");
        colName.setCellValueFactory(new PropertyValueFactory<Turnier,Date> ("datum"));
        coldatum.setCellFactory(DateCellFactory);


        for (int i = 1; i <= a.getTurnierliste().size(); i++) {
            turniere.add(a.getTurnierliste().get(i));

        }
        System.out.println("start3");
        tableView.setItems(turniere);
        tableView.getColumns().addAll(colturnierid, colName, coldatum);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);

        vbox.getChildren().add(tableView);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
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


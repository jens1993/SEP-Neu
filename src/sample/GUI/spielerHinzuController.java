package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.DAO.*;
import sample.Turnier;
import sample.Verein;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class spielerHinzuController implements Initializable
{
    @FXML
    private ComboBox combo_vereine;
    @FXML
            private TextField t_vn;
    auswahlklasse a = new auswahlklasse();

    @FXML
    public void ladeVereine(ActionEvent event) throws Exception
    {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(a.getAktuelleTurnierAuswahl().getVereine().size());
        ObservableList<Verein> vereine = FXCollections.observableArrayList();
        for (int i=1;i<=a.getVereine().size();i++){
            vereine.add(a.getVereine().get(i));

        }
        try {
            t_vn.setText("test");
            combo_vereine.setItems(vereine);
        } catch (Exception e) {
            e.printStackTrace();
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

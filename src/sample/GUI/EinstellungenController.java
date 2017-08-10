package sample.GUI;

import javafx.collections.FXCollections;
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
import sample.DAO.SQLConnection;
import sample.DAO.auswahlklasse;
import sample.Spiel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class EinstellungenController implements Initializable
{

    @FXML
    private TextField thost;
    @FXML
    private TextField tdb;
    @FXML
    private TextField tuser;
    @FXML
    private PasswordField tpw;
    @FXML
    private RadioButton rd;
    @FXML
    private RadioButton re;
    auswahlklasse a = new auswahlklasse();

 SQLConnection sqlConnection = new SQLConnection();

 public void radioswitch (ActionEvent event)
 {
     if(rd.isSelected())
     {
         a.setSprachid(1);
     }
     if(re.isSelected())
     {
         a.setSprachid(2);
     }
     System.out.println(a.getSprachid());
 }
 @FXML
 public void btn_einstellungenspeichern(ActionEvent event)
 {

     sqlConnection.setDbHost(thost.getText());
     sqlConnection.setDbName(tdb.getText());
     sqlConnection.setDbPass(tpw.getText());
     sqlConnection.setDbUser(tuser.getText());
 }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //urnierLaden();
            thost.setText(sqlConnection.getDbHost());
            tdb.setText(sqlConnection.getDbName());
            tpw.setText(sqlConnection.getDbPass());
            tuser.setText(sqlConnection.getDbUser());

            if(a.getSprachid()==1)
            {
                rd.setSelected(true);
            }

            if(a.getSprachid()==2)
            {
                re.setSelected(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DAO.VereinDAO;
import sample.DAO.VereinDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Verein;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class neuerVereinController
{
    @FXML
    private javafx.scene.control.TableView tabelle_spiele;


    auswahlklasse a = new auswahlklasse();

    @FXML
    private TextField t_vname;
    @FXML
    private TextField t_vverband;
    @FXML
    private TextField t_vextvereinsid;

    VereinDAO v = new VereinDAOimpl();
    Verein verein = null;

    @FXML
    public void pressBtn_VereinSpeichern(ActionEvent event) throws Exception {


        try {

            verein = new Verein(t_vname.getText(),t_vverband.getText(),t_vextvereinsid.getText());
            a.addVerein(verein);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            a.addStage(stage);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Neuer Spieler");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

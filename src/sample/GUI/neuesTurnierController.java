package sample.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Turnier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import jfxtras.labs.scene.control.BigDecimalField;
/**
 * Created by jens on 03.08.2017.
 */
public class neuesTurnierController
{
    TurnierDAO turnierDao = new TurnierDAOimpl();

    Dictionary<Integer,Turnier> turnierliste = turnierDao.getAllTurniere();

    @FXML
    private TextField Turniername;
    @FXML
    private DatePicker turnierDatum;

    @FXML
    private BigDecimalField AnzahlFelder;
 auswahlklasse a = new auswahlklasse();
    @FXML
    public void erstelleTurnier(ActionEvent event) throws Exception
    {
        String date = turnierDatum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate Datum = turnierDatum.getValue();
        System.out.println("Felder:  "+AnzahlFelder.getText());
        System.out.println("Name " +Turniername.getText());
        System.out.println("Datum " + date);
        TurnierDAO t = new TurnierDAOimpl();
        Turnier turnier = new Turnier(Turniername.getText(),turnierliste.size()+1, Datum);
        t.create(turnier);
        System.out.println("Erfolg");

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            for (int i=0; i<a.getStages().size();i++){
                a.getStages().get(i).close();
            }
            a.addStage(stage);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setMaximized(true);
            a.turnierAuswahlSpeichern(turnier);
            stage.setTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

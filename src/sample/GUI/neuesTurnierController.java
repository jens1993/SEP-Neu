package sample.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.Turnier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;

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
    private TextField AnzahlFelder;

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
    }

}

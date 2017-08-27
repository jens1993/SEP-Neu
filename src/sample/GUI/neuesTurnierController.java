package sample.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import sample.DAO.*;
import sample.Feld;
import sample.Turnier;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.ResourceBundle;

import jfxtras.labs.scene.control.BigDecimalField;
/**
 * Created by jens on 03.08.2017.
 */
public class neuesTurnierController implements Initializable
{
    boolean erfolg = false;
    TurnierDAO turnierDao = new TurnierDAOimpl();

    Dictionary<Integer,Turnier> turnierliste = turnierDao.getAllTurniere();
    @FXML
    private Button btn_starten;
    @FXML
    private TextField Turniername;
    @FXML
    private DatePicker turnierDatum;

    @FXML
    private BigDecimalField AnzahlFelder;
    @FXML

    public void erstelleTurnier(ActionEvent event) throws Exception {

        String date = turnierDatum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate Datum = turnierDatum.getValue();
        System.out.println("Felder:  " + AnzahlFelder.getText());
        System.out.println("Name " + Turniername.getText());
        System.out.println("Datum " + date);
        TurnierDAO t = new TurnierDAOimpl();
        FeldDAO feldDAO = new FeldDAOimpl();
        int anzahlfelder = Integer.parseInt(AnzahlFelder.getText());
        if (auswahlklasse.getTurnierzumupdaten() == null) {
            Turnier turnier = new Turnier(Turniername.getText(), Datum);
            erfolg=t.create(turnier);
            for (int i = 0; i < anzahlfelder; i++) {
                new Feld(turnier);
            }

            if(erfolg)
                auswahlklasse.InfoBenachrichtigung("Turnier erstellt", turnier.getName() + " wurde erstellt.");
            else
                auswahlklasse.WarnungBenachrichtigung("Turnier nicht erstellt", turnier.getName() + " wurde nicht erstellt.");
            System.out.println("Erfolg");

            try {
                ladeMain(event,turnier);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            int anzahlturnierfelderalt = auswahlklasse.getTurnierzumupdaten().getFelder().size();
            if (anzahlfelder > anzahlturnierfelderalt) {
                for (int i = anzahlturnierfelderalt; i < anzahlfelder; i++) {
                    new Feld(auswahlklasse.getTurnierzumupdaten());

                }
                auswahlklasse.InfoBenachrichtigung("Felder erstellt", String.valueOf(anzahlfelder - anzahlturnierfelderalt)+" Felder wurden erstellt");
            }
            if (anzahlfelder < anzahlturnierfelderalt)
            {
                boolean erfolg = false;
                for (int i = anzahlfelder; i < anzahlturnierfelderalt; i++) {

                    System.out.println("Lösche Feld");
                    erfolg = feldDAO.deleteFeld(auswahlklasse.getTurnierzumupdaten().getFelder().get(i));

                    if(!erfolg)
                        break;
                }
                if(erfolg)
                    auswahlklasse.InfoBenachrichtigung("Felder gelöscht",String.valueOf(anzahlturnierfelderalt-anzahlfelder)+" Felder wurden gelöscht");
                if (!erfolg)
                    auswahlklasse.WarnungBenachrichtigung("Feld fehler", "nicht löschbar");

            }
            auswahlklasse.getTurnierzumupdaten().setName(Turniername.getText());
            auswahlklasse.getTurnierzumupdaten().setDatum(turnierDatum.getValue());
            erfolg=turnierDao.update(auswahlklasse.getTurnierzumupdaten());
            if(erfolg)
                auswahlklasse.InfoBenachrichtigung("Turnier Update",auswahlklasse.getTurnierzumupdaten().getName()+" wurde geupdatet");
            if (!erfolg)
                auswahlklasse.WarnungBenachrichtigung("Feld fehler", "nicht löschbar");



            auswahlklasse.setTurnierzumupdaten(null);
            if(auswahlklasse.getStagesdict().get("TurnierLaden")!=null) {
                auswahlklasse.getStagesdict().get("TurnierLaden").close();
                auswahlklasse.getStagesdict().remove("TurnierLaden");
            }
            if(auswahlklasse.getStagesdict().get("NeuesTurnier")!=null) {
                auswahlklasse.getStagesdict().get("NeuesTurnier").close();
                auswahlklasse.getStagesdict().remove("NeuesTurnier");
            }
            ladeTurnierladen(event);


        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BigDecimal v = new BigDecimal(1);
        AnzahlFelder.setMinValue(v);
        v=new BigDecimal(30);
        AnzahlFelder.setMaxValue(v);
        AnzahlFelder.setText(String.valueOf(1));
        turnierDatum.setValue(LocalDate.now());
        if(auswahlklasse.getTurnierzumupdaten()!=null)
        {
            turnierDao.readFelder_Neu(auswahlklasse.getTurnierzumupdaten());
            btn_starten.setText("Update");

            //System.out.println(turnierzumupdaten.getFelder());

            if(auswahlklasse.getTurnierzumupdaten().getFelder().size()>30)
            {
                AnzahlFelder.setText(String.valueOf(30));
            }
            if(auswahlklasse.getTurnierzumupdaten().getFelder().size()<1)
            {
                AnzahlFelder.setText(String.valueOf(1));
            }
            else
            {
                AnzahlFelder.setText(String.valueOf(auswahlklasse.getTurnierzumupdaten().getFelder().size()));
            }
            turnierDatum.setValue(auswahlklasse.getTurnierzumupdaten().getDatum());
            Turniername.setText(auswahlklasse.getTurnierzumupdaten().getName());

        }
    }
    private void ladeTurnierladen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        auswahlklasse.addStagesdict(stage, "TurnierLaden");
        stage.setScene(new Scene(root1));
        stage.show();

        stage.setTitle("Übersicht");
    }
    private void ladeMain(ActionEvent event,Turnier turnier) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        auswahlklasse.addStagesdict(stage, "Main");
        stage.setScene(new Scene(root1));
        stage.show();

        auswahlklasse.turnierAuswahlSpeichern(turnier);
        stage.setTitle("Badminton Turnierverwaltung - Kein Turnier ausgewählt");
    }

}

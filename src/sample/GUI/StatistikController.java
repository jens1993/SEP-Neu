package sample.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.DAO.auswahlklasse;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jens on 11.08.2017.
 */
public class StatistikController implements Initializable
{
    @FXML
    public Label lturnier;
    @FXML
    public Label anzahlSpielerimTurnier;
    @FXML
    public Label anzahlSpielklassen;
    @FXML
    public Label anzahlSpiele;

    @FXML
            public Label anzahlobsspiele;
    auswahlklasse a = new auswahlklasse();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    lturnier.setText("Turnierinformationen: "+ a.getAktuelleTurnierAuswahl().getName());
    anzahlSpielklassen.setText(String.valueOf(a.getAktuelleTurnierAuswahl().getObs_spielklassen().size()));
    anzahlSpiele.setText(String.valueOf(a.getAktuelleTurnierAuswahl().getObs_spiele().size()));
    anzahlSpielerimTurnier.setText(String.valueOf(auswahlklasse.getObs_spieler().size()));
    anzahlobsspiele.setText(
            String.valueOf("Aktive "+
            a.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size()+"Gespielte "+
            a.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size()+"Ausstehende "+
            a.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size()));
    }
}

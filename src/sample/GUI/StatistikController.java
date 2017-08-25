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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    lturnier.setText("Turnierinformationen: "+ auswahlklasse.getAktuelleTurnierAuswahl().getName());
    anzahlSpielklassen.setText(String.valueOf(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size()));
    anzahlSpiele.setText(String.valueOf(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().size()));
    anzahlSpielerimTurnier.setText(String.valueOf(auswahlklasse.getObs_spieler().size()));
    anzahlobsspiele.setText(
            String.valueOf("Aktive "+
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size()+"Gespielte "+
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size()+"Ausstehende "+
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size()+ "Zukünftige"+auswahlklasse.getAktuelleTurnierAuswahl().getObs_zukuenftigeSpiele().size()));
    }
}

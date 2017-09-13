package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import sample.DAO.auswahlklasse;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by jens on 11.08.2017.
 */
public class StatistikController implements Initializable
{

    String baseName = "resources.Main";
    String titel ="";

    @FXML
    public Label lturnier;
    @FXML
    public Label anzahlSpielerimTurnier;
    @FXML
    public Label anzahlSpielklassen;
    @FXML
    public Label anzahlSpiele;
    @FXML
    public HBox hbox_statistik;
    @FXML
    public AnchorPane anchorpane_statistik;
    @FXML
    public Label anzahlobsspiele;
    @FXML
    public Label lab_spielklassen;
    @FXML
    private Label lab_spieler;
    @FXML
    private Label lab_obsSpiele;
    @FXML
    private Label lab_spiele;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lturnier");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lturnier.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_spielklassen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_spielklassen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_spieler");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_spieler.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_obsSpiele");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_obsSpiele.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_spiele");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_spiele.setText(titel);

    lturnier.setText("Turnierinformationen: "+ auswahlklasse.getAktuelleTurnierAuswahl().getName());
    anzahlSpielklassen.setText(String.valueOf(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size()));
    anzahlSpiele.setText(String.valueOf(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().size()));
    anzahlSpielerimTurnier.setText(String.valueOf(auswahlklasse.getObs_spieler().size()));
    anzahlobsspiele.setText(
            String.valueOf("Aktive "+
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size()+"Gespielte "+
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size()+"Ausstehende "+
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size()+ "Zukünftige"+auswahlklasse.getAktuelleTurnierAuswahl().getObs_zukuenftigeSpiele().size()));

        ObservableList <PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Ausstehende Spiele ("+auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size()+")", auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().size()),
                        //new PieChart.Data("Zukünftige Spiele", auswahlklasse.getAktuelleTurnierAuswahl().getObs_zukuenftigeSpiele().size()),
                        new PieChart.Data("Aktive Spiele ("+auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size()+")", auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().size()),
                        new PieChart.Data("Gespielte Spiele ("+auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size()+")", auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().size()));

        final PieChart chart = new PieChart(pieChartData);
        chart.setLabelLineLength(10);
        //
        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(false);
        hbox_statistik.getChildren().add(chart);
        chart.setTitle("Spielübersicht");

    }
}

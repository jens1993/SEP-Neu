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
import org.controlsfx.control.ToggleSwitch;
import sample.DAO.SQLConnection;
import sample.DAO.auswahlklasse;
import sample.Spiel;

import java.net.URL;
import java.util.Dictionary;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class EinstellungenController implements Initializable
{

    String baseName = "resources.Main";
    String titel ="";

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
    @FXML
            private Tab tab_Spieleinstellung;
    @FXML
            private Tab tab_Drucker;
    @FXML
            private Label lab_Sprache;
    @FXML
            private RadioButton rb_Verlierer;
    @FXML
            private RadioButton rb_Gewinner;
    @FXML
            private RadioButton rb_aus;
    @FXML
            private Label lab_VormSchiri;
    @FXML
            private ToggleSwitch ts_SchiriStandVerw;
    @FXML
            private Label lab_ausstehSpiel;
    @FXML
            private Label lab_aktiveSpiele;
    @FXML
            private Label lab_gespielSpiele;
    @FXML
            private Button b_standard;
    @FXML
            private Label lab_aktDruck;
    @FXML
            private Button b_tsDrucken;
    @FXML
            private Button b_DruckAender;


 SQLConnection sqlConnection = new SQLConnection();

 public void radioswitch (ActionEvent event)
 {
     if(rd.isSelected())
     {
         auswahlklasse.setSprachid(1);
     }
     if(re.isSelected())
     {
         auswahlklasse.setSprachid(2);
     }
     System.out.println(auswahlklasse.getSprachid());
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

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_Spieleinstellung");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_Spieleinstellung.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_Drucker");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_Drucker.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_Sprache");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_Sprache.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("rd");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        rd.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("re");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        re.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("rb_Verlierer");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        rb_Verlierer.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("rb_Gewinner");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        rb_Gewinner.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("rb_aus");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        rb_aus.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_VormSchiri");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_VormSchiri.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("ts_SchiriStandVerw");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        ts_SchiriStandVerw.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_ausstehSpiel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_ausstehSpiel.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_aktiveSpiele");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_aktiveSpiele.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_gespielSpiele");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_gespielSpiele.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_standard");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_standard.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_aktDruck");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_aktDruck.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_tsDrucken");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_tsDrucken.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_DruckAender");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_DruckAender.setText(titel);

        try {
            //a.getStagesdict().get("Main").close();
            Dictionary<String,Stage> stages =auswahlklasse.getStagesdict();

            //urnierLaden();
            thost.setText(sqlConnection.getDbHost());
            tdb.setText(sqlConnection.getDbName());
            tpw.setText(sqlConnection.getDbPass());
            tuser.setText(sqlConnection.getDbUser());

            if(auswahlklasse.getSprachid()==1)
            {
                rd.setSelected(true);
            }

            if(auswahlklasse.getSprachid()==2)
            {
                re.setSelected(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

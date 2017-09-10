package sample.GUI;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.omg.PortableInterceptor.INACTIVE;
import sample.DAO.auswahlklasse;
import sample.Ergebnis;
import sample.Spiel;
import sample.Team;

import java.net.URL;
import java.util.Dictionary;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Filter;

public class SpielErgebnisEingebenController implements Initializable{

    String baseName = "resources.Main";
    String titel ="";

    @FXML
    private Button btn_OK;

    @FXML
    private Button btn_Abbbruch;

    @FXML
    private Button btn_Zurueckziehen;

    @FXML
    private Button btn_Verlegen;

    @FXML
    private Button btn_schitzzettel;

    @FXML
    private TextField ts1_1;

    @FXML
    private TextField ts1_2;

    @FXML
    private TextField ts2_1;

    @FXML
    private TextField ts2_2;

    @FXML
    private TextField ts3_1;

    @FXML
    private TextField ts3_2;

    @FXML
    private Label l_heim;
    @FXML
    private Label l_gast;
    @FXML
    private Label l_meldungergebnis;
    @FXML
    private Text eins_satz;
    @FXML
    private Text zwei_satz;
    @FXML
    private Text drei_satz;
    @FXML
    private Text vier_satz;
    @FXML
    private Text fuenf_satz;
    @FXML
    private Text t_heim;
    @FXML
    private Text t_gast;

    Dictionary<Integer, Spiel> dictspiele = auswahlklasse.getAktuelleTurnierAuswahl().getSpiele();

    Spiel sp = dictspiele.get(auswahlklasse.getSpielAuswahlErgebniseintragen().getSpielID());


    Ergebnis erg;
    int s11=-1;
    int s12=-1;
    int s21=-1;
    int s22=-1;
    int s31=-1;
    int s32=-1;

    @FXML
    void pressbtn_Abbbruch(ActionEvent event) {

    }
    public boolean gueltigesErgebnis(int s11, int s12){

        if(s11<0||s12<0)
        {
            l_meldungergebnis.setText("Negative Ergebnisse sind ungültig");
            return false;
        }
        if (Math.abs(s11-s12)<2)
        {
            if (!((s11==29 && s12==30)||s11==30 && s12==29)){
                l_meldungergebnis.setText("Ein Satz muss mit 2 Punkten Differenz gewonnen werden");
                System.out.println("Fehler in Satz ");
                return false;
            }
        }
        if(s11<21&&s12<21)
        {
            l_meldungergebnis.setText("Ein Satz muss mindestens 21 Punkte haben");
            return false;
        }
        if (s11>30 || s12>30){
            l_meldungergebnis.setText("Ein Satz kann maximal bis 30 Punkte gehen");
            return false;
        }
        if((s11>18 && s12>18) && Math.abs(s11-s12)>2 ){
            l_meldungergebnis.setText("Ungültiges Satzergebnis ");
            return false;
        }

        return true;
    }
    @FXML
    void pressbtn_OK(ActionEvent event) {


        //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
        if(sp!=null && sp.getStatus()==0)
        {
            System.out.println("unvollständiges Spiel");
        }

        if(sp!=null && sp.getStatus()==2)
        {


            System.out.println("aktives Spiel");
            if(erg!=null) {
                try {

                    auswahlklasse.getSpielAuswahlErgebniseintragen().setErgebnis(erg);
                    auswahlklasse.getSpielAuswahlErgebniseintragen().setStatus(3);
                   // a.getAktuelleTurnierAuswahl().addobsGespielteSpiele(a.getSpielAuswahlErgebniseintragen());
                    auswahlklasse.getAktuelleTurnierAuswahl().removeobsAusstehendeSpiele(auswahlklasse.getSpielAuswahlErgebniseintragen());
                    l_meldungergebnis.setText("Ergebnis erfolgreich eingetragen");


                } catch (Exception e) {
                    e.printStackTrace();
                    l_meldungergebnis.setText("Satz nicht ausgefüllt");
                }

            }

        }
        if(sp!=null && sp.getStatus()==1)
        {
            System.out.println("ausstehendes Spiel");
        }
        if(sp!=null && sp.getStatus()==3)
        {
            System.out.println("gespieltes Spiel");
            ts1_1.setEditable(false);
        }

    }
    private void setzeErgebnis()
    {
        if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1)
        {

            erg = new Ergebnis(s11,s12,s21,s22,s31,s32);

            l_meldungergebnis.setText("Ergebnis ist ausgefüllt!");

            //a.getAktuelleTurnierAuswahl().
        }
    }
    @FXML
    void pressbtn_Verlegen(ActionEvent event) {

    }

    @FXML
    void pressbtn_Zurueckziehen(ActionEvent event) {

    }

    @FXML
    void pressbtn_schitzzettel(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_OK");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_OK.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Abbbruch");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_Abbbruch.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Zurueckziehen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_Zurueckziehen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Verlegen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_Verlegen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_schitzzettel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_schitzzettel.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("eins_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        eins_satz.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("zwei_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        zwei_satz.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("drei_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        drei_satz.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("vier_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        vier_satz.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("fuenf_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        fuenf_satz.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_heim");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        t_heim.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_gast");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        t_gast.setText(titel);


        if(sp!=null && sp.getStatus()==1)
        {
            System.out.println("ausstehendes Spiel");
        }
        if(sp!=null && sp.getStatus()==2)
        {
            System.out.println("aktives Spiel");
        }
        if(sp!=null && sp.getStatus()==3)
        {
            System.out.println("gespieltes Spiel");

            int[] ergebnisArray =auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis().getErgebnisArray() ;
            ts1_1.setText(String.valueOf(ergebnisArray[0]));
            ts1_2.setText(String.valueOf(ergebnisArray[1]));
            ts2_1.setText(String.valueOf(ergebnisArray[2]));
            ts2_2.setText(String.valueOf(ergebnisArray[3]));
            ts3_1.setText(String.valueOf(ergebnisArray[4]));
            ts3_2.setText(String.valueOf(ergebnisArray[5]));
        }




        //Listener für Textboxen
        if(auswahlklasse.getSpielAuswahlErgebniseintragen()!=null)
        {
            l_gast.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getGast().toString());
            l_heim.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getHeim().toString());

            if(auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis()!=null)
            {
                erg = auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis();
                System.out.println(erg);
            }

        }

        ts1_1.textProperty().addListener((observable, oldValue, newValue) -> {

                try {
                    s11 = Integer.parseInt(ts1_1.getText());
                    setzeErgebnis();


                        if(!gueltigesErgebnis(s11,s12))
                        {
                            l_meldungergebnis.setText("Satz 1 prüfen");

                        }
                        else
                        {
                            l_meldungergebnis.setText("Satz 1 OK");
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }

        });
        ts1_2.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                s12 = Integer.parseInt(ts1_2.getText());
                setzeErgebnis();

                if(s11>-1)
                {
                    if(!gueltigesErgebnis(s11,s12))
                    {
                        l_meldungergebnis.setText("Satz 1 prüfen");

                    }
                    else
                    {
                        l_meldungergebnis.setText("Satz 1 OK");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ts2_1.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                s21 = Integer.parseInt(ts2_1.getText());
                setzeErgebnis();

                if(s22>-1)
                {
                    if(!gueltigesErgebnis(s21,s22))
                    {
                        l_meldungergebnis.setText("Satz 2 prüfen");

                    }
                    else
                    {
                        l_meldungergebnis.setText("Satz 2 OK");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

                    });
        ts2_2.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                s22 = Integer.parseInt(ts2_2.getText());
                setzeErgebnis();

                if(s21>-1)
                {
                    if(!gueltigesErgebnis(s21,s22))
                    {
                        l_meldungergebnis.setText("Satz 2 prüfen");

                    }
                    else
                    {
                        l_meldungergebnis.setText("Satz 2 OK");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ts3_1.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                s31 = Integer.parseInt(ts3_1.getText());
                setzeErgebnis();

                if(s32>-1)
                {
                    if(!gueltigesErgebnis(s31,s32))
                    {
                        l_meldungergebnis.setText("Satz 3 prüfen");

                    }
                    else
                    {
                        l_meldungergebnis.setText("Satz 3 OK");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ts3_2.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                s32 = Integer.parseInt(ts1_2.getText());
                setzeErgebnis();

                if(s31>-1)
                {
                    if(!gueltigesErgebnis(s31,s32))
                    {
                        l_meldungergebnis.setText("Satz 3 prüfen");

                    }
                    else
                    {
                        l_meldungergebnis.setText("Satz 3 OK");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

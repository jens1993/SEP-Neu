package sample.GUI;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private HBox hbox_5;
    @FXML
    private HBox hbox_4;
    @FXML
    private Button btn_OK;


    @FXML
    private MenuItem menu_neuersatzhinzufuegen;

    @FXML
    private MenuItem menu_satzentfernen;


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
    private TextField ts4_1;

    @FXML
    private TextField ts4_2;
    @FXML
    private TextField ts5_1;

    @FXML
    private TextField ts5_2;
    @FXML
    private Label l_heim;
    @FXML
    private Label l_gast;
    @FXML
    private Label l_meldungergebnis;
    @FXML
    private Label eins_s;
    @FXML
    private Label zwei_s;
    @FXML
    private Label drei_s;
    @FXML
    private Label vier_s;
    @FXML
    private Label fuenf_s;
    @FXML
    private Label t_heim;
    @FXML
    private Label t_gast;
    @FXML
    private ImageView green_check_1;
    @FXML
    private ImageView green_check_2;
    @FXML
    private ImageView green_check_3;
    @FXML
    private ImageView green_check_4;
    @FXML
    private ImageView green_check_5;
    @FXML
    private ImageView red_cross_1;
    @FXML
    private ImageView red_cross_2;
    @FXML
    private ImageView red_cross_3;
    @FXML
    private ImageView red_cross_4;
    @FXML
    private ImageView red_cross_5;

    Dictionary<Integer, Spiel> dictspiele = auswahlklasse.getAktuelleTurnierAuswahl().getSpiele();

    Spiel sp = dictspiele.get(auswahlklasse.getSpielAuswahlErgebniseintragen().getSpielID());


    Ergebnis erg;
    int s11=-1;
    int s12=-1;
    int s21=-1;
    int s22=-1;
    int s31=-1;
    int s32=-1;
    int s41=-1;
    int s42=-1;
    int s51=-1;
    int s52=-1;

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

    private void leereSatzfelder(TextField textField1, TextField textField2)
    {
        textField1.setText("");
        textField2.setText("");
    }

    @FXML
    void menu_Satzentfernen(ActionEvent event) {

        if(hbox_4.isVisible()&&hbox_5.isVisible())
        {
            hbox_5.setVisible(false);
            menu_neuersatzhinzufuegen.setVisible(true);
            leereSatzfelder(ts5_1,ts5_2);
        }
        else if (hbox_4.isVisible()&&!hbox_5.isVisible())
        {
            hbox_4.setVisible(false);
            menu_satzentfernen.setVisible(false);
            leereSatzfelder(ts4_1,ts4_2);
        }



    }

    @FXML
    void menu_neuerSatz(ActionEvent event) {
        if(hbox_4.isVisible()&&!hbox_5.isVisible())
        {
            hbox_5.setVisible(true);
            menu_neuersatzhinzufuegen.setVisible(false);

        }
        if (!hbox_4.isVisible()&&!hbox_5.isVisible())
        {
            hbox_4.setVisible(true);
            menu_satzentfernen.setVisible(true);
        }
        else
        {
            System.out.println("Alle Sätze sichtbar");
        }
    }


    private void setzeErgebnis()
    {
        if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1&&s41!=-1&&s42!=-1&&s51!=-1&&s52!=-1)
        {

            erg = new Ergebnis(s11,s12,s21,s22,s31,s32,s41,s42,s51,s52);

            l_meldungergebnis.setText("Ergebnis ist ausgefüllt!");

            //a.getAktuelleTurnierAuswahl().
        }
        else if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1&&s41!=-1&&s42!=-1)
        {

            erg = new Ergebnis(s11,s12,s21,s22,s31,s32,s41,s42);

            l_meldungergebnis.setText("Ergebnis ist ausgefüllt!");

            //a.getAktuelleTurnierAuswahl().
        }
        else if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1)
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
        l_meldungergebnis.setVisible(false);
        menu_satzentfernen.setVisible(false);
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
        //btn_Abbbruch.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Zurueckziehen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
       // btn_Zurueckziehen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Verlegen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        //btn_Verlegen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_schitzzettel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        //btn_schitzzettel.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("eins_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        eins_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("zwei_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        zwei_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("drei_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        drei_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("vier_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        vier_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("fuenf_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        fuenf_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_heim");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        l_heim.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_gast");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
       l_gast.setText(titel);


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
            if(ts1_1.getText()!="") {
                try {
                    s11 = Integer.parseInt(ts1_1.getText());
                    setzeErgebnis();


                    satz1Gueltigkeit();

                } catch (Exception e) {
                    e.printStackTrace();
                    zeigeRedCross1();
                }
            }

        });
        ts1_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts1_2.getText()!="")
            {
            try {
                s12 = Integer.parseInt(ts1_2.getText());
                setzeErgebnis();

                satz1Gueltigkeit();
            } catch (Exception e) {
                zeigeRedCross1();
                e.printStackTrace();
            }}
        });
        ts2_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts1_1.getText()!="") {
                try {
                    s21 = Integer.parseInt(ts2_1.getText());
                    setzeErgebnis();

                    satz2Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross2();
                    e.printStackTrace();
                }
            }

                    });
        ts2_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts2_2.getText()!="") {
                try {
                    s22 = Integer.parseInt(ts2_2.getText());
                    setzeErgebnis();

                    satz2Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross2();
                    e.printStackTrace();
                }
            }
        });
        ts3_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts3_1.getText()!="") {
                try {
                    s31 = Integer.parseInt(ts3_1.getText());
                    setzeErgebnis();

                    satz3Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross3();
                    e.printStackTrace();
                }
            }
        });
        ts3_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts3_2.getText()!="")
            {
            try {
                s32 = Integer.parseInt(ts3_2.getText());
                setzeErgebnis();

                satz3Gueltigkeit();

            } catch (Exception e) {
                zeigeRedCross3();
                e.printStackTrace();
            }
            }
        });
        ts4_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts4_1.getText()!="") {
                try {
                    s41 = Integer.parseInt(ts4_1.getText());
                    setzeErgebnis();


                    satz4Gueltigkeit();

                } catch (Exception e) {
                    e.printStackTrace();
                    zeigeRedCross4();
                }
            }

        });
        ts4_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts4_2.getText()!="")
            {
                try {
                    s42 = Integer.parseInt(ts4_2.getText());
                    setzeErgebnis();

                    satz4Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross4();
                    e.printStackTrace();
                }}
        });
        ts5_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts5_1.getText()!="") {
                try {
                    s51 = Integer.parseInt(ts5_1.getText());
                    setzeErgebnis();


                    satz5Gueltigkeit();

                } catch (Exception e) {
                    e.printStackTrace();
                    zeigeRedCross5();
                }
            }

        });
        ts5_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(ts5_2.getText()!="")
            {
                try {
                    s52 = Integer.parseInt(ts5_2.getText());
                    setzeErgebnis();

                    satz5Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross5();
                    e.printStackTrace();
                }}
        });
    }

    private void satz3Gueltigkeit() {
        if(s32>-1&&s31>-1)
        {
            if(!gueltigesErgebnis(s31,s32))
            {
                l_meldungergebnis.setText("Satz 3 prüfen");
                zeigeRedCross3();

            }
            else
            {
                l_meldungergebnis.setText("Satz 3 OK");
                zeigeGreenCheck3();
            }
        }
    }

    private void satz1Gueltigkeit() {
        if(s11>-1&&s12>-1)
        {
            if(!gueltigesErgebnis(s11,s12))
            {
                 l_meldungergebnis.setText("Satz 1 prüfen");
                zeigeRedCross1();

            }
            else
            {
                 l_meldungergebnis.setText("Satz 1 OK");
                zeigeGreenCheck1();
            }
        }
    }

    private void satz2Gueltigkeit() {
        if(s22>-1&&s21>-1)
        {
            if(!gueltigesErgebnis(s21,s22))
            {
                l_meldungergebnis.setText("Satz 2 prüfen");
                zeigeRedCross2();

            }
            else
            {
                l_meldungergebnis.setText("Satz 2 OK");
                zeigeGreenCheck2();
            }
        }
    }
    private void satz4Gueltigkeit() {
        if(s41>-1&&s42>-1)
        {
            if(!gueltigesErgebnis(s41,s42))
            {
                l_meldungergebnis.setText("Satz 4 prüfen");
                zeigeRedCross4();

            }
            else
            {
                l_meldungergebnis.setText("Satz 4 OK");
                zeigeGreenCheck4();
            }
        }
    }
    private void satz5Gueltigkeit() {
        if(s51>-1&&s52>-1)
        {
            if(!gueltigesErgebnis(s51,s52))
            {
                l_meldungergebnis.setText("Satz 5 prüfen");
                zeigeRedCross5();

            }
            else
            {
                l_meldungergebnis.setText("Satz 5 OK");
                zeigeGreenCheck5();
            }
        }
    }
    private void zeigeGreenCheck1() {
        red_cross_1.setVisible(false);
        green_check_1.setVisible(true);
    }

    private void zeigeRedCross1() {
        red_cross_1.setVisible(true);
        green_check_1.setVisible(false);
    }
    private void zeigeGreenCheck2() {
        red_cross_2.setVisible(false);
        green_check_2.setVisible(true);
    }

    private void zeigeRedCross2() {
        red_cross_2.setVisible(true);
        green_check_2.setVisible(false);
    }
    private void zeigeGreenCheck3() {
        red_cross_3.setVisible(false);
        green_check_3.setVisible(true);
    }

    private void zeigeRedCross3() {
        red_cross_3.setVisible(true);
        green_check_3.setVisible(false);
    }
    private void zeigeGreenCheck4() {
        red_cross_4.setVisible(false);
        green_check_4.setVisible(true);
    }

    private void zeigeRedCross4() {
        red_cross_4.setVisible(true);
        green_check_4.setVisible(false);
    }
    private void zeigeGreenCheck5() {
        red_cross_5.setVisible(false);
        green_check_5.setVisible(true);
    }

    private void zeigeRedCross5() {
        red_cross_5.setVisible(true);
        green_check_5.setVisible(false);
    }
}

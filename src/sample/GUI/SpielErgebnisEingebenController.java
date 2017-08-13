package sample.GUI;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.omg.PortableInterceptor.INACTIVE;
import sample.DAO.auswahlklasse;
import sample.Ergebnis;
import sample.Spiel;
import sample.Team;

import java.net.URL;
import java.util.Dictionary;
import java.util.ResourceBundle;
import java.util.logging.Filter;

public class SpielErgebnisEingebenController implements Initializable{

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

    auswahlklasse a = new auswahlklasse();
    Dictionary<Integer, Spiel> dictspiele = a.getAktuelleTurnierAuswahl().getSpiele();
    Spiel sp = dictspiele.get(a.getSpielAuswahlErgebniseintragen().getSpielID());


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
            return false;
        }
        if (Math.abs(s11-s12)<2)
        {
            if (!((s11==29 && s12==30)||s11==30 && s12==29)){
                System.out.println("Ein Satz muss mit 2 Punkten Differenz gewonnen werden");
                System.out.println("Fehler in Satz ");
                return false;
            }
        }
        if(s11<21&&s12<21)
        {
            System.out.println("Ein Satz muss mindestens 21 Punkte haben");
            return false;
        }
        if (s11>30 || s12>30){
            System.out.println("Ein Satz kann maximal bis 30 Punkte gehen");
            return false;
        }
        if((s11>18 && s12>18) && Math.abs(s11-s12)>2 ){
            System.out.println("Ungültiges Satzergebnis ");
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

        if(sp!=null && sp.getStatus()==1)
        {


            System.out.println("ausstehendes Spiel");
            if(erg!=null) {
                try {

                    a.getSpielAuswahlErgebniseintragen().setErgebnis(erg);
                    a.getSpielAuswahlErgebniseintragen().setStatus(3);
                    a.getAktuelleTurnierAuswahl().addobsGespielteSpiele(a.getSpielAuswahlErgebniseintragen());
                    l_meldungergebnis.setText("Ergebnis eingetragen");

                } catch (Exception e) {
                    l_meldungergebnis.setText("Satz nicht ausgefüllt");
                }

            }

        }
        if(sp!=null && sp.getStatus()==2)
        {
            System.out.println("aktives Spiel");
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

            int[] ergebnisArray =a.getSpielAuswahlErgebniseintragen().getErgebnis().getErgebnisArray() ;
            ts1_1.setText(String.valueOf(ergebnisArray[0]));
            ts1_2.setText(String.valueOf(ergebnisArray[1]));
            ts2_1.setText(String.valueOf(ergebnisArray[2]));
            ts2_2.setText(String.valueOf(ergebnisArray[3]));
            ts3_1.setText(String.valueOf(ergebnisArray[4]));
            ts3_2.setText(String.valueOf(ergebnisArray[5]));
        }




        //Listener für Textboxen
        if(a.getSpielAuswahlErgebniseintragen()!=null)
        {
            l_gast.setText(a.getSpielAuswahlErgebniseintragen().getGast().toString());
            l_heim.setText(a.getSpielAuswahlErgebniseintragen().getHeim().toString());

            if(a.getSpielAuswahlErgebniseintragen().getErgebnis()!=null)
            {
                erg = a.getSpielAuswahlErgebniseintragen().getErgebnis();
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

            }
        });

    }
}

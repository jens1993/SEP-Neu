package sample.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spielklasse;

import java.net.URL;
import java.util.Dictionary;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class KlasseUebersichtController implements Initializable
{
    auswahlklasse a = new auswahlklasse();
    Dictionary<Integer,Spielklasse> turnierauswahlspielklassen = null;
    @FXML
    private VBox klassseeinzel_vbox;
    @FXML
    private VBox klasssedoppel_vbox;
    @FXML
    private VBox klasssemixed_vbox;
    //Label label2 = new Label("Search---------------------");

    public void pressBtn_neueKlassehinzufuegen(ActionEvent event) throws Exception{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzufuegen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            for (int i=0; i<a.getStages().size()-1;i++){
                a.getStages().get(i).close();
            }
            a.addStage(stage);
            stage.setScene(new Scene(root1));
            stage.show();

            stage.setTitle("Neue Klasse");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pressBtn_Spielsystem(Spielklasse spielklasse) throws Exception {
        try {
            a.spielklassenAuswahlSpeichern(spielklasse);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielSystem_neu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            for (int i=0; i<a.getStages().size()-1;i++){
                a.getStages().get(i).close();
            }
            a.addStage(stage);

            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle(spielklasse.getDisziplin()+ " "+ spielklasse.getNiveau());

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim laden");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

         turnierauswahlspielklassen= a.getAktuelleTurnierAuswahl().getSpielklassen();
         Spielklasse sp = null;
        Label label[] = new Label[turnierauswahlspielklassen.size()];
        //label[1].setText("jens");

        System.out.println("Anzahl Klassen = "+turnierauswahlspielklassen.size());
        TextFlow[] flow = new TextFlow[turnierauswahlspielklassen.size()+1];
        final Spielklasse[] spauswahl = {null};
        Hyperlink hp;
        for ( int i = 1; i <= turnierauswahlspielklassen.size(); i++) {
            sp= turnierauswahlspielklassen.get(i);
            if(sp.getSpiele()!=null&&sp.getSetzliste()!=null&&sp.getSpiele().size()>0)
            {
                sp.setSetzliste_gesperrt(true);
                //System.out.println(sp.isSetzliste_gesperrt());
                hp = new Hyperlink(sp.getDisziplin()+"-"+sp.getNiveau()+" Spiele:"+sp.getSpiele().size()+" Spieler:"+(sp.getSetzliste().size()*2));
            }
            else {
                sp.setSetzliste_gesperrt(false);
                 hp = new Hyperlink(sp.getDisziplin() + "-" + sp.getNiveau());
            }
            if(sp.getDisziplin().contains("doppel"))
            {
                flow[i] = new TextFlow(new Text("Doppelklasse: "),hp);
                flow[i].setPadding(new Insets(10));
                klasssedoppel_vbox.getChildren().addAll(flow[i]);
            }
            if(sp.getDisziplin().contains("einzel"))
            {
                flow[i] = new TextFlow(new Text("Einzelklasse: "),hp);
                flow[i].setPadding(new Insets(10));
                klassseeinzel_vbox.getChildren().addAll(flow[i]);
            }
            if(sp.getDisziplin().contains("Mix"))
            {
                flow[i] = new TextFlow(new Text("Mixedklasse: "),hp);
                flow[i].setPadding(new Insets(10));
                klasssemixed_vbox.getChildren().addAll(flow[i]);
            }


            //lbs[i]=new Label(s);//initializing labels

            int finalI = i;
            Spielklasse[] finalSp = new Spielklasse[turnierauswahlspielklassen.size()];
            finalSp[i-1]= sp;
            hp.setOnMouseClicked(event -> {
                /*spauswahl[finalI] =a.getSpielklasseDAO().getSpielklassenDict(a.getTurnierDAO().
                        read(a.getAktuelleTurnierAuswahl())).get(finalI);*/

                if(finalSp[finalI-1]!=null)
                {

                    finalSp[finalI-1] =a.getAktuelleTurnierAuswahl().getSpielklassen().get(finalI);

                   // System.out.println(spauswahl[finalI].getDisziplin());
                    try {
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                        pressBtn_Spielsystem(finalSp[finalI-1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

            //doing what you want here with labels
            //...

        }


        for (int i=1;i<=turnierauswahlspielklassen.size();i++)
        {
            //label[1].setText("Jens ist toll");
            System.out.println("jens");
            //GridPane_NeueKlasse.add(label[i],2,1);

//
//            System.out.println(sp.getDisziplin());
        }



//A label with the text element

//A label with the text element and graphical icon
//GridPane_NeueKlasse.add(label2,1,0);

    }
}

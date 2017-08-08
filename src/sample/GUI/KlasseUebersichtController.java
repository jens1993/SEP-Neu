package sample.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
    //Label label2 = new Label("Search---------------------");

    public void pressBtn_neueKlassehinzufuegen(ActionEvent event) throws Exception{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzufuegen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielSystem.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
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
        TextFlow[] flow = new TextFlow[turnierauswahlspielklassen.size()];
        final Spielklasse[] spauswahl = {null};
        for ( int i = 0; i < turnierauswahlspielklassen.size(); i++) {
            sp= turnierauswahlspielklassen.get(i+1);
            Hyperlink hp = new Hyperlink(sp.getDisziplin()+"-"+sp.getNiveau());
            flow[i] = new TextFlow(new Text("Einzelklasse: "),hp);
            flow[i].setPadding(new Insets(10));

            //lbs[i]=new Label(s);//initializing labels
            klassseeinzel_vbox.getChildren().addAll(flow[i]);
            int finalI = i;
            Spielklasse[] finalSp = new Spielklasse[turnierauswahlspielklassen.size()];
            finalSp[i]= sp;
            hp.setOnMouseClicked(event -> {
                /*spauswahl[finalI] =a.getSpielklasseDAO().getSpielklassenDict(a.getTurnierDAO().
                        read(a.getAktuelleTurnierAuswahl())).get(finalI);*/

                spauswahl[finalI] =a.getAktuelleTurnierAuswahl().getSpielklassen().get(finalI);

               // System.out.println(spauswahl[finalI].getDisziplin());
                try {
                    pressBtn_Spielsystem(finalSp[finalI]);
                } catch (Exception e) {
                    e.printStackTrace();
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

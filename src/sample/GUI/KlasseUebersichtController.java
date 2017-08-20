package sample.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spielklasse;

import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
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
            for (int i=1; i<a.getStages().size()-1;i++){
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

        
        TextFlow[] flow = new TextFlow[turnierauswahlspielklassen.size()+1];
        final Spielklasse[] spauswahl = {null};
         Hyperlink hp=null;
        Enumeration enumkeys = turnierauswahlspielklassen.keys();
        int i=1;
        while(enumkeys.hasMoreElements()){

            int key = (int)enumkeys.nextElement();
            sp= turnierauswahlspielklassen.get(key);
            if(sp.getSetzliste()!=null&&sp.getSetzliste().size()>0)
            {
                hp = new Hyperlink(sp.getDisziplin()+"-"+sp.getNiveau()+" Spieler:"+(sp.getSetzliste().size()*2));
                //System.out.println(hp+"----------1");
            }
            if(sp.getSpiele()!=null&&sp.getSetzliste()!=null&&sp.getSpiele().size()>0)
            {
                sp.setSetzliste_gesperrt(true);
                //System.out.println(sp.isSetzliste_gesperrt());
                hp = new Hyperlink(sp.getDisziplin()+"-"+sp.getNiveau()+" Spieler:"+(sp.getSetzliste().size()*2)+" Spiele:"+sp.getSpiele().size());
                //System.out.println(hp+"----------2");
            }
            if(sp.getSetzliste().size()==0||sp.getSetzliste()==null)
            {
                sp.setSetzliste_gesperrt(false);
                hp = new Hyperlink(sp.getDisziplin() + "-" + sp.getNiveau());
                //System.out.println(hp+"----------3");
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
            Hyperlink finalHp = hp;
            hp.setOnMouseClicked(event -> {
                /*spauswahl[finalI] =a.getSpielklasseDAO().getSpielklassenDict(a.getTurnierDAO().
                        read(a.getAktuelleTurnierAuswahl())).get(finalI);*/
                if (finalSp[finalI - 1] != null) {
                    finalSp[finalI - 1] = a.getAktuelleTurnierAuswahl().getSpielklassen().get(key);
                if(MouseButton.PRIMARY==event.getButton()) {




                        // System.out.println(spauswahl[finalI].getDisziplin());
                        try {
                            //((Node)(event.getSource())).getScene().getWindow().hide();
                            pressBtn_Spielsystem(finalSp[finalI - 1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                        if(MouseButton.SECONDARY==event.getButton()) {
                            ContextMenu contextMenu = new ContextMenu();
                            MenuItem item1 = new MenuItem("Spielklasse bearbeiten");
                            item1.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    //tabpane_spieler.getSelectionModel().select(tab_sphin);
                                }
                            });
                            MenuItem item2 = new MenuItem("Spielklasse löschen");
                            item2.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    System.out.println(finalSp[finalI - 1]);
                                    boolean erfolgloeschen = finalSp[finalI - 1].getSpielklasseDAO().delete(finalSp[finalI - 1]);
                                    if(erfolgloeschen)
                                    {
                                        a.InfoBenachrichtigung("erfolg", "klasse gelöscht");
                                        a.getAktuelleTurnierAuswahl().removeobs_spielklassen(finalSp[finalI - 1]);
                                        a.getAktuelleTurnierAuswahl().removeSpielklassen(finalSp[finalI - 1]);
                                    }
                                    else
                                    {
                                        a.WarnungBenachrichtigung("Fehler", "klasse konnte nicht gelöscht werden");
                                    }
                                    //tabpane_spieler.getSelectionModel().select(tab_sphin);
                                }
                            });
                            contextMenu.getItems().addAll(item1,item2);
                            finalHp.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                                @Override
                                public void handle(ContextMenuEvent event) {

                                    contextMenu.show(finalHp, event.getScreenX(), event.getScreenY());
                                }
                            });

                        }


            }});

            //doing what you want here with labels
            //...
            i++;
        }






//A label with the text element

//A label with the text element and graphical icon
//GridPane_NeueKlasse.add(label2,1,0);

    }
}
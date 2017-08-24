package sample.GUI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spielklasse;

import javax.xml.ws.RequestWrapper;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by jens on 03.08.2017.
 */
public class KlasseUebersichtController implements Initializable
{
    ObservableList<Spielklasse> obs_spielklasse=auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen();
    @FXML
    private VBox klassseeinzel_vbox;
    @FXML
    private VBox klasssedoppel_vbox;
    @FXML
    private VBox klasssemixed_vbox;
    @FXML
    private TabPane tabpane_uebersicht;
    //Label label2 = new Label("Search---------------------");

    public void pressBtn_neueKlassehinzufuegen(ActionEvent event) throws Exception{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzufuegen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
//            for (int i=0; i<a.getStages().size()-1;i++){
//                a.getStages().get(i).close();
//            }
            auswahlklasse.getStagesdict().put("KlasseHinzufuegen",stage);
            stage.setScene(new Scene(root1));
            stage.show();

            stage.setTitle("Neue Klasse");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pressBtn_Spielsystem(Spielklasse spielklasse) throws Exception {
        try {
            auswahlklasse.spielklassenAuswahlSpeichern(spielklasse);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpielSystem_neu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
//            for (int i=1; i<a.getStages().size()-1;i++){
//                a.getStages().get(i).close();
//            }
            auswahlklasse.getStagesdict().put("Spielsytem",stage);

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





        Spielklasse sp = null;
        Label label[] = new Label[obs_spielklasse.size()];
        //label[1].setText("jens");

        
        TextFlow[] flow = new TextFlow[obs_spielklasse.size()+1];
        final Spielklasse[] spauswahl = {null};
         Hyperlink hp=null;

         for(int i=0;i<obs_spielklasse.size();i++)
         {
             sp= obs_spielklasse.get(i);
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

             tabpane_uebersicht.setOnMouseClicked(event ->{
                 if(MouseButton.SECONDARY==event.getButton()) {
                     ContextMenu contextMenu = new ContextMenu();
                     MenuItem item1 = new MenuItem("Neue Spielklasse");
                     item1.setOnAction(new EventHandler<ActionEvent>() {

                         @Override
                         public void handle(ActionEvent event) {
                             try {
                                 pressBtn_neueKlassehinzufuegen(event);
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }

                     });
                     contextMenu.getItems().add(item1);
                     tabpane_uebersicht.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                         @Override
                         public void handle(ContextMenuEvent event) {

                             contextMenu.show(tabpane_uebersicht, event.getScreenX(), event.getScreenY());
                         }
                     });
                 }
             } );
             Spielklasse[] finalSp = new Spielklasse[obs_spielklasse.size()];
             int finalI = i;
             finalSp[i]= sp;
             Hyperlink finalHp = hp;
             hp.setOnMouseClicked(event -> {
                /*spauswahl[finalI] =a.getSpielklasseDAO().getSpielklassenDict(a.getTurnierDAO().
                        read(a.getAktuelleTurnierAuswahl())).get(finalI);*/
                 if (finalSp[finalI] != null) {
                     finalSp[finalI] = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(finalI);
                     if(MouseButton.PRIMARY==event.getButton()) {




                         // System.out.println(spauswahl[finalI].getDisziplin());
                         try {
                             //((Node)(event.getSource())).getScene().getWindow().hide();
                             pressBtn_Spielsystem(finalSp[finalI]);
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
                                 System.out.println(finalSp[finalI]);
                                 boolean erfolgloeschen = finalSp[finalI].getSpielklasseDAO().delete(finalSp[finalI]);
                                 if(erfolgloeschen)
                                 {
                                     auswahlklasse.InfoBenachrichtigung("erfolg", "klasse gelöscht");
                                     auswahlklasse.getAktuelleTurnierAuswahl().removeobs_spielklassen(finalSp[finalI]);
                                     auswahlklasse.getAktuelleTurnierAuswahl().removeSpielklassen(finalSp[finalI]);
                                 }
                                 else
                                 {
                                     auswahlklasse.WarnungBenachrichtigung("Fehler", "klasse konnte nicht gelöscht werden");
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
         }


            //lbs[i]=new Label(s);//initializing labels





            //doing what you want here with labels
            //...








//A label with the text element

//A label with the text element and graphical icon
//GridPane_NeueKlasse.add(label2,1,0);

    }
}
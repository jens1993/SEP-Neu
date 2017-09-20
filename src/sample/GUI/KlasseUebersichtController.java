package sample.GUI;

import com.sun.javafx.scene.control.skin.LabeledText;
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
import sample.ExcelImport;
import sample.Main;
import sample.Spielklasse;

import javax.xml.ws.RequestWrapper;
import java.net.URL;
import java.util.*;

/**
 * Created by jens on 03.08.2017.
 */
public class KlasseUebersichtController implements Initializable
{
    private MainController mainController;
    String baseName = "resources.Main";
    String titel ="";

    ObservableList<Spielklasse> obs_spielklasse=auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen();
    ContextMenu context_spielklasse = new ContextMenu();
    ContextMenu contextMenu_all = new ContextMenu();

    @FXML
    private VBox klassseeinzel_vbox;
    @FXML
    private VBox klasssedoppel_vbox;
    @FXML
    private VBox klasssemixed_vbox;
    @FXML
    private TabPane tabpane_uebersicht;

    @FXML
    private Button b_neueKlasse;
    @FXML
    private Tab tab_einzel;
    @FXML
    private Tab tab_doppel;


    //Label label2 = new Label("Search---------------------");

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void pressBtn_neueKlassehinzufuegen(ActionEvent event) throws Exception {
        try {
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzuGruppe.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzufuegen.fxml"));
            //System.out.println("test");
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            auswahlklasse.addStagesdict(stage, "klasseHinzufuegen");
            stage.setScene(new Scene(root1));
            stage.show();


            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName);
                titel = bundle.getString("klasseHinzufuegen");
            } catch (MissingResourceException e) {
                System.err.println(e);
            }
            stage.setTitle(titel);
            auswahlklasse.getStagesdict().put(titel,stage);

        } catch (Exception e) {
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
            ((SpielSystemController_neu)fxmlLoader.getController()).setMainController(mainController);

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim laden");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_neueKlasse");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_neueKlasse.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_einzel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_einzel.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_doppel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_doppel.setText(titel);



        Spielklasse sp = null;
        Label label[] = new Label[obs_spielklasse.size()];
        //label[1].setText("jens");


        TextFlow[] flow = new TextFlow[obs_spielklasse.size()+1];
        final Spielklasse[] spauswahl = {null};
        Hyperlink hp=null;

        try{
            ResourceBundle bundle = ResourceBundle.getBundle(baseName);
            String einzelklasse = bundle.getString("einzelklasse");
            String doppelklasse = bundle.getString("doppelklasse");
            String mixedklasse = bundle.getString("mixedklasse");


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
                    flow[i] = new TextFlow(new Text(doppelklasse),hp);

                    flow[i].setPadding(new Insets(10));
                    klasssedoppel_vbox.getChildren().addAll(flow[i]);
                }
                if(sp.getDisziplin().contains("einzel"))
                {
                    flow[i] = new TextFlow(new Text(einzelklasse),hp);
                    flow[i].setPadding(new Insets(10));
                    klassseeinzel_vbox.getChildren().addAll(flow[i]);
                }
                if(sp.getDisziplin().contains("Mix"))
                {
                    flow[i] = new TextFlow(new Text(mixedklasse),hp);
                    flow[i].setPadding(new Insets(10));
                    klasssemixed_vbox.getChildren().addAll(flow[i]);
                }

                tabpane_uebersicht.setOnMouseClicked(event ->{

                    if(MouseButton.SECONDARY==event.getButton()&&(event.getTarget()==klassseeinzel_vbox||event.getTarget()==klasssedoppel_vbox||event.getTarget()==klasssemixed_vbox)) {


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
                        contextMenu_all.getItems().clear();
                        contextMenu_all.getItems().add(item1);
                        tabpane_uebersicht.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                if(!contextMenu_all.isShowing())
                                {
                                    contextMenu_all.show(tabpane_uebersicht, event.getScreenX(), event.getScreenY());
                                }

                            }
                        });
                    }
                    else
                    {
                        contextMenu_all.hide();
                    }
                } );
                Spielklasse[] finalSp = new Spielklasse[Integer.valueOf(obs_spielklasse.size())];
                int finalI = i;
                finalSp[i]= sp;
                Hyperlink finalHp = hp;
                hp.setOnMouseClicked(event -> {

                /*spauswahl[finalI] =a.getSpielklasseDAO().getSpielklassenDict(a.getTurnierDAO().
                        read(a.getAktuelleTurnierAuswahl())).get(finalI);*/
                    if (finalSp[finalI] != null ) {
                        contextMenu_all.hide();
                        finalSp[finalI] = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(finalI);
                        if(MouseButton.PRIMARY==event.getButton()) {

                            context_spielklasse.hide();


                            // System.out.println(spauswahl[finalI].getDisziplin());
                            try {
                                //((Node)(event.getSource())).getScene().getWindow().hide();
                                pressBtn_Spielsystem(finalSp[finalI]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if(MouseButton.SECONDARY==event.getButton()) {

                            if(context_spielklasse.isShowing())
                            {
                                context_spielklasse.hide();
                            }
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
                                    if(finalSp[finalI].getSetzliste().size()>0)
                                    {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Spielklasse löschen");
                                        alert.setHeaderText("Das Spielsystem ist aktiv");
                                        alert.setContentText("Möchten Sie die Spielklasse wirklich löschen?");
                                        ButtonType buttonTypeOne = new ButtonType("Ja");
                                        ButtonType buttonTypeTwo = new ButtonType("Nein");
                                        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == buttonTypeOne){


                                            Spielklassekomplettloeschen(finalSp[finalI]);

                                            // ... user chose OK
                                        } else {
                                            // ... user chose CANCEL or closed the dialog
                                        }
                                    }
                                    else
                                    {
                                        Spielklassekomplettloeschen(finalSp[finalI]);
                                    }

                                    //tabpane_spieler.getSelectionModel().select(tab_sphin);
                                }
                            });
                            context_spielklasse.getItems().clear();
                            context_spielklasse.getItems().addAll(item1,item2);
                            contextMenu_all.getItems().clear();
                            finalHp.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                                @Override
                                public void handle(ContextMenuEvent event) {

                                    {
                                        context_spielklasse.show(finalHp, event.getScreenX(), event.getScreenY());
                                    }

                                }
                            });

                        }


                    }}

                    );
            }
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }


        //lbs[i]=new Label(s);//initializing labels





        //doing what you want here with labels
        //...








//A label with the text element

//A label with the text element and graphical icon
//GridPane_NeueKlasse.add(label2,1,0);

    }

    private void Spielklassekomplettloeschen(Spielklasse spielklasse) {
        if(spielklasse.getSetzliste().size()>0)
        {
            spielklasse.getSpielklasseDAO().stoppeSpielsystem(spielklasse);
        }
        Enumeration enumeration= auswahlklasse.getAktuelleTurnierAuswahl().getTeams().keys();
        while (enumeration.hasMoreElements())
        {
            int key = (int) enumeration.nextElement();
            if( auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getSpielklasse()==spielklasse)
            {
                auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getTeamDAO().delete(auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key));
            }

        }
        boolean erfolgloeschen = spielklasse.getSpielklasseDAO().delete(spielklasse);
        auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().remove(spielklasse.getSpielklasseID());
        if(erfolgloeschen)
        {
            auswahlklasse.InfoBenachrichtigung("erfolg", "klasse gelöscht");
            auswahlklasse.getAktuelleTurnierAuswahl().removeobs_spielklassen(spielklasse);
            auswahlklasse.getAktuelleTurnierAuswahl().removeSpielklassen(spielklasse);
        }
        else
        {
            auswahlklasse.WarnungBenachrichtigung("Fehler", "klasse konnte nicht gelöscht werden");
        }
    }
}
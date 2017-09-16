package sample.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.poi.poifs.macros.VBAMacroExtractor;
import sample.DAO.auswahlklasse;
import sample.Spieler;
import sample.Spielklasse;
import sample.Verein;

import java.net.URL;
import java.util.*;

/**
 * Created by jens on 15.09.2017.
 */
public class spielerEigenschaftenController implements Initializable{

    ObservableList<Spielklasse> obs_spielklasse = FXCollections.observableArrayList();

    ContextMenu context_spielklasse = new ContextMenu();
    ContextMenu contextMenu_all = new ContextMenu();

    @FXML
    private VBox vbox_spielklassen;
    @FXML
    private Tab tab_allgemein;

    @FXML
    private TextField t_vorname;

    @FXML
    private TextField t_nachname;

    @FXML
    private DatePicker d_geburtsdatum;

    @FXML
    private RadioButton r_m;

    @FXML
    private ToggleGroup geschlecht;

    @FXML
    private RadioButton r_w;

    @FXML
    private ChoiceBox<Verein> choice_verein;

    @FXML
    private TextField t_spielerid;

    @FXML
    private Tab tab_spielklassen;

    @FXML
    private Tab tab_verfuegbarkeit;

    @FXML
    private Tab tab_statistik;

    @FXML
    private Tab tab_gebuehren;

    @FXML
    private Tab tab_notizen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ladeVereine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FuelleFelder(auswahlklasse.getSpielerzumHinzufeuegen());

        ArrayList<Spielklasse> a = auswahlklasse.getSpielerzumHinzufeuegen().checkeSetzlisteMitglied(auswahlklasse.getSpielerzumHinzufeuegen());

        for(int i=0;i<a.size();i++)
        {
            obs_spielklasse.add(a.get(i));
        }
        spielklassenTab();





    }

    private void spielklassenTab() {




        Spielklasse sp = null;
        Label label[] = new Label[obs_spielklasse.size()];
        //label[1].setText("jens");


        TextFlow[] flow = new TextFlow[obs_spielklasse.size()+1];
        final Spielklasse[] spauswahl = {null};
        Hyperlink hp=null;

        try{



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
                    flow[i] = new TextFlow(new Text(""),hp);

                    flow[i].setPadding(new Insets(10));
                    vbox_spielklassen.getChildren().addAll(flow[i]);
                }
                if(sp.getDisziplin().contains("einzel"))
                {
                    flow[i] = new TextFlow(new Text(""),hp);
                    flow[i].setPadding(new Insets(10));
                    vbox_spielklassen.getChildren().addAll(flow[i]);
                }
                if(sp.getDisziplin().contains("Mix"))
                {
                    flow[i] = new TextFlow(new Text(""),hp);
                    flow[i].setPadding(new Insets(10));
                    vbox_spielklassen.getChildren().addAll(flow[i]);
                }

                vbox_spielklassen.setOnMouseClicked(event ->{

                    if(MouseButton.SECONDARY==event.getButton()&&(event.getTarget()==vbox_spielklassen)) {


                        Menu item1 = new Menu("Neue Spielklasse");
                        item1.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                   // pressBtn_neueKlassehinzufuegen(event);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                        contextMenu_all.getItems().clear();
                        contextMenu_all.getItems().add(item1);
                        vbox_spielklassen.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                if(!contextMenu_all.isShowing())
                                {
                                    contextMenu_all.show(vbox_spielklassen, event.getScreenX(), event.getScreenY());
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

    private void ladeVereine() throws Exception
    {

        ObservableList vereine = FXCollections.observableArrayList();
        Enumeration enumKeys = auswahlklasse.getVereine().keys();
        while (enumKeys.hasMoreElements()){
            int key = (int) enumKeys.nextElement();
            vereine.add(auswahlklasse.getVereine().get(key));

        }
        try {
            choice_verein.setItems(vereine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void FuelleFelder(Spieler clickedRow)
    {
        t_vorname.setText(clickedRow.getVName());
        t_nachname.setText(clickedRow.getNName());
        d_geburtsdatum.setValue(clickedRow.getGDatum());
        t_spielerid.setText(clickedRow.getExtSpielerID());
        /*t_re1.setText(String.valueOf(clickedRow.getrPunkte()[0]));
        t_rd1.setText(String.valueOf(clickedRow.getrPunkte()[1]));
        t_rm1.setText(String.valueOf(clickedRow.getrPunkte()[2]));*/
        choice_verein.getSelectionModel().select(clickedRow.getVerein());
        if(clickedRow.getGeschlecht())
        {
            r_m.setSelected(true);
        }
        else
        {
            r_w.setSelected(true);
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






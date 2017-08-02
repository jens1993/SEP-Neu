package sample.GUI;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

import javafx.util.Callback;
import sample.Spielsysteme.*;
import sample.Enums.*;
import sample.*;
import sample.DAO.*;

import javax.swing.JList;


public class Controller {
    TurnierDAO turnierDao = new TurnierDAOimpl();
    Dictionary<Integer,Turnier>  turnierliste = turnierDao.getAllTurniere();
    private ObservableList<Turnier> turnierData = FXCollections.observableArrayList();
    @FXML
    private TextField t_vn;
    @FXML
    private TextField Turniername;
    @FXML
    private DatePicker turnierDatum;
    @FXML
    private TextField AnzahlFelder;
    @FXML
    private TextField t_nn;
    @FXML
    private TextField t_geb;
    @FXML
    private TextField t_spid;
    @FXML
    private TextField t_re;
    @FXML
    private TextField t_rd;
    @FXML
    private TextField t_rm;
    @FXML
    private RadioButton r_m;
    @FXML
    private RadioButton r_w;
    @FXML
    private TableView tabelle_spielerliste;
    @FXML
    private TableView TurnierlisteTabelle;
    @FXML
    public TableColumn TurnierDatumSpalte;
    @FXML
    public TableColumn TurnierNameSpalte;
    @FXML
    public TableColumn TurnierIDSpalte;

    @FXML
    private TableColumn spielertab_vorname;
    @FXML
    public TableColumn spielertab_nachname;
    @FXML
    public ListView liste_spieler;
    

    @FXML
    private RadioButton radio_gruppe;
    @FXML
    private RadioButton radio_gruppeMitE;
    @FXML
    private RadioButton radio_ko;
    @FXML
    private RadioButton radio_schweizer;

    @FXML
    private RadioButton radio_trostJa;
    @FXML
    private RadioButton radio_trostNein;

    @FXML
    private Pane gruppe;
    @FXML
    private AnchorPane gruppeMitEndrunde;
    @FXML
    private AnchorPane koSystem;
    @FXML
    private AnchorPane koTrostRundeJa;
    @FXML
    private AnchorPane koTrostRundeNein;
    @FXML
    private AnchorPane schweizerSystem;

    private static int index_niveau=100;
    private static int index_diszipin=100;
    @FXML
    private void setNiveau_auswahl(ActionEvent event){
        //SingleSelectionModel niveau_auswahl = combo_niveau.getSelectionModel();
        //a = combo_niveau.getSelectionModel().getSelectedIndex();
        //System.out.println("hallo -----"+niveau_auswahl.toString());
        //combo_niveau.getSelectionModel().select(niveau_auswahl);
        //test1=combo_niveau.getItems().get(a);
        // System.out.println(test1.toString());
        //System.out.println(niveau_auswahl.toString());
        //System.out.println(a);
        //combo_niveau.setSelectionModel();
        index_niveau = combo_niveau.getSelectionModel().getSelectedIndex();
    }
    @FXML
    private void setDisziplin_auswahl(ActionEvent event){
        index_diszipin = combo_disziplin.getSelectionModel().getSelectedIndex();
    }

    @FXML
    private void klassenSwitch(ActionEvent event) throws IOException, InterruptedException {

        if(radio_gruppe.isSelected()) {
            gruppe.toFront();
            gruppe.setVisible(true);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(false);
                    }

        else if(radio_gruppeMitE.isSelected()) {
            gruppeMitEndrunde.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(true);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(false);
                    }

        else if(radio_ko.isSelected()){
            koSystem.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(true);
            schweizerSystem.setVisible(false);
                    }

        else if(radio_schweizer.isSelected()){
            schweizerSystem.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(true);
                    }
        else{
            //label1.setText("");
            //label2.setText("");
            //label3.setText("");
            //hbox_1.getChildren().clear();
            //hbox_2.getChildren().clear();
        }
    }

    @FXML
    private void trostSwitch(ActionEvent event) throws IOException{
        if(radio_trostNein.isSelected()){
            koTrostRundeNein.setVisible(true);
            koTrostRundeJa.setVisible(false);
                   }
        else{
            koTrostRundeNein.setVisible(false);
            koTrostRundeJa.setVisible(true);
        }
    }
    @FXML
    public void auswahlSpeichern(ActionEvent event) throws Exception {
        try {
            System.out.println("test");
            TurnierlisteTabelle.getSelectionModel().

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void printTable(ActionEvent event) throws Exception {

        ObservableList<Turnier> turniere = FXCollections.observableArrayList();
        for (int i=1;i<=turnierliste.size();i++){
            turniere.add(turnierliste.get(i));
        }
        TurnierlisteTabelle.setItems(turniere);
        TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,Date>("datum"));
        TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,String>("name"));
        TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,Integer>("turnierid"));

       // TurnierlisteTabelle.getColumns().addAll(TurnierDatumSpalte,TurnierNameSpalte,TurnierIDSpalte);
       /* try {
                for(int i =1;i<=turnierliste.size();i++)
                {

                    System.out.println(turnierliste.get(i).getName());
                    //TurnierlisteTabelle.getColumns().addAll(TurnierDatumSpalte,TurnierNameSpalte,TurnierIDSpalte);
                   // TurnierlisteTabelle.getColumns().addAll(turnierliste.get(i).getDatum(),turnierliste.get(i).getName(),turnierliste.get(i).getTurnierid());

                }



//            System.out.println(turnierliste.get(1).getName());
//            System.out.println(turnierliste.get(20).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
/*
        TurnierDAO test = new TurnierDAOimpl();
        Turnier turnier = test.read(1);
        turnier.getSpieler().;
        System.out.println("test");
*/

       // tabelle_spielerliste.ad

    }/*
    public String getSpielerName(int id)
    {
    	String sql = "SELECT ID,VNAME, NNAME FROM spieler";
    	ResultSet r = executeSQL(sql);
    	try {
    		while(r.next())
    		{ 
    			if(id==Integer.parseInt(r.getString(1))) //Spalte 1 = firstname
    			{
    				return r.getString(2)+" "+r.getString(3);
    			}
    		}
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return " Nicht gefunden"; //�berpr�fung in main, ob nicht -1 return
    }*/
@FXML
public void ladeVereine(ActionEvent event) throws Exception
{

    VereinDAO verein = new VereinDAOimpl();

}
@FXML
public void erstelleTurnier(ActionEvent event) throws Exception
{
    String date = turnierDatum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    LocalDate Datum = turnierDatum.getValue();
    System.out.println("Felder:  "+AnzahlFelder.getText());
    System.out.println("Name " +Turniername.getText());
    System.out.println("Datum " + date);
    TurnierDAO t = new TurnierDAOimpl();
    Turnier turnier = new Turnier(Turniername.getText(),turnierliste.size()+1, Datum);
    t.create(turnier);
    System.out.println("Erfolg");
}
public void SpeicherSpieler(ActionEvent event)throws Exception
{
    String vname = t_vn.getText();
    String nname = t_nn.getText();
   

    int spid = Integer.parseInt(t_spid.getText());
    int ire=Integer.parseInt(t_re.getText());
    int ird=Integer.parseInt(t_rd.getText());
    int irm=Integer.parseInt(t_rm.getText());
     String geb = t_geb.getText();

    boolean rm = r_m.isSelected();
    boolean rw = r_w.isSelected();


/*    try {
    
        SpielerDAO sp = new SpielerDAOimpl();
    	System.out.println("Speichern");
        Spieler.spielerHinzufueg(vname,nname);
        Spieler.spielerHinzufuegen(vname,nname,geb,spid,ire,ird,irm,rm,rw);
		liste_spieler.getItems().add(vname+" "+nname);
    } catch (Exception e) {
        e.printStackTrace();
    }*/
}

    public void pressBtn_spieler(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Spieler");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    
    public void pressBtn_klassen(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzuGruppe.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Klassen");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void pressBtn_neuesTurnier(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("neuesTurnier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Neues Turnier");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void pressBtn_turnierLaden (ActionEvent event) throws Exception {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Turnierladen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Turnier auswählen");

    }
    @FXML
    public  ComboBox<Niveau> combo_niveau = new ComboBox<>();


    
    @FXML
    public ComboBox<Disziplin> combo_disziplin = new ComboBox<>();
    @FXML
    public void comboBoxFill() throws IOException{
        try{
            combo_niveau.getItems().setAll(Niveau.values());
            combo_disziplin.getItems().setAll(Disziplin.values());
            combo_niveau.getSelectionModel().select(index_niveau);
            combo_disziplin.getSelectionModel().select(index_diszipin);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //combo_niveau.getSelectionModel().select(0);
        //combo_niveau.setItems( FXCollections.observableArrayList((E[]) Niveau.values()));
        //combo_disziplin.setItems(Disziplin.Herreneinzel);
    }



}

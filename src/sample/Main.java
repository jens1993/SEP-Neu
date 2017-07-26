package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import sample.Spielsysteme.*;
import sample.Enums.*;
import sample.DAO.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/sample.fxml"));
        primaryStage.setTitle("Badminton Turnierverwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
    	
    	 //testverbindung.PrintResult(r);
        //launch(args);
        //SQLConnection testverbindung = new SQLConnection();
        //KO testsystem = new KO(65);
        //testsystem.rundenBerechnen();


        //VereinDAOimpl test = new VereinDAOimpl();
        //Verein Osterbrock = new Verein(5, "123456789","Osterbrock",  "BWE");
        //Verein Testest = new Verein(6, "123456789","Osterbrock",  "BWE");
        //test.create(Osterbrock);
        //test.update(Testest);
        //test.delete(Osterbrock);

       /* SpielerDAO test = new SpielerDAOimpl();
        List<Spieler> alleSpieler;
        alleSpieler = test.getAllSpieler();
        for (int i=0; i < alleSpieler.size();i++){
            System.out.println(alleSpieler.get(i).getName());
        }*/

        /*SpielklasseDAO test = new SpielklasseDAOimpl();
        Spielklasse deb = new Spielklasse(5, "Dameneinzel", "B", 1);
        test.create(deb);
        test.update(deb);
        test.delete(deb);
        List<Spielklasse> alleKlassen = test.getAllSpielklassen();
        for (int i=0; i < alleKlassen.size();i++){
            System.out.println(alleKlassen.get(i).getDisziplin()+alleKlassen.get(i).getNiveau());
        }*/
        Spielklasse spielklasse = new Spielklasse(1,"Herreneinzel","A",1);
        Spieler spieler = new Spieler("Herbert", "Müller",1);
        Spieler spieler2 = new Spieler("Herbert", "Müller",2);
        Spiel spiel = new Spiel(spieler, spieler2,spielklasse);
        Ergebnis ergebnis = new Ergebnis(21,15,21,19);
        spiel.setErgebnis(ergebnis);
        ErgebnisDAO test = new ErgebnisDAOimpl();
        test.update(spiel);

        //for (int i=0; i<setzliste.size(); i++){
          //  System.out.println((i+1)+" "+setzliste.get(i).getName());
        //}
        //Spielsystem spielsystem = new KO(setzliste);

        //SpielDAO test = new SpielDAOimpl();
        //Spielklasse spielklasse = new Spielklasse(1, "Herren", "A", 1);

//        Spiel testspiel = new Spiel(1,1,2,spielklasse);



       /* SpielDAOimpl test = new SpielDAOimpl();
        Spielklasse heA = new Spielklasse(5);
        Spieler hans = new Spieler("Hans", "Müller", 41);
        Spieler harald = new Spieler("Harald", "Test", 42);
        Spiel spiel1 = new Spiel(30, hans, harald, heA);
        test.delete(spiel1);*/
        //KO test = new KO(16);


        //Alle Spieler bei Programmstart einlesen: (in einzelnen Thread packen)
        //vereineEinlesen();
        //spielerEinlesen();


        /*SQLConnection testverbindung = new SQLConnection();
        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        boolean hallo = testverbindung.insertSpieler("jens", "isttoll");
        System.out.println("Einfügen = "+hallo);
        
        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        String name = testverbindung.getSpielerName(1);
        System.out.println("Spielername: "+name);

        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        int i = testverbindung.getSpielerID("jens", "isttoll");
        if(i>0)
        {
        	System.out.println("Der Index lautet "+i);
        }
        else
        {
        	System.out.println("Spieler nicht gefunden");
        }
        
        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        ResultSet j = testverbindung.getSpielerr(i);
        testverbindung.PrintResult(j);
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        ResultSet r = testverbindung.executeSQL("SELECT * FROM spieler");
        testverbindung.PrintResult(r);
*/
    }
}

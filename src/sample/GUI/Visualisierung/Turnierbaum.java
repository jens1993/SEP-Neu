package sample.GUI.Visualisierung;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.*;
import sample.DAO.auswahlklasse;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class Turnierbaum {

    Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(0);
    Dictionary<Integer,Spiel> alleSpiele = spielklasse.getSpiele();
    int anzahlSpiele = alleSpiele.size();
    double anzahlTeilnehmerDouble = (((Math.sqrt(1 + anzahlSpiele*2*4))/2*2)+1)/2;     //(1/2) + (((1/4) + anzahlSpiele*2)^(1/2))
    int anzahlTeilnehmer = (int) anzahlTeilnehmerDouble;


    public void erstelleTurnierbaum(Spielklasse spielklasse, Tab tab) {
        ArrayList<ArrayList<Spiel>> runden = spielklasse.getSpielsystem().getRunden();
        int xObenLinks = 20; //Startpunkt
        int yObenLinks = 20;
        int breite = 200;
        int hoehe = 50;
        int xAbstand = 100;
        int yAbstand = 20;
        int gesamtHoehe =runden.get(0).size()*(hoehe+yAbstand)+yObenLinks+2-yAbstand;
        int gesamtBreite = runden.size()*(breite+xAbstand)+xObenLinks+2-xAbstand;

        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AnchorPane anchorPane = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        tab.setContent(scrollPane);
        scrollPane.setContent(anchorPane);
        anchorPane.getChildren().add(canvas);
        canvas.setHeight(gesamtHoehe);
        canvas.setWidth(gesamtBreite);
        canvas.setStyle("-fx-background-color: deeppink");
        scrollPane.setStyle("-fx-background-color: yellow");
        anchorPane.setStyle("-fx-background-color: #96b946");

        gc.setFill(Color.rgb(216,216,216));
        gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());

        ArrayList<TurnierbaumSpiel> letzteRunde = new ArrayList<>();
        ArrayList<TurnierbaumSpiel> neueRunde = new ArrayList<>();
        for(int j=0; j<runden.get(0).size();j++){

            Spiel aktuellesSpiel = runden.get(0).get(j);
            TurnierbaumSpiel turnierbaumSpiel = new TurnierbaumSpiel(xObenLinks,yObenLinks,breite,hoehe,aktuellesSpiel,xAbstand, yAbstand);
            turnierbaumSpiel.draw(gc);
            yObenLinks += hoehe + yAbstand;
            letzteRunde.add(turnierbaumSpiel);

        }

        for(int i=0;i<letzteRunde.size();i++){
            if(i%2==0){
                TurnierbaumSpiel neuesSpiel = letzteRunde.get(i).neuesSpielerstellen(gc);
                if (neuesSpiel!=null) {
                    neuesSpiel.draw(gc);
                    letzteRunde.add(neuesSpiel);
                }
            }
            else{
                letzteRunde.get(i).linieZuNaechstemSpiel(letzteRunde.get(i),letzteRunde.get(letzteRunde.size()-1),gc);
            }
        }

        for (int zeile = 0; zeile < anzahlTeilnehmer; zeile++) {
            for (int spalte = 0; spalte < anzahlTeilnehmer; spalte++) {
                //Hier die Zellen der Tabelle erstellen

            }
        }
    }

}



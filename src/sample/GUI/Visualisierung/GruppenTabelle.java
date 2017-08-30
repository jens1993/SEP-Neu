package sample.GUI.Visualisierung;

import com.sun.javafx.scene.control.skin.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.*;
import sample.DAO.auswahlklasse;

import java.util.ArrayList;
import java.util.Dictionary;

public class GruppenTabelle {
    private int yObenLinks;
    private int xObenLinks;
    private int breite;
    private int hoehe;
    private Spiel spiel;
    private int xAbstand;
    private int yAbstand;



        Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(0);
        Dictionary<Integer, Spiel> alleSpiele = spielklasse.getSpiele();
        int anzahlSpiele = alleSpiele.size();
        double anzahlTeilnehmerDouble = (((Math.sqrt(1 + anzahlSpiele * 2 * 4)) / 2 * 2) + 1) / 2;     //(1/2) + (((1/4) + anzahlSpiele*2)^(1/2))
        int anzahlTeilnehmer = (int) anzahlTeilnehmerDouble;


    public void erstelleGruppenTabelle(Spielklasse spielklasse,GraphicsContext gc){

        this.yObenLinks = yObenLinks;
        this.xObenLinks = xObenLinks;
        this.breite = breite;
        this.hoehe = hoehe;
        this.spiel = spiel;
        this.xAbstand=xAbstand;
        this.yAbstand=yAbstand;
        ArrayList<Team> teams = spielklasse.getSetzliste();
        ArrayList<ArrayList<Spiel>> runden = spielklasse.getSpielsystem().getRunden();
        int xObenLinks = 20; //Startpunkt
        int yObenLinks = 20;
        int breite = 200;
        int hoehe = 50;
        int xAbstand = 100;
        int yAbstand = 20;
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


        gc.setFill(Color.BLACK);
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(xObenLinks, yObenLinks);
        gc.lineTo(xObenLinks+breite, yObenLinks);
        gc.lineTo(xObenLinks+breite, yObenLinks+hoehe);
        gc.lineTo(xObenLinks, yObenLinks+hoehe);
        gc.lineTo(xObenLinks, yObenLinks);
        gc.stroke();
        gc.closePath();
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(xObenLinks,yObenLinks+ hoehe*0.5);
        gc.lineTo(xObenLinks+breite,yObenLinks+hoehe*0.5);
        gc.stroke();
        gc.closePath();

        /*Font schriftart = new Font("Calibri",12);
        Font fetteschriftart = new Font ("Calibri Bold", 12);
            gc.beginPath();
            gc.setStroke(Color.RED);
            gc.setLineWidth(5);
            gc.moveTo(60, 60);
            gc.lineTo(660, 60);
            gc.lineTo(660, 310);
            gc.lineTo(60, 310);
            gc.lineTo(60, 60);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(5);
            gc.moveTo(180, 60);
            gc.lineTo(180, 310);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(300, 60);
            gc.lineTo(300, 310);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(420, 60);
            gc.lineTo(420, 310);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(540, 60);
            gc.lineTo(540, 310);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(660, 60);
            gc.lineTo(660, 310);
            gc.stroke();
            gc.closePath();
////////////////////////////////////
            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(5);
            gc.moveTo(60, 110);
            gc.lineTo(660, 110);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(60, 160);
            gc.lineTo(660, 160);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(60, 210);
            gc.lineTo(660, 210);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(60, 260);
            gc.lineTo(660, 260);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.moveTo(60, 310);
            gc.lineTo(660, 310);
            gc.stroke();
            gc.closePath();
            //////////////////////////////////////
            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(50);
            gc.moveTo(205, 135);
            gc.lineTo(275, 135);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(50);
            gc.moveTo(325, 185);
            gc.lineTo(395, 185);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(50);
            gc.moveTo(445, 235);
            gc.lineTo(515, 235);
            gc.stroke();
            gc.closePath();

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(50);
            gc.moveTo(565, 285);
            gc.lineTo(635, 285);
            gc.stroke();
            gc.closePath();*/

        }
    }

package sample.GUI.Visualisierung;

import com.sun.javafx.scene.control.skin.Utils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.*;
import sample.DAO.auswahlklasse;

import java.util.ArrayList;
import java.util.Dictionary;

public class GruppenTabelle {
    private Spielklasse spielklasse;
    private Tab tab;

    public GruppenTabelle(Spielklasse spielklasse, Tab tab) {
        this.spielklasse = spielklasse;
        this.tab = tab;
    }

    public void erstelleGruppenTabelle(){

        ArrayList<Team> teams = spielklasse.getSetzliste();
        ArrayList<ArrayList<Spiel>> runden = spielklasse.getSpielsystem().getRunden();

        int anzahlSpiele =spielklasse.getSpiele().size();
        double anzahlTeilnehmerDouble = (((Math.sqrt(1 + anzahlSpiele * 2 * 4)) / 2 * 2) + 1) / 2;
        int anzahlTeilnehmer = (int) anzahlTeilnehmerDouble;

        int xObenLinksLeereZelle = 20;
        int yObenLinksLeereZelle = 20;
        int xObenLinks = 170; //Startpunkt
        int yObenLinks = 20;
        int zellenBreite = 150;
        int zellenHoehe = 50;
        int xAbstand = 100;
        int yAbstand = 20;

        Canvas spieluebersicht = new Canvas(2000,2000);
        GraphicsContext gc = spieluebersicht.getGraphicsContext2D();
        ScrollPane scrollPane = new ScrollPane();
        tab.setContent(scrollPane);
        scrollPane.setContent(spieluebersicht);

        //Leere Zelle oben links erstellen
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.beginPath();
        gc.moveTo(xObenLinksLeereZelle, yObenLinksLeereZelle);
        gc.lineTo(xObenLinksLeereZelle+zellenBreite, yObenLinksLeereZelle);
        gc.lineTo(xObenLinksLeereZelle+zellenBreite, yObenLinksLeereZelle+zellenHoehe);
        gc.lineTo(xObenLinksLeereZelle, yObenLinksLeereZelle+zellenHoehe);
        gc.lineTo(xObenLinksLeereZelle, yObenLinksLeereZelle);
        gc.stroke();
        gc.closePath();


        for(int i=0; i<teams.size();i++){

            Team aktuellesTeam = teams.get(i);
            if(!aktuellesTeam.isFreilos()) {
                gc.beginPath();
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(1);
                gc.beginPath();
                gc.moveTo(xObenLinks + i * zellenBreite, yObenLinks - i * zellenHoehe);
                gc.lineTo(xObenLinks + i * zellenBreite + zellenBreite, yObenLinks - i * zellenHoehe);
                gc.lineTo(xObenLinks + i * zellenBreite + zellenBreite, yObenLinks - i * zellenHoehe + zellenHoehe);
                gc.lineTo(xObenLinks + i * zellenBreite, yObenLinks - i * zellenHoehe + zellenHoehe);
                gc.lineTo(xObenLinks + i * zellenBreite, yObenLinks - i * zellenHoehe);
                gc.fillText(aktuellesTeam.toString(), xObenLinks + i * zellenBreite + 40, yObenLinks - i * zellenHoehe + 30);

                gc.stroke();
                gc.closePath();


                gc.beginPath();
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(1);
                gc.beginPath();
                gc.moveTo(xObenLinks - zellenBreite, yObenLinks + zellenHoehe);
                gc.lineTo(xObenLinks, yObenLinks + zellenHoehe);
                gc.lineTo(xObenLinks, yObenLinks + zellenHoehe + zellenHoehe);
                gc.lineTo(xObenLinks - zellenBreite, yObenLinks + zellenHoehe + zellenHoehe);
                gc.lineTo(xObenLinks - zellenBreite, yObenLinks + zellenHoehe);
                gc.fillText(aktuellesTeam.toString(), xObenLinks - zellenBreite + 40, yObenLinks + zellenHoehe + 30);
                gc.stroke();
                gc.closePath();


                //Titelreihe und Titelspalte erstellen

                yObenLinks += zellenHoehe;
            }

        }

        for (int zeile = 0; zeile < anzahlTeilnehmer; zeile++) {
            for (int spalte = 0; spalte < anzahlTeilnehmer; spalte++) {
                //Hier die Zellen der Tabelle erstellen


                /*gc.beginPath();
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(1);
                gc.beginPath();
                gc.moveTo(xObenLinks-zellenBreite, yObenLinks+zellenHoehe-yAbstand);
                gc.lineTo(xObenLinks, yObenLinks+zellenHoehe-yAbstand);
                gc.lineTo(xObenLinks, yObenLinks+zellenHoehe+zellenHoehe-yAbstand);
                gc.lineTo(xObenLinks-zellenBreite, yObenLinks+zellenHoehe+zellenHoehe-yAbstand);
                gc.lineTo(xObenLinks-zellenBreite, yObenLinks+zellenHoehe-yAbstand);
                gc.stroke();
                gc.closePath();*/

            }
        }



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

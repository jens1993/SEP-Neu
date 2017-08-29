package sample.GUI.Visualisierung;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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


    public void erstelleTurnierbaum(GraphicsContext gc) {
        Enumeration e = alleSpiele.keys();
            int key =(int) e.nextElement();
            Spiel spiel = alleSpiele.get(key);


        // 4-Teams-Turnierbaum ->

        TurnierbaumSpiel turnierbaumSpiel = new TurnierbaumSpiel(60,330,120,50,spiel);
        turnierbaumSpiel.draw(gc);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(60, 355);
        gc.lineTo(180, 355);
        gc.moveTo(120, 355);
        gc.lineTo(120, 380);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(190, 355);
        gc.lineTo(270, 355);
        gc.lineTo(270, 415);
        gc.lineTo(190, 415);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(270, 385);
        gc.lineTo(310, 385);
        gc.stroke();
        gc.closePath();


        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(60, 390);
        gc.lineTo(180, 390);
        gc.lineTo(180, 440);
        gc.lineTo(60, 440);
        gc.lineTo(60, 390);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(60, 415);
        gc.lineTo(180, 415);
        gc.moveTo(120, 415);
        gc.lineTo(120, 440);
        gc.stroke();
        gc.closePath();


        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(60, 460);
        gc.lineTo(180, 460);
        gc.lineTo(180, 510);
        gc.lineTo(60, 510);
        gc.lineTo(60, 460);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(60, 485);
        gc.lineTo(180, 485);
        gc.moveTo(120, 485);
        gc.lineTo(120, 510);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(190, 485);
        gc.lineTo(270, 485);
        gc.lineTo(270, 545);
        gc.lineTo(190, 545);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(270, 515);
        gc.lineTo(310, 515);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(60, 520);
        gc.lineTo(180, 520);
        gc.lineTo(180, 570);
        gc.lineTo(60, 570);
        gc.lineTo(60, 520);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(60, 545);
        gc.lineTo(180, 545);
        gc.moveTo(120, 545);
        gc.lineTo(120, 570);
        gc.stroke();
        gc.closePath();

        ////////////////////////

        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(320, 360);
        gc.lineTo(440, 360);
        gc.lineTo(440, 410);
        gc.lineTo(320, 410);
        gc.lineTo(320, 360);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(320, 385);
        gc.lineTo(440, 385);
        gc.moveTo(380, 385);
        gc.lineTo(380, 410);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(450, 385);
        gc.lineTo(530, 385);
        gc.lineTo(530, 515);
        gc.lineTo(450, 515);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(530, 450);
        gc.lineTo(570, 450);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(320, 490);
        gc.lineTo(440, 490);
        gc.lineTo(440, 540);
        gc.lineTo(320, 540);
        gc.lineTo(320, 490);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(320, 515);
        gc.lineTo(440, 515);
        gc.moveTo(380, 515);
        gc.lineTo(380, 540);
        gc.stroke();
        gc.closePath();

        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(580, 425);
        gc.lineTo(700, 425);
        gc.lineTo(700, 475);
        gc.lineTo(580, 475);
        gc.lineTo(580, 425);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(580, 450);
        gc.lineTo(700, 450);
        gc.moveTo(640, 450);
        gc.lineTo(640, 475);
        gc.stroke();
        gc.closePath();
        ////////////////////////////////////////////

        int spaltenBreite = 60;
        int zeilenBreite = 50;
        for (int zeile = 0; zeile < anzahlTeilnehmer; zeile++) {
            for (int spalte = 0; spalte < anzahlTeilnehmer; spalte++) {
                //Hier die Zellen der Tabelle erstellen

            }
        }
    }
    //gc.strokeText("DUfiuzefuzis isatgwafaw", 300,200);
    /////////////////////////////////////////////////////////////////////

}



package sample.GUI.Visualisierung;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spielklasse;

import java.util.Dictionary;

public class GruppenTabelle {
    Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(0);
    Dictionary<Integer,Spiel> alleSpiele = spielklasse.getSpiele();
    int anzahlSpiele = alleSpiele.size();
    double anzahlTeilnehmerDouble = (((Math.sqrt(1 + anzahlSpiele*2*4))/2*2)+1)/2;     //(1/2) + (((1/4) + anzahlSpiele*2)^(1/2))
    int anzahlTeilnehmer = (int) anzahlTeilnehmerDouble;

    public void erstelleGruppenTabelle(Spielklasse spielklasse, GraphicsContext gc){
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
        gc.closePath();

    }
}

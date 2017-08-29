package sample.GUI.Visualisierung;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Ergebnis;
import sample.Spiel;

public class TurnierbaumSpiel {
    private int yObenLinks;
    private int xObenLinks;
    private int breite;
    private int hoehe;
    private Spiel spiel;
    private int xAbstand;
    private int yAbstand;


   /* public TurnierbaumSpiel(int xObenLinks, int yObenLinks, int breite, int hoehe, Spiel spiel) {
        this.yObenLinks = yObenLinks;
        this.xObenLinks = xObenLinks;
        this.breite = breite;
        this.hoehe = hoehe;
        this.spiel = spiel;
    }
*/
    public TurnierbaumSpiel(int xObenLinks, int yObenLinks, int breite, int hoehe, Spiel spiel, int xAbstand, int yAbstand) {
        this.yObenLinks = yObenLinks;
        this.xObenLinks = xObenLinks;
        this.breite = breite;
        this.hoehe = hoehe;
        this.spiel = spiel;
        this.xAbstand=xAbstand;
        this.yAbstand=yAbstand;
    }

    public int getXRechtsMitte(){
        return xObenLinks+breite;
    }
    public int getYRechtsMitte(){
        return yObenLinks+(hoehe/2);
    }
    public int getXLinksMitte(){
        return xObenLinks;
    }
    public int getYLinksMitte(){
        return yObenLinks+hoehe/2;
    }

    public void draw(GraphicsContext gc){
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(xObenLinks, yObenLinks);
        gc.lineTo(xObenLinks+breite, yObenLinks);
        gc.lineTo(xObenLinks+breite, yObenLinks+hoehe);
        gc.lineTo(xObenLinks, yObenLinks+hoehe);
        gc.lineTo(xObenLinks, yObenLinks);
        gc.strokeText(spiel.getHeimString(),xObenLinks+10,yObenLinks+20);
        gc.stroke();
        gc.closePath();
    }

    public void linieZuNaechstemSpiel(TurnierbaumSpiel spiel1, TurnierbaumSpiel spiel2, GraphicsContext gc){
        Ergebnis ergebnis = spiel.getErgebnis();
        int xStart = spiel1.getXRechtsMitte();
        int yStart = spiel1.getYRechtsMitte();
        int xEnde = spiel2.getXLinksMitte();
        int yEnde = spiel2.getYLinksMitte();
        int xWendePunkt = (int) (xStart+ 0.8*(xEnde-xStart));
        gc.beginPath();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(xStart, yStart);
        gc.lineTo(xWendePunkt, yStart);
        gc.lineTo(xWendePunkt, yEnde);
        gc.lineTo(xEnde, yEnde);
        gc.stroke();
        gc.closePath();
        if(ergebnis!=null){
            ergebnis.getErgebnisArray(); //[0] = satz1Heim [1] = satz1Gast [2] = satz2Heim.....
        }
    }

    public TurnierbaumSpiel neuesSpielerstellen(GraphicsContext gc) {
        int neuXObenLinks = xObenLinks+breite+xAbstand;
        int neuYObenLinks = yObenLinks +(((2*hoehe+yAbstand)/2)-hoehe/2);
        int neueSystemSpielID = neueSystemSpielIDberechnen() ;
        int neuerYAbstand = 2*yAbstand+hoehe;
        Spiel neuesSpiel = spiel.getSpielsystem().getSpielklasse().getSpiele().get(neueSystemSpielID);
        if (neuesSpiel!=null) {
            TurnierbaumSpiel turnierbaumSpiel = new TurnierbaumSpiel(neuXObenLinks, neuYObenLinks, breite, hoehe, neuesSpiel, xAbstand, neuerYAbstand);
            linieZuNaechstemSpiel(this,turnierbaumSpiel,gc);
            return turnierbaumSpiel;
        }
        else{
            return null;
        }
    }

    private int neueSystemSpielIDberechnen() {
        int alteSystemSpielID = spiel.getSystemSpielID();
        int neueSystemSpielID = alteSystemSpielID - spiel.getSpielsystem().getSpielSystemArt()*10000000;
        int alteRundenNummer = (neueSystemSpielID/1000);
        neueSystemSpielID = neueSystemSpielID - alteRundenNummer*1000;
        neueSystemSpielID = ((int)neueSystemSpielID/2);
        neueSystemSpielID = neueSystemSpielID+(alteRundenNummer-1)*1000;
        neueSystemSpielID += spiel.getSpielsystem().getSpielSystemArt()*10000000;
        System.out.println(neueSystemSpielID);
        return neueSystemSpielID;
    }
}

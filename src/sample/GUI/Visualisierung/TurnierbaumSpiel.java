package sample.GUI.Visualisierung;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.Ergebnis;
import sample.Spiel;

import javax.xml.transform.*;
import java.awt.*;
import java.util.Properties;

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
        Font schriftart = new Font("Calibri Light",12);
        Font fetteschriftart = new Font ("Calibri Bold", 12);
        /*for (int i=0;i<Font.getFontNames().size();i++){
            System.out.println(Font.getFontNames().get(i));
        }*/
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
        gc.lineTo(xObenLinks+breite*0.42,yObenLinks+hoehe*0.5);
        gc.moveTo(xObenLinks+breite*0.59,yObenLinks+ hoehe*0.5);
        gc.lineTo(xObenLinks+breite,yObenLinks+hoehe*0.5);
        gc.fillText("gegen",xObenLinks+breite*0.43,yObenLinks+hoehe*0.55);
        gc.stroke();
        gc.closePath();

        if (spiel.getHeim()==spiel.getSieger()&&spiel.getHeim()!=null) {
            gc.setFont(fetteschriftart);
            gc.fillText(spiel.getHeimString(), xObenLinks + 10, yObenLinks + 15);
        }
        else{
            gc.setFont(schriftart);
            gc.fillText(spiel.getHeimString(), xObenLinks + 10, yObenLinks + 15);
        }

        if (spiel.getGast()==spiel.getSieger()&&spiel.getGast()!=null) {
            gc.setFont(fetteschriftart);
            gc.fillText(spiel.getGastString(), xObenLinks + 10, yObenLinks + 45);
        }
        else{
            gc.setFont(schriftart);
            gc.fillText(spiel.getGastString(), xObenLinks + 10, yObenLinks + 45);
        }

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

        //gc.fillText(ergebnis.getErgebnisArray().toString(),xWendePunkt,yStart);
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






    /*public class ScrollTransformer extends Transformer {
        private static final double MIN_SCALE = .1;
        private static final double MAX_SCALE = 5;

        private double currentScale = 1;


        @Override
        public void transform(Source xmlSource, Result outputTarget) throws TransformerException {

        }

        @Override
        public void setParameter(String name, Object value) {

        }

        @Override
        public Object getParameter(String name) {
            return null;
        }

        @Override
        public void clearParameters() {

        }

        @Override
        public void setURIResolver(URIResolver resolver) {

        }

        @Override
        public URIResolver getURIResolver() {
            return null;
        }

        @Override
        public void setOutputProperties(Properties oformat) {

        }

        @Override
        public Properties getOutputProperties() {
            return null;
        }

        @Override
        public void setOutputProperty(String name, String value) throws IllegalArgumentException {

        }

        @Override
        public String getOutputProperty(String name) throws IllegalArgumentException {
            return null;
        }

        @Override
        public void setErrorListener(ErrorListener listener) throws IllegalArgumentException {

        }

        @Override
        public ErrorListener getErrorListener() {
            return null;
        }*/
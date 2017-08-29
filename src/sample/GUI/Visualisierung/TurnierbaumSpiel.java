package sample.GUI.Visualisierung;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Spiel;

public class TurnierbaumSpiel {
    private int yObenLinks;
    private int xObenLinks;
    private int breite;
    private int hoehe;
    private Spiel spiel;
    private Spiel folgeSpiel;

    public TurnierbaumSpiel(int xObenLinks, int yObenLinks, int breite, int hoehe, Spiel spiel) {
        this.yObenLinks = yObenLinks;
        this.xObenLinks = xObenLinks;
        this.breite = breite;
        this.hoehe = hoehe;
        this.spiel = spiel;
    }

    public void setFolgeSpiel(Spiel folgeSpiel) {
        this.folgeSpiel = folgeSpiel;
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
    }
}

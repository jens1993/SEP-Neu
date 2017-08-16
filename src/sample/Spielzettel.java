package sample;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.ArrayList;

import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

public class Spielzettel {
    private Spiel spiel;
    private ArrayList<Spiel> spiele;
    private boolean sechs = false;

    public Spielzettel(Spiel spiel) {
        this.spiel = spiel;
        this.sechs=false;
    }
    public Spielzettel(ArrayList<Spiel> spiele) {
        this.spiele = spiele;
        this.sechs=false;
    }

    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if(!sechs) { //Drucke einen SPielzettel
            // We have only one page, and 'page'
            // is zero-based
            if (page > 0) {
                return NO_SUCH_PAGE;
            }


            Graphics2D g2 = (Graphics2D) g;

            g2.translate(pf.getImageableX(), pf.getImageableY());

            //g2.setStroke(new BasicStroke(12));
            Rectangle2D.Double border = new Rectangle2D.Double(0, 0, pf.getImageableWidth(), pf.getImageableHeight());
            g2.drawLine((int) pf.getImageableWidth() / 2, 0, (int) pf.getImageableWidth() / 2, (int) pf.getImageableHeight());
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font[] fonts = ge.getAllFonts();
            for (int i = 0; i < fonts.length; i++) { //gibt alle möglichen Schriftarten aus!
                System.out.println(fonts[i].getFontName());
            }
            g2.draw(border);
            Font font = new Font("Calibri Bold", Font.PLAIN, 20);
            // Now we perform our rendering
            g2.setFont(font);
            g2.drawString("Ausrichter:", 260, 20);
            g2.draw(new Rectangle(258, 23, 200, 1));

            g2.drawString("Turnier:", 22, 60);
            g2.drawString("Turnier(variabel)", 157, 60);
            g2.drawString("Disziplin:", 22, 85);
            g2.drawString("Disziplin(variabel)", 157, 85);
            g2.drawString("Feldnummer:", 22, 110);
            g2.drawString("FeldNr.(variabel)", 157, 110);


            g2.drawString("Datum:", 350, 60);
            g2.drawString("Datum(variabel)", 495, 60);
            g2.drawString("Aufrufzeit:", 350, 85);
            g2.drawString(spiel.getAufrufZeit().toString(), 495, 85);
            g2.drawString("Schiedsrichter:", 350, 110);
            g2.drawString("SR(variabel)", 495, 110);

            g2.draw(new Rectangle(0, 120, 800, 2));

            g2.drawString("Spieler Heim:", 25, 160);
            g2.draw(new Rectangle(22, 137, 215, 30));
            g2.drawString("Spieler Gast:", 525, 160);
            g2.draw(new Rectangle(522, 137, 215, 30));
            g2.drawString(spiel.getHeim().getSpielerEins().toString(), 155, 160);
            g2.drawString(spiel.getGast().getSpielerEins().toString(), 655, 160);
            if(!spiel.getHeim().isEinzel()){
                //Hier doppelpartner für heim eintragen
            }
            g2.drawString("gegen", 350, 160);

            g2.drawString("Satz 1", 150, 250);
            g2.drawString(":", 370, 250);
            g2.draw(new Rectangle(300, 223, 50, 40));
            g2.draw(new Rectangle(395, 223, 50, 40));
            g2.drawString("Satz 2", 150, 350);
            g2.drawString(":", 370, 350);
            g2.draw(new Rectangle(300, 323, 50, 40));
            g2.draw(new Rectangle(395, 323, 50, 40));
            g2.drawString("Satz 3", 150, 450);
            g2.drawString(":", 370, 450);
            g2.draw(new Rectangle(300, 423, 50, 40));
            g2.draw(new Rectangle(395, 423, 50, 40));

            g2.draw(new Rectangle(0, 500, 800, 2));

            g2.drawString("Sieger:", 50, 530);

            g2.drawString("Gesamtspielstand:", 450, 530);
            // tell the caller that this page is part
            // of the printed document
            return PAGE_EXISTS;
        }
        else{
            if (page > 0) {
                return NO_SUCH_PAGE;
            }

            // User (0,0) is typically outside the
            // imageable area, so we must translate
            // by the X and Y values in the PageFormat
            // to avoid clipping.
            Graphics2D g2 = (Graphics2D)g;
        /*pf.setOrientation(PageFormat.REVERSE_LANDSCAPE);
        Paper paper = new Paper();
        paper.setSize(29.7,21.0);
        paper.setImageableArea(2,2,25.7,17.0);
        pf.setPaper(paper);*/
            g2.translate(pf.getImageableX(), pf.getImageableY());

            //g2.setStroke(new BasicStroke(12));
            Rectangle2D.Double border = new Rectangle2D.Double(0, 0, pf.getImageableWidth(), pf.getImageableHeight());
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font[] fonts = ge.getAllFonts();
            for (int i = 0; i < fonts.length; i++) { //gibt alle möglichen Schriftarten aus!
                System.out.println(fonts[i].getFontName());
            }
            g2.draw(border);
            Font font=new Font("Times New Roman", Font.PLAIN, 9);
            // Now we perform our rendering
            g2.setFont(font);

            // String spieler;
            //for (int i=0;i<spielerListe.size();i++){
            //  spieler = spielerListe.get(i);

            // }
            //Print Borders
            g2.draw(new Rectangle((int) (pf.getImageableWidth()/2),0,1,(int) pf.getImageableHeight()));
            g2.draw(new Rectangle(0,237,1000,1));
            g2.draw(new Rectangle(0,474,1000,1));

            //Print Part Bordes
            g2.draw(new Rectangle(0,44,1000,0));
            g2.draw(new Rectangle(0,278,1000,0));
            g2.draw(new Rectangle(0,512,1000,0));
            /////////////////////////////////////////////////////////////

            for(int i=0;i<spiele.size();i++){
                int[] koordinatenObenLinks = new int[2];
                if(i%2==0){
                    koordinatenObenLinks[0] = 0;
                }
                else{
                    koordinatenObenLinks[0] = (int) pf.getImageableWidth()/2;
                }
                if(i<2){
                    koordinatenObenLinks[1] = 0;
                }
                else if(i<4){
                    koordinatenObenLinks[1] = (int) pf.getImageableHeight()/3;
                }
                else{
                    koordinatenObenLinks[1] = (int) (pf.getImageableHeight()/3)*2;
                }
                Spiel aktuellesSpiel = spiele.get(i);
                Spieler spielerHeimEins = aktuellesSpiel.getHeim().getSpielerEins();
                Spieler spielerGastEins = aktuellesSpiel.getGast().getSpielerEins();
                g2.drawString("Turnier:",koordinatenObenLinks[0]+10,koordinatenObenLinks[1]+17);
                g2.drawString(aktuellesSpiel.getSpielsystem().getSpielklasse().getTurnier().getName(),koordinatenObenLinks[0]+50,koordinatenObenLinks[1]+17);
                g2.drawString("Disziplin:",koordinatenObenLinks[0]+132,koordinatenObenLinks[1]+17);
                g2.drawString("Disziplin(variabel)",koordinatenObenLinks[0]+182,koordinatenObenLinks[1]+17);
                g2.drawString("Aufrufzeit:",koordinatenObenLinks[0]+132,koordinatenObenLinks[1]+32);
                g2.drawString("Aufrufzeit(variabel)",koordinatenObenLinks[0]+182,koordinatenObenLinks[1]+32);
                g2.drawString("Feldnummer:",koordinatenObenLinks[0]+10,koordinatenObenLinks[1]+32);

                g2.drawString("Spieler Heim:",koordinatenObenLinks[0]+5,koordinatenObenLinks[1]+60);
                g2.drawString("test",koordinatenObenLinks[0]+67,koordinatenObenLinks[1]+60);
                g2.draw(new Rectangle(koordinatenObenLinks[0]+4,koordinatenObenLinks[1]+50,100,12));
                g2.drawString("Spieler Gast:",koordinatenObenLinks[0]+160,koordinatenObenLinks[1]+60);
                g2.drawString("test",koordinatenObenLinks[0]+220,koordinatenObenLinks[1]+60);
                g2.draw(new Rectangle(koordinatenObenLinks[0]+159,koordinatenObenLinks[1]+50,100,12));
                g2.drawString("gegen",koordinatenObenLinks[0]+120,koordinatenObenLinks[1]+60);

                g2.drawString("Satz 1",koordinatenObenLinks[0]+5,koordinatenObenLinks[1]+100);
                g2.drawString(":",koordinatenObenLinks[0]+130,koordinatenObenLinks[1]+100);
                g2.draw(new Rectangle(koordinatenObenLinks[0]+88,koordinatenObenLinks[1]+90,25,20));
                g2.draw(new Rectangle(koordinatenObenLinks[0]+150,koordinatenObenLinks[1]+90,25,20));
                g2.drawString("Satz 2",koordinatenObenLinks[0]+5,koordinatenObenLinks[1]+153);
                g2.drawString(":",koordinatenObenLinks[0]+130,koordinatenObenLinks[1]+153);
                g2.draw(new Rectangle(koordinatenObenLinks[0]+88,koordinatenObenLinks[1]+143,25,20));
                g2.draw(new Rectangle(koordinatenObenLinks[0]+150,koordinatenObenLinks[1]+143,25,20));
                g2.drawString("Satz 3",koordinatenObenLinks[0]+5,koordinatenObenLinks[1]+206);
                g2.drawString(":",koordinatenObenLinks[0]+130,koordinatenObenLinks[1]+206);
                g2.draw(new Rectangle(koordinatenObenLinks[0]+88,koordinatenObenLinks[1]+196,25,20));
                g2.draw(new Rectangle(koordinatenObenLinks[0]+150,koordinatenObenLinks[1]+196,25,20));
            }


            // tell the caller that this page is part
            // of the printed document
            return PAGE_EXISTS;
        }
    }
}

package sample;
import javafx.fxml.FXML;

import javax.swing.text.html.ListView;
import java.util.Date;
import java.util.Timer;
import java.util.Vector;

public class Spieler {
	private int iD;
	private String vName;



	private String nName;
	private String gDatum;
	private int spielerID;
	private java.util.Vector<Spielklasse[]> spielklasse;
	private java.util.Vector<int[]> ranglistenpunkte;
	private char geschlecht;
	private Verein verein;
	private float meldeGebuehren;
	private int anzahlSiege = 0;
	private int anzahlNiederlagen = 0;
	private int gewonneneSaetze = 0;
	private int verloreneSaetze = 0;
	private int erspieltePunkte = 0;
	private int zugelassenePunkte = 0;
	private Timer verfuegbar;
	private int mattenSpiele = 0;
	private int extSpielerID;
	private int aktuellesSpiel;
	public Vector<Spielklasse> unnamed_Spielklasse_ = new Vector<Spielklasse>();
	public Vector<Spiel> unnamed_Spiel_ = new Vector<Spiel>();
	public Verein unnamed_Verein_;


	//agDatum muss noch auf Date geÃ¤ndert werden, Jens , int aRanglistenpunkte


	public static void spielerHinzufuegen(String aVName, String aNName, String aGDatum, int aSpielerID, int rang_re, int rang_rd,int rang_rm, boolean rm, boolean rw) {

		
		System.out.println("Klappt");
		System.out.println("Vorname: "+aVName+""+" Nachname: "+aNName+""+" GD: "+aGDatum+""+" Spielerid: "+aSpielerID+""+" Mann: "+rm+" Frau"+rw+"Punkte"+rang_re+"-"+rang_rd+"-"+rang_rm);
		//liste_spieler.
	}
	public String toString(){
		return vName + " " + nName + ", " + gDatum;
	}
	public String getName() {
		return vName + " " + nName;
	}

	public void spielerLoeschen(int aSpielerID) {
		throw new UnsupportedOperationException();
	}

	public void spielerBearbeiten(Object aSpielerID) {
		throw new UnsupportedOperationException();
	}

	public void meldeFormularimportieren() {
		throw new UnsupportedOperationException();
	}

	public static void spielerHinzufueger() {
	}
	public static void spielerHinzufueg(String vname, String nname) {
		// TODO Auto-generated method stub
		System.out.println("Klappt");
		System.out.println("Vorname: "+vname+""+" Nachname: "+nname);
		SQLConnection testverbindung = new SQLConnection();
		boolean hallo = testverbindung.insertSpieler(vname, nname);
		if(hallo)
        {
			System.out.println("Einfügen erfolgreich!");
        }
		if(!hallo)
        {
			System.out.println("Einfügen nicht erfolgreich!");
        }
		//liste_spieler.
	}
}
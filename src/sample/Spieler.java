package sample;

import java.util.Timer;
import java.util.Vector;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Spieler {
	private String vName;
	private String nName;
	private String gDatum;
	private int spielerID;
	private boolean geschlecht;
	private int[] rPunkte = new int[3]; //[0]=Einzel-, [1]=Doppel-, [2]=Mixed-Ranglistenpunkte
	private Verein verein;
	private float meldeGebuehren;
	private String Nationalitaet = "Deutschland";
	private int anzahlSiege = 0;
	private int anzahlNiederlagen = 0;
	private int gewonneneSaetze = 0;
	private int verloreneSaetze = 0;
	private int erspieltePunkte = 0;
	private int zugelassenePunkte = 0;
	private Timer verfuegbar;
	private int mattenSpiele = 0;
	private String extSpielerID;
	private int aktuellesSpiel;
	private boolean freilos=false;

	public Spieler(String vName, String nName, int spielerID){
		this.vName = vName;
		this.nName = nName;
		this.spielerID = spielerID;
	}

	public Spieler(String vName) {
		this.vName = vName;
		this.freilos = true;
	}

	public boolean isFreilos() {
		return freilos;
	}

	public boolean deleteSpieler(Spieler spieler){
		try {
			SpielerDAOimpl dao = new SpielerDAOimpl();
			dao.delete(this);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Loeschen klappt nicht");
			return false;
		}

	}

	//agDatum muss noch auf Date geändert werden, Jens , int aRanglistenpunkte


	public static void spielerHinzufuegen(String aVName, String aNName, String aGDatum, int aSpielerID, int rang_re, int rang_rd,int rang_rm, boolean rm, boolean rw) {

		
		System.out.println("Klappt");
		System.out.println("Vorname: "+aVName+""+" Nachname: "+aNName+""+" GD: "+aGDatum+""+" Spielerid: "+aSpielerID+""+" Mann: "+rm+" Frau"+rw+"Punkte"+rang_re+"-"+rang_rd+"-"+rang_rm);
		//liste_spieler.
	}
	public String toString(){
		return vName + " " + nName + ", " + gDatum;
	}
	public String getName() {
		if (nName != null) {
			return vName + " " + nName;
		}
		else
		{
			return vName;
		}
	}

	public String getNationalitaet() {
		return Nationalitaet;
	}

	public String getgDatum() {
		return gDatum;
	}

	public int[] getrPunkte() {
		return rPunkte;
	}

	public boolean getGeschlecht() {
		return geschlecht;
	}

	public Verein getVerein() {
		return verein;
	}

	public float getMeldeGebuehren() {
		return meldeGebuehren;
	}

	public Timer getVerfuegbar() {
		return verfuegbar;
	}

	public int getMattenSpiele() {
		return mattenSpiele;
	}

	public String getExtSpielerID() {
		return extSpielerID;
	}

	public int getAktuellesSpiel() {
		return aktuellesSpiel;
	}

	public String getvName() {
		return vName;
	}
	public String getnName() {
		return nName;
	}

	public void meldeFormularimportieren() {
		throw new UnsupportedOperationException();
	}

	public int getSpielerID() {
		return spielerID;
	}
}
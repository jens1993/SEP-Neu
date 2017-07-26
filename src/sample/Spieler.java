package sample;

import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;
import java.util.Vector;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Spieler {
	private String vName;
	private String nName;
	private LocalDate gDatum;
	private int spielerID;
	private boolean geschlecht;
	private int[] rPunkte = new int[3]; //[0]=Einzel-, [1]=Doppel-, [2]=Mixed-Ranglistenpunkte
	private Verein verein;
	private float meldeGebuehren;
	private String Nationalitaet = "Deutschland";
	private LocalDate verfuegbar;
	private int mattenSpiele = 0;
	private String extSpielerID;
	private Spiel aktuellesSpiel;

	public Spieler(String vName, String nName, int spielerID){
		this.vName = vName;
		this.nName = nName;
		this.spielerID = spielerID;
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

	public String toString(){
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

	public LocalDate getgDatum() {
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

	public LocalDate getVerfuegbar() {
		return verfuegbar;
	}

	public int getMattenSpiele() {
		return mattenSpiele;
	}

	public String getExtSpielerID() {
		return extSpielerID;
	}

	public Spiel getAktuellesSpiel() {
		return aktuellesSpiel;
	}

	public String getvName() {
		return vName;
	}

	public void setGeschlecht(boolean geschlecht) {
		this.geschlecht = geschlecht;
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
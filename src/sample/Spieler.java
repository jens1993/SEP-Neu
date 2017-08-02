package sample;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.Vector;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Spieler {
	SpielerDAO spielerDAO = new SpielerDAOimpl();
	private String vName;
	private String nName;
	private LocalDate gDatum;
	private int spielerID;
	private boolean geschlecht;
	private int[] rPunkte = new int[3]; //[0]=Einzel-, [1]=Doppel-, [2]=Mixed-Ranglistenpunkte
	private Verein verein;
	private float meldeGebuehren;
	private String Nationalitaet = "Deutschland";
	private LocalDate verfuegbar = LocalDate.now();
	private int mattenSpiele = 0;
	private String extSpielerID;
	private Spiel aktuellesSpiel;

	public Spieler(String vName, String nName, int spielerID){
		this.vName = vName;
		this.nName = nName;
		this.spielerID = spielerID;
		spielerDAO.create(this);
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

	public void setgDatum(LocalDate gDatum) {
		this.gDatum = gDatum;
		spielerDAO.update(this);
	}

	public void setrPunkte(int[] rPunkte) {
		this.rPunkte = rPunkte;
		spielerDAO.update(this);
	}

	public void setVerein(Verein verein) {
		this.verein = verein;
		spielerDAO.update(this);
	}

	public void setMeldeGebuehren(float meldeGebuehren) {
		this.meldeGebuehren = meldeGebuehren;
		spielerDAO.update(this);
	}



	public void setNationalitaet(String nationalitaet) {
		Nationalitaet = nationalitaet;
		spielerDAO.update(this);
	}

	public void setVerfuegbar(LocalDate verfuegbar) {
		this.verfuegbar = verfuegbar;
		spielerDAO.update(this);
	}

	public void setMattenSpiele(int mattenSpiele) {
		this.mattenSpiele = mattenSpiele;
		spielerDAO.update(this);
	}

	public void setExtSpielerID(String extSpielerID) {
		this.extSpielerID = extSpielerID;
		spielerDAO.update(this);
	}

	public void setAktuellesSpiel(Spiel aktuellesSpiel) {
		this.aktuellesSpiel = aktuellesSpiel;
		spielerDAO.update(this);
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
		spielerDAO.update(this);
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
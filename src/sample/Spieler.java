package sample;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Spieler {
	SpielerDAO spielerDAO = new SpielerDAOimpl();
	private String vName;

	public void setvName(String vName) {
		this.vName = vName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}

	public void setSpielerID(int spielerID) {
		this.spielerID = spielerID;
	}

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



	public Spieler(String vName, String nName){
		this.vName = vName;
		this.nName = nName;
		spielerDAO.create(this);
	}

	public Spieler(String vName, String nName, LocalDate gDatum, boolean geschlecht, int[] rPunkte, Verein verein,String extSpielerID)
	{
		this.vName = vName;
		this.nName = nName;
		this.gDatum = gDatum;
		this.geschlecht = geschlecht;
		this.rPunkte = rPunkte;
		this.verein = verein;
		this.extSpielerID = extSpielerID;
		spielerDAO.create(this);

	}
	//Spieler erstellen (Prüfung)
	public Spieler(String vName, String nName, LocalDate gDatum, boolean geschlecht, int[] rPunkte, Verein verein,String extSpielerID, String s)
	{
		this.vName = vName;
		this.nName = nName;
		this.gDatum = gDatum;
		this.geschlecht = geschlecht;
		this.rPunkte = rPunkte;
		this.verein = verein;
		this.extSpielerID = extSpielerID;

	}
	//EINLESEN
	public Spieler(String vName, String nName, LocalDate gDatum, int spielerID, boolean geschlecht, int[] rPunkte, Verein verein, float meldeGebuehren, String nationalitaet, LocalDate verfuegbar, int mattenSpiele, String extSpielerID) {
		this.vName = vName;
		this.nName = nName;
		this.gDatum = gDatum;
		this.spielerID = spielerID;
		this.geschlecht = geschlecht;
		this.rPunkte = rPunkte;
		this.verein = verein;
		this.meldeGebuehren = meldeGebuehren;
		Nationalitaet = nationalitaet;
		this.verfuegbar = verfuegbar;
		this.mattenSpiele = mattenSpiele;
		this.extSpielerID = extSpielerID;
		this.aktuellesSpiel = aktuellesSpiel;
	}
	public Spieler(){

	}
	public void einzelPunkteUpdate(int einzelPunkte){
		rPunkte[0] = einzelPunkte;
	}
	public void doppelPunkteUpdate(int doppelPunkte){
		rPunkte[1] = doppelPunkte;
	}
	public void mixedPunkteUpdate(int mixedPunkte){
		rPunkte[2] = mixedPunkte;
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
		//spielerDAO.update(this);
	}

	public void setrPunkte(int[] rPunkte) {
		this.rPunkte = rPunkte;
		//spielerDAO.update(this);
	}

	public void setVerein(Verein verein) {
		this.verein = verein;
		//spielerDAO.update(this);
	}

	public void setMeldeGebuehren(float meldeGebuehren) {
		this.meldeGebuehren = meldeGebuehren;
		//spielerDAO.update(this);
	}

	public SpielerDAO getSpielerDAO() {
		return spielerDAO;
	}

	public void setNationalitaet(String nationalitaet) {
		Nationalitaet = nationalitaet;
		//spielerDAO.update(this);
	}

	public void setVerfuegbar(LocalDate verfuegbar) {
		this.verfuegbar = verfuegbar;
		//spielerDAO.update(this);
	}

	public void setMattenSpiele(int mattenSpiele) {
		this.mattenSpiele = mattenSpiele;
		//spielerDAO.update(this);
	}

	public void setExtSpielerID(String extSpielerID) {
		this.extSpielerID = extSpielerID;
		//spielerDAO.update(this);
	}

	public void setAktuellesSpiel(Spiel aktuellesSpiel) {
		this.aktuellesSpiel = aktuellesSpiel;
		//spielerDAO.update(this);
	}


	public String getNationalitaet() {
		return Nationalitaet;
	}

	public LocalDate getGDatum() {
		return gDatum;
	}

	public int[] getrPunkte() {
		return rPunkte;
	}

	public boolean getGeschlecht() {
		return geschlecht;
	}
	public String getSGeschlecht() {
		if(geschlecht){
			return "m";
		}
		else {
			return "w";
		}
	}
	public ImageView getIGeschlecht() {
		Image imgmale = new Image("sample/Images/icon/user_male.png",24,24,true,true);
		Image imgfemale = new Image("sample/Images/icon/user_female.png",24,24,true,true);
		ImageView imageView = new ImageView(imgmale);
		ImageView imageView2 = new ImageView(imgfemale);
		if(geschlecht){
			return imageView;
		}
		else {
			return imageView2;
		}
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

	public String getVName() {
		return vName;
	}

	public void setGeschlecht(boolean geschlecht) {
		this.geschlecht = geschlecht;
		//spielerDAO.update(this);
	}

	public String getNName() {
		return nName;
	}


	public void meldeFormularimportieren() {
		throw new UnsupportedOperationException();
	}

	public int getSpielerID() {
		return spielerID;
	}
}
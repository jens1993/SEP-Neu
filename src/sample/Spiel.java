package sample;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalTime;

import sample.DAO.*;
import sample.Spielsysteme.*;

public class Spiel {
	//ErgebnisDAO ergebnisDAO = new ErgebnisDAOimpl();
	SpielDAO spielDAO = new SpielDAOimpl();
	private int spielID;
	private Team heim;
	private Team gast;
	private Ergebnis ergebnis;
	private LocalTime aufrufZeit;
	private Spieler schiedsrichter;
	private Spielsystem spielsystem;
	private Turnier turnier;


	private Feld feld;
	private int status = 0; //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
	private int systemSpielID;
	private int setzPlatzHeim;
	private int setzPlatzGast;
	private int zeitplanNummer;
	private int rundenZeitplanNummer;


	public void setFeld(Feld feld) {
		this.feld = feld;
		feld.setAktivesSpiel(this);
		spielDAO.update(this);
	}


	public Team getHeim() {
		return heim;
	}

	@Override
	public String toString() {

		return (heim.toString()  +" --- "+ gast.toString()) ;
	}

	public String getHeimString() {
		if(heim != null){
			return heim.toString();
		}
		else{
			return "ausstehend";
		}

	}
	public String getGastString() {
		if(gast!=null){
			return gast.toString();
		}
		else{
			return "ausstehend";
		}
	}

	public Team getGast() {
		return gast;
	}

	public boolean isGast() {


		return false;
	}
	public void setHeim(Team heim) {
		this.heim = heim;
		if(this.gast != null && !(this.systemSpielID<20000000)){
			this.status = 1;

		}
		//spielDAO.update(this);
	}

	public void setGast(Team gast) {
		this.gast = gast;
		if(this.heim != null && !(this.systemSpielID<20000000)){
			this.status = 1;
		/*	if (this.heim.isFreilos()){
				this.setErgebnis(new Ergebnis(0,21,0,21));
			}
			else if(this.gast.isFreilos()){
				this.setErgebnis(new Ergebnis(21,0,21,0));
			}*/
		}
		//spielDAO.update(this);
	}

	public SpielDAO getSpielDAO() {
		return spielDAO;
	}

	public Spieler getSchiedsrichter() {
		return schiedsrichter;
	}

	public LocalTime getAufrufZeit() {
		return aufrufZeit;
	}

	public Feld getFeld() {
		return feld;
	}

	public int getStatus() {
		return status;
	}

	public int getSystemSpielID() {
		return systemSpielID;
	}

	public int getSpielID() {
		return spielID;
	}

	public Spiel(Team heim, Team gast, int systemSpielID, Spielsystem spielsystem) {
		this.heim = heim;
		this.gast = gast;
		this.spielsystem=spielsystem;
		this.systemSpielID = systemSpielID;
		this.turnier = this.spielsystem.getSpielklasse().getTurnier();
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		turnier.getObs_ausstehendeSpiele().add(this);
		auswahlklasse.getAktuelleTurnierAuswahl().addobsAusstehendeSpiele(this);
		//spielDAO.create(this);
		this.status = 1;
		if(heim.isFreilos()){
			setErgebnis(new Ergebnis(21,0,21,0));
		}
	}


	public Spiel(int systemSpielID, Spielsystem spielsystem) { //Constructor für Extrarunden (Gruppe mit Endrunde)
		this.spielsystem = spielsystem;
		this.systemSpielID = systemSpielID;
		this.turnier = this.spielsystem.getSpielklasse().getTurnier();
		//spielDAO.create(this);
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		this.spielsystem.getSpielklasse().getTurnier().getObs_ausstehendeSpiele().add(this);

	}

	public Spiel(int systemSpielID, int setzPlatzHeim, int setzPlatzGast, Spielsystem spielsystem) {
		this.systemSpielID = systemSpielID; //Constructor für SpielTree in KO-System
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzGast = setzPlatzGast;
		this.spielsystem=spielsystem;
		//this.spielDAO.create(this);
		this.turnier = this.spielsystem.getSpielklasse().getTurnier();
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		this.spielsystem.getSpielklasse().getTurnier().getObs_ausstehendeSpiele().add(this);

	}


	public Spiel(int spielID, Team heim, Team gast, LocalTime aufrufZeit, Spieler schiedsrichter, int status, int systemSpielID, Feld feld) {
		this.spielID = spielID;
		this.heim = heim;						//Constructor für einlesen. Anschließend MUSS Spielsystem gesettet werden!)
		this.gast = gast;
		this.aufrufZeit = aufrufZeit;
		this.schiedsrichter = schiedsrichter;
		this.status = status;
		this.systemSpielID = systemSpielID;
		this.feld = feld;
	}

	public Team getSieger(){
		if(ergebnis!=null){
			return ergebnis.getSieger(this);
		}
		else{
			return null;
		}
	}

	public void setSpielsystem(Spielsystem spielsystem) {
		this.spielsystem = spielsystem;
		this.spielsystem.getSpielklasse().getTurnier().getSpiele().put(spielID,this);
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
	}

	public int getZeitplanNummer() {
		return zeitplanNummer;
	}

	public void setZeitplanNummer(int zeitplanNummer) {
		this.zeitplanNummer = zeitplanNummer;
	}

	public int getRundenZeitplanNummer() {
		return rundenZeitplanNummer;
	}

	public void setRundenZeitplanNummer(int rundenZeitplanNummer) {
		this.rundenZeitplanNummer = rundenZeitplanNummer;
	}

	public Spielsystem getSpielsystem() {
		return spielsystem;
	}

	public void setSchiedsrichter(Spieler schiedsrichter) {
		this.schiedsrichter = schiedsrichter;
	}

	public void spielAufrufen(Feld feld) {
		this.feld = feld;
		this.aufrufZeit = LocalTime.now();
		this.status = 2;
	}
	public String getSpielNummer(){
		Integer nr = zeitplanNummer;
		return nr.toString();
	}
	public String getRundenName(){
		if (systemSpielID<20000000){ //Gruppe
			int rundenNummer = (systemSpielID-10000000)/1000;
			return "Runde "+(rundenNummer + 1);
		}
		else if(systemSpielID<30000000){ //Gruppe mit Endrunde

		}
		else if(systemSpielID<40000000){ //K.O. system
			int rundenNummer = (systemSpielID-30000000)/1000;
			if(rundenNummer==0){
				return "Finale";
			}
			else if(rundenNummer==1){
				return "Halbfinale";
			}
			else if(rundenNummer==2){
				return "Viertelfinale";
			}
			else if(rundenNummer==3){
				return "Achtelfinale";
			}
			else{
				rundenNummer = spielsystem.getAnzahlRunden() - rundenNummer;
				return "Runde "+rundenNummer;
			}
		}
		else if(systemSpielID<50000000){ //K.O. mit TrostRunde

		}
		else{ // Schweizer System
			int rundenNummer = (systemSpielID-10000000)/1000;
			return "Runde "+rundenNummer + 1;
		}
		return "";
	}
	public void setStatus(int status) {
		this.status = status;
		spielDAO.update(this);
	}

	public Ergebnis getErgebnis() {
		return ergebnis;
	}

	public String getErgebnisString() {
		if (ergebnis!=null){
			return ergebnis.toString();
		}
		else {
			return "";
		}
	}
	public String getSpielklasseString() {
		if (spielsystem.getSpielklasse()!=null){
			return spielsystem.getSpielklasse().getDisziplin()+"-"+spielsystem.getSpielklasse().getNiveau();
		}
		else {
			return "";
		}

	}
	public String getFeldNr() {
		if (feld!=null){
			return feld.toString();
		}
		else{
			return "";
		}
	}
	public void setErgebnis(Ergebnis ergebnis) {
		this.ergebnis = ergebnis;
		statistikAktualisieren();
		status=3;
		this.spielsystem.beendeMatch(this);
		spielDAO.update(this);
		heim.getTeamDAO().update(heim);
		gast.getTeamDAO().update(gast);
		ergebnis.getErgebnisDAO().create(this);
		this.getSpielsystem().getSpielklasse().getTurnier().getObs_aktiveSpiele().remove(this);
		this.getSpielsystem().getSpielklasse().getTurnier().getObs_gespielteSpiele().add(this);
		if (this.feld != null){
			this.feld.spielBeenden();
		}

	}

	public void setErgebnis(Ergebnis ergebnis, String einlesen) {
		this.ergebnis = ergebnis;
		statistikAktualisieren();
		status=3;
		this.spielsystem.beendeMatch(this,einlesen);
	}

	private void statistikAktualisieren() {
		int satzpunkteHeim = 0;
		int satzpunkteGast = 0;
		heim.addBisherigenGegner(gast);
		gast.addBisherigenGegner(heim);
		getSieger().addGewonnenesSpiel();
		int i=0;
		while (i<ergebnis.getErgebnisArray().length/2){
			satzpunkteHeim = ergebnis.getErgebnisArray()[i*2];
			satzpunkteGast = ergebnis.getErgebnisArray()[i*2+1];
			heim.addGespieltePunkte(satzpunkteHeim,satzpunkteGast);
			gast.addGespieltePunkte(satzpunkteGast,satzpunkteHeim);
			if (satzpunkteHeim>satzpunkteGast) {
				heim.addGewonnenenSatz();
				gast.addVerlorenenSatz();
			}
			else{
				gast.addGewonnenenSatz();
				heim.addVerlorenenSatz();
			}
			i++;
		}
	}

	private void punkteHinzufuegen(){

	}

	public void setSpielID(int spielID) {
		this.spielID = spielID;
		this.turnier.getSpiele().put(spielID,this);
	}

	public int getSetzPlatzHeim() {
		return setzPlatzHeim;
	}

	public int getSetzPlatzGast() {
		return setzPlatzGast;
	}

	public void spielzettelDrucken() {

		Spielzettel test = new Spielzettel(this);
        /*//*JFrame window = new JFrame();
		window.setSize(800,600);
		window.setTitle("Spielzettel");
		window.setVisible(true);
		DrawingComponent drawingComponent = new DrawingComponent();
		window.add(test);*/

		PrinterJob job = PrinterJob.getPrinterJob();
		//Book book = new Book();
		PageFormat querFormat = new PageFormat();
		Paper paper = querFormat.getPaper();
		//Remove borders from the paper
		paper.setImageableArea(45, 45, querFormat.getPaper().getWidth()-90, querFormat.getPaper().getHeight()-90);
		querFormat.setPaper(paper);
		querFormat.setOrientation(PageFormat.LANDSCAPE);
		//book.append(test,querFormat);
		job.setPrintable(test,querFormat);
		try {
			job.print();
		}
		catch (PrinterException e)
		{
			System.out.println("Drucken fehlgeschlagen");
		}
		System.exit(0);



		/*sechsSpielzettel test = new sechsSpielzettel(spieler);
        /*JFrame window = new JFrame();
        window.setSize(800,600);
        window.setTitle("Spielzettel");
        window.setVisible(true);
        DrawingComponent drawingComponent = new DrawingComponent();
        window.add(test);

		PrinterJob job = PrinterJob.getPrinterJob();
		//Book book = new Book();
		PageFormat querFormat = new PageFormat();
		Paper paper = querFormat.getPaper();
		//Remove borders from the paper
		paper.setImageableArea(45, 45, querFormat.getPaper().getWidth()-90, querFormat.getPaper().getHeight()-90);
		querFormat.setPaper(paper);
		querFormat.setOrientation(PageFormat.PORTRAIT);
		//book.append(test,querFormat);
		job.setPrintable(test,querFormat);
		job.printDialog();
		try {
			job.print();
		}
		catch (PrinterException e)
		{
			System.out.println("Drucken fehlgeschlagen");
		}
		System.exit(0);*/

	}

}
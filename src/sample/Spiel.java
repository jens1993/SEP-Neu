package sample;

import java.time.LocalDate;

import sample.DAO.*;
import sample.Spielsysteme.*;

public class Spiel {
	//ErgebnisDAO ergebnisDAO = new ErgebnisDAOimpl();
	SpielDAO spielDAO = new SpielDAOimpl();
	private int spielID;
	private Team heim;
	private Team gast;
	private Ergebnis ergebnis;
	private LocalDate aufrufZeit;
	private Spieler schiedsrichter;
	private Spielsystem spielsystem;
	private Feld feld;
	private int status = 0; //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
	private int systemSpielID;
	private int setzPlatzHeim;
	private int setzPlatzGast;
	private int zeitplanNummer;

	public Team getHeim() {
		return heim;
	}
	public String getHeimString() {
		return heim.toString();
	}
	public String getGastString() {
		return gast.toString();
	}

	public Team getGast() {
		return gast;
	}

	public void setHeim(Team heim) {
		this.heim = heim;
		if(this.gast != null){
			this.status = 1;
		}
		spielDAO.update(this);
	}

	public void setGast(Team gast) {
		this.gast = gast;
		if(this.heim != null){
			this.status = 1;
		}
		spielDAO.update(this);
	}

	public Spieler getSchiedsrichter() {
		return schiedsrichter;
	}

	public LocalDate getAufrufZeit() {
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
		spielID = spielsystem.getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielsystem.getSpielklasse().getTurnier().getSpiele().put(spielID,this);
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		this.spielsystem.getSpielklasse().getTurnier().getAusstehendeSpiele().add(this);

		spielDAO.create(this);
		this.status = 1;
	}

/*	public Spiel(Spielklasse spielklasse, int systemSpielID) {
		this.spielklasse = spielklasse;
		this.systemSpielID = systemSpielID;
		spielID = getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielklasse.getTurnier().getSpiele().put(spielID,this);
		this.spielklasse.getSpiele().put(systemSpielID,this);
		spielDAO.create(this);
	}*/

	public Spiel(int systemSpielID, Spielsystem spielsystem) { //Constructor für Extrarunden (Gruppe mit Endrunde)
		this.spielsystem = spielsystem;
		this.systemSpielID = systemSpielID;
		spielID = spielsystem.getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielsystem.getSpielklasse().getTurnier().getSpiele().put(spielID,this);
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		spielDAO.create(this);
	}

	public Spiel(int systemSpielID, int setzPlatzHeim, int setzPlatzGast, Spielsystem spielsystem) {
		this.systemSpielID = systemSpielID; //Constructor für SpielTree in KO-System
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzGast = setzPlatzGast;
		this.spielsystem=spielsystem;
		spielID = this.spielsystem.getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielsystem.getSpielklasse().getTurnier().getSpiele().put(spielID,this);
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		spielDAO.create(this);
	}


	public Spiel(int spielID, Team heim, Team gast, LocalDate aufrufZeit, Spieler schiedsrichter, int status, int systemSpielID, Feld feld) {
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

	public Spielsystem getSpielsystem() {
		return spielsystem;
	}

	public void setSchiedsrichter(Spieler schiedsrichter) {
		this.schiedsrichter = schiedsrichter;
	}

	public void spielAufrufen(Feld feld) {
		this.feld = feld;
		this.aufrufZeit = LocalDate.now();
		this.status = 2;
	}

	public void setStatus(int status) {
		this.status = status;
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
		this.getSpielsystem().getSpielklasse().getTurnier().getAktiveSpiele().remove(this);
		this.getSpielsystem().getSpielklasse().getTurnier().getGespielteSpiele().add(this);
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

	public int getSetzPlatzHeim() {
		return setzPlatzHeim;
	}

	public int getSetzPlatzGast() {
		return setzPlatzGast;
	}

	public void spielzettelDrucken() {
		String aufrufzeit = this.aufrufZeit.toString();
		this.heim.toString();
	}

}
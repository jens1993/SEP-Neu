package sample;

import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Spiel {
	ErgebnisDAO ergebnisDAO = new ErgebnisDAOimpl();
	SpielDAO spielDAO = new SpielDAOimpl();
	private int spielID;
	private Team heim;
	private Team gast;
	private Ergebnis ergebnis;
	private LocalDate aufrufZeit;
	private Spieler schiedsrichter;
	private Feld feld;
	private int status = 0; //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
	private Spielklasse spielklasse;
	private int systemSpielID;
	private int setzPlatzHeim;
	private int setzPlatzGast;

	public Team getHeim() {
		return heim;
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

	public Spielklasse getSpielklasse() {
		return spielklasse;
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

	public Spiel(Team heim, Team gast, Spielklasse spielklasse, int systemSpielID) {
		this.heim = heim;
		this.gast = gast;
		this.spielklasse = spielklasse;
		this.systemSpielID = systemSpielID;
		spielID = getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielklasse.getTurnier().getSpiele().put(spielID,this);
		this.spielklasse.getSpiele().put(systemSpielID,this);
		spielDAO.create(this);
	}

	/*public Spiel(Team heim, Team auswaerts, Spielklasse spielklasse) {
		this.heim = heim;
		this.gast = auswaerts;
		this.spielklasse = spielklasse;
		this.status = 1;
		spielID = getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielklasse.getTurnier().getSpiele().put(spielID,this);
		spielDAO.create(this);
	}
*/

	public Spiel(Spielklasse spielklasse, int systemSpielID) {
		this.spielklasse = spielklasse;
		this.systemSpielID = systemSpielID;
		spielID = getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielklasse.getTurnier().getSpiele().put(spielID,this);
		this.spielklasse.getSpiele().put(systemSpielID,this);
		spielDAO.create(this);
	}

	public Spiel(int systemSpielID, int setzPlatzHeim, int setzPlatzGast, Spielklasse spielklasse) {
		this.systemSpielID = systemSpielID; //Constructor für SpielTree in KO-System
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzGast = setzPlatzGast;
		this.spielklasse = spielklasse;
		spielID = getSpielklasse().getTurnier().getSpiele().size()+1;
		this.spielklasse.getTurnier().getSpiele().put(spielID,this);
		this.spielklasse.getSpiele().put(systemSpielID,this);
		spielDAO.create(this);
	}
	public Team getSieger(){
		if(ergebnis!=null){
			return ergebnis.getSieger(this);
		}
		else{
			return null;
		}
	}

	public void setAufrufZeit(LocalDate aufrufZeit) {
		this.aufrufZeit = aufrufZeit;
	}

	public void setSchiedsrichter(Spieler schiedsrichter) {
		this.schiedsrichter = schiedsrichter;
	}

	public void setFeld(Feld feld) {
		this.feld = feld;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Ergebnis getErgebnis() {
		return ergebnis;
	}

	public void setErgebnis(Ergebnis ergebnis) {
		this.ergebnis = ergebnis;
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
		status=3;
		spielklasse.getSpielsystem().beendeMatch(this);
		spielDAO.update(this);
		ergebnisDAO.create(this);
	}

	public int getSetzPlatzHeim() {
		return setzPlatzHeim;
	}

	public int getSetzPlatzGast() {
		return setzPlatzGast;
	}

	public void spielzettelDrucken(int aSpielID) {
		throw new UnsupportedOperationException();
	}

	public void ergebnisEintragen(){

	}

}
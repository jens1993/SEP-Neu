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
	}

	public void setGast(Team gast) {
		this.gast = gast;
		if(this.heim != null){
			this.status = 1;
		}
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

	public Spiel(Team heim, Team auswaerts, Spielklasse spielklasse) {
		this.heim = heim;
		this.gast = auswaerts;
		this.spielklasse = spielklasse;
		this.status = 1;
	}

	public Spiel(int systemSpielID, int setzPlatzHeim, int setzPlatzGast, Spielklasse spielklasse) {
		this.systemSpielID = systemSpielID; //Constructor für SpielTree in KO-System
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzGast = setzPlatzGast;
	}
	public Team getSieger(){
		if(ergebnis!=null){
			return ergebnis.getSieger(this);
		}
		else{
			return null;
		}
	}

	public Ergebnis getErgebnis() {
		return ergebnis;
	}

	public void setErgebnis(Ergebnis ergebnis) {
		this.ergebnis = ergebnis;
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
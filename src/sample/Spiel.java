package sample;

import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Spiel {
	private int spielID;
	private Spieler heim;
	private Spieler gast;
	private Ergebnis ergebnis;
	private Date aufrufZeit;
	private Spieler schiedsrichter;
	private Feld feld;
	private int status = 0; //0 = ausstehend, 1=aktiv, 2=gespielt
	private Spielklasse spielklasse;
	private int systemSpielID;
	private int setzPlatzHeim;
	private int setzPlatzGast;

	public Spieler getHeim() {
		return heim;
	}

	public Spieler getGast() {
		return gast;
	}

	public void setHeim(Spieler heim) {
		this.heim = heim;
	}

	public void setGast(Spieler gast) {
		this.gast = gast;
	}

	public Spielklasse getSpielklasse() {
		return spielklasse;
	}

	public Spieler getSchiedsrichter() {
		return schiedsrichter;
	}

	public Date getAufrufZeit() {
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

	public Spiel(Spieler heim, Spieler auswaerts, Spielklasse spielklasse) {
		this.heim = heim;
		this.gast = auswaerts;
		this.spielklasse = spielklasse;
		System.out.println("Spiel erstellt: "+heim.getName()+" gegen "+gast.getName());
	}

	public Spiel(int systemSpielID, int setzPlatzHeim, int setzPlatzGast, Spielklasse spielklasse) {
		this.systemSpielID = systemSpielID;
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzGast = setzPlatzGast;
		System.out.println("Spieler "+setzPlatzHeim+" gegen Spieler "+setzPlatzGast);
	}
	public Spieler getSieger(){
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

	public void spielErstellen(String heim, String auswaerts) {
		/*for (int i=0; i<heim.length && i<auswaerts.length; i++){
			System.out.println(heim[i].getName());
		}
		*/
		System.out.println(heim+" gegen "+auswaerts);

	}
	public void ergebnisEintragen(){

	}

}
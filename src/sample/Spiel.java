package sample;

import java.util.Date;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Spiel {
	private int spielID;
	private Spieler heim;
	private Spieler gast;
	private int[] ergebnis;
	private Date aufrufZeit;
	private Spieler schiedsrichter;
	private Feld feld;
	private Spielstatus status;
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

	public void setStatus(Spielstatus status) {
		this.status = status;
	}

	public Spielklasse getSpielklasse() {
		return spielklasse;
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

	public int getSetzPlatzHeim() {
		return setzPlatzHeim;
	}

	public int getSetzPlatzGast() {
		return setzPlatzGast;
	}

	public void spielzettelDrucken(int aSpielID) {
		throw new UnsupportedOperationException();
	}

	public void ergebnisEingeben(String[] aErgebnis) {
		throw new UnsupportedOperationException();
	}

	public void spielErstellen(String heim, String auswaerts) {
		/*for (int i=0; i<heim.length && i<auswaerts.length; i++){
			System.out.println(heim[i].getName());
		}
		*/
		System.out.println(heim+" gegen "+auswaerts);

	}
	Spiel(int iD, String heim, String auswaerts){
		this.spielID = spielID;
		System.out.println("Spiel erstellt: "+heim+" gegen "+ auswaerts);
	}
}
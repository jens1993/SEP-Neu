package sample;

import java.util.Date;

public class Spiel {
	private int spielID;
	private Spieler heim;
	private Spieler auswaerts;
	private int[] ergebnis;
	private Date aufrufZeit;
	private Spieler schiedsrichter;
	private Feld feld;
	private Spielstatus status;
	private Spielklasse spielklasse;

	public Spieler getHeim() {
		return heim;
	}

	public Spieler getAuswaerts() {
		return auswaerts;
	}

	public void setHeim(Spieler heim) {
		this.heim = heim;
	}

	public void setAuswaerts(Spieler auswaerts) {
		this.auswaerts = auswaerts;
	}

	public Spielklasse getSpielklasse() {
		return spielklasse;
	}

	public int getSpielID() {
		return spielID;
	}

	public Spiel(int spielID, Spieler heim, Spieler auswaerts, Spielklasse spielklasse) {
		this.spielID = spielID;
		this.heim = heim;
		this.auswaerts = auswaerts;
		this.spielklasse = spielklasse;
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
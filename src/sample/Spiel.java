package sample;

import java.util.Date;

public class Spiel {
	private int iD;
	private int spielID;
	private Spieler[] heim;
	private Spieler[] auswaerts;
	private String[] ergebnis;
	private Date aufrufZeit;
	private Spieler schiedsrichter;
	private Feld feld;
	private Spielstatus status;

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
		System.out.println(heim+" gegen "+auswaerts);
	}
}
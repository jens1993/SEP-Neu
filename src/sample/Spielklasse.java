package sample;
import java.util.Vector;

public class Spielklasse {
	private int spielklasseID;
	private Disziplin disziplin;
	private Niveau niveau;
	private int setzliste;
	private int anzahlTeilnehmerProTeam;
	private Spielsystem spielsystem;
	private float meldeKosten;
	private boolean aktiv;

	public Spielklasse(int spielklasseID) {
		this.spielklasseID = spielklasseID;
	}

	public int getSpielklasseID() {
		return spielklasseID;
	}

	public float getMeldeKosten() {
		return meldeKosten;
	}

	public void spielerEintragen(Object aSpieler) {
		throw new UnsupportedOperationException();
	}

	public void spielerEntfernen(Object aSpieler) {
		throw new UnsupportedOperationException();
	}

	public void spielklasseErstellen() {
		throw new UnsupportedOperationException();
	}

	public void spielklasseLoeschen() {
		throw new UnsupportedOperationException();
	}

	public void turnierBaumErstellen() {
		throw new UnsupportedOperationException();
	}

	public void turnierBaumDrucken() {
		throw new UnsupportedOperationException();
	}
}
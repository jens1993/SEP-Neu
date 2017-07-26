package sample.Spielsysteme;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;
import java.util.List;

public abstract class Spielsystem {
	private String name;
	private int anzahlTeilnehmer;
	private int anzahlRunden;
	private int anzahlSpiele;
	private int spielsystemID;
	private static Zaehlweise zaehlweise;
	private Spielklasse spielklasse;

	public Spielklasse getSpielklasse() {
		return spielklasse;
	}

	public void setSpielklasse(Spielklasse spielklasse) {
		this.spielklasse = spielklasse;
	}

	public int getAnzahlRunden() {
		return anzahlRunden;
	}
	public void setAnzahlRunden(int anzahlRunden) {
		this.anzahlRunden = anzahlRunden;
	}
	public abstract boolean beendeMatch(Spiel spiel);
}
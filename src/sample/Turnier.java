package sample;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Timer;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Turnier {
	private int matchDauer;
	private int gesamtSpiele;
	private LocalDate datum = LocalDate.now();
	private String name;
	private int zaehlweise = 0; // 0=bis21 1=bis 11 mit Verlängerung 2=bis 11 ohne Verlängerung
	private int spielerPausenZeit = 10;
	private int turnierid;
	private Dictionary<Integer, Spielklasse> spielklassen = new Hashtable<Integer,Spielklasse>();
	private Dictionary<Integer, Feld> felder = new Hashtable<Integer,Feld>();
	private Dictionary<Integer, Verein> vereine = new Hashtable<Integer,Verein>();
	private Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();
	private Dictionary<Integer, Team> teams = new Hashtable<Integer,Team>();
	private Dictionary<Integer, Spiel> spiele = new Hashtable<Integer,Spiel>();


	public Turnier(String name, int turnierid, LocalDate datum) {
		this.datum = datum;
		this.name = name;
		this.turnierid = turnierid;
	}

	public int getGesamtSpiele() {
		return gesamtSpiele;
	}

	public int getSpielerPausenZeit() {
		return spielerPausenZeit;
	}

	public int getMatchDauer() { return matchDauer; }

	public void setGesamtSpiele(int gesamtSpiele) {
		this.gesamtSpiele = gesamtSpiele;
	}

	public int getZaehlweise() { return zaehlweise; }

	public LocalDate getDatum() {
		return datum;
	}

	public String getName() {
		return name;
	}

	public int getTurnierid() {
		return turnierid;
	}

	public Dictionary<Integer, Spielklasse> getSpielklassen() {
		return spielklassen;
	}

	public Dictionary<Integer, Verein> getVereine() {
		return vereine;
	}

	public void setVereine(Dictionary<Integer, Verein> vereine) {
		this.vereine = vereine;
	}

	public Dictionary<Integer, Spieler> getSpieler() {
		return spieler;
	}

	public Dictionary<Integer, Feld> getFelder() {
		return felder;
	}

	public void setFelder(Dictionary<Integer, Feld> felder) {
		this.felder = felder;
	}

	public Dictionary<Integer, Team> getTeams() {
		return teams;
	}

	public void setTeams(Dictionary<Integer, Team> team) {
		this.teams = team;
	}

	public Dictionary<Integer, Spiel> getSpiele() {
		return spiele;
	}

	public void setSpiele(Dictionary<Integer, Spiel> spiele) {
		this.spiele = spiele;
	}

	public void setSpielklassen(Dictionary<Integer, Spielklasse> spielklassen) {
		this.spielklassen = spielklassen;
	}

	public void setSpieler(Dictionary<Integer, Spieler> spieler) {
		this.spieler = spieler;
	}

	public void setMatchDauer(int matchDauer) {
		this.matchDauer = matchDauer;
	}

	public void setZaehlweise(int zaehlweise) {
		this.zaehlweise = zaehlweise;
	}

	public void setSpielerPausenZeit(int spielerPausenZeit) {
		this.spielerPausenZeit = spielerPausenZeit;
	}

	public void turnierplanErstellen() {
		throw new UnsupportedOperationException();
	}

	public void turnierplanDrucken() {
		throw new UnsupportedOperationException();
	}

	public void ergebnisFormularerstellen() {
		throw new UnsupportedOperationException();
	}

	public void startgeldlisteDrucken() {
		throw new UnsupportedOperationException();
	}
}
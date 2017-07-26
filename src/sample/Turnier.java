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
	private int groesse;
	private int matchDauer;
	private int gesamtSpiele;
	private LocalDate datum = LocalDate.now();
	private String name;
	private int zaehlweise = 0; // 0=bis21 1=bis 11 mit Verlängerung 2=bis 11 ohne Verlängerung
	private int anzahlSpieler;
	private Feld[] felder;
	private int spielerPausenZeit = 10;
	private int turnierid;
	private Dictionary<Integer,Spielklasse> spielklassen = new Hashtable<Integer,Spielklasse>();
	private Dictionary<Integer,Spieler> spieler = new Hashtable<Integer,Spieler>();


	public Turnier(String name, int turnierid) {
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
package sample;
import java.util.Date;
import java.util.Timer;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Turnier {
	private int groesse;
	private Timer matchDauer;
	private int gesamtSpiele;
	private Date datum;
	private String name;
	private int anzahlSpieler;
	private Feld[] felder;
	private int spielerPausenZeit = 10;
	private int turnierid;

	public Turnier(String name, int turnierid) {
		this.name = name;
		this.turnierid = turnierid;
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
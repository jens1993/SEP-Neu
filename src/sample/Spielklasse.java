package sample;
import java.util.List;
import java.util.Vector;

public class Spielklasse {
	private int spielklasseID;
	private String disziplin;
	private String niveau;
	private List<Spieler> setzliste;
	private Spielsystem spielsystem;
	private float meldeKosten = (float) 5;
	private Turnier turnier;
	private int turnierid;
	private boolean aktiv;

	public Spielklasse(int spielklasseID, String disziplin, String niveau, int turnierid) {
		this.spielklasseID = spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		this.turnierid = turnierid;
	}

	public int getSpielklasseID() {
		return spielklasseID;
	}

	public String getDisziplin() {
		return disziplin;
	}

	public String getNiveau() {
		return niveau;
	}

	public int getTurnierid() {
		return turnierid;
	}

	public List<Spieler> getSetzliste() {
		return setzliste;
	}

	public void setSetzliste(List<Spieler> setzliste) {
		this.setzliste = setzliste;
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
}
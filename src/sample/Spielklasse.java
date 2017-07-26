package sample;
import java.util.List;
import sample.DAO.*;
import sample.Enums.*;
import sample.Spielsysteme.Spielsystem;

public class Spielklasse {
	private int spielklasseID;
	private String disziplin;
	private String niveau;
	private List<Spieler> setzliste;
	private Spielsystem spielsystem;
	private float meldeKosten = (float) 5;
	private Turnier turnier;
	private boolean aktiv;

	public Spielklasse(int spielklasseID, String disziplin, String niveau, Turnier turnier) {
		this.spielklasseID = spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		this.turnier = turnier;
	}

	public Spielsystem getSpielsystem() {
		return spielsystem;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public boolean isAktiv() {
		return aktiv;
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

	public List<Spieler> getSetzliste() {
		return setzliste;
	}

	public void setSetzliste(List<Spieler> setzliste) {
		this.setzliste = setzliste;
	}

	public float getMeldeKosten() {
		return meldeKosten;
	}
}
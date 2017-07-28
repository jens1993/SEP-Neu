package sample;
import java.util.ArrayList;

import sample.Spielsysteme.Spielsystem;

public class Spielklasse {
	private int spielklasseID;
	private String disziplin;
	private String niveau;
	private ArrayList<Team> setzliste;
	private Spielsystem spielsystem;
	private float meldeKosten = (float) 5;
	private Turnier turnier;
	private boolean aktiv;

	public Spielklasse(int spielklasseID, String disziplin, String niveau, Turnier turnier) {
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		this.turnier = turnier;
	}

	public int getSpielklasseID() {
		return spielklasseID;
	}

	public void setSpielklasseID(int spielklasseID) {
		this.spielklasseID = spielklasseID;
	}

	public void setSpielsystem(Spielsystem spielsystem) {
		this.spielsystem = spielsystem;
	}

	public void setMeldeKosten(float meldeKosten) {
		this.meldeKosten = meldeKosten;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
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


	public String getDisziplin() {
		return disziplin;
	}

	public String getNiveau() {
		return niveau;
	}

	public ArrayList<Team> getSetzliste() {
		return setzliste;
	}

	public void setSetzliste(ArrayList<Team> setzliste) {
		this.setzliste = setzliste;
	}

	public float getMeldeKosten() {
		return meldeKosten;
	}
}
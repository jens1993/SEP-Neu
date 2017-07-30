package sample.Spielsysteme;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.Hashtable;
import java.util.List;

public abstract class Spielsystem {
	private String name;
	private int anzahlTeilnehmer;
	private int anzahlRunden;
	private int anzahlSpiele;
	private int spielsystemID;
	private Spielklasse spielklasse;
	private int offeneRundenSpiele;
	private int aktuelleRunde=0;
	private int spielSystemArt;

	protected int spielsystemCode;


	public Spielklasse getSpielklasse() {
		return spielklasse;
	}

	public int spielSystemIDberechnen(){
		int spielSystemID= spielSystemArt * 1000000;
		spielSystemID += aktuelleRunde*1000;
		spielSystemID += offeneRundenSpiele;
		return spielSystemID;
	}

	public void setSpielSystemArt(int spielSystemArt) {
		this.spielSystemArt = spielSystemArt;
	}

	public void senkeOffeneRundenSpiele(){
		this.offeneRundenSpiele--;
	}
	public void erhoeheOffeneRundenSpiele(){
		this.offeneRundenSpiele++;
	}

	public void erhoeheAktuelleRunde(){
		this.aktuelleRunde++;
	}

	public int getAktuelleRunde() {
		return aktuelleRunde;
	}
	public boolean keineOffenenRundenSpiele(){
		return offeneRundenSpiele==0;
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

	public int getSpielsystemCode() {
		return spielsystemCode;
	}
	public abstract List<Team> getPlatzierungsliste();
	public boolean systemWiederherstellen(int spielsystemCode){
		return false;
	}
	public abstract boolean beendeMatch(Spiel spiel);
}
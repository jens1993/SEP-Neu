package sample.Spielsysteme;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public abstract class Spielsystem {
	private String name;
	private int anzahlTeilnehmer;
	private int anzahlRunden;
	private int anzahlSpiele;
	private int spielsystemID;
	private Spielklasse spielklasse;
	private int offeneRundenSpiele=0;
	private int aktuelleRunde=0;
	private int spielSystemArt;
	private int extraRunde=0;
	private List<Team> platzierungsListe;

	protected int spielsystemCode;

	public Spielklasse getSpielklasse() {
		return spielklasse;
	}

	protected int spielSystemIDberechnen(){
		int spielSystemID= spielSystemArt * 10000000;
		spielSystemID += extraRunde *100000;
		spielSystemID += aktuelleRunde*1000;
		spielSystemID += offeneRundenSpiele;
		return spielSystemID;
	}

	public int getSpielSystemArt() {
		return spielSystemArt;
	}

	public void setExtraRunde(int extraRunde) {
		this.extraRunde = extraRunde;
	}

	protected void setSpielSystemArt(int spielSystemArt) {
		this.spielSystemArt = spielSystemArt;
	}

	protected void senkeOffeneRundenSpiele(){
		this.offeneRundenSpiele--;
	}
	protected void erhoeheOffeneRundenSpiele(){
		this.offeneRundenSpiele++;
	}

	protected void resetOffeneRundenSpiele(){
		offeneRundenSpiele=0;
	}

	protected void erhoeheAktuelleRunde(){
		this.aktuelleRunde++;
	}

	protected int getAktuelleRunde() {
		return aktuelleRunde;
	}
	protected boolean keineOffenenRundenSpiele(){
		return offeneRundenSpiele==0;
	}
	protected void resetAktuelleRunde(){
		this.aktuelleRunde=0;
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

	public void setOffeneRundenSpiele(int offeneRundenSpiele) {
		this.offeneRundenSpiele = offeneRundenSpiele;
	}

	public int getSpielsystemCode() {
		return spielsystemCode;
	}

	public int getExtraRunde() {
		return extraRunde;
	}

	public List<Team> getPlatzierungsliste(){
		return platzierungsListe;
	}

	public void setPlatzierungsListe(List<Team> platzierungsListe) {
		this.platzierungsListe = platzierungsListe;
	}

	protected List<Team> sortList(List<Team> teamList) {
		Collections.sort(teamList, new Comparator<Team>() {
			@Override
			public int compare(Team team1, Team team2) {
				return team1.compareTo(team2);
			}
		});
		return teamList;
	}

	public boolean systemWiederherstellen(int spielsystemCode){
		return false;
	}
	public abstract boolean beendeMatch(Spiel spiel);
}
package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.*;

public class KO extends Spielsystem {
	private boolean platzDreiAusspielen;
	static int teilnehmerzahl;
	private SpielTree finale;
	private Spielsystem spielsystem;

	public KO(List<Team> setzliste, Spielklasse spielklasse){
		this.setSpielklasse(spielklasse);
		this.teilnehmerzahl=setzliste.size();
		setSpielSystemArt(3);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(setzliste);
		knotenAufbauen(teilnehmerzahl);
		ersteRundeFuellen(setzliste);
		alleSpieleSchreiben();
	}

	public KO(List<Team> setzliste, Spielsystem spielsystem, Spielklasse spielklasse){
		this.spielsystem = spielsystem;
		this.setSpielklasse(spielklasse);
		this.teilnehmerzahl=setzliste.size();
		setSpielSystemArt(2);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(setzliste);
		knotenAufbauen(teilnehmerzahl);
		ersteRundeFuellen(setzliste);
		alleSpieleSchreiben();
	}

	private void alleSpieleSchreiben() {
		for (int i=getRunden().size()-1; i>=0;i--){
			for(int j=0;j<getRunden().get(i).size();j++){
				Spiel spiel = getRundenArray().get(i).get(j);
				spiel.getSpielDAO().create(spiel);
				spiel.setFreilosErgebnis();
			}
		}
	}

	public KO(ArrayList<Team> setzliste, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.setSpielklasse(spielklasse);		//Constructor nur für Einlesen aus der Datenbank
		this.teilnehmerzahl=setzliste.size();
		setSpielSystemArt(3);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(setzliste);
		knotenEinlesen(spiele);
		alleErgebnisseEinlesen(ergebnisse);
    }

	private void alleErgebnisseEinlesen(Dictionary<Integer, Ergebnis> ergebnisse){
		Enumeration e = ergebnisse.keys();
		int key;
		while(e.hasMoreElements()){
			key = (int) e.nextElement();
			getSpielklasse().getSpiele().get(key).setErgebnis(ergebnisse.get(key),"einlesen");
		}
	}

	private void knotenEinlesen(ArrayList<Spiel> spiele) {
		Dictionary<Integer,Spiel> dicSpiele = new Hashtable<>();
		for (int i=0;i<spiele.size();i++){
			Spiel spiel = spiele.get(i);
			int key = spiel.getSystemSpielID();
			spiel.setSpielsystem(this);
			dicSpiele.put(key,spiel);
		}
		teilnehmerzahl=spiele.size()+1;
		int anzahlRunden = rundenBerechnen();
		int hoechsterSetzplatz;
		SpielTree aktuellerKnoten = finale;
		finale.setSpiel(dicSpiele.get(getSpielSystemArt()*10000000));
		this.getRundenArray().add(new ArrayList<>());
		this.getRundenArray().get(0).add(finale.getSpiel());

		for (int i=0; i<anzahlRunden-1; i++){ //erstelle für jeder Runde Spiele
			aktuellerKnoten = finale.getSpielTree(spielSystemIDberechnen(),finale);
			hoechsterSetzplatz = (int) Math.pow(2,i+2);
			this.getRundenArray().add(0,new ArrayList<>());
			for (int j=1; j<=Math.pow(2,i); j++)
			{
				int leftKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1000;
				int rightKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1001;
				int leftKnotenSetzPlatzHeim = aktuellerKnoten.getSetzplatzHeim();
				int leftKnotenSetzPlatzGast = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1;
				int rightKnotenSetzPlatzHeim = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1;
				int rightKnotenSetzPlatzGast = aktuellerKnoten.getSetzplatzGast();
				aktuellerKnoten.addLeft(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast );
				aktuellerKnoten.getLeft().setSpiel(dicSpiele.get(leftKnotenSpielID));
				aktuellerKnoten.addRight(rightKnotenSpielID, rightKnotenSetzPlatzHeim ,rightKnotenSetzPlatzGast );
				aktuellerKnoten.getRight().setSpiel(dicSpiele.get(rightKnotenSpielID));
				aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale);
				this.getRundenArray().get(0).add(dicSpiele.get(leftKnotenSpielID));
				this.getRundenArray().get(0).add(dicSpiele.get(rightKnotenSpielID));
			}
			erhoeheAktuelleRunde();
		}
	}


	public void alleSpieleErstellen(){

	}

	public SpielTree getFinale() {
		return finale;
	}

	public SpielTree knotenAufbauen (int teilnehmerzahl){
		int anzahlRunden = rundenBerechnen();
		int hoechsterSetzplatz;
		SpielTree aktuellerKnoten = finale;
		finale.setSpiel(new Spiel(spielSystemIDberechnen(),1,2,this));
		this.getRundenArray().add(new ArrayList<>());
		this.getRundenArray().get(0).add(finale.getSpiel());

		for (int i=0; i<anzahlRunden-1; i++){ //erstelle für jeder Runde Spiele
			aktuellerKnoten = finale.getSpielTree(spielSystemIDberechnen(),finale);
			this.getRundenArray().add(0,new ArrayList<>());
			hoechsterSetzplatz = (int) Math.pow(2,i+2);
			for (int j=1; j<=Math.pow(2,i); j++)
			{
				int leftKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1000;
				int rightKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1001;
				int leftKnotenSetzPlatzHeim = aktuellerKnoten.getSetzplatzHeim();
				int leftKnotenSetzPlatzGast = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1;
				int rightKnotenSetzPlatzHeim = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1;
				int rightKnotenSetzPlatzGast = aktuellerKnoten.getSetzplatzGast();
				Spiel leftSpiel = new Spiel(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast,this);
				Spiel rightSpiel = new Spiel(rightKnotenSpielID, rightKnotenSetzPlatzHeim , rightKnotenSetzPlatzGast, this);
				aktuellerKnoten.addLeft(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast );
				aktuellerKnoten.getLeft().setSpiel(leftSpiel);
				aktuellerKnoten.addRight(rightKnotenSpielID, rightKnotenSetzPlatzHeim ,rightKnotenSetzPlatzGast );
				aktuellerKnoten.getRight().setSpiel(rightSpiel);
				aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale);
				this.getRundenArray().get(0).add(leftSpiel);
				this.getRundenArray().get(0).add(rightSpiel);
			}
			erhoeheAktuelleRunde();
		}
	return finale;
	}

	public void ersteRundeFuellen(List<Team> setzliste){
		//Zu erstem Knoten in erster Runde gehen:
		SpielTree aktuellerKnoten = finale;
		while (aktuellerKnoten.getLeft() != null){
			aktuellerKnoten = aktuellerKnoten.getLeft();
		}
		for (int i=0; i<Math.pow(2,getAnzahlRunden()-1);i++){
			for (int j=0; j<setzliste.size();j++){
				if(j+1 == aktuellerKnoten.getSetzplatzHeim()){
					aktuellerKnoten.getSpiel().setHeim(setzliste.get(j));
				}
				if(j+1 == aktuellerKnoten.getSetzplatzGast()){
					aktuellerKnoten.getSpiel().setGast(setzliste.get(j));
				}
			}

			aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale); //zum Spiel mit nächster ID gehen (im Turnierbaum 1 Spiel nach unten)
		}
	}

	public int rundenBerechnen(){
		setAnzahlRunden((int) Math.ceil(Math.log(teilnehmerzahl) / Math.log(2.0)));
		return getAnzahlRunden();
	}
	private void freiloseHinzufuegen (List<Team> setzliste){
		for (int i=setzliste.size(); i<Math.pow(2,rundenBerechnen());i++){
			setzliste.add(new Team("Freilos",this.getSpielklasse()));
			super.setzlisteDAO.update(setzliste.size(),setzliste.get(setzliste.size()-1),this.getSpielklasse());
		}
	}

	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {

		int sysID = spiel.getSystemSpielID();
		if(sysID>getSpielSystemArt()*10000000) {
			SpielTree parent = finale.getSpielTree(sysID, finale).getParent();
			if (parent.getLeft().getSpielID() == sysID) {
				parent.getSpiel().setHeim(spiel.getSieger());
			} else {
				parent.getSpiel().setGast(spiel.getSieger());
			}
			parent.getSpiel().getSpielDAO().update(parent.getSpiel());
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		return false;
	}
}
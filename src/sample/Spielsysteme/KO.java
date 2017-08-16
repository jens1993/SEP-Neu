package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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
	}

    public KO(ArrayList<Team> setzliste, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.setSpielklasse(spielklasse);		//Constructor nur für Einlesen aus der Datenbank
		this.teilnehmerzahl=setzliste.size();
		setSpielSystemArt(3);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(setzliste);
		knotenAufbauen(teilnehmerzahl);
		ersteRundeFuellen(setzliste);
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

		for (int i=0; i<anzahlRunden-1; i++){ //erstelle für jeder Runde Spiele
			aktuellerKnoten = finale.getSpielTree(spielSystemIDberechnen(),finale);
			hoechsterSetzplatz = (int) Math.pow(2,i+2);
			for (int j=1; j<=Math.pow(2,i); j++)
			{
				int leftKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1000;
				int rightKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1001;
				int leftKnotenSetzPlatzHeim = aktuellerKnoten.getSetzplatzHeim();
				int leftKnotenSetzPlatzGast = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1;
				int rightKnotenSetzPlatzHeim = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1;
				int rightKnotenSetzPlatzGast = aktuellerKnoten.getSetzplatzGast();
				aktuellerKnoten.addLeft(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast );
				aktuellerKnoten.getLeft().setSpiel(new Spiel(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast,this));
				aktuellerKnoten.addRight(rightKnotenSpielID, rightKnotenSetzPlatzHeim ,rightKnotenSetzPlatzGast );
				aktuellerKnoten.getRight().setSpiel(new Spiel(rightKnotenSpielID, rightKnotenSetzPlatzHeim , rightKnotenSetzPlatzGast, this));
				aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale);
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
		if(sysID>1) {
			SpielTree parent = finale.getSpielTree(sysID, finale).getParent();
			if (parent.getLeft().getSpielID() == sysID) {
				parent.getSpiel().setHeim(spiel.getSieger());
			} else {
				parent.getSpiel().setGast(spiel.getSieger());
			}
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
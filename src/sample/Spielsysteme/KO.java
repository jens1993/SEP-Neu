package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class KO extends Spielsystem {
	private boolean platzDreiAusspielen;
	static int teilnehmerzahl;
	private SpielTree finale = new SpielTree(1, 1, 2);

	public KO(List<Team> setzliste){
		this.teilnehmerzahl=setzliste.size();
		freiloseHinzufuegen(setzliste);
		knotenAufbauen(teilnehmerzahl);
		ersteRundeFuellen(setzliste);
	}

	public SpielTree getFinale() {
		return finale;
	}

	public SpielTree knotenAufbauen (int teilnehmerzahl){
		int anzahlRunden = rundenBerechnen();
		int hoechsterSetzplatz;
		SpielTree aktuellerKnoten = finale;

		for (int i=1; i<anzahlRunden; i++){

			hoechsterSetzplatz = (int) Math.pow(2,i+1);
			for (int j=1; j<=Math.pow(2,i-1); j++)
			{
				aktuellerKnoten.addLeft(aktuellerKnoten.getSpielID()*2, aktuellerKnoten.getSetzplatzHeim(), hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1);
				aktuellerKnoten.getLeft().setSpiel(new Spiel(aktuellerKnoten.getSpielID()*2, aktuellerKnoten.getSetzplatzHeim(), hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1,this.getSpielklasse()));
				aktuellerKnoten.addRight(aktuellerKnoten.getSpielID()*2+1, hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1, aktuellerKnoten.getSetzplatzGast());
				aktuellerKnoten.getRight().setSpiel(new Spiel(aktuellerKnoten.getSpielID()*2+1, hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1, aktuellerKnoten.getSetzplatzGast(), this.getSpielklasse()));
				aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale);
			}
		}
	return finale;
	}

	public void ersteRundeFuellen(List<Team> setzliste){
		//Zu erstem Knoten in erster Runde gehen:
		SpielTree aktuellesSpiel = finale;
		while (aktuellesSpiel.getLeft() != null){
			aktuellesSpiel = aktuellesSpiel.getLeft();
		}
		for (int i=0; i<Math.pow(2,getAnzahlRunden()-1);i++){
			for (int j=0; j<setzliste.size();j++){
				if(j+1 == aktuellesSpiel.getSetzplatzHeim()){
					aktuellesSpiel.getSpiel().setHeim(setzliste.get(j));
				}
				if(j+1 == aktuellesSpiel.getSetzplatzGast()){
					aktuellesSpiel.getSpiel().setGast(setzliste.get(j));
				}
			}

			aktuellesSpiel = aktuellesSpiel.getSpielTree(aktuellesSpiel.getSpielID()+1, finale); //zum Spiel mit nächster ID gehen (im Turnierbaum 1 Spiel nach unten)
		}
	}

	public int rundenBerechnen(){
		setAnzahlRunden((int) Math.ceil(Math.log(teilnehmerzahl) / Math.log(2.0)));
		return getAnzahlRunden();
	}
	private void freiloseHinzufuegen (List<Team> setzliste){
		for (int i=setzliste.size(); i<Math.pow(2,rundenBerechnen());i++){
			setzliste.add(new Team("Freilos"));
			System.out.println("Freilos an Setzplatz "+(i+1) +" hinzugefügt");
		}
	}

	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		return false;
	}
}
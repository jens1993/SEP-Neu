package sample.Spielsysteme;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.ArrayList;
import java.util.List;

public class GruppeMitEndrunde extends Spielsystem{
	private int anzahlGruppen;
	private int anzahlWeiterkommender;
	private int benoetigteFreilose;
	private List<Team> setzliste;
	private ArrayList<Team> templist = new ArrayList<>();
	private ArrayList<List<Team>> alleSetzListen = new ArrayList<>();
	private ArrayList<Gruppe> alleGruppen = new ArrayList<>();
	private Spielsystem endrunde;

	public GruppeMitEndrunde(List<Team> setzliste, Spielklasse spielklasse, int anzahlGruppen, int anzahlWeiterkommender) {
		this.setzliste = setzliste;
		this.anzahlGruppen = anzahlGruppen;
		this.anzahlWeiterkommender = anzahlWeiterkommender;
		setSpielklasse(spielklasse);
		setzListeAufteilen();
	}

	private void setzListeAufteilen(){
		for (int k=0; k<setzliste.size();k++){
			templist.add(setzliste.get(k));
		}
		for(int i=0; i<anzahlGruppen; i++){
			benoetigteFreilose = anzahlGruppen - (templist.size()%anzahlGruppen);
		}
		freiloseHinzufuegen();
		for (int j=0; j<templist.size()/anzahlGruppen;j++){
			alleSetzListen.add(new ArrayList<>());
		}
		boolean hochzaehlen = true;
		int zaehler = 0;
		while(templist.size()>1)
		{
			Team tempTeam = templist.get(0);
			templist.remove(tempTeam);
			alleSetzListen.get(zaehler).add(tempTeam);
			if (hochzaehlen){
				zaehler++;
			}
			else if(zaehler==anzahlGruppen-1){
				hochzaehlen = !hochzaehlen;
			}
			else{
				zaehler--;
			}
		}
	}

	private void freiloseHinzufuegen (){
		for (int i=0; i<benoetigteFreilose;i++){
			templist.add(new Team("Freilos",this.getSpielklasse()));
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
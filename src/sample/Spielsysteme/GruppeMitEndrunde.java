package sample.Spielsysteme;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class GruppeMitEndrunde extends Spielsystem{
	private int anzahlGruppen;
	private int anzahlWeiterkommender;
	private List<Team> endrundenSetzliste = new ArrayList<>();
	private List<Team> setzliste;
	private ArrayList<Team> templist = new ArrayList<>();
	private ArrayList<ArrayList<Team>> alleSetzListen = new ArrayList<>();
	private ArrayList<Gruppe> alleGruppen = new ArrayList<>();
	private Dictionary<Integer,ArrayList<Team>> allePlatzierungslisten = new Hashtable();
	private Spielsystem endrunde;

	public GruppeMitEndrunde(List<Team> setzliste, Spielklasse spielklasse, int anzahlGruppen, int anzahlWeiterkommender) {
		this.setzliste = setzliste;
		this.anzahlGruppen = anzahlGruppen;
		this.anzahlWeiterkommender = anzahlWeiterkommender;
		setSpielklasse(spielklasse);
		setzListeAufteilen();
		gruppenErstellen();
	}

	public GruppeMitEndrunde(List<Team> setzliste, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.setzliste = setzliste;		//Constructor nur für Einlesen aus der Datenbank
		this.anzahlGruppen = anzahlGruppen;
		this.anzahlWeiterkommender = anzahlWeiterkommender;
		setSpielklasse(spielklasse);
		setzListeAufteilen();
		gruppenErstellen();
	}

	private void setzListeAufteilen(){
		for (int k=0; k<setzliste.size();k++){
			templist.add(setzliste.get(k));
		}
		for (int j=0; j<anzahlGruppen;j++){
			alleSetzListen.add(new ArrayList<>());
		}
		freiloseHinzufuegen();
		boolean hochzaehlen = false;
		boolean wurdeschongetauscht=false;
		int zaehler = 0;
		while(templist.size()>0)
		{
			if ((zaehler==anzahlGruppen-1||zaehler==0)&&!wurdeschongetauscht){
				hochzaehlen = !hochzaehlen;
				wurdeschongetauscht=true;
			}
			else if(hochzaehlen){
				zaehler++;
				wurdeschongetauscht=false;
				System.out.println(zaehler);
			}
			else{
				wurdeschongetauscht=false;
				zaehler--;
			}
			Team tempTeam = templist.get(0);
			templist.remove(tempTeam);
			alleSetzListen.get(zaehler).add(tempTeam);

		}
	}

	private void gruppenErstellen(){
		for(int i=0; i<alleSetzListen.size();i++){
			alleGruppen.add(new Gruppe(alleSetzListen.get(i),this,this.getSpielklasse(),i+1));
		}
	}


	private void freiloseHinzufuegen (){
		while ((double)templist.size()%(anzahlGruppen*2)>0){
			templist.add(new Team("Freilos",this));
			super.setzlisteDAO.update(templist.size(),templist.get(templist.size()-1),this.getSpielklasse());
		}
	}
	private void endRundeErstellen(){
		int qualiPlaetzeJeGruppe = anzahlWeiterkommender/anzahlGruppen;
		int wildCards = anzahlWeiterkommender-qualiPlaetzeJeGruppe*anzahlGruppen;
		Team tempTeam;
		for(int i=0; i<qualiPlaetzeJeGruppe;i++){
			if (i%2==0){
				for (int j=0;j<allePlatzierungslisten.size();j++){
					tempTeam=allePlatzierungslisten.get(j).get(i);
					endrundenSetzliste.add(tempTeam);
				}
			}
			else{
				for (int k=allePlatzierungslisten.size()-1;k>=0;k--){
					tempTeam=allePlatzierungslisten.get(k).get(i);
					endrundenSetzliste.add(tempTeam);
				}
				for (int l=i*anzahlGruppen;l<(i+1)*anzahlGruppen;l++){
					tempTeam=endrundenSetzliste.get(l);
					endrundenSetzliste.remove(tempTeam);
					l++;
					endrundenSetzliste.add(l,tempTeam);
				}
			}
		}
		endrunde= new KO(endrundenSetzliste,this, this.getSpielklasse());
	}


	public void addPlatzierungsliste(ArrayList<Team> platzierungsliste, int extraRundenID){
		this.allePlatzierungslisten.put(extraRundenID-1,platzierungsliste);
		if(allePlatzierungslisten.size()==4) {
			int justforbreakpoint = 0;
		}
		if (allePlatzierungslisten.size()==anzahlGruppen){
			endRundeErstellen();
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

	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		return false;
	}
}
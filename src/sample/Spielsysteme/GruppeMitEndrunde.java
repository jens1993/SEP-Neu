package sample.Spielsysteme;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.*;

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

	public GruppeMitEndrunde(Spielklasse spielklasse, int anzahlGruppen, int anzahlWeiterkommender) {
		this.setzliste = spielklasse.getSetzliste();
		this.anzahlGruppen = anzahlGruppen;
		this.anzahlWeiterkommender = anzahlWeiterkommender;
		setSpielklasse(spielklasse);
		setzListeAufteilen();
		gruppenErstellen();
		rundenArrayErstellen();
	}


	public GruppeMitEndrunde(List<Team> setzliste, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.setzliste = setzliste;		//Constructor nur für Einlesen aus der Datenbank
		this.anzahlGruppen = ermittleAnzahlGruppen(spiele);
		this.anzahlWeiterkommender = anzahlWeiterkommender;
		setSpielklasse(spielklasse);
		setzListeAufteilen();
		gruppenEinlesen(spiele, ergebnisse);
		rundenArrayErstellen();
	}

	private int ermittleAnzahlGruppen(ArrayList<Spiel> spiele) {
		int anzahlGruppen = 0;
		for (int i=0; i<spiele.size();i++){
			Spiel spiel = spiele.get(i);
			int systemSpielID = spiel.getSystemSpielID();
			int gruppenNummer = systemSpielID/100000 - (spiel.getSystemSpielID()/10000000)*100;
			if (gruppenNummer>anzahlGruppen){
				anzahlGruppen = gruppenNummer;
			}
		}
		return anzahlGruppen;
	}

	private void gruppenEinlesen(ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		for(int i=0; i<alleSetzListen.size();i++){
			ArrayList<Spiel> gruppenSpiele = new ArrayList<>();
			Dictionary<Integer,Ergebnis> gruppenErgebnisse = new Hashtable<>();
			for (int j=0;j<spiele.size();j++){
				Spiel spiel = spiele.get(j);
				int gruppenNummer = (spiel.getSystemSpielID()-spiel.getSystemSpielID()/10000000 * 10000000)/100000;
				if (i+1 == gruppenNummer){
					gruppenSpiele.add(spiel);
					if (ergebnisse.get(spiel.getSystemSpielID())!=null){
						gruppenErgebnisse.put(spiel.getSystemSpielID(),ergebnisse.get(spiel.getSystemSpielID()));
					}
				}
			}
			alleGruppen.add(new Gruppe(alleSetzListen.get(i),this,this.getSpielklasse(),i+1, gruppenSpiele, gruppenErgebnisse));
		}
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
			super.setzlisteDAO.create(templist.size(),templist.get(templist.size()-1),this.getSpielklasse());
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
		endrunde= new KO(endrundenSetzliste,this, this.getSpielklasse(),true);
	}


	public void addPlatzierungsliste(ArrayList<Team> platzierungsliste, int extraRundenID){
		this.allePlatzierungslisten.put(extraRundenID-1,platzierungsliste);
		if (allePlatzierungslisten.size()==anzahlGruppen){
			endRundeErstellen();
		}
	}

	private void rundenArrayErstellen() {
		for (int i=0;i< alleGruppen.size();i++){
			ArrayList<ArrayList<Spiel>> gruppenArrayList = alleGruppen.get(i).getRundenArray();
			arrayListIntegrieren(gruppenArrayList);
		}
	}

	private void arrayListIntegrieren(ArrayList<ArrayList<Spiel>> gruppenArrayList) {
		ArrayList<ArrayList<Spiel>> gesamtArrayList = this.getRundenArray();
		while(gruppenArrayList.size()>gesamtArrayList.size()){
			gesamtArrayList.add(new ArrayList<>());
		}
		for(int i=0;i<gruppenArrayList.size();i++){
			for (int j=0;j<gruppenArrayList.get(i).size();j++){
				Spiel spiel = gruppenArrayList.get(i).get(j);
				gesamtArrayList.get(i).add(spiel);
			}
		}
	}


	public ArrayList<Gruppe> getAlleGruppen() {
		return alleGruppen;
	}

	public Spielsystem getEndrunde() {
		return endrunde;
	}

	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		int systemSpielID = spiel.getSystemSpielID();
		int extraRundenNummer = systemSpielID-spiel.getSystemSpielID()/10000000 * 10000000;
		extraRundenNummer = extraRundenNummer / 100000;
		for (int i=0;i<alleGruppen.size();i++){
			if (alleGruppen.get(i).getExtraRunde()==extraRundenNummer){
				alleGruppen.get(i).beendeMatch(spiel);
			}
		}
		return false;
	}

	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		int systemSpielID = spiel.getSystemSpielID();
		int extraRundenNummer = systemSpielID-spiel.getSystemSpielID()/10000000 * 10000000;
		extraRundenNummer = extraRundenNummer / 100000;
		for (int i=0;i<alleGruppen.size();i++){
			if (alleGruppen.get(i).getExtraRunde()==extraRundenNummer){
				alleGruppen.get(i).beendeMatch(spiel,"einlesen");
			}
		}
		return false;
	}
}
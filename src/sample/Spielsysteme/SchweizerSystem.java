package sample.Spielsysteme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import sample.*;
import sample.DAO.SpielDAO;
import sample.DAO.SpielDAOimpl;

public class SchweizerSystem extends Spielsystem {
	private int anzahlRunden;
	private int offeneRundenSpiele=0;
	private int aktuelleRunde = 1;
	private int randomVersuche=0;
	private boolean beendet;
	boolean kombinationGefunden = false;
	private ArrayList<Team> teamList;
	private ArrayList<Spiel> spiele = new ArrayList<Spiel>();
	private ArrayList<Team> nextTeamList = new ArrayList<Team>();
	private ArrayList<ArrayList<Team>> teamListArray =new ArrayList<ArrayList<Team>>();
	private ArrayList<Team> tempList = new ArrayList<>();

	public SchweizerSystem(int anzahlRunden, ArrayList<Team> teamList, Spielklasse spielklasse) {
		setSpielklasse(spielklasse);
		this.anzahlRunden = anzahlRunden;
		this.teamList = teamList;
		freiloseHinzufuegen(this.teamList);
		ersteRundeErstellen();
	}
	private void sortList(){
		Collections.sort(nextTeamList, new Comparator<Team>() {
			@Override
			public int compare(Team team1, Team team2) {
				return team1.compareTo(team2);
			}
		});
		teamListArray.add(new ArrayList<>());
		teamListArray.get(aktuelleRunde-1).clear();
		for (int i=0; i<nextTeamList.size();i++){
			System.out.println((i+1)+": "+nextTeamList.get(i).getGewonneneSpiele()+", "+
					nextTeamList.get(i).getGewonneneSaetze()+":"+
					nextTeamList.get(i).getVerloreneSaetze()+", "+
					nextTeamList.get(i).getGewonnnenePunkte()+":"+
					nextTeamList.get(i).getVerlorenePunkte());
			teamListArray.get(aktuelleRunde-1).add(nextTeamList.get(i));
			//System.out.println("Size: "+teamListArray.get(aktuelleRunde-1).size());
		}




	}

	public void ersteRundeErstellen() {
		while (teamList.size()>1){
			Team teamEins = getRandomTeam();
			this.teamList.remove(teamEins);
			this.nextTeamList.add(teamEins);
			Team teamZwei = getRandomTeam();
			this.teamList.remove(teamZwei);
			this.nextTeamList.add(teamZwei);
			Spiel spiel = new Spiel(teamEins,teamZwei,this.getSpielklasse(),spielSystemIDberechnen()-1000);
			spiele.add(spiel);
			offeneRundenSpiele++;
		}
	}
	private boolean rundeErstellen(){
		sortList();
		boolean beendet = (rundenListeErstellen(0, aktuelleRunde-1));
		if(!beendet) {
			rundeFuellen();
		}
		return beendet;
	}
	private void teamListReset(){
		teamList.clear();
		for (int i=0;i<teamListArray.get(aktuelleRunde-1).size();i++)
		{
			this.teamList.add(teamListArray.get(aktuelleRunde-1).get(i));
		}
	}
	public boolean rundenListeErstellen(int versuch, int rundenNummer) {

		if (rundenNummer+1<anzahlRunden){
			teamList.clear();
			for (int i=0;i<teamListArray.get(rundenNummer).size();i++)
			{
				this.teamList.add(teamListArray.get(rundenNummer).get(i));
			}
			nextTeamList.clear();
			System.out.println("Runde: "+(aktuelleRunde+1));
			while (this.teamList.size()>1){
				Team teamEins = teamList.get(0);
				this.teamList.remove(teamEins);
				this.nextTeamList.add(teamEins);
				Team teamZwei = sucheGegner(teamEins,versuch);
				if(teamZwei!=null) {
					this.teamList.remove(teamZwei);
					this.nextTeamList.add(teamZwei);
				}
				else{
					if(versuch+1<teamListArray.get(rundenNummer).size()) {
						if(rundenListeErstellen(versuch + 1, rundenNummer)){
							return true;
						}
					}
					else if(randomVersuche<20){
						randomVersuche++;
						listeWuerfeln(rundenNummer);
						if (rundenListeErstellen(0,rundenNummer)==true){
							return true;
						}
					}
					else {
						System.out.println("Kein möglicher Gegner gefunden, jeder hat gegen jeden gespielt");
						return true;
					}
				}
			}
			return false;
		}
		else{
			System.out.println("Schweizer System beendet");
			return true;
		}
	}
	public void rundenListeErstellenNeu(){
		teamListReset();
		nextTeamList.clear();
		System.out.println("Runde: "+(aktuelleRunde+1));
		sucheKombinationNeu();
		rundeFuellen();

	}

	private void sucheKombinationNeu(){
		boolean warNochKeinGegner;
		teamListReset();
		ArrayList<Team> tempList = new ArrayList<>();
		while (teamList.size()>1){
			if(aktuelleRunde==5){
				int forbreakpoint=0;
			}
			Team teamEins = teamList.get(0);
			teamList.remove(teamEins);
			tempList.add(teamEins);
			for(int i=0; i<teamList.size();i++)
			{
				warNochKeinGegner = teamList.get(i).warNochKeinGegner(teamEins);
				if((isPotentiellerGegner(teamList.get(i))||teamList.size()<2)&&warNochKeinGegner){
					Team teamZwei = teamList.get(i);
					teamList.remove(teamZwei);
					tempList.add(teamZwei);
					break;
				}
			}

		}
		nextTeamList = tempList;


	}

	private void sucheKombination(){
		while(this.teamList.size()>1 && !kombinationGefunden){
			Team erstesTeam = teamList.get(0); //erhalte erstes Team aus der TeamList
			List <Team> verbleibendeGegner=erstesTeam.getVerbleibendeGegner(teamList); //erhalte verbleibende Gegner für ersten Spieler
			if(verbleibendeGegner.size()==0){
				teamListReset();
				tempList.clear();
			}
			if(!kombinationGefunden) {
				teamList.remove(erstesTeam);
				tempList.add(erstesTeam);
				Team zweitesTeam;
				if(aktuelleRunde==9){
					int nurfuerbreakpoint = 0;
				}

				for (int i = 0; i < verbleibendeGegner.size(); i++) {
					if(!kombinationGefunden) {
						zweitesTeam = verbleibendeGegner.get(i);
						if(verbleibendeGegner.size()==0){
							System.out.println("keine verbleibenden Gegner");
						}
						teamList.remove(zweitesTeam);
						tempList.add(zweitesTeam);
						if (zweitesTeam == null) {
							teamListReset();
							tempList.clear();
						}
						if(tempList.size()==teamListArray.get(aktuelleRunde-1).size()&&!kombinationGefunden){
							kombinationGefunden = true;
							for (int j=0;j<tempList.size();j++) {
								nextTeamList.add(tempList.get(j));
							}
						}
						sucheKombination();

					}
				}
			}
		}
	}
	private boolean isPotentiellerGegner (Team potentiellerGegner){
		for (int i=0; i<teamList.size();i++){
			for(int j=0; j<teamList.get(i).getVerbleibendeGegner(teamList).size();j++){
				if(teamList.get(i).getVerbleibendeGegner(teamList).get(j)==potentiellerGegner){
					return true;
				}
			}
		}
		return false;
	}

	private void listeWenden(int rundenNummer) {
		Team tempTeam;
		for(int i=teamListArray.get(rundenNummer).size()-1;i>=0;i--){
			tempTeam = teamListArray.get(rundenNummer).get(i);
			teamListArray.get(rundenNummer).remove(i);
			teamListArray.get(rundenNummer).add(tempTeam);
		}
	}
	private void listeWuerfeln(int rundenNummer){
		Team tempTeam;
		//tempTeam = teamListArray.get(rundenNummer).get(verschoben);
		//teamListArray.get(rundenNummer).remove(tempTeam);
		//teamListArray.get(rundenNummer).add(teamListArray.get(rundenNummer).size()-1,tempTeam);
		for(int i=0;i<teamListArray.get(rundenNummer).size()-1;i++){
			int randomIndex = (int)(Math.random()*(teamListArray.get(rundenNummer).size()-i))+i;
			tempTeam = teamListArray.get(rundenNummer).get(randomIndex);
			teamListArray.get(rundenNummer).remove(tempTeam);
			teamListArray.get(rundenNummer).add(0,tempTeam);
		}
	}

	private void rundeFuellen(){
		teamList.clear();
		for (int i=0;i<nextTeamList.size();i++)
		{
			this.teamList.add(nextTeamList.get(i));
		}
		nextTeamList.clear();
		while (this.teamList.size()>1){
			Team teamEins = teamList.get(0);
			this.teamList.remove(teamEins);
			this.nextTeamList.add(teamEins);
			Team teamZwei = teamList.get(0);
			this.teamList.remove(teamZwei);
			this.nextTeamList.add(teamZwei);
			Spiel spiel = new Spiel(teamEins, teamZwei, this.getSpielklasse(), spielSystemIDberechnen());
			spiele.add(spiel);
			offeneRundenSpiele++;
		}


	}

	public int spielSystemIDberechnen(){
		int spielSystemID=100000;
		spielSystemID += aktuelleRunde*1000;
		spielSystemID += offeneRundenSpiele;
		return spielSystemID;
	}

	private Team sucheGegner(Team Gegner, int versuch){
		//System.out.println("Versuch: "+versuch+" Teamlist: "+teamList.size());
		Team ergebnisGegner=null;
		for(int i=0;i<teamList.size();i++){
			if(teamList.size()-versuch>0) {

				if (i + versuch < teamList.size()) {
					//System.out.println("i="+i+", Versuch="+versuch+", Teamlistsize="+teamList.size());
					Team moeglicherGegner = teamList.get(i + versuch);
					if (moeglicherGegner.warNochKeinGegner(Gegner)) {
						//System.out.println("returne: " + moeglicherGegner);
						return moeglicherGegner;
					}
				}
			}
			else
			{
				if(versuch<1){
					System.out.println("returne Null");

					return null;
				}
				else {
					ergebnisGegner = sucheGegner(Gegner, versuch - 1);
				}

			}
		}
		/*System.out.println("Team 1: "+Gegner);
		for(int j=0; j<teamList.size();j++){
			System.out.println("Team "+(j+2)+": "+teamList.get(j));
		}*/
		return ergebnisGegner;
	}
	public Team getRandomTeam(){
		int random = (int) Math.round(Math.random()*(teamList.size()-1));
		Team randomTeam = this.teamList.get(random);
		return randomTeam;
	}

	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		offeneRundenSpiele --;
		if(offeneRundenSpiele==0){
			if(aktuelleRunde<anzahlRunden){
				rundeErstellen();
				aktuelleRunde++;
				randomVersuche=0;
				kombinationGefunden = false;
				return true;
			}

		}
		return false;
	}

	private void freiloseHinzufuegen(List<Team> teamList){
		if ((teamList.size()/2) * 2 != teamList.size()){ // /2 *2 überprüft, ob Spieleranzahl gerade oder ungerade (int)
			this.teamList.add(new Team("Freilos",this.getSpielklasse()));
			System.out.println("Freilos zu schweizer hinzugefügt");
		}
	}
}
package sample.Spielsysteme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import sample.*;
import sample.DAO.SpielDAO;
import sample.DAO.SpielDAOimpl;

public class SchweizerSystem extends Spielsystem {
	private int anzahlRunden;
	private int offeneRundenSpiele=0;
	private int aktuelleRunde = 1;
	private List<Team> teamList;
	private List<Spiel> spiele = new ArrayList<Spiel>();
	private List<Team> nextTeamList = new ArrayList<Team>();

	public SchweizerSystem(int anzahlRunden, List<Team> teamList, Spielklasse spielklasse) {
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
		for (int i=0; i<nextTeamList.size();i++){
			System.out.println((i+1)+": "+nextTeamList.get(i).getGewonneneSpiele()+", "+
					nextTeamList.get(i).getGewonneneSaetze()+":"+
					nextTeamList.get(i).getVerloreneSaetze()+", "+
					nextTeamList.get(i).getGewonnnenePunkte()+":"+
					nextTeamList.get(i).getVerlorenePunkte());
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
			Spiel spiel = new Spiel(teamEins,teamZwei,this.getSpielklasse(),spielSystemIDberechnen());
			spiele.add(spiel);
			offeneRundenSpiele++;
		}
	}

	public boolean rundeErstellen() {
		sortList();
		if (aktuelleRunde<=anzahlRunden){
			for (int i=0;i<nextTeamList.size();i++)
			{
				this.teamList.add(nextTeamList.get(i));
			}
			nextTeamList.clear();
			System.out.println("Teamlistegröße: "+teamList.size());
			while (this.teamList.size()>1){
				Team teamEins = teamList.get(0);
				this.teamList.remove(teamEins);
				this.nextTeamList.add(teamEins);
				Team teamZwei = sucheGegner(teamEins);
				this.teamList.remove(teamZwei);
				this.nextTeamList.add(teamZwei);
				Spiel spiel = new Spiel(teamEins,teamZwei,this.getSpielklasse(),spielSystemIDberechnen());
				spiele.add(spiel);
				offeneRundenSpiele++;

			}
			return false;
		}
		else{
			System.out.println("Schweizer System beendet");
			return true;
		}


	}

	public int spielSystemIDberechnen(){
		int spielSystemID=100000;
		spielSystemID += aktuelleRunde*1000;
		spielSystemID += offeneRundenSpiele;
		return spielSystemID;
	}

	private Team sucheGegner(Team Gegner){
		Team team=null;
		for(int i=0;i<teamList.size();i++){
			for(int j=0;j<Gegner.getBisherigeGegner().size();j++){
				if(teamList.get(i)!=Gegner.getBisherigeGegner().get(j)){
					return teamList.get(i);
				}
			}

		}
		return team;
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
			aktuelleRunde++;
			return (rundeErstellen());
		}
		return false;
	}

	private void freiloseHinzufuegen(List<Team> teamList){
		if ((teamList.size()/2) * 2 != teamList.size()){ // /2 *2 überprüft, ob Spieleranzahl gerade oder ungerade (int)
			this.teamList.add(new Team("Freilos"));
			System.out.println("Freilos zu schweizer hinzugefügt");
		}
	}
}
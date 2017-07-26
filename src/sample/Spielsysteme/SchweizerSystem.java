package sample.Spielsysteme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import sample.*;

public class SchweizerSystem extends Spielsystem {
	private int anzahlRunden;
	private List<Team> teamList;
	private List<Spiel> aktuelleRunde = new ArrayList<Spiel>();
	private List<Team> nextTeamList = new ArrayList<Team>();

	public SchweizerSystem(int anzahlRunden, List<Team> teamList, Spielklasse spielklasse) {
		setSpielklasse(spielklasse);
		this.anzahlRunden = anzahlRunden;
		this.teamList = teamList;
		freiloseHinzufuegen(teamList);
		ersteRundeErstellen();
	}
	private void sortList(){
		Collections.sort(nextTeamList, new Comparator<Team>() {
			@Override
			public int compare(Team team1, Team team2) {
				return team1.compareTo(team2);
			}
		});

	}

	public void ersteRundeErstellen() {

		while (teamList.size()>1){
			Team teamEins = getRandomTeam();
			this.teamList.remove(teamEins);
			this.nextTeamList.add(teamEins);
			Team teamZwei = getRandomTeam();
			this.teamList.remove(teamZwei);
			this.nextTeamList.add(teamZwei);
			aktuelleRunde.add(new Spiel(teamEins, teamZwei, this.getSpielklasse()));
		}
	}
	public Team getRandomTeam(){
		int random = (int) Math.round(Math.random()*(teamList.size()-1));
		Team randomTeam = this.teamList.get(random);
		return randomTeam;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		return false;
	}

	private void freiloseHinzufuegen(List<Team> teamList){
		if ((teamList.size()/2) * 2 != teamList.size()){ // /2 *2 überprüft, ob Spieleranzahl gerade oder ungerade (int)
			this.teamList.add(new Team("Freilos"));
			System.out.println("Freilos zu schweizer hinzugefügt");
		}
	}
}
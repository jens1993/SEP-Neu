package sample.Spielsysteme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

public class SchweizerSystem extends Spielsystem {
	private int anzahlRunden;
	private List<Spieler> spielerList;
	private List<Spiel> aktuelleRunde = new ArrayList<Spiel>();
	private List<Team> nextSpielerList = new ArrayList<Team>();

	public SchweizerSystem(int anzahlRunden, List<Spieler> spielerList, Spielklasse spielklasse) {
		setSpielklasse(spielklasse);
		this.anzahlRunden = anzahlRunden;
		this.spielerList = spielerList;
		freiloseHinzufuegen(spielerList);
		ersteRundeErstellen();
	}
	private void sortList(){
		Collections.sort(nextSpielerList, new Comparator<Team>() {
			@Override
			public int compare(Team team1, Team team2) {
				return team1.compareTo(team2);
			}
		});

	}

	public void ersteRundeErstellen() {

		while (spielerList.size()>1){
			Spieler spielerEins = getRandomSpieler();
			this.spielerList.remove(spielerEins);
			this.nextSpielerList.add(spielerEins);
			Spieler spielerZwei = getRandomSpieler();
			this.spielerList.remove(spielerZwei);
			this.nextSpielerList.add(spielerZwei);
			aktuelleRunde.add(new Spiel(spielerEins, spielerZwei, this.getSpielklasse()));
		}
	}
	public Spieler getRandomSpieler(){
		int random = (int) Math.round(Math.random()*(spielerList.size()-1));
		Spieler randomSpieler = this.spielerList.get(random);
		return randomSpieler;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		return false;
	}

	private void freiloseHinzufuegen(List<Spieler> spielerList){
		if ((spielerList.size()/2) * 2 != spielerList.size()){ // /2 *2 überprüft, ob Spieleranzahl gerade oder ungerade (int)
			this.spielerList.add(new Spieler("Freilos"));
			System.out.println("Freilos zu schweizer hinzugefügt");
		}
	}
}
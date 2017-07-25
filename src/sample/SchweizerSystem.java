package sample;

import java.util.List;

public class SchweizerSystem extends Spielsystem {
	private int anzahlRunden;
	private List<Spieler> spielerList;
	private List<Spiel> aktuelleRunde;
	private List<Spieler> nextSpielerList;
	public SchweizerSystem(int anzahlRunden, List<Spieler> spielerList, Spielklasse spielklasse) {
		setSpielklasse(spielklasse);
		this.anzahlRunden = anzahlRunden;
		this.spielerList = spielerList;
		freiloseHinzufuegen(spielerList);
		ersteRundeErstellen(spielerList);
	}

	public void ersteRundeErstellen(List<Spieler> spielerList){
		int i=0;
		while (i<spielerList.size()){
			aktuelleRunde.add(new Spiel(spielerList.get(i), spielerList.get(i+1), this.getSpielklasse()));
			i=i+2;
		}
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
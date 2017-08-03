package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.*;

public class Gruppe extends Spielsystem {
	private ArrayList<Team> teamList;
	private GruppeMitEndrunde spielsystem;
	int anzahlTeams;
	int[][] schablone;

	private static ArrayList<Integer> arrayVerschieben(ArrayList<Integer> arrayList){
		int temp = arrayList.get(0);
		arrayList.remove(0);
		arrayList.add(temp);
		return arrayList;
	}

	public Gruppe(ArrayList<Team> setzliste, Spielklasse spielklasse) {
		this.setSpielSystemArt(1);
		setSpielklasse(spielklasse);
		this.teamList = setzliste;
		freiloseHinzufuegen(teamList);
		setAnzahlRunden(teamList.size()-1);
		anzahlTeams = teamList.size();
		alleSpieleErstellen();
		schablone = new int[anzahlTeams][anzahlTeams];
		schabloneBauen();
		for (int i=0;i<getAnzahlRunden();i++){
			rundeErstellen();
			resetOffeneRundenSpiele();
		}
	}
	public Gruppe(ArrayList<Team> setzliste, Spielklasse spielklasse, List<Spiel> spielListe, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.setSpielSystemArt(1); //Constructor nur für Einlesen aus der Datenbank
		setSpielklasse(spielklasse);
		this.teamList = setzliste;
		freiloseHinzufuegen(teamList);
		setAnzahlRunden(teamList.size()-1);
		anzahlTeams = teamList.size();
		alleSpieleErstellen();
		schablone = new int[anzahlTeams][anzahlTeams];
		schabloneBauen();
		for (int i=0;i<getAnzahlRunden();i++){
			rundeErstellen();
			resetOffeneRundenSpiele();
		}
	}

	public Gruppe(ArrayList<Team> setzliste, GruppeMitEndrunde spielsystem,Spielklasse spielklasse, int extraRunde) {
		this.setSpielSystemArt(2);
		this.setExtraRunde(extraRunde);
		this.spielsystem = spielsystem;
		this.setSpielklasse(spielklasse);
		this.teamList = setzliste;
		freiloseHinzufuegen(teamList);
		setAnzahlRunden(teamList.size()-1);
		anzahlTeams = teamList.size();
		alleSpieleErstellen();
		schablone = new int[anzahlTeams][anzahlTeams];
		schabloneBauen();
		for (int i=0;i<getAnzahlRunden();i++){
			rundeErstellen();
			resetOffeneRundenSpiele();
		}
		setOffeneRundenSpiele(anzahlTeams/2);
		resetAktuelleRunde();
	}

	private void alleSpieleErstellen(){
		for (int i=1;i<=getAnzahlRunden();i++){
			resetOffeneRundenSpiele();
			for (int j=0; j<anzahlTeams/2;j++){
				//if(spielsystem==null) {
				new Spiel(spielSystemIDberechnen(),this);
				/*}
				else{
					new Spiel(spielSystemIDberechnen(),this.spielsystem);
				}*/
				erhoeheOffeneRundenSpiele();
			}
			erhoeheAktuelleRunde();
		}
		resetOffeneRundenSpiele();
		resetAktuelleRunde();
	}

	private void schabloneBauen(){
		ArrayList<Integer> rundenArray = new ArrayList<>();
		rundenArray.add(1);
		for (int j=anzahlTeams-1;j>1;j--){
			rundenArray.add(j);
		}
		for(int i=0; i<anzahlTeams-1; i++){
			for (int k=0;k<anzahlTeams-1; k++){
				schablone[i][k] = rundenArray.get(k);
			}
			schablone[i][anzahlTeams-1]=schablone[i][i];
			schablone[anzahlTeams-1][i]=schablone[i][i];
			schablone[i][i] = 0;
			arrayVerschieben(rundenArray);
		}
	}

	private void rundeErstellen(){
		if(getAktuelleRunde()<=getAnzahlRunden()){
			ArrayList<Team> tempList = new ArrayList<>();
			for (int i=0; i<teamList.size();i++){
				tempList.add(teamList.get(i));
			}
			erhoeheAktuelleRunde();
			while (tempList.size()>1){
				Team teamEins = tempList.get(tempList.size()-1);//nehme letztes Team aus der temporären Liste (höchster verbleibender Setzplatz)
				int setzplatzTeamEins = teamList.indexOf(teamEins)+1;
				tempList.remove(teamEins);
				int setzplatzTeamZwei = schabloneDurchsuchen(setzplatzTeamEins); //durchsuche die Schablone nach aktuellem Gegner für TeamEins
				Team teamZwei = teamList.get(setzplatzTeamZwei-1);  //hole Gegner aus der Setzliste! (nicht TempList, weil diese kleiner wird!
				tempList.remove(teamZwei);  						//entferne Gegner aus tempList
				//alleSpiele.add(new Spiel(teamZwei,teamEins,getSpielklasse(),spielSystemIDberechnen()));
				if(getSpielklasse()!=null){
					getSpielklasse().getSpiele().get(spielSystemIDberechnen()-1000).setHeim(teamZwei);
					getSpielklasse().getSpiele().get(spielSystemIDberechnen()-1000).setGast(teamEins);
				}
				else{
					spielsystem.getSpielklasse().getSpiele().get(spielSystemIDberechnen()-1000).setHeim(teamZwei);
					spielsystem.getSpielklasse().getSpiele().get(spielSystemIDberechnen()-1000).setGast(teamEins);
				}
				erhoeheOffeneRundenSpiele();
			}

		}
	}
	private int schabloneDurchsuchen(int setzplatzTeamEins){
		for(int i=0; i<schablone[setzplatzTeamEins-1].length;i++){
			if(schablone[setzplatzTeamEins-1][i] == getAktuelleRunde()){
				return i+1;
			}
		}
		System.out.println("Gegner für diese Runde nicht gefunden!");
		return 0;
	}

	private void freiloseHinzufuegen(List<Team> teamList){
		if ((teamList.size()/2) * 2 != teamList.size()){ // /2 *2 überprüft, ob Spieleranzahl gerade oder ungerade (int)
			this.teamList.add(new Team("Freilos",this.getSpielklasse()));
			System.out.println("Freilos zu Gruppe hinzugefügt");
			super.setzlisteDAO.update(teamList.size(),teamList.get(teamList.size()-1),this.getSpielklasse());
		}
	}



	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		senkeOffeneRundenSpiele();
		if(keineOffenenRundenSpiele()){
			erhoeheAktuelleRunde();
			setOffeneRundenSpiele(anzahlTeams/2);
			System.out.println(getAktuelleRunde());
			if(getAktuelleRunde()==getAnzahlRunden()){
				sortList(teamList);
				setPlatzierungsListe(teamList);
				if (spielsystem!=null){
					spielsystem.addPlatzierungsliste(teamList,getExtraRunde());
				}
			}
		}
		return false;
	}
}
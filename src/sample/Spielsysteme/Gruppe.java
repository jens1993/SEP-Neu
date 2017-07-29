package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.ArrayList;
import java.util.List;

public class Gruppe extends Spielsystem {
	private List<Team> teamList;
	private int aktuelleRunde=0;
	int anzahlTeams;
	int[][] schablone;
	private int offeneRundenSpiele;
	List<Spiel> alleSpiele = new ArrayList<>();
	private static ArrayList<Integer> arrayVerschieben(ArrayList<Integer> arrayList){
		int temp = arrayList.get(0);
		arrayList.remove(0);
		arrayList.add(temp);
		return arrayList;
	}

	public Gruppe(List<Team> setzliste, Spielklasse spielklasse) {
		setSpielklasse(spielklasse);
		this.teamList = setzliste;
		freiloseHinzufuegen(teamList);
		setAnzahlRunden(teamList.size()-1);
		anzahlTeams = teamList.size();
		schablone = new int[anzahlTeams][anzahlTeams];
		schabloneBauen();
		rundeErstellen();
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
		for(int x=0; x<anzahlTeams; x++){
			for (int y=0;y<anzahlTeams; y++){
				System.out.print("["+schablone[x][y]+"]");
			}
			System.out.println();
		}
	}

	private void rundeErstellen(){
		if(aktuelleRunde<getAnzahlRunden()){
			ArrayList<Team> tempList = new ArrayList<>();
			for (int i=0; i<teamList.size();i++){
				tempList.add(teamList.get(i));
			}
			aktuelleRunde++;
			offeneRundenSpiele = 0;
			while (tempList.size()>1){
				Team teamEins = tempList.get(tempList.size()-1);//nehme letztes Team aus der temporären Liste (höchster verbleibender Setzplatz)
				int setzplatzTeamEins = teamList.indexOf(teamEins)+1;
				tempList.remove(teamEins);
				int setzplatzTeamZwei = schabloneDurchsuchen(setzplatzTeamEins); //durchsuche die Schablone nach aktuellem Gegner für TeamEins
				Team teamZwei = teamList.get(setzplatzTeamZwei-1);  //hole Gegner aus der Setzliste! (nicht TempList, weil diese kleiner wird!
				tempList.remove(teamZwei);  						//entferne Gegner aus tempList
				alleSpiele.add(new Spiel(teamZwei,teamEins,getSpielklasse(),spielSystemIDberechnen()));
				offeneRundenSpiele++;
			}
		}
	}
	private int schabloneDurchsuchen(int setzplatzTeamEins){
		for(int i=0; i<schablone[setzplatzTeamEins-1].length;i++){
			if(schablone[setzplatzTeamEins-1][i] == aktuelleRunde){
				return i+1;
			}
		}
		System.out.println("Gegner für diese Runde nicht gefunden!");
		return 0;
	}

	private void freiloseHinzufuegen(List<Team> teamList){
		if ((teamList.size()/2) * 2 != teamList.size()){ // /2 *2 überprüft, ob Spieleranzahl gerade oder ungerade (int)
			this.teamList.add(new Team("Freilos",this.getSpielklasse()));
			System.out.println("Freilos zu schweizer hinzugefügt");
		}
	}

	public int spielSystemIDberechnen(){
		int spielSystemID=200000;
		spielSystemID += aktuelleRunde*1000;
		spielSystemID += offeneRundenSpiele;
		return spielSystemID;
	}

	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		offeneRundenSpiele --;
		if(aktuelleRunde==getAnzahlRunden()){
			return true;
		}
		else{
			if(offeneRundenSpiele==0){
					rundeErstellen();

				}

			}
		return false;
	}
}
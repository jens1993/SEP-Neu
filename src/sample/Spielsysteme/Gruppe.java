package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.ArrayList;
import java.util.List;

public class Gruppe extends Spielsystem {
	private List<Team> teamList;

	int anzahlTeams;
	int[][] schablone;

	List<Spiel> alleSpiele = new ArrayList<>();
	private static ArrayList<Integer> arrayVerschieben(ArrayList<Integer> arrayList){
		int temp = arrayList.get(0);
		arrayList.remove(0);
		arrayList.add(temp);
		return arrayList;
	}

	public Gruppe(List<Team> setzliste, Spielklasse spielklasse) {
		this.setSpielSystemArt(1);
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
				alleSpiele.add(new Spiel(teamZwei,teamEins,getSpielklasse(),spielSystemIDberechnen()));
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
			System.out.println("Freilos zu schweizer hinzugefügt");
		}
	}



	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		senkeOffeneRundenSpiele();
		if(getAktuelleRunde()==getAnzahlRunden()){
			return true;
		}
		else{
			if(keineOffenenRundenSpiele()){
					rundeErstellen();

				}

			}
		return false;
	}
}
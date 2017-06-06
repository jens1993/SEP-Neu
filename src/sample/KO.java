package sample;

import java.util.ArrayList;

public class KO extends Spielsystem {
	private boolean platzDreiAusspielen;
	ArrayList teilnehmer = new ArrayList();
	static int ifor;
	ArrayList ersteRunde = new ArrayList();
	/*private void stringFuellen(){
		for (int i=0; i<65;i++){
			this.teilnehmer[i] = "spieler"+i;
		}
	}
	*/

	public void rundenBerechnen(){
		for (int i=1; i<= ifor; i++){
			this.teilnehmer.add(i-1, "spieler" + i);
		}
		setAnzahlRunden((int) Math.ceil(Math.log(teilnehmer.size()) / Math.log(2.0)));
		//System.out.println(getAnzahlRunden());

	}

	KO(int ifor){
		this.ifor=ifor;
		ersteRundeErstellen();
	}

	public void ersteRundeErstellen(){
		rundenBerechnen();
		for (int i=teilnehmer.size(); i< Math.pow(2,getAnzahlRunden());i++){
			teilnehmer.add(i,"Rast");
		}
		for(int i=0; i<teilnehmer.size()/2;i++){
			//Spiel spiel = new Spiel(i,(String) teilnehmer.get(i),(String) teilnehmer.get(teilnehmer.size()-i));
			//ersteRunde.add(i,spiel );
			System.out.println((String) teilnehmer.get(i)+ " gegen " +(String) teilnehmer.get(teilnehmer.size()-i-1));
		}
	}


}
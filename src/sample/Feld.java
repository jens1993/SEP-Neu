package sample;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Feld {
	private boolean profiMatte;
	private int feldID;
	private Spiel inVorbereitung;
	private Spiel aktivesSpiel;
	private Turnier turnier;

	public Feld(boolean profiMatte, int feldID, Turnier turnier) {
		this.profiMatte = profiMatte;
		this.feldID = feldID;
		this.turnier = turnier;
	}

	public Feld(boolean profiMatte, int feldID, Spiel inVorbereitung, Spiel aktivesSpiel, Turnier turnier) {
		this.profiMatte = profiMatte;
		this.feldID = feldID;
		this.inVorbereitung = inVorbereitung;
		this.aktivesSpiel = aktivesSpiel;
		this.turnier = turnier;
	}

	public boolean isProfiMatte() {
		return profiMatte;
	}

	public int getFeldID() {
		return feldID;
	}

	public Spiel getInVorbereitung() {
		return inVorbereitung;
	}

	public Spiel getAktivesSpiel() {
		return aktivesSpiel;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public void setInVorbereitung(Spiel inVorbereitung) {
		this.inVorbereitung = inVorbereitung;
	}

	public void setAktivesSpiel(Spiel aktivesSpiel) {
		this.aktivesSpiel = aktivesSpiel;
	}
}
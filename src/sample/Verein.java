package sample;

import java.util.Vector;

public class Verein {
	private int vereinsID;
	private String extVereinsID;
	private String name;
	private String verband;

	public int getVereinsID() {
		return vereinsID;
	}

	public String getName() {
		return name;
	}

	public String getVerband() {
		return verband;
	}

	public String getExtVereinsID() {
		return extVereinsID;
	}


	public Verein(int vereinsID, String extVereinsID, String name, String verband) {
		this.vereinsID = vereinsID;
		this.extVereinsID = extVereinsID;
		this.name = name;
		this.verband = verband;
	}

	public void vereinErstellen(int aVereinsnummer, String aName, String aVerband) {
		throw new UnsupportedOperationException();
	}

	public void vereinLoeschen(int aVereinsnummer) {

		throw new UnsupportedOperationException();
	}
}
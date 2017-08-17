package sample;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
import java.util.Vector;

public class Verein {
	private int vereinsID;
	private String extVereinsID;
	private String name;
	private String verband;
	VereinDAO vereinDAO = new VereinDAOimpl();

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
		vereinDAO.create(this);
	}
	public Verein(int vereinsID, String extVereinsID, String name, String verband,String einlesen) {
		this.vereinsID = vereinsID;
		this.extVereinsID = extVereinsID;
		this.name = name;
		this.verband = verband;
	}

	@Override
	public String toString() {
		return name;
	}
}
package sample;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.Enums.Disziplin;
import sample.Enums.Niveau;
import sample.Spielsysteme.Spielsystem;

public class Spielklasse {
	private int spielklasseID;
	private String disziplin;
	private String niveau;
	private ArrayList<Team> setzliste;
	private Spielsystem spielsystem;
	private float meldeKosten = (float) 5;
	private Turnier turnier;
	private Hashtable<Integer,Spiel> spiele = new Hashtable<>();
	TurnierDAO t = new TurnierDAOimpl();
	private boolean aktiv;

	public Spielklasse(int spielklasseID, String disziplin, String niveau, Turnier turnier) {
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		this.turnier = turnier;
	}
	public Spielklasse(int spielklasseID,					   Disziplin disziplin,					   Niveau niveau,					   Turnier turnier)
	{
		System.out.println("neue Spielklasse mit Enums1 und gesetzter SpielklasseID");
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin.toString();
		this.niveau = niveau.toString();
		this.turnier = turnier;
	}
	public Spielklasse(int spielklasseID,					   Disziplin disziplin,					   Niveau niveau,					   int turnierid)
	{
		System.out.println("neue Spielklasse mit Enums2 und gesetzter SpielklasseID");
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin.toString();
		this.niveau = niveau.toString();
		Turnier turnier = new Turnier("",turnierid, LocalDate.now());
		this.turnier = t.read(turnier);
	}


	public Spielklasse(int spielklasseID, String disziplin, String niveau, int turnierid)
	{
		System.out.println("neue Spielklasse mit gesetzter SpielklasseID ohne Enum");
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		Turnier turnier = new Turnier("",turnierid, LocalDate.now());
		this.turnier = t.read(turnier);
		System.out.println(this.turnier.getName());

	}

	public Hashtable<Integer, Spiel> getSpiele() {
		return spiele;
	}

	public int getSpielklasseID() {
		return spielklasseID;
	}

	public void setSpielklasseID(int spielklasseID) {
		this.spielklasseID = spielklasseID;
	}

	public void setSpielsystem(Spielsystem spielsystem) {
		this.spielsystem = spielsystem;
	}

	public void setMeldeKosten(float meldeKosten) {
		this.meldeKosten = meldeKosten;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	public Spielsystem getSpielsystem() {
		return spielsystem;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public boolean isAktiv() {
		return aktiv;
	}


	public String getDisziplin() {
		return disziplin;
	}

	public String getNiveau() {
		return niveau;
	}

	public ArrayList<Team> getSetzliste() {
		return setzliste;
	}

	public void setSetzliste(ArrayList<Team> setzliste) {
		this.setzliste = setzliste;
	}

	public float getMeldeKosten() {
		return meldeKosten;
	}
}
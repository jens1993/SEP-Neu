package sample;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import sample.DAO.SpielklasseDAO;
import sample.DAO.SpielklasseDAOimpl;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.Enums.Disziplin;
import sample.Enums.Niveau;
import sample.Spielsysteme.Spielsystem;

public class Spielklasse {
	private SpielklasseDAO spielklasseDAO = new SpielklasseDAOimpl();
	private int spielklasseID;
	private String disziplin;
	private String niveau;
	private ArrayList<Team> setzliste=new ArrayList<>();
	private Spielsystem spielsystem;
	private float meldeKosten = (float) 5;
	private Turnier turnier;
	private Hashtable<Integer,Spiel> spiele = new Hashtable<>();
	private boolean aktiv;
	private boolean einzel = false;
	private boolean setzliste_gesperrt= false;


	@Override
	public String toString() {
		//funktioniert nicht, weil freilose auch als Spiele zählen..
//		if(this.getSpiele()!=null&&this.getSpiele().size()>0)
//		{
//			return	 disziplin +
//					" - " + niveau + " - Spiele:"+this.getSpiele().size() ;
//		}
//		else
//		{
			return	 disziplin +
					" - " + niveau  ;


	}

	public Spielklasse(int spielklasseID, String disziplin, String niveau, Turnier turnier) {
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		this.turnier = turnier;
		if (disziplin.toUpperCase().contains("EINZEL")){
			einzel=true;
		}
	}
	public Spielklasse(Disziplin disziplin, Niveau niveau, Turnier turnier)
	{
		System.out.println("neue Spielklasse mit Enums1 und gesetzter SpielklasseID");
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin.toString();
		this.niveau = niveau.toString();
		this.turnier = turnier;
		System.out.println("Disziplin = "+disziplin.toString());
		if(disziplin.toString().toUpperCase().contains("EINZEL"))
		{
			System.out.println("Erfolg");
			einzel=true;
		}
	}



	/*public Spielklasse(int spielklasseID, String disziplin, String niveau, int turnierid)
	{
		System.out.println("neue Spielklasse mit gesetzter SpielklasseID ohne Enum");
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		Turnier turnier = new Turnier("",turnierid, LocalDate.now());
		this.turnier = t.read(turnier);
		System.out.println(this.turnier.getName());
		if (disziplin.toUpperCase().contains("EINZEL")){
			einzel=true;
		}

	}*/

	public boolean isEinzel() {
		return einzel;
	}

	public SpielklasseDAO getSpielklasseDAO() {
		return spielklasseDAO;
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

	public boolean isSetzliste_gesperrt() {
		return setzliste_gesperrt;
	}

	public void setSetzliste_gesperrt(boolean setzliste_gesperrt) {
		this.setzliste_gesperrt = setzliste_gesperrt;
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

	public void addSetzliste (Team team )
	{
		this.setzliste.add(team);
	}
	public float getMeldeKosten() {
		return meldeKosten;
	}
}
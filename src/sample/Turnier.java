package sample;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import sample.GUI.MainController;

public class Turnier  implements Initializable {
	private int matchDauer;
	private int gesamtSpiele;
	private LocalDate datum = LocalDate.now();
	private String name;
	private int zaehlweise = 0; // 0=bis21 1=bis 11 mit Verlängerung 2=bis 11 ohne Verlängerung
	private int spielerPausenZeit = 10;
	private int turnierid;

	private Dictionary<Integer, Spielklasse> spielklassen = new Hashtable<Integer,Spielklasse>();
	private ArrayList<Feld> felder = new ArrayList<>();
	private Dictionary<Integer, Verein> vereine = new Hashtable<Integer,Verein>();
	private Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();
	private Dictionary<Integer, Team> teams = new Hashtable<Integer,Team>();
	private Dictionary<Integer, Spiel> spiele = new Hashtable<Integer,Spiel>();
	private Dictionary<Integer, Turnier> turnierliste = new Hashtable<Integer,Turnier>();


	private ObservableList<Spiel> obs_gespielteSpiele = FXCollections.observableArrayList();
	private ObservableList<Spiel> obs_aktiveSpiele = FXCollections.observableArrayList();
	private ObservableList<Spiel> obs_ausstehendeSpiele = FXCollections.observableArrayList();
	private ObservableList<Spielklasse> obs_spielklassen = FXCollections.observableArrayList();



	public ObservableList<Spielklasse> getObs_spielklassen() {
		return obs_spielklassen;
	}

	public void setObs_spielklassen(ObservableList<Spielklasse> obs_spielklassen) {
		this.obs_spielklassen = obs_spielklassen;
	}
	public void addtObs_spielklassen(Spielklasse spielklasse) {
		this.obs_spielklassen.add(spielklasse);
	}
	public void removeobs_spielklassen(Spielklasse spielklasse) {
		this.obs_spielklassen.remove(spielklasse);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obs_ausstehendeSpiele.addListener(new ListChangeListener<Spiel>() {
			@Override
			public void onChanged(Change<? extends Spiel> c) {
				// System.out.println("Changed on " + c.toString());

				MainController m = new MainController();
				try {
					System.out.println("Änderung der obsListe ausstehende Spiele");
					m.fuelleSpielElemente();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (c.next()) {
					System.out.println(c.getFrom());
				}

			}
		});
	}



	public ObservableList<Spiel> getObs_gespielteSpiele() {
		return obs_gespielteSpiele;
	}

	public void removeobsGespielteSpiele(Spiel spiel) {
		this.obs_gespielteSpiele.remove(spiel);

	}
	public void removeobsAktiveSpiele(Spiel spiel) {
		this.obs_aktiveSpiele.remove(spiel);
	}
	public void removeobsAusstehendeSpiele(Spiel spiel) {
		this.obs_ausstehendeSpiele.remove(spiel);
	}
	public void addobsGespielteSpiele(Spiel spiel) {
		this.obs_gespielteSpiele.add(spiel);
	}
	public void addobsAktiveSpiele(Spiel spiel) {
		this.obs_aktiveSpiele.add(spiel);
	}
	public void addobsAusstehendeSpiele(Spiel spiel) {
		this.obs_ausstehendeSpiele.add(spiel);
	}
	public void setObs_gespielteSpiele(ObservableList<Spiel> obs_gespielteSpiele) {

		this.obs_gespielteSpiele = obs_gespielteSpiele;
	}

	public void setObs_aktiveSpiele(ObservableList<Spiel> obs_aktiveSpiele) {

		this.obs_aktiveSpiele = obs_aktiveSpiele;
	}

	public void setObs_ausstehendeSpiele(ObservableList<Spiel> obs_ausstehendeSpiele) {


		this.obs_ausstehendeSpiele = obs_ausstehendeSpiele;
	}

	public ObservableList<Spiel> getObs_aktiveSpiele() {
		return obs_aktiveSpiele;
	}

	public ObservableList<Spiel> getObs_ausstehendeSpiele() {
		return obs_ausstehendeSpiele;
	}

	public Turnier(String name, int turnierid, LocalDate datum) {
		this.datum = datum;
		this.name = name;
		this.turnierid = turnierid;
	}


	public int getGesamtSpiele() {
		return gesamtSpiele;
	}

	public int getSpielerPausenZeit() {
		return spielerPausenZeit;
	}

	public int getMatchDauer() { return matchDauer; }

	public void setGesamtSpiele(int gesamtSpiele) {
		this.gesamtSpiele = gesamtSpiele;
	}

	public int getZaehlweise() { return zaehlweise; }

	public LocalDate getDatum() {
		return datum;
	}

	public String getName() {
		return name;
	}

	public int getTurnierid() {
		return turnierid;
	}

	public Dictionary<Integer, Spielklasse> getSpielklassen() {
		return spielklassen;
	}
	public Dictionary<Integer, Spielklasse> addSpielklassen(Spielklasse sp) {

		spielklassen.put(sp.getSpielklasseID(),sp);
		return spielklassen;
	}

	public Dictionary<Integer, Verein> getVereine() {
		return vereine;
	}

	public Dictionary<Integer, Turnier> getTurniere() {
		return turnierliste;
	}

	public void setVereine(Dictionary<Integer, Verein> vereine) {
		this.vereine = vereine;
	}

	public void setTurniere(Dictionary<Integer, Turnier> turnierliste) {
		this.turnierliste = turnierliste;
	}

	public Dictionary<Integer, Spieler> getSpieler() {
		return spieler;
	}

	public ArrayList<Feld> getFelder() {
		return felder;
	}

	public void setFelder(ArrayList<Feld> felder) {
		this.felder = felder;
	}

	public Dictionary<Integer, Team> getTeams() {
		return teams;
	}

	public void setTeams(Dictionary<Integer, Team> team) {
		this.teams = team;
	}

	public Dictionary<Integer, Spiel> getSpiele() {
		return spiele;
	}

	public void setSpiele(Dictionary<Integer, Spiel> spiele) {
		this.spiele = spiele;
	}

	public void setSpielklassen(Dictionary<Integer, Spielklasse> spielklassen) {
		this.spielklassen = spielklassen;
	}

	public void setSpieler(Dictionary<Integer, Spieler> spieler) {
		this.spieler = spieler;
	}

	public void setMatchDauer(int matchDauer) {
		this.matchDauer = matchDauer;
	}

	public void setZaehlweise(int zaehlweise) {
		this.zaehlweise = zaehlweise;
	}

	public void setSpielerPausenZeit(int spielerPausenZeit) {
		this.spielerPausenZeit = spielerPausenZeit;
	}

	public void turnierplanErstellen() {
		throw new UnsupportedOperationException();
	}

	public void turnierplanDrucken() {
		throw new UnsupportedOperationException();
	}

	public void ergebnisFormularerstellen() {
		throw new UnsupportedOperationException();
	}

	public void startgeldlisteDrucken() {
		throw new UnsupportedOperationException();
	}


}
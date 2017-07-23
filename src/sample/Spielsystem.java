package sample;
public class Spielsystem {
	private String name;
	private int anzahlTeilnehmer;
	private int anzahlRunden;
	private int anzahlSpiele;
	private int id;
	private Zaehlweise zaehlweise;
	public int getAnzahlRunden() {
		return anzahlRunden;
	}
	public void setAnzahlRunden(int anzahlRunden) {
		this.anzahlRunden = anzahlRunden;
	}
	public boolean beendeMatch(Spiel spiel){
		System.out.println("beende Match der Oberklasse wurde aufgerufen!");
		return false;
	}
}
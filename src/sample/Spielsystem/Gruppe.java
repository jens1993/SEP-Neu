package sample;
public class Gruppe extends Spielsystem {
	private int _gruppengroesse;

	@Override
	public boolean beendeMatch(Spiel spiel) {
		return false;
	}
}
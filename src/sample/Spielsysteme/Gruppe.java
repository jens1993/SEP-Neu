package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

public class Gruppe extends Spielsystem {
	private int gruppengroesse;

	@Override
	public boolean beendeMatch(Spiel spiel) {
		return false;
	}
}
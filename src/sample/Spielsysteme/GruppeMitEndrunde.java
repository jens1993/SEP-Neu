package sample.Spielsysteme;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.List;

public class GruppeMitEndrunde extends Gruppe {
	private int anzahlGruppen;
	private int anzahlWeiterkommender;
	private Spielsystem endrunde;

	public GruppeMitEndrunde(List<Team> setzliste, Spielklasse spielklasse) {
		super(setzliste, spielklasse);
	}
}
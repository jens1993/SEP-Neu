package sample.Spielsysteme;

import java.util.List;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

public class KOmitTrostrunde extends KO {
	private int tRbisPlatz;
	private int ausspielenBisPlatz;

	public KOmitTrostrunde(List<Spieler> setzliste, int tRbisPlatz) {
		super(setzliste);
		this.tRbisPlatz = tRbisPlatz;
	}
}
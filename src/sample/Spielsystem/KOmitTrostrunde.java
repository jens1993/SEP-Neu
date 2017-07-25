package sample;

import java.util.List;

public class KOmitTrostrunde extends KO {
	private int tRbisPlatz;
	private int ausspielenBisPlatz;

	public KOmitTrostrunde(List<Spieler> setzliste, int tRbisPlatz) {
		super(setzliste);
		this.tRbisPlatz = tRbisPlatz;
	}
}
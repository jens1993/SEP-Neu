package sample;


public class KO extends Spielsystem {
	private boolean platzDreiAusspielen;
	static int teilnehmerzahl;
	private SpielTree finale = new SpielTree(1, 1, 2);

	public KO(int teilnehmerzahl){
		this.teilnehmerzahl=teilnehmerzahl;
		knotenAufbauen(teilnehmerzahl);
	}

	public SpielTree getFinale() {
		return finale;
	}

	public SpielTree knotenAufbauen (int teilnehmerzahl){
		int anzahlRunden = rundenBerechnen();
		int hoechsterSetzplatz;
		SpielTree aktuellerKnoten = finale;

		for (int i=1; i<anzahlRunden; i++){

			hoechsterSetzplatz = (int) Math.pow(2,i+1);
			System.out.println("Runde:"+(i+1));
			for (int j=1; j<=Math.pow(2,i-1); j++)
			{
				aktuellerKnoten.addLeft(aktuellerKnoten.getSpielID()*2, aktuellerKnoten.getSetzplatzHeim(), hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1);
				aktuellerKnoten.addRight(aktuellerKnoten.getSpielID()*2+1, hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1, aktuellerKnoten.getSetzplatzGast());
				aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale);
			}
		}
	return finale;
	}

	public int rundenBerechnen(){
		setAnzahlRunden((int) Math.ceil(Math.log(teilnehmerzahl) / Math.log(2.0)));
		return getAnzahlRunden();

	}


}
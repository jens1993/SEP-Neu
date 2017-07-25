package sample;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
/**
 * Created by Florian-PC on 25.07.2017.
 */
public class Team {
    private Spieler spielerEins;
    private Spieler spielerZwei;
    private boolean einzel;

    public Team(Spieler spielerEins, Spieler spielerZwei) {
        this.spielerEins = spielerEins;
        this.spielerZwei = spielerZwei;
        this.einzel = false;
    }

    public Team(Spieler spielerEins) {
        this.spielerEins = spielerEins;
        this.einzel = true;
    }

    public Spieler getSpielerEins() {
        return spielerEins;
    }

    public Spieler getSpielerZwei() {
        return spielerZwei;
    }
}

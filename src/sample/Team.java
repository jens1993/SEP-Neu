package sample;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
/**
 * Created by Florian-PC on 25.07.2017.
 */
public class Team {
    private int teamid;
    private Spieler spielerEins;
    private Spieler spielerZwei;
    private Spielklasse spielklasse;
    private boolean einzel;
    private int gewonneneSpiele;
    private int gewonneneSaetze;
    private int verloreneSaetze;
    private int gewonnnenePunkte;
    private int verlorenePunkte;
    private boolean freilos = false;

    public Team(Spieler spielerEins, Spieler spielerZwei, Spielklasse spielklasse) {
        this.spielerEins = spielerEins;
        this.spielerZwei = spielerZwei;
        this.spielklasse = spielklasse;
        this.einzel = false;
    }

    public Team(Spieler spielerEins, Spielklasse spielklasse) {
        this.spielerEins = spielerEins;
        this.einzel = true;
        this.spielklasse = spielklasse;
    }

    public Team(String freilos) {
        this.freilos = true;
    }

    public boolean isFreilos() {
        return freilos;
    }

    public boolean isEinzel() {
        return einzel;
    }

    public Spielklasse getSpielklasse() {
        return spielklasse;
    }

    public Spieler getSpielerEins() {
        return spielerEins;
    }

    public Spieler getSpielerZwei() {
        return spielerZwei;
    }

    public void addGewonnenesSpiel() {
        this.gewonneneSpiele ++;
    }

    public void setGewonneneSaetze(int gewonneneSaetze) {
        this.gewonneneSaetze = this.gewonneneSaetze+ gewonneneSaetze;
    }

    public void addVerloreneSaetze(int verloreneSaetze) {
        this.verloreneSaetze = this.verloreneSaetze + verloreneSaetze;
    }

    public void addGewonnnenePunkt(int gewonnnenePunkt) {
        this.gewonnnenePunkte = this.gewonnnenePunkte + gewonnnenePunkt;
    }

    public void addVerlorenePunkte(int verlorenePunkte) {
        this.verlorenePunkte = this.verlorenePunkte + verlorenePunkte;
    }

    public int getTeamid() {
        return teamid;
    }

    public int getGewonneneSpiele() {
        return gewonneneSpiele;
    }

    public int getGewonneneSaetze() {
        return gewonneneSaetze;
    }

    public int getVerloreneSaetze() {
        return verloreneSaetze;
    }

    public int getGewonnnenePunkte() {
        return gewonnnenePunkte;
    }

    public int getVerlorenePunkte() {
        return verlorenePunkte;
    }

    public String getName(){
        if(this.einzel=true){
            return this.spielerEins.toString();
        }
        else if(this.einzel = false){
            return this.spielerEins.toString() + " / " + this.spielerZwei.toString();
        }
        else
        {
            return "Freilos";
        }
    }

    public int compareTo(Team team){
        if (this.gewonneneSpiele>team.getGewonneneSpiele()){
            return 1;
        }
        else if (this.gewonneneSpiele==team.getGewonneneSpiele()){
            if(this.gewonneneSaetze>team.getGewonneneSaetze()){
                return 1;
            }
            else if(this.gewonneneSaetze>team.getGewonneneSaetze()){
                if(this.gewonnnenePunkte > getGewonnnenePunkte()){
                    return 1;
                }
                else if(this.gewonnnenePunkte==team.getGewonnnenePunkte()){
                    return 0;
                }
                else{
                    return -1;
                }
            }
            else
            {
                return -1;
            }
        }
        else{
            return -1;
        }

    }
}

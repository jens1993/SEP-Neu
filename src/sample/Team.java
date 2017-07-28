package sample;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class Team {
    private TeamDAO teamDAO = new TeamDAOimpl();
    private int teamid;
    private Spieler spielerEins;
    private Spieler spielerZwei;
    private Spielklasse spielklasse;
    private boolean einzel = true;
    private int gewonneneSpiele;
    private int gewonneneSaetze;
    private int verloreneSaetze;
    private int gewonnnenePunkte;
    private int verlorenePunkte;
    private boolean freilos = false;
    private List<Team> bisherigeGegner = new ArrayList<Team>();

    public Team(int teamid, Spieler spielerEins, Spieler spielerZwei, Spielklasse spielklasse) {
        this.teamid = teamid;
        this.spielerEins = spielerEins;
        this.spielerZwei = spielerZwei;
        this.spielklasse = spielklasse;
        this.einzel = false;
    }

    public Team(int teamid, Spieler spielerEins, Spielklasse spielklasse) {
        this.teamid = teamid;
        this.spielerEins = spielerEins;
        this.einzel = true;
        this.spielklasse = spielklasse;
    }

    public void addSpieler(Spieler spieler){
        if (spielerEins==null){
            this.spielerEins = spieler;
        }
        else{
            this.spielerZwei = spieler;
            this.einzel=false;
        }

    }

    public Team(int teamid, Spielklasse spielklasse) { //nur für bestehendes Turnier einlesen
        this.teamid = teamid;
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

    public void addBisherigenGegner(Team team){
        bisherigeGegner.add(team);
    }

    public List<Team> getBisherigeGegner() {
        return bisherigeGegner;
    }

    public void addGewonnenesSpiel() {
        this.gewonneneSpiele ++;
        teamDAO.updateTeam(this);
    }

    public void addGewonnenenSatz() {
        this.gewonneneSaetze ++;
        teamDAO.updateTeam(this);
    }

    public void addVerlorenenSatz() {
        this.verloreneSaetze ++;
        teamDAO.updateTeam(this);
    }

    public void addGespieltePunkte(int gewonnnenePunkte, int verlorenePunkte) {
        this.gewonnnenePunkte = this.gewonnnenePunkte + gewonnnenePunkte;
        this.verlorenePunkte = this.verlorenePunkte + verlorenePunkte;
        teamDAO.updateTeam(this);
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

    public String toString(){
        if(this.einzel==true){
            return this.spielerEins.toString();
        }
        else if(this.einzel == false){
            return this.spielerEins.toString() + " / " + this.spielerZwei.toString();
        }
        else
        {
            return "Freilos";
        }
    }

    public int compareToOld(Team team){
        int satzdifferenzThis = this.gewonneneSaetze-this.verloreneSaetze;
        int punktdifferenzThis = this.gewonnnenePunkte-this.verlorenePunkte;
        int satzdifferenzTeam = team.getGewonneneSaetze()-team.getVerloreneSaetze();
        int punktdifferenzTeam = team.getGewonnnenePunkte()-team.getVerlorenePunkte();
        if (this.gewonneneSpiele>team.getGewonneneSpiele()){
            return -1;
        }
        else if (this.gewonneneSpiele==team.getGewonneneSpiele()){
            if(satzdifferenzThis>satzdifferenzTeam){
                return -1;
            }
            else if(satzdifferenzThis==satzdifferenzTeam){

                if (punktdifferenzThis > punktdifferenzTeam) {
                    return -1;
                } else if (punktdifferenzThis == punktdifferenzTeam) {
                    return 0;
                } else {
                    return 1;
                }

            }
            else
            {
                return 1;
            }
        }
        else{
            return 1;
        }

    }

    public int compareTo(Team team){
        int satzdifferenzThis = this.gewonneneSaetze-this.verloreneSaetze;
        int punktdifferenzThis = this.gewonnnenePunkte-this.verlorenePunkte;
        int satzdifferenzTeam = team.getGewonneneSaetze()-team.getVerloreneSaetze();
        int punktdifferenzTeam = team.getGewonnnenePunkte()-team.getVerlorenePunkte();
        if (this.gewonneneSpiele==team.getGewonneneSpiele()){

            if (satzdifferenzTeam==satzdifferenzThis){
                if(punktdifferenzTeam==punktdifferenzThis){
                    return 0;
                }
                else{
                    return punktdifferenzTeam-punktdifferenzThis;
                }
            }
            else
            {
                return satzdifferenzTeam-satzdifferenzThis;
            }
        }
        else{
            return team.getGewonneneSpiele()-this.gewonneneSpiele;
        }


    }


}

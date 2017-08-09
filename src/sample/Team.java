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



    public Team(Spieler spielerEins, Spieler spielerZwei, Spielklasse spielklasse) {
        this.teamid = teamid;
        this.spielerEins = spielerEins;
        this.spielerZwei = spielerZwei;
        this.spielklasse = spielklasse;
        this.einzel = false;
        teamDAO.create(this);
    }

    public Team(Spieler spielerEins, Spielklasse spielklasse) {
        this.teamid = teamid;
        this.spielerEins = spielerEins;
        this.einzel = true;
        this.spielklasse = spielklasse;
        teamDAO.create(this);
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
    public void setTeamid(int teamid){
        this.teamid = teamid;
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
    }
    public Team(int teamid, Spielklasse spielklasse) { //nur für bestehendes Turnier einlesen
        this.teamid = teamid;
        this.spielklasse = spielklasse;
    }

    public Team(String freilos, Spielklasse spielklasse) {
        this.spielklasse = spielklasse;
        this.freilos = true;
        teamid = getSpielklasse().getTurnier().getTeams().size()+1;
        teamDAO.createFreilos(this);
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
    }

    public Team(String freilos, Spielsystem spielsystem) {
        this.spielklasse = spielsystem.getSpielklasse();
        this.freilos = true;
        teamid = getSpielklasse().getTurnier().getTeams().size()+1;
        teamDAO.createFreilos(this);
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
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

    public TeamDAO getTeamDAO() {
        return teamDAO;
    }

    public List<Team> getBisherigeGegner() {
        return bisherigeGegner;
    }
    public boolean warNochKeinGegner(Team team){
        for(int i=0; i<bisherigeGegner.size();i++){
            if (bisherigeGegner.get(i)==team){
                return false;
            }
        }
        return true;
    }

    public void addGewonnenesSpiel() {
        this.gewonneneSpiele ++;
        //teamDAO.update(this);
    }

    public void addGewonnenenSatz() {
        this.gewonneneSaetze ++;
        //teamDAO.update(this);
    }


    public void addVerlorenenSatz() {
        this.verloreneSaetze ++;
        //teamDAO.update(this);
    }

    public void addGespieltePunkte(int gewonnnenePunkte, int verlorenePunkte) {
        this.gewonnnenePunkte = this.gewonnnenePunkte + gewonnnenePunkte;
        this.verlorenePunkte = this.verlorenePunkte + verlorenePunkte;
        //teamDAO.update(this);
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

        if(this.freilos==true){
            return "Freilos";
        }
        else {
            if(this.einzel==true){
                return this.spielerEins.toString();
            }
            else if(this.einzel == false){
                return this.spielerEins.toString() + " / " + this.spielerZwei.toString();
            }
        }
        return "Fehler";
    }
    public List<Team> getVerbleibendeGegner(ArrayList<Team> teams){
        List<Team> verbleibendeGegner = new ArrayList<>();
        for (int j=0;j<teams.size();j++){
            verbleibendeGegner.add(teams.get(j));
        }
        for(int i=0; i<bisherigeGegner.size();i++){
            verbleibendeGegner.remove(bisherigeGegner.get(i));
        }
        verbleibendeGegner.remove(this);
        return verbleibendeGegner;
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

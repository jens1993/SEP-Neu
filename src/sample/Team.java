package sample;

import javafx.beans.property.SimpleIntegerProperty;
import sample.DAO.*;
import sample.Spielsysteme.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class Team {


    private  SimpleIntegerProperty id ;
    private  SimpleIntegerProperty value_0;
    private  SimpleIntegerProperty value_1;
    private  SimpleIntegerProperty value_2;
    private  SimpleIntegerProperty value_3;
    private  SimpleIntegerProperty value_4;

    Team(int i, int v0, int v1, int v2, int v3,
           int v4) {
        this.id = new SimpleIntegerProperty(i);
        this.value_0 = new SimpleIntegerProperty(v0);
        this.value_1 = new SimpleIntegerProperty(v1);
        this.value_2 = new SimpleIntegerProperty(v2);
        this.value_3 = new SimpleIntegerProperty(v3);
        this.value_4 = new SimpleIntegerProperty(v4);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int v) {
        id.set(v);
    }

    public int getValue_0() {
        return value_0.get();
    }

    public void setValue_0(int v) {
        value_0.set(v);
    }


    public int getValue_1() {
        return value_1.get();
    }

    public void setValue_1(int v) {
        value_1.set(v);
    }

    public int getValue_2() {
        return value_2.get();
    }

    public void setValue_2(int v) {
        value_2.set(v);
    }

    public int getValue_3() {
        return value_3.get();
    }

    public void setValue_3(int v) {
        value_3.set(v);
    }

    public int getValue_4() {
        return value_4.get();
    }

    public void setValue_4(int v) {
        value_4.set(v);
    }



    private TeamDAO teamDAO = new TeamDAOimpl();
    private int teamid;
    private Spieler spielerEins;
    private Spieler spielerZwei;
    private Spielklasse spielklasse;
    private int setzplatznummer=42;
    private boolean einzel = true;
    private int gewonneneSpiele;
    private int gewonneneSaetze;
    private int verloreneSaetze;
    private int gewonnnenePunkte;
    private int verlorenePunkte;
    private boolean freilos = false;
    private List<Team> bisherigeGegner = new ArrayList<Team>();



    public Team(Spielklasse spielklasse) {
        this.spielklasse = spielklasse;
    }

    public Team(Spieler spielerEins, Spieler spielerZwei, Spielklasse spielklasse) {
        this.spielerEins = spielerEins;
        this.spielerZwei = spielerZwei;
        this.spielklasse = spielklasse;
        this.einzel = false;
        teamDAO.create(this);
    }

    public Team(Spieler spielerEins, Spielklasse spielklasse) {
        this.spielerEins = spielerEins;   //teamID
        this.einzel = true;
        this.spielklasse = spielklasse;
        teamDAO.create(this);
    }
    public Team(Spieler spielerEins, Spielklasse spielklasse, Boolean b) {
        this.spielerEins = spielerEins;   //teamID
        this.einzel = true;
        this.spielklasse = spielklasse;

    }
    public void addSpieler(Spieler spieler){
        this.freilos=false;
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
    public void setTeamid(int teamid, Spielklasse sp){
        this.teamid = teamid;
        this.spielklasse=sp;
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
    }
    public Team(int teamid, Spielklasse spielklasse) { //nur für bestehendes Turnier einlesen
        this.teamid = teamid;
        this.spielklasse = spielklasse;
        this.freilos=true;
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
    public boolean istImTeam(Spieler spieler){
        if (spielerEins==spieler){
            return true;
        }
        if(spielerZwei==spieler){
            return true;
        }
        return false;
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
    public String getSetzplatz()
    {

        if(auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzplatzanzeigen(spielerEins,spielerZwei)!="")
        {
            setzplatznummer= Integer.parseInt(auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzplatzanzeigen(spielerEins,spielerZwei));
            return String.valueOf(setzplatznummer);
        }
        else
        {
            return "-1";
        }

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

    public Team()
    {

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

    public String toStringKomplett(){

        if(this.freilos==true){
            return "Freilos";
        }
        else {
            if(this.einzel==true){
                if(this.spielerEins!=null)
                {
                    return this.spielerEins.toString();
                }
                else
                {
                    return null;
                }
            }
            else if(this.einzel == false){
                return this.spielerEins.getVName()+" "+ this.spielerEins.getNName()+" / "+this.spielerZwei.getVName()+" "+ this.spielerZwei.getNName();
            }
        }
        return "Fehler";
    }
    public String toString(){

        if(this.freilos==true){
            return "Freilos";
        }
        else {
            if(this.einzel==true){
                if(this.spielerEins!=null)
                {
                    return this.spielerEins.toString();
                }
                else
                {
                    return null;
                }
            }
            else if(this.einzel == false){
                return this.spielerEins.getNName()+" "+this.spielerEins.getVName().charAt(0)+"." + " / " + this.spielerZwei.getNName()+" "+this.spielerZwei.getVName().charAt(0)+".";
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

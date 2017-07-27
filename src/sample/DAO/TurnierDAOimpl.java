package sample.DAO;

import com.sun.org.apache.bcel.internal.generic.Select;

import sample.*;
import sample.Spielsysteme.Spielsystem;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class TurnierDAOimpl implements TurnierDAO {

    @Override
    public boolean create(Turnier turnier) {

        String sql = "INSERT INTO Turnier("
                + "Datum, "
                + "Name, "
                + "Turnierid)"
                + "VALUES(?,?,?)";

        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setObject(1, turnier.getDatum());
            smt.setString(2, turnier.getName());
            smt.setInt(3, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean delete(Turnier turnier) {
        String sql = "Delete From turnier Where turnierID= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Turnier turnier) {

        String sql = "UPDATE Turnier " +
                "SET MatchDauer = ?, " +
                "Datum = ?, " +
                "Name= ?, " +
                "SpielerPausenZeit = ?, " +
                "Zaehlweise = ? " +
                "WHERE Turnierid = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getMatchDauer());
            smt.setObject(2, turnier.getDatum());
            smt.setString(3, turnier.getName());
            smt.setInt(4, turnier.getSpielerPausenZeit());
            smt.setInt(5, turnier.getZaehlweise());
            smt.setInt(6, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Update Klappt nicht");
        }
        return false;
    }

    @Override
    public Turnier read(int turnierID) {
        Turnier turnier = null;
        String sql = "SELECT NAME, Matchdauer, Gesamtspiele, Datum, SpielerPausenZeit, Zaehlweise FROM turnier WHERE turnierID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnierID);
            ResultSet turnierResult = smt.executeQuery(sql);
            smt.close();
            turnierResult.next();
            turnier = new Turnier(turnierResult.getString("Name"),turnierID, turnierResult.getDate("Datum").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            turnier.setMatchDauer(turnierResult.getInt("MatchDauer"));
            turnier.setSpielerPausenZeit(turnierResult.getInt("SpielerPausenZeit"));
            turnier.setZaehlweise(turnierResult.getInt("Zaehlweise"));
            turnier.setFelder(readFelder(turnier));
            turnier.setSpielklassen(readSpielklassen(turnier));
            turnier.setVereine(readVereine(turnier));
            turnier.setSpieler(readSpieler(turnier));
            turnier.setTeams(readTeams(turnier));
            for(int i=1; i<=turnier.getSpielklassen().size();i++){
                Spielklasse spielklasse = turnier.getSpielklassen().get(i);
                readSetzliste(spielklasse);
            }
            //turnier.setSpiele(readSpiele(turnier));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier lesen klappt nicht");
        }
        return turnier;
    }
    private Dictionary<Integer,Spielklasse> readSpielklassen(Turnier turnier) {
        Dictionary<Integer,Spielklasse> spielklassen = new Hashtable<Integer,Spielklasse>();
        String sql = "SELECT * FROM spielklasse WHERE turnierID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet spielklassenResult = smt.executeQuery(sql);
            smt.close();
            while (spielklassenResult.next()){
                int spielklasseid = spielklassenResult.getInt("SpielklasseID");
                spielklassen.put(spielklasseid,new Spielklasse(spielklasseid, spielklassenResult.getString("Disziplin"),spielklassenResult.getString("Niveau"),turnier));
                spielklassen.get(spielklasseid).setAktiv(spielklassenResult.getBoolean("aktiv"));
                spielklassen.get(spielklasseid).setMeldeKosten(spielklassenResult.getFloat("MeldeKosten"));
                spielklassen.get(spielklasseid).setSpielsystem(readSpielsystem(spielklassen.get(spielklasseid)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spielklasse lesen klappt nicht");
        }
        return spielklassen;
    }
    private List<Team> readSetzliste(Spielklasse spielklasse){
        List<Team> setzliste = new ArrayList<Team>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT COUNT(setzplatz) from spielklasse_setzliste where spielklasseID = "+spielklasse.getSpielklasseID());
            count.next();
            size = count.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Setzplätze Zählen Klappt nicht");
        }
        try {
            Statement st = connection.createStatement();
            ResultSet setzlisteResult = st.executeQuery("SELECT setzplatz,VName,NName,spielklasse_setzliste.teamID FROM spielklasse_setzliste " +
                    "inner JOIN spieler ON spielklasse_setzliste.teamID = team.TeamID " +
                    "WHERE spielklasseID = " + spielklasse.getSpielklasseID() +
                    " ORDER BY setzplatz ;");
            while (setzlisteResult.next()) {
                Team team = spielklasse.getTurnier().getTeams().get(setzlisteResult.getInt("TeamID"));
                setzliste.add(team);
                System.out.println(team);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Setzliste Lesen Klappt nicht");
        }
        return setzliste;
    }
    private Spielsystem readSpielsystem(Spielklasse spielklasse){
        Spielsystem spielsystem = null;


        return spielsystem;
    }
    private Dictionary<Integer,Feld> readFelder(Turnier turnier) {
        Dictionary<Integer, Feld> felder = new Hashtable<Integer,Feld>();

        String sql = "SELECT * FROM feld WHERE turnierID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet feldResult = smt.executeQuery(sql);
            smt.close();
            while (feldResult.next()){
                int feldid = feldResult.getInt("FeldID");
                felder.put(feldid,new Feld(feldResult.getBoolean("ProfiMatte"),feldid,turnier));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Felder lesen klappt nicht");
        }

        return felder;
    }

    private Dictionary<Integer,Verein> readVereine(Turnier turnier) {
        Dictionary<Integer, Verein> vereine = new Hashtable<Integer,Verein>();
        String sql = "SELECT * FROM verein WHERE turnierID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet vereinResult = smt.executeQuery(sql);
            smt.close();
            while (vereinResult.next()){
                int vereinsid = vereinResult.getInt("VereinsID");
                vereine.put(vereinsid,new Verein(vereinsid,vereinResult.getString("ExtVereinsID"),vereinResult.getString("Name"),vereinResult.getString("Verband")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein lesen klappt nicht");
        }


        return vereine;
    }
    private Dictionary<Integer,Spieler> readSpieler(Turnier turnier) {
        Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();

        String sql = "SELECT * FROM spieler " +
                "INNER JOIN team_spieler ON spieler.SpielerID = team_spieler.SpielerID " +
                "INNER JOIN team on team_spieler.TeamID = team.TeamID " +
                "INNER JOIN spielklasse on team.SpielklasseID = spielklasse.SpielklasseID " +
                "where turnierid = ? " +
                "GROUP BY spieler.SpielerID;";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet spielerResult = smt.executeQuery(sql);
            smt.close();
            while (spielerResult.next()){
                int spielerID  = spielerResult.getInt("SpielerID");
                spieler.put(spielerID,new Spieler(spielerResult.getString("VName"),spielerResult.getString("NName"),spielerID));
                spieler.get(spielerID).setgDatum(spielerResult.getDate("GDatum").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                spieler.get(spielerID).setGeschlecht(spielerResult.getBoolean("Geschlecht"));
                spieler.get(spielerID).setVerein(turnier.getVereine().get(spielerResult.getInt("VereinsID")));
                int[] rPunkte = new int[3];
                rPunkte[0] = spielerResult.getInt("RLP_Einzel");
                rPunkte[1] = spielerResult.getInt("RLP_Doppel");
                rPunkte[2] = spielerResult.getInt("RLP_Mixed");
                spieler.get(spielerID).setrPunkte(rPunkte);
                spieler.get(spielerID).setMeldeGebuehren(spielerResult.getFloat("Meldegebuehren"));
                spieler.get(spielerID).setNationalitaet(spielerResult.getString("Nationalitaet"));
                spieler.get(spielerID).setVerfuegbar(spielerResult.getDate("Verfuegbar").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                spieler.get(spielerID).setMattenSpiele(spielerResult.getInt("MattenSpiele"));
                spieler.get(spielerID).setExtSpielerID(spielerResult.getString("ExtSpielerID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spieler lesen klappt nicht");
        }
        return spieler;
    }
    private Dictionary<Integer,Team> readTeams(Turnier turnier) {
        Dictionary<Integer, Team> teams = new Hashtable<Integer,Team>();

        String sql = "select * from team inner join spielklasse on team.SpielklasseID = spielklasse.SpielklasseID where turnierid = ? ";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet teamResult = smt.executeQuery(sql);
            smt.close();
            while (teamResult.next()){
                int teamid = teamResult.getInt("TeamID");
                teams.put(teamid, new Team(teamid,turnier.getSpielklassen().get(teamResult.getInt("SpielklasseID"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Teams lesen klappt nicht");
        }
        String sqlzwei = "select * from team_spieler " +
                "INNER join team ON team_spieler.TeamID = team.TeamID " +
                "INNER JOIN spielklasse on team.SpielklasseID = spielklasse.SpielklasseID " +
                "where turnierid =? ";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smtzwei = con.SQLConnection().prepareStatement(sqlzwei);
            smtzwei.setInt(1, turnier.getTurnierid());
            ResultSet teamSpielerResult = smtzwei.executeQuery(sql);
            smtzwei.close();
            while (teamSpielerResult.next()){
                int spielerid = teamSpielerResult.getInt("SpielerID");
                int teamid = teamSpielerResult.getInt("TeamID");
                Team team = turnier.getTeams().get(teamid);
                Spieler spieler = turnier.getSpieler().get(spielerid);
                team.addSpieler(spieler);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spieler den Teams hinzufügen klappt nicht");
        }



        return teams;
    }
    /*private Dictionary<Integer,Spiel> readSpiele(Turnier turnier) {
        Dictionary<Integer, Spiel> spiele = new Hashtable<Integer,Spiel>();

        String sql = "Select * from spiel INNER JOIN spielklasse on spiel.SpielklasseID = spielklasse.SpielklasseID where turnierid = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet spieleResult = smt.executeQuery(sql);
            smt.close();
            while (spieleResult.next()){
                int spielerID  = spieleResult.getInt("SpielerID");
                spiele.put(spielerID,new Spiel(spieleResult.getString("VName"),spieleResult.getString("NName"),spielerID));
                spiele.get(spielerID).setgDatum(spieleResult.getDate("GDatum").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                spiele.get(spielerID).setGeschlecht(spieleResult.getBoolean("Geschlecht"));
                spiele.get(spielerID).setVerein(turnier.getVereine().get(spieleResult.getInt("VereinsID")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spieler lesen klappt nicht");
        }
        return spiele;
    }*/


}

package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean delete(Turnier turnier) {
        FeldDAO feldDao = new FeldDAOimpl();
        SpielklasseDAO spielklasseDAO = new SpielklasseDAOimpl();
        for (int i=1; i<=turnier.getFelder().size();i++){

        }

        String sql = "Delete From turnier Where turnierID= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            con.closeCon();
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
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Update Klappt nicht");
        }
        return false;
    }

    @Override
    public Turnier read(Turnier turnierEingabe) {

        String sql = "SELECT * FROM turnier WHERE turnierID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnierEingabe.getTurnierid());
            ResultSet turnierResult = smt.executeQuery();
            turnierResult.next();
            //turnier = turnierEingabe;
            turnierEingabe.setMatchDauer(turnierResult.getInt("MatchDauer"));
            turnierEingabe.setSpielerPausenZeit(turnierResult.getInt("SpielerPausenZeit"));
            turnierEingabe.setZaehlweise(turnierResult.getInt("Zaehlweise"));
            turnierEingabe.setFelder(readFelder(turnierEingabe));
            turnierEingabe.setSpielklassen(readSpielklassen(turnierEingabe));
            turnierEingabe.setVereine(readVereine(turnierEingabe));
            turnierEingabe.setSpieler(readSpieler(turnierEingabe));
            turnierEingabe.setTeams(readTeams(turnierEingabe));
            for(int i=1; i<=turnierEingabe.getSpielklassen().size();i++){
                Spielklasse spielklasse = turnierEingabe.getSpielklassen().get(i);
                readSetzliste(spielklasse);
                Spielsystem spielsystem = readSpielsystem(spielklasse);
                if (spielsystem != null){
                    turnierEingabe.getSpielklassen().get(i).setSpielsystem(spielsystem);
                }
            }
            spielListenFuellen(turnierEingabe);
            smt.close();
            con.closeCon();
            //turnier.setSpiele(readSpiele(turnier));
            return turnierEingabe;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier lesen klappt nicht");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Fehler");
        }
        return turnierEingabe;
    }

    private void spielListenFuellen(Turnier turnierEingabe) {
        Enumeration e = turnierEingabe.getSpiele().keys();
        while(e.hasMoreElements()){
            int spielID = (int) e.nextElement();
            Spiel spiel = turnierEingabe.getSpiele().get(spielID);
            if(spiel.getStatus() == 3){
                turnierEingabe.getObs_gespielteSpiele().add(spiel);
            }
            else if (spiel.getStatus()==2){
                turnierEingabe.getObs_aktiveSpiele().add(spiel);

            }
            else if (spiel.getStatus()==1){
                turnierEingabe.getObs_ausstehendeSpiele().add(spiel);
            }
        }
    }

    private Dictionary<Integer,Spielklasse> readSpielklassen(Turnier turnier) {
        Dictionary<Integer,Spielklasse> spielklassen = new Hashtable<Integer,Spielklasse>();
        String sql = "SELECT * FROM spielklasse WHERE turnierID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet spielklassenResult = smt.executeQuery();
            while (spielklassenResult.next()){
                int spielklasseid = spielklassenResult.getInt("SpielklasseID");
                spielklassen.put(spielklasseid,new Spielklasse(spielklasseid, spielklassenResult.getString("Disziplin"),spielklassenResult.getString("Niveau"),turnier));
                spielklassen.get(spielklasseid).setAktiv(spielklassenResult.getBoolean("aktiv"));
                spielklassen.get(spielklasseid).setMeldeKosten(spielklassenResult.getFloat("MeldeKosten"));
                //spielklassen.get(spielklasseid).setSpielsystem(readSpielsystem(spielklassen.get(spielklasseid)));
            }
            smt.close();
            con.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spielklasse lesen klappt nicht");
        }
        return spielklassen;
    }
    private ArrayList<Team> readSetzliste(Spielklasse spielklasse){
        ArrayList<Team> setzliste = new ArrayList<Team>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet setzlisteResult = st.executeQuery("SELECT team.TeamID FROM spielklasse_setzliste " +
                    "inner JOIN team ON spielklasse_setzliste.teamID = team.TeamID " +
                    "WHERE spielklasse_setzliste.spielklasseID = " + spielklasse.getSpielklasseID() +
                    " ORDER BY setzplatz ;");
            while (setzlisteResult.next()) {
                Team team = spielklasse.getTurnier().getTeams().get(setzlisteResult.getInt("TeamID"));
                setzliste.add(team);
            }
            st.close();
            con.closeCon();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Setzliste Lesen Klappt nicht");
        }
        spielklasse.setSetzliste(setzliste);
        return setzliste;
    }
    private Spielsystem readSpielsystem(Spielklasse spielklasse){
        Spielsystem spielsystem = null;
        ArrayList<Spiel> spiele = readSpiele(spielklasse);
        Dictionary<Integer,Ergebnis> ergebnisse = readErgebnisse(spielklasse);
        if (spiele != null) {
            if (spiele.get(0).getSystemSpielID() < 20000000) {
                spielsystem = new Gruppe(spielklasse.getSetzliste(), spielklasse, spiele, ergebnisse);
            } else if (spiele.get(0).getSystemSpielID() < 30000000) {
                spielsystem = new GruppeMitEndrunde(spielklasse.getSetzliste(), spielklasse, spiele, ergebnisse);
            } else if (spiele.get(0).getSystemSpielID() < 40000000) {
                spielsystem = new KO(spielklasse.getSetzliste(), spielklasse, spiele, ergebnisse);
            } else if (spiele.get(0).getSystemSpielID() < 50000000) {
                spielsystem = new KOmitTrostrunde(spielklasse.getSetzliste(), spielklasse, spiele, ergebnisse);
            } else if (spiele.get(0).getSystemSpielID() < 60000000) {
                spielsystem = new SchweizerSystem(spielklasse.getSetzliste(), spielklasse, spiele, ergebnisse);
            }
        }
        return spielsystem;
    }
    private ArrayList<Spiel> readSpiele(Spielklasse spielklasse){
        ArrayList<Spiel> spiele = new ArrayList<>();
        String sql = "SELECT * FROM spiel INNER JOIN spielklasse_spielid on spiel.spielid=spielklasse_spielid.spielid WHERE spiel.spielklasseID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spielklasse.getSpielklasseID());
            ResultSet spielResult = smt.executeQuery();
            while (spielResult.next()){
                int spielid = spielResult.getInt("SpielID");
                int heimid=spielResult.getInt("Heim");
                Team heim = spielklasse.getTurnier().getTeams().get(heimid);
                int gastid=spielResult.getInt("Gast");
                Team gast = spielklasse.getTurnier().getTeams().get(gastid);
                Time date = spielResult.getTime("AufrufZeit");
                LocalTime aufrufzeit = LocalTime.now();
                if(date!=null){
                    aufrufzeit=date.toLocalTime();
                }
                int schiedsrichterid = spielResult.getInt("schiedsrichter");
                Spieler schiedsrichter = null;
                if(schiedsrichterid!=0) {
                    schiedsrichter = spielklasse.getTurnier().getSpieler().get(schiedsrichterid);
                }
                int status = spielResult.getInt("status");
                int systemspielid = spielResult.getInt("SpielklasseSpielID");
                Feld feld = null;
                for (int i=0; i<spielklasse.getTurnier().getFelder().size();i++){
                    if (spielklasse.getTurnier().getFelder().get(i).getFeldID()==spielResult.getInt("Feld")){
                        feld = spielklasse.getTurnier().getFelder().get(i);
                    }
                }
                spiele.add(new Spiel(spielid,heim,gast,aufrufzeit,schiedsrichter,status,systemspielid,feld));

            }
            smt.close();
            con.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiele lesen klappt nicht");
        }
        if (spiele.size()>0) {
            return spiele;
        }
        else{
            return null;
        }
    }
    private Dictionary<Integer,Ergebnis> readErgebnisse(Spielklasse spielklasse){
        Dictionary<Integer, Ergebnis> ergebnisse = new Hashtable<Integer,Ergebnis>();
        Dictionary<Integer, ArrayList<int[]>> satzListe = new Hashtable<>();
        String sql = "SELECT SpielklasseSpielID, Heimpunkte, Gastpunkte FROM spiel_satzergebnis " +
                "Inner join spiel on spiel_satzergebnis.spielID=spiel.spielid " +
                "inner join spielklasse_spielid on spiel.spielid=spielklasse_spielid.SpielID " +
                "where spiel.spielklasseid=?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spielklasse.getSpielklasseID());
            ResultSet ergebnisResult = smt.executeQuery();
            while (ergebnisResult.next()){
                int spielid = ergebnisResult.getInt("SpielklasseSpielID");
                int[] satz = new int[2];
                satz[0] = ergebnisResult.getInt("Heimpunkte");
                satz[1] = ergebnisResult.getInt("Gastpunkte");
                if(satzListe.get(spielid)==null){
                    satzListe.put(spielid,new ArrayList<>());

                }
                satzListe.get(spielid).add(satz);
            }
            smt.close();
            con.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ergebnisse lesen klappt nicht");
        }
        Enumeration keys = satzListe.keys();
        while(keys.hasMoreElements()){
            int key=(int)keys.nextElement();
            ArrayList<int[]> ergebnis = satzListe.get(key);
            int anzahlSaetze = ergebnis.size();
            int[] ergebnisarray = new int[anzahlSaetze*2];
            for (int i=0;i<anzahlSaetze;i++){
                ergebnisarray[i*2]=ergebnis.get(i)[0];
                ergebnisarray[i*2+1]=ergebnis.get(i)[1];
            }
            if(ergebnisarray.length==4){
                ergebnisse.put(key,new Ergebnis(ergebnisarray[0],ergebnisarray[1],ergebnisarray[2],ergebnisarray[3]));
            }
            else if(ergebnisarray.length==6){
                ergebnisse.put(key,new Ergebnis(ergebnisarray[0],ergebnisarray[1],ergebnisarray[2],ergebnisarray[3],ergebnisarray[4],ergebnisarray[5]));
            }
            else if(ergebnisarray.length==8){
                ergebnisse.put(key,new Ergebnis(ergebnisarray[0],ergebnisarray[1],ergebnisarray[2],ergebnisarray[3],ergebnisarray[4],ergebnisarray[5],ergebnisarray[6],ergebnisarray[7]));
            }
            else if(ergebnisarray.length==10){
                ergebnisse.put(key,new Ergebnis(ergebnisarray[0],ergebnisarray[1],ergebnisarray[2],ergebnisarray[3],ergebnisarray[4],ergebnisarray[5],ergebnisarray[6],ergebnisarray[7],ergebnisarray[8],ergebnisarray[9]));
            }
        }
        return ergebnisse;
    }


    private ArrayList<Feld> readFelder(Turnier turnier) {
        ArrayList<Feld> felder = new ArrayList<>();

        String sql = "SELECT * FROM feld WHERE turnierID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet feldResult = smt.executeQuery();
            while (feldResult.next()){
                int feldid = feldResult.getInt("FeldID");
                Spiel aktivesSpiel = turnier.getSpiele().get(feldResult.getInt("aktivesSpiel"));
                Spiel inVorbereitung = turnier.getSpiele().get(feldResult.getInt("inVorbereitung"));
                Feld feld = new Feld(feldid,aktivesSpiel,inVorbereitung,turnier);
                felder.add(feld);
                feld.setFeldnummer(felder.indexOf(feld)+1);
            }
            smt.close();
            con.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Felder lesen klappt nicht");
        }

        return felder;
    }

    private Dictionary<Integer,Verein> readVereine(Turnier turnier) {
        Dictionary<Integer, Verein> vereine = new Hashtable<Integer,Verein>();
        String sql = "SELECT verein.VereinsID, verein.ExtVereinsID, verein.Name,verein.Verband FROM spieler " +
                "inner join team_spieler on spieler.SpielerID = team_spieler.SpielerID " +
                "inner join team on team_spieler.TeamID = team.TeamID " +
                "inner join verein on spieler.Vereinsid = verein.VereinsID " +
                "inner join spielklasse on team.SpielklasseID = spielklasse.SpielklasseID " +
                "where turnierid = ? " +
                "Group by verein.VereinsID";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            ResultSet vereinResult = smt.executeQuery();
            while (vereinResult.next()){
                int vereinsid = vereinResult.getInt("VereinsID");
                vereine.put(vereinsid,new Verein(vereinsid,vereinResult.getString("ExtVereinsID"),vereinResult.getString("Name"),vereinResult.getString("Verband"),"einlesen"));
            }
            smt.close();
            con.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein lesen klappt nicht");
        }


        return vereine;
    }
    @Override
    public Dictionary<Integer,Turnier> getAllTurniere() {
        Dictionary<Integer, Turnier> turnierListe = new Hashtable<Integer,Turnier>();
        String sql ="SELECT * FROM Turnier";

        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            //smt.setInt(1, turnier.getTurnierid());
            ResultSet TurnierResult = smt.executeQuery();
            while (TurnierResult.next()){
                int turnierid  = TurnierResult.getInt("TurnierID");
                String turnierName = TurnierResult.getString("Name");
                Date Datum = TurnierResult.getDate("Datum");
                turnierListe.put(turnierid,new Turnier(turnierName,turnierid, Datum.toLocalDate()));
            }
            smt.close();
            con.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier lesen klappt nicht");
        }


        return turnierListe;
    }

    private Dictionary<Integer,Spieler> readSpieler(Turnier turnier) {
        Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();
        String sql ="SELECT * FROM SPIELER";
        /*String sql = "SELECT * FROM spieler " +
                "INNER JOIN team_spieler ON spieler.SpielerID = team_spieler.SpielerID " +
                "INNER JOIN team on team_spieler.TeamID = team.TeamID " +
                "INNER JOIN spielklasse on team.SpielklasseID = spielklasse.SpielklasseID " +
                "where turnierid = ? " +
                "GROUP BY spieler.SpielerID;";*/
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            //smt.setInt(1, turnier.getTurnierid());
            ResultSet spielerResult = smt.executeQuery();

            while (spielerResult.next()){
                int spielerID  = spielerResult.getInt("SpielerID");
                int[] rPunkte = new int[3];
                rPunkte[0] = spielerResult.getInt("RLP_Einzel");
                rPunkte[1] = spielerResult.getInt("RLP_Doppel");
                rPunkte[2] = spielerResult.getInt("RLP_Mixed");
                LocalDate verfuegbar=LocalDate.now();
                Date geburtstag = spielerResult.getDate("GDatum");
                LocalDate gdatum = LocalDate.now();
                if (geburtstag!=null){
                    gdatum = geburtstag.toLocalDate();
                }
                if(spielerResult.getDate("Verfuegbar")!=null) {
                     verfuegbar = spielerResult.getDate("Verfuegbar").toLocalDate();
                }
                spieler.put(spielerID,new Spieler(spielerResult.getString("VName"),
                        spielerResult.getString("NName"),
                        gdatum,
                        spielerID,spielerResult.getBoolean("Geschlecht"),
                        rPunkte,turnier.getVereine().get(spielerResult.getInt("VereinsID")),
                        spielerResult.getFloat("Meldegebuehren"),
                        spielerResult.getString("Nationalitaet"),
                        verfuegbar,
                        spielerResult.getInt("MattenSpiele"),
                        spielerResult.getString("ExtSpielerID")));
            }
            smt.close();
            con.closeCon();
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
            ResultSet teamResult = smt.executeQuery();
            while (teamResult.next()){
                int teamid = teamResult.getInt("TeamID");
                teams.put(teamid, new Team(teamid,turnier.getSpielklassen().get(teamResult.getInt("SpielklasseID"))));
            }
            smt.close();
            con.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Teams lesen klappt nicht");
        }
        String sqlzwei = "select team.TeamID, team_spieler.spielerID from team_spieler " +
                "INNER join team ON team_spieler.TeamID = team.TeamID " +
                "INNER JOIN spielklasse on team.SpielklasseID = spielklasse.SpielklasseID " +
                "where turnierid =? ";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smtzwei = con.SQLConnection().prepareStatement(sqlzwei);
            smtzwei.setInt(1, turnier.getTurnierid());
            ResultSet teamSpielerResult = smtzwei.executeQuery();
            while (teamSpielerResult.next()){
                int spielerid = teamSpielerResult.getInt("SpielerID");
                int teamid = teamSpielerResult.getInt("TeamID");
                Team team = teams.get(teamid);
                Spieler spieler = turnier.getSpieler().get(spielerid);
                team.addSpieler(spieler);
            }
            smtzwei.close();
            con.closeCon();
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
            while (spieleResult.next()){
                int spielerID  = spieleResult.getInt("SpielerID");
                spiele.put(spielerID,new Spiel(spieleResult.getString("VName"),spieleResult.getString("NName"),spielerID));
                spiele.get(spielerID).setgDatum(spieleResult.getDate("GDatum").toLocalDate());
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

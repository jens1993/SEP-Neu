package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian-PC on 23.07.2017.
 */
public class SetzlisteDAOimpl implements SetzlisteDAO {
    @Override
    public boolean create(int setzplatz, Team team,  Spielklasse spielklasse) {
        try {
            SQLConnection con = new SQLConnection();
            String sql = "INSERT INTO spielklasse_setzliste("
                        + "setzplatz,"
                        + "spielklasseID, "
                        + "teamid "
                        + "VALUES(?,?,?)";

                PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
                smt.setInt(1, setzplatz);
                smt.setInt(2, spielklasse.getSpielklasseID());
                smt.setInt(3, team.getTeamid());
                smt.executeUpdate();
                smt.close();
                System.out.println("Setzliste Einfügen klappt");
                return true;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Einfügen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean delete(int spielklasseid) {
        String sql = "Delete From spielklasse_setzliste Where SpielklasseID= ?";
        try{
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spielklasseid);
            smt.executeUpdate();
            smt.close();
            System.out.println("Setzliste Loeschen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Loeschen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean update(int setzplatz, Team team,  Spielklasse spielklasse) {
        String sql = "UPDATE spielklasse_setzliste "
                + "SET teamid = ? "
                + "WHERE spielklasseID = ? "
                + "AND setzplatz = ?"
                ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, team.getTeamid());
            smt.setInt(2, spielklasse.getSpielklasseID());
            smt.setInt(3, setzplatz);
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Update Klappt nicht");
        }
        return false;
    }

    @Override
    public List<Team> read(int spielklasseID) {
        /*List<Team> setzliste = new ArrayList<Team>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT COUNT(setzplatz) from spielklasse_setzliste where spielklasseID = "+spielklasseID);
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
                    "WHERE spielklasseID = " + spielklasseID +
                    " ORDER BY setzplatz ;");
            for (int i = 1; i <= size; i++) {
                setzlisteResult.next();
                setzliste.add(new Spieler(setzlisteResult.getString(2), setzlisteResult.getString(3),setzlisteResult.getInt(4)));
                System.out.println(setzlisteResult.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Setzliste Lesen Klappt nicht");
        }*/
        return null;
    }
}

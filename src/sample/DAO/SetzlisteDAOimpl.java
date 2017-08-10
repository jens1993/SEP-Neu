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
            String sql = "INSERT INTO spielklasse_setzliste ("
                        + "setzplatz, "
                        + "spielklasseID, "
                        + "TeamID) "
                        + "VALUES (?,?,?)";

                PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
                smt.setInt(1, setzplatz);
                smt.setInt(2, spielklasse.getSpielklasseID());
                smt.setInt(3, team.getTeamid());
                smt.executeUpdate();
                smt.close();
                System.out.println("Setzliste Einfügen klappt");
            con.closeCon();
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
            con.closeCon();
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
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Update Klappt nicht");
        }
        return false;
    }
}

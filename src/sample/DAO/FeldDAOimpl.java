package sample.DAO;

import sample.Feld;
import sample.Spieler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class FeldDAOimpl implements FeldDAO {
    @Override
    public boolean createFeld(Feld feld) {
        String sql = "INSERT INTO feld("
                + "FeldID,"
                + "turnierid, "
                + "ProfiMatte) "
                + "VALUES(?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, feld.getFeldID());
            smt.setInt(2, feld.getTurnier().getTurnierid());
            smt.setBoolean(3, feld.isProfiMatte());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feld Einfügen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean updateFeld(Feld feld) {
        String sql = "UPDATE feld "
                + "SET aktivesSpiel = ?, "
                + "InVorbereitung = ?, "
                + "turnierid = ?, "
                + "ProfiMatte = ? "
                + "WHERE FeldID = ? "
                ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, feld.getAktivesSpiel().getSpielID());
            smt.setInt(2, feld.getInVorbereitung().getSpielID());
            smt.setInt(3, feld.getTurnier().getTurnierid());
            smt.setBoolean(4, feld.isProfiMatte());
            smt.setInt(5, feld.getFeldID());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feld Update Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean deleteFeld(Feld feld) {
        String sql = "Delete From feld Where feldid= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, feld.getFeldID());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feld Loeschen Klappt nicht");
        }
        return false;
    }
}

package sample.DAO;

import sample.Feld;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                + "aktivesSpiel, "
                + "ProfiMatte, "
                + "InVorbereitung) "
                + "VALUES(?,?,?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, feld.getFeldID());
            smt.setInt(2, feld.getTurnier().getTurnierid());
            smt.setInt(3, feld.getAktivesSpiel().getSpielID());
            smt.setBoolean(4, feld.isProfiMatte());
            smt.setInt(5, feld.getInVorbereitung().getSpielID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Feld Einfügen klappt");
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
            System.out.println("Feld Update klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feld Update Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean deleteFeld(Feld feld) {
        return false;
    }

    @Override
    public List<Feld> getAllFelder() {
        return null;
    }
}

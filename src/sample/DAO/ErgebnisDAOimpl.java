package sample.DAO;

import sample.Feld;
import sample.Spiel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Florian-PC on 26.07.2017.
 */
public class ErgebnisDAOimpl implements ErgebnisDAO {
    @Override
    public boolean create(Spiel ergebnis) {
        String sql = "INSERT INTO Spiel_Satzergebnis( " +
                "SpielID, " +
                "HeimPunkte, " +
                "Gastpunkte) " +
                "VALUES(?,?,?)";
        for(int i=1; i< ergebnis.getErgebnis().getErgebnisArray().length/2;i++){
            sql += ", (?,?,?)";
        }
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1,ergebnis.getSpielID());
            smt.setInt(2,ergebnis.getErgebnis().getErgebnisArray()[0]);
            smt.setInt(3,ergebnis.getErgebnis().getErgebnisArray()[1]);
            for(int j=1; j< ergebnis.getErgebnis().getErgebnisArray().length/2;j++){
                smt.setInt(j*3+1,ergebnis.getSpielID());
                smt.setInt(j*3+2,ergebnis.getErgebnis().getErgebnisArray()[j*2]);
                smt.setInt(j*3+3,ergebnis.getErgebnis().getErgebnisArray()[j*2+1]);
            }
            smt.executeUpdate();
            smt.close();


            con.closeCon();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ergebnis Einfügen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean delete(Spiel ergebnis) {
        String sql = "Delete from Spiel_Satzergebnis Where spielid = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, ergebnis.getSpielID());
            smt.executeUpdate();
            smt.close();
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ergebnis Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Spiel ergebnis) {
        String sql = "UPDATE spiel_ergebnis SET "
                +"satz1_heim = ?"
                +", satz1_gast = ?";
        for(int i=1; i< ergebnis.getErgebnis().getErgebnisArray().length/2;i++){
            sql = sql+ ", Satz"+(i+1)+"_heim = ?";
            sql = sql+ ", Satz"+(i+1)+"_gast = ?";
        }
        sql = sql + " WHERE SpielID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            for (int k=0;k<ergebnis.getErgebnis().getErgebnisArray().length;k++)
            {
                smt.setInt(k+1,ergebnis.getErgebnis().getErgebnisArray()[k]);
            }
            smt.setInt(ergebnis.getErgebnis().getErgebnisArray().length+1,ergebnis.getSpielID());
            smt.executeUpdate();
            smt.close();
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ergebnis Update Klappt nicht");
        }
        return false;
    }
}

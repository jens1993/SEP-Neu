package sample.DAO;

import sample.Turnier;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class TurnierDAOimpl implements TurnierDAO {

    @Override
    public boolean create(Turnier turnier) {

        String sql = "Create Turnier " +
                "Datum = ?, " +
                "Name= ?, " +
                "WHERE Turnierid = ?";

        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setDate(1, (Date) turnier.getDatum());
            smt.setString(2, turnier.getName());
            smt.setInt(3, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            System.out.println("Turnier Einfügen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean delete(Turnier turnier) {
        return false;
    }

    @Override
    public boolean update(Turnier turnier) {

        String sql = "UPDATE Turnier " +
                "SET MatchDauer = ?, " +
                "GesamtSpiele = ?, " +
                "Datum = ?, " +
                "Name= ?, " +
                "SpielerPausenZeit = ?, " +
                "Zaehlweise_bis21 = ?, " +
                "Zaehlweise_bis11mit = ?, " +
                "Zaehlweise_bis11ohne = ?, " +
                "WHERE Turnierid = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, turnier.getMatchDauer());
            smt.setString(2, turnier.GesamtSpiele());
            smt.setDate(3, (Date) turnier.getgDatum());
            smt.setBoolean(4, turnier.getGeschlecht());
            if(spieler.getVerein()!=null){
                smt.setInt(5, spieler.getVerein().getVereinsID());
            }
            else{
                smt.setNull(5, Types.INTEGER);
            }
            smt.setInt(6, spieler.getrPunkte()[0]);
            smt.setInt(7, spieler.getrPunkte()[1]);
            smt.setInt(8, spieler.getrPunkte()[2]);
            smt.setFloat(9, spieler.getMeldeGebuehren());
            smt.setString(10, spieler.getNationalitaet());
            smt.setDate(11, (Date) spieler.getVerfuegbar());
            smt.setInt(12, spieler.getMattenSpiele());
            smt.setString(13, spieler.getExtSpielerID());
            if(spieler.getAktuellesSpiel()!=null){
                smt.setInt(14, spieler.getAktuellesSpiel().getSpielID());
            }
            else {
                smt.setNull(14,Types.INTEGER);
            }
            smt.setInt(15, spieler.getSpielerID());
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
        return null;
    }
}

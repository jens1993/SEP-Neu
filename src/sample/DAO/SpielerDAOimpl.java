package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flori on 30.05.2017.
 */
public class SpielerDAOimpl implements SpielerDAO {

    @Override
    public boolean create(Spieler spieler) {

        String sql = "INSERT INTO spieler("
                + "SpielerID,"
                + "VName,"
                + "NName,"
                + "GDatum,"
                + "Geschlecht,"
                + "Verein,"
                + "RLP_Einzel, "
                + "RLP_Doppel, "
                + "RLP_Mixed, "
                + "Nationalitaet, "
                + "ExtSpielerID) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spieler.getSpielerID());
            smt.setString(2, spieler.getvName());
            smt.setString(3, spieler.getnName());
            smt.setDate(4, Date.valueOf(spieler.getgDatum()));
            smt.setBoolean(5, spieler.getGeschlecht());
            smt.setInt(6, spieler.getVerein().getVereinsID());
            smt.setInt(7, spieler.getrPunkte()[0]);
            smt.setInt(8, spieler.getrPunkte()[1]);
            smt.setInt(9, spieler.getrPunkte()[2]);
            smt.setString(10, spieler.getNationalitaet());
            smt.setString(11, spieler.getExtSpielerID());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spieler Einfügen Klappt nicht");
        }


        return false;
    }

    @Override
    public boolean delete(Spieler spieler) {
        String sql = "Delete From spieler Where spielerID= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spieler.getSpielerID());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spieler Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Spieler spieler) {

        String sql = "UPDATE spieler " +
                "SET VName = ?, " +
                "NName = ?, " +
                "GDatum = ?, " +
                "Geschlecht = ?, " +
                "Verein = ?, " +
                "RLP_Einzel = ?, " +
                "RLP_Doppel = ?, " +
                "RLP_Mixed = ?, " +
                "Meldegebuehren = ?, " +
                "Nationalitaet = ?, " +
                "Verfuegbar = ?, " +
                "MattenSpiele = ?, " +
                "ExtSpielerID = ?, " +
                "AktuellesSpiel = ? " +
                "WHERE spielerID = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, spieler.getvName());
            smt.setString(2, spieler.getnName());
            smt.setDate(3, Date.valueOf(spieler.getgDatum()));
            smt.setBoolean(4, spieler.getGeschlecht());
            smt.setInt(5, spieler.getSpielerID());
            smt.setInt(6, spieler.getSpielerID());
            smt.setInt(7, spieler.getSpielerID());
            smt.setInt(8, spieler.getSpielerID());
            smt.setInt(9, spieler.getSpielerID());
            smt.setInt(10, spieler.getSpielerID());
            smt.setInt(11, spieler.getSpielerID());
            smt.setInt(12, spieler.getSpielerID());
            smt.setInt(13, spieler.getSpielerID());
            smt.setInt(14, spieler.getSpielerID());
            smt.setInt(15, spieler.getSpielerID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Update klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update Klappt nicht");
        }


        return false;
    }

    /*@Override
    public Spieler read(int spielerID) {
        String sql = "Select * from spieler Where spielerid=" + spielerID;
        Spieler temp = null;
        try {
            SQLConnection con = new SQLConnection();
            Connection connection = con.SQLConnection();
            Statement st = connection.createStatement();
            ResultSet spielerResult = st.executeQuery(sql);
            spielerResult.next();
            temp = new Spieler(spielerResult.getString(2), spielerResult.getString(3), spielerID);
            System.out.println(spielerResult.getString(2));
            System.out.println("Lesen klappt");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lesen Klappt nicht");
        }
        return temp;

    }*/

    /*@Override
    public List<Spieler> getAllSpieler() {
        List<Spieler> alleSpieler = new ArrayList<Spieler>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT COUNT(SpielerID) from spieler");
            count.next();
            size = count.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spieler Zählen Klappt nicht");
        }
        try {
            Statement st = connection.createStatement();
            ResultSet spielerResult = st.executeQuery("Select VName, NName, SpielerID from spieler");
            for (int i = 1; i <= size; i++) {
                spielerResult.next();
                alleSpieler.add(new Spieler(spielerResult.getString(1), spielerResult.getString(2), spielerResult.getInt(3)));
                System.out.println(spielerResult.getString(2));
                System.out.println("Spielerliste Lesen klappt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spielerliste Lesen Klappt nicht");
        }

        return alleSpieler;
    }

    @Override
    public List<Spieler> getSetzliste(Spielklasse spielklasse) {

        List<Spieler> setzliste = new ArrayList<Spieler>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT * from spieler");
            ResultSetMetaData countMetaData = count.getMetaData();
            size = countMetaData.getColumnCount();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spieler Zählen Klappt nicht");
        }
        for (int i = 1; i <= size; i++) {
            setzliste.add(read(i));
            return null;
        }
        return setzliste;
    }*/
}
package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.sql.*;

/**
 * Created by flori on 30.05.2017.
 */
public class SpielerDAOimpl implements SpielerDAO {

    @Override
    public boolean create(Spieler spieler) {

        String idAbfrage = "Select AUTO_INCREMENT " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = 'turnierverwaltung_neu' " +
                "AND TABLE_NAME = 'Spieler'";


        String sql = "INSERT INTO spieler("
                + "VName,"
                + "NName,"
                + "GDatum,"
                + "Geschlecht,"
                + "Vereinsid,"
                + "RLP_Einzel, "
                + "RLP_Doppel, "
                + "RLP_Mixed, "
                + "Nationalitaet, "
                + "ExtSpielerID) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection con =  SQLConnection.getCon();
            Statement smtID = con.createStatement();
            ResultSet count = smtID.executeQuery(idAbfrage);
            count.next();
            int spielerID = count.getInt(1);
            spieler.setSpielerID(spielerID);
            smtID.close();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setString(1, spieler.getVName());
            smt.setString(2, spieler.getNName());
            smt.setObject(3, spieler.getGDatum());
            smt.setBoolean(4, spieler.getGeschlecht());
            if(spieler.getVerein()!=null){
                smt.setInt(5, spieler.getVerein().getVereinsID());
            }
            else{
                smt.setNull(5, Types.INTEGER);
            }
            smt.setInt(6, spieler.getrPunkte()[0]);
            smt.setInt(7, spieler.getrPunkte()[1]);
            smt.setInt(8, spieler.getrPunkte()[2]);
            smt.setString(9, spieler.getNationalitaet());
            smt.setString(10, spieler.getExtSpielerID());
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
            Connection con =  SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
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
                "VereinsID = ?, " +
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
            Connection con =  SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setString(1, spieler.getVName());
            smt.setString(2, spieler.getNName());
            smt.setObject(3, spieler.getGDatum());
            smt.setBoolean(4, spieler.getGeschlecht());
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
            smt.setObject(11, spieler.getVerfuegbar());
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
            System.out.println("Spieler Update Klappt nicht");
        }


        return false;
    }

    /*@Override
    public Spieler read(int spielerID) {
        String sql = "Select * from spieler Where spielerid=" + spielerID;
        Spieler temp = null;
        try {
            Connection con =  SQLConnection.getCon();
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
        Connection con =  SQLConnection.getCon();
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
    }*/

}
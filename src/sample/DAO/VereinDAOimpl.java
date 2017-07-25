package sample;

import java.sql.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public class VereinDAOimpl implements VereinDAO {
    @Override
    public boolean create(Verein verein) {
        //String sql = "INSERT INTO verein (Name ,Verband, ExtVereinsID, VereinsID) VALUES(?,?,?,?)";

        String sql = "INSERT INTO verein("
                + "name,"
                + "Verband, "
                + "ExtVereinsID, "
                + "VereinsID) "
                + "VALUES(?,?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, verein.getName());
            smt.setString(2, verein.getVerband());
            smt.setString(3, verein.getExtVereinsID());
            smt.setInt(4, verein.getVereinsID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Verein Einfügen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein Einfügen Klappt nicht");
        }


        return false;
    }

    @Override
    public boolean delete(Verein verein) {
        String sql = "Delete From verein Where vereinsid= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, verein.getVereinsID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Verein Loeschen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Verein verein) {
        String sql = "UPDATE verein SET Name = ?,Verband = ?, ExtVereinsID = ? WHERE vereinsID = ?";
        ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, verein.getName());
            smt.setString(2, verein.getVerband());
            smt.setString(3, verein.getExtVereinsID());
            smt.setInt(4, verein.getVereinsID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Verein Update klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein Update Klappt nicht");
        }


        return false;
    }

    @Override
    public Verein read(int vereinsID) {
        String sql = "Select * from verein Where vereinsID="+vereinsID;
        Verein temp = null;
        try {
            SQLConnection con = new SQLConnection();
            Connection connection = con.SQLConnection();
            Statement st = connection.createStatement();
            ResultSet vereinResult = st.executeQuery(sql);
            vereinResult.next();
            temp = new Verein( vereinsID, vereinResult.getString(2), vereinResult.getString(3), vereinResult.getString(4));
            System.out.println(vereinResult.getString(2));
            System.out.println("Verein Lesen klappt");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Verein Lesen Klappt nicht");
        }
        return temp;
    }
}

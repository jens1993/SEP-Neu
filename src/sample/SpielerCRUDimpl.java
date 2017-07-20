package sample;

import javax.xml.bind.SchemaOutputResolver;
import java.sql.*;

/**
 * Created by flori on 30.05.2017.
 */
public class SpielerCRUDimpl implements SpielerCRUD {

    @Override
    public boolean create(Spieler spieler) {

        String sql = "INSERT INTO spieler("
                + "VName,"
                + "NName, "
                + "SpielerID) "
                + "VALUES(?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, spieler.getvName());
            smt.setString(2, spieler.getnName());
            smt.setString(3, String.valueOf(spieler.getSpielerID()));
            smt.executeUpdate();
            smt.close();
            System.out.println("Einfügen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Einfügen Klappt nicht");
        }


        return false;
    }

    @Override
    public boolean delete(Spieler spieler) {
        String sql = "Delete From spieler Where spielerid="+spieler.getSpielerID();
        try {
            SQLConnection con = new SQLConnection();
            con.executeSQL(sql);
            System.out.println("Loeschen klappt");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Spieler spieler) {
        return false;
    }

    @Override
    public Spieler read(int spielerID) {
        String sql = "Select * from spieler Where spielerid="+spielerID;
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

    }

}

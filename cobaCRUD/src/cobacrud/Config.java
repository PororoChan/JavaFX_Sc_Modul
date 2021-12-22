package cobacrud;

import java.sql.*;

/**
 *
 * @author ASUS
 */
public class Config {
    Connection con = null;
    public static Connection koneksi(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mahasiswa","root","");
            return conn;
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

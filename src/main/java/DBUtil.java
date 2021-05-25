import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.9.0.6/sqllab_users?autoReconnect=true&useSSL=false&allowMultiQueries=true";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "dees";


    public Connection createConn(){
        Connection conn = null;
        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

        } catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

}

import java.sql.*;
import java.util.ArrayList;

public class SQLInjection {

    static Connection conn = null;

    DBUtil dbUtil = null;
    SQLInjection(){
        dbUtil = new DBUtil();
        conn = dbUtil.createConn();
    }

    public ArrayList<String> getEmails(){

        ArrayList<String> emails = new ArrayList<>();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String query = "SELECT email FROM credential;";
            // Execute a query
            ResultSet rs = statement.executeQuery(query);
            // Extract data from result set
            while(rs.next()){
                String email = rs.getString("Email");
                emails.add(email);
            }
            rs.close();
            statement.close();
            //conn.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }
//                if (conn != null){
//                    conn.close();
//                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return emails;
    }

    public ArrayList<String> getNamesUnsafe(ArrayList<String> emails){
        ArrayList<String> names = new ArrayList<>();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            for(String email: emails){
                System.out.println("Fetching Names for email id : " + email);
                String query = "Select Name from credential where Email = '" + email + "';";
                // Execute a query
                ResultSet rs = statement.executeQuery(query);

                while (rs.next()){
                    String name = rs.getString("Name");
                    System.out.println("Name is : " + name);
                }
                rs.close();
            }
            statement.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return names;
    }

    public ArrayList<String> getNamesSafe(ArrayList<String> emails){
        ArrayList<String> names = new ArrayList<String>();
        PreparedStatement statement = null;
        try {
            for(String email: emails){
                System.out.println("Fetching Names for email id : " + email);
                String query = "Select * from credential where Email = ? ;";
                statement = conn.prepareStatement(query);

                System.out.println("Before: " + statement.toString());
                statement.setString(1, email);
                System.out.println("After: " + statement);

                ResultSet rs = statement.executeQuery();

                while (rs.next()){
                    String name = rs.getString("Name");
                    System.out.println("Name is : " + name);
                }
                rs.close();
            }
            statement.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return names;
    }

    public ArrayList<String> detectandgetNames(ArrayList<String> emails){
        PreparedStatement statement = null;
        InjectionDetection injectionDetection = new InjectionDetection();
        try {
            for(String email: emails){
                System.out.println("Fetching Names for email id : " + email);
                String query = "SELECT * FROM credential WHERE Email = ? ;";

                String randomized = injectionDetection.randomise(query);

                statement = conn.prepareStatement(randomized);
                statement.setString(1, email);
                System.out.println("Randomized Query: " + statement);

                injectionDetection.detectAttack(statement.toString());
            }
            statement.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        return getNamesSafe(emails);
    }
}
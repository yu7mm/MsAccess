
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MsAccessDatabaseConnectionInJava8 {

    public static void main(String[] args) {

        // variables
        // PROBNI KOMENT
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int x;
        String s;
        double d;

        // Step 1: Loading or registering Oracle JDBC driver class
        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {

            String msAccessDBName = "D:/Java/bazazgrada.mdb";
            // String dbURL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + msAccessDBName + ";DriverID=22;READONLY=true";
            String dbURL = "jdbc:ucanaccess://" + msAccessDBName;

            // Step 2.A: Create and get connection using DriverManager class
            connection = DriverManager.getConnection(dbURL);

            // Step 2.B: Creating JDBC Statement 
            statement = connection.createStatement();

            // Step 2.C: Executing SQL & retrieve data into ResultSet
            String upit = "SELECT * FROM ZGRADE WHERE BRISANO = false AND SKRIVENO = false ORDER BY br";
            resultSet = statement.executeQuery(upit);

            System.out.println("Brojac\tID\tBr\tAdresa\t\t\t\tPIB");
            System.out.println("======\t==\t====\t===============================\t===========");

            // processing returned data and printing into console
            int brojac = 0;
            while (resultSet.next()) {
                brojac++;
                String adresa = srediAdresu(resultSet.getString(9));
                System.out.println(brojac + "\t"
                        + resultSet.getInt(1) + "\t"
                        + resultSet.getString(2) + "\t"
                        + adresa + "\t"
                        + resultSet.getString(13));
            }

            System.out.println("\nPročitano redova: " + brojac);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {

            // Step 3: Closing database connection
            try {
                if (null != connection) {

                    // cleanup resources, once after processing
                    resultSet.close();
                    statement.close();

                    // and then finally close connection
                    connection.close();
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }

    private static String srediAdresu(String adr) {
        String adresa = adr;
        if (adr.length() < 30) {
            for (int i = 0; i < 30 - adr.length(); i++) {
                adresa += " ";
            }
        } else {
            while (adresa.contains("   ")) {
                adresa = adresa.replace("   ", " ");
            }
            if (adr.length() < 30) {
                for (int i = 0; i < 30 - adr.length(); i++) {
                    adresa += " ";
                }
            }
        }
        return adresa;
    }
}

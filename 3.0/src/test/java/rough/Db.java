package rough;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {

	public static void main(String[] args) throws SQLException {
//		String url="jdbc:sqlserver://cname-qadb-mssql-ivydev01-in.ivyops.com";
//		String Un="IvyCPG_BimboPanama_QAHA_QAUser";
//				String pw="fbfbQWfbf89*vv99";
//		Connection connection = DriverManager.getConnection(url, Un, pw);
		Connection connection = null;
		 try {
	            // Connection String
	            String connectionUrl = "jdbc:sqlserver://cname-qadb-mssql-ivydev01-in.ivyops.com;"
	                    + "databaseName=IvyCPG_BimboColumbia01;"
	                    + "user=IvyCPG_BimboColumbia01_QAuser;"
	                    + "password=IvyCPG_BimboColumbia01_QAuserjkiml90hbvrg;"
	                    + "encrypt=false;"; // set true if using SSL

	            // Establish the connection
	            connection = DriverManager.getConnection(connectionUrl);

	            System.out.println("✅ Connected to SQL Server successfully!");

	            // Create a Statement and execute query
	            Statement stmt = connection.createStatement();
	            String query = "select top 50 * from AppData_INT_Sales_Invoice_Header order by sih_id desc";
	            ResultSet rs = stmt.executeQuery(query);

	            while (rs.next()) {
	                System.out.println("Invoice No: " + rs.getString(1));
	            }

	            // Close resources
	            rs.close();
	            stmt.close();
	            connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

}

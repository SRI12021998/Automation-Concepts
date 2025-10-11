package rough;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;

public class passwordupdateto1query {

	public static void main(String[] args) throws SQLException {

		Connection connection = null;
		 try {
	            // Connection String
	            String connectionUrl = "jdbc:sqlserver://cname-qadb-mssql-ivydev01-in.ivyops.com;"
	                    + "databaseName=IvyCPG_BimboColumbia01;"
	                    + "user=IvyCPG_BimboColumbia01_QAuser;"
	                    + "password=IvyCPG_BimboColumbia01_QAuserjkiml90hbvrg;"
	                    + "encrypt=false;"; // set true if using SSL

	         // Establish connection
	            connection = DriverManager.getConnection(connectionUrl);
	            System.out.println("✅ Connected to SQL Server.");

	            // Update Query
	            String query = "update SADM_User_Authentication set sua_password='$2a$05$5LitG/Lgqe1kZCEPTPNB0O/v7GciqOhfSFrgm9tSNGEIdNJXZDHHK', SUA_Password_Modified_Date='2025-06-12 11:45:17.617', SUA_IsReset_Password=0";
	            Statement stmt = connection.createStatement();
	            int rs = stmt.executeUpdate(query);

         
	            System.out.print(rs);
	            stmt.close();
	            connection.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

}

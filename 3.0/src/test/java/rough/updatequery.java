package rough;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;

public class updatequery {

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
	            String query = "update AppData_Transaction_Sequence set TRS_Sequence_No='27507' where TRS_Id=11353";
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

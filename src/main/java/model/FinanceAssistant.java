package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class  FinanceAssistant{
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/egservice_paf", "root", "root");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
	// insert method
	public String insertfinanceassistant(String emp_name, String emp_type, String emp_email) {
		Connection con = connect();
		String output = "";
		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = " insert into financeassistant (`emp_id`,`emp_name`,`emp_type`,`emp_email`)"
				+ " values (?, ?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, emp_name);
			preparedStmt.setString(3, emp_type);
			preparedStmt.setString(4, emp_email);


			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (SQLException e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readfinanceassistant() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading finance assistant.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Employee name</th><th>Employeee Type</th>" + "<th>Employee Email</th></tr>";

			String query = "select * from financeassistant";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String emp_id = Integer.toString(rs.getInt("emp_id"));
				String emp_name = rs.getString("emp_name");
				String emp_type = rs.getString("emp_type");
				String emp_email = rs.getString("emp_email");
				

				// Add into the html table
				output += "<tr><td>" + emp_name + "</td>";
				output += "<td>" + emp_type + "</td>";
				output += "<td>" + emp_email + "</td>";
			}
			con.close();

			output += "</table>";
		}

		catch (Exception e) {
			output = "Error while reading the Finance Assistant.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatefinanceassistant(String emp_id, String emp_name, String emp_type , String emp_email  )

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = " update  financeassistant set emp_name = ? , emp_type = ? , emp_email = ?   where emp_id = ? ";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, emp_name);
			preparedStmt.setString(2, emp_type);
			preparedStmt.setString(3, emp_email);
			

			preparedStmt.setInt(4, Integer.parseInt(emp_id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Finance Assistant.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletefinanceassistant(String emp_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from financeassistant where emp_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(emp_id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Finance Assistant.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
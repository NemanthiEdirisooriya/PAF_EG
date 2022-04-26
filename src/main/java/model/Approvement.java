package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Approvement {

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
	public String insertApprovement(String status, String date, String details, String endorser) {
		Connection con = connect();
		String output = "";
		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = " insert into approvements (`ApproveID`,`ApproveStatus`,`ApproveDate`,`ApproveDetails`,`Endorser`)"
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, status);
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, details);
			preparedStmt.setString(5, endorser);

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";

		} catch (SQLException e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readApprovements() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Approve Status</th><th>Approve Date</th>" + "<th>Approve Details</th>"
					+ "<th> Endorser Type</th></tr>";

			String query = "select * from approvements";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String ApproveID = Integer.toString(rs.getInt("ApproveID"));
				String ApproveStatus = rs.getString("ApproveStatus");
				String ApproveDate = rs.getString("ApproveDate");
				String ApproveDetails = rs.getString("ApproveDetails");
				String Endorser = rs.getString("Endorser");

				// Add into the html table
				output += "<tr><td>" + ApproveStatus + "</td>";
				output += "<td>" + ApproveDate + "</td>";
				output += "<td>" + ApproveDetails + "</td>";
				output += "<td>" + Endorser + "</td>";

			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Approvements .";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateApprovement(String ID, String status, String date, String details, String endorser)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = " update approvements set ApproveStatus= ? , ApproveDate = ? , ApproveDetails = ? , Endorser = ?  where ApproveID = ? ";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, status);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, details);
			preparedStmt.setString(4, endorser);
			preparedStmt.setInt(5, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the product.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteApprovement(String ApproveID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from approvements where ApproveID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ApproveID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
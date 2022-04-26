package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bill {
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
	public String insertBill( String electricity_acc_no, String Meter_reading, String Amount ,String Month ,String owner) {
		Connection con = connect();
		String output = "";
		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = " insert into bill (`bill_no`,`electricity_acc_no`,`Meter_reading`,`Amount`,`Month`,`owner`)"
				+ " values (?, ?, ?, ?, ?,?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, electricity_acc_no);
			preparedStmt.setString(3, Meter_reading);
			preparedStmt.setString(4, Amount);
			preparedStmt.setString(5, Month);
			preparedStmt.setString(6, owner);

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (SQLException e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readBill() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading Customers.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>EG Account No</th><th>Meter Reading </th>"
					+ "<th>Amount</th>"
					+ "<th>Month</th>"
					+"<th>Bill Owner</th></tr>";

			String query = "select * from bill";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String bill_no = Integer.toString(rs.getInt("bill_no"));
				String electricity_acc_no = rs.getString("electricity_acc_no");
				String Meter_reading = rs.getString("Meter_reading");
				String Amount = rs.getString("Amount");
				String Month = rs.getString("Month");
				String owner = rs.getString("owner");

				// Add into the html table
				output += "<tr><td>" + electricity_acc_no + "</td>";
				output += "<td>" + Meter_reading + "</td>";
				output += "<td>" + Amount+ "</td>";
				output += "<td>" + Month + "</td>";
				output += "<td>" + owner + "</td>";
			}
			con.close();

			output += "</table>";
		}

		catch (Exception e) {
			output = "Error while reading the Bill.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	public String updateBill(String bill_no  , String electricity_acc_no, String Meter_reading, String Amount ,String Month ,String owner)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = " update bill set electricity_acc_no = ?,  Meter_reading = ?,  Amount = ?, Month = ? , owner = ? where bill_no = ? ";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, electricity_acc_no);
			preparedStmt.setString(2,Meter_reading);
			preparedStmt.setString(3, Amount);
			preparedStmt.setString(4, Month);
			preparedStmt.setString(5, owner);

			preparedStmt.setInt(6, Integer.parseInt(bill_no));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Bill.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteBill(String bill_no) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from bill where bill_no=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(bill_no));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Bill.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
package model;

import java.sql.*;


public class PowerConsumtion {
	
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/eg_online_system", "root", "pafproject");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String InsertPowerConsumptionDetails(String customerName, String accountNumber, String units, String days) {
		 
		String output = "";
		try {
			Connection con = connect();
			
			if (con == null)
			{return  "Error while connecting to the database for inserting.";}
			
			// create a prepared statement
			String query = "  insert into powerconsumption (`accountNumber`,`customerName`,`units`,`days`)" + " values (?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, accountNumber);
			 preparedStmt.setString(3, customerName); 
			 preparedStmt.setString(4, units); 
			 preparedStmt.setString(5, days);
		
			 
			// execute the statement
			 
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Inserted successfully"; 
		}
		catch (Exception e) {
			 output = "Error while inserting the Customer."; 
			 System.err.println(e.getMessage()); 
		}
		
		return output;
		}

	public String readPwerConsumption() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Customer Name</th><th>Customer Address</th>" +
			 "<th>Phone Number</th>" + 
			 "<th>NIC</th>" +
			 "<th>UserName</th>" +
			 "<th>Update</th><th>Remove</th></tr>";
			 
			 String query = "select * from customer"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 while(rs.next()) {
				 
				 
				 String accountNumber = Integer.toString(rs.getInt("accountNumber")); 
				 String customerName = rs.getString("customerName"); 
				 String units = rs.getString("units"); 
				 String days = rs.getString("days"); 
				
				 
				 // Add into the html table
				 output += "<tr><td>" + accountNumber + "</td>"; 
				 output += "<td>" + customerName + "</td>"; 
				 output += "<td>" + units + "</td>"; 
				 output += "<td>" + days + "</td>";
				 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>" + "<td><form method='post' action='customer.jsp'>"
				 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
				 + "<input name='idcustomer' type='hidden' value='" + accountNumber + "'>" + "</form></td></tr>"; 
			 }
			 con.close(); 
			 // Complete the html table
			 output += "</table>";
		}
		catch(Exception e)
		{
			 output = "Error while reading the customer."; 
			 System.err.println(e.getMessage()); 
		}
		
		return output;
	}
	}
	
	



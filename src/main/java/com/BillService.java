package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Bill;


@Path("/bills")

public class BillService {

	Bill BillObj = new Bill();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBill() {
		return BillObj.readBill();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("bill_no") String bill_no,
			@FormParam("electricity_acc_no") String electricity_acc_no, @FormParam("Meter_reading") String Meter_reading,
			@FormParam("Amount") String Amount,@FormParam("Month") String Month ,@FormParam("owner") String owner ){
		String output = BillObj.insertBill(electricity_acc_no, Meter_reading,Amount,Month,owner);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBill(String billData) {
		// Convert the input string to a JSON object
		JsonObject BillObject = new JsonParser().parse(billData).getAsJsonObject();
		// Read the values from the JSON object
		String bill_no = BillObject.get("bill_no").getAsString();
		String electricity_acc_no = BillObject.get("electricity_acc_no").getAsString();
		String Meter_reading = BillObject.get("Meter_reading").getAsString();
		String Amount = BillObject.get("Amount").getAsString();
		String Month = BillObject.get("Month").getAsString();
		String owner = BillObject.get("owner").getAsString();
		String output = BillObj.updateBill(bill_no, electricity_acc_no, Meter_reading,Amount, Month,owner);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBill(String billData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(billData, "", Parser.xmlParser());

		// Read the value from the element <CustomerID>
		String bill_no = doc.select("bill_no").text();
		String output = BillObj.deleteBill(bill_no);
		return output;
	}

}
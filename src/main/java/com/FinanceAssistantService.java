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


import model.FinanceAssistant;

@Path("/financeassistant")

public class FinanceAssistantService {

	FinanceAssistant FAobj = new FinanceAssistant();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readfinanceassistant() {
		return FAobj.readfinanceassistant();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(@FormParam("emp_id") String emp_id,
			@FormParam("emp_name") String emp_name, @FormParam("emp_type") String emp_type,
			@FormParam("emp_email") String emp_email) {
		String output = FAobj.insertfinanceassistant(emp_name, emp_type, emp_email);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatefinanceassistant(String financeassistantData ) {
		// Convert the input string to a JSON object
		JsonObject FAobject = new JsonParser().parse(financeassistantData).getAsJsonObject();
		// Read the values from the JSON object
		String emp_id = FAobject.get("emp_id").getAsString();
		String emp_name = FAobject.get("emp_name").getAsString();
		String emp_type = FAobject.get("emp_type").getAsString();
		String emp_email = FAobject.get("emp_email").getAsString();
		String output = FAobj.updatefinanceassistant(emp_id, emp_name, emp_type, emp_email);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String financeassistantData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(financeassistantData, "", Parser.xmlParser());

		// Read the value from the element <CustomerID>
		String emp_id = doc.select("emp_id").text();
		String output = FAobj.deletefinanceassistant(emp_id);
		return output;
	}

}
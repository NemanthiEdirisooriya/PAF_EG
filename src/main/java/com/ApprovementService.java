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

import model.Approvement;

@Path("/Approvements")
public class ApprovementService {

	Approvement approvementObj = new Approvement();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readApprovements() {
		return approvementObj.readApprovements();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertApprovement(@FormParam("ApproveStatus") String ApproveStatus,
			@FormParam("ApproveDate") String ApproveDate, @FormParam("ApproveDetails") String ApproveDetails,
			@FormParam("Endorser") String Endorser) {
		String output = approvementObj.insertApprovement(ApproveStatus, ApproveDate, ApproveDetails, Endorser);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProduct(String approvementData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(approvementData).getAsJsonObject();
		// Read the values from the JSON object
		String ApproveID = productObject.get("ApproveID").getAsString();
		String ApproveStatus = productObject.get("ApproveStatus").getAsString();
		String ApproveDate = productObject.get("ApproveDate").getAsString();
		String ApproveDetails = productObject.get("ApproveDetails").getAsString();
		String Endorser = productObject.get("Endorser").getAsString();
		String output = approvementObj.updateApprovement(ApproveID, ApproveStatus, ApproveDate, ApproveDetails,
				Endorser);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteApprovement(String approvementData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(approvementData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String ApproveID = doc.select("ApproveID").text();
		String output = approvementObj.deleteApprovement(ApproveID);
		return output;
	}
}
package javaTest;

import org.testng.annotations.Test;

import chaining.PostClassRoom;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class PayPal {

	public static Response response;
	public static JsonPath jsonPath;
	@Test
	public void paypalPOST() {
		
		RestAssured.baseURI = "https://api.sandbox.paypal.com/v1/catalogs/products/";
		RestAssured.authentication=RestAssured.oauth2("A21AAFIf4qxnuaOkxzBWUwhl-bcqOy_0gOfajeYCd6-_oWG2vFsU4vfeVg7_j313wQQ_Zi9PVtWIZhjzj6989l87yyjjhnrXA");
		response = RestAssured
				.given()

				.log().all()

				.contentType(ContentType.JSON)
				.body("{\r\n" + 
						"  \"name\": \"Test Automation\",\r\n" + 
						"  \"description\": \"By Me in Rest Assured\",\r\n" + 
						"  \"type\": \"SERVICE\",\r\n" + 
						"  \"category\": \"SOFTWARE\",\r\n" + 
						"  \"image_url\": \"https://example.com/streaming.jpg\",\r\n" + 
						"  \"home_url\": \"https://example.com/home\"\r\n" + 
						"}")
				.post();
		
		;
		response.prettyPrint();
		
		jsonPath = response.jsonPath();
		Object productId = jsonPath.get("id");
		
	ValidatableResponse getResponse =	RestAssured
		.given()
		.log()
		.all()
		.when()
		.get(""+productId+"")
		.then()
		.assertThat()
		.statusCode(200)
		;
		
	
	
	}

	private ResponseAwareMatcher<Response> containsString(String type) {
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			return jsonPath.get(type);			
		}else {
			return null;
		}
	}

	public static String getContentOfCategory(Response response, String Category) {
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			return (String) jsonPath.get(Category);			
		}else {
			return null;
		}
	}

	
	
}

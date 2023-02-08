package br.ce.mmeneses.rest;

import static io.restassured.RestAssured.*;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidationException;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SchemaTest {

	@Test
	public void deveValidarSchemaXml() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/usersXml")
		.then()
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))



;
	}
	
	@Test(expected=SAXParseException.class)
	public void naoDeveValidarSchemaXmlInvalido() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/invalidUsersXml")
		.then()
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))



;
	}
	
	@Test(expected=JsonSchemaValidationException.class)
	public void deveValidarSchemaJson() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))



;
	}
}

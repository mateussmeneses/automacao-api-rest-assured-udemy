package br.ce.mmeneses.rest;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	@Test
	public void testOlaMundo() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");

		assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		assertTrue("O status code deveria ser 200", response.statusCode() == 200);
		assertEquals(200, response.statusCode());

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}

	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);

		get("https://restapi.wcaquino.me/ola").then().statusCode(200);

		given()
		// pré condições
		.when()
		// ações a serem realizadas
			.get("https://restapi.wcaquino.me/ola")
		.then()
		// asserções/verificações a serem realizadas
			.statusCode(200);
	}

	@Test
	public void devoValidarBody() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"));
	
	}
	
	
	
}

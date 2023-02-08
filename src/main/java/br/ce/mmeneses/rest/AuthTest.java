package br.ce.mmeneses.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;

public class AuthTest {
	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.body("name", is("Luke Skywalker"))
			
			
;			
	}
	//868957082d4dc304649e9bd9b732b791
	@Test
	public void deveObterUmClima() {
		given()
			.log().all()
			.queryParam("q","Fortaleza,BR")
			.queryParam("appid", "868957082d4dc304649e9bd9b732b791")
			.queryParam("units", "metric")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Fortaleza"))
			.body("coord.lon", is(-38.5247f))
			.body("main.temp", greaterThan(25f))
			;
	}

	
	@Test
	public void naoDeveAcessarSemSenha() {
		given()
		.log().all()
	.when()
		.get("https://restapi.wcaquino.me/basicauth")
	.then()
		.log().all()
		.statusCode(401)
		
		
		
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica1() {
		given()
		.log().all()
	.when()
		.get("https://admin:senha@restapi.wcaquino.me/basicauth")
	.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		
		
		;
	}
	@Test
	public void deveFazerAutenticacaoBasica2() {
		given()
		.log().all()
		.auth().basic("admin", "senha")
	.when()
		.get("https://restapi.wcaquino.me/basicauth")
	.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		
		
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasicaComChallenge() {
		given()
		.log().all()
		.auth().preemptive().basic("admin", "senha")
	.when()
		.get("https://restapi.wcaquino.me/basicauth2")
	.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		
		
		;
	}
		
	@Test
	public void deveFazerAutenticacaoComTokenJWT() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "mateus@testador.com");
		login.put("senha", "123456");
		
		
		// Login API
		// Receber o token
	String token =	given()
		.log().all()
		.body(login)
		.contentType(ContentType.JSON)
	.when()
		.post("https://barrigarest.wcaquino.me/signin")
	.then()
		.log().all()
		.statusCode(200)
		.extract().path("token")
		
		;

		
		// Obter as contas
		given()
			.log().all()
			.header("Authorization", "JWT " + token)
		.when()
			.get("https://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", hasItem("mateus"))
			
			
			;
	}
	
	@Test
	public void deveAcessarAplicacaoWeb() {
		
		//login
	String cookie = given()
		.log().all()
		.formParam("email", "mateus@testador.com")
		.formParam("senha", "123456")
		.contentType(ContentType.URLENC.withCharset("UTF-8"))
	.when()
		.post("https://seubarriga.wcaquino.me/logar")
	.then()
		.log().all()
		.statusCode(200)
		.extract().header("set-cookie")
;
	
	
	cookie = cookie.split("=")[1].split(";")[0];
	System.out.println(cookie)
	;
	
	
		String body = given()
			.log().all()
			.cookie("connect.sid", cookie)
		.when()
			.get("https://seubarriga.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("html.body.table.tbody.tr[0].td[0]", is ("mateus"))
			.extract().body().asString()
	;
		
		System.out.println("-------------------------------");
		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, body);
		System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]" ));
	}
}

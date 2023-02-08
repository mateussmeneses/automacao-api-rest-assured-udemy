package br.ce.mmeneses.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.ContentType;


public class VerbosTest {
	@Test
	public void deveSalvarUsuario() {
		given()
		.contentType("application/json")
		.log().all()
		.body("{	\"name\": \"Jose\", \"age\": 50 	}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201 )
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
			
			
	;}

	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
		.contentType("application/json")
		.log().all()
		.body("{	 \"age\": 50 	}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400 )
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório") )
			
			
	;
	}

	@Test
	public void deveSalvarUsuarioViaXml() {
		given()
		.log().all()
		.contentType(ContentType.XML)
		.body("<user> <name>Jose</name> <age>50</age> </user>")
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201 )
			.body("user.age", is("50"))
			.body("user.name", is("Jose"))
			.body("user.@id", is(notNullValue()) )
			
			
	;
	}

	@Test
	public void deveAlterarUsuario() {
		given()
		.contentType("application/json")
		.log().all()
		.body("{ \"name\": \"Usuario alterado\",	 \"age\": 80 	}")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("salary", is(1234.5678f));
			
	}
	@Test
	public void devoCustomizarUrlParte1() {
		given()
		.contentType("application/json")
		.log().all()
		.body("{ \"name\": \"Usuario alterado\",	 \"age\": 80 	}")
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{id}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("age", is(80))
			.body("salary", is(1234.5678f));
			
	}
	@Test
	public void devoCustomizarUrlParte2() {
		given()
		.contentType("application/json")
		.log().all()
		.body("{ \"name\": \"Usuario alterado\",	 \"age\": 80 	}")
		.pathParam("entidade", "users")
		.pathParam("userId", 1)
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("salary", is(1234.5678f));
			
	}
	@Test
	public void deveDeletarUsuario() {
		given()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204);
	}
	@Test
	public void naoDeveDeletarUsuarioInexistente() {
		given()
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"));
	}
	
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object > params = new HashMap<String,Object >();
		params.put("name", "Usuario via Map");
		params.put("age", 25);
		
		given()
		.contentType("application/json")
		.log().all()
		.body(params)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201 )
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via Map"))
			.body("age", is(25))
			
			
	;}	
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		User user = new User ("Usuario via objeto", 35);
	
		given()
		.contentType("application/json")
		.log().all()
		.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201 )
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via objeto"))
			.body("age", is(35))
			
			
	;
		}
	@SuppressWarnings("deprecation")
	@Test
	public void deveDeserializarObejtoSalvarUsuario() {
		User user = new User ("Usuario deserializado", 35);
	
	User usuarioInserido =	given()
		.contentType("application/json")
		.log().all()
		.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201 )
			.extract().body().as(User.class)
			
	;
	
		System.out.println(usuarioInserido);
		assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertEquals("Usuario deserializado", usuarioInserido.getName());
		assertThat(usuarioInserido.getAge(), is(35));
		
		}
	@Test
	public void deveSalvarESerializarUsuarioViaXmlUsandoObjeto() {
		User user = new User("Usuario Xml", 40);
		
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all() 
			.statusCode(201 )
			.body("user.age", is("40"))
			.body("user.name", is("Usuario Xml"))
			.body("user.@id", is(notNullValue()) )
			
			
	;
}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveDeserializarXmlAoSalvarUsuario() {
		User user = new User("Usuario Xml", 40);
		
		User usuarioInserido = given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all() 
			.statusCode(201 )
		.extract().body().as(User.class)
	
	
	;
		
		assertThat(usuarioInserido.getId(), notNullValue());
		assertThat(usuarioInserido.getName(), is("Usuario Xml"));
		assertThat(usuarioInserido.getAge(), is(40));
		assertThat(usuarioInserido.getSalary(), is(0.0));
}

}

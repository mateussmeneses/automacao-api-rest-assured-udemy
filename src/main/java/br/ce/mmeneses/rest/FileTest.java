package br.ce.mmeneses.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;





public class FileTest {
	@Test
	public void deveObrigarEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)  // Deveria ser 400 Bad Request
			.body("error", is("Arquivo não enviado"))
			
			
			
			;
		
	}
	
	@Test
	public void deveFazerEnvioArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/teste.feature"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) 
			
			
			
			;
		
	}
	
//	@Test
//	public void deveFazerEnvioArquivoGrande() {
//		given()
//			.log().all()
//			.multiPart("arquivo", new File("src/main/resources/teste.feature"))  // tem que ser arquivo de 1mb
//		.when()
//			.post("https://restapi.wcaquino.me/upload")
//		.then()
//			.log().all()
//			.time(lessThan(5000L))
//			.statusCode(413) 
//			
//			
//			
//			;
//		
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveBaixarArquivo() throws IOException{
		byte[] image = given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/download")
		.then()
		//	.log().all()
			.statusCode(200)
			.extract().asByteArray();
			
		File imagem = new File("src/main/resources/file.jpeg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		Assert.assertThat(imagem.length(), lessThan(100000L));

	}
	
	
	
	
}

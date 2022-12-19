package LiveProject;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)

public class ConsumerTest {
 //Headers object 
	Map<String, String> headers = new HashMap<>();
	
	//Resource path
	String resourcePath = "/api/users";
	
	//Generate a contract
	@Pact (consumer = "UserConsumer" , provider = "UserProvider")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		//Add the Headers
		headers.put("Content-Type", "application/json");
		
		//Create JSON body for request and response
		
		DslPart requestResponseBody = new PactDslJsonBody() 
				.numberType("id", 344)
				.stringType("firstName", "Pavithra")
				.stringType("lastName" , "ShivaKumar")
				.stringType("email","PavithraMSP@example.com");
		
		//Write a Fragment to Pact
		
		return builder.given("A request to create a user")
				.uponReceiving("A request to create a user")
				.method("POST")
				.headers(headers)
				.path(resourcePath)
				.body(requestResponseBody)
				
				.willRespondWith()
				.status(201)
				.body(requestResponseBody)
				
				.toPact();
}
	
	@Test
	
	@PactTestFor(providerName = "UserProvider", port = "8080")
	public void consumerTest() {
		//BaseURI
		String requestURI = "http://localhost:8080" +resourcePath;
		
		//RequestBody
		Map<String, Object> reqBody = new HashMap();
		reqBody.put("id", 344);
		reqBody.put("firstName", "Pavithra");
		reqBody.put ("lastName", "ShivaKumar");
		reqBody.put ("email", "PavithraMSP@example.com" );
		
		
		//Generate Response
		
		given().headers(headers).body(reqBody).
		when().post(requestURI).
		then().statusCode(201).log().all();
	}
}
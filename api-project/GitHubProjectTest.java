package LiveProject;


import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHubProjectTest {

	String SshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC0ERA+kPaacglsLMBKFtQ8m6utUKEEc5TQgIdeWgPLp/64H7bpnYaAlXL7CP5w/LoS1T8A0Ukqf2IEUNTFl3YDh2CSZztxGMlcnZIJ3Wm6Lu8k7XAYbAZvD99PgRwPHavBfLK+avuV8Rb33wSJstoxJOsebQaFP/umhh8Y76f8hVOiUk8CVCDaodxi5NKdEImJD4NtheUUaEYa18xfvLK/POTDF/6Skaj7wnsKnwwrJOi2WWRyF3ImAT3u3CoeT1B1Fxu5++qOekHDf9gnYfPmUrkj5P9wM8BlYUB6FHWDH9GPz+nE5Y2g+ir8SpmPCuQlj54qlcm9Kij0p+Lm2WOr \r\n"
			+ "";
	int SshId;
	RequestSpecification rs;
	
	@BeforeClass
	
	public void SetUp() {
		rs = new RequestSpecBuilder()
				.setBaseUri("https://api.github.com").addHeader("Content-Type" , "application/json")
				.addHeader("Authorization", "token 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")
				.build();
	}
	
	@Test 
	public void postTest() {
		Map<String, String> reqBody = new HashMap<>();
		reqBody.put("title" ,"testkey");
		reqBody.put("key", SshKey);
		
		Response resp = given().spec(rs).body(reqBody)
				.when().post("user/keys");
		System.out.println(resp.getBody().asPrettyString());
		resp.then().extract().path("id");
		resp.then().statusCode(201).body("key",equalTo(SshKey));
	}

	@Test
	public void GetTest() {
		Response resp = given().spec(rs).pathParam("keyId",SshId)
				.when().get("/user/keys/{keyID}");
		System.out.println(resp.getBody().asPrettyString());
		SshId= resp.then().extract().path("id");
		resp.then().statusCode(200).body("key", equalTo(SshKey));
		
	}
	
	
	@Test
	public void DeleteTest() {
		Response resp = given().spec(rs).pathParam("keyId",SshId)
				.when().delete("/user/keys/{keyID}");
		System.out.println(resp.getBody().asPrettyString());
				resp.then().statusCode(204);
		
	}
}
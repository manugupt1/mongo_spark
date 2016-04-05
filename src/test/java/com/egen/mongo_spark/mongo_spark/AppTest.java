package com.egen.mongo_spark.mongo_spark;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;

import spark.Spark;

/**
 * Unit test for simple App.
 */

public class AppTest{
	
	private static int PORT;
	private static String IP_ADDRESS;
	private static MongoClient mongoClient;
	private static String MongoDB = "users_egen_test";
	private static MongoDatabase database;

	@BeforeClass
	public static void beforeClass(){
		String[] args = new String[1];
		args[0] = MongoDB;
		App.main(args);
        IP_ADDRESS = "localhost";
        PORT = 27017;
		mongoClient = new MongoClient(new ServerAddress(IP_ADDRESS,PORT));
		mongoClient.setWriteConcern(WriteConcern.SAFE);
		database = mongoClient.getDatabase(MongoDB);
	}
	
	@AfterClass
	public static void afterClass(){
		Spark.stop();
		database.drop();
	}
	
	@Test
	public void createNewUser(){	
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost postRequest = new HttpPost("http://localhost:4567/api/v1/createUser");
			StringEntity input = new StringEntity(FileUtils.readFileToString(new File("test_files/add_test.json")));
			input.setContentType("application/json");
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);
			org.junit.Assert.assertThat(201, org.hamcrest.CoreMatchers.is(response.getStatusLine().getStatusCode()));
			response = httpClient.execute(postRequest);
			org.junit.Assert.assertThat(201, org.hamcrest.CoreMatchers.not(response.getStatusLine().getStatusCode()));
			httpClient.close();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void updateUser(){
		// Test 1 for update user
		try {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost postRequest = new HttpPost("http://localhost:4567/api/v1/createUser");
			StringEntity input = new StringEntity(FileUtils.readFileToString(new File("test_files/update_test.json")));
			input.setContentType("application/json");
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);

					
			HttpPut putRequest = new HttpPut("http://localhost:4567/api/v1/updateUser/1630215c-2608-44b9-aad4-9d56d8aafd4c12-manu");
			input = new StringEntity(FileUtils.readFileToString(new File("test_files/update_test_target.json")));
			input.setContentType("application/json");
			putRequest.setEntity(input);
			response = httpClient.execute(putRequest);
			org.junit.Assert.assertThat(201, org.hamcrest.CoreMatchers.is(response.getStatusLine().getStatusCode()));

			putRequest.reset();
			
			putRequest = new HttpPut("http://localhost:4567/api/v1/updateUser/1630215c-2608-44b9-aad4-9d56d8aafd4c12-manu-123");
			input = new StringEntity(FileUtils.readFileToString(new File("test_files/update_test_target.json")));
			input.setContentType("application/json");
			putRequest.setEntity(input);
			response = httpClient.execute(putRequest);
			org.junit.Assert.assertThat(201, org.hamcrest.CoreMatchers.not(response.getStatusLine().getStatusCode()));
			System.out.println("DONE");
			httpClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
	

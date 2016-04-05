package com.egen.mongo_spark.mongo_spark;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;


public class UserModelService {

	private final MongoDatabase db;
	private final MongoCollection<Document> collection;
	
	public UserModelService(MongoDatabase mongoDatabase){
		this.db = mongoDatabase;
		this.collection = db.getCollection("users");
	}
	
	
//ADD duplicate exception
	public void createNewUser(String userString) throws Exception{
		UserModel user = new Gson().fromJson(userString, UserModel.class);
		Document userDoc = createDocument(user);
		collection.insertOne(userDoc);

	}


	public ArrayList<UserModel> getAllUsers() {
		FindIterable<Document> userDocIterable = collection.find();
		ArrayList<UserModel> users = new ArrayList<UserModel>();
		for (Document document : userDocIterable) {
			UserModel user = new UserModel(document);
			users.add(user);
		}
    	return users;
	}
	
	public UserModel updateUser(String id,String body){
		UserModel userModel = new Gson().fromJson(body, UserModel.class);
		Document userDoc = createDocument(userModel);
		if (userDoc==null){
			return null;
		}
		System.out.println(userModel.getId());
		UpdateResult res = collection.replaceOne(new Document("_id",id),userDoc);
		System.out.println(res);
		if (res.getModifiedCount()==0){
			return null;
		}
		return userModel;
	}
	
	private Document createDocument(UserModel user){
		Document userDoc = new Document();
		userDoc.append("_id", user.getId());
		userDoc.append("firstName", user.getFirstName());
		userDoc.append("lastName", user.getLastName());
		userDoc.append("email", user.getEmail());
		userDoc.append("profilePic", user.getEmail());
		
		//Always update with the server's date
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
		Calendar cal = Calendar.getInstance();
		userDoc.append("dateCreated", format.format(cal.getTimeInMillis()));
		//ID is automatically created, so no need but the model will return it.
		
		Document userAddress = new Document();
		userAddress.append("street", user.getAddress().getStreet());
		userAddress.append("city", user.getAddress().getCity());
		userAddress.append("state", user.getAddress().getState());
		userAddress.append("zip", user.getAddress().getCity());
		userAddress.append("country", user.getAddress().getCountry());
		
		Document userCompany = new Document();
		userCompany.append("name", user.getCompany().getName());
		userCompany.append("website", user.getCompany().getWebsite());
		
		userDoc.append("company", userCompany);
		userDoc.append("address", userAddress);

		return userDoc;
		
	}




}

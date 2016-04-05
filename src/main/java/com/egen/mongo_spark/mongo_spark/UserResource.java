package com.egen.mongo_spark.mongo_spark;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;


public class UserResource {
	private static final String API_CONTEXT = "/api/v1";
	
	private final UserModelService userModelService;
	
	public UserResource(UserModelService userModelService){
		this.userModelService = userModelService;
		setupEndPoints();
	}
	
	private void setupEndPoints(){
		post(API_CONTEXT+"/createUser","application/json",(request,response)->{
			try{
				userModelService.createNewUser(request.body());
				response.status(201);
			}catch(Exception e){
				response.status(412);
			}
			return response;
		});
		
		get(API_CONTEXT+"/getAllUsers","application/json",(request,response)-> userModelService.getAllUsers(), new JsonTransformer());
		
		put(API_CONTEXT+"/updateUser/:id","application/json",(request,response)-> {
			System.out.println(request.params(":id"));
			UserModel user = userModelService.updateUser(request.params(":id"), request.body());
			if (user != null){
				response.status(201);
				return user;
			}
			response.status(404);
			return null;
		}, new JsonTransformer());
	}
}

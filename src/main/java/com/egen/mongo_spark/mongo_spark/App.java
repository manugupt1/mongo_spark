package com.egen.mongo_spark.mongo_spark;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 27017;
	private static MongoClient mongoClient;
	private static String MongoDB = "users_egen";

	
	public static void main( String[] args )
    {
    	//Make sure that while testing you are not working on a real database
		if (args.length> 0 && args[0].equals("users_egen_test")){
			MongoDB = args[0];

		}
		

        try{
        	new UserResource(new UserModelService(mongo()));
        }catch(Exception e){
        	System.out.println("Exception");
        }
        
    }
	
	private static MongoDatabase mongo() throws Exception{
        MongoClientOptions settings = MongoClientOptions.builder()
        		.codecRegistry(com.mongodb.MongoClient.getDefaultCodecRegistry())
        		.build();
        
		mongoClient = new MongoClient(new ServerAddress(IP_ADDRESS,PORT),settings);
		mongoClient.setWriteConcern(WriteConcern.SAFE);
		MongoDatabase database = mongoClient.getDatabase(MongoDB);
		return database;

		
	}
}

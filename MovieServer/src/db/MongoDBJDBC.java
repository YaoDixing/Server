package db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by yaodixing on 2016/12/6.
 */
public class MongoDBJDBC {

    public static MongoDatabase conn(String ip,int port,String dbName,CodecRegistry codecRegistry){
        try {
            MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();
            ServerAddress serverAddress = new ServerAddress(ip,port);
            MongoClient mongoClient = new MongoClient(serverAddress,options);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName).withCodecRegistry(codecRegistry);
            System.out.println("Connect to database successfully");
            return mongoDatabase;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return null;
    }
}

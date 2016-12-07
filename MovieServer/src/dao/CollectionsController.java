package dao;

import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import mongoexception.MongoExceptionHandler;
import org.bson.BSON;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodixing on 2016/12/6.
 */

public class CollectionsController {
    private MongoDatabase database;
    private String collectionName;
    private CURDController curdController;
    public CollectionsController(MongoDatabase database,String collectionName,CodecRegistry codecRegistry){
        this.database =  database;
        this.collectionName =  collectionName;
        curdController = new CURDController();
        try {
            curdController.setDbCollection(get(collectionName,codecRegistry)) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void create(String collectionName){
        try {
            database.createCollection(collectionName);
        }   catch (MongoException e){
            MongoExceptionHandler.handlerMongoException(e);
        }

    }
    public void create(String collectionName ,CreateCollectionOptions collectionOptions){
        database.createCollection(collectionName,collectionOptions);
    }

    public void insertOne(Document document){
        curdController.insert(document);
    }

    public void insertMany(List<Document> documents){
        curdController.insert(documents);
    }

    public List<Document> find(){
      MongoCursor mongoCursor =  curdController.find();
        if(mongoCursor!=null){
            List<Document>  list = new ArrayList<Document>();
            while (mongoCursor.hasNext()){
                list.add(((Document) mongoCursor.next()));
            }
            return list;
        }else {
            return null;
        }
    }

    public List<Document> find(Bson bson){
        MongoCursor cursor = curdController.find(bson);
        if(cursor!=null){
            List<Document> documents = new ArrayList<Document>();
            while (cursor.hasNext()){
                documents.add(((Document) cursor.next()));
            }
            return documents;
        }   else {
            return null;
        }
    }

    public MongoCollection<Document> get(String collectionName,CodecRegistry codecRegistry){
        try{
           MongoCollection<Document>  mongoCollection =  database.getCollection(collectionName).withCodecRegistry(codecRegistry);
            return mongoCollection;
        }   catch (Exception e){
            System.err.println(e.getClass().getName()+":"+e.getMessage());
        }
        return null;
    }

    class CURDController{
        private MongoCollection<Document> dbCollection;

        public void setDbCollection(MongoCollection<Document> dbCollection){
            this.dbCollection = dbCollection;
        }

        /**
         * 插入一条文档记录
         * @param document
         */
        void insert(Document document){
            try {
                dbCollection.insertOne(document);
            }   catch (MongoException e){
                MongoExceptionHandler.handlerMongoException(e);
            }
        }

        /**
         * 插入多条文档记录
         * @param documents
         */
        void insert(List<Document> documents){
            try {
                dbCollection.insertMany(documents);
            }   catch (MongoException e){
                MongoExceptionHandler.handlerMongoException(e);
            }
        }

        /**
         * 查询集合中的全部文档
         * @return
         */
        MongoCursor find(){
            try {
                FindIterable findIterable =  dbCollection.find();
                MongoCursor cursor = findIterable.iterator();
                return cursor;
            }   catch (MongoException e){
                MongoExceptionHandler.handlerMongoException(e);
            }
            return null;
        }

        MongoCursor find(Bson bson){
            try {
                FindIterable findIterable = dbCollection.find(bson);
                MongoCursor cursor = findIterable.iterator();
                return cursor;
            } catch (MongoException e) {
                MongoExceptionHandler.handlerMongoException(e);
            }
            return null;
        }
    }
}

package dbuse;

import logging.Log;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.JsonSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class MongoConnection implements MongoDbConnection<Log> {
    private MongoClient mongo;
    private MongoDatabase db;
    private MongoCursor<Document> cursor;
    private JsonSerializer<Log> serializer;
    private String url;
    public MongoConnection(JsonSerializer<Log> serializer,String url) {
        this.serializer = serializer;
    }




    public void connect(){
        mongo = new MongoClient(url);
        db = mongo.getDatabase("LogsDB");
    }

    public List<String> getIpbyURL(String url){
        ArrayList<String> IpAdrresses = new ArrayList<>();
        try{
            connect();
            MongoCollection logs = db.getCollection("logged");
            cursor = logs
                .find(eq("URL", url))
                .projection(fields(include("IP"), excludeId()))
                .sort(new Document("IP", 1)).iterator();
            while (cursor.hasNext()){
                String doc = cursor.next().toJson();
                IpAdrresses.add(doc);
            }
        }catch (MongoException ex){
            return null;
        }
        return IpAdrresses;
    }
    public List<String> getURLbyPeriod(Timestamp from,Timestamp to){
        ArrayList<String> IpAdrresses = new ArrayList<>();
        try{
            connect();
            MongoCollection logs = db.getCollection("logged");
            cursor = logs
                    .find(and(gte("timeStamp", from), lte("timeStamp", to)))
                    .projection(fields(include("URL"), excludeId()))
                    .sort(new Document("URL", 1)).iterator();
            while (cursor.hasNext()){
                String doc = cursor.next().toJson();
                IpAdrresses.add(doc);
            }
        }catch (MongoException ex){
            return null;
        }
        return IpAdrresses;
    }
    public List<String> getURLbyIP(String ip){
        ArrayList<String> IpAdrresses = new ArrayList<>();
        try{
            connect();
            MongoCollection logs = db.getCollection("logged");
            cursor = logs
                    .find(eq("IP", ip))
                    .projection(fields(include("URL"), excludeId()))
                    .sort(new Document("URL", 1)).iterator();
            while (cursor.hasNext()){
                String doc = cursor.next().toJson();
                IpAdrresses.add(doc);
            }
        }catch (MongoException ex){
            return null;
        }
        return IpAdrresses;

    }
    @Override
    public boolean create(Log crObj) {
        try{
        connect();
        MongoCollection logs = db.getCollection("logged");
        Document doc = new Document();
        doc.append("_id",crObj.get_id());
        doc.append("URL",crObj.getURL());
        doc.append("IP",crObj.getIP());
        doc.append("timeStamp",crObj.getTimeStamp());
        doc.append("timeSpent",crObj.getTimeSpent());
        logs.insertOne(doc);
        }catch (MongoException ex){
            return false;
        }finally {
            mongo.close();
        }
        return true;
    }
    @Override
    public List<Log> readAll() {
        ArrayList<Log> result = new ArrayList<>();
        try{
            connect();
            MongoCollection logs = db.getCollection("logged");
            cursor = logs.find().iterator();
            while (cursor.hasNext()){
                Log log = new Log();
                Document doc= cursor.next();
                log.set_id(doc.getString("_id"));
                log.setTimeSpent(doc.getLong("timeSpent"));
                log.setTimeStamp(Timestamp.from(doc.getDate("timeStamp").toInstant()));
                log.setURL(doc.getString("url"));
                log.setIP(doc.getString("IP"));
                result.add(log);
            }
        }catch (MongoException ex){
            return null;
        }finally {
            mongo.close();
        }
        return result;
    }

    @Override
    public boolean update(Log updObj) {
        try{
        connect();
        MongoCollection logs = db.getCollection("logged");
        cursor = logs.find(eq("_id",updObj.get_id())).iterator();
        String old = cursor.next().toJson();
        Document oldDoc = Document.parse(old);
        Document doc = new Document();
            doc.append("_id",updObj.get_id());
            doc.append("URL",updObj.getURL());
            doc.append("IP",updObj.getIP());
            doc.append("timeStamp",updObj.getTimeStamp());
            doc.append("timeSpent",updObj.getTimeSpent());
        logs.updateOne(oldDoc,new Document("$set",doc));
    }catch (MongoException ex){
        return false;
    }finally {
        mongo.close();
    }
        return true;
    }
    public boolean removeAll(){
        connect();
        MongoCollection collection = db.getCollection("logged");
        collection.drop();
        mongo.close();
        return true;
    }
    @Override
    public boolean delete(Log delObj) {
        try {
            connect();
            MongoCollection logs = db.getCollection("logged");
            cursor = logs.find(eq("_id",delObj.get_id())).iterator();
            BasicDBObject doc = BasicDBObject.parse(cursor.next().toJson());
            logs.deleteOne(doc);
        }catch (MongoException ex){
            return false;
        }finally {
            mongo.close();
        }
        return true;
    }
    public ArrayList<String> getMapReducedUrlTime() {
        connect();
        ArrayList<String> res = new ArrayList<>();
        MongoCollection logs = db.getCollection("logged");
        String collectionName = "url_timespent";
        String mapFunc = "function (){emit(this.URL, this.timeSpent);}";
        String reduceFunc = "function(keyUrl, valuesTime) {return Array.sum(valuesTime);}";
        logs.mapReduce(mapFunc, reduceFunc).collectionName(collectionName).toCollection();
        cursor = db.getCollection(collectionName).find()
                .sort(new Document("value", -1)).iterator();
        String docJson = "";
        while (cursor.hasNext()) {
            docJson = cursor.next().toJson()
                    .replace("_id", "url")
                    .replace("value", "timespent")
                    ;
            res.add(docJson);
        }
        cursor.close();
        mongo.close();
        return res;
    }

    public ArrayList<String> getMapReducedUrlCount() {
        connect();
        ArrayList<String> res = new ArrayList<>();
        MongoCollection logs = db.getCollection("logged");
        String collectionName = "url_count";
        String mapFunc = "function (){emit(this.URL, 1);}";
        String reduceFunc = "function(keyUrl, valuesCount) {return Array.sum(valuesCount);}";
        logs.mapReduce(mapFunc, reduceFunc).collectionName(collectionName).toCollection();
        cursor = db.getCollection(collectionName).find()
                .sort(new Document("value", -1)).iterator();
        String docJson = "";
        while (cursor.hasNext()) {
            docJson = cursor.next().toJson()
                    .replace("_id", "url")
                    .replace("value", "count");
            res.add(docJson);
        }
        cursor.close();
        mongo.close();
        return res;
    }

    public ArrayList<String> getMapReducedUrlCountByPeriod(Timestamp fromTime, Timestamp toTime) {
        connect();
        ArrayList<String> res = new ArrayList<>();
        MongoCollection logs = db.getCollection("logs");
        String collectionName = "url_count_period";
        String mapFunc = "function (){var ticks = this.timeStamp.getTime(); if((ticks >= "
                + fromTime.getTime() + ") && (ticks <= "
                + toTime.getTime() + "))emit(this.URL, 1);}";
        String reduceFunc = "function(keyUrl, valuesCount) {return Array.sum(valuesCount);}";
        logs.mapReduce(mapFunc, reduceFunc).collectionName(collectionName).toCollection();
        cursor = db.getCollection(collectionName).find()
                .sort(new Document("_id", 1))
                .sort(new Document("value", -1)).iterator();
        String docJson = "";
        while (cursor.hasNext()) {
            docJson = cursor.next().toJson()
                    .replace("_id", "url")
                    .replace("value", "count");
            res.add(docJson);
        }
        cursor.close();
        mongo.close();
        return res;
    }

    public ArrayList<String> getMapReducedIpCountAndTime() {
        connect();
        ArrayList<String> res = new ArrayList<>();
        MongoCollection logs = db.getCollection("logged");
        String collectionName = "ip_by_urlCount_urlTime";
        String mapFunc = "function (){emit(this.IP, this.timeSpent);}";
        String reduceFunc = "function(keyUrl, values) "
                + "{return {count:values.length, timespent:Array.sum(values)}; }";
        logs.mapReduce(mapFunc, reduceFunc).collectionName(collectionName).toCollection();
        cursor = db.getCollection(collectionName).find()
                .sort(new Document("_id", 1))
                .sort(new Document("value", -1))
                .iterator();
        String docJson = "";
        while (cursor.hasNext()) {
            docJson = cursor.next().toJson()
                    .replace("_id", "ip")
                    .replace("$numberLong", "timespent")
                    .replace("\"timespent\" : \"", "\"timespent\" : ")
                    .replace("\" } }", " }");
            res.add(docJson);
        }
        cursor.close();
        mongo.close();
        return res;
    }
}

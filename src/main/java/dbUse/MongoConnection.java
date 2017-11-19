package dbUse;

import Logging.Log;
import com.mongodb.MongoClient;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import javax.swing.text.DefaultStyledDocument;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class MongoConnection {
    private MongoClient mongo;
    private MongoDatabase db;
    private MongoCursor<Document> cursor;
    public void connect(){
        mongo = new MongoClient("localhost");
        db = mongo.getDatabase("LogsDB");
    }

    public void insertLog(Log log) {
        connect();
        MongoCollection logs = db.getCollection("logs");
        Document doc = new Document();
        doc.append("URL",log.getURL());
        doc.append("IP",log.getIP());
        doc.append("timeStamp",log.getTimeStamp());
        doc.append("timeSpent",log.getTimeSpent());
        logs.insertOne(doc);
        mongo.close();
    }
    public void updateLog(Log changedLog){

    }
    public ArrayList<String> getIpByUrl(String urlString) {
        connect();
        ArrayList<String> res = new ArrayList<>();
        MongoCollection logs = db.getCollection("logs");
        cursor = logs
                .find(eq("url", urlString))
                .projection(fields(include("ip"), excludeId()))
                .sort(new Document("ip", 1)).iterator();
        String docJson = "";
        while (cursor.hasNext()) {
            docJson = cursor.next().toJson();
            res.add(docJson);
            System.out.println(docJson);
        }
        cursor.close();
        mongo.close();
        return res;
    }
    public List<Log> getLogByIP(String IP){
        return null;
    }
    public List<Log> getLogByURL(String URL){
        return null;
    }
    public List<Log> getAllLogs(){
        return null;
    }

}

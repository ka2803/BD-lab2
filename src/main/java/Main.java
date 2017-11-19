import Logging.Log;
import dbUse.MongoConnection;
import util.GsonSerializer;

import java.sql.Timestamp;

public class Main {
    public static void main(String[] args){
        Log log = new Log("qwe.com","localhost",new Timestamp(120),130);
        MongoConnection conn = new MongoConnection();
        conn.connect();
        conn.insertLog(log);


    }
}

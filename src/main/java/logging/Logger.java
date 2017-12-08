package logging;

import dbuse.MongoConnection;
import dbuse.MongoDbConnection;

import java.sql.Timestamp;

public class Logger {
    private final String prefix;
    private MongoConnection conn;
    public Logger(String logPrefix, MongoDbConnection<Log> connection){
        prefix=logPrefix;
        conn= (MongoConnection) connection;
    }

    public void writeLog(String text){
        conn.create(new Log("url_example"+prefix,"my IP adress",new Timestamp(10000),500));
    }
}

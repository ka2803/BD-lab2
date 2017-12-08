import logging.Log;
import logging.Logger;
import dbuse.MongoConnection;
import org.junit.Before;
import org.junit.Test;
import util.GsonSerializer;

import java.sql.Timestamp;

public class LoggerTest {
    Logger logger;


    @Before
    public void Setup(){
        logger = new Logger("prefix", new MongoConnection(new GsonSerializer<>(Log.class),"localhost"));
    }

    @Test
    public void writeTest(){
        logger.writeLog(new Log("ads","asd",new Timestamp(2),2).toString());
    }
}

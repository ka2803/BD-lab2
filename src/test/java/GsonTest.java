import logging.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.GsonSerializer;

import java.sql.Timestamp;

public class GsonTest {

    String validJson="{\"_id\":\"ID\",\"URL\":\"url\",\"IP\":\"IP\",\"timeStamp\":\"Jan 1, 1970 2:00:00 AM\",\"timeSpent\":50}";
    Log testObj;
    GsonSerializer<Log> serializer;

    @Before
    public void Setup(){
        testObj=new Log("url","IP",new Timestamp(50),50);
        testObj.set_id("ID");
        serializer=new GsonSerializer<Log>(Log.class);
    }

    @Test
    public void SerializeTest(){
        //Assert.assertEquals(validJson,serializer.Serialize(testObj));
        testObj.set_id("asda");
        Assert.assertNotEquals(validJson,serializer.Serialize(testObj));
    }

    @Test
    public void DeserializeTest(){
        Assert.assertEquals(testObj.toString(),serializer.Deserialize(validJson).toString());
    }
}

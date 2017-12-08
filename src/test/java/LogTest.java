import Logging.Log;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class LogTest {
    @Test
    public void gettersTest(){
        Log testingObject = new Log("urla","IP",new Timestamp(10),10);
        Assert.assertEquals(true,!testingObject.get_id().isEmpty());
        Assert.assertEquals("urla",testingObject.getURL());
        Assert.assertNotEquals("asdsadaadurla",testingObject.getURL());
        Assert.assertEquals("IP",testingObject.getIP());
        Assert.assertNotEquals("asdsadaadurla",testingObject.getIP());
        Assert.assertEquals(new Timestamp(10),testingObject.getTimeStamp());
        Assert.assertNotEquals(new Timestamp(0),testingObject.getTimeStamp());
        Assert.assertEquals(10,testingObject.getTimeSpent());
        Assert.assertNotEquals(0,testingObject.getTimeSpent());
    }
    @Test
    public void settersTest(){
        Log testingObject = new Log("urla","IP",new Timestamp(10),10);
        testingObject.set_id("aydi");
        testingObject.setIP("IP");
        testingObject.setURL("urla");
        testingObject.setTimeSpent(10);
        testingObject.setTimeStamp(new Timestamp(10));
        Assert.assertEquals(true,!testingObject.get_id().isEmpty());
        Assert.assertEquals("urla",testingObject.getURL());
        Assert.assertNotEquals("asdsadaadurla",testingObject.getURL());
        Assert.assertEquals("IP",testingObject.getIP());
        Assert.assertNotEquals("asdsadaadurla",testingObject.getIP());
        Assert.assertEquals(new Timestamp(10),testingObject.getTimeStamp());
        Assert.assertNotEquals(new Timestamp(0),testingObject.getTimeStamp());
        Assert.assertEquals(10,testingObject.getTimeSpent());
        Assert.assertNotEquals(0,testingObject.getTimeSpent());
    }
}

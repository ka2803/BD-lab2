import logging.Log;
import dbuse.MongoConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.GsonSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MongoConnectionTest {
    MongoConnection connTestObject;
    List<Log> logsForTest;


    @Before
    public void Setup(){
        connTestObject = new MongoConnection(new GsonSerializer<>(Log.class),"localhost");
        logsForTest=new ArrayList<>();
        Log log = new Log("google.com","102.14.15.100",new Timestamp(1000000),800);
        logsForTest.add(log);
        log = new Log("facebook.com","10.22.98.100",new Timestamp(1000222),800);
        logsForTest.add(log);
        log = new Log("youtube.com","192.1.15.25",new Timestamp(500000),300);
        logsForTest.add(log);
        log = new Log("twitter.com","100.9.8.78",new Timestamp(800000),200);
        logsForTest.add(log);
        log = new Log("ecampus.kpi.us","1.1.1.1",new Timestamp(2000000),2800);
        logsForTest.add(log);
    }

    @Test
    public void insertTest(){
        for (Log log :
                logsForTest) {
            connTestObject.create(log);
        }
        List<Log>logsAfterTest = connTestObject.readAll();
        for(int i =0;i<5;i++){
            Assert.assertEquals(logsForTest.get(i).get_id(),logsAfterTest.get(i).get_id());
        }


    }
    @Test
    public void updateTest(){

        for (Log log :
                logsForTest) {
            connTestObject.create(log);
        }
        for (Log log:
                logsForTest){
            log.setTimeSpent(500+logsForTest.indexOf(log));
            connTestObject.update(log);
        }
        List<Log>logsAfterTest = connTestObject.readAll();
        for(int i =0;i<5;i++){
            Assert.assertEquals(logsForTest.get(i).get_id(),logsAfterTest.get(i).get_id());
        }
    }
    @Test
    public void getTest(){
        for (Log log :
                logsForTest) {
            connTestObject.create(log);
        }
        List<Log>logsAfterTest = connTestObject.readAll();
        for(int i =0;i<5;i++){
            Assert.assertEquals(logsForTest.get(i).get_id(),logsAfterTest.get(i).get_id());
        }
    }
    @Test
    public void deleteTest(){
        for (Log log :
                logsForTest) {
            connTestObject.create(log);
        }
        for (Log log :
                logsForTest){
            connTestObject.delete(log);
        }
        List<Log> logsAfter = connTestObject.readAll();
        Assert.assertEquals(0,logsAfter.size());
    }
    @Test
    public void connectTest(){
        connTestObject.connect();
    }
    @Test
    public void ipByUrlTest(){
        Log log = logsForTest.get(0);
        connTestObject.create(log);
        Assert.assertEquals(true,connTestObject.getIpbyURL(log.getURL()).get(0).contains(log.getIP()));
    }
    @Test
    public void urlByIpTest(){
        Log log = logsForTest.get(0);
        connTestObject.create(log);
        Assert.assertEquals(true,connTestObject.getURLbyIP(log.getIP()).get(0).contains(log.getURL()));
    }

    @Test
    public void getUrlByPeriodTest(){
        Log log = logsForTest.get(0);
        connTestObject.create(log);
        Assert.assertEquals(true,connTestObject.getURLbyPeriod(new Timestamp(100),new Timestamp(1000000000)).get(0).contains(log.getURL()));
    }

    @Test
    public void removeAllTest(){
        for (Log log :
                logsForTest) {
            connTestObject.create(log);
        }
        connTestObject.removeAll();
        List<Log> logsAfter = connTestObject.readAll();
        Assert.assertEquals(0,logsAfter.size());
    }
    @After
    public void After(){
        connTestObject.removeAll();

    }

}

package Logging;

import java.sql.Timestamp;
import java.util.Date;

public class Log {
    private String URL;
    private String IP;
    private Timestamp timeStamp;
    private long timeSpent;

    public Log(String resourceURL,String userIP,Timestamp timeStamp,long timeSpent){
        this.URL =resourceURL;this.IP =userIP;this.timeStamp =timeStamp;this.timeSpent =timeSpent;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }


    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }
}

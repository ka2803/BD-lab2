package Logging;

import org.bson.types.ObjectId;

import java.sql.Timestamp;

public class Log {
    private String _id;
    private String URL;
    private String IP;
    private Timestamp timeStamp;
    private long timeSpent;
public Log(){}
    public Log(String resourceURL,String userIP,Timestamp timeStamp,long timeSpent){
        this.URL =resourceURL;this.IP =userIP;this.timeStamp =timeStamp;this.timeSpent =timeSpent;
        _id = ObjectId.get().toString();
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return _id+IP+timeSpent;
    }
}

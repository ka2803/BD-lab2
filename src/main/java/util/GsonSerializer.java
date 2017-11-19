package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSerializer<T> {
    private Class<T> className;
    public GsonSerializer(Class<T> cls){
        className=cls;
    }
    public String Serialize(T obj) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(obj);
    }
    public T Deserialize(String obj) {
        Gson gson = new GsonBuilder().create();
        return  gson.fromJson(obj,className);
    }
}
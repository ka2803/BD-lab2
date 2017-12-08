package util;

public interface JsonSerializer<T> {
    String Serialize(T obj);
    T Deserialize(String obj);
}

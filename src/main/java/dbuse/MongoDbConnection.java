package dbuse;

import java.util.List;

public interface MongoDbConnection<T>  {
    boolean create(T crObj);
    List<T> readAll();
    boolean update(T updObj);
    boolean delete(T delObj);
}

import org.junit.Assert;
import org.junit.Test;
import util.JsonFromCsvConverter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CsvToJsonTest {

    @Test
    public void CsvTOJson() throws IOException {

        String filepath = "file.csv";
        String json = "{\"_id\":\"5a1ac3f2af117a4ae406fd17\",\"URL\":\"qwe.com\",\"IP\":\"localhost\",\"timeStamp\":\"Jan 1, 1970 2:00:00 AM\",\"timeSpent\":180}";
        String resultJson = JsonFromCsvConverter.CsvToJson(filepath);
        Assert.assertEquals(json,resultJson);
    }
    @Test(expected = FileNotFoundException.class)
    public void CsvToJsonExcTest() throws IOException {
        JsonFromCsvConverter.CsvToJson("filefilefile");
    }
}

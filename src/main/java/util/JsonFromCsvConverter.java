package util;

import logging.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonFromCsvConverter {
    public static String CsvToJson(String inputCSV) throws IOException {
        Pattern pattern = Pattern.compile(",");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(inputCSV));){
            List<Log> logs;
            logs = in
                    .lines()
                    .skip(1)
                    .map(line -> {
                        String[] x = pattern.split(line);
                        Log tmp=null;

                            tmp = new Log(x[1],
                                    x[2],
                                    new Timestamp(Long.parseLong(x[3])),
                                    Long.parseLong(x[4]));
                            tmp.set_id(x[0]);

                        return tmp;
                    })
                    .collect(Collectors.toList());
            GsonSerializer<Log> ser = new GsonSerializer<Log>(Log.class);
            for (Log res:logs
                 ) {
                result.append(ser.Serialize(res));
            }
            return result.toString();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}

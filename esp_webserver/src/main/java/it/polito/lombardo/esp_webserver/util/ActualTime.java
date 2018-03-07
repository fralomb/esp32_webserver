package it.polito.lombardo.esp_webserver.util;

import it.polito.lombardo.esp_webserver.entities.ActualTimestamp;

import java.sql.Timestamp;

public class ActualTime {
    public static Long getActualTimestampInMillis() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.print("actual timestamp: "+timestamp+" - ");
        //return number of milliseconds since January 1, 1970, 00:00:00 GMT
        System.out.println("millis: "+timestamp.getTime());
        return timestamp.getTime();
    }
}

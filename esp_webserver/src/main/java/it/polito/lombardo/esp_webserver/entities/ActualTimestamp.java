package it.polito.lombardo.esp_webserver.entities;

public class ActualTimestamp {
    /*
    * the actual timestamp
    * */
    public long timestamp;

    public ActualTimestamp(long _timestamp) {
        timestamp = _timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

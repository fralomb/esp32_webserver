package it.polito.lombardo.esp_webserver.documents;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* a single probe belongs to a GlobalDevice
* */
public class ProbeEntity {
    /*
    * the Ssid of the network that the device is looking for
    * */
    @Field("ssid")
    private String ssid;

    /*
    * the int hash of the ssid
    * */
    @Field("ssid_hash")
    private Long ssid_hash;

    /*
    * the list of timestamp in which the same ssid is looked for from the device
    * */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("timestamps")
    private List<Date> timestamps;

    @Field("timestamps_millis")
    private List<Long> timestamps_millis;

    public ProbeEntity() {
        timestamps = new ArrayList<Date>();
        timestamps_millis = new ArrayList<Long>();
    }

    /*getters and setters*/
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Long getSsid_hash() {
        return ssid_hash;
    }

    public void setSsid_hash(Long ssid_hash) {
        this.ssid_hash = ssid_hash;
    }

    public List<Date> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<Date> timestamps) {
        this.timestamps = timestamps;
    }

    public void addTimestamp(Date t) {
        timestamps.add(t);
    }

    public List<Long> getTimestamps_millis() {
        return timestamps_millis;
    }

    public void setTimestamps_millis(List<Long> timestamps_millis) {
        this.timestamps_millis = timestamps_millis;
    }

    public void addTimestamp_millis(Long t) {
        timestamps_millis.add(t);
    }
}

package it.polito.lombardo.esp_webserver.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "gps_tracking")
public class GPSElements {
    /*
    * the id in the mongo collection
    * */
    @Id
    private String _id;

    /*
    * the imei of the router
    * */
    @Indexed
    @Field("imei")
    private String imei;

    /*
    * the timestamp in which the set of gps coordinates has been received
    * */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("timestamp")
    private Date timestamp;

    /*
    * the list of all the received gps data
    * */
    private List<GPSElement> gpsData;

    /*the public constructor*/
    public GPSElements() {
        this.gpsData = new ArrayList<GPSElement>();
    }

    /*getter and setters*/

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<GPSElement> getGpsData() {
        return gpsData;
    }

    public void setGpsData(List<GPSElement> gpsData) {
        this.gpsData = gpsData;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

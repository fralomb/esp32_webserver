package it.polito.lombardo.esp_webserver.documents;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
* a single element containing the gps informations in a certain timestamp
* */
public class GPSElement {
    /*
    * the timestamp in which the gps coordinates has been registered
    * */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("timestamp")
    private Date timestamp;

    /*
    * the priority
    * */
    @Field("priority")
    private int priority;

    /*
    * the longitude
     */
    @Field("lng")
    private double lng;

    /*
    * the latitude
    * */
    @Field("lat")
    private double lat;

    /*
    * the altitude (in meters)
    * */
    @Field("altitude")
    private int altitude;

    /*
    * the angle (in degrees)
    * */
    @Field("angle")
    private int angle;

    /*
    * the number of visible satellites
    * */
    @Field("satellites")
    private int satellites;

    /*
    * the speed during the capture (km/h)
    * */
    @Field("speed")
    private int speed;

    /*empty constructor*/
    public GPSElement() {

    }

    /*getter and setters*/
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

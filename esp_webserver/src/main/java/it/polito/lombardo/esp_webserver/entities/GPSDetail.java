package it.polito.lombardo.esp_webserver.entities;

import java.util.Date;

/*the detail of the gps data obtained at a certain time*/
public class GPSDetail {
    /*
    * the timestamp in which the gps coordinates has been registered
    * */
    public Date timestamp;

    /*
    * the priority
    * */
    public int priority;

    /*
    * the longitude
     */
    public double lng;

    /*
    * the latitude
    * */
    public double lat;

    /*
    * the altitude (in meters)
    * */
    public int altitude;

    /*
    * the angle (in degrees)
    * */
    public int angle;

    /*
    * the number of visible satellites
    * */
    public int satellites;

    /*
    * the speed during the capture (km/h)
    * */
    public int speed;

    public GPSDetail() {
    }
}
